package com.google.android.exoplayer2.demo;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

public class SendDeviceDetails extends AsyncTask<String, Void, String> {

    private static final String TAG = "SendDeviceDetail";
    public int HttpResult;

    @SuppressLint("LongLogTag")
    @Override
    public String doInBackground(String... params) {

        StringBuilder sb = null;
        //  HttpURLConnection httpURLConnection = null;
        //  Invio Json();
        String json = params[1];
        URL url = null;
        try {
            url = new URL(params[0]);

            trustEveryone();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
//        //connection.setInstanceFollowRedirects(false);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("POST");
//          connection.setUseCaches(true);
            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(json);
            wr.flush();
            wr.close();
//            OutputStream os = connection.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(
//                    new OutputStreamWriter(os, "UTF-8"));
//
//            writer.write(json);
////            writer.writeBytes(json);
//
//            writer.flush();
//            writer.close();
//            os.close();
            Log.i(TAG, "***********  JSON... inviato ! ");
            //display what returns the POST request

            sb = new StringBuilder();
             HttpResult = connection.getResponseCode();
            Log.i(TAG, "***********  HTTP RESULT :  " + HttpResult);

            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                Log.i(TAG, "***********    http response SB: " + sb.toString());
                Log.i(TAG, "***********    connection.getResponseMessage().toString(): " + connection.getResponseMessage().toString());
            } else if (HttpResult == HttpURLConnection.HTTP_ACCEPTED) {
                Log.i(TAG, "***********    http response getResponse : " + connection.getResponseMessage().toString());
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.i(TAG, "" + e.toString());
        } catch (UnsupportedEncodingException e) {
            Log.i(TAG, "" + e.toString());
            e.printStackTrace();
        } catch (ProtocolException e) {
            Log.i(TAG, "" + e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.i(TAG, "" + e.toString());
            e.printStackTrace();
        }
//
//            connection = (HttpURLConnection) new URL(params[0]).openConnection();
//            connection.setRequestMethod("POST");
//
//            connection.setDoOutput(true);
//
//            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
//            wr.writeBytes("PostData=" + params[1]);
//            wr.flush();
//            wr.close();
//
//            InputStream in = connection.getInputStream();
//            InputStreamReader inputStreamReader = new InputStreamReader(in);
//
//            int inputStreamData = inputStreamReader.read();
//            while (inputStreamData != -1) {
//                char current = (char) inputStreamData;
//                inputStreamData = inputStreamReader.read();
//                data += current;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (httpURLConnection != null) {
//                httpURLConnection.disconnect();
//            }
//        }
        return sb.toString();
    }

    @Override
    public void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.e("TAG", result);
        // this is expecting a response code to be sent from your server upon receiving the POST data
    }

    private void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception e) { // should never happen
            e.printStackTrace();
        }
    }

}
