package com.google.android.exoplayer2.demo.lib.Model;

import java.util.ArrayList;


//        SDC : Session Download Completed  (reached download end)


public class SDC extends Close {
    String completed_time;

    public String getCompleted_time() {
        return completed_time;
    }

    public void setCompleted_time(String completed_time) {
        this.completed_time = completed_time;
    }

    public SDC() {

    }

    public void update() {
        payload = new ArrayList<String>();
        payload.add(sessionId);
        payload.add(completed_time);
    }

}
