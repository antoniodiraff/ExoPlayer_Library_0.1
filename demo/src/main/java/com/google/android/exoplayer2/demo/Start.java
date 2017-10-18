package com.google.android.exoplayer2.demo;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public abstract class Start extends PayLoadElement {

    // ############   START : Every time a session starts. ############

    //  CH : Live Channel
    //  VOD : Video On Demand

    //    PayloadElement attribute -

    // String session_id;
    // String ip_server;
    // String http_response;
    String restart_sec;
    String original_session_id;
    String playback_start_time;
    String start_time;
    String drm_time;
    String buffering_time;
    String manifest_uri;
    String manifest_dwnl_byte;
    String manifest_dwnl_time;

    public String getRestart_sec() {
        return restart_sec;
    }

    public void setRestart_sec(String restart_sec) {
        this.restart_sec = restart_sec;
    }

    public String getOriginal_session_id() {
        return original_session_id;
    }

    public void setOriginal_session_id(String original_session_id) {
        this.original_session_id = original_session_id;
    }

    public String getPlayback_start_time() {
        return playback_start_time;
    }

    public void setPlayback_start_time(String playback_start_time) {
        this.playback_start_time = playback_start_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getDrm_time() {
        return drm_time;
    }

    public void setDrm_time(String drm_time) {
        this.drm_time = drm_time;
    }

    public String getBuffering_time() {
        return buffering_time;
    }

    public void setBuffering_time(String buffering_time) {
        this.buffering_time = buffering_time;
    }

    public String getManifest_uri() {
        return manifest_uri;
    }

    public void setManifest_uri(String manifest_uri) {
        this.manifest_uri = manifest_uri;
    }

    public String getManifest_dwnl_byte() {
        return manifest_dwnl_byte;
    }

    public void setManifest_dwnl_byte(String manifest_dwnl_byte) {
        this.manifest_dwnl_byte = manifest_dwnl_byte;
    }

    public String getManifest_dwnl_time() {
        return manifest_dwnl_time;
    }

    public void setManifest_dwnl_time(String manifest_dwnl_time) {
        this.manifest_dwnl_time = manifest_dwnl_time;
    }

}

//  CH : Live Channel
//  ****************************************************
//  SSCH : Session Start CHannel
//  SSRCH : Session Start Restart Channel


abstract class LiveCH extends Start {

    String channel_id;
    String channel_type;
    String channel_epg;
    String channel_name;

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getChannel_type() {
        return channel_type;
    }

    public void setChannel_type(String channel_type) {
        this.channel_type = channel_type;
    }

    public String getChannel_epg() {
        return channel_epg;
    }

    public void setChannel_epg(String channel_epg) {
        this.channel_epg = channel_epg;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }
}

// "session_id",
//         "start_time",
//         "drm_time",
//         "buffering_time",
//         "playback_start_time",
//         "ip_server",
//         "manifest_uri",
//         "manifest_dwnl_byte",
//         "manifest_dwnl_byte",
//         "manifest_dwnl_time",
//         "http_response",
//         "channel_id",
//         "channel_type",
//         "channel_epg",
//         "channel_name",
//         "restart_sec"

    @JsonIgnoreProperties({
            "session_id",
            "start_time",
            "drm_time",
            "buffering_time",
            "playback_start_time",
            "ip_server",
            "manifest_uri",
            "manifest_dwnl_byte",
            "manifest_dwnl_byte",
            "manifest_dwnl_time",
            "http_response",
            "channel_id",
            "channel_type",
            "channel_epg",
            "channel_name",
            "restart_sec",
            "original_session_id",
            "restart_sec"})
    @JsonPropertyOrder({"payload","payLoad_String"})
    class SSCH extends LiveCH {

// payload [0] = sessionI

        public SSCH() {


        }

       public ArrayList<String> createSSCHPayload(){
           payLoad = new ArrayList<String>();
           payLoad.add(session_id);
           payLoad.add(start_time);
           payLoad.add(drm_time);
           payLoad.add(buffering_time);
           payLoad.add(playback_start_time);
           return payLoad;
        }

//        payload[0]= session_id;
//        payload[1]=start_time;
//        payload[2]=drm_time;
//        payload[3]=buffering_time;
//        payload[4]=playback_start_time;
    }

    @JsonPropertyOrder({"session_id",
            "original_session_id",
            "start_time",
            "drm_time",
            "buffering_time",
            "playback_start_time",
            "ip_server",
            "manifest_uri",
            "manifest_dwnl_byte",
            "manifest_dwnl_byte",
            "manifest_dwnl_time",
            "http_response",
            "channel_id",
            "channel_type",
            "channel_epg",
            "channel_name",
            "restart_sec"})
    class SSRCH extends LiveCH {

        public SSRCH() {
        }
    }

//  VOD : Video On Demand
//  ****************************************************
//  SSVOD : Session Start VOD
//  SSDVOD : Session Start Download VOD (**)
//  SSPVOD : Session Start Playback VOD (**)

abstract class VOD extends Start {
    String offer_id;
    String asset_title;
    String asset_type;
    String asset_source;

    // SSVOD : Session Start VOD
    // SSDVOD : Session Start Download VOD
    // SSPVOD : Session Start Playback VOD. During the Playback, if the user is offline, the SSPVOD events should be collected and sent as soon as will come back online
    // SSLINK event is always generated after the new SSCH or SSVOD events. If for any reason the previos_session_id is not available, the SSLINK event will not be generated.
}

@JsonPropertyOrder({
        "session_id",
        "start_time",
        "drm_time",
        "buffering_time",
        "playback_start_time",
        "ip_server",
        "manifest_uri",
        "manifest_dwnl_byte",
        "manifest_dwnl_time",
        "http_response",
        "offer_id",
        "asset_title",
        "asset_type",
        "asset_source"
})
@JsonIgnoreProperties({
        "original_session_id",
        "restart_sec"
})
class SSVOD extends VOD {
    public SSVOD() {
    }
}

@JsonPropertyOrder({
        "session_id",
        "start_time",
        "drm_time",
        "buffering_time",
        "ip_server",
        "manifest_uri",
        "manifest_dwnl_byte",
        "manifest_dwnl_time",
        "http_response",
        "offer_id",
        "asset_title",
        "asset_source"})

@JsonIgnoreProperties({
        "restart_sec",
        "original_session_id",
        "asset_type",
        "playback_start_time"})
class SSDVOD extends VOD {
    public SSDVOD() {
    }
}

@JsonPropertyOrder({
        "session_id",
        "original_session_id",
        "start_time",
        "playback_start_time",
        "offer_id",
        "asset_title",
        "asset_source",
        "delay_time_sec"})

@JsonIgnoreProperties({
        "restart_sec",
        "drm_time",
        "buffering_time",
        "ip_server",
        "manifest_uri",
        "manifest_dwnl_byte",
        "manifest_dwnl_time",
        "http_response",
        "asset_type",
})
class SSPVOD extends VOD {
    String delay_time_sec;

    public SSPVOD() {
    }
}

//    SSLINK : Session Start LINK
//  ******************************************************

@JsonPropertyOrder({
        "new_session_id",
        "previos_session_id"
})

@JsonIgnoreProperties({
        "session_id",
        "start_time",
        "drm_time",
        "buffering_time",
        "ip_server",
        "manifest_uri",
        "manifest_dwnl_byte",
        "manifest_dwnl_time",
        "http_response",
        "offer_id",
        "asset_title",
        "asset_source",
        "restart_sec",
        "original_session_id",
        "asset_type",
        "playback_start_time"})
class SSLINK extends Start {
    String new_session_id;
    String previos_session_id;

    public SSLINK() {
    }
}



