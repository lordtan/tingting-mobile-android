package com.ccrt.app.UI;

import java.util.ArrayList;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;


public class MainActivity extends SlidingFragmentActivity implements OnClickListener  {
	
	private Button buttonLeft;
	
	
	
	/***********地图所用控件***********/
	private MapView mapView;
	private AMap aMap;
	/***********地图所用控件***********/
	
	/**********侧滑菜单控件*************/
	protected SlidingMenu mSlidingMenu;
	private ImageButton ivTitleBtnLeft;
	private Fragment mContent;
	/**********侧滑菜单控件*************/
	
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initSlidingMenu();
		setContentView(R.layout.activity_main);
//		setUpMapIfNeeded();
		findViewById();
		
		
	}
	
	

	
	private void findViewById() {
		buttonLeft = (Button) findViewById(R.id.main_ivTitleBtnLeft);
		buttonLeft.setOnClickListener(this);
		
	}

	//初始化左侧菜单
		private void initSlidingMenu() {
			mContent = new Fragment_location();
			getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.content_frame, mContent)
			.commit();
			
			
			setBehindContentView(R.layout.main_left_layout);//设置左边的菜单布局
			
			FragmentTransaction mFragementTransaction = getSupportFragmentManager().beginTransaction();
			Fragment mFrag = new LeftSlidingMenuFragment();
			mFragementTransaction.replace(R.id.main_left_fragment, mFrag);
			mFragementTransaction.commit();
			
			mSlidingMenu = getSlidingMenu();
			mSlidingMenu.setMode(SlidingMenu.LEFT);// 设置是左滑还是右滑，还是左右都可以滑，我这里只做了左滑
			mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);// 设置菜单宽度
			mSlidingMenu.setFadeDegree(0.35f);// 设置淡入淡出的比例
			mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);//设置手势模式
			mSlidingMenu.setShadowDrawable(R.drawable.shadow);// 设置左菜单阴影图片
			mSlidingMenu.setFadeEnabled(true);// 设置滑动时菜单的是否淡入淡出
			mSlidingMenu.setBehindScrollScale(0.0f);// 设置滑动时拖拽效果
			mSlidingMenu.setBehindWidth(360);
			
		}
	
		
		/**
		 *    左侧菜单点击切换首页的内容
		 */
		public void switchContent(Fragment fragment) {
			mContent = fragment;
			getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.content_frame, fragment)
			.commit();
			getSlidingMenu().showContent();
			
		}
	
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_ivTitleBtnLeft:
			//点击标题左边按钮弹出左侧菜单
			mSlidingMenu.showMenu(true);
			
			break;

		default:
			break;
         }
	}
	

	
}
