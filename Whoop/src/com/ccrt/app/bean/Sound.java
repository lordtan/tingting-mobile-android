package com.ccrt.app.bean;

import java.io.IOException;
import java.util.Date;


/**
 * 声音信息，包括用户，声音文件id等
 * 经纬度暂时依赖百度地图，其实还是需要存一个真实经纬度的
 * @author lordtan
 * @date 2015年4月20日
 */
public class Sound {

	private String id; //id
	private String userId; //声音所有者，用户id
	private String fileId; //声音文件id
	private String title; //标题
	private String type; //类型
	private String lat; //纬度，百度坐标的前一个值
	private String lng; //经度，百度坐标的后一个值
	private String gpsLat; //从手机的gps中读取的真实纬度，不是经过百度地图转换过的
	private String gpsLng; //从手机的gps中读取的真实 经度，不是经过百度地图转换过的
	private int isActive; //是否激活，如果存在时间过长会失去激活
	private long great; //被顶次数
	private long degrade; //被踩次数
	private long listened; //被收听次数
	private long createdDate; //创建时间,格林尼治时间UT，到当前的毫秒数
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getGpsLat() {
		return gpsLat;
	}
	public void setGpsLat(String gpsLat) {
		this.gpsLat = gpsLat;
	}
	public String getGpsLng() {
		return gpsLng;
	}
	public void setGpsLng(String gpsLng) {
		this.gpsLng = gpsLng;
	}
	public long getGreat() {
		return great;
	}
	public void setGreat(long great) {
		this.great = great;
	}
	public long getDegrade() {
		return degrade;
	}
	public void setDegrade(long degrade) {
		this.degrade = degrade;
	}
	public long getListened() {
		return listened;
	}
	public void setListened(long listened) {
		this.listened = listened;
	}
	public long getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(long createdDate) {
		this.createdDate = createdDate;
	}
	
//	@Override
//	public String toString() {
//		return JsonUtil.obj2json(this);
//	}
}
