package com.ecwid.ip;

enum CmpResult {GREATER, EQUAL, LESS}

public class IPNode {
    int value = 0; //4 byte variable chosen to save memory, see details in compareTo() method
    IPNode left = null;
    IPNode right = null;

    IPNode(String ip) {
        //just constructor, convert string IP to 4-byte integer
        byte i = 0;
        for (String s: ip.split("\\.")) {
            value += Integer.parseInt(s) << (24 - (8 * i));
            i++;
        }
    }

    /**
     * this method can be easier and quicker in case long value chosen
     * but in case of long value for 1M IP addresses we'll get +4Mb memory consumption
     * in case of 1G IP addresses we'll get +4Gb memory consumption
     * @param ipValue IP in 4-byte format
     */
    CmpResult compareTo(int ipValue) {
        if (this.value < 0 && ipValue >= 0) {
            return CmpResult.GREATER;
        } else if (this.value >= 0 && ipValue < 0) {
            return CmpResult.LESS;
        } else if ((this.value&0x7FFFFFFF) > (ipValue&0x7FFFFFFF)) {
            return CmpResult.GREATER;
        } else if ((this.value&0x7FFFFFFF) < (ipValue&0x7FFFFFFF)) {
            return CmpResult.LESS;
        } else {
            return CmpResult.EQUAL;
        }
    }

}
