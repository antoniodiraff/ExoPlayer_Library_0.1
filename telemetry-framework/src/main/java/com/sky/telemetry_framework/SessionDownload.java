package com.sky.telemetry_framework;

import android.content.Context;
import android.content.SharedPreferences;

import com.sky.telemetry_framework.model.*;
import com.sky.telemetry_framework.model.SSDVOD;

import static com.sky.telemetry_framework.EventElementBuilder.addToEventList;
import static com.sky.telemetry_framework.EventElementBuilder.getSessionId;
import static com.sky.telemetry_framework.EventElementBuilder.sessionID;
import static com.sky.telemetry_framework.Observer.getCurrentTimeStamp;

/**
 * Created by antoniodiraffaele on 30/11/17.
 */

public final class SessionDownload {


    /*
    DOWNLOAD : During the download of VOD chunks. (**)
    SDP : Session Download Pause
    SDR : Session Download Resume
    SDD : Session Download Delete (download removed)

    */

   /* protected static void sessionStartDownloadVod(String drm_time, String buffering_time, String ip_server, String manifest_uri, String manifest_dwnl_byte, String manifest_dwnl_time,
                                                  String http_response, String offer_id, String asset_title, String asset_source) {

        SSDVOD ssdvod = new SSDVOD(drm_time, buffering_time, ip_server, manifest_uri, manifest_dwnl_byte,
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
        addToEventList(ssdvod, "SSDVOD", 1);
    }
*/

    protected static SSDVOD sessionStartDownloadVod(Context c, String currentTimeStamp, String offerID, String assetTitle, String assetSource, String ipServer) {
        SSDVOD ssdvod = new SSDVOD();
        ssdvod.setSessionId(sessionID = getSessionId());

        SharedPreferences sharedPreferences = c.getApplicationContext().getSharedPreferences("sessionID_SSDVOD", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("originalSessionIdSSDVOD", sessionID);
        editor.commit();

        ssdvod.setStartTime(currentTimeStamp);
        ssdvod.setIpServer(ipServer);
        ssdvod.setOfferId(offerID);
        ssdvod.setAssetTitle(assetTitle);
        ssdvod.setAssetSource(assetSource);
        return ssdvod;
    }

    public static void sessionDownloadPause(PauseCause pauseCause, String bufferedPercentage) {
        //SDP : Session Download Pause
        SDP sdp = new SDP();
        sdp.setSessionId(sessionID);
        sdp.setPauseTime(getCurrentTimeStamp());
        sdp.setPauseCause(pauseCause.getPauseCode());
        sdp.setDownloadPerc(bufferedPercentage);
        sdp.update();
        addToEventList(sdp, "SDP");
    }

    public static void sessionDownloadResume() {
        //SDR : Session Download Resume
        SDR sdr = new SDR();
        sdr.setSessionId(sessionID);
        sdr.setResumeTime(getCurrentTimeStamp());
        sdr.update();
        addToEventList(sdr, "SDR");
    }

    public static void sessionDownloadDelete() {
        //SDD : Session Download Delete (download removed)
        SDD sdd = new SDD();
        sdd.setSessionId(sessionID);
        sdd.setDeleteTime(getCurrentTimeStamp());
        sdd.update();
        addToEventList(sdd, "SDD");
    }

    public static void sessionDownloadCompleted() {
        //Session downloaded completed
        SDC sdc = new SDC();
        sdc.setSessionId(sessionID);
        sdc.setCompleted_time(getCurrentTimeStamp());
        sdc.update();
        addToEventList(sdc, "SDC");
    }

    public static void sessionsTreamingDownload(String ChunkUri, String ChunkIndex, String ChunkType, String bitrate, String FpsDecoded, String httpResponse,
                                                String ResponseTime, String ipServer, String dwnTime, String dwnlByte,String bufferSize) {
        STD std = new STD();
        std.setSessionId(sessionID);
        std.setLayer(bitrate);
        std.setDwnlByte(dwnlByte);
        std.setDwnlTime(dwnTime);
        //secondi rimanenti del video già bufferizzato
        std.setBufferSize(bufferSize);
        std.setChunkIndex(ChunkIndex);
        std.setChunkType(ChunkType);
        std.setChunkUri(ChunkUri);
        std.setFpsDecoded(FpsDecoded);
        std.setResponseTime(ResponseTime);
        std.setIpServer(ipServer);
        std.setHttpResponse(httpResponse);
        std.update();
        addToEventList(std, "STD");
    }

}
