package com.google.android.exoplayer2.demo.lib;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.demo.lib.Model.SDC;
import com.google.android.exoplayer2.demo.lib.Model.SDD;
import com.google.android.exoplayer2.demo.lib.Model.SDP;
import com.google.android.exoplayer2.demo.lib.Model.SDR;
import com.google.android.exoplayer2.demo.lib.Model.SSDVOD;
import com.google.android.exoplayer2.demo.lib.Model.STD;
import com.google.android.exoplayer2.upstream.DataSpec;

import static com.google.android.exoplayer2.demo.lib.EventElementBuilder.addToEventList;
import static com.google.android.exoplayer2.demo.lib.EventElementBuilder.getSessionId;
import static com.google.android.exoplayer2.demo.lib.EventElementBuilder.sessionID;
import static com.google.android.exoplayer2.demo.lib.Observer.getCurrentTimeStamp;

/**
 * Created by antoniodiraffaele on 30/11/17.
 */

public final class SessionDownload  {

    /*
    DOWNLOAD : During the download of VOD chunks. (**)
    SDP : Session Download Pause
    SDR : Session Download Resume
    SDD : Session Download Delete (download removed)

    */

    protected static void sessionStartDownloadVod(String drm_time, String buffering_time, String ip_server, String manifest_uri, String manifest_dwnl_byte, String manifest_dwnl_time,
                                                 String http_response, String offer_id, String asset_title, String asset_source) {

        SSDVOD ssdvod = new SSDVOD( drm_time, buffering_time, ip_server, manifest_uri, manifest_dwnl_byte,
                 manifest_dwnl_time, http_response, offer_id, asset_title, asset_source);

        ssdvod.setSessionId(sessionID = getSessionId());
        ssdvod.setStartTime(getCurrentTimeStamp());
    //REGION  ATTRIBUTE
        //ssdvod.setDrm_time("drm_time");
        //   ssdvod.setBuffering_time(bufferingTimeStamp);
        // ssdvod.setIp_server(playerMonitor.serverURL);
      //  ssdvod.setIp_server(ip_server);
       // ssdvod.setHttp_response("Http_Response");
        //ssdvod.setManifest_uri("Maniest URI");
        //ssdvod.setManifest_dwnl_byte("Maniest dwnl byte");
        //ssdvod.setManifest_dwnl_time("Maniest dwnl time");
        // ssdvod.setOffer_id(vodID);
        //   ssdvod.setAsset_title(VODTitle);
        // ssdvod.setAsset_source(assetPath);
        //ENDREGION

        ssdvod.update();
        addToEventList(ssdvod,"SSDVOD",1);
    }

    protected static SSDVOD sessionStartDownloadVod(String currentTimeStamp){
       SSDVOD ssdvod= new SSDVOD();
       ssdvod.setSessionId(sessionID=getSessionId());
       ssdvod.setStartTime(currentTimeStamp);
       return ssdvod;
    }

    public static void sessionDownloadPause(PauseCause pauseCause, String bufferedPercentage) {
        //SDP : Session Download Pause
        SDP sdp = new SDP();
        sdp.setSessionId(sessionID);
        sdp.setPause_time(getCurrentTimeStamp());
        sdp.setPause_cause(pauseCause.getPauseCode());
        sdp.setDownload_perc(bufferedPercentage);
        sdp.update();
        addToEventList(sdp, "SDP");
    }

    public static void sessionDownloadResume() {
        //SDR : Session Download Resume
        SDR sdr = new SDR();
        sdr.setSessionId(sessionID);
        sdr.setResume_time(getCurrentTimeStamp());
        sdr.update();
        addToEventList(sdr, "SDR");
    }

    public static void sessionDownloadDelete() {
        //SDD : Session Download Delete (download removed)
        SDD sdd = new SDD();
        sdd.setSessionId(sessionID);
        sdd.setDelete_time(getCurrentTimeStamp());
        sdd.update();
        addToEventList(sdd, "SDD");
    }

    public static void sessionDownloadCompleted( ) {
        //Session downloaded completed
        SDC sdc = new SDC();
        sdc.setSessionId(sessionID);
        sdc.setCompleted_time(getCurrentTimeStamp());
        sdc.update();
        addToEventList(sdc, "SDC");
    }

    public static void sessionsTreamingDownload(String ChunkUri, String ChunkIndex, String ChunkType, String bitrate, String Fps_decoded, String Http_response,
                                                String Response_time, String Ip_server, String loadDurationMs, String bytesLoaded) {
        STD std = new STD();
        std.setSessionId(sessionID);
        std.setLayer(bitrate);
        std.setDwnlByte(bytesLoaded);
        std.setDwnlTime(loadDurationMs);
        //secondi rimanenti del video già bufferizzato
        std.setBufferSize(bytesLoaded);
        std.setChunkIndex(ChunkIndex);
        std.setChunkType(ChunkType);
        std.setChunkUri(ChunkUri);
        std.setFpsDecoded(Fps_decoded);
        std.setResponseTime(Response_time);
        std.setIpServer(Ip_server);
        std.setHttpResponse(Http_response);
        std.update();
        addToEventList(std, "STD");
    }

    }
