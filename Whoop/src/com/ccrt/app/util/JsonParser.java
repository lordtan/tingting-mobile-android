package com.ccrt.app.util;

import com.google.gson.Gson;

/**
 * 简单json解析工具类
 * @author 谭罗乐
 * @date 2015年6月3日
 * 版权：绿豆科技有限公司
 */
public class JsonParser {
	private static Gson g = null;
	
	public static Gson getGson(){
		if(null==g){
			g = new Gson();
		}
		
		return g;
	}

    /**
     * 把对象转换成json字符串
     * @param src
     * @return
     */
	public static String toJson(Object src){
		return getGson().toJson(src);
	}
	
	/**
	 * 把json字符串转换成对象
	 * @param json
	 * @param classOfT
	 * @return
	 */
	public static <T> T toObj(String json, Class<T> classOfT){
		return getGson().fromJson(json, classOfT);
	}
	
}
