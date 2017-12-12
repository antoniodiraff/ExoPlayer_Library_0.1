package com.sky.telemetry_framework.model;

import java.util.ArrayList;


/**
 * Created by sps on 06/10/2017.
 */
// The generic Payload Element
//@JsonIgnoreProperties("payLoad")
public abstract class PayLoadElement {

    public  String sessionId;
    public  String ipServer;
    public  String httpResponse;
    public ArrayList<String> payload;

    public PayLoadElement() {
    }

    public PayLoadElement(String session_id) {
        this.sessionId = session_id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getIpServer() {
        return ipServer;
    }

    public void setIpServer(String ipServer) {
        this.ipServer = ipServer;
    }

    public String getHttpResponse() {
        return httpResponse;
    }

    public void setHttpResponse(String httpResponse) {
        this.httpResponse = httpResponse;
    }

    public abstract void update();

    public ArrayList<String> getPayload() {
        return payload;
    }

    public void setPayload(ArrayList<String> payload) {
        this.payload = payload;
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

