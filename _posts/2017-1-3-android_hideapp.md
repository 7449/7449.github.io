---
layout:     post
title:      "Android_通过root权限彻底隐藏系统垃圾应用"
subtitle:   "可隐藏一些Rom自带垃圾应用"
date:       2017-1-3
tags:
---


因为不喜欢国内一些rom自带的应用，所以root后此app可帮助冻结某些不想要的应用；

此root指令冻结的app无法自启，无法唤醒， 无法在应用列表找到，等同于彻底隐藏，<br>
但是MIUI貌似会在系统启动的时候遍历隐藏掉的系统app显示，这个问题不做考虑，显然万物基于MIUI<br>


root设备本篇文章不做讨论，各凭本事！<br>

而且这个只是简单的冻结一下，并没有多么好的提示以及UI展示，代码也比较混乱，毕竟开始只是为了能够隐藏掉一些我不需要的app<br>



隐藏以及显示的指令如下：<br>

`adb shell pm hide packages`<br>
`adb shell pm unhide packages`<br>

hide之后的软件我这里使用循环遍历`PackageManager`然后返回false的就是隐藏掉的，这个方法不建议使用，检测起来很慢，这里就随便用下；

<br>

项目地址：[FuckApp](https://github.com/7449/AndroidDevelop/blob/develop/FuckApp)<br>

虚拟机并没有root，所以看个运行效果即可<br>

完整root执行代码如下：


	public class RootUtils {
	    public static final String ADB_COMMAND_HIDE = "pm hide ";
	    public static final String ADB_COMMAND_UN_HIDE = "pm unhide ";
	    private static boolean b = true;
	
	    public static void execShell(List<AppModel> appModels, RootUtilsInterface rootUtilsInterface) {
	        for (AppModel appModel : appModels) {
	            if (!b) {
	                rootUtilsInterface.execShellError();
	                return;
	            }
	            b = execShell(ADB_COMMAND_HIDE + appModel.getPkgName());
	        }
	        if (b) {
	            rootUtilsInterface.execShellSuccess();
	        } else {
	            rootUtilsInterface.execShellError();
	        }
	    }
	
	    public interface RootUtilsInterface {
	        void execShellSuccess();
	
	        void execShellError();
	    }
	
	    public static boolean execShell(String adbCommand) {
	        Process process = null;
	        DataOutputStream os = null;
	        try {
	            process = Runtime.getRuntime().exec("su");
	            os = new DataOutputStream(process.getOutputStream());
	            os.writeBytes(adbCommand + "\n");
	            os.writeBytes("exit\n");
	            os.flush();
	            return process.waitFor() == 0;
	        } catch (Exception e) {
	            return false;
	        } finally {
	            try {
	                if (os != null) {
	                    os.close();
	                }
	                if (process != null) {
	                    process.destroy();
	                }
	            } catch (Exception ignored) {
	            }
	        }
	    }
	
	    /**
	     * hide之后的app是false
	     */
	    static boolean isApk(String packageName) {
	        final PackageManager packageManager = App.getApp().getPackageManager();
	        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
	        if (pinfo != null) {
	            for (PackageInfo packageInfo : pinfo) {
	                if (packageInfo.packageName.equals(packageName)) {
	                    return true;
	                }
	            }
	        }
	        return false;
	    }
	}

![_config.yml]({{ site.baseurl }}/assets/screenshot/17/fuckapp.gif)
