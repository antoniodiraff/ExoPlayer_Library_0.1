package com.google.android.exoplayer2.demo.Model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;

import static com.google.android.exoplayer2.demo.Observer.getCurrentTimeStamp;

//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonPropertyOrder({"ts", "name", "payload"})
@JsonIgnoreProperties("payloadObject")

public class EventElement {
    String ts;
    String name;
    @JsonProperty("payload")
    ArrayList<String> payload;


    //    PayLoadElement payLoadElement;
//
//    public EventElement(String ts, ArrayList<String> payload_String) {
//        this.ts = ts;
//        this.name = name;
//        this.payload = payload_String;
//
//    }
    public EventElement(String name, ArrayList<String> payload) {
        this.ts = getCurrentTimeStamp();
        this.name = name;
        this.payload = payload;

//        this.payloadObject = payLoadElement;
//        payload= payLoadElement.payload;
    }

    //    public PayLoadElement getPayloadObject() {
//        return payloadObject;
//    }
//
//    public void setPayloadObject(PayLoadElement payloadObject) {
//        this.payloadObject = payloadObject;
//    }
    public void setPayload(ArrayList<String> payload) {
        this.payload = payload;
    }

    public ArrayList<String> getPayload() {
        return payload;
    }

    public void setPayLoad(ArrayList<String> payload) {
        this.payload = payload;
    }


    public EventElement(String name) {
        this.name = name;
    }

//    public PayLoadElement getPayload() {
//        return payloadObject;
//    }
//
//    public void setPayload(PayLoadElement payload) {
//
//        this.payloadObject = payload;
//    }

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
