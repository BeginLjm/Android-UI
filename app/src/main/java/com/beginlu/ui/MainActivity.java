package com.beginlu.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

import com.beginlu.arcseekbar.ArcSeekBar;

public class MainActivity extends AppCompatActivity {

    private ArcSeekBar arcSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.arcSeekBar = (ArcSeekBar) findViewById(R.id.rounded_seek_bar);
        arcSeekBar.setOnArcSeekBarChangeListener(new ArcSeekBar.OnArcSeekBarChangeListener() {
            @Override
            public void onProgressChanged(ArcSeekBar arcSeekBar, float progress, boolean fromUser) {
                Log.d("Main", progress + "..." + fromUser);
            }

            @Override
            public void onStartTrackingTouch(ArcSeekBar arcSeekBar) {
                Log.d("Main", "start");
            }

            @Override
            public void onStopTrackingTouch(ArcSeekBar arcSeekBar) {
                Log.d("Main", "stop");
            }
        });
    }
}
