package com.ecwid.ip;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPv4ValidatorRegex {

    private static final String IPV4_PATTERN =
            "^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$";

    private static final Pattern pattern = Pattern.compile(IPV4_PATTERN);

    /**
     * method validates string by IPv4 regexp mask stolen from the Internet
     * nothing special, validation can be removed in case good source data
     * performance will be much better if validation disabled
     * @param ip IP address in String format
     * @return true in case of valid IP address or false in case invalid address
     */
    public static boolean isValid(final String ip) {
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

}
