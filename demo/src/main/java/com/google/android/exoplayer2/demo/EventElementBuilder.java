package com.google.android.exoplayer2.demo;

import android.util.Log;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.demo.Model.ACI;
import com.google.android.exoplayer2.demo.Model.ASI;
import com.google.android.exoplayer2.demo.Model.Event;
import com.google.android.exoplayer2.demo.Model.EventElement;
import com.google.android.exoplayer2.demo.Model.SC;
import com.google.android.exoplayer2.demo.Model.SDC;
import com.google.android.exoplayer2.demo.Model.SDD;
import com.google.android.exoplayer2.demo.Model.SDP;
import com.google.android.exoplayer2.demo.Model.SDR;
import com.google.android.exoplayer2.demo.Model.SE;
import com.google.android.exoplayer2.demo.Model.SPC;
import com.google.android.exoplayer2.demo.Model.SPP;
import com.google.android.exoplayer2.demo.Model.SPR;
import com.google.android.exoplayer2.demo.Model.SSCH;
import com.google.android.exoplayer2.demo.Model.SSDVOD;
import com.google.android.exoplayer2.demo.Model.SSPVOD;
import com.google.android.exoplayer2.demo.Model.SSRCH;
import com.google.android.exoplayer2.demo.Model.SSVOD;
import com.google.android.exoplayer2.demo.Model.STCL;
import com.google.android.exoplayer2.demo.Model.STD;
import com.google.android.exoplayer2.demo.Model.STP;
import com.google.android.exoplayer2.demo.Model.STR;
import com.google.android.exoplayer2.demo.Model.STRB;
import com.google.android.exoplayer2.upstream.DataSpec;

import static com.google.android.exoplayer2.demo.Observer.getCurrentTimeStamp;


/**
 * Created by antoniodiraffaele on 28/11/17.
 */

public final class EventElementBuilder {

    private static final String TAG = "EventElementBuilder";
    public static ASI asi = null;
    public static ACI aci = null;
    static String sessionID =null;
    private static String originalSessionId;


    /*
     At application level will be generated two category of events:
     START : everytime the application starts.
     ASI : Application Startup Information
     CLOSE : everytime the application closes.
     ACI : Application Close Information
         */


    public static void ASI(String device_vendor, String device_model, String device_so, String codice_cliente) {
        asi = new ASI();
        asi.setStart_time(getCurrentTimeStamp());
        asi.setCodice_cliente(codice_cliente);
        asi.setDevice_model(device_model);
        asi.setDevice_so(device_so);
        asi.setDevice_vendor(device_vendor);
        asi.updateASIPayload();
    }

    public static void ACI(Event event, String currentTimeStamp) {
        aci = new ACI();
        aci.setStop_time(currentTimeStamp);
        aci.updateACIPayload();
        event.events_list.add(new EventElement("ACI", aci.getPayload()));
    }

    public static void SPP(String currentTimeStamp, Event event) {
        SPP spp;
        spp = new SPP();
        spp.setSession_id(sessionID);
        spp.setPause_time(currentTimeStamp);
        event.events_list.add(new EventElement("SPP", spp.getPayload()));
    }

    public static void SPR(String currentTimeStamp, Event event) {
        SPR spr;
        spr = new SPR();
        spr.setSession_id(sessionID);
        spr.setRestart_time(currentTimeStamp);
        event.events_list.add(new EventElement("SPR", spr.getPayload()));
    }


        /*
    DOWNLOAD : During the download of VOD chunks. (**)
    SDP : Session Download Pause
    SDR : Session Download Resume
    SDD : Session Download Delete (download removed)
    */

    public static void SDP(String pauseCause, String currentTimeStamp, Event event, SimpleExoPlayer player) {
        //SDP : Session Download Pause
        SDP sdp = new SDP();
        sdp.setSession_id(sessionID);
        sdp.setDownload_perc(String.valueOf(player.getBufferedPercentage()));
        sdp.setPause_cause(pauseCause);
        sdp.setPause_time(currentTimeStamp);
        // sdd.setDelete_time();

        sdp.updateSDPPayload();

        EventElement e = new EventElement("SDP", sdp.getPayload());
        event.events_list.add(e);
    }

    public static void SDR(String currentTimeStamp, Event event) {
        //SDR : Session Download Resume
        SDR sdr = new SDR();
        sdr.setSession_id(sessionID);
        sdr.setResume_time(currentTimeStamp);
        sdr.updateSDRPayload();
        EventElement e = new EventElement("SDR", sdr.getPayload());
        event.events_list.add(e);
    }

    public static void SDD(String currentTimeStamp, Event event) {
        //SDD : Session Download Delete (download removed)
        SDD sdd = new SDD();
        sdd.setSession_id(sessionID);
        sdd.setDelete_time(currentTimeStamp);
        sdd.updateSDDPayload();
        EventElement e = new EventElement("SDD", sdd.getPayload());
        event.events_list.add(e);
    }

    /*
    START  : Every time a session starts.
    SSCH   : Session Start CHannel
    SSRCH  : Session Start Restart Channel (**)
    SSVOD  : Session Start VOD
    SSDVOD : Session Start Download VOD (**) SSPVOD : Session Start Playback VOD (**)
    */

    public static SSCH SSCH(String currentTimeStamp) {
        SSCH ssch = null;
        ssch = new SSCH();
        ssch.setSession_id(sessionID=getSessionId());
        originalSessionId= sessionID;
        ssch.setStart_time(currentTimeStamp);
        //  ssch.setPlayback_start_time("");
        //  ssch.setBuffering_time("");
        ssch.setChannel_epg("Channel EPG");
      /*  ssch.setChannel_id(channelID);
        ssch.setChannel_name(channelName);
        ssch.setChannel_type(channelType);
        ssch.setDrm_time(drm_time);*/
        ssch.setHttp_response("Http Response");
        ssch.setIp_server("ipServer");
    /*    ssch.setManifest_uri("Maniest URI");
        ssch.setManifest_dwnl_byte("Maniest dwnl byte");
        ssch.setManifest_dwnl_time("Maniest dwnl time");
        */
        return ssch;
    }

    public static SSRCH SSRCH(String currentTimeStamp) {
        SSRCH ssrch = new SSRCH();
        ssrch.setSession_id(sessionID=getSessionId());
        ssrch.setOriginal_session_id(originalSessionId);
        ssrch.setStart_time(currentTimeStamp);
        //ssrch.setPlayback_start_time(currentTimeStamp);
        //ssrch.setBuffering_time(bufferingTimeStamp);
        ssrch.setDrm_time("drm_time");
        ssrch.setIp_server("IP_Server");
        ssrch.setManifest_uri("manifest_uri");
        ssrch.setManifest_dwnl_time("manifest_dwl_time");
        ssrch.setManifest_dwnl_byte("manifest_dwnl_byte");
        ssrch.setChannel_epg("Channel EPG");
 /*       ssrch.setChannel_id(channelID);
        ssrch.setChannel_name(channelName);
        ssrch.setChannel_type(channelType);*/
        ssrch.setHttp_response("Http Response");
        ssrch.setRestart_sec("restart_sec");
        return ssrch;
    }

    public static SSVOD SSVOD(String currentTimeStamp) {
        SSVOD ssvod = new SSVOD();
        ssvod.setSession_id(sessionID= getSessionId());
        ssvod.setStart_time(currentTimeStamp);
        return ssvod;
    }

    public static SSDVOD SSDVOD(String currentTimeStamp) {
        SSDVOD ssdvod = new SSDVOD();
        ssdvod.setSession_id(sessionID=getSessionId());
        ssdvod.setStart_time(currentTimeStamp);
        ssdvod.setDrm_time("drm_time");
     //   ssdvod.setBuffering_time(bufferingTimeStamp);
       // ssdvod.setIp_server(playerMonitor.serverURL);
        ssdvod.setHttp_response("Http_Response");
        ssdvod.setManifest_uri("Maniest URI");
        ssdvod.setManifest_dwnl_byte("Maniest dwnl byte");
        ssdvod.setManifest_dwnl_time("Maniest dwnl time");
       // ssdvod.setOffer_id(vodID);
     //   ssdvod.setAsset_title(VODTitle);
       // ssdvod.setAsset_source(assetPath);

       // ssdvod.updateSSDVODPayload();
      //  event.events_list.add(new EventElement("SSDVOD", ssdvod.getPayload()));
        return ssdvod;
    }

    public static SSPVOD SSPVOD(String currentTimeStamp) {
        SSPVOD sspvod = new SSPVOD();
        sspvod.setSession_id(sessionID=getSessionId());
        sspvod.setOriginal_session_id("originalSessionId");
        sspvod.setPlayback_start_time(currentTimeStamp);
        sspvod.setOffer_id("vodID");
        sspvod.setAsset_title("VODTitle");
        sspvod.setAsset_source("assetPath");
        sspvod.setDelay_time_sec("delay_time_sec");
        return sspvod;

    }

    public static String getSessionId() {
        sessionID = String.valueOf(System.currentTimeMillis());
        //sessionID = sessionID + deviceInfo.getDev_id();
        Log.i(TAG, "**************************  questo è il session ID " + sessionID);
        return sessionID;
        //  sessionID=sessionID +
    }


      /*
    STREAMING : During the streaming.
    STD : Session sTreaming Download (valid also for VOD download) STP : Session sTreaming Pause
    STR : Session sTreaming Restart
    STCL : Session sTreaming Change Level
    STRB : Session sTreaming ReBuferring
    */

    public static void STRB(String currentTimeStamp, Event event) {
        STRB strb = new STRB();
        strb.setSession_id(sessionID);
        strb.setRebuffering_start_time(getCurrentTimeStamp());
        strb.setRebuffering_end_time("");

        strb.updateSTRBPayload();
        if (event != null) {
            event.events_list.add(new EventElement("STRB", strb.getPayload()));
        } else {
            Log.d(TAG, "****************** evento nullo ");
        }
    }

    public static void SessionsTreamingChangeLevel(String currentTimeStamp, Event event, Format trackFormat,String oldBitRate) {
        STCL stcl = new STCL();
        stcl.setSession_id(sessionID);
        if (oldBitRate != null) {
            stcl.setBitrate_from(oldBitRate);
        } else {
            stcl.setBitrate_from("");
        }
        stcl.setBitrate_to(String.valueOf(trackFormat.bitrate));
        oldBitRate = String.valueOf(trackFormat.bitrate);

        stcl.updateSTCLPayload();
        event.events_list.add(new EventElement("STCL", stcl.getPayload()));
    }

    public static void STR(String currentTimeStamp, Event event) {
        STR str = new STR();
        str.setSession_id(sessionID);
        str.setRestart_time(currentTimeStamp);

        str.updateSTRPayload();
        event.events_list.add(new EventElement("STR", str.getPayload()));
    }

    public static void STP(String currentTimeStamp, Event event) {
        STP stp = new STP();
        stp.setSession_id(sessionID);
        stp.setPause_time(currentTimeStamp);

        stp.updateSTPPayload();
        event.events_list.add(new EventElement("STP", stp.getPayload()));
    }

    public static void STD(String currentTimeStamp, Event event, DataSpec dataSpec, int dataType, int trackType, Format trackFormat,
                     int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs,
                     long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded, String assetType) {

        STD std = new STD();
        std.setSession_id(sessionID);
        if (trackFormat != null) {
            std.setLayer("Layer : " + String.valueOf(trackFormat.bitrate));
        }
        std.setDwnl_byte("Dwnl_byte : " + String.valueOf(bytesLoaded));
        std.setDwnl_time("Dwnl_time : " + String.valueOf(loadDurationMs));

        //secondi rimanenti del video già bufferizzato
        std.setBuffer_size("Buffer_size : " + String.valueOf(bytesLoaded));

        //Chunk info totali
        std.setChunk_index("Chunk_index : " + String.valueOf(dataSpec.position) + "   absolute stream position    " + dataSpec.absoluteStreamPosition);
        std.setChunk_type("Chunk_type : " + String.valueOf(dataType) + " - assetType : " + assetType);
        std.setChunk_uri("Chunk_uri : " + dataSpec.uri.toString());

        //Not present
        std.setFps_decoded("Fps_decoded : ");
        std.setResponse_time("Response Time");
        std.setIp_server("ipServer");
        std.setHttp_response("200");

        /*
        std.setSession_id(sessionID);
        std.setBuffer_size("");
        std.setChunk_index("");
        std.setChunk_type("");
        std.setChunk_uri("");
        std.setDwnl_byte("");
        std.setDwnl_time("");
        std.setFps_decoded("");
        std.setLayer("");
        std.setResponse_time("");
        */

        std.updateSTDPayload();
        event.events_list.add(new EventElement("STD", std.getPayload()));
    }

    /*
    ERROR : Everytime an Error is raised.
    SE : Session Error
    */

    public static void SessionError(String type,Event event) {

        SE se = new SE();
        se.setErrorType(type);
        se.setSession_id(sessionID);


        se.updateSEPayload();
        EventElement e = new EventElement("Session Error", se.getPayload());
        event.events_list.add(e);
    }

    /*
    CLOSE : When a session finishes (also if is caused by an error that can’t permit streaming anymore).
    SC  : Session Close
    SDC : Session Download Completed (**) (reached download end)
    SPC : Session Playback Close (**) (playback finished)
    */

    public static void SessionClose(String currentTimeStamp, Event event) {
        SC sc = new SC();
        sc.setSession_id(sessionID);
        sc.setClosing_time(getCurrentTimeStamp());
        sc.updateSCPayload();
        event.events_list.add(new EventElement("SC", sc.getPayload()));
    }

    public static void SessionDownloadCompleted(String currentTimeStamp, Event event) {
        //Session downloaded completed
        SDC sdc = new SDC();
        sdc.setSession_id(sessionID);
        sdc.setCompleted_time(currentTimeStamp);

        sdc.updateSDCPayload();

        event.events_list.add(new EventElement("SDC", sdc.getPayload()));
    }

    public static void SessionPlaybackClose(String currentTimeStamp,Event event, SimpleExoPlayer player) {
        SPC spc = new SPC();
        spc.setSession_id(sessionID);
        spc.setClosing_time(currentTimeStamp);
        spc.setViewving_perc(String.valueOf(player.getBufferedPercentage()));
        spc.updateSPCPayload();

        event.events_list.add(new EventElement("SPC", spc.getPayload()));
        Log.i(TAG, "************   percentage " + String.valueOf(player.getBufferedPercentage()));
    }

}











// SSVOD



// ssvod.setDrm_time("drm_time");
//   ssvod.setBuffering_time();
      /*  ssvod.setManifest_uri("manifest_uri");
        ssvod.setManifest_dwnl_time("manifest_dwl_time");
        ssvod.setManifest_dwnl_byte("manifest_dwnl_byte");
        ssvod.setIp_server("IP_Server");
        ssvod.setHttp_response("Http Response");*/
//  ssvod.setPlayback_start_time(currentTimeStamp);
  /*      ssvod.setOffer_id("vodID");
        ssvod.setAsset_title(VODTitle);
        ssvod.setAsset_type(assetType);
        ssvod.setAsset_source(assetPath);*/

// SSVOD (Session Start VOD)

// drm_time
//        yyyy-MM-ddTHH:mm:ss.SSS±[hhmm] Acquire licenze start timestamp
//
// buffering_time
//        yyyy-MM-ddTHH:mm:ss.SSS±[hhmm] Buffering start timestamp
//        The time that the first chunk is downloaded
//
// playback_start_time
//        yyyy-MM-ddTHH:mm:ss.SSS±[hhmm]
//        Playback start timestamp
//        The time when the player shows the chunk to the user
// manifest_uri
//        Manifest uri
//
// manifest_dwnl_byte
//        Manifest download byte
//
// manifest_dwnl_time
//        Manifest Download time ms
// ip_server
//        Source Server IP - The IP of the edge server .
//                If not present, equal to the “Hostname” of manifest uri.
//
// http_response
//        Manifest uri http numerical response code. Es: 200, 504, ecc
//        In case of error, here will be indicated the error code AND the Device will generate ALSO SE event.
//
