package com.cmlteam.hibernate;

import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.cmlteam.hibernate.HibernateProfilingInterceptorProperties.HIBERNATE_PROFILING_INTERCEPTOR_PROPS;

@ConfigurationProperties(prefix = HIBERNATE_PROFILING_INTERCEPTOR_PROPS)
public class HibernateProfilingInterceptorProperties {
  static final String HIBERNATE_PROFILING_INTERCEPTOR_PROPS = "hibernate-profiling-interceptor";
  static final String HIBERNATE_PROFILING_INTERCEPTOR_PROPS_ENABLED =
      HIBERNATE_PROFILING_INTERCEPTOR_PROPS + ".enabled";

  private boolean showSql;
  private int singleQueryCntOk;

  public void setShowSql(boolean showSql) {
    this.showSql = showSql;
  }

  public void setSingleQueryCntOk(int singleQueryCntOk) {
    this.singleQueryCntOk = singleQueryCntOk;
  }

  public boolean isShowSql() {
    return showSql;
  }

  public int getSingleQueryCntOk() {
    return singleQueryCntOk;
  }
}
