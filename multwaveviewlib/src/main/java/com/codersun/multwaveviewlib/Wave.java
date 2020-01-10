package com.codersun.multwaveviewlib;

/**
 * 每条波浪的参数,并实现Comparable接口
 * 可以进行排序,之所以进行排序是因为Set集合的遍历
 * 是无序的,因为每条线的宽度和颜色不一致,无序会导致每次重回时
 * 线条覆盖情况不一致
 *
 * @author codersun
 * @time 2019/10/23 10:09
 */
public class Wave implements Comparable<Wave>
{

	//水波纹的偏移量   取值范围[0,1] 即view宽的 多少倍
	public float offsetX;

	//波高
	public int waveHeight;

	//波浪的速度
	public int waveSpeed;

	//波浪线条颜色
	public int waveColor;

	//波浪线条宽度
	public int waveStrokeWidth;

	public Wave(float offsetX, int waveHeight, int waveSpeed, int waveColor, int waveStrokeWidth)
	{

		//对边界值进行处理
		offsetX = Math.max(0, Math.min(1, offsetX));
		this.offsetX = offsetX;
		this.waveHeight = waveHeight;
		this.waveSpeed = waveSpeed;
		this.waveColor = waveColor;
		this.waveStrokeWidth = waveStrokeWidth;
	}

	/**
	 * 默认按照线条宽度进行排序,
	 * 因为当波长波高波速一致时
	 * 粗线条会把细线条完全覆盖
	 *
	 * @author codersun
	 * @time 2019/10/24 16:02
	 */
	@Override
	public int compareTo(Wave o)
	{
		return -this.waveStrokeWidth + o.waveStrokeWidth;
	}
}

