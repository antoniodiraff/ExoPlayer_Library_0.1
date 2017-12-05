package com.google.android.exoplayer2.demo.lib;

import android.util.Log;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.demo.lib.Model.ACI;
import com.google.android.exoplayer2.demo.lib.Model.ASI;
import com.google.android.exoplayer2.demo.lib.Model.Event;
import com.google.android.exoplayer2.demo.lib.Model.EventElement;
import com.google.android.exoplayer2.demo.lib.Model.PayLoadElement;
import com.google.android.exoplayer2.demo.lib.Model.SC;
import com.google.android.exoplayer2.demo.lib.Model.SE;
import com.google.android.exoplayer2.demo.lib.Model.SPC;
import com.google.android.exoplayer2.demo.lib.Model.SPP;
import com.google.android.exoplayer2.demo.lib.Model.SPR;
import com.google.android.exoplayer2.demo.lib.Model.SSCH;
import com.google.android.exoplayer2.demo.lib.Model.SSPVOD;
import com.google.android.exoplayer2.demo.lib.Model.SSRCH;
import com.google.android.exoplayer2.demo.lib.Model.SSVOD;
import com.google.android.exoplayer2.demo.lib.Model.STCL;
import com.google.android.exoplayer2.demo.lib.Model.STD;
import com.google.android.exoplayer2.demo.lib.Model.STP;
import com.google.android.exoplayer2.demo.lib.Model.STR;
import com.google.android.exoplayer2.demo.lib.Model.STRB;
import com.google.android.exoplayer2.demo.lib.Model.Start;
import com.google.android.exoplayer2.upstream.DataSpec;

import static com.google.android.exoplayer2.demo.lib.Observer.event;


/**
 * Created by antoniodiraffaele on 28/11/17.
 */

public final class EventElementBuilder {

    // (**) SkygoPlus capabilities

    private static final String TAG = "EventElementBuilder";
    public static ASI asi = null;
    public static ACI aci = null;
    static String sessionID = null;
    private static String originalSessionId;

    /*
     At application level will be generated two category of events:

     START : everytime the application starts.
     ASI : Application Startup Information

     CLOSE : everytime the application closes.
     ACI : Application Close Information
     */

    public static void ApplicationStartupInfo(String device_vendor, String device_model, String device_so, String codice_cliente) {
        asi = new ASI();
        asi.setStart_time(Observer.getCurrentTimeStamp());
        asi.setCodice_cliente(codice_cliente);
        asi.setDevice_model(device_model);
        asi.setDevice_so(device_so);
        asi.setDevice_vendor(device_vendor);
        asi.updateASIPayload();
    }

    public static void ApplicationCloseInfo(Event event, String currentTimeStamp) {
        aci = new ACI();
        aci.setStop_time(currentTimeStamp);
        aci.updateACIPayload();
        event.events_list.add(new EventElement("ACI", aci.getPayload()));
    }


   /*
    START  : Every time a session starts.

    SSCH   : Session Start CHannel
    SSRCH  : Session Start Restart Channel (**)
    SSVOD  : Session Start VOD
    SSDVOD : Session Start Download VOD (**)
    SSPVOD : Session Start Playback VOD (**)
    */

    public static SSCH sessionStartCHannel(String currentTimeStamp, String channelID, String channelName, String channelEPG, String channelType, String ipServer) {
        SSCH ssch = new SSCH();
        ssch.setSessionId(sessionID = getSessionId());
        originalSessionId = sessionID;
        ssch.setStartTime(currentTimeStamp);
        ssch.setIpServer(ipServer);
        ssch.setChannelId(channelID);
        ssch.setChannelType(channelType);
        ssch.setChannelEpg(channelEPG);
        ssch.setChannelName(channelName);
        ssch.setDrmTime("");
        //   ssch.setHttp_response("Http Response");
        //   ssch.setIp_server("ipServer");
       /*    ssch.setManifest_uri("Maniest URI");
             ssch.setManifest_dwnl_byte("Maniest dwnl byte");
             ssch.setManifest_dwnl_time("Maniest dwnl time");
        */
        return ssch;
    }

    public static SSRCH sessionStartRestartCHannel(String timeStamp, String channelID, String channelName, String channelEPG, String channelType, String ipServer, String restartSec) {


        SSRCH ssrch = new SSRCH();
        ssrch.setSessionId(sessionID = getSessionId());
        ssrch.setOriginalSessionId(originalSessionId);
        ssrch.setStartTime(timeStamp);
        ssrch.setChannelEpg(channelEPG);
        ssrch.setChannelId(channelID);
        ssrch.setChannelName(channelName);
        ssrch.setChannelType(channelType);
        ssrch.setDrmTime("drm_time");
        ssrch.setIpServer(ipServer);
        ssrch.setRestartSec(restartSec);
        return ssrch;
    }

    public static SSVOD sessionStartVOD(String currentTimeStamp, String offerId,String assetTitle,String assetType, String assetSource,String ipServer) {
        SSVOD ssvod = new SSVOD();
        ssvod.setSessionId(sessionID = getSessionId());
        ssvod.setStartTime(currentTimeStamp);
        ssvod.setDrmTime("");
        ssvod.setIpServer(ipServer);
        ssvod.setOfferId(offerId);
        ssvod.setAssetTitle(assetTitle);
        ssvod.setAssetType(assetType);
        ssvod.setAssetSource(assetSource);
        return ssvod;
    }

    public static SSPVOD sessionStartPlaybackVOD(String currentTimeStamp, String originalSessionId,String vodID,String  VODTitle,String  assetPath) {
        SSPVOD sspvod = new SSPVOD();
        sspvod.setSessionId(sessionID = getSessionId());
        sspvod.setOriginalSessionId(originalSessionId);
        sspvod.setPlaybackStartTime(currentTimeStamp);
        sspvod.setOfferId(vodID);
        sspvod.setAssetTitle(VODTitle);
        sspvod.setAssetSource(assetPath);
        sspvod.setDelayTimeSec("delay_time_sec");
        return sspvod;

    }

    /*
    STREAMING : During the streaming.

    STD : Session sTreaming Download (valid also for VOD download) STP : Session sTreaming Pause
    STR : Session sTreaming Restart
    STCL : Session sTreaming Change Level
    STRB : Session sTreaming ReBuferring
    */

    public static STRB sessionsTreamingReBuferring(String currentTimeStamp) {
        STRB strb = new STRB();
        strb.setSessionId(sessionID);
        strb.setRebufferingStartTime(currentTimeStamp);
        strb.update();
        return strb;
    }

    public static void updateSTRB(STRB strb, String currentTimeStamp) {
        if (strb != null) {
            strb.setRebufferingEndTime(currentTimeStamp);
            strb.update();
            addToEventList(strb, "STRB");
        }
    }

    public static void sessionsTreamingChangeLevel(String currentTimeStamp, Format trackFormat, String oldBitRate) {
        STCL stcl = new STCL();
        stcl.setSessionId(sessionID);
        if (oldBitRate != null) {
            stcl.setBitrateFrom(oldBitRate);
        } else {
            stcl.setBitrateFrom("");
        }
        stcl.setBitrateTo(String.valueOf(trackFormat.bitrate));
        stcl.update();
        addToEventList(stcl, "STCL");
    }

    public static void sessionsTreamingRestart(String currentTimeStamp) {
        STR str = new STR();
        str.setSessionId(sessionID);
        str.setRestartTime(currentTimeStamp);
        str.update();
        addToEventList(str, "STR");
    }

    public static void sessionsTreamingPause(String currentTimeStamp) {
        STP stp = new STP();
        stp.setSessionId(sessionID);
        stp.setPauseTime(currentTimeStamp);
        stp.update();
        addToEventList(stp, "STP");
    }

    private static void sessionshTreamingDownload( DataSpec dataSpec, int dataType, int trackType, Format trackFormat,
                                                   int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs,
                                                   long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded, String assetType,String ipServer,String bufferSize) {
        STD std = new STD();
        std.setSessionId(sessionID);
        if (trackFormat != null) {
            std.setLayer(String.valueOf(trackFormat.bitrate));
        } else {
            std.setLayer("");
        }
        std.setDwnlByte(String.valueOf(bytesLoaded));
        std.setDwnlTime(String.valueOf(loadDurationMs));
        //secondi rimanenti del video già bufferizzato
        std.setBufferSize(bufferSize);
        //Chunk info totali
        std.setChunkIndex("");
        std.setChunkType("");
        std.setChunkUri(dataSpec.uri.toString());
        //Not present
        std.setFpsDecoded("");
        std.setResponseTime("");
        std.setIpServer(ipServer);
        std.setHttpResponse("200");

        std.update();
        addToEventList(std, "STD");
    }

    /*
    PLAYBACK : During the playback of downloaded VOD. (**)

    SPP : Session Playback Pause
    SPR : Session Playback Restart
     */

    public static void sessionPlaybackPause(String currentTimeStamp) {
        SPP spp = new SPP();
        spp.setSessionId(sessionID);
        spp.setPause_time(currentTimeStamp);
        addToEventList(spp, "SPP");
        // event.events_list.add(new EventElement("SPP", spp.getPayload()));
    }

    public static void sessionPlaybackRestart(String currentTimeStamp) {
        SPR spr = new SPR();
        spr.setSessionId(sessionID);
        spr.setRestart_time(currentTimeStamp);
        addToEventList(spr, "SPR");
        //  event.events_list.add(new EventElement("SPR", spr.getPayload()));
    }

    /*
    ERROR : Everytime an Error is raised.

    SE : Session Error
    */

    public static void sessionError(String error_text, String errorType, String error_code, String chunk_uri, String channel_id,
                                    String event_id, String vod_id, String player_version, String event_name, String error_message) {
        SE se = new SE();
        se.setSessionId(sessionID);
        se.setError_text(error_text);
        se.setErrorType(errorType);
        se.setError_code(error_code);
        se.setChunk_uri(chunk_uri);
        se.setChannel_id(channel_id);
        se.setEvent_id(event_id);
        se.setVod_id(vod_id);
        se.setPlayer_version(player_version);
        se.setEvent_name(event_name);
        se.setError_message(error_message);
        se.update();
        addToEventList(se, "SE");
    }

    /*
    CLOSE : When a session finishes (also if is caused by an error that can’t permit streaming anymore).

    SC  : Session Close
    SDC : Session Download Completed (**) (reached download end)
    SPC : Session Playback Close (**) (playback finished)
    */

    public static void sessionClose(String currentTimeStamp) {
        SC sc = new SC();
        sc.setSessionId(sessionID);
        sc.setClosing_time(Observer.getCurrentTimeStamp());
        sc.update();
        addToEventList(sc, "SC");
    }

    public static void sessionPlaybackClose(String currentTimeStamp, SimpleExoPlayer player) {
        SPC spc = new SPC();
        spc.setSessionId(sessionID);
        spc.setClosing_time(currentTimeStamp);
        spc.setViewving_perc("");
        spc.update();
        addToEventList(spc, "SPC");
        //  Log.i(TAG, "************   percentage " + String.valueOf(player.getBufferedPercentage()));
    }

    public static void updateStartEvent(Start startEvent, String name, String currentTimeStamp, int i) {
        if (startEvent != null) {
            startEvent.setPlaybackStartTime(currentTimeStamp);
            startEvent.update();
            addToEventList(startEvent, name, i);
        }
    }

    // add the EventElement to the queue

    public static void addToEventList(PayLoadElement payLoadElement, String name) {
        if (event != null) {
            event.events_list.add(new EventElement(name, payLoadElement.getPayload()));
        } else {
            Log.i(TAG, "*********    Null Event Object");
        }
    }

    public static void addToEventList(PayLoadElement payLoadElement, String name, int i) {
        if (event != null) {
            event.events_list.add(i, new EventElement(name, payLoadElement.getPayload()));
        } else {
            Log.i(TAG, "*********    Null Event Object");
        }
    }

    public static String getSessionId() {
        sessionID = String.valueOf(System.currentTimeMillis());
        //sessionID = sessionID + deviceInfo.getDev_id();
        Log.i(TAG, "**************************  questo è il session ID " + sessionID);
        return sessionID;
        //  sessionID=sessionID +
    }
}

//region SSVOD
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
//endregion