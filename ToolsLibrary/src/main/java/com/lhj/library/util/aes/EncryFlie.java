package com.lhj.library.util.aes;

import android.content.Context;
import android.net.Uri;

import com.lhj.library.util.common.ExecutorsSingLeThread;
import com.lhj.library.util.common.FileUtil;
import com.lhj.library.util.common.TypeConversionUtils;

/**
 * Created by Administrator on 2016/12/28.
 */
public class EncryFlie {
    private Context context;
    private String filepath,assetsFileName;
    private int rawId;
    private Uri uri;

    public EncryFlie(Context context){
        this.context = context;
    }

    public void setFile(String filepath){
        init(filepath,null,0,null);
    }

    public void setFile(Uri uri){
        init(null,uri,0,null);
    }

    public void setFile(int rawId){
        init(null,null,rawId,null);
    }

    public void setAssetsFile(String assetsFileName){
        init(null,null,0,assetsFileName);
    }

    private void init(String path,Uri uri,int rawId,String assetsFileName){
        this.filepath = path;
        this.uri = uri;
        this.rawId = rawId;
        this.assetsFileName = assetsFileName;
    }

    public void start(final String pathEncryFile,final EncryResult encryResult){
        Runnable run = new Runnable() {
            @Override
            public void run() {
                byte[] bytes = null;
                if(filepath!=null){
                    bytes = FileUtil.readBytes(filepath);
                }else if(uri!=null){
                    bytes = FileUtil.readBytes(uri);
                }else if(rawId>0){
                    bytes = FileUtil.getRaws(context,rawId);
                }else if(assetsFileName!=null){
                    bytes = FileUtil.getAssets(context,assetsFileName);
                }
                if(dealNull(bytes)){
                    String bytesrt = TypeConversionUtils.byte2StringNotHex(bytes);
                    //生成一个动态的key
                    String secretKey = AesUtils.generateKey();
                    //AES加密
                    String encryStr = AesUtils.encrypt(secretKey, bytesrt);
                    //生成一个加密的文件
                    boolean isWrite = FileUtil.writeBytes(pathEncryFile, TypeConversionUtils.String2byteNotHex(encryStr));
                    encryResult.onResult(isWrite,secretKey);
                }else{
                    encryResult.onResult(false,null);
                }
            }
        };
        ExecutorsSingLeThread.getInstance().enqueue(run);
    }

    private boolean dealNull(Object o){
        if(o==null){
            return false;
        }
        return true;
    }

    public interface EncryResult{
        void onResult(boolean isEncry, String secretKey);
    }

}
