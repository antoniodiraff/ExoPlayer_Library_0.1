package com.google.android.exoplayer2.demo;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static com.google.android.exoplayer2.demo.Observer.getCurrentTimeStamp;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class EventList {
    ArrayList<EventElement> elementArrayList;

    public EventList(String ts, String name, EventElement eventElement) {

        elementArrayList = new ArrayList<EventElement>();

    }

    public EventList(ArrayList<EventElement> eventElementArrayList) {


        this.elementArrayList = eventElementArrayList;
    }

    public EventList() {

        elementArrayList = new ArrayList<EventElement>();
    }

//    public int size(){
//        return elementArrayList.size();
//    }
//
//    public boolean isEmpty(){
//        return elementArrayList.isEmpty();
//    }

    public void add(EventElement eventElement) {
        elementArrayList.add(eventElement);
    }
}




