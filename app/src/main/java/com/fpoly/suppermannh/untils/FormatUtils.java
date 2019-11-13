package com.fpoly.suppermannh.untils;

import android.text.format.DateFormat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class   FormatUtils {
    private static final String PATTERN_ESTIMATED_PRICE = "###,###,###";
    private static final String PATTERN_ESTIMATED_DISTANCE = "###,###.#";
    private static final String PATTERN_ESTIMATED_DURATION = "###,###,###";
    private static final String PATTERN_DATE = "HH:mm - dd/MM/yyyy";
    private static final String PATTERN_DAY = "dd";
    private static final String PATTERN_MONTH = "MM";
    private static final String PATTERN_DATE1 = "dd/MM/yyyy";
    private static final String PATTERN_DATE2 = "HH:mm";
    private static final String PATTERN_DATE3 = "HH";


    public static String convertEstimatedPrice(double estimatedPrice) {
        DecimalFormat format = new DecimalFormat(PATTERN_ESTIMATED_PRICE);
        return format.format(estimatedPrice);
    }
    public static String convertEstimatedDate3(Date estimatedPrice) {
        SimpleDateFormat format = new SimpleDateFormat(PATTERN_DATE3);
        return format.format(estimatedPrice);
    }
    public static String convertEstimatedDay(Date estimatedPrice) {
        SimpleDateFormat format = new SimpleDateFormat(PATTERN_DAY);
        return format.format(estimatedPrice);
    }
    public static String convertEstimatedMonth(Date estimatedPrice) {
        SimpleDateFormat format = new SimpleDateFormat(PATTERN_MONTH);
        return format.format(estimatedPrice);
    }

    public static String convertEstimatedDate1(Date estimatedPrice) {
        SimpleDateFormat format = new SimpleDateFormat(PATTERN_DATE1);
        return format.format(estimatedPrice);
    }
    public static String convertEstimatedDate2(Date estimatedPrice) {
        SimpleDateFormat format = new SimpleDateFormat(PATTERN_DATE2);
        return format.format(estimatedPrice);
    }

    public static String convertEstimatedDuration(double estimatedDuration) {
        DecimalFormat format = new DecimalFormat(PATTERN_ESTIMATED_DURATION);
        return format.format(estimatedDuration);
    }

    public static String convertEstimatedDistance(double estimatedDistance) {
        DecimalFormat format = new DecimalFormat(PATTERN_ESTIMATED_DISTANCE);
        return format.format(estimatedDistance);
    }

    public static CharSequence convertDate(Date date) {
        return DateFormat.format(PATTERN_DATE, date);
    }

    public static String convertLocationToString(double latitude, double longitude) {
        return String.format("%s,%s", latitude, longitude);
    }

    /**
     * @param objectId TP1565342407585299300
     * @return TRIP1565342407585299300
     * <p>
     * objectId.substring(0,1) => 1565342407585299300
     */
    public static String convertObjectIdToTripId(String objectId) {
        return String.format("TRIP%s ", objectId.substring(2));
    }
}
