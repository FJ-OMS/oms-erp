package org.skyer.tags.infra.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 日期工具
 *
 * @author bojiangzhou 2018/07/31
 */
public class Dates {

    private Dates() {}

    public static long getSecondsFromNowToDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return -1;
        }
        long diff = (dateTime.toInstant(ZoneOffset.UTC).toEpochMilli()
                        - LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()) / 1000;
        return diff < 0 ? -1 : diff;
    }

    public static boolean isBetweenOrEqual(LocalDateTime date, LocalDateTime startDate, LocalDateTime endDate) {
        return date.isAfter(startDate) && date.isBefore(endDate) || date.isEqual(startDate) || date.isEqual(endDate);
    }

    public static boolean isAfterOrEqual(LocalDateTime date, LocalDateTime another) {
        return date.isAfter(another) || date.isEqual(another);
    }

}
