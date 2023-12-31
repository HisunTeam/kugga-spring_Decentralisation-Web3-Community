package com.hisun.kugga.framework.socketio;

import com.corundumstudio.socketio.store.HazelcastStoreFactory;
import com.corundumstudio.socketio.store.StoreFactory;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore({SocketioServerAutoConfiguration.class})
@ConditionalOnClass({HazelcastClient.class})
@ConditionalOnProperty(prefix = SocketioHazelcastProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({SocketioHazelcastProperties.class})
public class SocketioHazelcastConfiguration {

    protected static Logger LOG = LoggerFactory.getLogger(SocketioHazelcastConfiguration.class);

    @Autowired
    private SocketioHazelcastProperties config;

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean
    public HazelcastInstance hazelcastClient() {
        return HazelcastClient.newHazelcastClient(config);
    }

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean
    public HazelcastInstance hazelcastPub() {
        return HazelcastClient.newHazelcastClient(config);
    }

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean
    public HazelcastInstance hazelcastSub() {
        return HazelcastClient.newHazelcastClient(config);
    }

    @Bean
    public StoreFactory clientStoreFactory(HazelcastInstance hazelcastClient, HazelcastInstance hazelcastPub, HazelcastInstance hazelcastSub) {
        return new HazelcastStoreFactory(hazelcastClient, hazelcastPub, hazelcastSub);
    }

}
