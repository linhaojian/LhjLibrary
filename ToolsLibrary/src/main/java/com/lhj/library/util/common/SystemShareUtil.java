package com.lhj.library.util.common;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/11/10.
 */
public class SystemShareUtil {
    private static String path;
    /**
     * 截屏 （ 静态 ）
     * @return
     */
    public static void captureScreen(View view,Activity activity,String name) {
        path = Environment.getExternalStorageDirectory() + File.separator + SystemTools.getApplicationName(activity);
        view.setDrawingCacheEnabled(true);
        Bitmap bmp=view.getDrawingCache();
        savePicture(bmp,name);
        shareSingleImage(activity,name);
        view.setDrawingCacheEnabled(false);
    }

    private static void savePicture(Bitmap bm,String name){
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        File f1 = new File(path+File.separator,name+".jpg");
        try {
            FileOutputStream out = new FileOutputStream(f1);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //分享单张图片
    private static void shareSingleImage(Activity activity,String name) {
        String imagePath = path + File.separator + name+".jpg";
        //由文件得到uri
        Uri imageUri = Uri.fromFile(new File(imagePath));

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        activity.startActivity(Intent.createChooser(shareIntent, "Share"));
    }



}
