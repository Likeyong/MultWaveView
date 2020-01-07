package com.codersun.multwaveview;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.codersun.multwaveviewlib.Wave;
import com.codersun.multwaveviewlib.WaveArg;
import com.codersun.multwaveviewlib.WaveView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		WaveView waveView = findViewById(R.id.wave);
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
	}
}

