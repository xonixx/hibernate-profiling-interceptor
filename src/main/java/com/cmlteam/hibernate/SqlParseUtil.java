package com.cmlteam.hibernate;

import java.util.regex.Pattern;

class SqlParseUtil {

  private static Pattern selectPattern = Pattern.compile("select ", Pattern.CASE_INSENSITIVE);
  private static Pattern insertPattern = Pattern.compile("insert ", Pattern.CASE_INSENSITIVE);
  private static Pattern updatePattern = Pattern.compile("update ", Pattern.CASE_INSENSITIVE);
  private static Pattern deletePattern = Pattern.compile("delete ", Pattern.CASE_INSENSITIVE);

  static SqlQueryType determineSqlQueryType(String sql) {
    if (selectPattern.matcher(sql).find()) return SqlQueryType.SELECT;
    else if (insertPattern.matcher(sql).find()) return SqlQueryType.INSERT;
    else if (updatePattern.matcher(sql).find()) return SqlQueryType.UPDATE;
    else if (deletePattern.matcher(sql).find()) return SqlQueryType.DELETE;

    return null;
  }

  static String getFromTableName(String sql) {
    int idx1 = sql.lastIndexOf("FROM ");
    int idx2 = sql.lastIndexOf("from ");
    int idx = Math.max(idx1, idx2);
    if (idx < 0) return null;
    int space = sql.indexOf(" ", idx + 5);
    return sql.substring(idx + 5, space);
  }

  private SqlParseUtil() {}
}
