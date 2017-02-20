package com.lhj.library.util.common;

import java.util.List;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * 
 * 编写了一个Notification通知工具类，里面含有监听系统清除通知栏方法，焦点在加粗斜体部分：
 *
 */
public class Notifier {
	private static final String TAG = Notifier.class.getSimpleName();
	private static Notifier instance;
	private Context mContext;

			
	private static final int NOTIFICATION_ID_1 = 0;
	private static final int NOTIFICATION_ID_2 = 1;
	private static final int NOTIFICATION_ID_3 = 2;
	private static final int NOTIFICATION_ID_4 = 3;
	private static final int NOTIFICATION_ID_5 = 4;
	private static final int NOTIFICATION_ID_6 = 5;
	private static final int NOTIFICATION_ID_7 = 6;
	private static final int NOTIFICATION_ID_8 = 7;


	public static Notifier getInstance(Context context) {
		if (instance == null) {
			instance = new Notifier(context);
		}
		return instance;
	}
	
	
	public Notifier(Context context) {
		mContext = context;
	}


	public NotificationManager getNotificationManager(Context context) {
		return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	}


	public void showNotify(Context context, Class<?> desClzz, int largeIcon, int smallIcon,
			CharSequence tickerText, CharSequence contentInfo,
			CharSequence contentTitle, CharSequence contentText) {
	
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				new Intent(context, desClzz), 0);

		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),largeIcon);
		NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(context);
		// notifyBuilder.setLargeIcon(bitmap);
		notifyBuilder.setSmallIcon(smallIcon);
		notifyBuilder.setTicker(tickerText);
		notifyBuilder.setContentInfo(contentInfo);
		notifyBuilder.setContentTitle(contentTitle);
		notifyBuilder.setContentText(contentText);
		notifyBuilder.setAutoCancel(false);
		notifyBuilder.setWhen(System.currentTimeMillis());
		notifyBuilder.setDefaults(Notification.DEFAULT_ALL);
		notifyBuilder.setContentIntent(contentIntent);
		Notification noti = notifyBuilder.build();
		getNotificationManager(context).notify(NOTIFICATION_ID_1, noti);
	}

	
	public void showCustomViewNotify(Context context, Class<?> desClzz,int smallIcon,
			CharSequence tickerText, int layoutId) {
		RemoteViews mRemoteViews = new RemoteViews(context.getPackageName(),layoutId);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				new Intent(context,desClzz), 0);
		NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(context);
		notifyBuilder.setSmallIcon(smallIcon);
		notifyBuilder.setContent(mRemoteViews);
		notifyBuilder.setTicker(tickerText);
		notifyBuilder.setOngoing(true);
		notifyBuilder.setAutoCancel(true);
		notifyBuilder.setDefaults(Notification.DEFAULT_ALL);
		notifyBuilder.setContentIntent(contentIntent);
		
		Notification noti = notifyBuilder.build();
		Intent dismissedIntent = new Intent(
		"com.xxxx.Android.notify.dismissed");
		/**
		* The intent to execute when the notification is explicitly dismissed
		* by the user, either with the "Clear All" button or by swiping it away
		* individually.
		* 
		* This probably shouldn't be launching an activity since several of
		* those will be sent at the same time.
		*/
		noti.deleteIntent = PendingIntent.getBroadcast(context, 0,
		dismissedIntent, 0);
		getNotificationManager(context).notify(NOTIFICATION_ID_2, noti);
	}

	
	public Notification getForegroundServiceNotify(Context context, Class<?> desClzz,
		int smallIcon, CharSequence tickerText, CharSequence contentInfo,
		CharSequence contentTitle, CharSequence contentText) {
		
		
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				new Intent(context, desClzz), 0);
		NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(context);
		notifyBuilder.setSmallIcon(smallIcon);
		notifyBuilder.setTicker(tickerText);
		notifyBuilder.setContentInfo(contentInfo);
		notifyBuilder.setContentTitle(contentTitle);
		notifyBuilder.setContentText(contentText);
		notifyBuilder.setAutoCancel(false);
		notifyBuilder.setContentIntent(contentIntent);
		// notifyBuilder.setWhen(System.currentTimeMillis());
		notifyBuilder.setDefaults(Notification.DEFAULT_ALL);
		return notifyBuilder.build();
	}
	
	/**
     * 用来判断服务是否运行.
     * @param context
     * @param className 判断的服务名字：包名+类名
     * @return true 在运行, false 不在运行
     */
      
    public boolean isServiceRunning(Context context,String className) {        
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE); 
        List<ActivityManager.RunningServiceInfo> serviceList 
        	= activityManager.getRunningServices(Integer.MAX_VALUE);
        if (!(serviceList.size()>0)) {
            return false;
        }
      
        for (int i=0; i<serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        Log.i(TAG,"service is running?=="+isRunning);
        return isRunning;
    }
}
