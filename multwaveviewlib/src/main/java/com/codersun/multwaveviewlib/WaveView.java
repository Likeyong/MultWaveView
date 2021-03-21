package com.codersun.multwaveviewlib;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author codersun
 * @time 2019/10/23 11:15
 */
public class WaveView extends View
{

	private Paint mPaint;

	private ValueAnimator mDxAnimator;

	private int mDx;

	//用来存储Wave 和 Path
	private HashMap<Wave, Path> pathHashMap = new HashMap<>();

	//波高 波动因子
	private float mHeightMultiple = 1;

	//所有波纹数据
	private List<Wave> mWaves;

	//对画布进行转换的路径
	private Path mTransformPath;

	//对画布进行转换的回调
	private ITransformCanvas mTransformCanvas;

	// 波浪上涨的属性动画
	private ValueAnimator mDyAnimator;

	//波浪上涨的值
	private int mDy;

	//path 绘制策略
	private IDrawPathStrategy mDrawPathStrategy;

	//是否是线条模式
	private boolean mStroke;

	//波浪是否自动上涨
	private boolean mAutoRise;

	//波浪在这个bitmap内部绘制
	private Bitmap mBgBitmap;

	//背景颜色
	private int mViewBgColor;

	private int mWidth;

	private int mHeight;

	//水波纹横向滚动周期
	private int mXDuration = 1000;

	//水波纹纵向增长的周期
	private int mYDuration = 1000 * 2;

	public WaveView(Context context)
	{
		this(context, null);
	}

	public WaveView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public WaveView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaveView);
		init(typedArray);
		typedArray.recycle();
	}

	private void init(TypedArray typedArray)
	{
		//初始化画笔
		initPaint();
		//初始化属性
		initAttrs(typedArray);
		//初始化动画
		initAnimator();
		mTransformPath = new Path();

	}

	private void initAttrs(TypedArray typedArray)
	{
		mStroke = typedArray.getBoolean(R.styleable.WaveView_is_stroke, true);
		mAutoRise = typedArray.getBoolean(R.styleable.WaveView_auto_rise, false);
		int mBgSrc = typedArray.getResourceId(R.styleable.WaveView_transform_src, 0);
		mBgBitmap = BitmapFactory.decodeResource(getResources(), mBgSrc);
	}

	private void initAnimator()
	{
		//初始化波纹波动的动画
		mDxAnimator = new ValueAnimator();
		mDxAnimator.setDuration(mXDuration);
		mDxAnimator.setInterpolator(new LinearInterpolator());
		mDxAnimator.setRepeatCount(ValueAnimator.INFINITE);
		mDxAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
		{

			@Override
			public void onAnimationUpdate(ValueAnimator animation)
			{
				mDx = (int) animation.getAnimatedValue();
				postInvalidate();
			}
		});

		mDyAnimator = new ValueAnimator();
		mDyAnimator.setDuration(mYDuration);
		mDyAnimator.setRepeatCount(ValueAnimator.INFINITE);
		mDyAnimator.setInterpolator(new LinearInterpolator());
		mDyAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
		{

			@Override
			public void onAnimationUpdate(ValueAnimator animation)
			{
				mDy = (int) animation.getAnimatedValue();
			}
		});
	}

	private void initPaint()
	{
		mPaint = new Paint();
		mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		mPaint.setColor(Color.BLUE);
		mPaint.setAntiAlias(true);
	}

	private Runnable animatorRunnable = new Runnable()
	{

		@Override
		public void run()
		{
			mDxAnimator.cancel();
			mDxAnimator.setIntValues(0, getWidth());
			mDxAnimator.start();
		}
	};

	public void start(WaveArg waveArg)
	{
		//解析参数
		parseArg(waveArg);
		//解析线条数据
		parseWaveData();
		//根据是否传入了bitmap来创建path绘制策略
		if (mBgBitmap == null)
		{
			mDrawPathStrategy = new CommonPathStrategy();
		}
		else
		{
			mDrawPathStrategy = new BitmapPathStrategy();
		}
		//重新测量 布局 绘制
		requestLayout();
	}

	private void parseArg(WaveArg waveArg)
	{
		if (waveArg.getxDuration() != 0)
		{

			mXDuration = waveArg.getxDuration();
		}
		if (waveArg.getyDuration() != 0)
		{

			mYDuration = waveArg.getyDuration();
		}

		mViewBgColor = waveArg.getViewBgColor();
		mStroke = waveArg.getIsStroke();
		mAutoRise = waveArg.getAutoRise();
		mTransformCanvas = waveArg.getTransformCanvas();
		mBgBitmap = waveArg.getTransformBitmap();
		mWaves = waveArg.getWaveList();

	}

	/**
	 * 设置波纹数据,并开启绘制
	 *
	 * @author codersun
	 * @time 2019/10/24 15:49
	 */
	private void parseWaveData()
	{
		pathHashMap.clear();
		//给每一个波纹设置一个对应的Path对象
		for (Wave wave : mWaves)
		{
			pathHashMap.put(wave, new Path());
		}
		//将波纹进行排序
		Collections.sort(mWaves);
		postInvalidate();
		removeCallbacks(null);
		post(animatorRunnable);

	}

	/**
	 * 设置波峰高度为当前多少倍
	 *
	 * @author codersun
	 * @time 2020/1/10 10:50
	 */
	public void setWaveHeightMultiple(float multiple)
	{
		mHeightMultiple = multiple;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int viewHeight = MeasureSpec.getSize(heightMeasureSpec);
		int viewWidth = MeasureSpec.getSize(widthMeasureSpec);

		if (widthMode == MeasureSpec.AT_MOST)
		{
			viewWidth = mBgBitmap == null ? 300 : mBgBitmap.getWidth();
		}

		if (widthMode == MeasureSpec.EXACTLY && mBgBitmap != null)
		{
			viewHeight = mBgBitmap.getHeight();
		}

		if (heightMode == MeasureSpec.AT_MOST)
		{

			viewHeight = mBgBitmap == null ? 300 : mBgBitmap.getHeight();
		}

		if (heightMode == MeasureSpec.EXACTLY && mBgBitmap != null)
		{
			viewWidth = mBgBitmap.getWidth();
		}

		setMeasuredDimension(viewWidth, viewHeight);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);
		mWidth = w;
		mHeight = h;
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom)
	{
		super.onLayout(changed, left, top, right, bottom);
		mPaint.setStyle(mStroke ? Paint.Style.STROKE : Paint.Style.FILL_AND_STROKE);
		mWidth = getWidth();
		mHeight = getHeight();
		mDyAnimator.setIntValues(mHeight, 0);
		mDxAnimator.setIntValues(0, mWidth);
		if (mAutoRise)
		{
			mDyAnimator.start();
		}
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		//绘制view设置的背景色
		if (mViewBgColor != 0)
		{
			canvas.drawColor(mViewBgColor);
		}

		canvas.save();
		//如果给view设置了画布转换,就执行转换逻辑
		if (mTransformCanvas != null)
		{
			mTransformPath.reset();
			int color = mTransformCanvas.transform(canvas, mTransformPath, mWidth, mHeight);
			canvas.clipPath(mTransformPath);
			if (color != 0)
			{
				canvas.drawColor(color);
			}
		}

		//这里进行分层绘制
		int saveLayer = 0;
		if (mDrawPathStrategy != null)
		{
			saveLayer = mDrawPathStrategy.onPreDrawPath(canvas, mBgBitmap, mPaint, mWidth, mHeight);
		}

		//重置每一条path, 并设置path起点
		//控制波纹速度: 波纹的移动是通过mDx 设置mDx数值进行移动, 同一时间点,mDx越大移动的越快
		//所以这里 使用(mDx * wave.waveSpeed) 控制速度,最后还要 % wave.offsetX 进行取余.因为
		//每一个波纹的移动距离不能大于 波长
		//所以每条 path的起点是 -mWidth(从屏幕左侧最大波长开始) + (mDx * wave.waveSpeed) % wave.offsetX (当前path需要移动的距离)
		for (Wave wave : mWaves)
		{
			Path path = pathHashMap.get(wave);
			path.reset();
			path.moveTo(-mWidth + (mDx * wave.waveSpeed) % mWidth, mAutoRise ? mDy : mHeight >> 1);
		}

		//这个for循环是为了绘制三遍,绘制三个屏幕的水波纹,通常是绘制两个水波纹,从而进行水波纹移动,这里绘制三个水波纹是为了
		//支持设置每条水波纹的偏移量.因为通常情况下每条水波纹的宽度为两个屏幕宽度,如果设置了水波纹的偏移量,水波纹会向左平移,
		// 就样用户能会看到水波纹的尾部

		for (int i = -mWidth; i <= mWidth; i = i + mWidth)
		{

			//一共三个屏幕,所以要在每一个屏幕内把每条水波纹都绘制出来
			//所以这里要把mWaves 都绘制出来
			for (Wave wave : mWaves)
			{
				//一个完成的波浪 是两个二阶贝塞尔(三阶贝塞尔不够规则) 一个向上一个向下
				Path path = pathHashMap.get(wave);
				if (mDrawPathStrategy != null)
				{
					mDrawPathStrategy.calculatePath(path, wave, mWidth, mHeightMultiple);
				}

			}

		}

		//设置每一个path的颜色以及宽度进行绘制
		for (Wave wave : mWaves)
		{
			canvas.save();

			Path path = pathHashMap.get(wave);
			//对path进行close
			if (mDrawPathStrategy != null)
			{
				mDrawPathStrategy.closePath(mPaint, path, wave, mWidth * 3,  mHeight );
			}

			//设置每条水波纹设置的偏移量
			canvas.translate(-mWidth * wave.offsetX, 0);
			canvas.drawPath(pathHashMap.get(wave), mPaint);
			canvas.restore();
		}

		if (mBgBitmap != null)
		{
			mPaint.setXfermode(null);
			if (saveLayer != 0)
				canvas.restoreToCount(saveLayer);
		}

		canvas.restore();

	}

	@Override
	protected void onDetachedFromWindow()
	{
		super.onDetachedFromWindow();
		if (mDxAnimator != null)
		{
			mDxAnimator.cancel();
		}
	}

	@Override
	protected void onAttachedToWindow()
	{
		super.onAttachedToWindow();
		if (mDxAnimator != null)
		{
			PropertyValuesHolder[] values = mDxAnimator.getValues();
			if (values != null && values.length != 0)
			{
				mDxAnimator.start();
			}
		}
	}

}