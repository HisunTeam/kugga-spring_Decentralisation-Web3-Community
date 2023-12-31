package com.hisun.kugga.framework.jackson.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.hisun.kugga.framework.common.util.json.JsonUtils;
import com.hisun.kugga.framework.jackson.core.databind.LocalDateTimeDeserializer;
import com.hisun.kugga.framework.jackson.core.databind.LocalDateTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Slf4j
@Configuration
public class KuggaJacksonAutoConfiguration {

    @Bean
    public BeanPostProcessor objectMapperBeanPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (!(bean instanceof ObjectMapper)) {
                    return bean;
                }
                ObjectMapper objectMapper = (ObjectMapper) bean;
                SimpleModule simpleModule = new SimpleModule();
                /*
                 * 1. 新增Long类型序列化规则，数值超过2^53-1，在JS会出现精度丢失问题，因此Long自动序列化为字符串类型
                 * 2. 新增LocalDateTime序列化、反序列化规则
                 */
                simpleModule
//                .addSerializer(Long.class, ToStringSerializer.instance)
//                    .addSerializer(Long.TYPE, ToStringSerializer.instance)
                        .addSerializer(LocalDateTime.class, LocalDateTimeSerializer.INSTANCE)
                        .addDeserializer(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE);

                // @see https://stackoverflow.com/questions/27952472/serialize-deserialize-java-8-java-time-with-jackson-json-mapper
                // new JavaTimeModule() 是不是就够了？
                objectMapper.registerModules(simpleModule);

                JsonUtils.init(objectMapper);
                log.info("初始化 jackson 自动配置");
                return bean;
            }
        };
    }

}
