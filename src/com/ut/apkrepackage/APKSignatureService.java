/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ut.apkrepackage;

import com.ut.apkrepackage.util.ApkSignedUtil;
import com.ut.apkrepackage.util.StringUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.JTextArea;
import org.apache.commons.io.FileUtils;
import org.dom4j.DocumentException;

/**
 *
 * @author Administrator
 */
public class APKSignatureService {
    
    private JTextArea logArea;
    
    public APKSignatureService() {}
    
    public APKSignatureService(JTextArea logArea) {
        this.logArea = logArea;
    }
    
    public String signature(APKInfo apkInfo) {
        //当前的工作目录
        String path = System.getProperty("user.dir").replace("\\", "/");
        //apk的签名目录
        String apktoolPath = path + "/apkSigned/apktool.jar";
        
        //校验apkInfo合法性
        verifyApkInfo(apkInfo);
        
        /**
        * 编译和签名的根目录
        */
        String toolDirPath = apktoolPath.substring(0, apktoolPath.lastIndexOf("/")+1);
        //签名工具的目录
        String signapkPath = toolDirPath + "signapk.jar";
        String pemPath = toolDirPath + "cert.x509.pem";
        String pk8Path = toolDirPath + "private.pk8";

        //apk包的名称
        String apkPath = apkInfo.getApkPath();
        String apkName = apkPath.substring(apkPath.lastIndexOf("/")+1, apkPath.lastIndexOf(".apk"));
        Runtime run = Runtime.getRuntime();
        try {
            printMessage("正在解包...");
            //解包命令
            String openJarCode = "java -jar " + apktoolPath + " d -f -s " +apkPath;
            Process p = run.exec(openJarCode, null, new File(toolDirPath));
            p.waitFor();
            
            //支持Android 7
            String forceCode = "java -jar " + apktoolPath + " empty-framework-dir --force";
            p = run.exec(forceCode, null, new File(toolDirPath));
            p.waitFor();
            printMessage("解包成功。");

            printMessage("正在修改AndroidManifest...");
            ApkSignedUtil.updateAndroidMainifest(toolDirPath + apkName + "/AndroidManifest.xml", apkInfo.getAppPackage(), apkInfo.getVersionCode(), apkInfo.getVersionName());
            printMessage("修改AndroidManifest成功。");

            printMessage("正在修改应用名称...");
            ApkSignedUtil.updateAppName(toolDirPath + apkName + "/res/values/strings.xml", apkInfo.getAppName());
            printMessage("修改应用名称成功");

            printMessage("正在修改应用图标...");
            String iconDirPath = toolDirPath + apkName + "/res/mipmap-hdpi/";
            File iconDir = new File(iconDirPath);
            if(iconDir.exists()) {
                Files.copy(Paths.get(apkInfo.gethIcon()), Paths.get(toolDirPath + apkName + "/res/mipmap-hdpi/ic_launcher.png"), StandardCopyOption.REPLACE_EXISTING);
                Files.copy(Paths.get(apkInfo.getmIcon()), Paths.get(toolDirPath + apkName + "/res/mipmap-mdpi/ic_launcher.png"), StandardCopyOption.REPLACE_EXISTING);
                Files.copy(Paths.get(apkInfo.getXhIcon()), Paths.get(toolDirPath + apkName + "/res/mipmap-xhdpi/ic_launcher.png"), StandardCopyOption.REPLACE_EXISTING);
                Files.copy(Paths.get(apkInfo.getXxhIcon()), Paths.get(toolDirPath + apkName + "/res/mipmap-xxhdpi/ic_launcher.png"), StandardCopyOption.REPLACE_EXISTING);
                Files.copy(Paths.get(apkInfo.getXxxhIcon()), Paths.get(toolDirPath + apkName + "/res/mipmap-xxxhdpi/ic_launcher.png"), StandardCopyOption.REPLACE_EXISTING);
            }else {
                Files.copy(Paths.get(apkInfo.gethIcon()), Paths.get(toolDirPath + apkName + "/res/mipmap-hdpi-v4/ic_launcher.png"), StandardCopyOption.REPLACE_EXISTING);
                Files.copy(Paths.get(apkInfo.getmIcon()), Paths.get(toolDirPath + apkName + "/res/mipmap-mdpi-v4/ic_launcher.png"), StandardCopyOption.REPLACE_EXISTING);
                Files.copy(Paths.get(apkInfo.getXhIcon()), Paths.get(toolDirPath + apkName + "/res/mipmap-xhdpi-v4/ic_launcher.png"), StandardCopyOption.REPLACE_EXISTING);
                Files.copy(Paths.get(apkInfo.getXxhIcon()), Paths.get(toolDirPath + apkName + "/res/mipmap-xxhdpi-v4/ic_launcher.png"), StandardCopyOption.REPLACE_EXISTING);
                Files.copy(Paths.get(apkInfo.getXxxhIcon()), Paths.get(toolDirPath + apkName + "/res/mipmap-xxxhdpi-v4/ic_launcher.png"), StandardCopyOption.REPLACE_EXISTING);  
            }
            printMessage("修改应用图标成功");

            if(StringUtil.isNotEmpty(apkInfo.getResPath())) {
                printMessage("正在设置应用配置...");
                File sourceFile = new File(apkInfo.getResPath());
                File targetFile = new File(toolDirPath + apkName + "/res/raw/res_decrypted.zip");
                if(!sourceFile.exists()) {
                    printMessage("应用配置不存在，请检查路径！");
                    throw new IllegalArgumentException("应用配置不存在，请检查路径！");
                }

                FileUtils.copyFile(sourceFile , targetFile);
                printMessage("设置应用配置成功");
            }

            printMessage("正在重新打包...");

            String packJarCode = "java -jar " + apktoolPath + " b " +apkName;
            p = run.exec(packJarCode, null, new File(toolDirPath));
            p.waitFor();
            
            printMessage("重新打包成功。");

            printMessage("正在签名...");

            String sinedApkName = toolDirPath + apkInfo.getAppName() + "-" + apkInfo.getVersionName() + ".apk";
            String signedApkCode = "java -jar "+ signapkPath +" "+ pemPath +" "+ pk8Path +" "+ toolDirPath+apkName+"/dist/"+apkName+".apk" +" " +sinedApkName;
            p = run.exec(signedApkCode, null, new File(toolDirPath));
            p.waitFor();
            printMessage("签名成功。");
            printMessage("生成apk："+sinedApkName);
            
            FileUtils.deleteDirectory(new File(toolDirPath + apkName));
            return sinedApkName;
        } catch (IOException | DocumentException | InterruptedException e) {
            printMessage(e.getMessage());
            return null;
        }
    }
    
    private void verifyApkInfo(APKInfo apkInfo) {
        if(StringUtil.isEmpty(apkInfo.getApkPath())) {
            printErrorMessage("APK Path不能为空。");
        }
        if(!new File(apkInfo.getApkPath()).exists()) {
            printErrorMessage("APK文件不存在。");
        }

        if(StringUtil.isEmpty(apkInfo.getVersionCode())) {
            printErrorMessage("版本号不能为空。");
        }

        if(StringUtil.isEmpty(apkInfo.getVersionName())) {
            printErrorMessage("版本名称不能为空。");
        }

        if(StringUtil.isEmpty(apkInfo.getAppPackage())) {
            printErrorMessage("应用包名不能为空。");
        }

        if(StringUtil.isEmpty(apkInfo.getAppName())) {
            printErrorMessage("应用名称不能为空。");
        }

        if(StringUtil.isEmpty(apkInfo.getmIcon())) {
            printErrorMessage("mIcon不能为空。");
        }
        if(!new File(apkInfo.getmIcon()).exists()) {
            printErrorMessage("mIcon不存在。");
        }

        if(StringUtil.isEmpty(apkInfo.gethIcon())) {
            printErrorMessage("hIcon不能为空。");
        }
        if(!new File(apkInfo.gethIcon()).exists()) {
            printErrorMessage("hIcon不存在。");
        }

        if(StringUtil.isEmpty(apkInfo.getXxhIcon())) {
            printErrorMessage("xxhIcon不能为空。");
        }
        if(!new File(apkInfo.getXxhIcon()).exists()) {
            printErrorMessage("xxhIcon不存在。");
        }

        if(StringUtil.isEmpty(apkInfo.getXxxhIcon())) {
            printErrorMessage("xxxhIcon不能为空。");
        }
        if(!new File(apkInfo.getXxxhIcon()).exists()) {
            printErrorMessage("xxxhIcon不存在。");
        }
    }
    
    public void printMessage(String message) {
        if(logArea != null) {
            logArea.append(message);
            logArea.append("\n");
        }else {
            System.out.println(message);
        }       
    }
    
    public void printErrorMessage(String message) {
        if(logArea != null) {
            logArea.append(message);
            logArea.append("\n");
        }else {
            throw new IllegalArgumentException(message);
        }       
    }
}
