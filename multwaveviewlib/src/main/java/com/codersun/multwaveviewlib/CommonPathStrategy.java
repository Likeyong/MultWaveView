package com.codersun.multwaveviewlib;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * @author codersun
 */
public class CommonPathStrategy implements IDrawPathStrategy
{

	@Override
	public int onPreDrawPath(Canvas canvas, Bitmap bgBitmap, Paint paint, int viewWidth, int viewHeight)
	{
		return 0;
	}

	@Override
	public void calculatePath(Path path, Wave wave, float viewWidth, float heightMultiple)
	{
		path.rQuadTo(viewWidth / 4, -wave.waveHeight * heightMultiple, viewWidth / 2, 0);
		path.rQuadTo(viewWidth / 4, wave.waveHeight * heightMultiple, viewWidth / 2, 0);

	}

	@Override
	public void closePath(Paint paint, Path path, Wave wave, int waveXLength, int waveDy)
	{
		path.rLineTo(0, waveDy);
		path.rLineTo(-waveXLength, 0);
		path.close();
		paint.setColor(wave.waveColor);
		paint.setStrokeWidth(wave.waveStrokeWidth);
	}
}
