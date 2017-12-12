package com.sky.telemetry_framework.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;

import static com.sky.telemetry_framework.Observer.getCurrentTimeStamp;


//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonPropertyOrder({"ts", "name", "payload"})
@JsonIgnoreProperties("payloadObject")

public class EventElement {
    public String ts;
    public String name;
    @JsonProperty("payload")
    public ArrayList<String> payload;

    public EventElement(String name, ArrayList<String> payload) {
        this.ts = getCurrentTimeStamp();
        this.name = name;
        this.payload = payload;
    }

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
