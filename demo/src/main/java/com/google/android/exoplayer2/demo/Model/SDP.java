package com.google.android.exoplayer2.demo.Model;

//@JsonPropertyOrder({
//        "session_id",
//        "pause_time",
//        "pause_cause",
//        "download_perc"})
public class SDP extends Download {
    String pause_time;
    String pause_cause;
    String download_perc;

    public SDP() {

    }
}
