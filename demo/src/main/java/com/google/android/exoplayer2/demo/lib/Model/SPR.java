package com.google.android.exoplayer2.demo.lib.Model;

import java.util.ArrayList;

//SPR : Session Playback Restart

public class SPR extends Download {
    public String getRestart_time() {
        return restart_time;
    }

    public void setRestart_time(String restart_time) {
        this.restart_time = restart_time;
        this.update();
    }

    String restart_time;

    public SPR() {

    }

    public void update() {
        payload = new ArrayList<String>();
        payload.add(session_id);
        payload.add(restart_time);
    }
}