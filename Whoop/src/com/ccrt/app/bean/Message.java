package com.ccrt.app.bean;


/**
 * 服务器返回信息
 * @author lordtan
 * @date 2015年5月21日
 */
public class Message {
    public static int SUCCESS=1;  //成功
    public static int FAILED=0;  //失败
	
	private String msg;  //消息内容
	private int state = FAILED; //状态，只有两种状态，成功或者失败，默认为失败
	public String getMsg() {
		return msg;
	}
	public Message setMsg(String msg) {
		this.msg = msg;
		return this;
	}
	public int getState() {
		return state;
	}
	public Message setState(int state) {
		this.state = state;
		return this;
	}
	
	public static Message getInstance(){
		return new Message();
	} 
	
//	@Override
//	public String toString() {
//		return JsonUtil.obj2json(this);
//	}
}
