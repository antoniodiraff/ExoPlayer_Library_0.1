package com.google.android.exoplayer2.demo.Model;

import java.util.ArrayList;


// SDR : Session Download Resume

public class SDR extends Download {
    String resume_time;

    public SDR() {

    }

    public void updateSDRPayload() {
        payload = new ArrayList<String>();
        payload.add(session_id);
        payload.add(resume_time);


    }

    public String getResume_time() {
        return resume_time;
    }

    public void setResume_time(String resume_time) {
        this.resume_time = resume_time;
    }
}
