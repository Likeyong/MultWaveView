package com.codersun.multwaveviewlib;

import android.graphics.Bitmap;

import java.util.List;

/**
 * @author codersun
 * @time 2019/10/25 11:26
 */
public class WaveArg
{

	private ITransformCanvas mTransformCanvas;

	private List<Wave> mWaveList;

	private Bitmap mTransformBitmap;

	private int viewBgColor;

	//	private int mBgId;

	private boolean mAutoRise;

	private boolean mIsStroke;

	private int mXDuration;

	private int mYDuration;

	private WaveArg()
	{
	}

	public static WaveArg build()
	{
		return new WaveArg();
	}

	public WaveArg setTransform(ITransformCanvas transform)
	{
		mTransformCanvas = transform;
		return this;
	}

	public WaveArg setWaveList(List<Wave> waveList)
	{
		mWaveList = waveList;
		return this;
	}

	public List<Wave> getWaveList()
	{
		return mWaveList;
	}

	public ITransformCanvas getTransformCanvas()
	{
		return mTransformCanvas;
	}

	public Bitmap getTransformBitmap()
	{
		return mTransformBitmap;
	}

	public WaveArg setTransformBitmap(Bitmap mTransformBitmap)
	{
		this.mTransformBitmap = mTransformBitmap;
		return this;
	}

	public int getViewBgColor()
	{
		return viewBgColor;
	}

	public WaveArg setViewBgColor(int viewBgColor)
	{
		this.viewBgColor = viewBgColor;
		return this;
	}

	public boolean getAutoRise()
	{
		return mAutoRise;
	}

	public WaveArg setAutoRise(boolean mAutoRise)
	{
		this.mAutoRise = mAutoRise;
		return this;
	}

	public boolean getIsStroke()
	{
		return mIsStroke;
	}

	public WaveArg setIsStroke(boolean mIsStroke)
	{
		this.mIsStroke = mIsStroke;
		return this;
	}

	public int getxDuration()
	{
		return mXDuration;
	}

	public WaveArg setxDuration(int xDuration)
	{
		mXDuration = xDuration;
		return this;
	}

	public int getyDuration()
	{
		return mYDuration;
	}

	public WaveArg setyDuration(int yDuration)
	{
		mYDuration = yDuration;
		return this;
	}
}
