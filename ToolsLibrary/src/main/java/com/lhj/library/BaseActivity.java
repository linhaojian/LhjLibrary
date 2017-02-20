package com.lhj.library;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.lhj.library.util.common.PermissionTools;
import com.lhj.library.util.common.SystemTools;

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SystemTools.restartForChangeLanguage(SystemTools.getSystemLanguage(this));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode,grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            PermissionTools.permissonCallback(true);
        } else if(grantResults[0] == PackageManager.PERMISSION_DENIED){
            PermissionTools.permissonCallback(false);
        }
    }

}
