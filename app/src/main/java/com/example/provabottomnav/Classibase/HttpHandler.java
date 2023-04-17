package com.example.provabottomnav.Classibase;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class HttpHandler {
    //private static final String TAG= it.esempiandroid.asynctest.HttpHandler.class.getSimpleName();
    //private HttpsURLConnection conn;
    private String TAG = "GestioneErrore";
    private HttpURLConnection conn;

    public HttpHandler() {
    }

    //https://innovazionetop.com/test_data/persone_2.json
    public String makeServiceCall(String reqUrl) {
        String response = null;

        try {
            //1.
            URL url = new URL(reqUrl);

            conn = dataSnapshot.getValue();
            conn.setRequestMethod("GET");
            //conn.connect();

            //2.
            //Read the response.
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);

            //3.
            Map<String, List<String>> headers = conn.getHeaderFields();

            Log.i(TAG, "headers: " + headers);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        } finally {
            Log.i(TAG, "finally, Disconnect()");

            conn.disconnect();
        }

        return response;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}