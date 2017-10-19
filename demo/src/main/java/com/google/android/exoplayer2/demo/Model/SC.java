package com.google.android.exoplayer2.demo.Model;

import java.util.ArrayList;

//        SC : Session Close


public class SC extends Close {
    String closing_time;


    public SC() {


    }

    public void updateSCPayload() {
        payload = new ArrayList<String>();
        payload.add(session_id);
        payload.add(closing_time);

    }

    public String getClosing_time() {
        return closing_time;
    }

    public void setClosing_time(String closing_time) {
        this.closing_time = closing_time;
    }


}
