package com.ccrt.app.util;

import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import android.content.Context;

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
}
