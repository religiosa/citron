package com.example.reli.citron;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by reli on 3.4.2017.
 */

public class SendService extends IntentService {

    public SendService() {
        super("SendService");
    };

    private String status;

    @Override
    protected void onHandleIntent(Intent workIntent) {
        // Gets data from the incoming Intent
        String dataString = workIntent.getDataString();

        try {
            URL url = new URL("http://reli.kumkvatti.fi/");

            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            try {
                urlConn.setDoOutput(true);
                urlConn.setChunkedStreamingMode(0);

                OutputStream out = new BufferedOutputStream(urlConn.getOutputStream());
                InputStream in = new BufferedInputStream(urlConn.getInputStream());

                out.write(dataString.getBytes());
                Scanner scanner = new Scanner(in);
                status = scanner.next();
                Intent localIntent = new Intent(SendStatus.BROADCAST_ACTION).putExtra(SendStatus.EXTENDED_DATA_STATUS, status);
                LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);

            } finally {
                urlConn.disconnect();
            }

        } catch (Exception e) {

        }

    }
}
