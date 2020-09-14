package com.miaqoi.redis.redislike.config;

import lombok.Data;
import org.redisson.config.ReadMode;
import org.redisson.config.SslProvider;
import org.redisson.config.SubscriptionMode;
import org.redisson.config.TransportMode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URL;

/**
 * Redisson 配置文件
 *
 * @author miaoqi
 * @date 2020/3/9
 */
@Data
@ConfigurationProperties(prefix = "spring.redisson")
public class RedissonProperties {

    private String mode;

    /**
     * 基础配置
     */
    private String codec;
    private Integer threads;
    private Integer nettyThreads;
    private TransportMode transportMode;
    private Integer lockWatchdogTimeout;
    private Boolean keepPubSubOrder;

    /**
     * 通用配置
     */
    private Integer subscriptionConnectionMinimumIdleSize;
    private Integer subscriptionConnectionPoolSize;
    private SslProvider sslProvider;
    private URL sslTruststore;
    private String sslTruststorePassword;
    private URL sslKeystore;
    private String sslKeystorePassword;
    private Boolean sslEnableEndpointIdentification;
    private String clientName;
    private Integer timeout;
    private Integer retryAttempts;
    private Integer retryInterval;
    private String password;
    private Integer failedAttempts;
    private Integer connectTimeout;
    private Integer reconnectionTimeout;
    private Integer subscriptionsPerConnection;
    private Integer idleConnectionTimeout;



    /**
     * 单机配置
     */
    private String address;
    private Integer connectionMinimumIdleSize;
    private Integer connectionPoolSize;
    private Integer dnsMonitoringInterval;
    private Integer database;

    /**
     * 主从
     */
    private String masterAddress;
    private String[] slaveAddress;
    private ReadMode readMode;
    private SubscriptionMode subscriptionMode;
    private String loadBalancer;
    private Integer slaveConnectionMinimumIdleSize;
    private Integer slaveConnectionPoolSize;
    private Integer masterConnectionMinimumIdleSize;
    private Integer masterConnectionPoolSize;

    /**
     * 哨兵
     */
    private String masterName;
    private String[] sentinelAddresses;

    /**
     * 集群
     */
    private String[] nodeAddresses;
    private Integer scanInterval;


}
