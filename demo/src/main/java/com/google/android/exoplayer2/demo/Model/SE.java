package com.google.android.exoplayer2.demo.Model;

import java.util.ArrayList;

//SE : Session Error

public class SE extends PayLoadElement {
    public String error_text;
    public String errorType;
    public String error_code;
    public String chunk_uri;
    public String channel_id;
    public String event_id;
    public String vod_id;
    public String player_version;
    public String event_name;
    public String error_message;

    public SE() {
    }

    public void updateSEPayload() {
        payload = new ArrayList<String>();
        payload.add(session_id);
        payload.add(error_text);
        payload.add(errorType);
        payload.add(error_code);
        payload.add(chunk_uri);
        payload.add(channel_id);
        payload.add(event_id);
        payload.add(vod_id);
        payload.add(player_version);
        payload.add(event_name);
        payload.add(error_message);
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
