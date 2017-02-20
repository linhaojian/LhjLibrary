package com.lhj.library.util.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class NetUtil {

	/**
	 *   MD5运算--密码
	 * @param string
	 * @return
	 */
	public static String md5(String string) {
	    byte[] hash;
	    try {
	        hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
	    } catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException("Huh, MD5 should be supported?", e);
	    } catch (UnsupportedEncodingException e) {
	        throw new RuntimeException("Huh, UTF-8 should be supported?", e);
	    }

	    StringBuilder hex = new StringBuilder(hash.length * 2);
	    for (byte b : hash) {
	        if ((b & 0xFF) < 0x10) hex.append("0");
	        hex.append(Integer.toHexString(b & 0xFF));
	    }
	    return hex.toString();
	}
	
	/**
	 *   判断WIFI是否连接上
	 * @param context
	 * @return
	 */
	public static boolean isWifiEnabled(Context context) {   
        ConnectivityManager mgrConn = (ConnectivityManager) context   
                .getSystemService(Context.CONNECTIVITY_SERVICE);   
//        TelephonyManager mgrTel = (TelephonyManager) context   
//                .getSystemService(Context.TELEPHONY_SERVICE);  
        NetworkInfo networkINfo = mgrConn.getActiveNetworkInfo();    
//        return ((mgrConn.getActiveNetworkInfo() != null && mgrConn   
//                .getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel   
//                .getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);   
        if (networkINfo != null   
                && networkINfo.getType() == ConnectivityManager.TYPE_WIFI) {   
            return true;   
        }   
        return false;   
    }
	
	/**
	 *   判断3G是否连接上
	 * @param context
	 * @return
	 */
	 public static boolean is3rd(Context context) {   
	        ConnectivityManager cm = (ConnectivityManager) context   
	                .getSystemService(Context.CONNECTIVITY_SERVICE);   
	        NetworkInfo networkINfo = cm.getActiveNetworkInfo();   
	        if (networkINfo != null   
	                && networkINfo.getType() == ConnectivityManager.TYPE_MOBILE) {   
	            return true;   
	        }   
	        return false;   
	    }  
	
	 /**
	  *   判断网络连接是否可用
	  * @param context
	  * @return
	  */
	 public static boolean isNetworkAvailable(Context context) {   
	        ConnectivityManager cm = (ConnectivityManager) context   
	                .getSystemService(Context.CONNECTIVITY_SERVICE);   
	        if (cm == null) {   
	        } else {
	        	//如果仅仅是用来判断网络连接
	        	//则可以使用 cm.getActiveNetworkInfo().isAvailable();  
	            NetworkInfo[] info = cm.getAllNetworkInfo();   
	            if (info != null) {   
	                for (int i = 0; i < info.length; i++) {   
	                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {   
	                        return true;   
	                    }   
	                }   
	            }   
	        }   
	        return false;   
	    } 
	 
}
