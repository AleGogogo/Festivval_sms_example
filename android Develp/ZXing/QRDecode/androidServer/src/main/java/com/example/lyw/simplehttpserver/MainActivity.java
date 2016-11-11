package com.example.lyw.simplehttpserver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
     private SimpleHttpServer simpleHttpServer;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebConfiguration webConfiguration = new WebConfiguration();
        webConfiguration.setPort(8088);
        webConfiguration.setMaxParalles(50);
        simpleHttpServer = new SimpleHttpServer(webConfiguration);
        simpleHttpServer.startAsync();
    }

    @Override
    protected void onDestroy() {
        try {
            simpleHttpServer.stopAsync();
        } catch (IOException e) {
            Log.e(TAG, e.toString() );
        }
        super.onDestroy();
    }
}
