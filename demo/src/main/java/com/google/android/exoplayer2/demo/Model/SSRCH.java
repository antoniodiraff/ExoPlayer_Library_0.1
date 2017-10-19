package com.google.android.exoplayer2.demo.Model;
import java.util.ArrayList;


public class SSRCH extends LiveCH {
    String restart_sec;
    String original_session_id;



    public SSRCH() {
        }
        public ArrayList<String> updateSSRCHPayload() {
            payload = new ArrayList<String>();
            payload.add(session_id);
            payload.add(original_session_id);
            payload.add(start_time);
            payload.add(drm_time);
            payload.add(buffering_time);
            payload.add(playback_start_time);
            payload.add(ip_server);
            payload.add(manifest_uri);
            payload.add(manifest_dwnl_byte);
            payload.add(manifest_dwnl_time);
            payload.add(http_response);
            payload.add(channel_id);
            payload.add(channel_type);
            payload.add(channel_epg);
            payload.add(channel_name);
            payload.add(restart_sec);
            return payload;

//         "session_id",
            // original_session_id   *** only for ssrch
//         "start_time",
//         "drm_time",
//         "buffering_time",
//         "playback_start_time",
//         "ip_server",
//         "manifest_uri",
//         "manifest_dwnl_byte",
//         "manifest_dwnl_time",
//         "http_response",
//         "channel_id",
//         "channel_type",
//         "channel_epg",
//         "channel_name",
//        // "restart_sec"   *** only for ssrch
        }
    public String getRestart_sec() {
        return restart_sec;
    }

    public void setRestart_sec(String restart_sec) {
        this.restart_sec = restart_sec;
    }

    public String getOriginal_session_id() {
        return original_session_id;
    }

    public void setOriginal_session_id(String original_session_id) {
        this.original_session_id = original_session_id;
    }

    }
