package com.gyzj.core.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 持久化工具
 * 
 * @author 程才
 * @date  2014-4-2
 */
public final class PersistentUtil
{
    /**
     * 文件名称
     */
    private final String FILENAME = "supercode";
    
    /**
     * 用户登录账号KEY
     */
    public static final String KEY_LOGIN_NAME = "username";
    
    /**
     * 用户登录密码KEY
     */
    public static final String KEY_LOGIN_PWD = "userpwd";
    
    /**
     * 用户查询记录KEY
     */
    public static final String KEY_SEARCH_RECORD = "searchrecode";
    
    /**
     * 首次启动
     */
    public static final String KEY_FIRST_LAUNCH = "firstlaunch";
    
    /**
     * 存储的版本号
     */
    public static final String KEY_VERSION = "version";
    
    /**
     * 搜索记录分隔符
     */
    public static final String SPLIT_SEARCH_RECORD = "/";
    
    /**
     * 用户登录记住密码KEY
     */
    public static final String KEY_LOGIN_REMEMBER_PWD = "isRememberPWD";
    
    /**
     * 是否有商城信息
     */
    public static final String KEY_HASNO_COMPLETE_INFO = "hascominfo";
    
    private static final PersistentUtil INSTANCE = new PersistentUtil();
    
    private PersistentUtil()
    {
        
    }
    
    public static PersistentUtil getInstance()
    {
        return INSTANCE;
    }
    
    public void write(Context ctx, String key, String value)
    {
        if (ctx == null)
        {
            return;
        }
        
        SharedPreferences sp = ctx.getSharedPreferences(FILENAME,
                Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putString(key, value);
        
        editor.commit();
    }
    
    public void write(Context ctx, String key, boolean value)
    {
        if (ctx == null)
        {
            return;
        }
        
        SharedPreferences sp = ctx.getSharedPreferences(FILENAME,
                Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
    
    public String readString(Context ctx, String key, String defValue)
    {
        if (ctx == null)
        {
            return "";
        }
        
        SharedPreferences sp = ctx.getSharedPreferences(FILENAME,
                Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }
    
    public boolean readBoolean(Context ctx, String key, boolean defValue)
    {
        if (ctx == null)
        {
            return false;
        }
        
        SharedPreferences sp = ctx.getSharedPreferences(FILENAME,
                Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }
    
    public String md5(String string)
    {
        byte[] hash;
        
        try
        {
            hash = MessageDigest.getInstance("MD5")
                    .digest(string.getBytes("UTF-8"));
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException("MD5 not be supported!", e);
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException("UTF-8 not be supported!", e);
        }
        
        StringBuilder hex = new StringBuilder(hash.length * 2);
        
        for (byte b : hash)
        {
            int i = (b & 0xFF);
            if (i < 0x10)
                hex.append('0');
            hex.append(Integer.toHexString(i));
        }
        
        return hex.toString().toUpperCase(Locale.getDefault());
    }
    
}
