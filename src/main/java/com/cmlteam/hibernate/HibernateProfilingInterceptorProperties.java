package com.cmlteam.hibernate;

public class HibernateProfilingInterceptorProperties {
  static final String HIBERNATE_PROFILING_INTERCEPTOR_PROPS = "hibernateProfilingInterceptor";
  static final String HIBERNATE_PROFILING_INTERCEPTOR_PROPS_ENABLED =
      HIBERNATE_PROFILING_INTERCEPTOR_PROPS + ".enabled";

  private boolean showSql;
  private int singleQueryCntOk;

  public boolean isShowSql() {
    return showSql;
  }

  public int getSingleQueryCntOk() {
    return singleQueryCntOk;
  }
}
