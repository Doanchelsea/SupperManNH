package com.fpoly.suppermannh.untils;

/**
 * doannd ==> ok
 * <p>
 * <p>
 * check phone number has valid ?
 */
public class PhoneUtils {

    /*
     * "098", "097", "096", "086", "032", "033", "034", "035", "036", "037", "038", "039", //   Viettel
     * "091", "094", "088", "081", "082", "083", "084", "085", // VinaPhone
     * "089", "090", "093", "070", "079", "077", "076", "078", // MobiPhone
     * "092", "056", "058", // Vietnammobile
     * "099", "059" // Gmobile
     */

    /**
     * example: 0987654321
     * number range: [0,1,2,3,4,5,6,7,8,9]
     * length: 10 (rule: 0 the first)
     */
    private static final String PATTERN_PHONE_NUMBER_NORMAL = "^(098|097|096|086|032|033|034|035|036|037|038|039" +
            "|091|094|088|081|082|083|084|085" +
            "|089|090|093|070|079|077|076|078" +
            "|092|056|058" +
            "|099|059)[0-9]{7}$";

    /**
     * example: 987654321
     * number range: [0,1,2,3,4,5,6,7,8,9]
     * length: 9 (not 0 the first)
     */
    private static final String PATTERN_PHONE_NUMBER_COUNTRY_CODE = "^(98|97|96|86|32|33|34|35|36|37|38|39" +
            "|91|94|88|81|82|83|84|85" +
            "|89|90|93|70|79|77|76|78" +
            "|92|56|58" +
            "|99|59)[0-9]{7}$";

    public static boolean isPhoneNumberNormal(String phoneNumber) {
        return phoneNumber.matches(PATTERN_PHONE_NUMBER_NORMAL);
    }

    public static boolean isPhoneNumberWithCountryCode(String phoneNumber) {
        return phoneNumber.matches(PATTERN_PHONE_NUMBER_COUNTRY_CODE);
    }

}
