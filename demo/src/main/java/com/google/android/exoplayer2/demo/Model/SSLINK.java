package com.google.android.exoplayer2.demo.Model;


//    SSLINK : Session Start LINK


// SSLINK event is always generated after the new SSCH or SSVOD events. If for any reason the previos_session_id is not available, the SSLINK event will not be generated.




public class SSLINK extends Start {
    String new_session_id;
    String previos_session_id;

    public SSLINK() {
    }

}
