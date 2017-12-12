package com.sky.telemetry_framework.model;

public abstract class Start extends PayLoadElement {

    // ############   START : Every time a session starts. ############

    //  CH : Live Channel
    //  VOD : Video On Demand

    String originalSessionId;
    String playbackStartTime;
    String startTime;
    String drmTime;
    String bufferingTime;
    String manifestUri;
    String manifestDwnlByte;
    String manifestDwnlTime;
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


    public String getOriginalSessionId() {
        return originalSessionId;
    }

    public void setOriginalSessionId(String originalSessionId) {
        this.originalSessionId = originalSessionId;
    }

    public String getPlaybackStartTime() {
        return playbackStartTime;
    }

    public void setPlaybackStartTime(String playbackStartTime) {
        this.playbackStartTime = playbackStartTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDrmTime() {
        return drmTime;
    }

    public void setDrmTime(String drmTime) {
        this.drmTime = drmTime;
    }

    public String getBufferingTime() {
        return bufferingTime;
    }

    public void setBufferingTime(String bufferingTime) {
        this.bufferingTime = bufferingTime;
    }

    public String getManifestUri() {
        return manifestUri;
    }

    public void setManifestUri(String manifestUri) {
        this.manifestUri = manifestUri;
    }

    public String getManifestDwnlByte() {
        return manifestDwnlByte;
    }

    public void setManifestDwnlByte(String manifestDwnlByte) {
        this.manifestDwnlByte = manifestDwnlByte;
    }

    public String getManifestDwnlTime() {
        return manifestDwnlTime;
    }

    public void setManifestDwnlTime(String manifestDwnlTime) {
        this.manifestDwnlTime = manifestDwnlTime;
    }
}
