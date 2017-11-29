package com.google.android.exoplayer2.demo.Model;


// SSPVOD : Session Start Playback VOD. During the Playback, if the user is offline, the SSPVOD events should be collected and sent as soon as will come back online

import java.util.ArrayList;

public class SSPVOD extends VOD {
    String delay_time_sec;

    public SSPVOD() {
    }


    public void update() {
        payload = new ArrayList<String>();
        payload.add(session_id);
        payload.add(original_session_id);
        payload.add(playback_start_time);
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
