package com.google.android.exoplayer2.demo.lib.Model;

import java.util.ArrayList;

//    STRB : Session sTreaming ReBuferring


public class STRB extends Streaming {
    String rebuffering_start_time;
    String rebuffering_end_time;

    public STRB() {
    }

    public void update() {
        payload = new ArrayList<String>();
        payload.add(session_id);
        payload.add(rebuffering_start_time);
        payload.add(rebuffering_end_time);
    }

    public String getRebuffering_start_time() {
        return rebuffering_start_time;
    }

    public void setRebuffering_start_time(String rebuffering_start_time) {
        this.rebuffering_start_time = rebuffering_start_time;
    }

    public String getRebuffering_end_time() {
        return rebuffering_end_time;
    }

    public void setRebuffering_end_time(String rebuffering_end_time) {
        this.rebuffering_end_time = rebuffering_end_time;
    }
}
