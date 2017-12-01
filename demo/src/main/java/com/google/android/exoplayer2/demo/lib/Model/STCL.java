package com.google.android.exoplayer2.demo.lib.Model;

import java.util.ArrayList;

//    STCL : Session sTreaming Change Level

public class STCL extends Streaming {
    String bitrate_from;
    String bitrate_to;


    public STCL() {
    }

    public void update() {
        payload = new ArrayList<String>();
        payload.add(session_id);
        payload.add(bitrate_from);
        payload.add(bitrate_to);
    }

    public String getBitrate_from() {
        return bitrate_from;
    }

    public void setBitrate_from(String bitrate_from) {
        this.bitrate_from = bitrate_from;
    }

    public String getBitrate_to() {
        return bitrate_to;
    }

    public void setBitrate_to(String bitrate_to) {
        this.bitrate_to = bitrate_to;
    }

}
