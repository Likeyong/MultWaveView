package com.codersun.multwaveview;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.codersun.multwaveviewlib.Wave;
import com.codersun.multwaveviewlib.WaveArg;
import com.codersun.multwaveviewlib.WaveView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WaveView waveView = findViewById(R.id.wave);
        WaveView waveView2 = findViewById(R.id.wave2);
        final WaveView waveView3 = findViewById(R.id.wave3);
        SeekBar seekBar = findViewById(R.id.seekbar);
        List<Wave> waves = new ArrayList<>();
        Wave wave1 = new Wave(0, 150, 1, Color.parseColor("#00FFFF"), 3);
        Wave wave2 = new Wave(1f / 8, 150, 1, Color.parseColor("#6600FFFF"), 3);
        Wave wave3 = new Wave(1f / 4, 150, 1, Color.parseColor("#4400FFFF"), 3);
        waves.add(wave1);
        waves.add(wave2);
        waves.add(wave3);

        waveView.start(WaveArg.build()
                .setWaveList(waves)
                .setAutoRise(false)
                .setIsStroke(false)
                .setTransformBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.batman_logo))

        );


        setUpWaveView2(waveView2);
        setUpWaveView3(waveView3);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float mult = 1 + i / 100f;
                waveView3.setWaveHeightMultiple(mult);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setUpWaveView3(WaveView waveView3) {
        List<Wave> waves = new ArrayList<>();
        Wave wave1 = new Wave(0, 150, 1, Color.parseColor("#000000"), 3);
        Wave wave2 = new Wave(0, 150, 3, Color.parseColor("#000000"), 3);
        Wave wave3 = new Wave(0, 150, 2, Color.parseColor("#000000"), 3);
        waves.add(wave1);
        waves.add(wave2);
        waves.add(wave3);

        waveView3.start(WaveArg.build()
                .setWaveList(waves)
                .setAutoRise(false)
                .setIsStroke(true)

        );
    }

    private void setUpWaveView2(WaveView waveView2) {
        List<Wave> waves = new ArrayList<>();
        Wave wave1 = new Wave(0, 150, 1, Color.parseColor("#00FFFF"), 3);
        waves.add(wave1);

        waveView2.start(WaveArg.build()
                .setWaveList(waves)
                .setAutoRise(true)
                .setIsStroke(false)
                .setTransformBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.batman_logo))

        );
    }
}

