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

package org.apache.airavata.xbaya.model.registrybrowser;

import org.apache.airavata.client.api.AiravataAPI;
import org.apache.airavata.registry.api.AiravataRegistry2;

public class AiravataConfigurations {
//    private AiravataRegistry2 registry;
    private AiravataAPI airavataAPI;

//    public AiravataConfigurations(AiravataRegistry2 registry) {
//        setRegistry(registry);
//    }
    public AiravataConfigurations(AiravataAPI airavataAPI) {
        setAiravataAPI(airavataAPI);
    }

//    public AiravataRegistry2 getRegistry() {
//        return registry;
//    }
//
//    public void setRegistry(AiravataRegistry2 registry) {
//        this.registry = registry;
//    }


    public AiravataAPI getAiravataAPI() {
        return airavataAPI;
    }

    public void setAiravataAPI(AiravataAPI airavataAPI) {
        this.airavataAPI = airavataAPI;
    }
}
