package com.google.android.exoplayer2.demo.lib.Model;

import java.util.ArrayList;

// SDD : Session Download Delete (download removed)

public class SDD extends Download {
    String delete_time;

    public String getDelete_time() {
        return delete_time;
    }

    public void setDelete_time(String delete_time) {
        this.delete_time = delete_time;
    }

    public SDD() {

    }

    public void update() {
        payload = new ArrayList<String>();
        payload.add(sessionId);
        payload.add(delete_time);
    }

}
