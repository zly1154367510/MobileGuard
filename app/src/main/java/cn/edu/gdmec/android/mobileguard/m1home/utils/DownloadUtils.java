package cn.edu.gdmec.android.mobileguard.m1home.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;

/**
 * Created by zly11 on 2017/9/12.
 */

public class DownloadUtils {
    /*
    下载更新方法
    url 下载路径
    targeFile 存储文件夹
     */
    public void downloadApk(String url,String targeFile,Context context){
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url));
        request.setAllowedOverRoaming(false);
        MimeTypeMap mimeTypeMap =MimeTypeMap.getSingleton();
        String mimeString=mimeTypeMap.getMimeTypeFromExtension(mimeTypeMap.getMimeTypeFromExtension(url));
        request.setMimeType(mimeString);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir("/download", targeFile);
        DownloadManager downloadManager=(DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        long mTaskid=downloadManager.enqueue(request);
    }
}
