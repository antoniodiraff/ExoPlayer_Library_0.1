package com.google.android.exoplayer2.demo.lib;

/**
 * Created by antoniodiraffaele on 01/12/17.
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