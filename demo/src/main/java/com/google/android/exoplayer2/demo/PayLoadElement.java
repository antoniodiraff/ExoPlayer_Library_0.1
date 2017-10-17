package com.google.android.exoplayer2.demo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Created by sps on 06/10/2017.
 */
// The generic Payload Element
class PayLoadElement {

    String session_id;
    String ip_server;
    String http_response;

    public PayLoadElement() {
    }

    public PayLoadElement(String session_id) {
        this.session_id = session_id;
    }

    public String getSession_id() {
        return session_id;
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
}

// ############   START : Every time a session starts. ############
abstract class Start extends PayLoadElement {

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
    //  CH : Live Channel
//  ****************************************************
//  SSCH : Session Start CHannel
//  SSRCH : Session Start Restart Channel


//  VOD : Video On Demand
//  ****************************************************
//  SSVOD : Session Start VOD
//  SSDVOD : Session Start Download VOD (**)
//  SSPVOD : Session Start Playback VOD (**)

//  ******************************************************
//    SSLINK : Session Start LINK
}

//CH
abstract class CH extends Start {

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
@JsonPropertyOrder({
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
        "restart_sec"})
@JsonIgnoreProperties({
        "original_session_id",
        "restart_sec"})
class SSCH extends CH {

    public SSCH() {
    }
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
class SSRCH extends CH {

    public SSRCH() {
    }
}

//VOD
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

//SSLINK
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


// ############  DOWNLOAD : During the download of VOD chunks.  ############
@JsonIgnoreProperties({
        "ip_server",
        "http_response",
})
abstract class Download extends PayLoadElement {
//    SDP : Session Download Pause
//    SDR : Session Download Resume
//    SDD : Session Download Delete (download removed)
}
@JsonPropertyOrder({
        "session_id",
        "pause_time",
        "pause_cause",
        "download_perc"})
class SDP extends Download {
    String pause_time;
    String pause_cause;
    String download_perc;

    public SDP() {

    }
}
@JsonPropertyOrder({
        "session_id",
        "resume_time"})
class SDR extends Download {
    String resume_time;

    public SDR() {

    }
}
@JsonPropertyOrder({
        "session_id",
        "delete_time"})
class SDD extends Download {
    String delete_time;

    public String getDelete_time() {
        return delete_time;
    }

    public void setDelete_time(String delete_time) {
        this.delete_time = delete_time;
    }

    public SDD() {

    }
}


// ############  PLAYBACK : During the playback of downloaded VOD. (**)
@JsonIgnoreProperties({
        "ip_server",
        "http_response",
})
abstract class Playback extends PayLoadElement {
//        SPP : Session Playback Pause
//        SPR : Session Playback Restart
}
@JsonPropertyOrder({
        "session_id",
        "pause_time"})
class SPP extends Download {
    String pause_time;

    public SPP() {

    }
}
@JsonPropertyOrder({
        "session_id",
        "restart_time"})
class SPR extends Download {
    String restart_time;

    public SPR() {

    }
}


// ############   CLOSE : When a session finishes (also if is caused by an error that can’t permit streaming anymore).
@JsonIgnoreProperties({
        "ip_server",
        "http_response"
})
abstract class Close extends PayLoadElement {
//        SC : Session Close
//        SDC : Session Download Completed  (reached download end)
//        SPC : Session Playback Close  (playback finished)

}

@JsonPropertyOrder({
        "session_id",
        "closing_time"})
class SC extends Close {
    String closing_time;

    public String getClosing_time() {
        return closing_time;
    }

    public void setClosing_time(String closing_time) {
        this.closing_time = closing_time;
    }

    public SC() {

    }
}
@JsonPropertyOrder({
        "session_id",
        "completed_time"})
class SDC extends Close {
    String completed_time;

    public String getCompleted_time() {
        return completed_time;
    }

    public void setCompleted_time(String completed_time) {
        this.completed_time = completed_time;
    }

    public SDC() {

    }
}
@JsonPropertyOrder({
        "session_id",
        "closing_time",
        "viewving_perc"})
class SPC extends Close {
    String closing_time;
    String viewving_perc;

    public SPC() {
    }
}


//   ############  ERROR : Everytime an Error is raised.
//        SE : Session Error
@JsonIgnoreProperties({
        "http_response"
})
@JsonPropertyOrder({
        "session_id",
        "error_text",
        "errorType",
        "error_code",
        "chunk_uri",
        "channel_id",
        "event_id",
        "vod_id",
        "player_version",
        "event_name",
        "error_message"
})
class SE extends PayLoadElement {
    String error_text;
    String errorType;
    String error_code;
    String chunk_uri;
    String channel_id;
    String event_id;
    String vod_id;
    String player_version;
    String event_name;
    String error_message;

    public SE(String Type){
        errorType=Type;
    }

    public String getError_text() {
        return error_text;
    }

    public void setError_text(String error_text) {
        this.error_text = error_text;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getChunk_uri() {
        return chunk_uri;
    }

    public void setChunk_uri(String chunk_uri) {
        this.chunk_uri = chunk_uri;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getVod_id() {
        return vod_id;
    }

    public void setVod_id(String vod_id) {
        this.vod_id = vod_id;
    }

    public String getPlayer_version() {
        return player_version;
    }

    public void setPlayer_version(String player_version) {
        this.player_version = player_version;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
}