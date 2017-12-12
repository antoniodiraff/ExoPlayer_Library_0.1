package com.sky.telemetry_framework.model;

public abstract class VOD extends Start {

    //  VOD : Video On Demand
//  ****************************************************
//  SSVOD : Session Start VOD
//  SSDVOD : Session Start Download VOD (**)
//  SSPVOD : Session Start Playback VOD. During the Playback, if the user is offline, the SSPVOD events should be collected and sent as soon as will come back online

    // (**) SkygoPlus capabilities

    String offerId;
    String assetTitle;
    String assetType;
    String assetSource;


    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getAssetTitle() {
        return assetTitle;
    }

    public void setAssetTitle(String assetTitle) {
        this.assetTitle = assetTitle;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getAssetSource() {
        return assetSource;
    }

    public void setAssetSource(String assetSource) {
        this.assetSource = assetSource;
    }
}
