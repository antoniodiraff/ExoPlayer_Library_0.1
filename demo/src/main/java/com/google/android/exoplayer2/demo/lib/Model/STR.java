package com.google.android.exoplayer2.demo.lib.Model;

import java.util.ArrayList;

/*
@JsonPropertyOrder({
            "session_id",
            "restart_time"})
    @JsonIgnoreProperties({
            "ip_server",
            "http_response"
    })
*/

//    STR : Session sTreaming Restart


public class STR extends Streaming {
    String restartTime;

    public String getRestartTime() {
        return restartTime;
    }

    public void setRestartTime(String restartTime) {
        this.restartTime = restartTime;
    }

    public STR() {
    }

    public void update() {
        payload = new ArrayList<String>();
        payload.add(sessionId);
        payload.add(restartTime);
    }

}
