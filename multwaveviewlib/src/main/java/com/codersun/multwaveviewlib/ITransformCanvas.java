package com.codersun.multwaveviewlib;

import android.graphics.Canvas;
import android.graphics.Path;

/**
 * @author 李可勇
 * @time 2019/10/24 17:15
 */
public interface ITransformCanvas
{

	/**
	 * @param canvas view的canvas
	 * @param clipPath 你想将canvas切割成的路径,切割成圆形/方形/任性path形状
	 * @param width View的宽度
	 * @param height view高度
	 *
	 * @return 返回一个颜色,将会设置给转变后的视图作为背景色
	 * @author 李可勇
	 * @time 2019/10/25 11:30
	 */
	int transform(Canvas canvas, Path clipPath, int width, int height);

}
