package com.google.android.exoplayer2.demo.lib.Model;

import java.util.ArrayList;

//    STCL : Session sTreaming Change Level

public class STCL extends Streaming {
    String bitrateFrom;
    String bitrateTo;


    public STCL() {
    }

    public void update() {
        payload = new ArrayList<String>();
        payload.add(sessionId);
        payload.add(bitrateFrom);
        payload.add(bitrateTo);
    }

    public String getBitrateFrom() {
        return bitrateFrom;
    }

    public void setBitrateFrom(String bitrateFrom) {
        this.bitrateFrom = bitrateFrom;
    }

    public String getBitrateTo() {
        return bitrateTo;
    }

    public void setBitrateTo(String bitrateTo) {
        this.bitrateTo = bitrateTo;
    }
}
