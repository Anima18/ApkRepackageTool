# ApkRepackageTool
ApkRepackageTool是一个对APK进行二次打包的工具，可以修改应用包名、应用名称、图标和版本等信息。支持可运行程序和集成到WEB端。

## 可运行程序
运行ApkRepackageTool/tool/bin/run.bat  

![image](https://github.com/Anima18/ApkRepackageTool/blob/master/image/repackage.png?raw=true)

## Web项目集成
在webjar目录下有2个文件
- lib （所有的jar，需要集成到web项目）
- 二次打包空间 （需要放在服务器上）

#### 代码示例
```
//打包的apk路径
String apkPath = "xxx.apk";
//压入apk的资源路径
String resPath = "";
//新apk名称
String appName = "Hello";
//新apk的包名
String appPackage = "com.anima18.hello";
//新apk的版本号
String versionCode = "1";
//新apk的版本名称
String versionName = "1.0";
//新apk的应用图标
String mIcon = "xxx.png";
String hIcon = "xxx.png";
String xhIcon = "xxx.png";
String xxhIcon = "xxx.png";
String xxxhIcon = "xxx.png";

//初始化apk信息
APKInfo apkInfo = new APKInfo(apkPath, appName, appPackage, versionCode, versionName, null, null, mIcon, hIcon, xhIcon, xxhIcon, xxxhIcon);
APKSignatureService service = new APKSignatureService();
//apkSigned的路径
String signaturePath = xxxx";
//apk打包，newApkPath是新apk的路径
String newApkPath = service.signature(apkInfo, signaturePath);

```
