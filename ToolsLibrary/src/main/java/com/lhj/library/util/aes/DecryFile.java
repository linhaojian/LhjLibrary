package com.lhj.library.util.aes;

import android.content.Context;

import com.lhj.library.util.common.FileUtil;
import com.lhj.library.util.common.TypeConversionUtils;

/**
 * Created by Administrator on 2016/12/28.
 */
public class DecryFile {
    public DecryFile(){

    }

    public byte[] startForAssets(Context context, String filename, String secretKey){
        String encryStr = TypeConversionUtils.byte2StringNotHex(FileUtil.getAssets(context,filename));
        //AES解密
        String decryStr = AesUtils.decrypt(secretKey, encryStr);
        byte[] bytes = TypeConversionUtils.String2byteNotHex(decryStr);
        return bytes;
    }

    public byte[] start(String encryFilepath,String secretKey){
        String encryStr = TypeConversionUtils.byte2StringNotHex(FileUtil.readBytes(encryFilepath));
        //AES解密
        String decryStr = AesUtils.decrypt(secretKey, encryStr);
        byte[] bytes = TypeConversionUtils.String2byteNotHex(decryStr);
        return bytes;
    }

    public void startLocal(String pathLocal,String encryFilepath,String secretKey){
        String encryStr = TypeConversionUtils.byte2StringNotHex(FileUtil.readBytes(encryFilepath));
        //AES解密
        String decryStr = AesUtils.decrypt(secretKey, encryStr);
        byte[] bytes = TypeConversionUtils.String2byteNotHex(decryStr);
        FileUtil.writeBytes(pathLocal,bytes);
    }

    public void startLocalForAssets(Context context, String filename,String secretKey,String pathLocal){
        String encryStr = TypeConversionUtils.byte2StringNotHex(FileUtil.getAssets(context,filename));
        //AES解密
        String decryStr = AesUtils.decrypt(secretKey, encryStr);
        byte[] bytes = TypeConversionUtils.String2byteNotHex(decryStr);
        FileUtil.writeBytes(pathLocal,bytes);
    }


}
