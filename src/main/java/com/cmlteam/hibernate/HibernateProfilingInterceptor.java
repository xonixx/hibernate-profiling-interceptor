package com.cmlteam.hibernate;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.EmptyInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HibernateProfilingInterceptor extends EmptyInterceptor {
  private static final Logger log = LoggerFactory.getLogger(HibernateProfilingInterceptor.class);

  private static final int TOTAL_MAX_OK = 20;
  private static final int MAX_DURATION_MILLIS = 2000;

  private ThreadLocal<MetricsHolder> queryCntTL = new ThreadLocal<>();
  private final boolean showSql;
  private final int singleQueryCntOk;

  @Autowired
  public HibernateProfilingInterceptor(
      @Value("${hibernateProfilingInterceptor.showSql}") boolean showSql,
      @Value("${hibernateProfilingInterceptor.singleQueryCntOk}") int singleQueryCntOk) {
    this.showSql = showSql;
    this.singleQueryCntOk = singleQueryCntOk;
  }

  public HibernateProfilingInterceptor() {
    this(false, 10);
  }

  private static class MetricsHolder {
    int selects = 0;
    int inserts = 0;
    int updates = 0;
    int deletes = 0;
    int total = 0;
    long start = System.currentTimeMillis();
    Map<String, Integer> map = new HashMap<>();
  }

  public void reset() {
    queryCntTL.set(new MetricsHolder());
  }

  @Override
  public String onPrepareStatement(String sql) {
    MetricsHolder holder = queryCntTL.get();
    if (holder != null) {
      holder.total++;

      SqlQueryType sqlQueryType = SqlParseUtil.determineSqlQueryType(sql);

      if (sqlQueryType == SqlQueryType.SELECT) holder.selects++;
      else if (sqlQueryType == SqlQueryType.INSERT) holder.inserts++;
      else if (sqlQueryType == SqlQueryType.UPDATE) holder.updates++;
      else if (sqlQueryType == SqlQueryType.DELETE) holder.deletes++;

      Integer cnt = holder.map.get(sql);
      if (cnt == null) cnt = 0;

      holder.map.put(sql, cnt + 1);
    }

    return sql;
  }

  public void report(String action, boolean full) {
    MetricsHolder metricsHolder = queryCntTL.get();
    List<Map.Entry<String, Integer>> sqlCntPairs = new ArrayList<>();

    boolean tooManyTotal = metricsHolder.total >= TOTAL_MAX_OK;

    for (Map.Entry<String, Integer> entry : metricsHolder.map.entrySet()) {
      Integer cnt = entry.getValue();
      if (cnt >= singleQueryCntOk || full) {
        sqlCntPairs.add(entry);
      }
    }

    long duration = System.currentTimeMillis() - metricsHolder.start;

    if (full || tooManyTotal || !sqlCntPairs.isEmpty() || duration >= MAX_DURATION_MILLIS) {
      sqlCntPairs.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

      StringBuilder sb =
          new StringBuilder()
              .append("SQLPERF [")
              .append(Util.renderDelta(duration))
              .append("] (")
              .append(action)
              .append("): T=")
              .append(metricsHolder.total)
              .append(", S=")
              .append(metricsHolder.selects)
              .append(", I=")
              .append(metricsHolder.inserts)
              .append(", U=")
              .append(metricsHolder.updates)
              .append(", D=")
              .append(metricsHolder.deletes);

      for (Map.Entry<String, Integer> entry : sqlCntPairs) {
        Integer cnt = entry.getValue();
        String sql = entry.getKey();

        sb.append('\n')
            .append(StringUtils.rightPad(cnt.toString(), 5))
            .append('(')
            .append(StringUtils.rightPad(SqlParseUtil.getFromTableName(sql) + ')', 30));

        if (showSql) sb.append(sql);
      }

      log.info(sb.toString());
    }
  }
}
