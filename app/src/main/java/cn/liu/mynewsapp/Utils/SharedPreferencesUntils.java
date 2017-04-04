package cn.liu.mynewsapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *
 * SharedPreferencesUntil工具类
 * Created by liu on 2017-02-15.
 */

public class SharedPreferencesUntils {

    public static void setInt(Context context,String key,int value){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putInt(key,value).commit();
    }


    public static void setBoolen(Context context,String key,boolean value){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }


    public static void setString(Context context,String key,String value){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }
    public static int getInt(Context context,String key,int defvalue){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getInt(key,defvalue);
    }


    public static boolean getboolean(Context context,String key,boolean defvalue){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getBoolean(key,defvalue);
    }


    public static String getString(Context context,String key,String defvalue){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getString(key,defvalue);
    }


}
