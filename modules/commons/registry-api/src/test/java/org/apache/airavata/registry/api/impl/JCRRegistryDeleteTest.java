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
package org.apache.airavata.registry.api.impl;

import org.apache.airavata.common.utils.IOUtil;
import org.apache.airavata.commons.gfac.type.HostDescription;
import org.apache.airavata.commons.gfac.type.ServiceDescription;
import org.apache.airavata.schemas.gfac.InputParameterType;
import org.apache.airavata.schemas.gfac.OutputParameterType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

public class JCRRegistryDeleteTest {
    @Test
       public void testHostDescriptionDelete() {
           try {
               /*
                * Create database
                */
               JCRRegistry jcrRegistry = new JCRRegistry(null, "org.apache.jackrabbit.core.RepositoryFactoryImpl",
                       "admin", "admin", null);

               String hostId = "localhost";
               String address = "127.0.0.1";
               /*
                * Host
                */
               HostDescription host = new HostDescription();
               host.getType().setHostName(hostId);
               host.getType().setHostAddress(address);

               jcrRegistry.saveHostDescription(host);

               jcrRegistry.deleteHostDescription(hostId);

               HostDescription hostR = jcrRegistry.getHostDescription(hostId);
               Assert.assertNull(hostR);


           } catch (Exception e) {
               e.printStackTrace();
               fail(e.getMessage());
           }
       }

    @Test
    public void testServiceDescriptionDelete() {
        try {
            /*
            * Create database
            */
            JCRRegistry jcrRegistry = new JCRRegistry(null, "org.apache.jackrabbit.core.RepositoryFactoryImpl",
                    "admin", "admin", null);

            ServiceDescription serv = new ServiceDescription();
            serv.getType().setName("SimpleEcho");

            InputParameterType input = InputParameterType.Factory.newInstance();
            input.setParameterName("echo_input");
            List<InputParameterType> inputList = new ArrayList<InputParameterType>();
            inputList.add(input);
            InputParameterType[] inputParamList = inputList.toArray(new InputParameterType[inputList
                    .size()]);

            OutputParameterType output = OutputParameterType.Factory.newInstance();
            output.setParameterName("echo_output");
            List<OutputParameterType> outputList = new ArrayList<OutputParameterType>();
            outputList.add(output);
            OutputParameterType[] outputParamList = outputList
                    .toArray(new OutputParameterType[outputList.size()]);
            serv.getType().setInputParametersArray(inputParamList);
            serv.getType().setOutputParametersArray(outputParamList);

            /*
             * Save to registry
             */
            jcrRegistry.saveServiceDescription(serv);

            jcrRegistry.deleteServiceDescription(serv.getType().getName());
            jcrRegistry.getServiceDescription(serv.getType().getName());
        } catch (Exception e) {
            junit.framework.Assert.assertTrue(true);
            return;
        }
        Assert.assertTrue(false);
    }

        @After
    public void cleanup(){
        File jackrabbit = new File(".");
           String s = jackrabbit.getAbsolutePath() + File.separator +
                   "modules" + File.separator + "registry-api" + File.separator +"jackrabbit";
           IOUtil.deleteDirectory(new File(s));
    }
}