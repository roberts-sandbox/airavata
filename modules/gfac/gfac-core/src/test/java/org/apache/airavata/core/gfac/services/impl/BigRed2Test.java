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
package org.apache.airavata.core.gfac.services.impl;

import org.apache.airavata.commons.gfac.type.ActualParameter;
import org.apache.airavata.commons.gfac.type.ApplicationDescription;
import org.apache.airavata.commons.gfac.type.HostDescription;
import org.apache.airavata.commons.gfac.type.ServiceDescription;
import org.apache.airavata.gfac.GFacConfiguration;
import org.apache.airavata.gfac.GFacException;
import org.apache.airavata.gfac.SecurityContext;
import org.apache.airavata.gfac.context.ApplicationContext;
import org.apache.airavata.gfac.context.JobExecutionContext;
import org.apache.airavata.gfac.context.MessageContext;
import org.apache.airavata.gfac.context.security.SSHSecurityContext;
import org.apache.airavata.gfac.cpi.GFacImpl;
import org.apache.airavata.gsi.ssh.api.Cluster;
import org.apache.airavata.gsi.ssh.api.SSHApiException;
import org.apache.airavata.gsi.ssh.api.ServerInfo;
import org.apache.airavata.gsi.ssh.api.authentication.AuthenticationInfo;
import org.apache.airavata.gsi.ssh.api.job.JobManagerConfiguration;
import org.apache.airavata.gsi.ssh.api.job.PBSJobConfiguration;
import org.apache.airavata.gsi.ssh.impl.PBSCluster;
import org.apache.airavata.gsi.ssh.impl.authentication.DefaultPasswordAuthenticationInfo;
import org.apache.airavata.gsi.ssh.util.CommonUtils;
import org.apache.airavata.schemas.gfac.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class BigRed2Test {
    private JobExecutionContext jobExecutionContext;

    private static final String hostAddress = "bigred2";
    private static final String hostName = "bigred2.uits.iu.edu";
    private static  String userName = "lginnali";
    private static  String password = "";

    @Before
    public void setUp() throws Exception {

        if(System.getProperty("bigred2.password") == null || System.getProperty("bigred2.username") == null){
            System.out.println("set the bigred2 password/username in maven command : mvn clean install -Dbigred2.username=xxx -Dbigred2.password=yyy");
            throw new Exception("Wrong inputs given");
        }
        userName = System.getProperty("bigred2.username");
        password = System.getProperty("bigred2.password");
        URL resource = GramProviderTest.class.getClassLoader().getResource(org.apache.airavata.common.utils.Constants.GFAC_CONFIG_XML);
        assert resource != null;
        System.out.println(resource.getFile());
        GFacConfiguration gFacConfiguration = GFacConfiguration.create(new File(resource.getPath()), null, null);

//        gFacConfiguration.setMyProxyLifeCycle(3600);
//        gFacConfiguration.setMyProxyServer("myproxy.teragrid.org");
//        gFacConfiguration.setMyProxyUser("*****");
//        gFacConfiguration.setMyProxyPassphrase("*****");
//        gFacConfiguration.setTrustedCertLocation("./certificates");
//        //have to set InFlwo Handlers and outFlowHandlers
//        gFacConfiguration.setInHandlers(Arrays.asList(new String[] {"org.apache.airavata.gfac.handler.GramDirectorySetupHandler","org.apache.airavata.gfac.handler.GridFTPInputHandler"}));
//        gFacConfiguration.setOutHandlers(Arrays.asList(new String[] {"org.apache.airavata.gfac.handler.GridFTPOutputHandler"}));

        /*
        * Host
        */
        HostDescription host = new HostDescription(SSHHostType.type);
        host.getType().setHostAddress(hostAddress);
        host.getType().setHostName(hostName);
        ((SSHHostType)host.getType()).setHpcResource(true);
        /*
        * App
        */
        ApplicationDescription appDesc = new ApplicationDescription(HpcApplicationDeploymentType.type);
        HpcApplicationDeploymentType app = (HpcApplicationDeploymentType) appDesc.getType();
        ApplicationDeploymentDescriptionType.ApplicationName name = ApplicationDeploymentDescriptionType.ApplicationName.Factory.newInstance();
        name.setStringValue("EchoLocal");
        app.setApplicationName(name);

        app.setCpuCount(1);
        app.setJobType(JobTypeType.SERIAL);
        app.setNodeCount(1);
        app.setProcessorsPerNode(1);

        /*
        * Use bat file if it is compiled on Windows
        */
        app.setExecutableLocation("/bin/echo");

        /*
        * Default tmp location
        */
        String tempDir = "~/";
        String date = (new Date()).toString();
        date = date.replaceAll(" ", "_");
        date = date.replaceAll(":", "_");

        tempDir = tempDir + File.separator
                + "SimpleEcho" + "_" + date + "_" + UUID.randomUUID();

        System.out.println(tempDir);
        app.setScratchWorkingDirectory(tempDir);
        app.setStaticWorkingDirectory(tempDir);
        app.setInputDataDirectory(tempDir + File.separator + "inputData");
        app.setOutputDataDirectory(tempDir + File.separator + "outputData");
        app.setStandardOutput(tempDir + File.separator + app.getApplicationName().getStringValue() + ".stdout");
        app.setStandardError(tempDir + File.separator + app.getApplicationName().getStringValue() + ".stderr");
        app.setMaxWallTime(5);
        app.setJobSubmitterCommand("aprun -n 1");
        app.setInstalledParentPath("/opt/torque/torque-4.2.3.1/bin/");

        /*
        * Service
        */
        ServiceDescription serv = new ServiceDescription();
        serv.getType().setName("SimpleEcho");

        List<InputParameterType> inputList = new ArrayList<InputParameterType>();

        InputParameterType input = InputParameterType.Factory.newInstance();
        input.setParameterName("echo_input");
        input.setParameterType(StringParameterType.Factory.newInstance());
        inputList.add(input);

        InputParameterType[] inputParamList = inputList.toArray(new InputParameterType[inputList

                .size()]);
        List<OutputParameterType> outputList = new ArrayList<OutputParameterType>();
        OutputParameterType output = OutputParameterType.Factory.newInstance();
        output.setParameterName("echo_output");
        output.setParameterType(StringParameterType.Factory.newInstance());
        outputList.add(output);

        OutputParameterType[] outputParamList = outputList
                .toArray(new OutputParameterType[outputList.size()]);

        serv.getType().setInputParametersArray(inputParamList);
        serv.getType().setOutputParametersArray(outputParamList);

        jobExecutionContext = new JobExecutionContext(gFacConfiguration, serv.getType().getName());
        // Adding security context
        jobExecutionContext.addSecurityContext(SSHSecurityContext.SSH_SECURITY_CONTEXT, getSecurityContext(app));
        ApplicationContext applicationContext = new ApplicationContext();
        jobExecutionContext.setApplicationContext(applicationContext);
        applicationContext.setServiceDescription(serv);
        applicationContext.setApplicationDeploymentDescription(appDesc);
        applicationContext.setHostDescription(host);

        MessageContext inMessage = new MessageContext();
        ActualParameter echo_input = new ActualParameter();
        ((StringParameterType) echo_input.getType()).setValue("echo_output=hello");
        inMessage.addParameter("echo_input", echo_input);


        jobExecutionContext.setInMessageContext(inMessage);

        MessageContext outMessage = new MessageContext();
        ActualParameter echo_out = new ActualParameter();
//		((StringParameterType)echo_input.getType()).setValue("echo_output=hello");
        outMessage.addParameter("echo_output", echo_out);

        jobExecutionContext.setOutMessageContext(   outMessage);

    }

    private SecurityContext getSecurityContext(HpcApplicationDeploymentType app) {
        AuthenticationInfo authenticationInfo = new DefaultPasswordAuthenticationInfo(this.password);
        // Server info
        ServerInfo serverInfo = new ServerInfo(this.userName, this.hostName);

        Cluster pbsCluster = null;
        try {
            JobManagerConfiguration pbsJobManager = CommonUtils.getPBSJobManager(app.getInstalledParentPath());
             pbsCluster = new PBSCluster(serverInfo, authenticationInfo,pbsJobManager);
        } catch (SSHApiException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        SSHSecurityContext sshSecurityContext = new SSHSecurityContext();
        sshSecurityContext.setPbsCluster(pbsCluster);
        sshSecurityContext.setUsername(userName);
        return sshSecurityContext;
    }

    @Test
    public void testGramProvider() throws GFacException {
        GFacImpl gFacAPI = new GFacImpl();
        gFacAPI.submitJob(jobExecutionContext);
    }

}
