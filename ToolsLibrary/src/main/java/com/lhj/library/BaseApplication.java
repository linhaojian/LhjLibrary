package com.lhj.library;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.lhj.library.util.common.CrashHandler;
import com.lhj.library.util.common.Loger;
import com.lhj.library.util.common.SystemTools;

/**
 * Created by Administrator on 2016/12/19.
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.init(this);
        Loger.setPath(this);
        SystemTools.restartForChangeLanguage(SystemTools.getSystemLanguage(this));
    }


}
