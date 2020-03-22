package com.cmlteam.hibernate;

import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;

import java.util.Map;

public class HibernateProfilingCustomizer implements HibernatePropertiesCustomizer {
  private final HibernateProfilingInterceptor hibernateProfilingInterceptor;

  public HibernateProfilingCustomizer(HibernateProfilingInterceptor hibernateProfilingInterceptor) {
    this.hibernateProfilingInterceptor = hibernateProfilingInterceptor;
  }

  @Override
  public void customize(Map<String, Object> vendorProperties) {
    vendorProperties.put("hibernate.session_factory.interceptor", hibernateProfilingInterceptor);
  }
}
