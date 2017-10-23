package com.google.android.exoplayer2.demo;

import android.content.Context;

import com.google.android.exoplayer2.demo.Model.ACI;
import com.google.android.exoplayer2.demo.Model.ASI;
import com.google.android.exoplayer2.demo.Model.Event;
import com.google.android.exoplayer2.demo.Model.EventElement;

import java.util.ArrayList;


/**
 * Created by antoniodiraffaele on 18/10/17.
 */

public class PlayerMonitor {

    boolean enableTrace = false;
    String serverURL;
    int serverTimeout;
    String userAgent;
    String dev_id;
    String user_extid;
    String source;
    //    1. "SKYGO_INTV2.0" for SkyGo applcation
    //    2. "SOL_INTV2.0" for NowTV application
    //    3. "SKYKIDS_INTV2.0" for Kids application
    int dequeueingIntervalTime;

//    boolean isLocalFile = false;
//    boolean isRestart=false ;
//    String originalSessionID;
//    String restartSec;
//    boolean isFree = false;


//    String channelName;
//    String channelID;
//    String  channelType;
//    String channelEpg;
//
//
//    String vodID;
//    String VODTitle;
//    String assetType;
//    String assetPath;


    Context c;
    public String device_vendor;
    public String device_model;
    public String device_so;
    public String codice_cliente;

//    public void updateChannelInfo(String channelName, String channelID,String  channelType, String channelEpg){
//        this.channelEpg=channelEpg;
//        this.channelID=channelID;
//        this.channelName=channelName;
//        this.channelType=channelType;
//    }
//    public void updateVODInfo(String vodID, String VODTitle, String assetType, String assetPath){
//        this.vodID=vodID;
//        this.VODTitle=VODTitle;
//        this.assetPath=assetPath;
//        this.assetType=assetType;
//    }


//    public ACI getAci() {
//        return aci;
//    }
//
//    public void setAci(ACI aci) {
//        this.aci = aci;
//    }

//    public boolean isLocalFile() {
//        return isLocalFile;
//    }
//
//    public void setLocalFile(boolean localFile) {
//        isLocalFile = localFile;
//    }
//
//    public boolean isRestart() {
//        return isRestart;
//    }
//
//    public void setRestart(boolean restart) {
//        isRestart = restart;
//    }
//
//    public String getOriginalSessionID() {
//        return originalSessionID;
//    }
//
//    public void setOriginalSessionID(String originalSessionID) {
//        this.originalSessionID = originalSessionID;
//    }
//
//    public String getRestartSec() {
//        return restartSec;
//    }
//
//    public void setRestartSec(String restartSec) {
//        this.restartSec = restartSec;
//    }
//
//    public boolean isFree() {
//        return isFree;
//    }
//
//    public void setFree(boolean free) {
//        isFree = free;
//    }
//
//    public String getChannelName() {
//        return channelName;
//    }
//
//    public void setChannelName(String channelName) {
//        this.channelName = channelName;
//    }
//
//    public String getChannelID() {
//        return channelID;
//    }
//
//    public void setChannelID(String channelID) {
//        this.channelID = channelID;
//    }
//
//    public String getChannelType() {
//        return channelType;
//    }
//
//    public void setChannelType(String channelType) {
//        this.channelType = channelType;
//    }
//
//    public String getChannelEpg() {
//        return channelEpg;
//    }
//
//    public void setChannelEpg(String channelEpg) {
//        this.channelEpg = channelEpg;
//    }
//
//    public String getVodID() {
//        return vodID;
//    }
//
//    public void setVodID(String vodID) {
//        this.vodID = vodID;
//    }
//
//    public String getVODTitle() {
//        return VODTitle;
//    }
//
//    public void setVODTitle(String VODTitle) {
//        this.VODTitle = VODTitle;
//    }
//
//    public String getAssetType() {
//        return assetType;
//    }
//
//    public void setAssetType(String assetType) {
//        this.assetType = assetType;
//    }
//
//    public String getAssetPath() {
//        return assetPath;
//    }
//
//    public void setAssetPath(String assetPath) {
//        this.assetPath = assetPath;
//    }

    public Context getC() {
        return c;
    }

    public void setC(Context c) {
        this.c = c;
    }

//    public Event getEvent() {
//        return event;
//    }
//
//    public ASI getAsi() {
//        return asi;
//    }
//
//    public void setAsi(ASI asi) {
//        this.asi = asi;
//    }

    public PlayerMonitor(Context c, boolean enableTrace, String serverURL, int serverTimeout, String userAgent,
                         String dev_id, String user_extid, String source, int dequeueingIntervalTime,
                         String device_vendor, String device_model, String device_so, String codice_cliente
//            , String onClosing, String offset
    ) {
        this.enableTrace = enableTrace;
        this.serverURL = serverURL;
        this.serverTimeout = serverTimeout;
        this.userAgent = userAgent;
        this.dev_id = dev_id;
        this.user_extid = user_extid;
        this.source = source;
        this.dequeueingIntervalTime = dequeueingIntervalTime;
//        this.onClosing = onClosing;
//        this.offset = offset;
        this.c = c;
        this.device_vendor = device_vendor;
        this.device_model = device_model;
        this.device_so = device_so;
        this.codice_cliente = codice_cliente;
    }


    public PlayerMonitor() {
    }

    public boolean isEnableTrace() {
        return enableTrace;
    }

    public void setEnableTrace(boolean enableTrace) {
        this.enableTrace = enableTrace;
    }

    public String getServerURL() {
        return serverURL;
    }

    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    public int getServerTimeout() {
        return serverTimeout;
    }

    public void setServerTimeout(int serverTimeout) {
        this.serverTimeout = serverTimeout;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getDev_id() {
        return dev_id;
    }

    public void setDev_id(String dev_id) {
        this.dev_id = dev_id;
    }

    public String getUser_extid() {
        return user_extid;
    }

    public void setUser_extid(String user_extid) {
        this.user_extid = user_extid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getDequeueingIntervalTime() {
        return dequeueingIntervalTime;
    }

    public void setDequeueingIntervalTime(int dequeueingIntervalTime) {
        this.dequeueingIntervalTime = dequeueingIntervalTime;
    }

}
