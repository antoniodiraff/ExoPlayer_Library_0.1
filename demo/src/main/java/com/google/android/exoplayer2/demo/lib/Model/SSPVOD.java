package com.google.android.exoplayer2.demo.lib.Model;


// SSPVOD : Session Start Playback VOD. During the Playback, if the user is offline, the SSPVOD events should be collected and sent as soon as will come back online

import java.util.ArrayList;

public class SSPVOD extends VOD {
    String delay_time_sec;

    public SSPVOD() {
    }


    public void update() {
        payload = new ArrayList<String>();
        payload.add(sessionId);
        payload.add(originalSessionId);
        payload.add(playbackStartTime);
        payload.add(offer_id);
        payload.add(asset_title);
        payload.add(asset_source);
        payload.add(delay_time_sec);
    }

    public String getDelay_time_sec() {
        return delay_time_sec;
    }

    public void setDelay_time_sec(String delay_time_sec) {
        this.delay_time_sec = delay_time_sec;
    }
}
