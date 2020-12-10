package com.ecwid.ip;

import java.util.BitSet;

public class AllIPv4Flags {
    private BitSet hghBitSet;
    private BitSet lowBitSet;

    public AllIPv4Flags(){
        //There are 4 294 967 296 IPv4 addresses
        //We'll keep flag exists\not exists for every address
        //In our case we'll use 512M RAM for all flags
        //But we need two BitSets as max size of BitSet is 0x7FFFFFFF
        hghBitSet = new BitSet(0x7FFFFFFF);
        lowBitSet = new BitSet(0x7FFFFFFF);
    }

    public void markIP(String ip) {
        byte i = 0;
        long value = 0;
        for (String s: ip.split("\\.")) {
            value += Integer.parseInt(s) << (24 - (8 * i));
            i++;
        }
        setBit(value);
    }

    private void setBit(long index) {
        if (index < 0) { //i.e. greater 0x7FFFFFFF
            hghBitSet.set((int) (index & 0x7FFFFFFF));
        } else {
            lowBitSet.set((int) index);
        }
    }

    public long getUniqueCount () {
        long result = 0;

        for (int i = 0; i < 0x7FFFFFFF; i++ ) {
            if (hghBitSet.get(i)) {
                result++;
            }
            if (lowBitSet.get(i)) {
                result++;
            }
        }

        return result;
    }

}
