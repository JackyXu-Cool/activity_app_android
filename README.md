# activity_app_android

### 测试页面效果 （java resource file的逻辑）

因为我们现在创建的都不是一进去的那个主页面，所以尽量不在MainActivity来写。页面切换啥的我们过阵子再来解决。
我创建了的叫ApplyGroupActivity，主要handle我那个页面，你也可以按照你的页面的功能，创建xxxxxActivity

<br />

需要注意的就是你需要在AndroidManifest.xml里的activity tag把name从我现在的".ApplyGroupActivity"改成你对应的activity java file
这样模拟器才present你的页面

### Layout 文件夹
我在layout里面创建了不少可以重复利用的layout，比如tool_bar等等，要是符合你页面需求的话可以直接用<include layout="..."/> 拿去用，

### Progress
[10/20号进度](https://youtu.be/N_9_RSDaFBs)
