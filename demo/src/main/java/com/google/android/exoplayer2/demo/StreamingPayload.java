package com.google.android.exoplayer2.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

// ############   STREAMING : During the streaming ############

public abstract class StreamingPayload extends PayLoadElement {

//    STD : Session sTreaming Download (valid also for VOD download) STP : Session sTreaming Pause
//    STR : Session sTreaming Restart
//    STCL : Session sTreaming Change Level
//    STRB : Session sTreaming ReBuferring

//    PayloadElement attribute -

//    String session_id;
//    String ip_server;
//    String http_response;
}

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
class STD extends StreamingPayload {
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
class STP extends StreamingPayload {
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
class STR extends StreamingPayload {
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
class STRB extends StreamingPayload {
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
class STCL extends StreamingPayload {
    String bitrate_from;
    String bitrate_to;
    public STCL() {
    }
}

