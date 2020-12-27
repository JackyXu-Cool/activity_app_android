# activity_app_android

**[Update on 12/22/2020]**

### 程序用途
为大学生，高中生提供一个可以管理社团活动的平台。主要用途： <br />
1. 有权限的成员（如社长，副社长）可以发起活动
2. 社员可以报名参加活动
3. 任何用户均可申请加入某个社团 （需要通过管理员同意，一般为社长）
4. 任何用户均可申请创建新的社团 （需要通过管理员的同意，一般为某某老师）
5. 任何用户均可以查看各个社团的基础信息，过往活动照片

### 测试页面效果 （java resource file的逻辑）

因为我们现在创建的都不是一进去的那个主页面，所以尽量不在MainActivity来写。页面切换啥的我们过阵子再来解决。
你需要新建activity java file和对应的xml layout file来写你的页面

<br />

需要注意的就是你需要在AndroidManifest.xml里的activity tag把name从我现在的".ApplyGroupActivity"改成你对应的activity java file
这样模拟器才present你的页面

### Layout 文件夹
我在layout里面创建了不少可以重复利用的layout，比如tool_bar等等，要是符合你页面需求的话可以直接用<include layout="..."/> 拿去用，

### Progress
[10/20号进度](https://youtu.be/N_9_RSDaFBs) <br />
[12/26号进度](https://www.youtube.com/watch?v=ZF-lh1_uuSY) [latest]
