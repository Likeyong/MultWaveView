package com.codersun.multwaveviewlib;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;

/**
 * @author codersun
 */
public class CommonPathStrategy implements IDrawPathStrategy {


    @Override
    public int onPreDrawPath(Canvas canvas, Bitmap bgBitmap, Paint paint, int viewWidth, int viewHeight) {
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
