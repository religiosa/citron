package com.example.reli.citron;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String plaa = receiveStatus();
                Snackbar.make(view, plaa, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
        startSending();
    }

    public String receiveStatus() {
        IntentFilter statusIntentFilter = new IntentFilter(SendStatus.BROADCAST_ACTION);
        StateReceiver mStateReceiver = new StateReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(mStateReceiver, statusIntentFilter);
        return mStateReceiver.getResultData();
    }

    public void startSending() {
        String dataUrl = "plaaplaaplaa";
        Intent mServiceIntent = new Intent(this, SendService.class);
        mServiceIntent.setData(Uri.parse(dataUrl));

        //starts the intentservice
        this.startService(mServiceIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}