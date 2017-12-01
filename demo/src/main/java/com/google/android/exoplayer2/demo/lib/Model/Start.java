package com.google.android.exoplayer2.demo.lib.Model;

public abstract class Start extends PayLoadElement {

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

//    public abstract ArrayList<String> update();

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
