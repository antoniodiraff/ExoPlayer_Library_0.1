package com.google.android.exoplayer2.demo.Model;

import com.google.android.exoplayer2.demo.Observer;

import java.util.ArrayList;

//SDP : Session Download Pause

public class SDP extends Download {
    String pause_time;
    String pause_cause;
    String download_perc;

    public SDP() {

    }

    public void updateSDPPayload() {
        payload = new ArrayList<String>();
        payload.add(session_id);
        payload.add(pause_time);
        payload.add(pause_cause);
        payload.add(download_perc);
    }

    public String getPause_time() {
        return pause_time;
    }

    public void setPause_time(String pause_time) {
        this.pause_time = pause_time;
    }

    public String getPause_cause() {
        return pause_cause;
    }

    public void setPause_cause(String pause_cause) {
        this.pause_cause = pause_cause;
    }

    public String getDownload_perc() {
        return download_perc;
    }

    public void setDownload_perc(String download_perc) {
        this.download_perc = download_perc;
    }
}
