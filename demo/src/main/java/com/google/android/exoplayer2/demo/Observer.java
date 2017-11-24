package com.google.android.exoplayer2.demo;

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
import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Time;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;


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
    private final int serverTimeout;
    private final int dequeueingIntervalTime;

    //*** MODEL initialization ***
    public SSCH ssch = null;
    public SSRCH ssrch = null;
    public STCL stcl = null;
    public SDC sdc = null;
    public SDD sdd = null;
    public SE se = null;
    public SC sc = null;
    public SDR sdr;
    public STRB strb = null;
    public SSVOD ssvod = null;
    public ASI asi = null;
    public ACI aci = null;
    public SSPVOD sspvod = null;
    public SSDVOD ssdvod = null;
    public SPC spc = null;
    public SDP sdp = null;
    public STR str;
    public STP stp;
    public STD std;

    // *** LOCAL variables ***
    public Timer t;
    boolean isTheFirstTime = true;
    boolean isTheFirstTime_Buffering = true;
    boolean isTheFirstTime_Ready = true;
    private String oldBitRate;
    public Context c;
    public Event event;
    public EventElement e = null;
    public EventElement e_ = null;
    public String drm_time = "";
    private String bufferingTimeStamp = null;

    // *** VARIABLES needed 4 constructor ***
    String URL = "";
    boolean isLive = false;
    boolean isLocalFile = true;
    boolean isRestart = false;
    String originalSessionId = "";
    String restartSec = "";
    boolean isFree;
    String channelName = "";
    String channelID = "";
    String channelType = "";
    String vodID = "";
    String VODTitle = "";
    String assetType = "";
    String assetPath = "";
    public Timer _t;
    public String sessionID = "";
    PlayerMonitor playerMonitor;
    SimpleExoPlayer player;
    private boolean isFirstTime_Download = true;
    private boolean play = true;
    private String delay_time_sec;


    public Observer(final MappingTrackSelector trackSelector, final PlayerMonitor playerMonitor, String sessionID,
                    boolean isLive, boolean isLocalFile, boolean isRestart, String originalSessionId, String restartSec,
                    boolean isFree, String channelName, String channelID, String channelType, String vodID,
                    String VODTitle, String assetType, String assetPath) {


        this.playerMonitor = playerMonitor;
        this.c = playerMonitor.c;
        dequeueingIntervalTime = playerMonitor.dequeueingIntervalTime;
        this.URL = playerMonitor.serverURL;
        this.serverTimeout = playerMonitor.serverTimeout;
        this.isLive = isLive;
        this.isLocalFile = isLocalFile;
        this.isRestart = isRestart;
        this.originalSessionId = originalSessionId;
        this.restartSec = restartSec;
        this.isFree = isFree;
        this.channelName = channelName;
        this.channelID = channelID;
        this.channelType = channelType;
        this.vodID = vodID;
        this.VODTitle = VODTitle;
        this.assetType = assetType;
        this.assetPath = assetPath;
        this.trackSelector = trackSelector;
        window = new Timeline.Window();
        period = new Timeline.Period();
        startTimeMs = SystemClock.elapsedRealtime();
        this.sessionID = sessionID;
    }


    public void createNewEvent(Context c) {
        ArrayList<EventElement> eventList = new ArrayList<EventElement>();
        DeviceInfo deviceInfo = new DeviceInfo(c);
        this.event = new Event(c, deviceInfo, eventList);
        this.event.setSource(playerMonitor.source);
        this.event.device_info.setUser_extid(playerMonitor.user_extid);

//        event.device_info.user_agent = playerMonitor.userAgent;
//        event.device_info.dev_id = playerMonitor.dev_id;
    }

    /*
    Loading Percentage
     */

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

    /* Player.EventListener
     */
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
                buffering_CallBack(getCurrentTimeStamp(), this.event);
                break;
            case Player.STATE_READY:
                stateString = "Player.STATE_READY     -";
                if (!isTheFirstTime_Ready) {
                    if (play == false) {
                        play = true;
                        Log.i(TAG, " ***********************   il player è in PLAY");

                    } else {
                        play = false;
                        Log.i(TAG, " ***********************   il player è in PAUSA");

                    }
                }
                ready_CallBack(getCurrentTimeStamp(), this.event, play);
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
                }
                Log.d(TAG, "    ]");
            }
            Log.d(TAG, "  ]");
        }
        Log.d(TAG, "]");
//        onTracksChangedCallback(getCurrentTimeStamp(),event, );
    }

    private void onTracksChangedCallback() {
//
    }


    /* MetadataRenderer.Output
     */
    @Override
    public void onMetadata(Metadata metadata) {
        Log.d(TAG, "****************    onMetadata [");
        printMetadata(metadata, "  ");
        Log.d(TAG, "]");
    }

    /* AudioRendererEventListener
     */
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

    /* VideoRendererEventListener
     */

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

        //SC : Session Close
     /*   if (sc == null) {
            sc = new SC();
            sc.setSession_id("Session Close");
            sc.setClosing_time(getCurrentTimeStamp());

            sc.updateSCPayload();
            e = new EventElement("SC", sc.getPayload());
            event.events_list.add(e);
        }*/
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

    /* DefaultDrmSessionManager.EventListener
     */

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

    private void drmKeysLoaded_callback(String drm_time) {
        Log.d(TAG, " ********** drmKeysLoaded_callback");

        if (ssch != null) {
            ssch.setDrm_time(drm_time);
        } else if (ssrch != null) {
            ssrch.setDrm_time(drm_time);
        } else if (ssvod != null) {
            ssvod.setDrm_time(drm_time);
        } else if (ssdvod != null) {
            ssdvod.setDrm_time(drm_time);
        }
    }

    /*  ExtractorMediaSource.EventListener
    */
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
    }

    @Override
    public void onLoadCanceled(DataSpec dataSpec, int dataType, int trackType, Format trackFormat,
                               int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs,
                               long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded) {
        // Do nothing.
        Log.d(TAG, " ********** ONLOAD CANCELED " + "        DATI :   ");
//        Log.d(TAG, "dataSpec " + dataSpec);
//        Log.d(TAG, " dataType" + dataType);
//        Log.d(TAG, " trackType " + trackType);
//        Log.d(TAG, " trackFormat " + trackFormat);
//        Log.d(TAG, "trackSelectionReason  " + trackSelectionReason);
//        Log.d(TAG, " trackSelectionData " + trackSelectionData);
//        Log.d(TAG, "mediaStartTimeMs  " + mediaStartTimeMs);
//        Log.d(TAG, " mediaEndTimeMs " + mediaEndTimeMs);
//        Log.d(TAG, " elapsedRealtimeMs  " + elapsedRealtimeMs);
//        Log.d(TAG, " loadDurationMs " + loadDurationMs);
//        Log.d(TAG, " bytesLoaded " + bytesLoaded);
        // onLoadCanceled_callback(getCurrentTimeStamp());
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

    /*
     Internal methods
      */

    public void printInternalError(String type, Exception err) throws IOException {
        Log.d(TAG, " ********** printInternalError");
        Log.e(TAG, "internalError [" + getSessionTimeString() + ", " + type + "]", err);
        SE(type);
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

    private void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception e) { // should never happen
            e.printStackTrace();
        }
    }

    public void sendJSON(Event event, String serverURL) throws IOException {
        try {
           String json= fromObjectToJSON(event); createNewEvent(c); stampaJSON(json); send(json);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return;
    }

    public void send(String json) {
        SendDeviceDetails async = new SendDeviceDetails();
        async.execute(playerMonitor.getServerURL(), json);
        t.cancel();
        t = new Timer();
        t.scheduleAtFixedRate(newTask(), dequeueingIntervalTime, dequeueingIntervalTime);

        //t.scheduleAtFixedRate(newTask(), dequeueingIntervalTime, dequeueingIntervalTime);
    }

    public void  stampaJSON(String jsonInString) {
        Log.d(TAG, "****************** sto inviando il JSON :  " + jsonInString);
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


    public String fromObjectToJSON(ArrayList<EventElement> list) throws IOException {
        String jsonInString = null;
        ObjectMapper mapper = new ObjectMapper();
//      mapper.enable(SerializationFeature.INDENT_OUTPUT);
        jsonInString = mapper.writeValueAsString(list);
        return jsonInString;
    }

    public String fromObjectToJSON(Object object) throws IOException {
        String jsonInString = null;
        ObjectMapper mapper = new ObjectMapper();
//      mapper.enable(SerializationFeature.INDENT_OUTPUT);
        jsonInString = mapper.writeValueAsString(event);
        return jsonInString;
    }

    //    private void updateSSRCH(String bufferingTime) {
    //
    //        ssrch.setBuffering_time(bufferingTime);
    //        ssrch.updateSSRCHPayload();
    //
    //    }

    /*                  *****      EVENTS      *****

        StartUP / Closing Player Event
     */
    private void ASI(String currentTimeStamp, Event event) {
        asi = new ASI();
        asi.setStart_time(currentTimeStamp);
        asi.setCodice_cliente(playerMonitor.codice_cliente);
        asi.setDevice_model(playerMonitor.device_model);
        asi.setDevice_so(playerMonitor.device_so);
        asi.setDevice_vendor(playerMonitor.device_vendor);
        asi.updateASIPayload();
        event.events_list.add(new EventElement("ASI", asi.getPayload()));
    }

    private void ACI(Event event, String currentTimeStamp) {
        aci = new ACI();
        aci.setStop_time(currentTimeStamp);

        aci.updateACIPayload();
        event.events_list.add(new EventElement("ACI", aci.getPayload()));
    }


    /* Start Events
     */
    private void SSCH(String currentTimeStamp, Event event) {
        sc = null;
        ssch = new SSCH();
        ssch.setSession_id(sessionID);
        ssch.setStart_time(currentTimeStamp);
        ssch.setPlayback_start_time("");
        ssch.setBuffering_time("");
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

        ssch.updateSSCHPayload();

        event.events_list.add(new EventElement("SSCH", ssch.getPayload()));
        //updateEventList(e, e_);
    }

    private void SSRCH(String currentTimeStamp, Event event) {
        ssrch = new SSRCH();

        ssrch.setSession_id(sessionID);
        ssrch.setOriginal_session_id(originalSessionId);
        ssrch.setStart_time(currentTimeStamp);
        ssrch.setPlayback_start_time(currentTimeStamp);
        ssrch.setBuffering_time(bufferingTimeStamp);
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

        sspvod.updateSSPVODPayload();
        event.events_list.add(new EventElement("SSPVOD", sspvod.getPayload()));
    }


    /* Download Events
     */
    private void SDP(String pauseCause, String currentTimeStamp, Event event) {
        //SDP : Session Download Pause
        sdp = new SDP();
        sdp.setSession_id(sessionID);
        sdp.setDownload_perc(String.valueOf(player.getBufferedPercentage()));
        sdp.setPause_cause(pauseCause);
        sdp.setPause_time(currentTimeStamp);
        // sdd.setDelete_time();

        sdp.updateSDPPayload();

        e = new EventElement("SDP", sdp.getPayload());
        event.events_list.add(e);
    }

    private void SDR(String currentTimeStamp, Event event) {
        //SDR : Session Download Resume

        sdr = new SDR();
        sdr.setSession_id(sessionID);
        sdr.setResume_time(currentTimeStamp);

        sdr.updateSDRPayload();
        e = new EventElement("SDR", sdr.getPayload());
        event.events_list.add(e);
    }

    private void SDD(String currentTimeStamp, Event event) {
        //SDD : Session Download Delete (download removed)
        sdd = new SDD();
        sdd.setSession_id(sessionID);
        sdd.setDelete_time(currentTimeStamp);

        sdd.updateSDDPayload();

        e = new EventElement("SDD", sdd.getPayload());
        event.events_list.add(e);
    }

    /*
    Playback Events
     */


    /* Streaming Events
     */
    private void STRB(String currentTimeStamp, Event event) {
        strb = new STRB();
        strb.setRebuffering_start_time(getCurrentTimeStamp());
        strb.setRebuffering_end_time("");

        strb.updateSTRBPayload();
        if (event != null) {
            event.events_list.add(new EventElement("STRB", strb.getPayload()));
        } else {
            Log.d(TAG, "****************** evento nullo ");
        }
    }

    private void STCL(String currentTimeStamp, Event event, Format trackFormat) {
        stcl = new STCL();
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

    private void STR(String currentTimeStamp, Event event) {
        str = new STR();
        str.setSession_id(sessionID);
        str.setRestart_time(currentTimeStamp);

        str.updateSTRPayload();
        event.events_list.add(new EventElement("STR", str.getPayload()));
    }

    private void STP(String currentTimeStamp, Event event) {
        stp = new STP();
        stp.setSession_id(sessionID);
        stp.setPause_time(currentTimeStamp);

        stp.updateSTPPayload();
        event.events_list.add(new EventElement("STP", stp.getPayload()));
    }

    private void STD(String currentTimeStamp, Event event, DataSpec dataSpec, int dataType, int trackType, Format trackFormat,
                     int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs,
                     long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded) {

        std = new STD();
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


    /* Error Events
     */
    public void SE(String type) {

        se = new SE();
        se.setErrorType(type);
        se.setSession_id(sessionID);


        se.updateSEPayload();
        e = new EventElement("Session Error", se.getPayload());
        event.events_list.add(e);
        sendEventQueue();
    }

    /* Close Events
     */
    public void SC(String currentTimeStamp) {
        sc = new SC();
        sc.setSession_id(sessionID);
        sc.setClosing_time(getCurrentTimeStamp());
        sc.updateSCPayload();

        event.events_list.add(new EventElement("SC", sc.getPayload()));
    }

    private void SDC(String currentTimeStamp, Event event) {
        //Session downloaded completed
        sdc = new SDC();
        sdc.setSession_id(sessionID);
        sdc.setCompleted_time(currentTimeStamp);

        sdc.updateSDCPayload();

        event.events_list.add(new EventElement("SDC", sdc.getPayload()));
    }

    private void SPC(String currentTimeStamp) {
        spc = new SPC();
        spc.setClosing_time(currentTimeStamp);
        spc.setViewving_perc(String.valueOf(player.getBufferedPercentage()));
        spc.updateSPCPayload();

        event.events_list.add(new EventElement("SPC", spc.getPayload()));
        Log.i(TAG, "************   percentage " + String.valueOf(player.getBufferedPercentage()));
    }

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
    }


    /* **** STATE callbacks --- IDLE - READY - BUFFERING - ENDED ****

     */
    private void onLoadCompletedCallback(String currentTimeStamp, Event event, DataSpec dataSpec, int dataType, int trackType, Format trackFormat,
                                         int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs,
                                         long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded) {
        if (trackFormat != null) {
            Log.d(TAG, " ********** onLoadCompletedCallback" + trackFormat.toString());
        }

        if (event != null) {
            STD(currentTimeStamp, event, dataSpec, dataType, trackType, trackFormat,
                    trackSelectionReason, trackSelectionData, mediaStartTimeMs,
                    mediaEndTimeMs, elapsedRealtimeMs, loadDurationMs, bytesLoaded);
        } else {
            Log.d(TAG, " ********** event = null ....non dovrebbe mai succedere");

        }
    }

    private void buffering_CallBack(String currentTimeStamp, Event event) {

        Log.d(TAG, " ********** buffering_CallBack");

        if (isTheFirstTime_Buffering) {

            if (ssch != null) {
                ssch.setBuffering_time(currentTimeStamp);
            } else if (ssvod != null) {
                ssvod.setBuffering_time(currentTimeStamp);
            } else if (sspvod != null) {
                sspvod.setBuffering_time(currentTimeStamp);
            }
            bufferingTimeStamp = currentTimeStamp;
            isTheFirstTime_Buffering = false;
        } else {
            if (event != null) {
                STRB(currentTimeStamp, event);
            }
        }
    }

    private void idle_CallBack(String currentTimeStamp) {
        Log.d(TAG, " ********** idle_CallBack");
        if (isTheFirstTime) {
            isTheFirstTime = false;
            // if is live ch
//            SSCH(currentTimeStamp);
//
//            // if is vod
//            SSVOD(currentTimeStamp);

//            isTheFirstTime = false;
        }
    }

    private void ready_CallBack(String currentTimeStamp, Event event, boolean play) {

        Log.d(TAG, " ********** ready_CallBack");
        if (strb != null) {
            strb.setRebuffering_end_time("");
            strb.updateSTRBPayload();
        }
        if (isTheFirstTime_Ready) {
            if (isLive) {
                if (ssch != null) {
                    ssch.setPlayback_start_time(getCurrentTimeStamp());
                }
            } else if (isLocalFile) {
                if (sspvod != null) {
                    sspvod.setPlayback_start_time(getCurrentTimeStamp());
                }
            } else if (isRestart) {
                if (ssrch != null) {
                    ssrch.setPlayback_start_time(getCurrentTimeStamp());
                }
            }
            isTheFirstTime_Ready = false;
        } else {
            if (play) {
                STR(getCurrentTimeStamp(), event);
            } else if (!play) {
                // pause();
                STP(getCurrentTimeStamp(), event);
            }
        }
    }

    private void ended_CallBack(String currentTimeStamp) {
        Log.d(TAG, " ********** ended_CallBack");

        if (isLocalFile) {
            if (this.event != null) {
                SPC(currentTimeStamp);
            }
        } else if (sc == null) {
            if (this.event != null) {
                SC(currentTimeStamp);
            }
        }
        ACI(this.event,getCurrentTimeStamp());
        sendEventQueue();
        t.cancel();
    }

    /*
    STCL
    **/

    private void onDownstreamFormatChanged_callback(Event event, String currentTimeStamp, Format trackFormat) {

        if (event != null) {
            STCL(currentTimeStamp, event, trackFormat);
        }
    }

    /*
                        *****  API *****
        - Call these methods from the PLAYER Activity / Fragment  -
     */


    private Event createStartEvent() {

        if (isLocalFile) {
            SSPVOD(getCurrentTimeStamp(), event);
        } else if (isLive) {
            if (isTheFirstTime) {
                if (isRestart) {
                    SSRCH(getCurrentTimeStamp(), event);
                } else {
                    SSCH(getCurrentTimeStamp(), event);
                }
            }
        } else {
            SSVOD(getCurrentTimeStamp(), event);
        }
        return event;
    }

    private TimerTask newTask() {
        return new TimerTask() {
            @Override
            public void run() {
                Log.d(TAG, "********TIMER ");

                //  Observer o = new Observer(trackSelector, c);
                if (isTheFirstTime) {
                    isTheFirstTime = false;
                } else {
                    if (event != null) {
                        if (event.events_list.size() > 0) {
                            sendEventQueue();
                        } else {
                            Log.d(TAG, "******** La lista degli eventi è vuota quindi non invio il JSON : ");
                            try {
                                stampaJSON(fromObjectToJSON(event.events_list));
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            }
        };
    }

    public void sendEventAndEmptyOutEvent(Event event, PlayerMonitor playerMonitor) {
        try {
            sendJSON(event, playerMonitor.getServerURL());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        //this.event = null;
      //  createNewEvent(c);
    }

    //ssdvod

    public void sessionStartDownloadVOD() {
        if (event != null) {
            SSDVOD(getCurrentTimeStamp(), event);
        }
    }

    /* TODO

       Download
       SDR
       SDD
   */

    public void sessionDownloadPauseWithPauseCause(PauseCause pauseCause) {

        // pauseCause: possible causes of pause
        // See Enum Class PauseCause

        // 001: Network error
        // 002: No more space
        // 003: Voluntary pause
        // 004: Application put in background
        // 005: Title queued
        if (event != null) {
            SDP(pauseCause.getPauseCode(), getCurrentTimeStamp(), event);
        }
    }

    public void sessionDownloadResume() {
        if (event != null) {
            SDR(getCurrentTimeStamp(), event);
        }
    }

    public void sessionDownloadDelete() {
        if (event != null) {
            SDD(getCurrentTimeStamp(), event);
        }
    }

    public void sessionStartDownloadVOD(String currentTimeStamp) {
        if (event != null) {
            if (isFirstTime_Download) {
                isFirstTime_Download = false;
                SSDVOD(currentTimeStamp, event);
            }
        }
    }

    public void sessionDownloadVODCompleted() {
        if (event != null) {
            SDC(getCurrentTimeStamp(), event);
        }
    }

    public void becomeActive(SimpleExoPlayer player) {
        
        STR(getCurrentTimeStamp(), this.event);

        isTheFirstTime_Ready = false;
        isTheFirstTime_Buffering = false;
        isFirstTime_Download = false;
        isTheFirstTime = false;

        this.player = player;

     /*   if (event == null) {
          //  event = null;
            event = createNewEvent(c);
        }*/
    }

    public void pause() {
        STP(getCurrentTimeStamp(), this.event);
        sendEventQueue();
    }

    public void terminate() {
        ACI(event, getCurrentTimeStamp());
    }

    public void startSession(SimpleExoPlayer player) {
        this.player = player;
        createNewEvent(c);

        ASI(getCurrentTimeStamp(), this.event);
        this.event = createStartEvent();

        // TIMEout TRIGGER
        t = new Timer();
        t.scheduleAtFixedRate(newTask(), dequeueingIntervalTime, dequeueingIntervalTime);

    }

    public void stopSession() {
        ended_CallBack(getCurrentTimeStamp());
    }


    //per salvare la coda di eventi
    public void saveAll(Activity activity) {
          SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
               SharedPreferences.Editor editor = sharedPref.edit();
        try {
            editor.putString("event", fromObjectToJSON(event)  );
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        editor.commit();
    }

    //SEND EVENT QUEUE
    public synchronized void sendEventQueue() {
                sendEventAndEmptyOutEvent(this.event, this.playerMonitor);
    }

    /*
e.g.
sessionDownloadPauseWithPauseCause(PauseCause.NetworkError);
*/
    /*
      ENUM PauseCause
     */
    public enum PauseCause {

        NetworkError("001"),
        NoMoreSpace("002"),
        VoluntaryPause("003"),
        ApplicationPutInBackgroud("004"),
        TitledQueued("005");

        public final String pauseCode;

        PauseCause(String pauseCode) {
            this.pauseCode = pauseCode;
        }

        public String getPauseCode() {
            return pauseCode;
        }
    }
}