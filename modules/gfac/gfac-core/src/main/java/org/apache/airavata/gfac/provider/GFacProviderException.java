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

package org.apache.airavata.gfac.provider;

import org.apache.airavata.gfac.context.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GFacProviderException extends Exception {
    private static final Logger log = LoggerFactory.getLogger(GFacProviderException.class);

    private String aditionalInfo[] = null;

    public GFacProviderException(String message) {
        super(message);
        log.error(message);
    }


    public GFacProviderException(String message, Throwable cause) {
        super(message, cause);
        log.error(message,cause);
    }


    public GFacProviderException(String message, Exception e, String... additionExceptiondata) {
        super(message);
        this.aditionalInfo = additionExceptiondata;
        log.error(message,e);
    }

}
