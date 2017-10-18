package com.google.android.exoplayer2.demo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sps on 06/10/2017.
 */
// The generic Payload Element
@JsonIgnoreProperties("payLoad")
class PayLoadElement {

    String session_id;
    String ip_server;
    String http_response;
    ArrayList<String> payLoad;

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