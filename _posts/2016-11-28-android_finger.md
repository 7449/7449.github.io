---
layout:     post
title:      "Android_指纹识别测试"
subtitle:   "通过一个简单的Demo来测试下Android6.0以上的指纹识别"
date:       2016-11-28
tags:
    - android
    - finger
---

## Demo地址

[finger](https://github.com/7449/AndroidDevelop/blob/develop/UtilsSample/Finger)

## 注意

必须要了解的是指纹识别是从6.0开始添加的 这样的话，想要使用Api就要把App改到23以上

指纹识别的库都在android.hardware.fingerprint这个包下面

## 申请权限

    <uses-permission android:name="android.permission.USE_FINGERPRINT" />

## 获取FingerprintManager对象
	
	//google推荐使用V4包下的兼容对象
	fingerprintManager = FingerprintManagerCompat.from(this);
	//API 23
	fingerprintManager = (FingerprintManager)getSystemService(Context.FINGERPRINT_SERVICE);

## 判断权限

 判断是否有无权限 有无指纹模块 有无锁屏密码 以及有没有录入指纹

    public boolean isFinger() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            Toast("没有指纹识别权限");
            return false;
        }
        Log("有指纹权限");
        if (!fingerprintManager.isHardwareDetected()) {
            Toast("没有指纹识别模块");
            return false;
        }
        Log("有指纹模块");
        if (!keyguardManager.isKeyguardSecure()) {
            Toast("没有开启锁屏密码");
            return false;
        }
        Log("已开启锁屏密码");
        if (!fingerprintManager.hasEnrolledFingerprints()) {
            Toast("没有录入指纹");
            return false;
        }
        Log("已录入指纹");
        return true;
    }

## 调用指纹识别

	识别指纹很简单 直接调用 authenticate 方法即可。取消调用 CancellationSignal.cancel()即可

	authenticate方法需要5个参数，分别是：

	crypto :一个加密类对象，扫描器通过这个判断认证结果的合法性，这里简单测试 直接传null

	cancel :这个是CancellationSignal类对象，作用是取消扫描操作，如果不取消，扫描器会扫描到超时，一般是30S左右

	flags : 看文档给个0就行了。

	callback: 这个很重要，扫描之后不管结果都会调用这个对象的接口。在这个里面判断认证结果

	handler ： 直接传null,FingerprintManager默认使用app的Main Looper处理。

	//调用代码

    FingerprintManagerCompat.AuthenticationCallback mSelfCancelled = new FingerprintManagerCompat.AuthenticationCallback() {

        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            //指纹多次验证错误进入这个方法。并且暂时不能调用指纹验证
            Toast(errString + "  onAuthenticationError");
            showAuthenticationScreen();//这里如果认证错误，调用系统的锁屏密码
        }

        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
            Toast(helpString + "   onAuthenticationHelp");
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            Toast("指纹识别成功");
        }


        @Override
        public void onAuthenticationFailed() {
            Toast("指纹识别失败");
        }
    };

	 /**
     * 锁屏密码
     */
    private void showAuthenticationScreen() {
        Intent intent = keyguardManager.createConfirmDeviceCredentialIntent("finger", "测试指纹识别");
        if (intent != null) {
            startActivityForResult(intent, REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS);
        }
    }
