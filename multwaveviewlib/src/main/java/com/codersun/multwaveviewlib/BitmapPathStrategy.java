package com.codersun.multwaveviewlib;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

/**
 * @author 李可勇
 * @time $date$ $time$
 */
public class BitmapPathStrategy implements IDrawPathStrategy
{

	private final Paint mPaint;

	public BitmapPathStrategy()
	{
		mPaint = new Paint();
		mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		mPaint.setAntiAlias(true);
	}

	@Override
	public int onPreDrawPath(Canvas canvas,  Bitmap bitmap, Paint paint, int viewWidth, int viewHeight)
	{
		if (bitmap != null)
		{
			int bitmapHeight = bitmap.getHeight();
			int bitmapWidth = bitmap.getWidth();
			int saveLayer = canvas.saveLayer(0, 0, bitmapWidth, bitmapHeight, null, Canvas.ALL_SAVE_FLAG);
			canvas.drawBitmap(bitmap, 0, 0, mPaint);
			paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
			return saveLayer;
		}
		return 0;
	}

	@Override
	public 	void calculatePath(Path path, Wave wave,float viewWidth, float heightMultiple)
	{
		path.rQuadTo(viewWidth/ 4, -wave.waveHeight * heightMultiple, viewWidth / 2, 0);
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
