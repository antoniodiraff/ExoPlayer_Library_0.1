package com.google.android.exoplayer2.demo.Model;

import java.util.ArrayList;

//    @JsonPropertyOrder({
//            "session_id",
//            "closing_time",
//            "viewving_perc"})




//        SPC : Session Playback Close  (playback finished)




public class SPC extends Close {
    String closing_time;
    String viewving_perc;

    public SPC() {
    }
    public void createSPCPayload(){
        payload = new ArrayList<String>();
        payload.add(session_id);
        payload.add(closing_time);
        payload.add(viewving_perc);
    }
}
