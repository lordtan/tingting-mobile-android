package com.ccrt.app.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

/**
 * 弹出框工具类
 * 
 */
public class AlertTools {
	
	/**
	 * 弹出对话框，只有确定按钮,标题为提�?
	 * @param ctx
	 * @param text
	 * @return
	 */
	public static AlertDialog showConfirmAlert(Context ctx,String text){
		return showConfirmAlert(ctx,"提示",text,"确定");
	}
	/**
	 * 弹出对话框，只有确定按钮
	 * @param ctx
	 * @param title
	 * @param text
	 * @return
	 */
	public static AlertDialog showConfirmAlert(Context ctx,String title,String text){
		return showConfirmAlert(ctx,title,text,"确定");
	}
	
	/**
	 * 弹出确定对话框，确定提供监听处理
	 * @param ctx
	 * @param title
	 * @param text
	 * @param doYes
	 * @return
	 */
	public static AlertDialog showConfirmAlert(Context ctx,String title,String text,DialogInterface.OnClickListener doYes){
		return showConfirmAlert(ctx,title,text,"确定",doYes);
	}
	
	/**
	 * 弹出�?��按钮的对话框，需传入按钮名称
	 * @param ctx
	 * @param title
	 * @param text
	 * @param sbtn 按钮�?
	 * @return
	 */
	public static AlertDialog showConfirmAlert(Context ctx,String title,String text,String sbtn){
		return showConfirmAlert(ctx, title,text,sbtn, new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
	}
	
	/**
	 * 弹出�?��按钮的对话框，需传入按钮名称,及按钮的点击事件
	 * @param ctx
	 * @param title
	 * @param text
	 * @param sbtn
	 * @param doYes
	 * @return
	 */
	public static AlertDialog showConfirmAlert(Context ctx,String title,String text,String sbtn,DialogInterface.OnClickListener doYes){
		Builder ad = new AlertDialog.Builder(ctx);
		ad.setTitle(title);
		ad.setMessage(text);
		ad.setPositiveButton(sbtn, doYes);
		ad.setCancelable(true);
		return ad.show();
	}
	
	/**
	 * 弹出确定按钮对话框，点击后关闭当前activity
	 * @param ctx
	 * @param title
	 * @param text
	 * @return
	 */
	public static AlertDialog showAlertAndClose(final Context ctx,String title,String text){
		AlertDialog dialog = showAlertAndClose(ctx,title,text,"确定");
//		dialog.setOnCancelListener(new OnCancelListener() {
//			
//			@Override
//			public void onCancel(DialogInterface dialog) {
//				dialog.dismiss();
//				((Activity)ctx).finish();
//			}
//		});
		return dialog;
	}
	
	/**
	 * 弹出唯一的按钮对话框，点击后关闭当前activity
	 * @param ctx
	 * @param title
	 * @param text
	 * @param sbtn 按钮�?
	 * @return
	 */
	public static AlertDialog showAlertAndClose(final Context ctx,String title,String text,String sbtn){
		Builder ad =new AlertDialog.Builder(ctx);
		ad.setTitle(title);
		ad.setMessage(text);
		ad.setPositiveButton(sbtn, new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if( ctx instanceof Activity){
					dialog.dismiss();
					((Activity)ctx).finish();
				}
			}
			
		});
		ad.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				dialog.dismiss();
				((Activity)ctx).finish();
			}
		});
		ad.setCancelable(true);
		return ad.show();
	}
	
	/**
	 * 弹出对话框，只有确定按钮
	 * @param ctx
	 * @param title
	 * @param text
	 * @return
	 */
	public static AlertDialog showConfirmAlert(Context ctx,int title,int text){
		Builder ad=new AlertDialog.Builder(ctx);
		ad.setTitle(title);
		ad.setMessage(text);
		ad.setPositiveButton("确定", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
			
		});
		
		ad.setCancelable(true);
		return ad.show();
	}
	
	/**
	 * 弹出 确定，取消对话框�?�?��传�?点击监听
	 * @param ctx
	 * @param title
	 * @param text
	 * @param doYes
	 * @param doNo
	 * @return
	 */
	public static AlertDialog showTwoBtnAlert(Context ctx,String title,String text,DialogInterface.OnClickListener doYes,DialogInterface.OnClickListener doNo){
		return showTwoBtnAlert(ctx,title,text,"确定","取消",doYes,doNo);
	}
	
	/**
	 * 弹出 两个按钮的对话框�?�?��传�?点击监听
	 * @param ctx
	 * @param title 标题
	 * @param text  提示内容
	 * @param posBtnStr 前一按钮�?
	 * @param negatBtnStr 后一按钮�?
	 * @param doYes 前一按钮点击事件
	 * @param doNo  后一按钮点击事件
	 * @return
	 */
	public static AlertDialog showTwoBtnAlert(Context ctx,String title,String text,String posBtnStr,String negatBtnStr,DialogInterface.OnClickListener doYes,DialogInterface.OnClickListener doNo){
		
		Builder a=new Builder(ctx);
		a.setTitle(title);
		a.setMessage(text);
		a.setCancelable(true);
		a.setPositiveButton(posBtnStr, doYes);//显示对话�?
		if(doNo!=null){
			a.setNegativeButton(negatBtnStr,doNo);
		}
		a.create();
		return a.show();
	}

	 
}
