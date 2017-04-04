package cn.liu.mynewsapp.Utils;

import android.content.Context;

/**
 * Created by liu on 2017-02-19.
 */

//以url为key，json为values


    //写缓存
public class CacheUtils {
    public  static void setCache(Context context, String url, String json){
        SharedPreferencesUntils.setString(context,url,json);

    }
    //读缓存
    public  static String getCache(Context context,String url){
            return SharedPreferencesUntils.getString(context,url,null);
    }


}
