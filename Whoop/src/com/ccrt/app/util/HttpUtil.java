package com.ccrt.app.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import android.content.Context;

import com.ccrt.app.bean.Constant;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

/**
 * 异步请求
 * @author 谭罗乐
 * @date 2015年6月4日
 * 版权：绿豆科技有限公司
 */
public class HttpUtil {

	private static AsyncHttpClient asyncHttpClient = null;
	
	public static AsyncHttpClient getClient(){
		if(null==asyncHttpClient){
			asyncHttpClient = new AsyncHttpClient();
		}
		return asyncHttpClient;
	}
	
	/**
	 * 发送异步post请求
	 * @param context
	 * @param url 请求地址
	 * @param data 数据对象，可以是javabean或者 Map
	 * @param handler 请求结果处理
	 */
	public static void sendPost(Context context,String url,Object data, ResponseHandlerInterface handler){
		try {
			String json = JsonParser.toJson(data);
			HttpEntity entity = new StringEntity(json, "UTF-8");
			getClient().post(context, url, entity, RequestParams.APPLICATION_JSON, handler);
		    AppLogger.d("请求地址：" + url);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 发布声音，上传声音文件
	 * @param context
	 * @param sound 声音信息
	 * @param file 声音文件
	 * @param handler 请求结果处理，重写onProgress可以监听上传进度
	 */
	public static void upload(Context context,String sound,File file, ResponseHandlerInterface handler){
		RequestParams params = new RequestParams();
		try {
			params.put("file", file);
			params.put("sound", sound);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		getClient().post(context, Constant.PUBLISH, params, handler);
	}
}
