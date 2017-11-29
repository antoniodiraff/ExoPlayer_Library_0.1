package com.google.android.exoplayer2.demo.Model;


//    SSLINK : Session Start LINK


import java.util.ArrayList;

public class SSLINK extends Start {
    String new_session_id;
    String previos_session_id;

// SSLINK event is always generated after the new SSCH or SSVOD events. If for any reason the previos_session_id is not available, the SSLINK event will not be generated.


    public SSLINK() {
    }

    public  void update() {
        payload = new ArrayList<String>();
        payload.add(new_session_id);
        payload.add(previos_session_id);
    }

    public String getNew_session_id() {
        return new_session_id;
    }

    public void setNew_session_id(String new_session_id) {
        this.new_session_id = new_session_id;
    }

    public String getPrevios_session_id() {
        return previos_session_id;
    }

    public void setPrevios_session_id(String previos_session_id) {
        this.previos_session_id = previos_session_id;
    }
}
