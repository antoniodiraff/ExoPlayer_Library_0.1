package com.sky.telemetry_framework;

/**
 * Created by antoniodiraffaele on 01/12/17.
 */


public enum StreamingType {

    Live(0),
    Vod(1),
    LocalFile(2),
    Restart(3),
    Download(4);

    public final int streamingType;

    StreamingType(int streamingType) {
        this.streamingType = streamingType;
    }

    public int getPauseCode() {
        return streamingType;
    }
}