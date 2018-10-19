package com.cmlteam.hibernate;

import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.cmlteam.hibernate.HibernateProfilingInterceptorProperties.HIBERNATE_PROFILING_INTERCEPTOR_PROPS;

@ConfigurationProperties(prefix = HIBERNATE_PROFILING_INTERCEPTOR_PROPS)
public class HibernateProfilingInterceptorProperties {
  public static final String HIBERNATE_PROFILING_INTERCEPTOR_PROPS =
      "hibernateProfilingInterceptor";
  private boolean showSql;
  private int singleQueryCntOk;

  public boolean isShowSql() {
    return showSql;
  }

  public int getSingleQueryCntOk() {
    return singleQueryCntOk;
  }
}
