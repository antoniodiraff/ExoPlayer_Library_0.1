package com.google.android.exoplayer2.demo;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import static com.google.android.exoplayer2.demo.Observer.getCurrentTimeStamp;

@JsonPropertyOrder({ "ts", "name", "payload" })
public class EventElement {
    String ts ;
    String name;
    PayLoadElement payload;

    public EventElement(String name, PayLoadElement payLoadElement) {
        this.ts = getCurrentTimeStamp();
        this.name = name;
        this.payload = payLoadElement;
    }

    public EventElement(String name) {
        this.name = name;
    }

    public PayLoadElement getPayload() {
        return payload;
    }

    public void setPayload(PayLoadElement payload) {

        this.payload = payload;
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
