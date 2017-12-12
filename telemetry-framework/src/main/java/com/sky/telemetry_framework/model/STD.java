package com.sky.telemetry_framework.model;

import java.util.ArrayList;


//    STD : Session sTreaming Download (valid also for VOD download)


public class STD extends Streaming {
    String Layer;
    String bufferSize;
    String fpsDecoded;
    String chunkType;
    String chunkIndex;
    String chunkUri;
    String dwnlByte;
    String dwnlTime;
    String responseTime;

    public STD() {
    }

    public void update() {
        payload = new ArrayList<String>();
        payload.add(sessionId);
        payload.add(Layer);
        payload.add(bufferSize);
        payload.add(fpsDecoded);
        payload.add(chunkType);
        payload.add(chunkIndex);
        payload.add(chunkUri);
        payload.add(dwnlByte);
        payload.add(dwnlTime);
        payload.add(responseTime);
        payload.add(ipServer);
        payload.add(httpResponse);
    }

    public String getLayer() {
        return Layer;
    }

    public void setLayer(String layer) {
        Layer = layer;
    }

    public String getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(String bufferSize) {
        this.bufferSize = bufferSize;
    }

    public String getFpsDecoded() {
        return fpsDecoded;
    }

    public void setFpsDecoded(String fpsDecoded) {
        this.fpsDecoded = fpsDecoded;
    }

    public String getChunkType() {
        return chunkType;
    }

    public void setChunkType(String chunkType) {
        this.chunkType = chunkType;
    }

    public String getChunkIndex() {
        return chunkIndex;
    }

    public void setChunkIndex(String chunkIndex) {
        this.chunkIndex = chunkIndex;
    }

    public String getChunkUri() {
        return chunkUri;
    }

    public void setChunkUri(String chunkUri) {
        this.chunkUri = chunkUri;
    }

    public String getDwnlByte() {
        return dwnlByte;
    }

    public void setDwnlByte(String dwnlByte) {
        this.dwnlByte = dwnlByte;
    }

    public String getDwnlTime() {
        return dwnlTime;
    }

    public void setDwnlTime(String dwnlTime) {
        this.dwnlTime = dwnlTime;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }
}
