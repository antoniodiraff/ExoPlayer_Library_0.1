package com.google.android.exoplayer2.demo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;

import static com.google.android.exoplayer2.demo.Observer.getCurrentTimeStamp;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonPropertyOrder({ "ts", "name", "payload" })
@JsonIgnoreProperties("payloadObject")

public class EventElement {
    String ts ;
    String name;
    PayLoadElement payloadObject;

    @JsonProperty("payLoad")
    ArrayList<String> payLoad;


    public EventElement(String name, PayLoadElement payLoadElement) {
        this.ts = getCurrentTimeStamp();
        this.name = name;
        this.payloadObject = payLoadElement;
        payLoad= payLoadElement.payLoad;
    }

    public PayLoadElement getPayloadObject() {
        return payloadObject;
    }

    public void setPayloadObject(PayLoadElement payloadObject) {
        this.payloadObject = payloadObject;
    }

    public ArrayList<String> getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(ArrayList<String> payLoad) {
        this.payLoad = payLoad;
    }

    public EventElement(String ts, ArrayList<String> payLoad_String) {
        this.ts = ts;
        this.name = name;
        this.payLoad = payLoad_String;

    }

    public EventElement(String name) {
        this.name = name;
    }

    public PayLoadElement getPayload() {
        return payloadObject;
    }

    public void setPayload(PayLoadElement payload) {

        this.payloadObject = payload;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
