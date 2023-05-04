package com.ebaykorea.payback.util;

public class PaybackSavedYN {
    public static String toYN(final String pointStatus) {
        return pointStatus.equals("SS") ? "Y" : "N";
    }
}
