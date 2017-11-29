package com.google.android.exoplayer2.demo.Model;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;


/**
 * Created by sps on 06/10/2017.
 */
// The generic Payload Element
//@JsonIgnoreProperties("payLoad")
public abstract class PayLoadElement {

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

    public abstract void update();

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

