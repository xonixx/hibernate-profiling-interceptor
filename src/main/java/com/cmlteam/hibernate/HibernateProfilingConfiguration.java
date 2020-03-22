package com.cmlteam.hibernate;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.cmlteam.hibernate.HibernateProfilingInterceptorProperties.HIBERNATE_PROFILING_INTERCEPTOR_PROPS_ENABLED;

@Configuration
@EnableConfigurationProperties(value = HibernateProfilingInterceptorProperties.class)
@ConditionalOnProperty(value = HIBERNATE_PROFILING_INTERCEPTOR_PROPS_ENABLED)
public class HibernateProfilingConfiguration {

  @Bean
  HibernateProfilingInterceptor hibernateProfilingInterceptor(
      HibernateProfilingInterceptorProperties properties) {
    return new HibernateProfilingInterceptor(
        properties.isShowSql(), properties.getSingleQueryCntOk());
  }

  @Bean
  HibernatePropertiesCustomizer hibernatePropertiesCustomizer(
      HibernateProfilingInterceptor hibernateProfilingInterceptor) {
    return new HibernateProfilingCustomizer(hibernateProfilingInterceptor);
  }
}
