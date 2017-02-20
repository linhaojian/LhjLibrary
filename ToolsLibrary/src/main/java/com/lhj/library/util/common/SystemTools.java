package com.lhj.library.util.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.Locale;

/**
 * Created by Administrator on 2016/12/19.
 */
public class SystemTools {

    /**
     * 获取包名
     * @param context
     * @return
     */
    public static String getApplicationName(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName =
                (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;
    }

    /**
     *  跳转界面
     * @param activity
     * @param class1
     * @param in
     * @param out
     */
    public static void turnActivity(Context activity, Class class1, int in, int out,int mark,String nameMark){
        if(in==0&&out==0){
            Intent intent=new Intent(activity,class1);
            if(nameMark!=null){
                intent.putExtra(nameMark,mark);
            }
            activity.startActivity(intent);
        }else{
            Intent intent=new Intent(activity,class1);
            if(nameMark!=null){
                intent.putExtra(nameMark,mark);
            }
            activity.startActivity(intent);
            ((Activity)activity).overridePendingTransition(in, out);
        }
    }

    /**
     *   重新启动APP--APPlication被杀
     * @param mContext
     */
    public static void restart(Context mContext){
        Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(intent);
        ((Activity)mContext).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        killProcess();
    }

    /**
     *   处理APP运行过程中--修改了多国语言
     *   流程：0nDestory  -->  OnCreate (可见activity)
     */
    private static String languge = "";
    public static void restartForChangeLanguage(String lang){
        if(languge!=null&&!languge.equals("")){
            if(!languge.equals(lang)){
                killProcess();
            }
        }
        languge = lang;
    }

    /**
     *  获取系统的应用
     * @param context
     * @return
     */
    public static String getSystemLanguage(Context context){
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        return language;
    }

    /**
     * 杀掉进程
     */
    public static void killProcess(){
        android.os.Process.killProcess(android.os.Process.myPid());  //结束进程之前可以把你程序的注销或者退出代码放在这段代码之前
    }

}
