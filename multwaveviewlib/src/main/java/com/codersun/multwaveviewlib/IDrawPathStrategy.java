package com.codersun.multwaveviewlib;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * @author codersun
 */
public interface IDrawPathStrategy
{

	/**
	 * 开始绘制path之前,你想对path进行的操作
	 *
	 * @author codersun
	 * @time 2019/10/28 15:21
	 */
	int onPreDrawPath(Canvas canvas, Bitmap bgBitmap, Paint paint, int viewWidth, int viewHeight);

	/**
	 * 计算并设置path路径
	 *
	 * @author codersun
	 * @time 2019/10/28 15:21
	 */
	void calculatePath(Path path, Wave wave, float viewWidth, float heightMultiple);

	/**
	 * 如果你的path需要闭合,就实现此方法
	 *
	 * @author codersun
	 * @time 2019/10/28 15:22
	 */
	void closePath(Paint paint, Path path, Wave wave, int waveXLength, int waveDy);
}
