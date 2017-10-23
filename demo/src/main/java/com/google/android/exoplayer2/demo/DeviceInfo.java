package com.google.android.exoplayer2.demo;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.telephony.CellInfo;
import android.util.Log;
import android.webkit.WebSettings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.android.exoplayer2.util.Util;

import java.util.List;
import java.util.UUID;

import static android.R.attr.type;
import static android.net.ConnectivityManager.TYPE_BLUETOOTH;
import static android.net.ConnectivityManager.TYPE_DUMMY;
import static android.net.ConnectivityManager.TYPE_ETHERNET;
import static android.net.ConnectivityManager.TYPE_MOBILE;
import static android.net.ConnectivityManager.TYPE_MOBILE_DUN;
import static android.net.ConnectivityManager.TYPE_VPN;
import static android.net.ConnectivityManager.TYPE_WIMAX;

/**
 * Created by sps on 06/10/2017.
 */
@JsonPropertyOrder({
        "dev_id",
        "dev_uuid",
        "dev_family",
        "user_extid",
        "user_agent",
        "conn",
        "ssid"
})
@JsonIgnoreProperties({
        "uniqueID",
        "PREF_UNIQUE_ID",
        "context",
        "TAG"})
public class DeviceInfo {

    private static String uniqueID = null;
    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";
    String dev_id = "dev_id";
    String dev_uuid = "dev_uuid";
    String dev_family = "dev_family";
    String user_extid = "user_extid";
    String user_agent = "user_agent";
    String conn = "conn";
    String ssid = "ssid";
    Context context;
    public static final String TAG = "*********   DeviceInfo";

    public DeviceInfo(Context c) {

        this.context = c;
        this.dev_id = "";
                //_getDevID();
        this.dev_uuid = uuid(context);
        dev_family = "SMARTPHONE";
        user_extid = "";
        user_agent = "";
                //_getUserAgent();
        conn = _getConnectivity();
        ssid = _getSsid();

//        Log.d(TAG,
//                "********* myDeviceModel [" + myDeviceModel + "] "+
//               "********* MANUFACTURER" + Util.MANUFACTURER+""+
//               "********* DEVICE" + Util.DEVICE+""+
//               "********* DEVICE_DEBUG_INFO" + Util.DEVICE_DEBUG_INFO+""+
//               "********* SDK_INT" + Util.SDK_INT+""+
//               "********* MODEL" + Util.MODEL+
//               "********* USER AGENT" + System.getProperty("http.agent")+
//               "********* USER AGENT " + WebSettings.getDefaultUserAgent(this.c)+
//               "********* MODEL" + Util.MODEL+""

        //    );
    }

    private String _getConnectivity() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        if (null != activeNetwork) {
//            if(activeNetwork.getType() == TYPE_WIFI)
//                return "TYPE_WIFI";
//
//            if(activeNetwork.getType() == TYPE_MOBILE)
//                return "TYPE_MOBILE";
//        }
        switch (activeNetwork.getType()) {
            case TYPE_MOBILE:
                return "MOBILE";
            case ConnectivityManager.TYPE_WIFI:
                return "WIFI";
            case TYPE_MOBILE_DUN:
                return "MOBILE_DUN";
            case TYPE_WIMAX:
                return "WIMAX";
            case TYPE_BLUETOOTH:
                return "BLUETOOTH";
            case TYPE_DUMMY:
                return "DUMMY";
            case TYPE_ETHERNET:
                return "ETHERNET";
            case TYPE_VPN:
                return "VPN";
            default:
                return Integer.toString(activeNetwork.getType());
        }
//        return "TYPE_NOT_CONNECTED";
    }

    private String _getDevID() {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    private String _getUserAgent() {
        String UserAgent = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            UserAgent = WebSettings.getDefaultUserAgent(context);
        }
        return UserAgent;
    }

    private String _getSsid() {
        WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String ssid = wifiInfo.getSSID();
        return ssid;
    }

    public String getDev_id() {
        return dev_id;
    }

    public void setDev_id(String dev_id) {
        this.dev_id = dev_id;
    }

    public String getDev_uuid() {
        return dev_uuid;
    }

    public void setDev_uuid(String dev_uuid) {
        this.dev_uuid = dev_uuid;
    }

    public String getDev_family() {
        return dev_family;
    }

    public void setDev_family(String dev_family) {
        this.dev_family = dev_family;
    }

    public String getUser_extid() {
        return user_extid;
    }

    public void setUser_extid(String user_extid) {
        this.user_extid = user_extid;
    }

    public String getUser_agent() {
        return user_agent;
    }

    public void setUser_agent(String user_agent) {
        this.user_agent = user_agent;
    }

    public String getConn() {
        return conn;
    }

    public void setConn(String conn) {
        this.conn = conn;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    // Random UUID method ---> UTILS
    private String uuid(Context context) {
        if (uniqueID == null) {
            SharedPreferences sharedPrefs = context.getSharedPreferences(
                    PREF_UNIQUE_ID, Context.MODE_PRIVATE);
            uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);
            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(PREF_UNIQUE_ID, uniqueID);
                editor.commit();
            }
        }
        return uniqueID;
    }


}
