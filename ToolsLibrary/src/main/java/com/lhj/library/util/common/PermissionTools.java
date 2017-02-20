package com.lhj.library.util.common;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lhj.library.R;


public class PermissionTools {
	public static final int LOCATION_PERMISSION = 0;
	public static final int CAMERA_PERMISSON = 1;
	public static final int RECORD_PERMISSON = 2;
	public static final int EXTERNAL_STORAGE_PERMISSON = 3;
	public static final int PHONE_STATUS_PERMISSON = 4;
	public static final int SMS_STATUS_PERMISSON = 5;
	private static QueueHanlder<ResultPermissonCallback> queueHanlder;

	static {
		queueHanlder = new QueueHanlder<ResultPermissonCallback>();
	}

	private static boolean CheckAndRequestPermission(Activity activity,int requesCode,String permission){
		 if (ContextCompat.checkSelfPermission(activity, permission)
	              != PackageManager.PERMISSION_GRANTED) {
			 return true;
	      }
		return  false;
	}

	/**
	 *  跳转到本应用的管理界面
	 * @param activity
     */
	public static void IntentSetting(Activity activity){
		Uri packageURI = Uri.parse("package:" + activity.getPackageName());
		Intent intent =  new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,packageURI);
		activity.startActivity(intent);
	}

	/**
	 *  检测android6.0以上系统，定位权限是否开启
	 * @param activity
	 * @return
     */
	public static boolean CheckLocationPermissionForM(Activity activity){
		if(Build.VERSION.SDK_INT >= 23){
			 if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
		              != PackageManager.PERMISSION_GRANTED
		              || ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
		              != PackageManager.PERMISSION_GRANTED) {

				 return true;
			 }
		}
		return false;
	}

	/**
	 *  请求系统定位权限权限dialog
	 * @param activity
	 */
	public static void RequestPermissonForLocation(Activity activity){
		ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},LOCATION_PERMISSION);
	}

	/**
	 *  检测拍照权限是否打开(自定义照相)
	 * @param activity
	 * @return
     */
	public static boolean CheckCameraPermisson(Activity activity){
		return CheckAndRequestPermission(activity,CAMERA_PERMISSON, Manifest.permission.CAMERA);
	}

	/**
	 *  请求系统拍照权限dialog
	 * @param activity
     */
	public static void RequestPermissonForCamera(Activity activity){
		ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.CAMERA},CAMERA_PERMISSON);
	}

	/**
	 *  检测录音权限是否打开
	 * @param activity
	 * @return
	 */
	public static boolean CheckRecordPermisson(Activity activity){
		return CheckAndRequestPermission(activity,RECORD_PERMISSON, Manifest.permission.RECORD_AUDIO);
	}

	/**
	 *  请求系统录音权限dialog
	 * @param activity
	 */
	public static void RequestPermissonForRecord(Activity activity){
		ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.RECORD_AUDIO},RECORD_PERMISSON);
	}

	/**
	 *  检测文件存储权限是否打开
	 * @param activity
	 * @return
	 */
	public static boolean CheckExternalStoragePermisson(Activity activity){
		return CheckAndRequestPermission(activity,EXTERNAL_STORAGE_PERMISSON, Manifest.permission.WRITE_EXTERNAL_STORAGE)
				||CheckAndRequestPermission(activity,EXTERNAL_STORAGE_PERMISSON, Manifest.permission.READ_EXTERNAL_STORAGE);
	}

	/**
	 *  请求系统文件存储权限dialog
	 * @param activity
	 */
	public static void RequestPermissonExternalStorage(Activity activity){
		ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},EXTERNAL_STORAGE_PERMISSON);
	}

	/**
	 *  检测读取电话权限是否打开
	 * @param activity
	 * @return
	 */
	public static boolean CheckPhonePermisson(Activity activity){
		return CheckAndRequestPermission(activity,PHONE_STATUS_PERMISSON, Manifest.permission.READ_PHONE_STATE);
	}

	/**
	 *  请求系统读取电话权限dialog
	 * @param activity
	 */
	public static void RequestPermissonForPhone(Activity activity){
		ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.READ_PHONE_STATE},PHONE_STATUS_PERMISSON);
	}

	/**
	 *  检测读取短信权限是否打开
	 * @param activity
	 * @return
	 */
	public static boolean CheckSMSPermisson(Activity activity){
		return CheckAndRequestPermission(activity,SMS_STATUS_PERMISSON, Manifest.permission.READ_SMS)
				||CheckAndRequestPermission(activity,SMS_STATUS_PERMISSON, Manifest.permission.RECEIVE_SMS);
	}

	/**
	 *  请求系统读取短信权限dialog
	 * @param activity
	 */
	public static void RequestPermissonForSMS(Activity activity){
		ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.READ_SMS,Manifest.permission.RECEIVE_SMS},SMS_STATUS_PERMISSON);
	}

	/**
	 *  判断系统，提示不同的窗口---读取短信
	 * @param activity
	 */
	public static void ShowSMS(Activity activity,ResultPermissonCallback resultPermissonCallback){
		if(Build.VERSION.SDK_INT >= 23) {
			showDialog(activity,R.string.ALERT,R.string.sms_per_sixup,true,SMS_STATUS_PERMISSON);
		}else{
			showDialog(activity,R.string.ALERT,R.string.sms_per_sixdown,false,SMS_STATUS_PERMISSON);
			permissonResult(resultPermissonCallback,false);
		}
	}

	/**
	 *  判断系统，提示不同的窗口---读取电话
	 * @param activity
	 */
	public static void ShowPhone(Activity activity,ResultPermissonCallback resultPermissonCallback){
		if(Build.VERSION.SDK_INT >= 23) {
			showDialog(activity, R.string.ALERT,R.string.phone_per_sixup,true,PHONE_STATUS_PERMISSON);
		}else{
			showDialog(activity,R.string.ALERT,R.string.phone_per_sixdown,false,PHONE_STATUS_PERMISSON);
			permissonResult(resultPermissonCallback,false);
		}
	}

	/**
	 *  判断系统，提示不同的窗口---拍照
	 * @param activity
	 */
	public static void ShowCamera(Activity activity,ResultPermissonCallback resultPermissonCallback){
		if(Build.VERSION.SDK_INT >= 23) {
			showDialog(activity,R.string.ALERT,R.string.camera_per_sixup,true,CAMERA_PERMISSON);
		}else{
			showDialog(activity,R.string.ALERT,R.string.camera_per_sixdown,false,CAMERA_PERMISSON);
			permissonResult(resultPermissonCallback,false);
		}
	}

	/**
	 *  判断系统，提示不同的窗口---录音
	 * @param activity
	 */
	public static void ShowRecord(Activity activity,ResultPermissonCallback resultPermissonCallback){
		if(Build.VERSION.SDK_INT >= 23) {
			showDialog(activity,R.string.ALERT,R.string.record_per_sixup,true,RECORD_PERMISSON);
		}else{
			showDialog(activity,R.string.ALERT,R.string.record_per_sixdown,false,RECORD_PERMISSON);
			permissonResult(resultPermissonCallback,false);
		}
	}

	/**
	 *  判断系统，提示不同的窗口---文件存储
	 * @param activity
     */
	public static void ShowExternalStorage(Activity activity,ResultPermissonCallback resultPermissonCallback){
		if(Build.VERSION.SDK_INT >= 23) {
			showDialog(activity,R.string.ALERT,R.string.storage_per_sixup,true,EXTERNAL_STORAGE_PERMISSON);
		}else{
			showDialog(activity,R.string.ALERT,R.string.storage_per_sixdown,false,EXTERNAL_STORAGE_PERMISSON);
			permissonResult(resultPermissonCallback,false);
		}
	}

	/**
	 * 弹出系统6.0以上的提示窗口--定位权限
	 * @param activity
	 */
	public static void ShowLocationSixUp(Activity activity){
		showDialog(activity,R.string.ALERT,R.string.location_per,true,LOCATION_PERMISSION);
	}

	private static void showDialog(final Context mContext, int body, int text,final boolean isSixUp,final int per){
		final AlertDialog alertDialog1 = new AlertDialog.Builder(mContext)
				.setCancelable(false)
				.create();
		alertDialog1.show();
		Window window = alertDialog1.getWindow();
		window.setContentView(R.layout.alertdialog);
		window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		android.view.WindowManager.LayoutParams layoutp = window.getAttributes();
//		layoutp.dimAmount=0;
		window.setAttributes(layoutp);
		TextView tvTitle=(TextView) window.findViewById(R.id.tv_alert_title);
		TextView tvText=(TextView) window.findViewById(R.id.tv_alert_text);
		Button btnOK=(Button) window.findViewById(R.id.btn_alert_ok);
		tvTitle.setText(body);
		tvText.setText(text);
		btnOK.setText(R.string.OK);
		btnOK.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				alertDialog1.cancel();
				if(isSixUp){
					if(per==EXTERNAL_STORAGE_PERMISSON){
						RequestPermissonExternalStorage((Activity) mContext);
					}else if(per==LOCATION_PERMISSION){
						RequestPermissonForLocation((Activity)mContext);
					}else if(per==RECORD_PERMISSON){
						RequestPermissonForRecord((Activity)mContext);
					}else if(per==CAMERA_PERMISSON){
						RequestPermissonForCamera((Activity)mContext);
					}else if(per==PHONE_STATUS_PERMISSON){
						RequestPermissonForPhone((Activity)mContext);
					}else if(per==SMS_STATUS_PERMISSON){
						RequestPermissonForSMS((Activity)mContext);
					}
				}
			}
		});
	}

	/**
	 * 处理android 6.0 定位权限的流程
	 * @param activity
     */
	public static void dealLocationPermission(Activity activity,ResultPermissonCallback resultPermissonCallback){
		addQue(resultPermissonCallback);
		if(!CheckLocationPermissionForM(activity)){
			ShowLocationSixUp(activity);
		}else{
			permissonResult(resultPermissonCallback,true);
		}
	}

	/**
	 * 处理拍照权限的流程
	 * @param activity
	 */
	public static void dealCameraPermisson(Activity activity,ResultPermissonCallback resultPermissonCallback){
		addQue(resultPermissonCallback);
		if(!CheckCameraPermisson(activity)){
			ShowCamera(activity,resultPermissonCallback);
		}else{
			permissonResult(resultPermissonCallback,true);
		}
	}

	/**
	 * 处理录音权限的流程
	 * @param activity
	 */
	public static void dealRecordPermisson(Activity activity,ResultPermissonCallback resultPermissonCallback){
		addQue(resultPermissonCallback);
		if(!CheckRecordPermisson(activity)){
			ShowRecord(activity,resultPermissonCallback);
		}else{
			permissonResult(resultPermissonCallback,true);
		}
	}

	/**
	 * 处理文件存储权限的流程
	 * @param activity
	 */
	public static void dealExternalStoragePermisson(Activity activity,ResultPermissonCallback resultPermissonCallback){
		addQue(resultPermissonCallback);
		if(!CheckExternalStoragePermisson(activity)){
			ShowExternalStorage(activity,resultPermissonCallback);
		}else{
			permissonResult(resultPermissonCallback,true);
		}
	}

	/**
	 * 处理读取来电状态权限的流程
	 * @param activity
	 */
	public static void dealPhonePermisson(Activity activity,ResultPermissonCallback resultPermissonCallback){
		addQue(resultPermissonCallback);
		if(!CheckPhonePermisson(activity)){
			ShowPhone(activity,resultPermissonCallback);
		}else{
			permissonResult(resultPermissonCallback,true);
		}
	}

	/**
	 * 处理读取短信状态权限的流程
	 * @param activity
	 */
	public static void dealSMSPermisson(Activity activity,ResultPermissonCallback resultPermissonCallback){
		addQue(resultPermissonCallback);
		if(!CheckSMSPermisson(activity)){
			ShowSMS(activity,resultPermissonCallback);
		}else{
			permissonResult(resultPermissonCallback,true);
		}
	}

	private static void permissonResult(ResultPermissonCallback resultPermissonCallback,boolean isGranted){
		resultPermissonCallback.PermissonResult(isGranted);
		queueHanlder.removeEntry();
	}

	public interface ResultPermissonCallback{
		void PermissonResult(boolean result);
	}

	private static void addQue(ResultPermissonCallback resultPermissonCallback){
		queueHanlder.addQueue(resultPermissonCallback);
	}

	public static void permissonCallback(boolean isGranted){
		permissonResult(queueHanlder.getEntry(),isGranted);
	}

}
