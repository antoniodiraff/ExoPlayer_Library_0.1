package com.google.android.exoplayer2.demo.lib.Model;


// SSDVOD : Session Start Download VOD


import java.util.ArrayList;

public class SSDVOD extends VOD {
    String drm_time;
    String buffering_time;
    String ip_server;
    String manifest_uri;
    String manifest_dwnl_byte;
    String manifest_dwnl_time;
    String http_response;
    String offer_id;
    String asset_title;
    String asset_source;


    public SSDVOD(String drm_time,String buffering_time,String ip_server,String manifest_uri,String manifest_dwnl_byte,
                  String manifest_dwnl_time,String http_response,String offer_id,String asset_title,String asset_source) {

        this.asset_source=asset_source;
        this.drm_time=drm_time;
        this.buffering_time=buffering_time;
        this.manifest_uri=manifest_uri;
        this.manifest_dwnl_time=manifest_dwnl_time;
        this.manifest_dwnl_byte=manifest_dwnl_byte;
        this.http_response=http_response;
        this.offer_id=offer_id;
        this.asset_title=asset_title;
        this.ip_server=ip_server;
    }

    public SSDVOD(){

    }

    public void update() {
        payload = new ArrayList<String>();
        payload.add(sessionId);
        payload.add(startTime);
        payload.add(drm_time);
        payload.add(buffering_time);
        payload.add(ip_server);
        payload.add(manifest_uri);
        payload.add(manifest_dwnl_byte);
        payload.add(manifest_dwnl_time);
        payload.add(http_response);
        payload.add(offer_id);
        payload.add(asset_title);
        payload.add(asset_source);
    }


}
