package com.google.android.exoplayer2.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;

@JsonPropertyOrder({
            "session_id",
            "restart_time"})
    @JsonIgnoreProperties({
            "ip_server",
            "http_response"
    })

//    STR : Session sTreaming Restart


    public class STR extends Streaming {
        String restart_time;

        public STR() {
        }

    public void updateSTRPayload() {
        payload = new ArrayList<String>();
        payload.add(session_id);
        payload.add(restart_time);

    }

    }
