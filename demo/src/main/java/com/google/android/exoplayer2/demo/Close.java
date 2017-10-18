package com.google.android.exoplayer2.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties({
        "ip_server",
        "http_response"
})
public abstract class Close extends PayLoadElement {


    // ############   CLOSE : When a session finishes (also if is caused by an error that can’t permit streaming anymore).

//        SC : Session Close
//        SDC : Session Download Completed  (reached download end)
//        SPC : Session Playback Close  (playback finished)
}
    @JsonPropertyOrder({
            "session_id",
            "closing_time"})
    class SC extends Close {
        String closing_time;

        public String getClosing_time() {
            return closing_time;
        }

        public void setClosing_time(String closing_time) {
            this.closing_time = closing_time;
        }

        public SC() {

        }
    }

    @JsonPropertyOrder({
            "session_id",
            "completed_time"})
    class SDC extends Close {
        String completed_time;

        public String getCompleted_time() {
            return completed_time;
        }

        public void setCompleted_time(String completed_time) {
            this.completed_time = completed_time;
        }

        public SDC() {

        }
    }

    @JsonPropertyOrder({
            "session_id",
            "closing_time",
            "viewving_perc"})
    class SPC extends Close {
        String closing_time;
        String viewving_perc;

        public SPC() {
        }
    }


