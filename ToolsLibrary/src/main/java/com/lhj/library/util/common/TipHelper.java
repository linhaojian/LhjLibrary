package com.lhj.library.util.common;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;

import java.util.Random;

public class TipHelper {

	// 播放默认铃声
	// 返回Notification id
	public static int PlaySound(final Context context) {
		try {
			//获取系统提示音
			Uri notificationUri = RingtoneManager.getDefaultUri(
					RingtoneManager.TYPE_NOTIFICATION);
			MediaPlayer mediaPlayer = new MediaPlayer();
			mediaPlayer.setDataSource(context, notificationUri);
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
			mediaPlayer.setLooping(false);
			mediaPlayer.prepare();
			mediaPlayer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
			// This pattern is got from android.R.array.config_defaultNotificationVibePattern
			// which is used in NotificationManagerService#mDefaultVibrationPattern
			long[] pattern = {0, 350, 250, 350};
			vibrator.vibrate(pattern, -1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}


	/**
	 *
	 * @param context
	 * @param milliseconds//震动的时长，单位是毫秒
	 */
	public static void Vibrate(final Context context, long milliseconds) {
		Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(milliseconds);
	}
	/**
	 *
	 * @param context
	 * @param pattern：自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。时长的单位是毫秒
	 * @param isRepeat ： 是否反复震动，如果是true，反复震动，如果是false，只震动一次
	 */
	public static void Vibrate(final Context context, long[] pattern,boolean isRepeat) {
		Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(pattern, isRepeat ? 1 : -1);
	}
}

