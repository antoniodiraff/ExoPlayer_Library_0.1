package com.google.android.exoplayer2.demo;

import android.content.Context;

import com.google.android.exoplayer2.demo.Model.ACI;
import com.google.android.exoplayer2.demo.Model.ASI;
import com.google.android.exoplayer2.demo.Model.Event;
import com.google.android.exoplayer2.demo.Model.EventElement;

import java.util.ArrayList;

import static com.google.android.exoplayer2.demo.Observer.event;

/**
 * Created by antoniodiraffaele on 18/10/17.
 */

public class PlayerMonitor {

  public ASI asi;
  public ACI aci;
    boolean enableTrace= false ;

    String serverURL;
    String serverTimeout;
    String userAgent;
    String dev_id;
    String user_extid;
    String source;//    1. "SKYGO_INTV2.0" for SkyGo applcation
                  //    2. "SOL_INTV2.0" for NowTV application
                  //    3. "SKYKIDS_INTV2.0" for Kids application
    String dequeueingIntervalTime;
    String onClosing;
    String offset;
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



    public ACI getAci() {
        return aci;
    }

    public void setAci(ACI aci) {
        this.aci = aci;
    }

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

    public Event getEvent() {
        return event;
    }

    public ASI getAsi() {
        return asi;
    }

    public void setAsi(ASI asi) {
        this.asi = asi;
    }

    public PlayerMonitor(Context c, boolean enableTrace, String serverURL, String serverTimeout, String userAgent, String dev_id, String user_extid, String source, String dequeueingIntervalTime, String onClosing, String offset) {
        this.enableTrace = enableTrace;
        this.serverURL = serverURL;
        this.serverTimeout = serverTimeout;
        this.userAgent = userAgent;
        this.dev_id = dev_id;
        this.user_extid = user_extid;
        this.source = source;
        this.dequeueingIntervalTime = dequeueingIntervalTime;
        this.onClosing = onClosing;
        this.offset = offset;
        this.c=c;
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

    public String getServerTimeout() {
        return serverTimeout;
    }

    public void setServerTimeout(String serverTimeout) {
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

    public String getDequeueingIntervalTime() {
        return dequeueingIntervalTime;
    }

    public void setDequeueingIntervalTime(String dequeueingIntervalTime) {
        this.dequeueingIntervalTime = dequeueingIntervalTime;
    }

    public String getOnClosing() {
        return onClosing;
    }

    public void setOnClosing(String onClosing) {
        this.onClosing = onClosing;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }



    public void activate(String device_vendor, String device_model, String device_so, String codice_cliente ) {

        createEventBody(c);
        asi = new ASI();
        asi.setStart_time(Observer.getCurrentTimeStamp());
        asi.setCodice_cliente(codice_cliente);
        asi.setDevice_model(device_model);
        asi.setDevice_so(device_so);
        asi.setDevice_vendor(device_vendor);

        asi.updateASIPayload();

        event.events_list.add(new EventElement("ASI", asi.getPayload()));

    }

    public void terminate()
    {
        aci= new ACI();
        aci.setStop_time(Observer.getCurrentTimeStamp());
    }




    public Event createEventBody(Context c) {

        ArrayList<EventElement> eventList= new ArrayList<EventElement>();
        DeviceInfo deviceInfo = new DeviceInfo(c);
        Observer.event = new Event(c, deviceInfo, eventList);

        return event;
    }


}
