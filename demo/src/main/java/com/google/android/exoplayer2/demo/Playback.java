package com.google.android.exoplayer2.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// ############  PLAYBACK : During the playback of downloaded VOD. (**)
@JsonIgnoreProperties({
        "ip_server",
        "http_response",
})
 public abstract class Playback extends PayLoadElement {
//        SPP : Session Playback Pause
//        SPR : Session Playback Restart


 //    PayloadElement attribute -

//    String session_id;
//    String ip_server;
//    String http_response;
}

