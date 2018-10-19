package com.cmlteam.hibernate;

/** TODO: move to separate maven module */
public final class Util {

  private Util() {}

  public static String renderDelta(long deltaMillis) {
    float deltaSec = deltaMillis / 1000f;
    int deltaMin = 0;
    int deltaHr = 0;
    int deltaDay = 0;

    if (deltaSec >= 60) {
      deltaMin = (int) (deltaSec / 60);
      deltaSec = deltaSec - (deltaMin * 60);
    }

    if (deltaMin >= 60) {
      deltaHr = deltaMin / 60;
      deltaMin = deltaMin - (deltaHr * 60);
    }

    if (deltaHr >= 24) {
      deltaDay = deltaHr / 24;
      deltaHr = deltaHr - (deltaDay * 24);
    }

    String dayS = deltaDay > 0 ? deltaDay + " d" : null;
    String hrS = deltaHr > 0 ? deltaHr + " h" : null;
    String minS = deltaMin > 0 ? deltaMin + " m" : null;
    String secS = deltaSec > 0 ? deltaSec + " s" : null;
    String secSI = deltaSec > 0 ? ((int) deltaSec) + " s" : null;

    return dayS != null
        ? dayS + " " + hrS
        : deltaHr > 0
            ? hrS + " " + minS
            : deltaMin > 0 ? minS + " " + secSI : deltaSec > 0 ? secS : "0 s";
  }
}
