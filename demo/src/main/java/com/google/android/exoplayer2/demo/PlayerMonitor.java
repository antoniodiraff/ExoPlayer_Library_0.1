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
    boolean isLocalFile = false;
    boolean isRestart=false ;
    String originalSessionID;
    String restartSec;
    boolean isFree = false;


    String channelName;
    String channelID;
    String  channelType;
    String channelEpg;


    String vodID;
    String VODTitle;
    String assetType;
    String assetPath;


    Context c;

    public void updateChannelInfo(String channelName, String channelID,String  channelType, String channelEpg){
        this.channelEpg=channelEpg;
        this.channelID=channelID;
        this.channelName=channelName;
        this.channelType=channelType;
    }
    public void updateVODInfo(String vodID, String VODTitle, String assetType, String assetPath){
        this.vodID=vodID;
        this.VODTitle=VODTitle;
        this.assetPath=assetPath;
        this.assetType=assetType;
    }

    public ACI getAci() {
        return aci;
    }

    public void setAci(ACI aci) {
        this.aci = aci;
    }

    public boolean isLocalFile() {
        return isLocalFile;
    }

    public void setLocalFile(boolean localFile) {
        isLocalFile = localFile;
    }

    public boolean isRestart() {
        return isRestart;
    }

    public void setRestart(boolean restart) {
        isRestart = restart;
    }

    public String getOriginalSessionID() {
        return originalSessionID;
    }

    public void setOriginalSessionID(String originalSessionID) {
        this.originalSessionID = originalSessionID;
    }

    public String getRestartSec() {
        return restartSec;
    }

    public void setRestartSec(String restartSec) {
        this.restartSec = restartSec;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
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

    public String getVodID() {
        return vodID;
    }

    public void setVodID(String vodID) {
        this.vodID = vodID;
    }

    public String getVODTitle() {
        return VODTitle;
    }

    public void setVODTitle(String VODTitle) {
        this.VODTitle = VODTitle;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getAssetPath() {
        return assetPath;
    }

    public void setAssetPath(String assetPath) {
        this.assetPath = assetPath;
    }

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
//    String statoplayer =  getStateString(stato);
//    Log.d(TAG, "********************* LO STATO DEL PLAYER è IN " + getStateString(stato)  );
//    //    boolPlay=true;
////    if (startBool) {
////      if (boolPause){
////        numPlay++;
////        boolPlay = true;
////      }
////    } else {
////      if (boolPlay){
////        numPause++;
////        boolPause = true;
////      }
////    }
//
//    String statoPlayer = startBool ? " PLAY" : "PAUSE";
//    Log.d(TAG, "********************* LO STATO DEL PLAYER è IN " + getStateString(stato)  );
        //    Log.d(TAG, "********************* IL PLAYER E STATO MESSO IN PLAY " + numPlay + " volte");
        //    Log.d(TAG, "********************* IL PLAYER E STATO MESSO IN PAUSE " + numPause + " volte");
        //  Log.d(TAG, "********************* Il Player è andato in RESTART  "    + numPlay + " volte");
        //  Log.d(TAG, "********************* Il Player è andato in PAUSE    "    + numPause + " volte");
        //
        ArrayList<EventElement> eventList= new ArrayList<EventElement>();
        DeviceInfo deviceInfo = new DeviceInfo(c);
        Observer.event = new Event(c, deviceInfo, eventList);
//        EventElement e = new EventElement("SSCH", new SSCH());
//        event.eventList.add(e);
        // SSCH ssch = new SSCH();
        //ssch.session_id = "SSCH";
        // e.setPayload(ssch);
//        e = null;
//
//        e = new EventElement();
//        SSRCH ssrch = new SSRCH();
//        ssrch.session_id = "SSRCH";
//        e.setPayload(ssrch);
//        event.eventList.add(e);
//        e = null;
//
//        e = new EventElement();
//        SSVOD ssvod = new SSVOD();
//        ssvod.session_id = "SSVOD";
//        e.setPayload(ssvod);
//        event.eventList.add(e);
//        e = null;
//
//        e = new EventElement();
//        SSDVOD ssdvod = new SSDVOD();
//        ssdvod.session_id = "SSDVOD";
//        e.setPayload(ssdvod);
//        event.eventList.add(e);
//        e = null;
//
//        e = new EventElement();
//        SSPVOD sspvod = new SSPVOD();
//        sspvod.session_id = "SSPVOD";
//        e.setPayload(sspvod);
//        event.eventList.add(e);
//        e = null;
//
//        e = new EventElement();
//        SSLINK sslink = new SSLINK();
//        sslink.new_session_id = "SSLINK";
//        e.setPayload(sslink);
//        event.eventList.add(e);
//        e = null;
//
//        //    STD : Session sTreaming Download (valid also for VOD download) STP : Session sTreaming Pause
//        //    STR : Session sTreaming Restart
//        //    STCL : Session sTreaming Change Level
//        //    STRB : Session sTreaming ReBuferring
//
//
//        e = new EventElement();
//        STD std = new STD();
//        std.session_id = "STD";
//        e.setPayload(std);
//        event.eventList.add(e);
//        e = null;
//
//        e = new EventElement();
//        STP stp = new STP();
//        stp.session_id = "STP";
//        e.setPayload(stp);
//        event.eventList.add(e);
//        e = null;
//
//        e = new EventElement();
//        STR str = new STR();
//        str.session_id = "STR";
//        e.setPayload(str);
//        event.eventList.add(e);
//        e = null;
//
//
//        e = new EventElement();
//        STCL stcl = new STCL();
//        stcl.session_id = "STCL";
//        e.setPayload(stcl);
//        event.eventList.add(e);
//        e = null;
//
//        e = new EventElement();
//        STRB strb = new STRB();
//        strb.session_id = "STRB";
//        e.setPayload(strb);
//        event.eventList.add(e);
//        e = null;
//
////    SDP : Session Download Pause
////    SDR : Session Download Resume
////    SDD : Session Download Delete (download removed)
//
//
//        e = new EventElement();
//        SDP sdp = new SDP();
//        sdp.session_id = "SDP";
//        e.setPayload(sdp);
//        event.eventList.add(e);
//        e = null;
//
//        e = new EventElement();
//        SDR sdr = new SDR();
//        sdr.session_id = "SDR";
//        e.setPayload(sdr);
//        event.eventList.add(e);
//        e = null;
//
//        e = new EventElement();
//        SDD sdd = new SDD();
//        sdd.session_id = "SDD";
//        e.setPayload(sdd);
//        event.eventList.add(e);
//        e = null;
//
//        //  PLAYBACK
//        //  SPP : Session Playback Pause
//        //  SPR : Session Playback Restart
//
//        e = new EventElement();
//        SPP spp = new SPP();
//        spp.session_id = "SPP";
//        e.setPayload(spp);
//        event.eventList.add(e);
//        e = null;
//
//        e = new EventElement();
//        SPR spr = new SPR();
//        spr.session_id = "SPR";
//        e.setPayload(spr);
//        event.eventList.add(e);
//        e = null;
//
//
////        SC : Session Close
////        SDC : Session Download Completed  (reached download end)
////        SPC : Session Playback Close  (playback finished)
//        e = new EventElement();
//        SC sc = new SC();
//        sc.session_id = "SC";
//        e.setPayload(sc);
//        event.eventList.add(e);
//        e = null;
//
//        e = new EventElement();
//        SDC sdc = new SDC();
//        sdc.session_id = "SDC";
//        e.setPayload(sdc);
//        event.eventList.add(e);
//        e = null;
//
//        e = new EventElement();
//        SPC spc = new SPC();
//        spc.session_id = "SPC";
//        e.setPayload(spc);
//        event.eventList.add(e);
//        e = null;
//
//        e = new EventElement();
//        SE se = new SE("errore generico");
//        se.session_id = "SE";
//        e.setPayload(se);
//        event.eventList.add(e);
//        e = null;
        return event;
    }


}
