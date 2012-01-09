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
package org.apache.airavata.xbaya.clients;

import org.apache.airavata.xbaya.component.ComponentException;
import org.apache.airavata.xbaya.component.ws.WSComponentPort;
import org.apache.airavata.xbaya.graph.GraphException;
import org.apache.airavata.xbaya.graph.impl.NodeImpl;
import org.apache.airavata.xbaya.graph.system.InputNode;
import org.apache.airavata.xbaya.interpretor.NameValue;
import org.apache.airavata.xbaya.interpretor.WorkflowInterpretorStub;
import org.apache.airavata.xbaya.wf.Workflow;
import org.apache.axis2.AxisFault;
import xsul5.MLogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Properties;


public class XBayaClient {
    private static final MLogger log = MLogger.getLogger();

    public static final String GFAC = "gfac";
    public static final String PROXYSERVER = "proxyserver";
    public static final String MSGBOX = "msgbox";
    public static final String BROKER = "broker";
    public static final String DEFAULT_GFAC_URL = "gfac.url";
    public static final String DEFAULT_MYPROXY_SERVER = "myproxy.url";
    public static final String DEFAULT_MESSAGE_BOX_URL = "messagebox.url";
    public static final String DEFAULT_BROKER_URL = "messagebroker.url";
    public static final String MYPROXYUSERNAME = "myproxy.username";
    public static final String MYPROXYPASS = "myproxy.password";
    public static final String WORKFLOWSERVICEURL = "xbaya.service.url";
    private static String workflow = "";

    private NameValue[] configurations = new NameValue[7];

    public XBayaClient(NameValue[] configuration) {
        configurations = configuration;
    }

    public XBayaClient(String fileName) throws IOException {
        URL url = this.getClass().getClassLoader().getResource(fileName);
        Properties properties = new Properties();
        properties.load(url.openStream());

        configurations[0] = new NameValue();
        configurations[0].setName(GFAC);
        configurations[0].setValue(properties.getProperty(DEFAULT_GFAC_URL));

        configurations[1] = new NameValue();
        configurations[1].setName(PROXYSERVER);
        configurations[1].setValue(properties.getProperty(DEFAULT_MYPROXY_SERVER));

        configurations[2] = new NameValue();
        configurations[2].setName(MSGBOX);
        configurations[2].setValue(properties.getProperty(DEFAULT_MESSAGE_BOX_URL));

        configurations[3] = new NameValue();
        configurations[3].setName(BROKER);
        configurations[3].setValue(properties.getProperty(DEFAULT_BROKER_URL));

        configurations[4] = new NameValue();
        configurations[4].setName(MYPROXYUSERNAME);
        configurations[4].setValue(properties.getProperty(MYPROXYUSERNAME));

        configurations[5] = new NameValue();
        configurations[5].setName(MYPROXYPASS);
        configurations[5].setValue(properties.getProperty(MYPROXYPASS));

        configurations[6] = new NameValue();
        configurations[6].setName(WORKFLOWSERVICEURL);
        configurations[6].setValue(properties.getProperty(WORKFLOWSERVICEURL));

    }

    public void loadWorkflowFromaFile(String workflowFile)throws URISyntaxException,IOException {

        URL url = XBayaClient.class.getClassLoader().getResource(workflowFile);
        FileInputStream stream = new FileInputStream(new File(url.toURI()));
        try {
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            /* Instead of using default, pass in a decoder. */
            this.workflow = Charset.defaultCharset().decode(bb).toString();
        } finally {
            stream.close();
        }
    }

    public void loadWorkflowasaString(String workflowAsaString) {
        this.workflow = workflowAsaString;
    }


    public NameValue[] setInputs(String fileName) throws IOException {
        URL url = this.getClass().getClassLoader().getResource(fileName);
        Properties properties = new Properties();
        properties.load(url.openStream());
        try {
            Workflow workflow = new Workflow(this.workflow);
            List<NodeImpl> inputs = workflow.getGraph().getNodes();
            int inputSize = 0;
            for (NodeImpl input : inputs) {
                if(input instanceof InputNode){
                    inputSize++;
                }
            }
            NameValue[] inputNameVals = new NameValue[inputSize];
            int index =0;
            for (NodeImpl input : inputs) {
                if(input instanceof InputNode){
                    inputNameVals[index] = new NameValue();
                    String name = input.getName();
                    inputNameVals[index].setName(name);
                    inputNameVals[index].setValue(properties.getProperty(name));
                    ((InputNode) input).setDefaultValue(properties.getProperty(name));
                    index++;
                }
            }
//            setWorkflow(XMLUtil.xmlElementToString((workflow.toXML()));
            return inputNameVals;
        } catch (GraphException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ComponentException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public void setInputs(Properties inputList){
       try {
            Workflow workflow = new Workflow(this.workflow);
            List<WSComponentPort> inputs = workflow.getInputs();
            for (WSComponentPort input : inputs) {
                input.setValue(inputList.getProperty(input.getName()));
            }
        } catch (GraphException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ComponentException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public String runWorkflow(String topic){
		String worflowoutput= null;
		try {
			WorkflowInterpretorStub stub = new WorkflowInterpretorStub(configurations[4].getValue());
		    worflowoutput = stub.launchWorkflow(workflow, topic, configurations[5].getValue(), configurations[6].getValue(), null,
					configurations);
		    log.info("Workflow output : " + worflowoutput);
		} catch (AxisFault e) {
			log.fine(e.getMessage(), e);
		} catch (RemoteException e) {
			log.fine(e.getMessage(), e);
		} catch (IOException e) {
			log.fine(e.getMessage(), e);
		}
		return worflowoutput;
	}

        public String runWorkflow(String topic, NameValue[] inputs){
		String worflowoutput= null;
		try {
			WorkflowInterpretorStub stub = new WorkflowInterpretorStub(configurations[6].getValue());
		    worflowoutput = stub.launchWorkflow(workflow, topic, configurations[5].getValue(), configurations[4].getValue(), inputs,
					configurations);
		    log.info("Workflow output : " + worflowoutput);
		} catch (AxisFault e) {
			log.fine(e.getMessage(), e);
		} catch (RemoteException e) {
			log.fine(e.getMessage(), e);
		} catch (IOException e) {
			log.fine(e.getMessage(), e);
		}
		return worflowoutput;
	}

    public static String getWorkflow() {
        return workflow;
    }

    public static void setWorkflow(String workflow) {
        XBayaClient.workflow = workflow;
    }
}
