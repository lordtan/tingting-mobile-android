package com.ccrt.app.util;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

/**
 * 公共参数
 * 
 */
public class StaticFeild {	
	
	/**** 日志TAG ****/
	public final static String TAG = "WhoopApp";
	
	public static String packageName = "com.ccrt.app.UI";
	
	/**** 设备id ****/
	public static String deviceId = "";
	
	/**** 屏幕宽 ****/
	public static int width = 0;
	
	/**** 屏幕高 ****/
	public static int height = 0;
	
	/**** 屏幕密度 ****/
	public static float density = 160;
	
	/**** 屏幕密度DIP ****/
	public static int densityDip;

	/**** 屏幕密度DIP ****/
	public static boolean isTestUI = false;
	
	/**** 是否执行home键 ****/
	public static boolean HomeKey = true;
	
	public static String appName = "iSEAL5200";
	
	/*****POST方式请求*****/
	public static String POST = "POST";
	
	public static boolean DEBUG = true; 
	
	public static String CHARSET = "UTF-8";  
	
	public final static String WAP = "wap";
	
	public final static String WAP_PROXY = "10.0.0.172";
	
	public final static String CTWAP_PROXY = "10.0.0.200";
	
	public final static int PROXY_PORT = 80;
	
	public final static String CTWAP = "ctwap";
	
	/**
	 * 初始化一些数据，在程序启动时调用
	 */
	public static void initData(Activity activity) {
		
		try {
			if(StaticFeild.height !=0 && StaticFeild.width !=0){
				return;
			}
			StaticFeild.packageName = activity.getPackageName();
			DisplayMetrics dm = new DisplayMetrics();
			activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
			StaticFeild.width = dm.widthPixels;
			StaticFeild.height = dm.heightPixels;
			StaticFeild.density = dm.density;
			StaticFeild.densityDip = dm.densityDpi;
			TelephonyManager tm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
			StaticFeild.deviceId = tm.getDeviceId();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
