/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
*/
package org.apache.airavata.xbaya.provenance;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.airavata.client.api.AiravataAPI;
import org.apache.airavata.client.api.exception.AiravataAPIInvocationException;
import org.apache.airavata.registry.api.exception.RegistryException;
import org.apache.airavata.common.utils.XMLUtil;
//import org.apache.airavata.registry.api.AiravataRegistry2;
import org.apache.airavata.registry.api.workflow.WorkflowExecution;
import org.apache.airavata.registry.api.workflow.WorkflowInstanceNode;
import org.apache.airavata.workflow.model.exceptions.WorkflowException;
import org.apache.airavata.workflow.model.graph.DataPort;
import org.apache.airavata.workflow.model.graph.ForEachExecutableNode;
import org.apache.airavata.workflow.model.graph.Node;
import org.apache.airavata.workflow.model.graph.system.EndForEachNode;
import org.apache.airavata.workflow.model.graph.system.ForEachNode;
import org.apache.airavata.workflow.model.graph.system.InputNode;
import org.apache.airavata.workflow.model.graph.ws.WSNode;
import org.apache.airavata.xbaya.concurrent.PredicatedExecutable;
import org.apache.airavata.xbaya.graph.controller.NodeController;
import org.apache.airavata.xbaya.invoker.Invoker;
import org.apache.airavata.xbaya.util.XBayaUtil;
import org.xmlpull.infoset.XmlElement;

import xsul5.XmlConstants;

/**
 * @author Chathura Herath
 */
public final class ProvenanceWrite implements PredicatedExecutable {

	private static final String PROVENANCE_DIR = "provenance";

	private Node node;

	private String workflowName;

	private Map<Node, Invoker> invokerMap;

    private String experimentId;

    private AiravataAPI airavataAPI;

	public ProvenanceWrite(Node node, String workflowName,
                           Map<Node, Invoker> invokerMap, String experimentId,AiravataAPI airavataAPI) {
		this.node = node;
		this.workflowName = workflowName;
		this.invokerMap = invokerMap;
        this.experimentId = experimentId;
        this.airavataAPI = airavataAPI;
	}

	public void run() {

		try {
			saveNodeOutputs(node, invokerMap, workflowName);
		} catch (WorkflowException e) {
			// do nothing its a failure but go on
			e.printStackTrace();
		}

	}

	public boolean isReady() {
		return NodeController.isFinished(this.node) && invokerMap.get(node) != null;
	}

	private void saveNodeOutputs(Node node,
			Map<Node, Invoker> invokerMap, String workflowName)
			throws WorkflowException {

		if (null != node && !(node instanceof InputNode)) {
			XmlElement elem = XmlConstants.BUILDER.newFragment("previousdat");
			XmlElement inputs = null;
			if (node instanceof WSNode) {
				String nodeID = node.getComponent().getName();
				XmlElement nodeElement = elem.newElement("wsnode");
				elem.addChild(nodeElement);
				nodeElement.addChild(nodeID);
				inputs = elem.newElement("inputs");
				elem.addChild(inputs);

				List<DataPort> portsToBeSaved = node.getInputPorts();
				for (DataPort savePort : portsToBeSaved) {

					String portID = savePort.getName();
					XmlElement portElem = inputs.newElement(portID);
					inputs.addChild(portElem);
					Object portInput = XBayaUtil.findInputFromPort(
                            savePort, invokerMap);
					if (portInput instanceof org.xmlpull.v1.builder.XmlElement) {
						portInput = XMLUtil
								.xmlElement3ToXmlElement5((org.xmlpull.v1.builder.XmlElement) portInput);
					}
					portElem.addChild(portInput);

				}

			} else if (node instanceof EndForEachNode) {
				// here we save the inputs for the entire foreach block
				Node middleNode = node.getInputPort(0).getFromNode();
				String nodeID = middleNode.getComponent().getName();
				XmlElement nodeElement = elem.newElement("foreach");
				elem.addChild(nodeElement);
				nodeElement.addChild(nodeID);
				inputs = elem.newElement("inputs");
				elem.addChild(inputs);
				XmlConstants.BUILDER.serializeToString(elem);
				if (middleNode instanceof ForEachExecutableNode) {
					List<DataPort> portsToBeSaved = middleNode.getInputPorts();
					for (DataPort savePort : portsToBeSaved) {
						// we will save all the inputs
						// these are static inputs and
						// input to the foreach node

						if (savePort.getFromNode() instanceof ForEachNode) {
							// this is the foreach node rest are simple
							// inputs
							Object value = XBayaUtil
									.getInputsForForEachNode(
											(ForEachNode) savePort
													.getFromNode(),
											new LinkedList<String>(),
											invokerMap);
							if (value instanceof org.xmlpull.v1.builder.XmlElement) {
								value = XMLUtil
										.xmlElement3ToXmlElement5((org.xmlpull.v1.builder.XmlElement) value);
							}

							XmlElement portElement = inputs.newElement(savePort
									.getName());
							inputs.addChild(portElement);
							portElement.addChild(value);
						} else {
							String portID = savePort.getName();
							XmlElement portElem = inputs.newElement(portID);
							inputs.addChild(portElem);
							Object portInput = XBayaUtil
									.findInputFromPort(savePort, invokerMap);
							if (portInput instanceof org.xmlpull.v1.builder.XmlElement) {
								portInput = XMLUtil
										.xmlElement3ToXmlElement5((org.xmlpull.v1.builder.XmlElement) portInput);
							}

							portElem.addChild(portInput);
						}

					}

				} else {
					// error but we will let it pass because it will be
					// caught at higher level
				}

			}
            if (inputs!=null) {
				try {
					this.airavataAPI.getProvenanceManager().setWorkflowInstanceNodeInput(new WorkflowInstanceNode(new WorkflowExecution(experimentId, experimentId), node.getID()), xsul5.XmlConstants.BUILDER.serializeToString(inputs));
                } catch (AiravataAPIInvocationException e) {
					throw new WorkflowException(e);
				}
				// deal with the outputs
			}
			XmlElement outputs = elem.newElement("outputs");
			elem.addChild(outputs);

			List<DataPort> outputPorts = node.getOutputPorts();
			for (DataPort outputPort : outputPorts) {
				String outputName = outputPort.getName();

				XmlElement outputParamElement = outputs.newElement(outputName);
				outputs.addChild(outputParamElement);
				Object ouputParamValue = invokerMap.get(node).getOutput(
						outputName);

				if (ouputParamValue instanceof org.xmlpull.v1.builder.XmlElement) {
					ouputParamValue = XMLUtil
							.xmlElement3ToXmlElement5((org.xmlpull.v1.builder.XmlElement) ouputParamValue);
				}

				if (ouputParamValue != null) {
					outputParamElement.addChild(ouputParamValue);
				} else {
					outputParamElement.addChild("null");
				}
			}
            try {
				this.airavataAPI.getProvenanceManager().setWorkflowInstanceNodeOutput(new WorkflowInstanceNode(new WorkflowExecution(experimentId,experimentId),node.getID()),xsul5.XmlConstants.BUILDER.serializeToString(outputs));
            } catch (AiravataAPIInvocationException e) {
				throw new WorkflowException(e);
			}
		}
	}
}
