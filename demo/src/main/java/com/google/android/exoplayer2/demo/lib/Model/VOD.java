package com.google.android.exoplayer2.demo.lib.Model;

public abstract class VOD extends Start {

    //  VOD : Video On Demand
//  ****************************************************
//  SSVOD : Session Start VOD
//  SSDVOD : Session Start Download VOD (**)
//  SSPVOD : Session Start Playback VOD. During the Playback, if the user is offline, the SSPVOD events should be collected and sent as soon as will come back online

    String offer_id;
    String asset_title;
    String asset_type;
    String asset_source;


    public String getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
    }

    public String getAsset_title() {
        return asset_title;
    }

    public void setAsset_title(String asset_title) {
        this.asset_title = asset_title;
    }

    public String getAsset_type() {
        return asset_type;
    }

    public void setAsset_type(String asset_type) {
        this.asset_type = asset_type;
    }

    public String getAsset_source() {
        return asset_source;
    }

    public void setAsset_source(String asset_source) {
        this.asset_source = asset_source;
    }


}
