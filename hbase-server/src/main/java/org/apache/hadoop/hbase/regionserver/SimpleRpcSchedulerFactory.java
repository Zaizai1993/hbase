/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hbase.regionserver;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Abortable;
import org.apache.hadoop.hbase.HBaseInterfaceAudience;
import org.apache.hadoop.hbase.HConstants;
import org.apache.yetus.audience.InterfaceAudience;
import org.apache.yetus.audience.InterfaceStability;
import org.apache.hadoop.hbase.ipc.PriorityFunction;
import org.apache.hadoop.hbase.ipc.RpcScheduler;
import org.apache.hadoop.hbase.ipc.SimpleRpcScheduler;

/** Constructs a {@link SimpleRpcScheduler}.
 */
@InterfaceAudience.LimitedPrivate({HBaseInterfaceAudience.COPROC, HBaseInterfaceAudience.PHOENIX})
@InterfaceStability.Evolving
public class SimpleRpcSchedulerFactory implements RpcSchedulerFactory {
  //这个方法用于创建一个新的RpcScheduler实例。其中，conf参数是HBase的配置对象，priority参数是一个优先级函数，abortable参数是一个可中止的对象。返回值是一个RpcScheduler实例。
  //RpcSchedulerFactory接口的默认实现类是SimpleRpcSchedulerFactory。SimpleRpcSchedulerFactory实现了create方法，用于创建SimpleRpcScheduler实例。
  // SimpleRpcScheduler是RpcScheduler的默认实现类，它采用FIFO（先进先出）调度算法，对所有请求进行排队处理。
  @Override
  public RpcScheduler create(Configuration conf, PriorityFunction priority, Abortable server) {
    int handlerCount = conf.getInt(HConstants.REGION_SERVER_HANDLER_COUNT,
        HConstants.DEFAULT_REGION_SERVER_HANDLER_COUNT);
    return new SimpleRpcScheduler(
      conf,
      handlerCount,
      conf.getInt(HConstants.REGION_SERVER_HIGH_PRIORITY_HANDLER_COUNT,
        HConstants.DEFAULT_REGION_SERVER_HIGH_PRIORITY_HANDLER_COUNT),
      conf.getInt(HConstants.REGION_SERVER_REPLICATION_HANDLER_COUNT,
          HConstants.DEFAULT_REGION_SERVER_REPLICATION_HANDLER_COUNT),
        conf.getInt(HConstants.MASTER_META_TRANSITION_HANDLER_COUNT,
            HConstants.MASTER__META_TRANSITION_HANDLER_COUNT_DEFAULT),
      priority,
      server,
      HConstants.QOS_THRESHOLD);
  }
}
