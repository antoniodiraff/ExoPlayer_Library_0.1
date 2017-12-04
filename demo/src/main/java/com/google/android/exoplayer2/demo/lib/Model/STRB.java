package com.google.android.exoplayer2.demo.lib.Model;

import java.util.ArrayList;

//    STRB : Session sTreaming ReBuferring


public class STRB extends Streaming {
    String rebufferingStartTime;
    String rebufferingEndTime;

    public STRB() {
    }

    public void update() {
        payload = new ArrayList<String>();
        payload.add(sessionId);
        payload.add(rebufferingStartTime);
        payload.add(rebufferingEndTime);
    }

    public String getRebufferingStartTime() {
        return rebufferingStartTime;
    }

    public void setRebufferingStartTime(String rebufferingStartTime) {
        this.rebufferingStartTime = rebufferingStartTime;
    }

    public String getRebufferingEndTime() {
        return rebufferingEndTime;
    }

    public void setRebufferingEndTime(String rebufferingEndTime) {
        this.rebufferingEndTime = rebufferingEndTime;
    }
}
