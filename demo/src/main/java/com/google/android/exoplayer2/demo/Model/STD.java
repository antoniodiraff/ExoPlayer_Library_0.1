package com.google.android.exoplayer2.demo.Model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;


//    STD : Session sTreaming Download (valid also for VOD download) STP : Session sTreaming Pause

public class STD extends Streaming {
    String Layer;
    String buffer_size;
    String fps_decoded;
    String chunk_type;
    String chunk_index;
    String chunk_uri;
    String dwnl_byte;
    String dwnl_time;
    String response_time;

    public STD() {
    }

    public void updateSTDPayload() {
        payload = new ArrayList<String>();
        payload.add(session_id);
        payload.add(Layer);
        payload.add(buffer_size);
        payload.add(fps_decoded);
        payload.add(chunk_type);
        payload.add(chunk_index);
        payload.add(chunk_uri);
        payload.add(dwnl_byte);
        payload.add(dwnl_time);
        payload.add(response_time);
        payload.add(ip_server);
        payload.add(http_response);
    }

    public String getLayer() {
        return Layer;
    }

    public void setLayer(String layer) {
        Layer = layer;
    }

    public String getBuffer_size() {
        return buffer_size;
    }

    public void setBuffer_size(String buffer_size) {
        this.buffer_size = buffer_size;
    }

    public String getFps_decoded() {
        return fps_decoded;
    }

    public void setFps_decoded(String fps_decoded) {
        this.fps_decoded = fps_decoded;
    }

    public String getChunk_type() {
        return chunk_type;
    }

    public void setChunk_type(String chunk_type) {
        this.chunk_type = chunk_type;
    }

    public String getChunk_index() {
        return chunk_index;
    }

    public void setChunk_index(String chunk_index) {
        this.chunk_index = chunk_index;
    }

    public String getChunk_uri() {
        return chunk_uri;
    }

    public void setChunk_uri(String chunk_uri) {
        this.chunk_uri = chunk_uri;
    }

    public String getDwnl_byte() {
        return dwnl_byte;
    }

    public void setDwnl_byte(String dwnl_byte) {
        this.dwnl_byte = dwnl_byte;
    }

    public String getDwnl_time() {
        return dwnl_time;
    }

    public void setDwnl_time(String dwnl_time) {
        this.dwnl_time = dwnl_time;
    }

    public String getResponse_time() {
        return response_time;
    }

    public void setResponse_time(String response_time) {
        this.response_time = response_time;
    }
}
