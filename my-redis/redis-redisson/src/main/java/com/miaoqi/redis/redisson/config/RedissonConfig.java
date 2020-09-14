package com.miaoqi.redis.redisson.config;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.MasterSlaveServersConfig;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.redisson.connection.balancer.LoadBalancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;

/**
 * Redisson 配置类
 *
 * @author miaoqi
 * @date 2020/3/9
 */
@Configuration
@EnableConfigurationProperties(RedissonProperties.class)
@ConditionalOnClass(Config.class)
public class RedissonConfig {

    @Autowired
    private RedissonProperties redissonProperties;


    /**
     * 单机模式自动装配
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "spring.redisson.mode", havingValue = "single")
    public RedissonClient redissonSingle() throws Exception {
        Config config = new Config();
        Codec codec=(Codec) ClassUtils.forName(this.redissonProperties.getCodec(),ClassUtils.getDefaultClassLoader()).newInstance();
        config.setCodec(codec)
                .setThreads(redissonProperties.getThreads())
                .setNettyThreads(redissonProperties.getNettyThreads())
                .setTransportMode(redissonProperties.getTransportMode())
                .setLockWatchdogTimeout(redissonProperties.getLockWatchdogTimeout())
                .setKeepPubSubOrder(redissonProperties.getKeepPubSubOrder());

        SingleServerConfig singleServerConfig = config.useSingleServer()
                .setSubscriptionConnectionMinimumIdleSize(redissonProperties.getSubscriptionConnectionMinimumIdleSize())
                .setSubscriptionConnectionPoolSize(redissonProperties.getSubscriptionConnectionPoolSize())
                .setSslProvider(redissonProperties.getSslProvider())
                .setSslEnableEndpointIdentification(redissonProperties.getSslEnableEndpointIdentification())
                .setTimeout(redissonProperties.getTimeout())
                .setRetryAttempts(redissonProperties.getRetryAttempts())
                .setRetryInterval(redissonProperties.getRetryInterval())
                .setFailedAttempts(redissonProperties.getFailedAttempts())
                .setConnectTimeout(redissonProperties.getConnectTimeout())
                .setReconnectionTimeout(redissonProperties.getReconnectionTimeout())
                .setSubscriptionsPerConnection(redissonProperties.getSubscriptionsPerConnection())
                .setIdleConnectionTimeout(redissonProperties.getIdleConnectionTimeout())
                .setAddress(redissonProperties.getAddress())
                .setConnectionMinimumIdleSize(redissonProperties.getConnectionMinimumIdleSize())
                .setConnectionPoolSize(redissonProperties.getConnectionPoolSize())
                .setDnsMonitoringInterval(redissonProperties.getDnsMonitoringInterval())
                .setDatabase(redissonProperties.getDatabase());

        if (StringUtils.isNotBlank(this.redissonProperties.getClientName())) {
            singleServerConfig.setClientName(this.redissonProperties.getClientName());
        }
        if (StringUtils.isNotBlank(this.redissonProperties.getSslKeystorePassword())) {
            singleServerConfig.setSslKeystorePassword(this.redissonProperties.getSslKeystorePassword());
        }
        if (this.redissonProperties.getSslKeystore() != null) {
            singleServerConfig.setSslKeystore(this.redissonProperties.getSslKeystore());
        }
        if (StringUtils.isNotBlank(this.redissonProperties.getSslTruststorePassword())) {
            singleServerConfig.setSslTruststorePassword(this.redissonProperties.getSslTruststorePassword());
        }
        if (this.redissonProperties.getSslTruststore() != null) {
            singleServerConfig.setSslTruststore(this.redissonProperties.getSslTruststore());
        }
        if (StringUtils.isNotBlank(this.redissonProperties.getPassword())) {
            singleServerConfig.setPassword(this.redissonProperties.getPassword());
        }
        return Redisson.create(config);
    }

    @Bean
    @ConditionalOnProperty(name = "spring.redisson.mode", havingValue = "masterslave")
    public RedissonClient redissonMasterSlave() throws Exception {
        Config config = new Config();
        Codec codec=(Codec) ClassUtils.forName(this.redissonProperties.getCodec(),ClassUtils.getDefaultClassLoader()).newInstance();
        LoadBalancer loadBalancer = (LoadBalancer) ClassUtils.forName(this.redissonProperties.getLoadBalancer(),ClassUtils.getDefaultClassLoader()).newInstance();
        config.setCodec(codec)
                .setThreads(redissonProperties.getThreads())
                .setNettyThreads(redissonProperties.getNettyThreads())
                .setTransportMode(redissonProperties.getTransportMode())
                .setLockWatchdogTimeout(redissonProperties.getLockWatchdogTimeout())
                .setKeepPubSubOrder(redissonProperties.getKeepPubSubOrder());
        MasterSlaveServersConfig masterSlaveServersConfig = config.useMasterSlaveServers()
                .setSubscriptionConnectionMinimumIdleSize(redissonProperties.getSubscriptionConnectionMinimumIdleSize())
                .setSubscriptionConnectionPoolSize(redissonProperties.getSubscriptionConnectionPoolSize())
                .setSslProvider(redissonProperties.getSslProvider())
                .setSslEnableEndpointIdentification(redissonProperties.getSslEnableEndpointIdentification())
                .setTimeout(redissonProperties.getTimeout())
                .setRetryAttempts(redissonProperties.getRetryAttempts())
                .setRetryInterval(redissonProperties.getRetryInterval())
                .setFailedAttempts(redissonProperties.getFailedAttempts())
                .setConnectTimeout(redissonProperties.getConnectTimeout())
                .setReconnectionTimeout(redissonProperties.getReconnectionTimeout())
                .setSubscriptionsPerConnection(redissonProperties.getSubscriptionsPerConnection())
                .setIdleConnectionTimeout(redissonProperties.getIdleConnectionTimeout())
                .setDnsMonitoringInterval(redissonProperties.getDnsMonitoringInterval())
                .setMasterAddress(redissonProperties.getMasterAddress())
                .addSlaveAddress(redissonProperties.getSlaveAddress())
                .setReadMode(redissonProperties.getReadMode())
                .setSubscriptionMode(redissonProperties.getSubscriptionMode())
                .setLoadBalancer(loadBalancer)
                .setSlaveConnectionMinimumIdleSize(redissonProperties.getSlaveConnectionMinimumIdleSize())
                .setSlaveConnectionPoolSize(redissonProperties.getSlaveConnectionPoolSize())
                .setMasterConnectionMinimumIdleSize(redissonProperties.getMasterConnectionMinimumIdleSize())
                .setMasterConnectionPoolSize(redissonProperties.getMasterConnectionPoolSize())
                .setDatabase(redissonProperties.getDatabase());


        if (StringUtils.isNotBlank(this.redissonProperties.getClientName())) {
            masterSlaveServersConfig.setClientName(this.redissonProperties.getClientName());
        }
        if (StringUtils.isNotBlank(this.redissonProperties.getSslKeystorePassword())) {
            masterSlaveServersConfig.setSslKeystorePassword(this.redissonProperties.getSslKeystorePassword());
        }
        if (this.redissonProperties.getSslKeystore() != null) {
            masterSlaveServersConfig.setSslKeystore(this.redissonProperties.getSslKeystore());
        }
        if (StringUtils.isNotBlank(this.redissonProperties.getSslTruststorePassword())) {
            masterSlaveServersConfig.setSslTruststorePassword(this.redissonProperties.getSslTruststorePassword());
        }
        if (this.redissonProperties.getSslTruststore() != null) {
            masterSlaveServersConfig.setSslTruststore(this.redissonProperties.getSslTruststore());
        }
        if (StringUtils.isNotBlank(this.redissonProperties.getPassword())) {
            masterSlaveServersConfig.setPassword(this.redissonProperties.getPassword());
        }
        return Redisson.create(config);
    }

    /**
     * 哨兵模式自动装配
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "spring.redisson.mode", havingValue = "sentinel")
    public RedissonClient redissonSentinel() throws Exception {
        Config config = new Config();
        Codec codec=(Codec) ClassUtils.forName(this.redissonProperties.getCodec(),ClassUtils.getDefaultClassLoader()).newInstance();
        LoadBalancer loadBalancer = (LoadBalancer) ClassUtils.forName(this.redissonProperties.getLoadBalancer(),ClassUtils.getDefaultClassLoader()).newInstance();
        config.setCodec(codec)
                .setThreads(redissonProperties.getThreads())
                .setNettyThreads(redissonProperties.getNettyThreads())
                .setTransportMode(redissonProperties.getTransportMode())
                .setLockWatchdogTimeout(redissonProperties.getLockWatchdogTimeout())
                .setKeepPubSubOrder(redissonProperties.getKeepPubSubOrder());
        SentinelServersConfig sentinelServersConfig = config.useSentinelServers()
                .setSubscriptionConnectionMinimumIdleSize(redissonProperties.getSubscriptionConnectionMinimumIdleSize())
                .setSubscriptionConnectionPoolSize(redissonProperties.getSubscriptionConnectionPoolSize())
                .setSslProvider(redissonProperties.getSslProvider())
                .setSslEnableEndpointIdentification(redissonProperties.getSslEnableEndpointIdentification())
                .setTimeout(redissonProperties.getTimeout())
                .setRetryAttempts(redissonProperties.getRetryAttempts())
                .setRetryInterval(redissonProperties.getRetryInterval())
                .setFailedAttempts(redissonProperties.getFailedAttempts())
                .setConnectTimeout(redissonProperties.getConnectTimeout())
                .setReconnectionTimeout(redissonProperties.getReconnectionTimeout())
                .setSubscriptionsPerConnection(redissonProperties.getSubscriptionsPerConnection())
                .setIdleConnectionTimeout(redissonProperties.getIdleConnectionTimeout())
                .setDnsMonitoringInterval(redissonProperties.getDnsMonitoringInterval())
                .setMasterName(redissonProperties.getMasterName())
                .addSentinelAddress(redissonProperties.getSentinelAddresses())
                .setReadMode(redissonProperties.getReadMode())
                .setSubscriptionMode(redissonProperties.getSubscriptionMode())
                .setLoadBalancer(loadBalancer)
                .setSlaveConnectionMinimumIdleSize(redissonProperties.getSlaveConnectionMinimumIdleSize())
                .setSlaveConnectionPoolSize(redissonProperties.getSlaveConnectionPoolSize())
                .setMasterConnectionMinimumIdleSize(redissonProperties.getMasterConnectionMinimumIdleSize())
                .setMasterConnectionPoolSize(redissonProperties.getMasterConnectionPoolSize())
                .setDatabase(redissonProperties.getDatabase());

        if (StringUtils.isNotBlank(this.redissonProperties.getClientName())) {
            sentinelServersConfig.setClientName(this.redissonProperties.getClientName());
        }
        if (StringUtils.isNotBlank(this.redissonProperties.getSslKeystorePassword())) {
            sentinelServersConfig.setSslKeystorePassword(this.redissonProperties.getSslKeystorePassword());
        }
        if (this.redissonProperties.getSslKeystore() != null) {
            sentinelServersConfig.setSslKeystore(this.redissonProperties.getSslKeystore());
        }
        if (StringUtils.isNotBlank(this.redissonProperties.getSslTruststorePassword())) {
            sentinelServersConfig.setSslTruststorePassword(this.redissonProperties.getSslTruststorePassword());
        }
        if (this.redissonProperties.getSslTruststore() != null) {
            sentinelServersConfig.setSslTruststore(this.redissonProperties.getSslTruststore());
        }
        if (StringUtils.isNotBlank(this.redissonProperties.getPassword())) {
            sentinelServersConfig.setPassword(this.redissonProperties.getPassword());
        }
        return Redisson.create(config);
    }

    @Bean
    @ConditionalOnProperty(name = "spring.redisson.mode", havingValue = "cluster")
    public RedissonClient redissonCluster() throws Exception {
        Config config = new Config();
        Codec codec=(Codec) ClassUtils.forName(this.redissonProperties.getCodec(),ClassUtils.getDefaultClassLoader()).newInstance();
        LoadBalancer loadBalancer = (LoadBalancer) ClassUtils.forName(this.redissonProperties.getLoadBalancer(),ClassUtils.getDefaultClassLoader()).newInstance();
        config.setCodec(codec)
                .setThreads(redissonProperties.getThreads())
                .setNettyThreads(redissonProperties.getNettyThreads())
                .setTransportMode(redissonProperties.getTransportMode())
                .setLockWatchdogTimeout(redissonProperties.getLockWatchdogTimeout())
                .setKeepPubSubOrder(redissonProperties.getKeepPubSubOrder());
        ClusterServersConfig clusterServersConfig = config.useClusterServers()
                .setSubscriptionConnectionMinimumIdleSize(redissonProperties.getSubscriptionConnectionMinimumIdleSize())
                .setSubscriptionConnectionPoolSize(redissonProperties.getSubscriptionConnectionPoolSize())
                .setSslProvider(redissonProperties.getSslProvider())
                .setSslEnableEndpointIdentification(redissonProperties.getSslEnableEndpointIdentification())
                .setTimeout(redissonProperties.getTimeout())
                .setRetryAttempts(redissonProperties.getRetryAttempts())
                .setRetryInterval(redissonProperties.getRetryInterval())
                .setFailedAttempts(redissonProperties.getFailedAttempts())
                .setConnectTimeout(redissonProperties.getConnectTimeout())
                .setReconnectionTimeout(redissonProperties.getReconnectionTimeout())
                .setSubscriptionsPerConnection(redissonProperties.getSubscriptionsPerConnection())
                .setIdleConnectionTimeout(redissonProperties.getIdleConnectionTimeout())
                .addNodeAddress(redissonProperties.getNodeAddresses())
                .setScanInterval(redissonProperties.getScanInterval())
                .setReadMode(redissonProperties.getReadMode())
                .setSubscriptionMode(redissonProperties.getSubscriptionMode())
                .setLoadBalancer(loadBalancer)
                .setSlaveConnectionMinimumIdleSize(redissonProperties.getSlaveConnectionMinimumIdleSize())
                .setSlaveConnectionPoolSize(redissonProperties.getSlaveConnectionPoolSize())
                .setMasterConnectionMinimumIdleSize(redissonProperties.getMasterConnectionMinimumIdleSize())
                .setMasterConnectionPoolSize(redissonProperties.getMasterConnectionPoolSize());


        if (StringUtils.isNotBlank(this.redissonProperties.getClientName())) {
            clusterServersConfig.setClientName(this.redissonProperties.getClientName());
        }
        if (StringUtils.isNotBlank(this.redissonProperties.getSslKeystorePassword())) {
            clusterServersConfig.setSslKeystorePassword(this.redissonProperties.getSslKeystorePassword());
        }
        if (this.redissonProperties.getSslKeystore() != null) {
            clusterServersConfig.setSslKeystore(this.redissonProperties.getSslKeystore());
        }
        if (StringUtils.isNotBlank(this.redissonProperties.getSslTruststorePassword())) {
            clusterServersConfig.setSslTruststorePassword(this.redissonProperties.getSslTruststorePassword());
        }
        if (this.redissonProperties.getSslTruststore() != null) {
            clusterServersConfig.setSslTruststore(this.redissonProperties.getSslTruststore());
        }
        if (StringUtils.isNotBlank(this.redissonProperties.getPassword())) {
            clusterServersConfig.setPassword(this.redissonProperties.getPassword());
        }
        return Redisson.create(config);
    }

}
