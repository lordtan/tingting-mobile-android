package com.ccrt.app.UI;

import java.util.ArrayList;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.ccrt.app.util.AppLogger;
import com.ccrt.app.util.CKTools;
import com.ccrt.app.util.SoundMeter;

public class Fragment_location extends Fragment implements LocationSource,
		AMapLocationListener, OnClickListener , OnTouchListener{
	private static final int POLL_INTERVAL = 300;
	
	private MapView mapView;
	private AMap mMap;
	private View view;
	private Marker marker;// 定位雷达小图标
	private LocationManagerProxy mAMapLocationManager;
	private OnLocationChangedListener mListener;

	/************** 发送语音控件 *****************/
	private ImageView imageViewPopUp;
	private TextView buttonRcd; //按住说话
	private View rcChat_popup;
//	private LinearLayout voice_rcd_hint_loading , voice_rcd_hint_rcding , voice_rcd_hint_tooshort;
	private ImageView volume;
	private ImageView chatting_mode_btn;//录音与文字切换按钮
	private RelativeLayout rlBottomWZ;//底部控件布局
	private LinearLayout del_re;
	private ImageView img1;
	
	private boolean btn_vocie = false;
	private boolean isShosrt = false;
	private int flag = 1;
	private long startVoiceT, endVoiceT;
	private String voiceName;
	private SoundMeter mSensor;
	private Handler mHandler = new Handler();
	

	/************** 发送语音控件 *****************/

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_location, container, false);
		mapView = (MapView) view.findViewById(R.id.mapView);
		mapView.onCreate(savedInstanceState);
		init();

		return view;
	}

	/**
	 * 初始化
	 */
	private void init() {
		if (mMap == null) {
			mMap = mapView.getMap();
			setUpMap();
		}
		imageViewPopUp = (ImageView) view.findViewById(R.id.ivPopUp);
		imageViewPopUp.setOnClickListener(this);
		
		
		mSensor = new SoundMeter();
		
		chatting_mode_btn = (ImageView) view.findViewById(R.id.ivPopUp);
		chatting_mode_btn.setOnClickListener(this);
		buttonRcd = (TextView) view.findViewById(R.id.btn_rcd);
		buttonRcd.setOnTouchListener(this);
		rlBottomWZ = (RelativeLayout) view.findViewById(R.id.btn_bottom_wenzi);
		
		del_re = (LinearLayout) view.findViewById(R.id.del_re);
		rcChat_popup = view.findViewById(R.id.rcChat_popup);
//		voice_rcd_hint_rcding = (LinearLayout)view.findViewById(R.id.voice_rcd_hint_rcding);
//		voice_rcd_hint_loading = (LinearLayout)view.findViewById(R.id.voice_rcd_hint_loading);
//		voice_rcd_hint_tooshort = (LinearLayout)view.findViewById(R.id.voice_rcd_hint_tooshort);
		volume = (ImageView) this.view.findViewById(R.id.volume);
		img1 = (ImageView) view.findViewById(R.id.img1);
		img1.setVisibility(View.GONE);
	
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivPopUp://录音与文字切换按钮
		if(btn_vocie){
			btn_vocie = false;
			buttonRcd.setVisibility(View.GONE);//隐藏
			rlBottomWZ.setVisibility(View.VISIBLE);//显示
			chatting_mode_btn.setImageResource(R.drawable.chatting_setmode_msg_btn);
		}else{
			btn_vocie = true;
			buttonRcd.setVisibility(View.VISIBLE);//显示
			rlBottomWZ.setVisibility(View.GONE);//隐藏
			chatting_mode_btn.setImageResource(R.drawable.chatting_setmode_voice_btn);
		}
		break;

		default:
			break;
		}

	}

	
	
	
	
	
	

	
	private void start(String name) {
		mSensor.start(name);
		mHandler.postDelayed(mPollTask, POLL_INTERVAL);
	}
	private void stop() {
		mHandler.removeCallbacks(mSleepTask);
		mHandler.removeCallbacks(mPollTask);
		mSensor.stop();
		volume.setImageResource(R.drawable.amp1);
	}
	
	private Runnable mPollTask = new Runnable() {
		public void run() {
			double amp = mSensor.getAmplitude();
			updateDisplay(amp);
			mHandler.postDelayed(mPollTask, POLL_INTERVAL);

		}
	};
	
	private Runnable mSleepTask = new Runnable() {
		public void run() {
			stop();
		}
	};
	
	
	/**
	 * 设置一些amap的属性
	 */
	private void setUpMap() {
		ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
		giflist.add(BitmapDescriptorFactory.fromResource(R.drawable.point1));
		giflist.add(BitmapDescriptorFactory.fromResource(R.drawable.point2));
		giflist.add(BitmapDescriptorFactory.fromResource(R.drawable.point3));
		giflist.add(BitmapDescriptorFactory.fromResource(R.drawable.point4));
		giflist.add(BitmapDescriptorFactory.fromResource(R.drawable.point5));
		giflist.add(BitmapDescriptorFactory.fromResource(R.drawable.point6));
		marker = mMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
				.icons(giflist).period(50));
		// 自定义系统定位小蓝点
		MyLocationStyle myLocationStyle = new MyLocationStyle();
		myLocationStyle.myLocationIcon(BitmapDescriptorFactory
				.fromResource(R.drawable.location_marker));// 设置小蓝点的图标
		myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
		myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
		// myLocationStyle.anchor(int,int)//设置小蓝点的锚点
		myLocationStyle.strokeWidth(0.1f);// 设置圆形的边框粗细
		mMap.setMyLocationStyle(myLocationStyle);
		mMap.setMyLocationRotateAngle(180);
		mMap.setLocationSource(this);// 设置定位监听
		mMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		mMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
		// 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
		mMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);

	}

	/**
	 * 定位成功后回调函数
	 */
	@Override
	public void onLocationChanged(AMapLocation location) {
		if (mListener != null && location != null) {
			mListener.onLocationChanged(location);// 显示系统小蓝点
		}
	}

	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
		if (mAMapLocationManager == null) {
			mAMapLocationManager = LocationManagerProxy
					.getInstance(getActivity());
			/*
			 * mAMapLocManager.setGpsEnable(false);
			 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
			 * API定位采用GPS和网络混合定位方式
			 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
			 */
			mAMapLocationManager.requestLocationUpdates(
					LocationProviderProxy.AMapNetwork, 2000, 10, this);
		}
	}
	
	
	
	/**
	 * 停止定位
	 */
	@Override
	public void deactivate() {
		mListener = null;
		if (mAMapLocationManager != null) {
			mAMapLocationManager.removeUpdates(this);
			mAMapLocationManager.destory();
		}
		mAMapLocationManager = null;
	}

	
	private void updateDisplay(double signalEMA) {
		
		switch ((int) signalEMA) {
		case 0:
		case 1:
			volume.setImageResource(R.drawable.amp1);
			break;
		case 2:
		case 3:
			volume.setImageResource(R.drawable.amp2);
			
			break;
		case 4:
		case 5:
			volume.setImageResource(R.drawable.amp3);
			break;
		case 6:
		case 7:
			volume.setImageResource(R.drawable.amp4);
			break;
		case 8:
		case 9:
			volume.setImageResource(R.drawable.amp5);
			break;
		case 10:
		case 11:
			volume.setImageResource(R.drawable.amp6);
			break;
		default:
			volume.setImageResource(R.drawable.amp7);
			break;
		}
	}
	
	
	
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResume() {
		AppLogger.i("mf onResume");
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写 map的生命周期方法
	 */
	@Override
	public void onPause() {
		AppLogger.i("mf onPause");
		super.onPause();
		mapView.onPause();
	}

	/**
	 * 方法必须重写 map的生命周期方法
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		AppLogger.i("mf onSaveInstanceState");
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写 map的生命周期方法
	 */
	@Override
	public void onDestroy() {
		AppLogger.i("mf onDestroy");
		super.onDestroy();
		mapView.onDestroy();
	}

	
	 /**
     * 按下语音录制按钮时
     */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		AppLogger.e("event: " + event);

		if(!Environment.getExternalStorageDirectory().exists()){
			CKTools.toast(getActivity(), "没有SD卡");
			return false;
		}

		// TODO Auto-generated method stub
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			AppLogger.e("开始录音");
			buttonRcd.setBackgroundResource(R.drawable.voice_rcd_btn_pressed);
			rcChat_popup.setVisibility(View.VISIBLE);
			buttonRcd.setText("松开  结束");
			buttonRcd.setTextColor(getResources().getColor(R.color.white));
			int[] location = new int[2];
			buttonRcd.getLocationInWindow(location); // 获取在当前窗口内的绝对坐标
			int btn_rc_Y = location[1];
			int btn_rc_X = location[0];
			if(flag==1){
				if (!Environment.getExternalStorageDirectory().exists()) {
					CKTools.toast(getActivity(), "No SDCard");
					return false;
				}
				System.out.println("2");
				System.out.println(event.getY()+"..."+btn_rc_Y+"...."+event.getX() +"...."+btn_rc_X);
				if (event.getY() < btn_rc_Y && event.getX() > btn_rc_X) {//判断手势按下的位置是否是语音录制按钮的范围内
					System.out.println("3");
					rcChat_popup.setVisibility(View.VISIBLE);
					mHandler.postDelayed(new Runnable() {
						public void run() {
							if (!isShosrt) {
//								voice_rcd_hint_rcding.setVisibility(View.VISIBLE);
//								voice_rcd_hint_loading.setVisibility(View.GONE);
								rcChat_popup.setVisibility(View.VISIBLE);
							}
							
						}
					}, 300);
					startVoiceT = SystemClock.currentThreadTimeMillis();
					voiceName = startVoiceT + ".amr";
					start(voiceName);//在此已经将声音写入sd卡
					flag = 2;
				}
			}
		}else if(event.getAction()==MotionEvent.ACTION_UP){
			AppLogger.e("放开录音");
			buttonRcd.setBackgroundResource(R.drawable.voice_rcd_btn_nor);
			buttonRcd.setText("按住  说话");
			buttonRcd.setTextColor(Color.BLUE);
//			timedown.stop();
			if(flag==2){
				rcChat_popup.setVisibility(View.GONE);
				stop();
				flag = 1;
//				soundUse(voiceName);
				
//				File file = new File(android.os.Environment.getExternalStorageDirectory()+"/hq_100/"+ voiceName);
//				if (file.exists()) {
//					file.delete();
//				}
			}else {
//				voice_rcd_hint_rcding.setVisibility(View.GONE);
				stop();
				endVoiceT = SystemClock.currentThreadTimeMillis();
				flag = 1;
				int time = (int) ((endVoiceT - startVoiceT) / 1000);
				System.out.println(time);
			}
		}
		return true;
	
	
	}

}
