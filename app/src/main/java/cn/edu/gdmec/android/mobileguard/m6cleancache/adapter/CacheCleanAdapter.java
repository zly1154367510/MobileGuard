package cn.edu.gdmec.android.mobileguard.m6cleancache.adapter;


import android.content.Context;

import java.util.List;

import cn.edu.gdmec.android.mobileguard.m6cleancache.entity.CacheInfo;

public class CacheCleanAdapter {
    private Context context;
    private List<CacheInfo> cacheInfos;

    public CacheCleanAdapter(Context context,List<CacheInfo> cacheInfos){
        super();
        this.context=context;
        this.cacheInfos=cacheInfos;
    }


}
