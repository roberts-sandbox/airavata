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
package org.apache.airavata.gfac.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.airavata.gfac.GFacException;
import org.apache.airavata.gfac.context.JobExecutionContext;
import org.apache.airavata.gfac.context.security.GSISecurityContext;
import org.apache.airavata.gfac.context.security.SSHSecurityContext;
import org.apache.airavata.gfac.utils.GFacUtils;
import org.apache.airavata.gsi.ssh.api.Cluster;
import org.apache.airavata.gsi.ssh.api.SSHApiException;
import org.apache.airavata.model.workspace.experiment.CorrectiveAction;
import org.apache.airavata.model.workspace.experiment.DataTransferDetails;
import org.apache.airavata.model.workspace.experiment.ErrorCategory;
import org.apache.airavata.model.workspace.experiment.TransferState;
import org.apache.airavata.model.workspace.experiment.TransferStatus;
import org.apache.airavata.registry.cpi.ChildDataType;
import org.apache.airavata.schemas.gfac.ApplicationDeploymentDescriptionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SCPDirectorySetupHandler extends AbstractHandler{
    private static final Logger log = LoggerFactory.getLogger(SCPDirectorySetupHandler.class);
    private List<String> errors = new ArrayList<String>();

	public void invoke(JobExecutionContext jobExecutionContext) throws GFacException {
		log.info("Setup SSH job directorties");
	    super.invoke(jobExecutionContext);
		makeDirectory(jobExecutionContext);

	}
	private void makeDirectory(JobExecutionContext jobExecutionContext) throws GFacException {
		Cluster cluster = null;
            if (jobExecutionContext.getSecurityContext(GSISecurityContext.GSI_SECURITY_CONTEXT) != null) {
                cluster = ((GSISecurityContext) jobExecutionContext.getSecurityContext(GSISecurityContext.GSI_SECURITY_CONTEXT)).getPbsCluster();
            } else {
                cluster = ((SSHSecurityContext) jobExecutionContext.getSecurityContext(SSHSecurityContext.SSH_SECURITY_CONTEXT)).getPbsCluster();
            }
            if (cluster == null) {
                throw new GFacHandlerException("Security context is not set properly");
            } else {
                log.info("Successfully retrieved the Security Context");
            }
		ApplicationDeploymentDescriptionType app = jobExecutionContext.getApplicationContext().getApplicationDeploymentDescription().getType();
		try {
            String workingDirectory = app.getScratchWorkingDirectory();
			cluster.makeDirectory(workingDirectory);
            cluster.makeDirectory(app.getScratchWorkingDirectory());
            cluster.makeDirectory(app.getInputDataDirectory());
            cluster.makeDirectory(app.getOutputDataDirectory());
            DataTransferDetails detail = new DataTransferDetails();
            TransferStatus status = new TransferStatus();
            status.setTransferState(TransferState.DIRECTORY_SETUP);
            detail.setTransferStatus(status);
            detail.setTransferDescription("Working directory = " + workingDirectory);
         
            registry.add(ChildDataType.DATA_TRANSFER_DETAIL,detail, jobExecutionContext.getTaskData().getTaskID());
          
		} catch (SSHApiException e) {
            throw new GFacHandlerException("Error executing the Handler: " + SCPDirectorySetupHandler.class,e);  
        }catch (Exception e){
            DataTransferDetails detail = new DataTransferDetails();
            TransferStatus status = new TransferStatus();
            status.setTransferState(TransferState.FAILED);
            detail.setTransferStatus(status);
            try {
				registry.add(ChildDataType.DATA_TRANSFER_DETAIL,detail, jobExecutionContext.getTaskData().getTaskID());
				GFacUtils.saveErrorDetails(e.getLocalizedMessage(), CorrectiveAction.CONTACT_SUPPORT, ErrorCategory.FILE_SYSTEM_FAILURE,  jobExecutionContext.getTaskData().getTaskID());
	        } catch (Exception e1) {
			    throw new GFacHandlerException("Error persisting status", e1, e1.getLocalizedMessage());
		   }
            throw new GFacHandlerException("Error executing the Handler: " + SCPDirectorySetupHandler.class,e); 
        }
	}

    public void initProperties(Map<String, String> properties) throws GFacHandlerException, GFacException {

    }
}
