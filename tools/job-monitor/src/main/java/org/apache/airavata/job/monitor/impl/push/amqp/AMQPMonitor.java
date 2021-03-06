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
package org.apache.airavata.job.monitor.impl.push.amqp;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.apache.airavata.common.utils.Constants;
import org.apache.airavata.common.utils.ServerSettings;
import org.apache.airavata.job.monitor.HostMonitorData;
import org.apache.airavata.job.monitor.MonitorID;
import org.apache.airavata.job.monitor.UserMonitorData;
import org.apache.airavata.job.monitor.core.PushMonitor;
import org.apache.airavata.job.monitor.event.MonitorPublisher;
import org.apache.airavata.job.monitor.exception.AiravataMonitorException;
import org.apache.airavata.job.monitor.util.AMQPConnectionUtil;
import org.apache.airavata.job.monitor.util.CommonUtils;
import org.apache.airavata.schemas.gfac.GsisshHostType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.BlockingQueue;

/**
 * This is the implementation for AMQP based finishQueue, this uses
 * rabbitmq client to recieve AMQP based monitoring data from
 * mostly excede resources.
 */
public class AMQPMonitor extends PushMonitor {
    private final static Logger logger = LoggerFactory.getLogger(AMQPMonitor.class);


    /* this will keep all the channels available in the system, we do not create
      channels for all the jobs submitted, but we create channels for each user for each
      host.
    */
    private Map<String, Channel> availableChannels;

    private MonitorPublisher publisher;

    private BlockingQueue<UserMonitorData> runningQueue;

    private BlockingQueue<MonitorID> finishQueue;

    private String connectionName;

    private String proxyPath;

    private List<String> amqpHosts;

    private boolean startRegister;

    public AMQPMonitor(){

    }
    public AMQPMonitor(MonitorPublisher publisher, BlockingQueue<UserMonitorData> runningQueue,
                       BlockingQueue<MonitorID> finishQueue,
                       String proxyPath,String connectionName,List<String> hosts) {
        this.publisher = publisher;
        this.runningQueue = runningQueue;        // these will be initialized by the MonitorManager
        this.finishQueue = finishQueue;          // these will be initialized by the MonitorManager
        this.availableChannels = new HashMap<String, Channel>();
        this.connectionName = connectionName;
        this.proxyPath = proxyPath;
        this.amqpHosts = hosts;
    }

    public void initialize(String proxyPath, String connectionName, List<String> hosts) {
        this.availableChannels = new HashMap<String, Channel>();
        this.connectionName = connectionName;
        this.proxyPath = proxyPath;
        this.amqpHosts = hosts;
    }

    @Override
    public boolean registerListener(UserMonitorData userMonitorData) throws AiravataMonitorException {
        List<HostMonitorData> hostNames = userMonitorData.getHostMonitorData();
        String userName = userMonitorData.getUserName();
        for (HostMonitorData host : hostNames) {
            // with amqp monitor we do not use individual monitorID list but
            // we subscribe to read user-host based subscription
            String hostAddress = host.getHost().getType().getHostAddress();
            String channelID = CommonUtils.getChannelID(userName, hostAddress);
            if (availableChannels.get(channelID) == null) {
                //todo need to fix this rather getting it from a file
                Connection connection = AMQPConnectionUtil.connect(amqpHosts, connectionName, proxyPath);
                Channel channel = null;
                try {
                    channel = connection.createChannel();
                    String queueName = channel.queueDeclare().getQueue();

                    BasicConsumer consumer = new BasicConsumer(new JSONMessageParser(), publisher, host);
                    channel.basicConsume(queueName, true, consumer);
                    String filterString = CommonUtils.getRoutingKey(userName, hostAddress);
                    // here we queuebind to a particular user in a particular machine
                    channel.queueBind(queueName, "glue2.computing_activity", filterString);
                    logger.info("Using filtering string to monitor: " + filterString);
                } catch (IOException e) {
                    logger.error("Error creating the connection to finishQueue the job:" + userMonitorData.getUserName());
                }
            }

        }
        return true;
    }

    public void run() {
        // before going to the while true mode we start unregister thread
        startRegister = true; // this will be unset by someone else
        while (startRegister || !ServerSettings.isStopAllThreads()) {
            try {
                UserMonitorData take = runningQueue.take();
                this.registerListener(take);
            } catch (AiravataMonitorException e) { // catch any exceptino inside the loop
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        Set<String> strings = availableChannels.keySet();
        for(String key:strings) {
            Channel channel = availableChannels.get(key);
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }






    @Override
    public boolean unRegisterListener(MonitorID monitorID) throws AiravataMonitorException {
        String channelID = CommonUtils.getChannelID(monitorID);
        Channel channel = availableChannels.get(channelID);
        if (channel == null) {
            logger.error("Already Unregistered the listener");
            throw new AiravataMonitorException("Already Unregistered the listener");
        } else {
            try {
                channel.queueUnbind(channel.queueDeclare().getQueue(), "glue2.computing_activity", CommonUtils.getRoutingKey(monitorID));
            } catch (IOException e) {
                logger.error("Error unregistering the listener");
                throw new AiravataMonitorException("Error unregistering the listener");
            }



        }
        return true;
    }

    @Override
    public boolean stopRegister() throws AiravataMonitorException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Map<String, Channel> getAvailableChannels() {
        return availableChannels;
    }

    public void setAvailableChannels(Map<String, Channel> availableChannels) {
        this.availableChannels = availableChannels;
    }

    public MonitorPublisher getPublisher() {
        return publisher;
    }

    public void setPublisher(MonitorPublisher publisher) {
        this.publisher = publisher;
    }

    public BlockingQueue<UserMonitorData> getRunningQueue() {
        return runningQueue;
    }

    public void setRunningQueue(BlockingQueue<UserMonitorData> runningQueue) {
        this.runningQueue = runningQueue;
    }

    public BlockingQueue<MonitorID> getFinishQueue() {
        return finishQueue;
    }

    public void setFinishQueue(BlockingQueue<MonitorID> finishQueue) {
        this.finishQueue = finishQueue;
    }

    public String getProxyPath() {
        return proxyPath;
    }

    public void setProxyPath(String proxyPath) {
        this.proxyPath = proxyPath;
    }

    public List<String> getAmqpHosts() {
        return amqpHosts;
    }

    public void setAmqpHosts(List<String> amqpHosts) {
        this.amqpHosts = amqpHosts;
    }

    public boolean isStartRegister() {
        return startRegister;
    }

    public void setStartRegister(boolean startRegister) {
        this.startRegister = startRegister;
    }
}
