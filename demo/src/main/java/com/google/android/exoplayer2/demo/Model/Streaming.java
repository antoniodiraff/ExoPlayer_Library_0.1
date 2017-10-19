package com.google.android.exoplayer2.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;

// ############   STREAMING : During the streaming ############

public abstract class Streaming extends PayLoadElement {

//    STD : Session sTreaming Download (valid also for VOD download) STP : Session sTreaming Pause
//    STR : Session sTreaming Restart
//    STCL : Session sTreaming Change Level
//    STRB : Session sTreaming ReBuferring

//    PayloadElement attribute -

//    String session_id;
//    String ip_server;
//    String http_response;


    @JsonPropertyOrder({
            "session_id",
            "Layer",
            "buffer_size",
            "fps_decoded",
            "chunk_type",
            "chunk_index",
            "chunk_uri",
            "dwnl_byte",
            "dwnl_time",
            "response_time",
            "ip_server",
            "http_response",
    })
    public class STD extends Streaming {
        String Layer;
        String buffer_size;
        String fps_decoded;
        String chunk_type;
        String chunk_index;
        String chunk_uri;
        String dwnl_byte;
        String dwnl_time;
        String response_time;

        public STD() {
        }
    }

    @JsonPropertyOrder({
            "session_id",
            "pause_time"})
    @JsonIgnoreProperties({
            "ip_server",
            "http_response",
    })
    public class STP extends Streaming {
        String pause_time;

        public STP() {
        }
    }

    @JsonPropertyOrder({
            "session_id",
            "restart_time"})
    @JsonIgnoreProperties({
            "ip_server",
            "http_response"
    })
    public class STR extends Streaming {
        String restart_time;

        public STR() {
        }
    }

    @JsonIgnoreProperties({
            "ip_server",
            "http_response"
    })
    @JsonPropertyOrder({
            "session_id",
            "rebuffering_start_time",
            "rebuffering_end_time"
    })
    public class STRB extends Streaming {
        String rebuffering_start_time;
        String rebuffering_end_time;

        public STRB() {
        }
    }

    @JsonPropertyOrder({
            "session_id",
            "bitrate_from",
            "bitrate_to"})
    @JsonIgnoreProperties({
            "ip_server",
            "http_response"
    })
    public static class STCL extends Streaming {
        String bitrate_from;
        String bitrate_to;

        public void createSDDPayload() {
            payload = new ArrayList<String>();
            payload.add(session_id);
            payload.add(bitrate_from);
            payload.add(bitrate_to);
        }

        public String getBitrate_from() {
            return bitrate_from;
        }

        public void setBitrate_from(String bitrate_from) {
            this.bitrate_from = bitrate_from;
        }

        public String getBitrate_to() {
            return bitrate_to;
        }

        public void setBitrate_to(String bitrate_to) {
            this.bitrate_to = bitrate_to;
        }

        public STCL() {
        }
    }
}
