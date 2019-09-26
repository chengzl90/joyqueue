/**
 * Copyright 2019 The JoyQueue Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.chubao.joyqueue.broker.manage;

import io.chubao.joyqueue.broker.archive.ArchiveManager;
import io.chubao.joyqueue.broker.cluster.ClusterManager;
import io.chubao.joyqueue.broker.consumer.Consume;
import io.chubao.joyqueue.broker.consumer.MessageConvertSupport;
import io.chubao.joyqueue.broker.coordinator.CoordinatorService;
import io.chubao.joyqueue.broker.manage.config.BrokerManageConfig;
import io.chubao.joyqueue.broker.manage.exporter.BrokerManageExporter;
import io.chubao.joyqueue.broker.monitor.BrokerMonitorService;
import io.chubao.joyqueue.nsr.NameService;
import io.chubao.joyqueue.server.retry.api.MessageRetry;
import io.chubao.joyqueue.store.StoreManagementService;
import io.chubao.joyqueue.store.StoreService;
import io.chubao.joyqueue.toolkit.service.Service;

/**
 * brokermonitor
 *
 * author: gaohaoxiang
 * date: 2018/10/15
 */
public class BrokerManageService extends Service {

    private BrokerManageConfig config;
    private BrokerManageServiceManager brokerManageServiceManager;
    private BrokerManageExporter brokerManageExporter;
    private StoreService storeService;
    private MessageRetry retryManager;
    private CoordinatorService coordinatorService;
    private ArchiveManager archiveManager;
    private MessageConvertSupport messageConvertSupport;

    public BrokerManageService(BrokerManageConfig config, BrokerMonitorService brokerMonitorService,
                               ClusterManager clusterManager, StoreManagementService storeManagementService,
                               StoreService storeService, Consume consume,
                               MessageRetry retryManager, CoordinatorService coordinatorService,
                               ArchiveManager archiveManager, NameService nameService,
                               MessageConvertSupport messageConvertSupport) {
        this.config = config;
        this.storeService = storeService;
        this.retryManager = retryManager;
        this.coordinatorService = coordinatorService;
        this.brokerManageServiceManager = new BrokerManageServiceManager(brokerMonitorService.getBrokerMonitor(),
                clusterManager, storeManagementService,
                storeService, consume,
                retryManager, coordinatorService,
                archiveManager, nameService, messageConvertSupport);
        this.brokerManageExporter = new BrokerManageExporter(config, brokerManageServiceManager);
    }

    public void registerService(String key, Object service) {
        brokerManageExporter.registerService(key, service);
    }

    @Override
    protected void doStart() throws Exception {
        brokerManageServiceManager.start();
        brokerManageExporter.start();
    }

    @Override
    protected void doStop() {
        if (brokerManageExporter != null) {
            brokerManageExporter.stop();
        }
        if (brokerManageServiceManager != null) {
            brokerManageServiceManager.stop();
        }
    }

    public BrokerManageConfig getConfig() {
        return config;
    }

    public BrokerManageServiceManager getBrokerManageServiceManager() {
        return brokerManageServiceManager;
    }
}