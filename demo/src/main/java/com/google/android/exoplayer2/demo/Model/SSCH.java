package com.google.android.exoplayer2.demo.Model;

import java.util.ArrayList;

public class SSCH extends LiveCH {


    public SSCH() {
    }

    public void update() {
        payload = new ArrayList<String>();
        payload.add(session_id);
        payload.add(start_time);
        payload.add(drm_time);
        payload.add(3, buffering_time);
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
        //return payload;
    }


}
