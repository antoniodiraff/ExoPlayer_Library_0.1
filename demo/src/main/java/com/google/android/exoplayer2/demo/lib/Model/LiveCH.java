package com.google.android.exoplayer2.demo.lib.Model;

public abstract class LiveCH extends Start {

//  CH : Live Channel
//  ****************************************************
//  SSCH : Session Start CHannel
//  SSRCH : Session Start Restart Channel

    String channelId;
    String channelType;
    String channelEpg;
    String channelName;


    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getChannelEpg() {
        return channelEpg;
    }

    public void setChannelEpg(String channelEpg) {
        this.channelEpg = channelEpg;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

}
