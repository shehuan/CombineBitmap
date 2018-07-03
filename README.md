# CombineBitmap

### 效果预览

|![](images/d1.PNG)|![](images/d2.PNG)|![](images/d3.PNG)||
|---|---|---|---|
|![](images/w1.PNG)|![](images/w2.PNG)|![](images/w3.PNG)|![](images/w4.PNG)|
|![](images/w5.PNG)|![](images/w6.PNG)|![](images/w7.PNG)|![](images/w8.PNG)|

### 功能
* 生成类似钉钉、微信 群聊组合头像Bitmap
* 可使用图片资源id、bitmap或者使用url从网络加载，传入对应数组即可
* 网络加载时支持线程池
* 支持磁盘缓存、内存缓存。（**记得申请磁盘缓存需要的文件存储权限**）
* 对图片资源进行采样率压缩
* 支持子图像的点击事件
* ......
### 基本用法
**Step 1. 添加JitPack仓库**
在项目根目录下的 `build.gradle` 中添加仓库:
``` gradle
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```
**Step 2. 添加项目依赖**
``` gradle
dependencies {
    implementation 'com.github.Othershe:CombineBitmap:1.0.5'
}
```
**Step 3. 配置**
``` java
CombineBitmap.init(context)
    .setLayoutManager() // 必选， 设置图片的组合形式，支持WechatLayoutManager、DingLayoutManager
    .setSize() // 必选，组合后Bitmap的尺寸，单位dp
    .setGap() // 单个图片之间的距离，单位dp，默认0dp
    .setGapColor() // 单个图片间距的颜色，默认白色
    .setPlaceholder() // 单个图片加载失败的默认显示图片
    .setUrls() // 要加载的图片url数组
    .setBitmaps() // 要加载的图片bitmap数组
    .setResourceIds() // 要加载的图片资源id数组
    .setImageView() // 直接设置要显示图片的ImageView
    // 设置“子图片”的点击事件，需使用setImageView()，index和图片资源数组的索引对应
    .setOnSubItemClickListener(new OnSubItemClickListener() {
        @Override
        public void onSubItemClick(int index) {

        }
    })
    // 加载进度的回调函数，如果不使用setImageView()方法，可在onComplete()完成最终图片的显示
    .setProgressListener(new ProgressListener() {
        @Override
        public void onStart() {

        }

        @Override
        public void onComplete(Bitmap bitmap) {

        }
    })
    .build();
```
由于生成的组合Bitmap是矩形的，要实现钉钉的圆形显示效果，这里用到了一个可圆形显示的ImageView控件：[NiceImageView](https://github.com/Othershe/NiceImageView)
