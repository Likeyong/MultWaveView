# MultWaveView

# 先上效果
![wave.gif](https://upload-images.jianshu.io/upload_images/5406710-0a4814bbe5a304fa.gif?imageMogr2/auto-orient/strip)


gradle 引用
```
implementation 'com.maxcion:multwaveviewlib:1.0.0'
```

* 第一种效果是多重水波纹的效果， 三层水波纹，每层水播放可以设置不同的颜色、波高与波宽
* 第二种效果是 单层水波纹，并且支持从底部慢慢上涨的效果。 这里也可以设置为多层水波纹上涨效果，颜色、波高、波宽以及背景图都是可以定制
* 第三种效果就是模仿京东语音评价动画，最底部的seekbar 是用来模拟语音输出的声音大小，声音越大波高越大，并且每条水波纹的粗细以及速度都不同

![batman_logo.png](https://upload-images.jianshu.io/upload_images/5406710-47740aa295fb0c8a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


第一种和第二种效果的背景图是这样的， 如果设置了背景图，那么整个控件的大小就是背景图大小与xml中设置的大小无关
```
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
```
### 第一种样式存在三层水波纹，所以要生成3个Wave对象，参数说明如下
![wave参数.png](https://upload-images.jianshu.io/upload_images/5406710-880aac125f97bbed.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

设置完水波纹数据后，就可以开始动画了
```
 waveView.start(WaveArg.build()
                .setWaveList(waves)//设置水波纹数据
                .setAutoRise(false)//设置水波纹是否自动上升
                .setIsStroke(false)//设置水波纹是实心的还是线条模式
                .setTransformBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.batman_logo))
                //设置背景图

        );
```
### 第二种样式：只有一层水波纹但是需要自动上涨，所以只用生成一条wave对象
```
 List<Wave> waves = new ArrayList<>();
        Wave wave1 = new Wave(0, 150, 1, Color.parseColor("#00FFFF"), 3);
        waves.add(wave1);

 waveView2.start(WaveArg.build()
                .setWaveList(waves)
                .setAutoRise(true)//设置自动上涨
                .setIsStroke(false)
                .setTransformBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.batman_logo))

        );
```
### 第三种样式，仅线条模式，并且三条水波纹粗细不一样，速度不一样，水波纹的粗细和速度都是通过Wave的构造函数设置的
```
List<Wave> waves = new ArrayList<>();
        Wave wave1 = new Wave(0, 150, 1, Color.parseColor("#000000"), 3);
        Wave wave2 = new Wave(0, 150, 3, Color.parseColor("#000000"), 3);
        Wave wave3 = new Wave(0, 150, 2, Color.parseColor("#000000"), 3);
        waves.add(wave1);
        waves.add(wave2);
        waves.add(wave3);

 waveView3.start(WaveArg.build()
                .setWaveList(waves)
                .setAutoRise(false)
                .setIsStroke(true)

        );
```

这样写我们的三条粗细、速度都不同的水波纹动画就出来了，接下来就是根据声音大小来调整波高，通过 waveView.setWaveHeightMultiple(mult)， 因为语音一直输入，声音大小也是一直变化的，科大讯飞的语音转写SDK中就有声音大小的回调，然后将回调出来的声音大小通过setWaveHeightMultiple，就可以实现波高动态改变了
