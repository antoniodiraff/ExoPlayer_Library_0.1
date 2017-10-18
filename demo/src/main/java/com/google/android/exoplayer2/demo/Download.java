package com.google.android.exoplayer2.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

// ############  DOWNLOAD : During the download of VOD chunks.  ############
@JsonIgnoreProperties({
        "ip_server",
        "http_response",
})
public abstract class Download extends PayLoadElement {
//    SDP : Session Download Pause
//    SDR : Session Download Resume
//    SDD : Session Download Delete (download removed)
}
    @JsonPropertyOrder({
            "session_id",
            "pause_time",
            "pause_cause",
            "download_perc"})
    class SDP extends Download {
        String pause_time;
        String pause_cause;
        String download_perc;

        public SDP() {

        }
    }

    @JsonPropertyOrder({
            "session_id",
            "resume_time"})
    class SDR extends Download {
        String resume_time;

        public SDR() {

        }
    }

    @JsonPropertyOrder({
            "session_id",
            "delete_time"})
    class SDD extends Download {
        String delete_time;

        public String getDelete_time() {
            return delete_time;
        }

        public void setDelete_time(String delete_time) {
            this.delete_time = delete_time;
        }

        public SDD() {

        }
    }

