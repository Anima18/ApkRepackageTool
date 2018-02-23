/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ut.apkrepackage;

/**
 *
 * @author Administrator
 */
public class APKInfo {
    
    private String apkPath;
    private String appName;
    private String appPackage;
    private String versionCode;
    private String versionName;
    private String resPath;
    private String mIcon;
    private String hIcon;
    private String xhIcon;
    private String xxhIcon;
    private String xxxhIcon;

    public APKInfo(String apkPath, String appName, String appPackage, String versionCode, String versionName, String resPath, String mIcon, String hIcon, String xhIcon, String xxhIcon, String xxxhIcon) {
        this.apkPath = apkPath;
        this.appName = appName;
        this.appPackage = appPackage;
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.resPath = resPath;
        this.mIcon = mIcon;
        this.hIcon = hIcon;
        this.xhIcon = xhIcon;
        this.xxhIcon = xxhIcon;
        this.xxxhIcon = xxxhIcon;
    }
    
    public String getApkPath() {
        return apkPath;
    }

    public void setApkPath(String apkPath) {
        this.apkPath = apkPath;
    }

    public String getResPath() {
        return resPath;
    }

    public void setResPath(String resPath) {
        this.resPath = resPath;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getmIcon() {
        return mIcon;
    }

    public void setmIcon(String mIcon) {
        this.mIcon = mIcon;
    }

    public String gethIcon() {
        return hIcon;
    }

    public void sethIcon(String hIcon) {
        this.hIcon = hIcon;
    }

    public String getXhIcon() {
        return xhIcon;
    }

    public void setXhIcon(String xhIcon) {
        this.xhIcon = xhIcon;
    }

    public String getXxhIcon() {
        return xxhIcon;
    }

    public void setXxhIcon(String xxhIcon) {
        this.xxhIcon = xxhIcon;
    }

    public String getXxxhIcon() {
        return xxxhIcon;
    }

    public void setXxxhIcon(String xxxhIcon) {
        this.xxxhIcon = xxxhIcon;
    }
    
}
