package com.google.android.exoplayer2.demo.lib.Model;

import android.content.Context;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.android.exoplayer2.demo.lib.DeviceInfo;

import java.util.ArrayList;

import static com.google.android.exoplayer2.demo.lib.Observer.getCurrentTimeStamp;

/**
 * Created by sps on 06/10/2017.
 */

@JsonPropertyOrder({"msg_ts", "source", "device_info", "events_list"})
@JsonIgnoreProperties({"context"})
public class Event {

    public String msg_ts;
    public String source;
    public DeviceInfo device_info;
    @JsonProperty("events_list")
    public ArrayList<EventElement> events_list;
    public Context context;

    public Event(Context c, DeviceInfo device_info, ArrayList<EventElement> events_list) {

        if (events_list != null) {
            this.events_list = events_list;
        } else {
            this.events_list = new ArrayList<EventElement>();
        }

        this.context = c;
        msg_ts = getCurrentTimeStamp();
        source = "";    ///
        this.device_info = device_info;
    }

    public String getMsg_ts() {
        return msg_ts;
    }

    public void setMsg_ts(String msg_ts) {
        this.msg_ts = msg_ts;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public DeviceInfo getDevice_info() {
        return device_info;
    }

    public void setDevice_info(DeviceInfo device_info) {
        this.device_info = device_info;
    }

    public ArrayList<EventElement> getEvents_list() {
        return events_list;
    }

    public void setEvents_list(ArrayList<EventElement> events_list) {
        this.events_list = events_list;
    }
}



