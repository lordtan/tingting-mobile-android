package com.ccrt.app.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.os.Environment;


/**
 * Wrapper API for sending log output.
 */
public class AppLogger {
    protected static final String TAG = "oschina";

    public AppLogger() {
    }

    /**
     * Send a VERBOSE log message.
     *
     * @param msg The message you would like logged.
     */
    public static void v(String msg) {
        android.util.Log.v(TAG, buildMessage(msg));
    }

    /**
     * Send a VERBOSE log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void v(String msg, Throwable thr) {
        android.util.Log.v(TAG, buildMessage(msg), thr);
    }

    /**
     * Send a DEBUG log message.
     *
     * @param msg
     */
    public static void d(String msg) {
        android.util.Log.d(TAG, buildMessage(msg));
    }

    /**
     * Send a DEBUG log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr  An exception to log
     */
    public static void d(String msg, Throwable thr) {
        android.util.Log.d(TAG, buildMessage(msg), thr);
    }

    /**
     * Send an INFO log message.
     *
     * @param msg The message you would like logged.
     */
    public static void i(String msg) {
        android.util.Log.i(TAG, buildMessage(msg));
    }

    /**
     * Send a INFO log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void i(String msg, Throwable thr) {
        android.util.Log.i(TAG, buildMessage(msg), thr);
    }

    /**
     * Send an ERROR log message.
     *
     * @param msg The message you would like logged.
     */
    public static void e(String msg) {
        android.util.Log.e(TAG, buildMessage(msg));
    }

    /**
     * Send a WARN log message
     *
     * @param msg The message you would like logged.
     */
    public static void w(String msg) {
        android.util.Log.w(TAG, buildMessage(msg));
    }

    /**
     * Send a WARN log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void w(String msg, Throwable thr) {
        android.util.Log.w(TAG, buildMessage(msg), thr);
    }

    /**
     * Send an empty WARN log message and log the exception.
     *
     * @param thr An exception to log
     */
    public static void w(Throwable thr) {
        android.util.Log.w(TAG, buildMessage(""), thr);
    }

    /**
     * Send an ERROR log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void e(String msg, Throwable thr) {
        android.util.Log.e(TAG, buildMessage(msg), thr);
    }

    /**
     * Building Message
     *
     * @param msg The message you would like logged.
     * @return Message String
     */
    protected static String buildMessage(String msg) {
        StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[2];

        return new StringBuilder()
                .append(caller.getClassName())
                .append(".")
                .append(caller.getMethodName())
                .append("(): ")
                .append(msg).toString();
    }
    
    /**
	 * 
	 */
	public static void writeLog(String str) {
		d(str);
		if (hasSDcard()) {
			
			File sdCardDir = Environment.getExternalStorageDirectory();// 获取SDCard目录
			String model = android.os.Build.MODEL;
			String release = android.os.Build.VERSION.RELEASE;
			
			String filePath = sdCardDir + File.separator 
					          + "iSEAL" + File.separator 
					          + TAG + "_" + model + "_" + release + "_log.txt";
			
			File saveFile = new File(filePath);
			
			if(!saveFile.exists()){
				File parentFile = saveFile.getParentFile();
				if(!parentFile.exists()){
					parentFile.mkdirs();
				}
				try {
					saveFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
//			long preTime = saveFile.lastModified();
//			long currTime = System.currentTimeMillis();
//			if((currTime - preTime)> 2 * 24 * 60 * 60 * 1000){
//				saveFile.delete();
//				try {
//					saveFile.createNewFile();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
			
			
			try {
				FileWriter fw = new FileWriter(saveFile, true);
				fw.write(str);
				fw.flush();
				fw.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
		}
	}
	
	public static boolean hasSDcard(){
		boolean b = false;
		try {
			b = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
	
	
}
