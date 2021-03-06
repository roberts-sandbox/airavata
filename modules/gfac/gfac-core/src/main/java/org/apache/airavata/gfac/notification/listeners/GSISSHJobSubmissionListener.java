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
package org.apache.airavata.gfac.notification.listeners;

import org.apache.airavata.gfac.context.JobExecutionContext;
import org.apache.airavata.gfac.notification.events.StatusChangeEvent;
import org.apache.airavata.gsi.ssh.api.SSHApiException;
import org.apache.airavata.gsi.ssh.api.job.JobDescriptor;
import org.apache.airavata.gsi.ssh.impl.JobStatus;
import org.apache.airavata.gsi.ssh.listener.JobSubmissionListener;

public class GSISSHJobSubmissionListener extends JobSubmissionListener {

    JobExecutionContext context;

    public GSISSHJobSubmissionListener(JobExecutionContext context) {
        this.context = context;
    }

    public void statusChanged(JobDescriptor jobDescriptor) throws SSHApiException {
        this.context.getNotifier().publish(new StatusChangeEvent("Job status has changed to : " + jobDescriptor.getStatus()));
    }

    @Override
    public void statusChanged(JobStatus jobStatus) throws SSHApiException {
        this.context.getNotifier().publish(new StatusChangeEvent("Job status has changed to : " + jobStatus.toString()));
    }

    @Override
    public boolean isJobDone() throws SSHApiException {
        return getJobStatus().equals(JobStatus.C);
    }

    public void setContext(JobExecutionContext context) {
        this.context = context;
    }
}
