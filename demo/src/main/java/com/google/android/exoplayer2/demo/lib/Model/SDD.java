package com.google.android.exoplayer2.demo.lib.Model;

import java.util.ArrayList;

// SDD : Session Download Delete (download removed)

public class SDD extends Download {
    String deleteTime;

    public SDD() {

    }

    public void update() {
        payload = new ArrayList<String>();
        payload.add(sessionId);
        payload.add(deleteTime);
    }

    public String getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(String deleteTime) {
        this.deleteTime = deleteTime;
    }
}
