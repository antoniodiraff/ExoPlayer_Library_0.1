package com.google.android.exoplayer2.demo;

import android.content.Context;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

import static com.google.android.exoplayer2.demo.Observer.getCurrentTimeStamp;

/**
 * Created by sps on 06/10/2017.
 */

@JsonPropertyOrder({ "msg_ts", "source", "device_info", "eventList" })
@JsonIgnoreProperties({"context"})
public class Event {

    String msg_ts;
    String source;
    DeviceInfo device_info;
    EventList eventList;
    Context context;

    public Event(Context c,EventList eventList, DeviceInfo device_info) {
        this.context=c;
        msg_ts=getCurrentTimeStamp();
        source = "SKYGO_INTV2.0";
        this.eventList = eventList;
        this.device_info = device_info;
    }

//    public DeviceInfo _getDeviceInfo(Context context) {
//
//
//        DeviceInfo d = new DeviceInfo(context);
//        return d;
//    }

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

    public EventList getEventList() {
        return eventList;
    }

    public void setEventList(EventList eventList) {
        this.eventList = eventList;
    }
}



