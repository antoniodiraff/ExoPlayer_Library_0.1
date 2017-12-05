package com.google.android.exoplayer2.demo.lib;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;
import android.view.Surface;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.demo.lib.Model.Event;
import com.google.android.exoplayer2.demo.lib.Model.EventElement;
import com.google.android.exoplayer2.demo.lib.Model.SSCH;
import com.google.android.exoplayer2.demo.lib.Model.SSDVOD;
import com.google.android.exoplayer2.demo.lib.Model.SSPVOD;
import com.google.android.exoplayer2.demo.lib.Model.SSRCH;
import com.google.android.exoplayer2.demo.lib.Model.SSVOD;
import com.google.android.exoplayer2.demo.lib.Model.STRB;
import com.google.android.exoplayer2.demo.lib.Model.Start;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataRenderer;
import com.google.android.exoplayer2.metadata.emsg.EventMessage;
import com.google.android.exoplayer2.metadata.id3.ApicFrame;
import com.google.android.exoplayer2.metadata.id3.CommentFrame;
import com.google.android.exoplayer2.metadata.id3.GeobFrame;
import com.google.android.exoplayer2.metadata.id3.Id3Frame;
import com.google.android.exoplayer2.metadata.id3.PrivFrame;
import com.google.android.exoplayer2.metadata.id3.TextInformationFrame;
import com.google.android.exoplayer2.metadata.id3.UrlLinkFrame;
import com.google.android.exoplayer2.source.AdaptiveMediaSourceEventListener;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.video.VideoRendererEventListener;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static com.google.android.exoplayer2.demo.lib.EventElementBuilder.asi;
import static com.google.android.exoplayer2.demo.lib.EventElementBuilder.sessionClose;
import static com.google.android.exoplayer2.demo.lib.EventElementBuilder.sessionError;
import static com.google.android.exoplayer2.demo.lib.EventElementBuilder.sessionPlaybackClose;
import static com.google.android.exoplayer2.demo.lib.EventElementBuilder.sessionPlaybackPause;
import static com.google.android.exoplayer2.demo.lib.EventElementBuilder.sessionPlaybackRestart;
import static com.google.android.exoplayer2.demo.lib.EventElementBuilder.sessionStartCHannel;
import static com.google.android.exoplayer2.demo.lib.EventElementBuilder.sessionStartPlaybackVOD;
import static com.google.android.exoplayer2.demo.lib.EventElementBuilder.sessionStartRestartCHannel;
import static com.google.android.exoplayer2.demo.lib.EventElementBuilder.sessionStartVOD;
import static com.google.android.exoplayer2.demo.lib.EventElementBuilder.sessionsTreamingPause;
import static com.google.android.exoplayer2.demo.lib.EventElementBuilder.sessionsTreamingReBuferring;
import static com.google.android.exoplayer2.demo.lib.EventElementBuilder.sessionsTreamingRestart;
import static com.google.android.exoplayer2.demo.lib.EventElementBuilder.updateSTRB;
import static com.google.android.exoplayer2.demo.lib.EventElementBuilder.updateStartEvent;
import static com.google.android.exoplayer2.demo.lib.SessionDownload.sessionStartDownloadVod;
import static com.google.android.exoplayer2.demo.lib.SessionDownload.sessionsTreamingDownload;


public final class Observer implements Player.EventListener, AudioRendererEventListener, VideoRendererEventListener,
        AdaptiveMediaSourceEventListener, ExtractorMediaSource.EventListener, DefaultDrmSessionManager.EventListener,
        MetadataRenderer.Output {

    private static final String TAG = "Observer";
    private static final int MAX_TIMELINE_ITEM_LINES = 3;
    private static final NumberFormat TIME_FORMAT;

    static {
        TIME_FORMAT = NumberFormat.getInstance(Locale.US);
        TIME_FORMAT.setMinimumFractionDigits(2);
        TIME_FORMAT.setMaximumFractionDigits(2);
        TIME_FORMAT.setGroupingUsed(false);
    }

    private final MappingTrackSelector trackSelector;
    private final Timeline.Window window;
    private final Timeline.Period period;
    private final long startTimeMs;
    private  String user_extid;
    private String source;
    private int dequeueingIntervalTime;

    //*** MODEL initialization ***
    public SSCH ssch = null;
    public SSRCH ssrch = null;
    public STRB strb = null;
    public SSVOD ssvod = null;
    public SSPVOD sspvod = null;
    public SSDVOD ssdvod = null;

    // *** LOCAL variables ***
    public static Timer t;
    public String oldBitRate;
    public Context c;
    private String channelEPG;

    public String getIpServer() {
        return ipServer;
    }

    public void setIpServer(String ipServer) {
        this.ipServer = ipServer;
    }

    public static Event event;
    public String drm_time = "";
    public String ipServer="";

    // *** VARIABLES needed 4 constructor ***
    static String URL = "";
    boolean isLive = false;
    boolean isLocalFile = false;
    boolean isRestart = false;
    boolean isVod = false;

    String originalSessionId = "";
    String restartSec = "";
    boolean isFree;
    String channelName = "";
    String channelID = "";
    String channelType = "";
    String offerID = "";
    String assetTitle = "";
    String assetType = "";
    String assetSource = "";
    public Timer _t;
    SimpleExoPlayer player;
    boolean isFirstTime_Download = true;
    boolean play = true;
    boolean isTheFirstTime_startEvent = true;
    boolean isFirstTime_OnLoadedCompleted = true;
    boolean isTheFirstTime_Buffering = true;
    boolean isTheFirstTime_Ready = true;
    private boolean isDownload;
    int i;
    private StreamingType streamingType;


    public Observer(final MappingTrackSelector trackSelector, boolean enableTrace, String serverURL, int serverTimeout, String userAgent, String dev_id, String user_extid,
                    String source, int dequeueingIntervalTime, Context c, StreamingType streamingType, String originalSessionId, String restartSec, boolean isFree,
                    String channelName, String channelID, String channelEPG,
                    String offerID, String assetTitle, String assetType, String assetSource) {

        this.c = c;
        this.source=source;
        this.user_extid=user_extid;
        this.dequeueingIntervalTime = dequeueingIntervalTime;
        this.URL = serverURL;
        this.streamingType = streamingType;
        selectStreamingType(streamingType);
        this.originalSessionId = originalSessionId;
        this.restartSec = restartSec;
        this.isFree = isFree;

        this.channelName = channelName;
        this.channelID = channelID;
        if(isFree){this.channelType = "F";} else{this.channelType="C";}
        this.channelEPG=channelEPG;

        this.offerID = offerID;
        this.assetTitle = assetTitle;
        this.assetType = assetType;
        this.assetSource = assetSource;

        this.trackSelector = trackSelector;
        window = new Timeline.Window();
        period = new Timeline.Period();
        startTimeMs = SystemClock.elapsedRealtime();
    }

    // MY LIB

    private void idle_CallBack(String currentTimeStamp) {
        Log.d(TAG, " ********** idle_CallBack");
    }

    private void buffering_CallBack(String currentTimeStamp) {
        Log.d(TAG, " ********** buffering_CallBack");
        if (isTheFirstTime_Buffering) {
            switch (streamingType) {
                case Live:
                    ssch.setBufferingTime(currentTimeStamp);
                    break;
                case Restart:
                    ssrch.setBufferingTime(currentTimeStamp);
                    break;
                case Vod:
                    ssvod.setBufferingTime(currentTimeStamp);
                    break;
                case LocalFile:
                    sspvod.setBufferingTime(currentTimeStamp);
                    break;
                case Download:
                    ssdvod.setBufferingTime(currentTimeStamp);
                default:
                    break;
            }
            isTheFirstTime_Buffering = false;
        } else {
            if (event != null && isLive) {
                strb = sessionsTreamingReBuferring(currentTimeStamp);
            }
        }
    }

    private void ready_CallBack(String currentTimeStamp, boolean play, StreamingType streamingType) {

        Log.d(TAG, " ********** ready_CallBack" );
        Log.d(TAG, " ********** IPSERVER : " + ipServer);
        updateSTRB(this.strb, currentTimeStamp);

        String name = null;
        Start startEvent=null;
        // set the playback Start Time
        if (isTheFirstTime_Ready) {
            startTimer();
            switch (streamingType) {
                case Live:
                    startEvent= this.ssch;
                    name = "SSCH";
                    break;
                case LocalFile:
                    startEvent= this.sspvod;
                    name = "SSPVOD";
                    break;
                case Vod:
                    startEvent= this.ssvod;
                    name = "SSVOD";
                    break;
                case Restart:
                    startEvent= this.ssrch;
                    name = "SSRCH";
                    break;
                case Download:
                    startEvent= this.ssdvod;
                    name ="SSDVOD";
                    break;
                default:
                    break;
            }
            updateStartEvent(startEvent, name, currentTimeStamp, i);
            isTheFirstTime_Ready = false;
        } else {
            if (play) {
                if (isLive) {
                    sessionsTreamingRestart(getCurrentTimeStamp());
                } else if (isLocalFile) {
                    sessionsTreamingPause(getCurrentTimeStamp());
                }
                play = false;
            } else if (!play) {
                // pause();
                if (isLive) {
                    sessionsTreamingPause(getCurrentTimeStamp());
                } else if (isLocalFile) {
                    sessionPlaybackPause(getCurrentTimeStamp());
                }
            }
        }
    }

    private void ended_CallBack(String currentTimeStamp) {
        Log.wtf(TAG, " ********** ended_CallBack");
        terminate(currentTimeStamp);
        sendEventQueue();
        t.cancel();
    }

    //region UPDATE deprecated
/*
    private void updateSSDVOD( String currentTimeStamp, Event event) {
        if(ssdvod!=null){
            ssdvod.setPlayback_start_time(currentTimeStamp);
            ssdvod.updateSSDVODPayload();
            event.events_list.add(i, new EventElement("SSDVOD", ssdvod.getPayload()));
        }
    }*/

/*    private void updateSSRCH(String currentTimeStamp, Event event) {
        if (ssrch != null) {
            ssrch.setPlayback_start_time(currentTimeStamp);
            ssrch.updateSSRCHPayload();
            event.events_list.add(i, new EventElement("SSRCH", ssrch.getPayload()));
        }
    }

    private void updateSSVOD(String currentTimeStamp, Event event) {
        if (ssvod != null) {
            ssvod.setPlayback_start_time(currentTimeStamp);
            ssvod.updateSSVODPayload();
            event.events_list.add(i, new EventElement("SSVOD", ssvod.getPayload()));
        }
    }

    private void updateSSCH(String currentTimeStamp,Event event) {
        if (ssch != null) {
            ssch.setPlayback_start_time(currentTimeStamp);
            ssch.updateSSCHPayload();
            event.events_list.add(i, new EventElement("SSCH", ssch.getPayload()));
        }
    }

    private void updateSSPVOD(String currentTimeStamp,Event event) {

        if (sspvod != null) {
        sspvod.setPlayback_start_time(currentTimeStamp);
        sspvod.updateSSPVODPayload();
        event.events_list.add(i, new EventElement("SSPVOD", sspvod.getPayload()));
    }
    }*/

   /* private void SPR(String currentTimeStamp, Event event) {
    }*/
//endregion



    /*
    Listeners callbacks
      */
    private void drmKeysLoaded_callback(String drm_time) {
        Log.d(TAG, " ********** drmKeysLoaded_callback");

        if (ssch != null) {
            ssch.setDrmTime(drm_time);
        } else if (ssrch != null) {
            ssrch.setDrmTime(drm_time);
        } else if (ssvod != null) {
            ssvod.setDrmTime(drm_time);
        }
       /* else if (ssdvod != null) {
            ssdvod.setDrm_time(drm_time);
        }*/
    }

    private void onDownstreamFormatChanged_callback(Event event, String currentTimeStamp, Format trackFormat) {

        if (event != null && (isLive || isVod)) {
            EventElementBuilder.sessionsTreamingChangeLevel(currentTimeStamp, trackFormat, oldBitRate);
        }
        oldBitRate = String.valueOf(trackFormat.bitrate);
    }

    private void onLoadCompletedCallback(String currentTimeStamp, Event event, DataSpec dataSpec, int dataType, int trackType, Format trackFormat,
                                         int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs,
                                         long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded) {
        if (trackFormat != null) {
            Log.d(TAG, " ********** onLoadCompletedCallback" + trackFormat.toString());
        }
        String bitrate="";
        String chunkUri="";
        String chunkIndex="";
        String chunkType="";
        String responseTime="";
        String bufferSize="";
        String dwnTime="";
        String dwnlByte="";

        Start startEvent = null;
        if (isFirstTime_OnLoadedCompleted) {
            isFirstTime_OnLoadedCompleted = false;
            if (isLive) {startEvent = ssch;} else if (isRestart) {startEvent = ssrch;}else if(isVod){startEvent=ssvod;}
            if (startEvent != null) {
                startEvent.setManifestUri(dataSpec.uri.toString());
                startEvent.setManifestDwnlByte(String.valueOf(bytesLoaded));
                startEvent.setManifestDwnlTime(String.valueOf(loadDurationMs));
                startEvent.setHttpResponse("200");
            }
        } else {
            if (event != null) {
                if (isLive || isVod)
                    if (trackFormat != null) {
                        bitrate = String.valueOf(trackFormat.bitrate);
                    }
                    if (dataSpec.uri != null) {
                    chunkUri = dataSpec.uri.toString();
                }
                dwnTime=String.valueOf(loadDurationMs);
                dwnlByte=String.valueOf(bytesLoaded);

                sessionsTreamingDownload(chunkUri, chunkIndex, chunkType, bitrate, "", "200",
                        responseTime, ipServer, dwnTime, dwnlByte, bufferSize);

/*
                sessionsTreamingDownload(dataSpec, dataType, trackType, trackFormat, trackSelectionReason,
                        trackSelectionData, mediaStartTimeMs, mediaEndTimeMs, elapsedRealtimeMs, loadDurationMs, bytesLoaded,
                        assetType, ipServer, "bufferSize");*/
            } else {
                Log.d(TAG, " ********** event = null ....it never should happen");
            }
        }
    }


    /*
      API: Call these methods from the PLAYER Activity / Fragment  -
     */


    public void onStartSession(boolean isOnRestart) {
        if (!isOnRestart) {
            // String jsonEvent = sharedPref.getString("event", null);
            //if (jsonEvent != null) {
            //  observer.send(jsonEvent);
            // Log.i(TAG, "**************************    json salvato e inviato    :" + jsonEvent);
            // }
            startSession();
        } else {
            //observer.createNewEvent(getApplicationContext());
            becomeActive();
        }
    }

    public void onStopSession() {
        ended_CallBack(getCurrentTimeStamp());
    }

    public void onPauseSession() {
        if (isLive || isVod) {
            sessionsTreamingPause(getCurrentTimeStamp());
        } else if (isLocalFile) {
            sessionPlaybackPause(getCurrentTimeStamp());
        }
        sendEventQueue();
    }

    static void createNewEvent(Context c) {
        ArrayList<EventElement> eventList = new ArrayList<EventElement>(2);
        DeviceInfo deviceInfo = new DeviceInfo(c);
        event = new Event(c, deviceInfo, eventList);
    }

    public void startSession() {
        //  this.player = player;
        createNewEvent(c);
        event.setSource(source);
        event.device_info.setUser_extid(user_extid);
        if (asi != null) {
            event.events_list.add(0, new EventElement("ASI", asi.getPayload()));
            i = 1;
            asi = null;
        } else {
            i = 0;
        }
        createStartEvent();
        // TIMEout TRIGGER
    }

    private void startTimer() {
        t = new Timer();
        t.scheduleAtFixedRate(newTask(), dequeueingIntervalTime, dequeueingIntervalTime);
    }

    public void becomeActive() {

        if (isLive || isVod || isRestart) {
            sessionsTreamingRestart(getCurrentTimeStamp());
        } else if (isLocalFile) {
            sessionPlaybackRestart(getCurrentTimeStamp());
        }
        isTheFirstTime_Ready = false;
        isTheFirstTime_Buffering = false;
        isFirstTime_Download = false;
    }

    private void createStartEvent( ) {

        if (isTheFirstTime_startEvent) {
            isTheFirstTime_startEvent = false;
            switch (streamingType) {
                case Live:
                    ssch = sessionStartCHannel(getCurrentTimeStamp(),channelID,channelName,channelEPG,channelType,ipServer);
                    break;
                case Restart:
                    ssrch = sessionStartRestartCHannel(getCurrentTimeStamp(),channelID,channelName,channelEPG,channelType,ipServer,restartSec);
                    break;
                case LocalFile:
                    sspvod = sessionStartPlaybackVOD(getCurrentTimeStamp(),originalSessionId, offerID, assetTitle, assetSource);
                    break;
                case Vod:
                    ssvod = sessionStartVOD(getCurrentTimeStamp(), offerID, assetTitle, assetType, assetSource, ipServer);
                    break;
                case Download:
                    ssdvod = sessionStartDownloadVod(getCurrentTimeStamp(),offerID, assetTitle, assetSource,ipServer);
                default:
                    break;
            }
        }
    }


    void sendEventAndEmptyOutEvent() throws IOException {
        try {
            String json = fromObjectToJSON(event);
            createNewEvent(c);
            event.setSource(source);
            event.device_info.setUser_extid(user_extid);
            stampaJSON(json);
            send(json);
            if(t!=null) {
                t.cancel();
            }
            t = new Timer();
            t.scheduleAtFixedRate(newTask(), dequeueingIntervalTime, dequeueingIntervalTime);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return;
    }

    public  void send(String json) {
        SendDeviceDetails async = new SendDeviceDetails();
        async.execute(URL, json);
    }

    public void stampaJSON(String jsonInString) {
        Log.i(TAG, "****************** sto inviando il JSON :  " + jsonInString);
    }

    public static String getCurrentTimeStamp() {
        try {
            //TODO verificare il Timezone corretto
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ITALIAN);
            String currentDateTime = dateFormat.format(new Date());
            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String fromObjectToJSN(ArrayList<EventElement> list) throws IOException {
        String jsonInString = null;
        ObjectMapper mapper = new ObjectMapper();
        jsonInString = mapper.writeValueAsString(list);
        return jsonInString;
    }

    public String fromObjectToJSON(Object object) throws IOException {
        String jsonInString = null;
        ObjectMapper mapper = new ObjectMapper();
        jsonInString = mapper.writeValueAsString(event);
        return jsonInString;
    }

    public void terminate(String currentTimeStamp) {
        if (event != null) {
            if (isLocalFile) {
                sessionPlaybackClose(currentTimeStamp, player);
            } else {
                sessionClose(currentTimeStamp);
            }
        }
    }

    private TimerTask newTask() {
        return new TimerTask() {
            @Override
            public void run() {
                Log.d(TAG, "******** Timer scaduto ");
                sendEventQueue();
            }
            ;
        };
    }

    private void selectStreamingType(StreamingType streamingType) {
        switch (streamingType) {
            case Live:
                isLive = true;
                break;
            case Vod:
                isVod = true;
                break;
            case Restart:
                isRestart = true;
                break;
            case Download:
                isDownload = true;
                break;
            case LocalFile:
                isLocalFile = true;
                break;
            default:
                Log.wtf(TAG, "****  non è stata scelta nessuna modalità di streaming");
                break;
        }
    }

    public synchronized void sendEventQueue()  {
        if (event != null) {
            if (event.events_list.size() > 0) {
                try {
                    sendEventAndEmptyOutEvent();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d(TAG, "********  Lista Vuota");
            }
        }else{
            Log.d(TAG, "********  Evento vuoto");
        }

    }

    public void saveAll(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        try {
            editor.putString("event", fromObjectToJSON(event));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        editor.commit();
    }

    public void perc() {
        _t = new Timer();
        _t.scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {
                        int i = player.getBufferedPercentage();
                        String percentage = String.valueOf(i);
                        Log.i(TAG, " --------->        percentage    " + percentage + "%");
                    }
                }
                , 0, 1000);
    }

    // Player.EventListener

    //region Player.EventListener
    @Override
    public void onLoadingChanged(boolean isLoading) {
        Log.d(TAG, " ********** onLoadingChanged");
        Log.d(TAG, "loading [" + isLoading + "]");
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int state) throws IOException {
        Log.d(TAG, "stato [ Session Time String: " + getSessionTimeString() + ", playWhenReady: " + playWhenReady + ", State:  "
                + getStateString(state) + "]");
        String stateString;
        switch (state) {
            case Player.STATE_IDLE:
                stateString = "Player.SATE_IDLE -";
                idle_CallBack(getCurrentTimeStamp());
                break;
            case Player.STATE_BUFFERING:
                stateString = "Player.STATE_BUFFERING -";
                buffering_CallBack(getCurrentTimeStamp());
                break;
            case Player.STATE_READY:
                stateString = "Player.STATE_READY     -";
                if (!isTheFirstTime_Ready) {
                    if (play == false) {
                        play = true;
                        Log.i(TAG, " ************   Il player è in Play    :" + play);
                    } else {
                        play = false;
                        Log.i(TAG, " ************   Il player è in Pausa   :" + play);
                    }
                }
                ready_CallBack(getCurrentTimeStamp(), play, streamingType);
                break;
            case Player.STATE_ENDED:
                ended_CallBack(getCurrentTimeStamp());
                stateString = "Player.STATE_ENDED -";
                break;
            default:
                stateString = "UNKNOWN_STATE -";
                break;
        }
        Log.i(TAG, "   -------->  onPlayerStateChange : " + stateString);
    }

    @Override
    public void onRepeatModeChanged(@Player.RepeatMode int repeatMode) {
        Log.d(TAG, " ********** onRepeatModeChanged");

        Log.d(TAG, "repeatMode [" + getRepeatModeString(repeatMode) + "]");
    }

    @Override
    public void onPositionDiscontinuity() {
        Log.d(TAG, " ********** onPositionDiscontinuity");


        Log.d(TAG, "positionDiscontinuity");
    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        Log.d(TAG, " ********** onPlaybackParametersChanged");

        Log.d(TAG, "********** playbackParameters " + String.format(
                "[speed=%.2f, pitch=%.2f]", playbackParameters.speed, playbackParameters.pitch));
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
        Log.d(TAG, " ********** onTimelineChanged");

        int periodCount = timeline.getPeriodCount();
        int windowCount = timeline.getWindowCount();
        Log.d(TAG, "sourceInfo [periodCount=" + periodCount + ", windowCount=" + windowCount);
        for (int i = 0; i < Math.min(periodCount, MAX_TIMELINE_ITEM_LINES); i++) {
            timeline.getPeriod(i, period);
            Log.d(TAG, "  " + "period [" + getTimeString(period.getDurationMs()) + "]");
        }
        if (periodCount > MAX_TIMELINE_ITEM_LINES) {
            Log.d(TAG, "  ...");
        }
        for (int i = 0; i < Math.min(windowCount, MAX_TIMELINE_ITEM_LINES); i++) {
            timeline.getWindow(i, window);
            Log.d(TAG, "  " + "window [" + getTimeString(window.getDurationMs()) + ", "
                    + window.isSeekable + ", " + window.isDynamic + "]");
        }
        if (windowCount > MAX_TIMELINE_ITEM_LINES) {
            Log.d(TAG, "  ...");
        }
        Log.d(TAG, "]");
    }

    @Override
    public void onPlayerError(ExoPlaybackException e) {
        Log.d(TAG, " ********** onPlayerError");

        Log.e(TAG, "playerFailed [" + getSessionTimeString() + "]", e);

    }

    @Override
    public void onTracksChanged(TrackGroupArray ignored, TrackSelectionArray trackSelections) {
        MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
        Log.d(TAG, "*******   onTracksChanged");

        TrackGroup trackGroupExt = null;
      /*  if (event != null) {
            STCL(getCurrentTimeStamp(), event, trackFormat);
        }*/

        if (mappedTrackInfo == null) {
            Log.d(TAG, "Tracks []");
            return;
        }
        Log.d(TAG, "Tracks [");
        // Log tracks associated to renderers.
        for (int rendererIndex = 0; rendererIndex < mappedTrackInfo.length; rendererIndex++) {
            TrackGroupArray rendererTrackGroups = mappedTrackInfo.getTrackGroups(rendererIndex);
            TrackSelection trackSelection = trackSelections.get(rendererIndex);
            if (rendererTrackGroups.length > 0) {
                Log.d(TAG, "  Renderer:" + rendererIndex + " [");
                for (int groupIndex = 0; groupIndex < rendererTrackGroups.length; groupIndex++) {
                    TrackGroup trackGroup = rendererTrackGroups.get(groupIndex);
                    String adaptiveSupport = getAdaptiveSupportString(trackGroup.length,
                            mappedTrackInfo.getAdaptiveSupport(rendererIndex, groupIndex, false));
                    Log.d(TAG, "    Group:" + groupIndex + ", adaptive_supported=" + adaptiveSupport + " [");
                    for (int trackIndex = 0; trackIndex < trackGroup.length; trackIndex++) {
                        String status = getTrackStatusString(trackSelection, trackGroup, trackIndex);
                        String formatSupport = getFormatSupportString(
                                mappedTrackInfo.getTrackFormatSupport(rendererIndex, groupIndex, trackIndex)
                        );
                        Log.d(TAG, "      " + status + " Track:" + trackIndex + ", "
                                + Format.toLogString(trackGroup.getFormat(trackIndex))
                                + ", supported=" + formatSupport);

                    }

                    Log.d(TAG, "    ]");
                }

                // Log metadata for at most one of the tracks selected for the renderer.
                if (trackSelection != null) {
                    for (int selectionIndex = 0; selectionIndex < trackSelection.length(); selectionIndex++) {
                        Metadata metadata = trackSelection.getFormat(selectionIndex).metadata;
                        if (metadata != null) {
                            Log.d(TAG, "    Metadata [");
                            printMetadata(metadata, "      ");
                            Log.d(TAG, "    ]");
                            break;
                        }
                    }
                }
                Log.d(TAG, "  ]");
            }
        }
        // Log tracks not associated with a renderer.
        TrackGroupArray unassociatedTrackGroups = mappedTrackInfo.getUnassociatedTrackGroups();
        if (unassociatedTrackGroups.length > 0) {
            Log.d(TAG, "  Renderer:None [");
            for (int groupIndex = 0; groupIndex < unassociatedTrackGroups.length; groupIndex++) {
                Log.d(TAG, "    Group:" + groupIndex + " [");
                TrackGroup trackGroup = unassociatedTrackGroups.get(groupIndex);
                for (int trackIndex = 0; trackIndex < trackGroup.length; trackIndex++) {
                    String status = getTrackStatusString(false);
                    String formatSupport = getFormatSupportString(
                            RendererCapabilities.FORMAT_UNSUPPORTED_TYPE);

                    Log.d(TAG, "      " + status + " Track:" + trackIndex + ", "
                            + Format.toLogString(trackGroup.getFormat(trackIndex))
                            + ", supported=" + formatSupport);
                  trackGroupExt   = trackGroup;
                }

                Log.d(TAG, "    ]");
            }
            Log.d(TAG, "  ]");
        }
        Log.d(TAG, "]");

    }



    //endregion

    // MetadataRenderer.Output

    @Override
    public void onMetadata(Metadata metadata) {
        Log.d(TAG, "****************    onMetadata [");
        printMetadata(metadata, "  ");
        Log.d(TAG, "]");
    }


    // AudioRendererEventListener

    //region AudioRendererEventListener
    @Override
    public void onAudioEnabled(DecoderCounters counters) {
        Log.d(TAG, "audioEnabled [" + getSessionTimeString() + "]");
    }

    @Override
    public void onAudioSessionId(int audioSessionId) {
        Log.d(TAG, "audioSessionId [" + audioSessionId + "]");
    }

    @Override
    public void onAudioDecoderInitialized(String decoderName, long elapsedRealtimeMs,
                                          long initializationDurationMs) {
        Log.d(TAG, "audioDecoderInitialized [" + getSessionTimeString() + ", " + decoderName + "]");
    }

    @Override
    public void onAudioInputFormatChanged(Format format) {
        Log.d(TAG, "audioFormatChanged [" + getSessionTimeString() + ", " + Format.toLogString(format)
                + "]");
    }

    @Override
    public void onAudioDisabled(DecoderCounters counters) {
        Log.d(TAG, "audioDisabled [" + getSessionTimeString() + "]");
    }

    @Override
    public void onAudioTrackUnderrun(int bufferSize, long bufferSizeMs, long elapsedSinceLastFeedMs) throws IOException {
        printInternalError("audioTrackUnderrun [" + bufferSize + ", " + bufferSizeMs + ", "
                + elapsedSinceLastFeedMs + "]", null);
    }
//endregion

    // VideoRendererEventListener

    //region VideoRendererEventListener
    @Override
    public void onVideoEnabled(DecoderCounters counters) {
        Log.d(TAG, "videoEnabled [" + getSessionTimeString() + "]");
        Log.e(TAG, "****************   onVideoEnabled");

    }

    @Override
    public void onVideoDecoderInitialized(String decoderName, long elapsedRealtimeMs,
                                          long initializationDurationMs) {
        Log.d(TAG, " ********** onVideoDecoderInitialized");

        Log.d(TAG, "videoDecoderInitialized [ getSessionTimeString : " + getSessionTimeString() + ",decoderName :  " + decoderName + " elapsedRealtimeMs : " + elapsedRealtimeMs + "initializationDurationMs : " + initializationDurationMs);
    }

    @Override
    public void onVideoInputFormatChanged(Format format) {
        Log.d(TAG, " ********** onVideoInputFormatChanged");

        Log.d(TAG, "videoFormatChanged [" + getSessionTimeString() + ", " + Format.toLogString(format)
                + "]");
    }

    @Override
    public void onVideoDisabled(DecoderCounters counters) {
        Log.d(TAG, "videoDisabled [" + getSessionTimeString() + "]");
    }

    @Override
    public void onDroppedFrames(int count, long elapsed) {
        Log.d(TAG, " ********** onDroppedFrames");

        Log.d(TAG, "droppedFrames [" + getSessionTimeString() + ", " + count + "]");
    }

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees,
                                   float pixelWidthHeightRatio) {
        Log.d(TAG, " ********** onVideoSizeChanged");

        Log.d(TAG, "videoSizeChanged [" + width + ", " + height + "]");
        Log.d(TAG, "videoSizeChanged [ unappliedRotationDegrees:" + unappliedRotationDegrees + ", pixelWidthHeightRatio :" + pixelWidthHeightRatio + "]");

    }

    @Override
    public void onRenderedFirstFrame(Surface surface) {
        Log.d(TAG, " ********** onRenderedFirstFrame");
        Log.d(TAG, "renderedFirstFrame [" + surface + "]");
    }
    //endregion

    // DefaultDrmSessionManager.EventListener

    //region DefaultDrmSessionManager.EventListener
    @Override
    public void onDrmSessionManagerError(Exception e) throws IOException {
        Log.d(TAG, " ********** onDrmSessionManagerError");

        printInternalError("drmSessionManagerError", e);

    }

    @Override
    public void onDrmKeysRestored() {
        Log.d(TAG, "**********  drmKeysRestored [" + getSessionTimeString() + "]");
    }

    @Override
    public void onDrmKeysRemoved() {
        Log.d(TAG, "**********  drmKeysRemoved [" + getSessionTimeString() + "]");
    }

    @Override
    public void onDrmKeysLoaded() {
        Log.d(TAG, " -------------->   drmKeysLoaded [" + getSessionTimeString() + "]");
        drm_time = getCurrentTimeStamp();
        drmKeysLoaded_callback(drm_time);
    }
    //endregion

    // ExtractorMediaSource.EventListener

    //region AdaptiveMediaSourceEventListener
    @Override
    public void onLoadError(IOException error) throws IOException {
        Log.d(TAG, " ********** onLoadError");

        printInternalError("loadError", error);

    }

    // AdaptiveMediaSourceEventListener

    @Override
    public void onLoadStarted(DataSpec dataSpec, int dataType, int trackType, Format trackFormat,
                              int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs,
                              long mediaEndTimeMs, long elapsedRealtimeMs) {
        Log.d(TAG, " ********** ONLOAD STARTED ");
        Log.d(TAG, " ********** trackFormat   :" + trackFormat.toString());


        //  onLoadStarted_callback(getCurrentTimeStamp());
        //     "        DATI :    ");
     /*   Log.d(TAG, "dataSpec  " + dataSpec);
        Log.d(TAG, "dataType  " + dataType);
        Log.d(TAG, "trackType  " + trackType);
        Log.d(TAG, "trackFormat  " + trackFormat);
        Log.d(TAG, "trackSelectionReason  " + trackSelectionReason);
        Log.d(TAG, "trackSelectionData  " + trackSelectionData);
        Log.d(TAG, "mediaStartTimeMs  " + mediaStartTimeMs);
        Log.d(TAG, "mediaEndTimeMs  " + mediaEndTimeMs);
        Log.d(TAG, "elapsedRealtimeMs  " + elapsedRealtimeMs);
*/
    }

    private void onLoadStartedCallback() {

    }


    @Override
    public void onLoadError(DataSpec dataSpec, int dataType, int trackType, Format trackFormat,
                            int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs,
                            long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded,
                            IOException error, boolean wasCanceled) throws IOException {
        Log.d(TAG, " ********** onLoadError");
        printInternalError("ONLOAD ERROR", error);

        //region LOG
  /*      Log.d(TAG, " ********** ONLOAD ERROR " +
                "        DATI :   ");
        Log.d(TAG, "dataSpec " + dataSpec);
        Log.d(TAG, " dataType" + dataType);
        Log.d(TAG, " trackType " + trackType);
        Log.d(TAG, " trackFormat " + trackFormat);
        Log.d(TAG, "trackSelectionReason  " + trackSelectionReason);
        Log.d(TAG, " trackSelectionData " + trackSelectionData);
        Log.d(TAG, "mediaStartTimeMs  " + mediaStartTimeMs);
        Log.d(TAG, " mediaEndTimeMs " + mediaEndTimeMs);
        Log.d(TAG, " elapsedRealtimeMs  " + elapsedRealtimeMs);
        Log.d(TAG, " loadDurationMs " + loadDurationMs);
        Log.d(TAG, " bytesLoaded " + bytesLoaded);
        Log.d(TAG, "wasCanceled" + wasCanceled);
*/
  //endregion
    }

    @Override
    public void onLoadCanceled(DataSpec dataSpec, int dataType, int trackType, Format trackFormat,
                               int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs,
                               long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded) {
        // Do nothing.
        Log.d(TAG, " ********** ONLOAD CANCELED " + "        DATI :   ");
//        Log.d(TAG, "dataSpec " + dataSpec);
//        Log.d(TAG, " dataType" + dataType);s
//        Log.d(TAG, " trackType " + trackType);
//        Log.d(TAG, " trackFormat " + trackFormat);
//        Log.d(TAG, "trackSelectionReason  " + trackSelectionReason);
//        Log.d(TAG, " trackSelectionData " + trackSelectionData);
//        Log.d(TAG, "mediaStartTimeMs  " + mediaStartTimeMs);
//        Log.d(TAG, " mediaEndTimeMs " + mediaEndTimeMs);
//        Log.d(TAG, " elapsedRealtimeMs  " + elapsedRealtimeMs);
//        Log.d(TAG, " loadDurationMs " + loadDurationMs);
//        Log.d(TAG, " bytesLoaded " + bytesLoaded);
         onLoadCanceled_callback(getCurrentTimeStamp());
    }

    private void onLoadCanceled_callback(String currentTimeStamp) {
    }


    @Override
    public void onLoadCompleted(DataSpec dataSpec, int dataType, int trackType, Format trackFormat,
                                int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs,
                                long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded) {
        Log.d(TAG, " ********** ONLOAD COMPLETED ");


        onLoadCompletedCallback(getCurrentTimeStamp(), this.event, dataSpec, dataType, trackType, trackFormat,
                trackSelectionReason, trackSelectionData, mediaStartTimeMs,
                mediaEndTimeMs, elapsedRealtimeMs, loadDurationMs, bytesLoaded);


        Log.d(TAG, "dataSpec " + dataSpec);
        Log.d(TAG, " dataType" + dataType);
        Log.d(TAG, " trackType " + trackType);
        Log.d(TAG, " trackFormat " + trackFormat);
        Log.d(TAG, "trackSelectionReason  " + trackSelectionReason);
        Log.d(TAG, " trackSelectionData " + trackSelectionData);
        Log.d(TAG, "mediaStartTimeMs  " + mediaStartTimeMs);
        Log.d(TAG, " mediaEndTimeMs " + mediaEndTimeMs);
        Log.d(TAG, " elapsedRealtimeMs  " + elapsedRealtimeMs);
        Log.d(TAG, " loadDurationMs " + loadDurationMs);
        Log.d(TAG, " bytesLoaded " + bytesLoaded);
    }


    @Override
    public void onUpstreamDiscarded(int trackType, long mediaStartTimeMs, long mediaEndTimeMs) {
        // Do nothing.
        Log.d(TAG, " ********** onUpstreamDiscarded  ");
/*        Log.d(TAG, " trackType " + trackType);
        Log.d(TAG, "mediaStartTimeMs  " + mediaStartTimeMs);
        Log.d(TAG, " mediaEndTimeMs " + mediaEndTimeMs);*/
    }

    @Override
    public void onDownstreamFormatChanged(int trackType, Format trackFormat, int trackSelectionReason,
                                          Object trackSelectionData, long mediaTimeMs) {
        Log.d(TAG, " ********** onDownstreamFormatChanged  " +
                "        DATI :   ");
        Log.d(TAG, " trackType " + trackType);
        Log.d(TAG, " trackFormat " + trackFormat);
        Log.d(TAG, "trackSelectionReason  " + trackSelectionReason);
        Log.d(TAG, " trackSelectionData " + trackSelectionData);

        onDownstreamFormatChanged_callback(event, getCurrentTimeStamp(), trackFormat);


    }


    //endregion




    public void printInternalError(String type, Exception err) throws IOException {
        Log.e(TAG, "internalError [" + getSessionTimeString() + ", " + type + "]", err);
        if(!isDownload) {
            String errorText= "";
            String erroMessage = "";
            if(err!=null){
                erroMessage=err.getMessage();
                errorText=String.valueOf(err.getCause());
            }
            sessionError(errorText, type, "", "", "", "", "", "", "event Name",erroMessage );
            sendEventQueue();
        }
    }

    public void notifyError(String error_text,String errorType, String error_code,String chunk_uri,String channel_id,
                            String event_id, String vod_id,String player_version, String event_name, String error_message) {
        sessionError(error_text, errorType, error_code, chunk_uri, channel_id, event_id, vod_id, player_version, event_name, error_message);
        sendEventQueue();
    }

    private void printMetadata(Metadata metadata, String prefix) {
        Log.d(TAG, " ********** printMetadata");

        for (int i = 0; i < metadata.length(); i++) {
            Metadata.Entry entry = metadata.get(i);

            if (entry instanceof TextInformationFrame) {
                TextInformationFrame textInformationFrame = (TextInformationFrame) entry;
                Log.d(TAG, prefix + String.format("%s: value=%s", textInformationFrame.id,
                        textInformationFrame.value));
            } else if (entry instanceof UrlLinkFrame) {
                UrlLinkFrame urlLinkFrame = (UrlLinkFrame) entry;
                Log.d(TAG, prefix + String.format("%s: url=%s", urlLinkFrame.id, urlLinkFrame.url));
            } else if (entry instanceof PrivFrame) {
                PrivFrame privFrame = (PrivFrame) entry;
                Log.d(TAG, prefix + String.format("%s: owner=%s", privFrame.id, privFrame.owner));
            } else if (entry instanceof GeobFrame) {
                GeobFrame geobFrame = (GeobFrame) entry;
                Log.d(TAG, prefix + String.format("%s: mimeType=%s, filename=%s, description=%s",
                        geobFrame.id, geobFrame.mimeType, geobFrame.filename, geobFrame.description));
            } else if (entry instanceof ApicFrame) {
                ApicFrame apicFrame = (ApicFrame) entry;
                Log.d(TAG, prefix + String.format("%s: mimeType=%s, description=%s",
                        apicFrame.id, apicFrame.mimeType, apicFrame.description));
            } else if (entry instanceof CommentFrame) {
                CommentFrame commentFrame = (CommentFrame) entry;
                Log.d(TAG, prefix + String.format("%s: language=%s, description=%s", commentFrame.id,
                        commentFrame.language, commentFrame.description));
            } else if (entry instanceof Id3Frame) {
                Id3Frame id3Frame = (Id3Frame) entry;
                Log.d(TAG, prefix + String.format("%s", id3Frame.id));
            } else if (entry instanceof EventMessage) {
                EventMessage eventMessage = (EventMessage) entry;
                Log.d(TAG, prefix + String.format("EMSG: scheme=%s, id=%d, value=%s",
                        eventMessage.schemeIdUri, eventMessage.id, eventMessage.value));
            }
        }
    }

    private String getSessionTimeString(){
        return getTimeString(SystemClock.elapsedRealtime() - startTimeMs);
    }

    private static String getTimeString(long timeMs) {
        return timeMs == C.TIME_UNSET ? "?" : TIME_FORMAT.format((timeMs) / 1000f);
    }

    private static String getStateString(int state) {
        switch (state) {
            case Player.STATE_BUFFERING:
                return "STATE_BUFFERING";
            case Player.STATE_ENDED:
                return "STATE_ENDED";
            case Player.STATE_IDLE:
                return "STATE_IDLE";
            case Player.STATE_READY:
                return "STATE_READY";
            default:
                return "?";
        }
    }

    private static String getFormatSupportString(int formatSupport) {
        switch (formatSupport) {
            case RendererCapabilities.FORMAT_HANDLED:
                return "YES";
            case RendererCapabilities.FORMAT_EXCEEDS_CAPABILITIES:
                return "NO_EXCEEDS_CAPABILITIES";
            case RendererCapabilities.FORMAT_UNSUPPORTED_DRM:
                return "NO_UNSUPPORTED_DRM";
            case RendererCapabilities.FORMAT_UNSUPPORTED_SUBTYPE:
                return "NO_UNSUPPORTED_TYPE";
            case RendererCapabilities.FORMAT_UNSUPPORTED_TYPE:
                return "NO";
            default:
                return "?";
        }
    }

    private static String getAdaptiveSupportString(int trackCount, int adaptiveSupport) {
        if (trackCount < 2) {
            return "N/A";
        }
        switch (adaptiveSupport) {
            case RendererCapabilities.ADAPTIVE_SEAMLESS:
                return "YES";
            case RendererCapabilities.ADAPTIVE_NOT_SEAMLESS:
                return "YES_NOT_SEAMLESS";
            case RendererCapabilities.ADAPTIVE_NOT_SUPPORTED:
                return "NO";
            default:
                return "?";
        }
    }

    private static String getTrackStatusString(TrackSelection selection, TrackGroup group, int trackIndex) {
        return getTrackStatusString(selection != null && selection.getTrackGroup() == group
                && selection.indexOf(trackIndex) != C.INDEX_UNSET);
    }

    private static String getTrackStatusString(boolean enabled) {
        return enabled ? "[X]" : "[ ]";
    }

    private static String getRepeatModeString(@Player.RepeatMode int repeatMode) {
        switch (repeatMode) {
            case Player.REPEAT_MODE_OFF:
                return "OFF";
            case Player.REPEAT_MODE_ONE:
                return "ONE";
            case Player.REPEAT_MODE_ALL:
                return "ALL";
            default:
                return "?";
        }
    }




}


//region RIUSO


//startTimer();
//region comments


  /*
    if (isLive) {
                updateSSCH(currentTimeStamp,event);

            } else if (isLocalFile) {
                if (sspvod != null) {
                    sspvod.setPlayback_start_time(currentTimeStamp);
                    sspvod.updateSSPVODPayload();
                    event.events_list.add(i, new EventElement("SSPVOD", sspvod.getPayload()));
                }
            } else if (isVod) {
                if (ssvod != null) {
                    ssvod.setPlayback_start_time(currentTimeStamp);
                    ssvod.updateSSVODPayload();
                    event.events_list.add(i, new EventElement("SSVOD", ssvod.getPayload()));
                }
            } else if (isRestart) {
                if (ssrch != null) {
                    ssrch.setPlayback_start_time(currentTimeStamp);
                    ssrch.updateSSRCHPayload();
                    event.events_list.add(i, new EventElement("SSRCH", ssrch.getPayload()));
                }
            }else if (isDownload){
                if(ssdvod!=null){
                    ssdvod.setPlayback_start_time(currentTimeStamp);
                    ssdvod.updateSSDVODPayload();
                    event.events_list.add(i, new EventElement("SSDVOD", ssdvod.getPayload()));
                }
            }

            */

//endregion



         /*   if (isLocalFile) {
                sspvod = sessionStartPlaybackVOD(getCurrentTimeStamp());
            } else if (isLive) {
                if (isRestart) {
                    ssrch = sessionStartRestartCHannel(getCurrentTimeStamp());
                } else {
                    ssch = sessionStartCHannel(getCurrentTimeStamp());
                }
            } else {
                ssvod = sessionStartVOD(getCurrentTimeStamp());
            }*/


//
/*
   public Event updateEventList(EventElement e, EventElement e_) {
        if (e != null) {
            if (e_ != e) {
                if (event != null) {
                    event.events_list.add(e);
                    e_ = e;
                }
            }
        }
        return event;
    }*/







    /*
     START : Every time a session starts.
     SSCH : Session Start CHannel
     SSRCH : Session Start Restart Channel (**)
     SSVOD : Session Start VOD
     SSDVOD : Session Start Download VOD (**) SSPVOD : Session
     */


 /*   private void SSCH(String currentTimeStamp, Event event) {
        sc = null;
        ssch = new SSCH();
        ssch.setSession_id(sessionID);
        ssch.setStart_time(currentTimeStamp);
        //  ssch.setPlayback_start_time("");
        //  ssch.setBuffering_time("");
        ssch.setChannel_epg("Channel EPG");
        ssch.setChannel_id(channelID);
        ssch.setChannel_name(channelName);
        ssch.setChannel_type(channelType);
        ssch.setDrm_time(drm_time);
        ssch.setHttp_response("Http Response");
        ssch.setIp_server("ipServer");
        ssch.setManifest_uri("Maniest URI");
        ssch.setManifest_dwnl_byte("Maniest dwnl byte");
        ssch.setManifest_dwnl_time("Maniest dwnl time");

*//*        ssch.updateSSCHPayload();

        event.events_list.add(1, new EventElement("SSCH", ssch.getPayload()));*//*
        //updateEventList(e, e_);
    }
*/


/*
    private void SSRCH(String currentTimeStamp, Event event) {
        ssrch = new SSRCH();

        ssrch.setSession_id(sessionID);
        ssrch.setOriginal_session_id(originalSessionId);
        ssrch.setStart_time(currentTimeStamp);
        //ssrch.setPlayback_start_time(currentTimeStamp);
        //ssrch.setBuffering_time(bufferingTimeStamp);
        ssrch.setDrm_time(drm_time);
        ssrch.setIp_server("IP_Server");
        ssrch.setManifest_uri("manifest_uri");
        ssrch.setManifest_dwnl_time("manifest_dwl_time");
        ssrch.setManifest_dwnl_byte("manifest_dwnl_byte");
        ssrch.setChannel_epg("Channel EPG");
        ssrch.setChannel_id(channelID);
        ssrch.setChannel_name(channelName);
        ssrch.setChannel_type(channelType);
        ssrch.setHttp_response("Http Response");
        ssrch.setRestart_sec("restart_sec");

        ssrch.updateSSRCHPayload();
        event.events_list.add(new EventElement("SSRCH", ssrch.getPayload()));
    }

    private void SSVOD(String currentTimeStamp, Event event) {
        ssvod = new SSVOD();
        ssvod.setSession_id(sessionID);
        ssvod.setStart_time(currentTimeStamp);
        ssvod.setDrm_time(drm_time);
        //   ssvod.setBuffering_time();
        ssvod.setManifest_uri("manifest_uri");
        ssvod.setManifest_dwnl_time("manifest_dwl_time");
        ssvod.setManifest_dwnl_byte("manifest_dwnl_byte");
        ssvod.setIp_server("IP_Server");
        ssvod.setHttp_response("Http Response");
        ssvod.setPlayback_start_time(currentTimeStamp);
        ssvod.setOffer_id(vodID);
        ssvod.setAsset_title(VODTitle);
        ssvod.setAsset_type(assetType);
        ssvod.setAsset_source(assetPath);

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
        ssvod.updateSSVODPayload();
        event.events_list.add(new EventElement("SSVOD", ssvod.getPayload()));
    }

    private void SSDVOD(String currentTimeStamp, Event event) {
        ssdvod = new SSDVOD();
        ssdvod.setSession_id(sessionID);
        ssdvod.setStart_time(currentTimeStamp);
        ssdvod.setDrm_time(drm_time);
        ssdvod.setBuffering_time(bufferingTimeStamp);
        ssdvod.setIp_server(playerMonitor.serverURL);
        ssdvod.setHttp_response("Http_Response");
        ssdvod.setManifest_uri("Maniest URI");
        ssdvod.setManifest_dwnl_byte("Maniest dwnl byte");
        ssdvod.setManifest_dwnl_time("Maniest dwnl time");
        ssdvod.setOffer_id(vodID);
        ssdvod.setAsset_title(VODTitle);
        ssdvod.setAsset_source(assetPath);

        ssdvod.updateSSDVODPayload();
        event.events_list.add(new EventElement("SSDVOD", ssdvod.getPayload()));
    }

    private void SSPVOD(String currentTimeStamp, Event event) {
        sspvod = new SSPVOD();
        sspvod.setSession_id(sessionID);
        sspvod.setOriginal_session_id(originalSessionId);
        sspvod.setPlayback_start_time(currentTimeStamp);
        sspvod.setOffer_id(vodID);
        sspvod.setAsset_title(VODTitle);
        sspvod.setAsset_source(assetPath);
        sspvod.setDelay_time_sec(delay_time_sec);

     */
/*   sspvod.updateSSPVODPayload();
        event.events_list.add(new EventElement("SSPVOD", sspvod.getPayload()));*//*

    }
*/


    /*
    Playback Events
     */

   /* private void SPP(String currentTimeStamp, Event event) {
        spp = new SPP();
        spp.setSession_id(sessionID);
        spp.setPause_time(currentTimeStamp);
        event.events_list.add(new EventElement("SPP", spp.getPayload()));
    }

    private void SPR(String currentTimeStamp, Event event) {
        spr = new SPR();
        spr.setSession_id(sessionID);
        spr.setRestart_time(currentTimeStamp);
        event.events_list.add(new EventElement("SPR", spr.getPayload()));
    }*/



    /*
    onPlayerStateChange STATE callbacks :   IDLE - READY - BUFFERING - ENDED
    */


//endregion