package com.google.android.exoplayer2.demo;

import android.content.Context;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Surface;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
import com.google.android.exoplayer2.demo.Model.Event;
import com.google.android.exoplayer2.demo.Model.EventElement;
import com.google.android.exoplayer2.demo.Model.SC;
import com.google.android.exoplayer2.demo.Model.SDC;
import com.google.android.exoplayer2.demo.Model.SDD;
import com.google.android.exoplayer2.demo.Model.SE;
import com.google.android.exoplayer2.demo.Model.SSCH;
import com.google.android.exoplayer2.demo.Model.SSRCH;
import com.google.android.exoplayer2.demo.Model.Streaming.STCL;
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
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;



public final class Observer implements Player.EventListener, AudioRendererEventListener,
        VideoRendererEventListener, AdaptiveMediaSourceEventListener,
        ExtractorMediaSource.EventListener, DefaultDrmSessionManager.EventListener,
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

//  static int numPlay = 0;
//  static int numPause = 0;
//  static boolean boolPause = false;
//  static boolean boolPlay = false;

    //Utils variables
    Context c;
    public static Event event;
    Timer t;

    public EventElement e = null;
    public EventElement e_ = null;


    public SSCH ssch = null;
    public String drm_time;
    public SSRCH ssrch;
    public STCL stcl;
    public SDC sdc;
    public SDD sdd;
    public SE se;
    public SC sc;

    public String sessionID;
    PlayerMonitor playerMonitor;
    SimpleExoPlayer player;

    boolean isTheFirstTime = true;
    boolean isTheFirstTime_Buffering = true;
    boolean isTheFirstTime_Ready = true;

    public Observer(final MappingTrackSelector trackSelector, final Context context, final PlayerMonitor playerMonitor, String sessionID) {

        this.playerMonitor = playerMonitor;

        event = playerMonitor.getEvent();
        this.c = context;
        this.trackSelector = trackSelector;
        window = new Timeline.Window();
        period = new Timeline.Period();
        startTimeMs = SystemClock.elapsedRealtime();
        this.sessionID = sessionID;
        this.player = player;

        //TRIGGER
        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
                                  @Override
                                  public void run() {
                                      Log.d(TAG, "********TIMER " + t.toString());
                                      //  Observer o = new Observer(trackSelector, c);
                                      if (event != null) {
                                          sendJSson(event);
                                          event = playerMonitor.createEventBody(c);
                                      } else {
                                          event = playerMonitor.createEventBody(c);
                                      }
                                  }
                              }
                , 0, 30000);
    }

//
//    private void createJsonEvents(Context c) {
//
////    String statoplayer =  getStateString(stato);
////    Log.d(TAG, "********************* LO STATO DEL PLAYER è IN " + getStateString(stato)  );
////    //    boolPlay=true;
//////    if (startBool) {
//////      if (boolPause){
//////        numPlay++;
//////        boolPlay = true;
//////      }
//////    } else {
//////      if (boolPlay){
//////        numPause++;
//////        boolPause = true;
//////      }
//////    }
////
////    String statoPlayer = startBool ? " PLAY" : "PAUSE";
////    Log.d(TAG, "********************* LO STATO DEL PLAYER è IN " + getStateString(stato)  );
//        //    Log.d(TAG, "********************* IL PLAYER E STATO MESSO IN PLAY " + numPlay + " volte");
//        //    Log.d(TAG, "********************* IL PLAYER E STATO MESSO IN PAUSE " + numPause + " volte");
//        //  Log.d(TAG, "********************* Il Player è andato in RESTART  "    + numPlay + " volte");
//        //  Log.d(TAG, "********************* Il Player è andato in PAUSE    "    + numPause + " volte");
//        //
//        eventList = new EventList();
//        deviceInfo = new DeviceInfo(c);
//        event = new Event(c, this.eventList, this.deviceInfo);
////        EventElement e = new EventElement("SSCH", new SSCH());
////        event.eventList.add(e);
//        // SSCH ssch = new SSCH();
//        //ssch.session_id = "SSCH";
//        // e.setPayload(ssch);
////        e = null;
////
////        e = new EventElement();
////        SSRCH ssrch = new SSRCH();
////        ssrch.session_id = "SSRCH";
////        e.setPayload(ssrch);
////        event.eventList.add(e);
////        e = null;
////
////        e = new EventElement();
////        SSVOD ssvod = new SSVOD();
////        ssvod.session_id = "SSVOD";
////        e.setPayload(ssvod);
////        event.eventList.add(e);
////        e = null;
////
////        e = new EventElement();
////        SSDVOD ssdvod = new SSDVOD();
////        ssdvod.session_id = "SSDVOD";
////        e.setPayload(ssdvod);
////        event.eventList.add(e);
////        e = null;
////
////        e = new EventElement();
////        SSPVOD sspvod = new SSPVOD();
////        sspvod.session_id = "SSPVOD";
////        e.setPayload(sspvod);
////        event.eventList.add(e);
////        e = null;
////
////        e = new EventElement();
////        SSLINK sslink = new SSLINK();
////        sslink.new_session_id = "SSLINK";
////        e.setPayload(sslink);
////        event.eventList.add(e);
////        e = null;
////
////        //    STD : Session sTreaming Download (valid also for VOD download) STP : Session sTreaming Pause
////        //    STR : Session sTreaming Restart
////        //    STCL : Session sTreaming Change Level
////        //    STRB : Session sTreaming ReBuferring
////
////
////        e = new EventElement();
////        STD std = new STD();
////        std.session_id = "STD";
////        e.setPayload(std);
////        event.eventList.add(e);
////        e = null;
////
////        e = new EventElement();
////        STP stp = new STP();
////        stp.session_id = "STP";
////        e.setPayload(stp);
////        event.eventList.add(e);
////        e = null;
////
////        e = new EventElement();
////        STR str = new STR();
////        str.session_id = "STR";
////        e.setPayload(str);
////        event.eventList.add(e);
////        e = null;
////
////
////        e = new EventElement();
////        STCL stcl = new STCL();
////        stcl.session_id = "STCL";
////        e.setPayload(stcl);
////        event.eventList.add(e);
////        e = null;
////
////        e = new EventElement();
////        STRB strb = new STRB();
////        strb.session_id = "STRB";
////        e.setPayload(strb);
////        event.eventList.add(e);
////        e = null;
////
//////    SDP : Session Download Pause
//////    SDR : Session Download Resume
//////    SDD : Session Download Delete (download removed)
////
////
////        e = new EventElement();
////        SDP sdp = new SDP();
////        sdp.session_id = "SDP";
////        e.setPayload(sdp);
////        event.eventList.add(e);
////        e = null;
////
////        e = new EventElement();
////        SDR sdr = new SDR();
////        sdr.session_id = "SDR";
////        e.setPayload(sdr);
////        event.eventList.add(e);
////        e = null;
////
////        e = new EventElement();
////        SDD sdd = new SDD();
////        sdd.session_id = "SDD";
////        e.setPayload(sdd);
////        event.eventList.add(e);
////        e = null;
////
////        //  PLAYBACK
////        //  SPP : Session Playback Pause
////        //  SPR : Session Playback Restart
////
////        e = new EventElement();
////        SPP spp = new SPP();
////        spp.session_id = "SPP";
////        e.setPayload(spp);
////        event.eventList.add(e);
////        e = null;
////
////        e = new EventElement();
////        SPR spr = new SPR();
////        spr.session_id = "SPR";
////        e.setPayload(spr);
////        event.eventList.add(e);
////        e = null;
////
////
//////        SC : Session Close
//////        SDC : Session Download Completed  (reached download end)
//////        SPC : Session Playback Close  (playback finished)
////        e = new EventElement();
////        SC sc = new SC();
////        sc.session_id = "SC";
////        e.setPayload(sc);
////        event.eventList.add(e);
////        e = null;
////
////        e = new EventElement();
////        SDC sdc = new SDC();
////        sdc.session_id = "SDC";
////        e.setPayload(sdc);
////        event.eventList.add(e);
////        e = null;
////
////        e = new EventElement();
////        SPC spc = new SPC();
////        spc.session_id = "SPC";
////        e.setPayload(spc);
////        event.eventList.add(e);
////        e = null;
////
////        e = new EventElement();
////        SE se = new SE("errore generico");
////        se.session_id = "SE";
////        e.setPayload(se);
////        event.eventList.add(e);
////        e = null;
//    }

    //        if(p.isCurrentWindowSeekable() && p.isCurrentWindowDynamic()) {
//
//            Log.d(TAG, "**************************    è un VOD !!!! ");
//
//        }

//        isCurrentWindowDynamic && isCurrentWindowSeekable


    // Player.EventListener
    @Override
    public void onLoadingChanged(boolean isLoading) {
        Log.d(TAG, "loading [" + isLoading + "]");
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int state) throws IOException {
        Log.d(TAG, "stato [ Session Time String: " + getSessionTimeString() + ", playWhenReady: " + playWhenReady + ", State:  "
                + getStateString(state) + "]");

        String startTime = null;
        String bufferingTime = null;
        String startPlaybackTime = null;
        String stateString;


        switch (state) {
            case Player.STATE_IDLE:
                stateString = "Player.SATE_IDLE -";
                if (isTheFirstTime) {
                    createSSCH();
                    isTheFirstTime = false;
                }
                break;
            case Player.STATE_BUFFERING:
                stateString = "Player.STATE_BUFFERING -";
                if (isTheFirstTime_Buffering) {
                    ssch.setBuffering_time(getCurrentTimeStamp());
                    isTheFirstTime_Buffering = false;
                } else {
                    bufferingTime = getCurrentTimeStamp();
                }
                break;
            case Player.STATE_READY:
                stateString = "Player.STATE_READY     -";
                if (isTheFirstTime_Ready) {
                    ssch.setPlayback_start_time(getCurrentTimeStamp());
                    isTheFirstTime_Ready = false;
                } else {
                    createSSRCH();
                }
                break;
            case Player.STATE_ENDED:
                //SC : Session
                if (sc == null) {
                    createSC();
                }
                stateString = "Player.STATE_ENDED     -";
                break;
            default:
                stateString = "UNKNOWN_STATE             -";
                break;
        }

        if (!isTheFirstTime) {
            if (ssrch != null) {

                updateSSRCH(bufferingTime);
            }
        } else {
            if (ssch != null) {
//                updateSSCH(ssch, bufferingTime, startPlaybackTime);
            }
        }
        event = updateEventList(e, e_);
//            else {
////                createJsonEvents(c);
//                event.eventList.add(e);
//            }
    }


    public static Event updateEventList(EventElement e, EventElement e_) {
        if (e != null) {
            if (e_ != e) {
                if (event != null) {
                    event.events_list.add(e);
                    e_ = e;
                }
            }
        }
        return event;
    }

    @Override
    public void onRepeatModeChanged(@Player.RepeatMode int repeatMode) {
        Log.d(TAG, "repeatMode [" + getRepeatModeString(repeatMode) + "]");
    }

    @Override
    public void onPositionDiscontinuity() {
        Log.d(TAG, "positionDiscontinuity");
    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        Log.d(TAG, "********** playbackParameters " + String.format(
                "[speed=%.2f, pitch=%.2f]", playbackParameters.speed, playbackParameters.pitch));
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
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
        Log.e(TAG, "playerFailed [" + getSessionTimeString() + "]", e);
    }

    @Override
    public void onTracksChanged(TrackGroupArray ignored, TrackSelectionArray trackSelections) {
        MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
        Log.d(TAG, "*******   onTracksChanged");
        Log.d(TAG, "Tracks info" + mappedTrackInfo.toString());

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
                                mappedTrackInfo.getTrackFormatSupport(rendererIndex, groupIndex, trackIndex));
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
                }
                Log.d(TAG, "    ]");
            }
            Log.d(TAG, "  ]");
        }
        Log.d(TAG, "]");
    }

    // MetadataRenderer.Output

    @Override
    public void onMetadata(Metadata metadata) {
        Log.d(TAG, "****************    onMetadata [");
        printMetadata(metadata, "  ");
        Log.d(TAG, "]");
    }

    // AudioRendererEventListener

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

    // VideoRendererEventListener

    @Override
    public void onVideoEnabled(DecoderCounters counters) {
        Log.d(TAG, "videoEnabled [" + getSessionTimeString() + "]");
        Log.e(TAG, "****************   onVideoEnabled");

    }

    @Override
    public void onVideoDecoderInitialized(String decoderName, long elapsedRealtimeMs,
                                          long initializationDurationMs) {
        Log.d(TAG, "videoDecoderInitialized [ getSessionTimeString : " + getSessionTimeString() + ",decoderName :  " + decoderName + " elapsedRealtimeMs : " + elapsedRealtimeMs + "initializationDurationMs : " + initializationDurationMs);
    }

    @Override
    public void onVideoInputFormatChanged(Format format) {
        Log.d(TAG, "videoFormatChanged [" + getSessionTimeString() + ", " + Format.toLogString(format)
                + "]");
    }

    @Override
    public void onVideoDisabled(DecoderCounters counters) {
        Log.d(TAG, "videoDisabled [" + getSessionTimeString() + "]");

        //SC : Session Close
        if (sc == null) {
            sc = new SC();
            sc.setSession_id("Session Close");
            sc.setClosing_time(getCurrentTimeStamp());

            sc.updateSCPayload();
            e = new EventElement("SC", sc.getPayload());
            event.events_list.add(e);
        }
    }

    @Override
    public void onDroppedFrames(int count, long elapsed) {
        Log.d(TAG, "droppedFrames [" + getSessionTimeString() + ", " + count + "]");
    }

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees,
                                   float pixelWidthHeightRatio) {
        Log.d(TAG, "videoSizeChanged [" + width + ", " + height + "]");
        Log.d(TAG, "videoSizeChanged [ unappliedRotationDegrees:" + unappliedRotationDegrees + ", pixelWidthHeightRatio :" + pixelWidthHeightRatio + "]");

    }

    @Override
    public void onRenderedFirstFrame(Surface surface) {
        Log.d(TAG, "renderedFirstFrame [" + surface + "]");
    }

    // DefaultDrmSessionManager.EventListener

    @Override
    public void onDrmSessionManagerError(Exception e) throws IOException {
        printInternalError("drmSessionManagerError", e);

    }

    @Override
    public void onDrmKeysRestored() {
        Log.d(TAG, "drmKeysRestored [" + getSessionTimeString() + "]");
    }

    @Override
    public void onDrmKeysRemoved() {
        Log.d(TAG, "drmKeysRemoved [" + getSessionTimeString() + "]");
    }

    @Override
    public void onDrmKeysLoaded() {
        Log.d(TAG, "drmKeysLoaded [" + getSessionTimeString() + "]");
        drm_time = getCurrentTimeStamp();
    }

    // ExtractorMediaSource.EventListener

    @Override
    public void onLoadError(IOException error) throws IOException {
        printInternalError("loadError", error);

    }

    // AdaptiveMediaSourceEventListener

    @Override
    public void onLoadStarted(DataSpec dataSpec, int dataType, int trackType, Format trackFormat,
                              int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs,
                              long mediaEndTimeMs, long elapsedRealtimeMs) {

        Log.d(TAG, " ********** ONLOAD STARTED " +
                "        DATI :    ");
        Log.d(TAG, "dataSpec  " + dataSpec);
        Log.d(TAG, "dataType  " + dataType);
        Log.d(TAG, "trackType  " + trackType);
        Log.d(TAG, "trackFormat  " + trackFormat);
        Log.d(TAG, "trackSelectionReason  " + trackSelectionReason);
        Log.d(TAG, "trackSelectionData  " + trackSelectionData);
        Log.d(TAG, "mediaStartTimeMs  " + mediaStartTimeMs);
        Log.d(TAG, "mediaEndTimeMs  " + mediaEndTimeMs);
        Log.d(TAG, "elapsedRealtimeMs  " + elapsedRealtimeMs);


//    gsonObject.dataSpec=dataSpec;
//    gsonObject.dataType=dataType;
//    gsonObject.trackType=trackType;
//    gsonObject.trackFormat=trackFormat;
//    gsonObject.trackSelectionReason=trackSelectionReason;
//    gsonObject.trackSelectionData=trackSelectionData;
//    gsonObject.mediaStartTimeMs=mediaStartTimeMs;
//    gsonObject.mediaEndTimeMs=mediaEndTimeMs;
//    gsonObject.elapsedRealtimeMs=elapsedRealtimeMs;
    }


    @Override
    public void onLoadError(DataSpec dataSpec, int dataType, int trackType, Format trackFormat,
                            int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs,
                            long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded,
                            IOException error, boolean wasCanceled) throws IOException {
        printInternalError("ONLOAD ERROR", error);
        Log.d(TAG, " ********** ONLOAD ERROR " +
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


  /*  event.eventList.add(new EventElement(new SE()));
    stampaJson();
    sendJSson();*/
    }

    @Override
    public void onLoadCanceled(DataSpec dataSpec, int dataType, int trackType, Format trackFormat,
                               int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs,
                               long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded) {
        // Do nothing.
        Log.d(TAG, " ********** ONLOAD CANCELED " +
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


        //SDD : Session Download Delete (download removed)
        sdd = new SDD();
        sdd.setSession_id("Session ID");
        sdd.setDelete_time(getCurrentTimeStamp());

        sdd.createSDDPayload();

        e = new EventElement("SDD", sdd.getPayload());
        event.events_list.add(e);

    }

    @Override
    public void onLoadCompleted(DataSpec dataSpec, int dataType, int trackType, Format trackFormat,
                                int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs,
                                long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded) {
        Log.d(TAG, " ********** ONLOAD COMPLETED " +
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

//        if(trac)
    }

    @Override
    public void onUpstreamDiscarded(int trackType, long mediaStartTimeMs, long mediaEndTimeMs) {
        // Do nothing.
        Log.d(TAG, " ********** onUpstreamDiscarded  " +
                "        DATI :   ");
        Log.d(TAG, " trackType " + trackType);
        Log.d(TAG, "mediaStartTimeMs  " + mediaStartTimeMs);
        Log.d(TAG, " mediaEndTimeMs " + mediaEndTimeMs);
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


        stcl = new STCL();
        stcl.setSession_id("session ID");
        stcl.setBitrate_to(String.valueOf(trackFormat.bitrate));
        stcl.createSDDPayload();

        event.events_list.add(new EventElement("STCL", stcl.getPayload()));

    }


    // Internal methods
    public void printInternalError(String type, Exception err) throws IOException {
        Log.e(TAG, "internalError [" + getSessionTimeString() + ", " + type + "]", err);

        se = new SE();
        se.setErrorType(type);
        se.setSession_id("session ID");

        se.createSDDPayload();
        e = new EventElement("Session Error", se.getPayload());

        updateEventList(e, e_);
//        if (e != null) {
//            if (e_ != e) {
//                if (event != null) {
//                    event.eventList.add(e);
//                    e_ = e;
//                }
//            }
////            else {
//////                createJsonEvents(c);
////                event.eventList.add(e);
////            }
//        }
//        se.setError_message(e.getMessage());
//        se.setError_code(String.valueOf(e.getStackTrace()));

        // se.setPlayer_version();
    }

    private void printMetadata(Metadata metadata, String prefix) {
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

    private String getSessionTimeString() {
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

    private static String getTrackStatusString(TrackSelection selection, TrackGroup group,
                                               int trackIndex) {
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


    //Mine

//    public void updateSSCH(SSCH ssch, String bufferingTime, String startPlaybackTime) {
//
//        if (bufferingTime != null) {
//            ssch.setBuffering_time(bufferingTime);
//            bufferingTime = null;
//        }
//        if (startPlaybackTime != null) {
//            ssch.setPlayback_start_time(startPlaybackTime);
//            startPlaybackTime = null;
//        }
//        ssch.setSession_id("SessionID");
//
//         isTheFirstTime = false;
//
//        //  ssch.setBuffering_time("");
//
//    }

    private void sendJSson(Event event) {
        Log.d(TAG, "*********** SENDING JSON... ");
        //  stampaJson();
        fromObjectToJSON(event);
    }

    @Nullable
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


    public String fromObjectToJSON(Object object) {
        String jsonInString = null;

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            jsonInString = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "****************** JSON :  " + jsonInString);
        return jsonInString;
    }

    private void createSDC() {
        //Session downloaded completed
        sdc = new SDC();
        sdc.setSession_id("Session Download completed");
        sdc.setCompleted_time(getCurrentTimeStamp());

        sdc.createSDCPayload();

        e = new EventElement("SDC", sdc.getPayload());


    }

//    private void updateEventList(EventElement e, EventElement e_) {
//        if (e != null) {
//            if (e_ != e) {
//                if (event != null) {
//                    event.eventList.add(e);
//                    e_ = e;
//                }
//            }
//        }
//    }

    private void updateSSRCH(String bufferingTime) {

        ssrch.setBuffering_time(bufferingTime);
        ssrch.updateSSRCHPayload();

    }

    private void createSC() {
        sc = new SC();
        sc.setSession_id("Session Close");
        sc.setClosing_time(getCurrentTimeStamp());
        sc.updateSCPayload();

        e = new EventElement("SC", sc.getPayload());

    }

    private void createSSRCH() {
        ssrch = new SSRCH();
        ssrch.setPlayback_start_time(getCurrentTimeStamp());
        ssrch.updateSSRCHPayload();


        e = new EventElement("SSRCH", ssrch.getPayload());
    }

    private void createSSCH() {
        sc = null;
        ssch = new SSCH();
        ssch.setSession_id(sessionID);
        ssch.setPlayback_start_time("");
        ssch.setBuffering_time("");
        ssch.setChannel_epg(playerMonitor.getChannelEpg());
        ssch.setChannel_id(playerMonitor.getChannelID());
        ssch.setChannel_name(playerMonitor.getChannelName());
        ssch.setChannel_type(playerMonitor.getChannelType());
        ssch.setDrm_time("");
        ssch.setHttp_response("");
        ssch.setIp_server(playerMonitor.getServerURL());
        ssch.setManifest_uri("Maniest URI");
        ssch.setManifest_dwnl_byte("Maniest dwnl byte");
        ssch.setManifest_dwnl_time("Maniest dwnl time");



        ssch.createSSCHPayload();


        e = new EventElement("SSCH", ssch.getPayload());


//        ssch.payLoad_String.add("session Id "); //[0] = "Session ID";
//        ssch.payLoad_String.add(getCurrentTimeStamp());
//        ssch.payLoad_String.add("par 3 ");
        //  ssch.payLoad_String[3] = "par 4";
//            Log.i(TAG, "*******   payload stringhe" + e.payLoad_String);

    }

}



