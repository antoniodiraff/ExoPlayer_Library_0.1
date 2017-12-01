package com.google.android.exoplayer2.demo.lib.Model;

import java.util.ArrayList;


// SSRCH: Session Start Restart CHannel

public class SSRCH extends LiveCH {
    String restart_sec;

    public SSRCH() {
    }

    public void update() {
        payload = new ArrayList<String>();
        payload.add(session_id);
        payload.add(original_session_id);
        payload.add(start_time);
        payload.add(drm_time);
        payload.add(buffering_time);
        payload.add(playback_start_time);
        payload.add(ip_server);
        payload.add(manifest_uri);
        payload.add(manifest_dwnl_byte);
        payload.add(manifest_dwnl_time);
        payload.add(http_response);
        payload.add(channel_id);
        payload.add(channel_type);
        payload.add(channel_epg);
        payload.add(channel_name);
        payload.add(restart_sec);
    }

    public String getRestart_sec() {
        return restart_sec;
    }

    public void setRestart_sec(String restart_sec) {
        this.restart_sec = restart_sec;
    }

}
