package com.lhj.library.util.common;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

/**
 * Created by Administrator on 2016/12/19.
 */
public class BitmapUtil {

    /**
     *  Uri To Path
     * @param uri
     * @param context
     * @return
     */
    public static String UritoPath(Uri uri, Context context){
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if(cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    /**
     *  Path To Bitmap
     * @param imagePath
     * @return
     */
    public static Bitmap PathtoBitmap(String imagePath){
        if(imagePath==null||imagePath.equals("")){
            return null;
        }
        int degree = readPictureDegree(imagePath);
        Bitmap bm = decodeSampledBitmapFromPath(imagePath, 200, 200);
        bm = rotateBitmap(bm, degree);
        return bm;
    }

    /**
     *   通过指定的路径，指定的宽高，进行图片的分辨率压缩或者缩放
     * @param path
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromPath(String path, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        options.inPurgeable = true;
        options.inInputShareable = true;

        return BitmapFactory.decodeFile(path, options);
    }

    /**
     *  处理指定宽与高的图片(缩放)
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger inSampleSize).
            long totalPixels = width * height / inSampleSize;

            // Anything more than 2x the requested pixels we'll sample down further
            final long totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels > totalReqPixelsCap) {
                inSampleSize *= 2;
                totalPixels /= 2;
            }
        }
        return inSampleSize;
    }

    /**
     *  读取指定路径的图片的角度
     * @param path
     * @return
     */
    public static int readPictureDegree(String path){
        int degree=0;
        try {
            ExifInterface e=new ExifInterface(path);
            int o=e.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (o) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree=90;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree=180;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree=270;
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     *  将bitmap与指定角度进行处理
     * @param bitmap
     * @param degress
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bitmap,int degress){
        if(bitmap!=null){
            Matrix m=new Matrix();
            m.postRotate(degress);
            bitmap=Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }


}
