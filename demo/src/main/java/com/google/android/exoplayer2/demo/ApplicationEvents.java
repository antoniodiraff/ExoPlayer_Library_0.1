package com.google.android.exoplayer2.demo;

/**
 * Created by antoniodiraffaele on 18/10/17.
 */

public class ApplicationEvents {
}

class ACI extends ApplicationEvents {

    String start_time;
    String device_vendor;
    String device_model;
    String device_so;
    String codice_cliente;

    public ACI() {

    }

    public ACI(String start_time, String device_vendor, String device_model, String device_so, String codice_cliente) {
        this.start_time = start_time;
        this.device_vendor = device_vendor;
        this.device_model = device_model;
        this.device_so = device_so;
        this.codice_cliente = codice_cliente;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getDevice_vendor() {
        return device_vendor;
    }

    public void setDevice_vendor(String device_vendor) {
        this.device_vendor = device_vendor;
    }

    public String getDevice_model() {
        return device_model;
    }

    public void setDevice_model(String device_model) {
        this.device_model = device_model;
    }

    public String getDevice_so() {
        return device_so;
    }

    public void setDevice_so(String device_so) {
        this.device_so = device_so;
    }

    public String getCodice_cliente() {
        return codice_cliente;
    }

    public void setCodice_cliente(String codice_cliente) {
        this.codice_cliente = codice_cliente;
    }
}

class ASI extends ApplicationEvents {

    String stop_time;

    public ASI() {

    }

    public ASI(String stop_time) {
        this.stop_time = stop_time;
    }

    public String getStop_time() {
        return stop_time;
    }

    public void setStop_time(String stop_time) {
        this.stop_time = stop_time;
    }
}

