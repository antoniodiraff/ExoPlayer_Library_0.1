package com.google.android.exoplayer2.demo.Model;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;


/**
 * Created by sps on 06/10/2017.
 */
// The generic Payload Element
//@JsonIgnoreProperties("payLoad")
abstract class PayLoadElement {

    public  String session_id;
    public  String ip_server;
    public  String http_response;
    public ArrayList<String> payload;

    public PayLoadElement() {
    }

    public PayLoadElement(String session_id) {
        this.session_id = session_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public ArrayList<String> getPayload() {
        return payload;
    }

    public void setPayload(ArrayList<String> payload) {
        this.payload = payload;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getIp_server() {
        return ip_server;
    }

    public void setIp_server(String ip_server) {
        this.ip_server = ip_server;
    }

    public String getHttp_response() {
        return http_response;
    }

    public void setHttp_response(String http_response) {
        this.http_response = http_response;
    }

//   ############  ERROR : Everytime an Error is raised.
//        SE : Session Error

    //@JsonIgnoreProperties({
//        "http_response"
//})
//@JsonPropertyOrder({
//        "session_id",
//        "error_text",
//        "errorType",
//        "error_code",
//        "chunk_uri",
//        "channel_id",
//        "event_id",
//        "vod_id",
//        "player_version",
//        "event_name",
//        "error_message"
//})
}

abstract class Start extends PayLoadElement {

    // ############   START : Every time a session starts. ############

    //  CH : Live Channel
    //  VOD : Video On Demand

    String original_session_id;
    String playback_start_time;
    String start_time;
    String drm_time;
    String buffering_time;
    String manifest_uri;
    String manifest_dwnl_byte;
    String manifest_dwnl_time;
    //    PayloadElement attribute -
    // String session_id;
    // String ip_server;
    // String http_response;

//    public String getRestart_sec() {
//        return restart_sec;
//    }
//
//    public void setRestart_sec(String restart_sec) {
//        this.restart_sec = restart_sec;
//    }
//
//    public String getOriginal_session_id() {
//        return original_session_id;
//    }


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
        if (start_time != null) {
            this.start_time = start_time;
        } else {
            this.start_time = "";
        }
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

abstract class LiveCH extends Start {

//  CH : Live Channel
//  ****************************************************
//  SSCH : Session Start CHannel
//  SSRCH : Session Start Restart Channel

    String channel_id;
    String channel_type;
    String channel_epg;
    String channel_name;
    String original_session_id;


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

abstract class VOD extends Start {

    //  VOD : Video On Demand
//  ****************************************************
//  SSVOD : Session Start VOD
//  SSDVOD : Session Start Download VOD (**)
// SSPVOD : Session Start Playback VOD. During the Playback, if the user is offline, the SSPVOD events should be collected and sent as soon as will come back online

    String offer_id;
    String asset_title;
    String asset_type;
    String asset_source;


    public String getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
    }

    public String getAsset_title() {
        return asset_title;
    }

    public void setAsset_title(String asset_title) {
        this.asset_title = asset_title;
    }

    public String getAsset_type() {
        return asset_type;
    }

    public void setAsset_type(String asset_type) {
        this.asset_type = asset_type;
    }

    public String getAsset_source() {
        return asset_source;
    }

    public void setAsset_source(String asset_source) {
        this.asset_source = asset_source;
    }


}

//@JsonIgnoreProperties({
//        "ip_server",
//        "http_response",
//})
abstract class Playback extends PayLoadElement {

    // ############  PLAYBACK : During the playback of downloaded VOD. (**)

//        SPP : Session Playback Pause
//        SPR : Session Playback Restart

}

abstract class Close extends PayLoadElement {


    public Close(){}
    // ############   CLOSE : When a session finishes (also if is caused by an error that can’t permit streaming anymore).

//        SC : Session Close
//        SDC : Session Download Completed  (reached download end)
//        SPC : Session Playback Close  (playback finished)

}
//@JsonIgnoreProperties({
//        "ip_server",
//        "http_response",
//})
abstract class Download extends PayLoadElement {

    // ############  DOWNLOAD : During the download of VOD chunks.  ############

//    SDP : Session Download Pause
//    SDR : Session Download Resume
//    SDD : Session Download Delete (download removed)

}

// ############  APPLICATION EVENTS startup / stop  ############

abstract class ApplicationEvents {

    public ArrayList<String> payload;

    public ArrayList<String> getPayload() {
        return payload;
    }

    public void setPayload(ArrayList<String> payload) {
        this.payload = payload;
    }

}



abstract class Streaming extends PayLoadElement {

//    STD : Session sTreaming Download (valid also for VOD download) STP : Session sTreaming Pause
//    STR : Session sTreaming Restart
//    STCL : Session sTreaming Change Level
//    STRB : Session sTreaming ReBuferring


}