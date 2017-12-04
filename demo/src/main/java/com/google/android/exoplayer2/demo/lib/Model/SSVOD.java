package com.google.android.exoplayer2.demo.lib.Model;

// SSVOD : Session Start VOD

import java.util.ArrayList;

public class SSVOD extends VOD {

    public SSVOD() {
    }

    public void update() {
        payload = new ArrayList<String>();
        payload.add(sessionId);
        payload.add(startTime);
        payload.add(drmTime);
        payload.add(bufferingTime);
        payload.add(playbackStartTime);
        payload.add(ipServer);
        payload.add(manifestUri);
        payload.add(manifestDwnlByte);
        payload.add(manifestDwnlTime);
        payload.add(httpResponse);
        payload.add(offer_id);
        payload.add(asset_title);
        payload.add(asset_type);
        payload.add(asset_source);
    }
}

