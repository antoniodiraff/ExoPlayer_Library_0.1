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
    SSDVOD : Session Start Download VOD (**) SSPVOD : Session Start Playback VOD (**)
    */

    public static SSCH sessionStartCHannel(String currentTimeStamp) {
        SSCH ssch = new SSCH();
        ssch.setSession_id(sessionID = getSessionId());
        originalSessionId = sessionID;
        ssch.setStart_time(currentTimeStamp);
        //  ssch.setPlayback_start_time("");
        //  ssch.setBuffering_time("");
        //ssch.setChannel_epg("Channel EPG");
      /*  ssch.setChannel_id(channelID);
        ssch.setChannel_name(channelName);
        ssch.setChannel_type(channelType);
        ssch.setDrm_time(drm_time);*/
      //  ssch.setHttp_response("Http Response");
     //   ssch.setIp_server("ipServer");
    /*    ssch.setManifest_uri("Maniest URI");
        ssch.setManifest_dwnl_byte("Maniest dwnl byte");
        ssch.setManifest_dwnl_time("Maniest dwnl time");
        */
        return ssch;
    }

    public static SSRCH sessionStartRestartCHannel(String currentTimeStamp) {
        SSRCH ssrch = new SSRCH();
        ssrch.setSession_id(sessionID = getSessionId());
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

    public static SSVOD sessionStartVOD(String currentTimeStamp) {
        SSVOD ssvod = new SSVOD();
        ssvod.setSession_id(sessionID = getSessionId());
        ssvod.setStart_time(currentTimeStamp);
        return ssvod;
    }

    public static SSPVOD sessionStartPlaybackVOD(String currentTimeStamp) {
        SSPVOD sspvod = new SSPVOD();
        sspvod.setSession_id(sessionID = getSessionId());
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

    public static STRB sessionsTreamingReBuferring(String currentTimeStamp) {
        STRB strb = new STRB();
        strb.setSession_id(sessionID);
        strb.setRebuffering_start_time(Observer.getCurrentTimeStamp());
        strb.setRebuffering_end_time("");
        strb.update();
        return strb;
    }

    public static void updateSTRB(STRB strb, String currentTimeStamp) {
        if (strb != null) {
            strb.setRebuffering_end_time(currentTimeStamp);
            strb.update();
            addToEventList(strb, "STRB");
         /*   if (event != null) {
                event.events_list.add(new EventElement("STRB", strb.getPayload()));
            } else {
                Log.d(TAG, "****************** evento nullo ");
            }*/
        }
    }

    public static void sessionsTreamingChangeLevel(String currentTimeStamp, Format trackFormat, String oldBitRate) {
        STCL stcl = new STCL();
        stcl.setSession_id(sessionID);
        if (oldBitRate != null) {
            stcl.setBitrate_from(oldBitRate);
        } else {
            stcl.setBitrate_from("");
        }
        stcl.setBitrate_to(String.valueOf(trackFormat.bitrate));
        stcl.update();
        addToEventList(stcl, "STCL");
        // event.events_list.add(new EventElement("STCL", stcl.getPayload()));
    }

    public static void sessionsTreamingRestart(String currentTimeStamp) {
        STR str = new STR();
        str.setSession_id(sessionID);
        str.setRestart_time(currentTimeStamp);
        str.update();
        addToEventList(str, "STR");
        // event.events_list.add(new EventElement("STR", str.getPayload()));
    }

    public static void sessionsTreamingPause(String currentTimeStamp) {
        STP stp = new STP();
        stp.setSession_id(sessionID);
        stp.setPause_time(currentTimeStamp);
        stp.update();
        addToEventList(stp, "STP");

        //event.events_list.add(new EventElement("STP", stp.getPayload()));
    }

    protected static void sessionsTreamingDownload(String currentTimeStamp, DataSpec dataSpec, int dataType, int trackType, Format trackFormat,
                                                int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs,
                                                long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded, String assetType) {

        STD std = new STD();
        std.setSession_id(sessionID);
        if (trackFormat != null) {
            std.setLayer( String.valueOf(trackFormat.bitrate));
        }
        std.setDwnl_byte(String.valueOf(bytesLoaded));
        std.setDwnl_time(String.valueOf(loadDurationMs));

        //secondi rimanenti del video già bufferizzato
        std.setBuffer_size(String.valueOf(bytesLoaded));

        //Chunk info totali
        std.setChunk_index("Chunk_index : " + String.valueOf(dataSpec.position) + "   absolute stream position    " + dataSpec.absoluteStreamPosition);
        std.setChunk_type("Chunk_type : " + String.valueOf(dataType) + " assetType" + assetType);
        std.setChunk_uri(dataSpec.uri.toString());

        //Not present
        std.setFps_decoded("");
        std.setResponse_time("");
        std.setIp_server("");
        std.setHttp_response("");

        std.update();
        addToEventList(std, "STD");


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


        // event.events_list.add(new EventElement("STD", std.getPayload()));
    }


    // Playback

    public static void sessionPlaybackPause(String currentTimeStamp) {
        SPP spp = new SPP();
        spp.setSession_id(sessionID);
        spp.setPause_time(currentTimeStamp);
        addToEventList(spp, "SPP");
        // event.events_list.add(new EventElement("SPP", spp.getPayload()));
    }

    public static void sessionPlaybackRestart(String currentTimeStamp) {
        SPR spr = new SPR();
        spr.setSession_id(sessionID);
        spr.setRestart_time(currentTimeStamp);
        addToEventList(spr, "SPR");
        //  event.events_list.add(new EventElement("SPR", spr.getPayload()));
    }




    /*
    ERROR : Everytime an Error is raised.
    SE : Session Error
    */

    public static void sessionError(String error_text,String errorType, String error_code,String chunk_uri,String channel_id,
                                    String event_id, String vod_id,String player_version, String event_name, String error_message) {
        SE se = new SE();
        se.setSession_id(sessionID);
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
        sc.setSession_id(sessionID);
        sc.setClosing_time(Observer.getCurrentTimeStamp());
        sc.update();
        addToEventList(sc, "SC");
    }

    public static void sessionPlaybackClose(String currentTimeStamp, SimpleExoPlayer player) {
        SPC spc = new SPC();
        spc.setSession_id(sessionID);
        spc.setClosing_time(currentTimeStamp);
        spc.setViewving_perc("");
        spc.update();
        addToEventList(spc, "SPC");
      //  Log.i(TAG, "************   percentage " + String.valueOf(player.getBufferedPercentage()));
    }


    //region UPDATES
/*    public static void updateSSDVOD(SSDVOD ssdvod, String currentTimeStamp, Event event, int i) {
        if(ssdvod!=null){
            ssdvod.setPlayback_start_time(currentTimeStamp);
            ssdvod.update();
            event.events_list.add(i, new EventElement("SSDVOD", ssdvod.getPayload()));
        }
    }

    public static  void updateSSRCH(SSRCH ssrch, String currentTimeStamp, Event event,int i) {
        if (ssrch != null) {
            ssrch.setPlayback_start_time(currentTimeStamp);
            ssrch.update();
            event.events_list.add(i, new EventElement("SSRCH", ssrch.getPayload()));
        }
    }

    public static  void updateSSVOD(SSVOD ssvod, String currentTimeStamp, Event event, int i) {
        if (ssvod != null) {
            ssvod.setPlayback_start_time(currentTimeStamp);
            ssvod.updateSSVODPayload();
            event.events_list.add(i, new EventElement("SSVOD", ssvod.getPayload()));
        }
    }

    public static  void updateSSCH(SSCH ssch , String currentTimeStamp,Event event, int i) {
        if (ssch != null) {
            ssch.setPlayback_start_time(currentTimeStamp);
            ssch.update();
            event.events_list.add(i, new EventElement("SSCH", ssch.getPayload()));
        }
    }

    public static  void updateSSPVOD(SSPVOD sspvod, String currentTimeStamp,Event event, int i) {

        if (sspvod != null) {
            sspvod.setPlayback_start_time(currentTimeStamp);
            sspvod.update();
            event.events_list.add(i, new EventElement("SSPVOD", sspvod.getPayload()));
        }
    }

    */
//endregion

    public static void updateStartEvent(Start startEvent, String name, String currentTimeStamp, int i) {
        if (startEvent != null) {
            startEvent.setPlayback_start_time(currentTimeStamp);
            startEvent.update();
            addToEventList(startEvent, name, i);
        }
    }


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


}

    // if "event" is not static



  /*  private static void addToEventList(PayLoadElement startEvent, String name, int i, Event event) {
        if (event!=null) {
            event.events_list.add(i, new EventElement(name, startEvent.getPayload()));
        }else {
            Log.i(TAG, "*********    Null Event Object");
        }
    }

    private static void addToEventList(PayLoadElement payLoadElement, String name,Event event) {
        if (event!=null) {
            event.events_list.add(new EventElement(name, payLoadElement.getPayload()));
        }else{
            Log.i(TAG, "*********    Null Event Object");
        }
    }

*/





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