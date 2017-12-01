package com.google.android.exoplayer2.demo.lib.Model;

public abstract class LiveCH extends Start {

//  CH : Live Channel
//  ****************************************************
//  SSCH : Session Start CHannel
//  SSRCH : Session Start Restart Channel

    String channel_id;
    String channel_type;
    String channel_epg;
    String channel_name;
    String original_session_id;


    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getChannel_type() {
        return channel_type;
    }

    public void setChannel_type(String channel_type) {
        this.channel_type = channel_type;
    }

    public String getChannel_epg() {
        return channel_epg;
    }

    public void setChannel_epg(String channel_epg) {
        this.channel_epg = channel_epg;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }
}
