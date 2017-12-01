package com.google.android.exoplayer2.demo.lib.Model;

// SSVOD : Session Start VOD

import java.util.ArrayList;

public class SSVOD extends VOD {

    public SSVOD() {
    }

    public void update() {
        payload = new ArrayList<String>();
        payload.add(session_id);
        payload.add(start_time);
        payload.add(drm_time);
        payload.add(buffering_time);
        payload.add(playback_start_time);
        payload.add(ip_server);
        payload.add(manifest_uri);
        payload.add(manifest_dwnl_byte);
        payload.add(manifest_dwnl_time);
        payload.add(http_response);
        payload.add(offer_id);
        payload.add(asset_title);
        payload.add(asset_type);
        payload.add(asset_source);
    }
}

