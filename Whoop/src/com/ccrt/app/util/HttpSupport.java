package com.ccrt.app.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * http请求，同步
 * @author 谭罗乐
 * @date 2015年6月4日
 * 版权：绿豆科技有限公司
 */
public class HttpSupport {

	private static HttpClient client;
	
	/**
	 * 单例模式
	 * @return
	 */
	synchronized public static HttpClient getHttpClient(){
		if(null==client){
			client = new DefaultHttpClient();
		}
		
		return client;
	}
	
	/**
	 * 发送post请求
	 * @param url 请求地址
	 * @param params 参数，类型可以是Map或者javabean
	 * @return
	 */
	synchronized public static String sentPost(String url, Object params){
		//post请求
		HttpPost request = new HttpPost(url);
		// 绑定到请求 Entry
		StringEntity se;
		//返回结果
		String data = null;
		try {
			
			se = new StringEntity(JsonParser.toJson(params));
			request.setEntity(se);
			
			// 发送请求
			HttpResponse httpResponse = getHttpClient().execute(request);
			data = EntityUtils.toString(httpResponse.getEntity());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return data;
	}
	
	
}
