package com.codersun.multwaveviewlib;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;

/**
 * @author codersun
 */
public class BitmapPathStrategy implements IDrawPathStrategy {

    private final Paint mPaint;

    public BitmapPathStrategy() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);
    }

    @Override
    public int onPreDrawPath(Canvas canvas, Bitmap bitmap, Paint paint, int viewWidth, int viewHeight) {
        if (bitmap != null) {
            int bitmapHeight = bitmap.getHeight();
            int bitmapWidth = bitmap.getWidth();
            int saveLayer = canvas.saveLayer(0, 0, viewHeight, viewHeight, null, Canvas.ALL_SAVE_FLAG);

            Matrix matrix = new Matrix();
            matrix.postScale(viewWidth * 1f / bitmapWidth, viewHeight * 1f / bitmapHeight);
            Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, true);
            canvas.drawBitmap(bitmap1, 0, 0, mPaint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            return saveLayer;
        }
        return 0;
    }

    @Override
    public void calculatePath(Path path, Wave wave, float viewWidth, float heightMultiple) {
        path.rQuadTo(viewWidth / 4, -wave.waveHeight * heightMultiple, viewWidth / 2, 0);
        path.rQuadTo(viewWidth / 4, wave.waveHeight * heightMultiple, viewWidth / 2, 0);
    }

    @Override
    public void closePath(Paint paint, Path path, Wave wave, int waveXLength, int waveDy, int viewWidth, int viewHeight) {
        path.rLineTo(0, waveDy);
        path.rLineTo(-waveXLength, 0);
        path.close();
        if (wave.waveColor.size() == 1) {

            paint.setColor(wave.waveColor.get(0));
        } else {

            paint.setShader(new LinearGradient(viewWidth / 2, viewHeight / 4, viewWidth / 2, viewHeight, wave.waveColor.get(0), wave.waveColor.get(1), Shader.TileMode.CLAMP));
        }
        paint.setStrokeWidth(wave.waveStrokeWidth);
    }
}
