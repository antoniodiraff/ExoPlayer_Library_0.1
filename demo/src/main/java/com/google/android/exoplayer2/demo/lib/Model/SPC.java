package com.google.android.exoplayer2.demo.lib.Model;

import java.util.ArrayList;


//        SPC : Session Playback Close  (playback finished)


public class SPC extends Close {
    String closing_time;
    String viewving_perc;

    public SPC() {
    }

    public String getViewving_perc() {
        return viewving_perc;
    }

    public void setViewving_perc(String viewving_perc) {
        this.viewving_perc = viewving_perc;
    }

    public String getClosing_time() {

        return closing_time;
    }

    public void setClosing_time(String closing_time) {
        this.closing_time = closing_time;
    }

    public void update() {
        payload = new ArrayList<String>();
        payload.add(sessionId);
        payload.add(closing_time);
        payload.add(viewving_perc);
    }
}
