package com.ccrt.app.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.Toast;


public class CKTools {

	private static Toast mToast;
	
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
	
	/**
	 * 将输入流转换为字符串
	 * 
	 * @param is
	 *            要转换的输入流
	 * @return 转换之后的字符串
	 * @throws IOException
	 *             如果要转化的输入流为空将抛出异常
	 */
	public static String inputStreamToString(InputStream is) throws IOException {
		if (is == null) {
			throw new IOException("不能将空的输入流转换为字符串");
		}
		byte[] data = inputStreamTobyte(is);
		return data == null ? null : new String(data);
	}
	
	/**
	 * 将输入流转化成字节数组
	 * 
	 * @param is
	 *            要转化的输入流
	 * @return 转化之后的字节数组
	 */
	public static byte[] inputStreamTobyte(InputStream is){
		byte[] data = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			byte[] b = new byte[1024 * 10];
			if (is != null) {
				int i = 0;
				while ((i = is.read(b)) != -1) {
					bos.write(b, 0, i);
				}
			}
			data = bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				is.close();
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}

	
	
	/**
	 * 获得文件存储数据库
	 * 
	 * @return
	 */
	public static SharedPreferences getSP(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	/**
	 * 提示消息
	 * 
	 * @param context
	 * @param msg
	 * @return
	 */
	public static void toast(Context context, String msg) {
		if (mToast == null) {
			mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(msg);
		}
		mToast.show();
	}
	
	/**
	 * 提示消息
	 * 
	 * @param context
	 * @param msg
	 * @return
	 */
	public static void toastLong(Context context, String msg) {
		if (mToast == null) {
			mToast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
		} else {
			mToast.setText(msg);
		}
		mToast.show();
	}
	
	/**
	 * 低像素值转高像素值
	 * 
	 * @param pxValue
	 * @return
	 */
	public static int hpx2otherpx(float pxValue) {
		return (int) ((pxValue/1.5)*StaticFeild.density);

	}

	/**
	 * DIP 转 像素
	 * 
	 * @param pxValue
	 * @return
	 */
	public static int dip2px(float dipValue) {

		return (int) (dipValue * StaticFeild.density);
	}

	/**
	 * 像素  转 DIP
	 * 
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(float pxValue) {

		return (int) (pxValue/StaticFeild.density);
	}

	/**
	 * 将字符串的首字符转化成小写
	 * 
	 * @param str
	 *            要转化的字符串
	 * @return 返回首字符变小写之后的字符串
	 */
	public static String tofirstLowerCase(String str) {
		if (str != null && str.length() > 0) {
			return str.substring(0, 1).toLowerCase()
					+ str.substring(1, str.length());
		} else {
			return str;
		}
	}
	
	
	/**
	 * 根据给定的类型名和字段名，返回R文件中的字段的值
	 * @param typeName 属于哪个类别的属性 （id,layout,drawable,string,color,attr......）
	 * @param fieldName 字段名
	 * @return 字段的值
	 * @throws Exception 
	 */
	public static int getFieldValue(String typeName,String fieldName){
			int i = -1;
			try {
				Class<?> clazz = Class.forName(StaticFeild.packageName+".R$"+typeName);
				i = clazz.getField(fieldName).getInt(null);
			} catch (Exception e) {
				Log.e(StaticFeild.TAG,"没有找到"+StaticFeild.packageName+".R$"+typeName+"类型资源 "+fieldName);
				Log.e(StaticFeild.TAG, e.getMessage());
			}
			return i;
	}
	
	public static int getId(String fieldName){
		return getFieldValue("id", fieldName);
	}
	
	public static int getLayout(String fieldName){
		return getFieldValue("layout", fieldName);
	}
	
	public static int getDrawable(String fieldName){
		return getFieldValue("drawable", fieldName);
	}
	
	
	/**
	 * 跳转发送短信页面
	 * 
	 * @param context
	 * @param mobile
	 *            手机号
	 * @param text
	 *            短信内容
	 */
	public static void sendMessage(Context context, String mobile, String text) {

		Uri smsToUri = Uri.parse("smsto:" + mobile);
		Intent smsToIntent = new Intent(android.content.Intent.ACTION_SENDTO, smsToUri);
		smsToIntent.putExtra("sms_body", text);
		context.startActivity(smsToIntent);
	}
	
	/**
	 * 拨打电话弹出提示框，整个程序拨打电话点都会执行这个统一方法
	 * 
	 * @param context
	 *            当前的activity上下文
	 * @param number
	 *            要拨打的电话号码
	 * @return Dialog 返回一个拨打电话的提示框
	 */
	public static Dialog callPhone(final Context context, final String number) {

		if (TextUtils.isEmpty(number)) {
			Log.e(StaticFeild.TAG, "phone is empty...");
			return null;
		}

		return AlertTools.showTwoBtnAlert(context, "提示", "您确定拨打客服热线：" + number, "拨打", "取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Uri uri = Uri.parse("tel:" + number);
				Intent call = new Intent(Intent.ACTION_CALL, uri);
				context.startActivity(call);
			}
		}, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

	}
	
	/**
	 * 传递经纬度，打开网页版谷歌地图
	 * 
	 * @param context
	 * @param lat
	 * @param lon
	 * @param name
	 */
	public static void showWebMap(Context context, String lat, String lon, String name) {
		String uri = "http://ditu.google.cn/maps?ll=" + lat + "," + lon + "&q=" + name + "";

		if (TextUtils.isEmpty(lat) || TextUtils.isEmpty(lon)) {
			uri = "http://ditu.google.cn/maps";
		}
		Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(uri)); // 把地图的经度和纬度传进去
		context.startActivity(i);
	}
	
	
	/**
	 * 将给定的HashMap里的数据存入到Intent中
	 * 
	 * @param map
	 *            给定的map集合
	 * @param intent
	 *            要存入的intent
	 * @return 包含了给定hashmap的key，value的Intent。
	 */
	public static Intent mapToIntent(HashMap<String, Object> map, Intent intent) {
		for (Entry<String, Object> entry : map.entrySet()) {
			try {
				Object value = entry.getValue();
				if(value instanceof Integer){
					intent.putExtra(entry.getKey(), (Integer) value);
				}else{
					intent.putExtra(entry.getKey(), (String) value);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return intent;
	}
	
	/**
	 * 返回popupwindow
	 * 
	 * @param activity
	 * @param layout
	 * @param animid
	 * @return
	 */
	public static PopupWindow getPopupWindow(Activity activity, int layout, int animid) {
		PopupWindow mPop = new PopupWindow(activity.getLayoutInflater().inflate(layout, null), LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT,
				true);
		mPop.setAnimationStyle(animid);
		mPop.setBackgroundDrawable(new BitmapDrawable());
		return mPop;
	}

	/**
	 * 返回popupwindow
	 * 
	 * @param activity
	 * @param layout
	 * @param animid
	 * @param width
	 * @param height
	 * @return
	 */
	public static PopupWindow getPopupWindow(Activity activity, int layout, int animid, int width, int height) {
		PopupWindow mPop = new PopupWindow(activity.getLayoutInflater().inflate(layout, null), width, height, true);
		mPop.setAnimationStyle(animid);
		mPop.setBackgroundDrawable(new BitmapDrawable());
		return mPop;
	}
	
	/**
	 * 资源文件中的通配符替换
	 * 
	 * @param source 资源文件中语句
	 * @param param  需要替换的文字
	 * @return
	 */
	public static String formatString(String source, String param) {
		if (source != null && param != null) {
			return String.format(source, param);
		}
		return source;
	}
	
	
	/**
	 * 获取最大月份
	 * 
	 * @param maxMonth
	 * @return
	 */
	public static String[] getRechargeMonths(int maxMonth) {
		String[] arr = new String[maxMonth];
		for (int i = 0; i < arr.length; i++) {
			if(i<9){
				arr[i] = "0" + (i + 1);
			}else{
				arr[i] = "" + (i + 1);
			}
		}
		return arr;
	}
	
	/**
	 * 获取当前栈顶activity
	 * @param cmdName
	 * @param context
	 * @return
	 */
	public static String topActivityName(Context context){
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> runingTask = manager.getRunningTasks(Integer.MAX_VALUE);
		String cmpNameTemp = "";
		if(runingTask != null){
			cmpNameTemp = runingTask.get(0).topActivity.toString();
		}
		return cmpNameTemp;
	}
	
	/**
	 * 判断当前app是否在前台
	 * @param context
	 * @return
	 */
	public static boolean isAppOnForeground(Context context){
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		String packageName = context.getPackageName();
		List<RecentTaskInfo> appTask = manager.getRecentTasks(Integer.MAX_VALUE, 1);
		if(appTask!=null){
			return appTask.get(0).baseIntent.toString().contains(packageName);
		}
		return false;
	}
	
	/**
	 * 获取所有的安装程序
	 * @param context
	 * @return
	 */
	public static List<String> getAllTheLauncher(Context context){
		List<String> names = null;
		PackageManager pkgMgt = context.getPackageManager();
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		List<ResolveInfo> ra = pkgMgt.queryIntentActivities(intent, 0);
		if(ra!=null && ra.size()!=0){
			names = new ArrayList<String>();
			for(int i = 0; i<ra.size();i++){
				names.add(ra.get(i).activityInfo.packageName);
			}
		}
		return names;
	}
	
	public static boolean isLauncherRunning(Context context){
		List<String> names = getAllTheLauncher(context);
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appList = manager.getRunningAppProcesses();
		for(RunningAppProcessInfo running : appList){
			if(running.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND){
				for(int i = 0; i<names.size();i++){
					if(names.get(i).equals(running.processName)){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static byte[] InputStreamToByte(InputStream iStrm) {
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		int ch;
		try {
			while ((ch = iStrm.read()) != -1)
			{
			bytestream.write(ch);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte imgdata[]=bytestream.toByteArray();
		try {
			bytestream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imgdata;
		} 
	
	/**
	 * hashmap生成url参数地址
	 * 
	 * @param map
	 * @return
	 */
	public static String handlerURLParams(HashMap<String, String> map) {
		if (map == null || map.size() == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String> entry : map.entrySet()) {
			String value = entry.getValue();
			if (TextUtils.isEmpty(value)) {
				value = "";
			}
			sb.append(entry.getKey() + "=" + value + "&");
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * 拼接URL。返回主机地址及参数
	 * 
	 * @param map
	 * @return
	 */
	public static String getHost(String url, HashMap<String, String> map) {
		if(url.contains("?")){
			if(url.endsWith("?")){
				return url + handlerURLParams(map);
			}else{
				return url + "&" +handlerURLParams(map);
			}
		}else{
			return url + "?" +handlerURLParams(map);
		}
	}
	
	/**
	 * 判断传入的字符串是否为中文、数字、字母
	 * @param str
	 * @return
	 */
	public static boolean isChinessSimple(String str){
		if(str.matches("^[\u4e00-\u9fa5]+$") | str.matches("^[A-Za-z0-9]*$")){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断Mid是否为4位数字
	 * @param str
	 * @return
	 */
	public static boolean isMid(String str){
		if(str.matches("^[0-9]{4}$")){
			return true;
		}
		return false;
	}

	/**
	 * 判断SN是否为12位数字
	 * @param str
	 * @return
	 */
	public static boolean isSN(String str){
		if(str.matches("^[0-9]{12}$")){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断应用名和账户名是否为数字或者字母
	 * @param str
	 * @return
	 */
	public static boolean isAppNameAndAccountName(String str){
		if(str.matches("^[A-Za-z0-9]*$")){
			return true;
		}
		return false;
	}
	/**
	 * 判断令牌过期标志
	 * @param str
	 * @return
	 */
	public static boolean overTimeCode(String str){
		if(str.matches("^[0-1]{1}$")){
			return true;
		}
		return false;
		
	}
}
