package com.codersun.multwaveview;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.os.Bundle;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.codersun.multwaveviewlib.ITransformCanvas;
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
        final WaveView waveView = findViewById(R.id.wave);
        WaveView waveView2 = findViewById(R.id.wave2);
        final WaveView waveView3 = findViewById(R.id.wave3);
        SeekBar seekBar = findViewById(R.id.seekbar);
        List<Integer> colorList = new ArrayList<>();
        colorList.add(Color.parseColor("#55ffffff"));
        colorList.add(Color.parseColor("#550000ff"));


        List<Wave> waves = new ArrayList<>();
        Wave wave1 = new Wave(0, 50, 1, colorList, 3);
        Wave wave2 = new Wave(1f / 4, 50, 2,colorList, 3);
//        Wave wave2 = new Wave(1f / 8, 150, 1, Color.parseColor("#6600FFFF"), 3);
//        Wave wave3 = new Wave(1f / 4, 150, 1, Color.parseColor("#4400FFFF"), 3);
        waves.add(wave1);
        waves.add(wave2);
//        waves.add(wave3);


        waveView.start(WaveArg.build()
                        .setWaveList(waves)
                        .setAutoRise(false)
                        .setIsStroke(false)
                        .setTransform(new ITransformCanvas() {
                            @Override
                            public int transform(Canvas canvas, Path clipPath, int width, int height) {
                                int widthPart = width / 10;
                                int heightPart = height / 10;
                                clipPath.moveTo(1.8f * widthPart, heightPart * 9.2f);
                                clipPath.quadTo(width / 2f, height * 1.03f, widthPart * 8.2f, heightPart * 9.2f);
                                clipPath.lineTo(widthPart * 9.5f, heightPart * 1.2f);
                                clipPath.quadTo(width / 2f, heightPart * 2.1f, widthPart * 0.5f, heightPart * 1.2f);
                                clipPath.close();
                                return 10;
                            }
                        })

//                .setTransformBitmap(BitmapFactory.decodeResource(getResources(),  R.mipmap.ic_drink_big_cup))

        );
        waveView.post(new Runnable() {
            @Override
            public void run() {
                waveView.setHeightPercent(0.8f);
            }
        });


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
        Wave wave1 = new Wave(0, 150, 1, Color.parseColor("#64D0FF"), 3);
        waves.add(wave1);

        waveView2.start(WaveArg.build()
                .setWaveList(waves)
                .setAutoRise(true)
                .setIsStroke(false)
                .setTransformBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.batman_logo))

        );
    }
}

