package com.gyzj.core.util.cache;

import java.io.File;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gyzj.core.util.PersistentUtil;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;


/**
 * 文件缓存类
 * 
 * @author 程才
 * @date  2014年5月9日
 */
public class FileCache
{
    
    /**
     * 路径缓存
     */
    private static final HashMap<String, String> PATH_CACHE = new HashMap<String, String>();
    
    private static final FileCache INSTANCE = new FileCache();
    
    /**
     * 保存所有缓存文件路径的KEY
     */
    private final String KEY_HTTP_FILE_PATH = "httpfilepath";
    
  
    
    /**
     * 缓存文件JSON的KEY
     */
    private final String KEY = "key";
    
    /**
     * 缓存文件对应的本地的路径的KEY
     */
    private final String KEY_FILE_PATH = "path";
    
    private FileCache()
    {
        
    }
    
    public static FileCache getInstance()
    {
        return INSTANCE;
    }
    
    /**
     * 缓存文件目录
     */
    public String getImgFileDir()
    {
        return Environment.getExternalStorageDirectory() + "/supercode/img/";
    }
    
    /**
     * 缓存文件路径
     */
    public synchronized String getImgFileName()
    {
        return getImgFileDir()
                + System.currentTimeMillis() + ".jpg";
    }
    
    /**
     * 初始化
     * 从配置文件中读取缓存数据
     */
    public synchronized void init(final Context ctx)
    {
        String filepath = PersistentUtil.getInstance().readString(ctx,
                KEY_HTTP_FILE_PATH,
                "");
        if (TextUtils.isEmpty(filepath))
        {
            Log.i("FileCache", "file path is empty");
            return;
        }
        
        try
        {
            JSONArray filesArray = new JSONArray(filepath);
            for (int i = 0; i < filesArray.length(); i++)
            {
                JSONObject file = filesArray.optJSONObject(i);
                if (file == null)
                {
                    continue;
                }
                
                String url = file.optString(KEY);
                String path = file.optString(KEY_FILE_PATH);
                
                if (TextUtils.isEmpty(url) || TextUtils.isEmpty(path))
                {
                    continue;
                }
                
                File f = new File(path);
                if (f.exists() == false || f.isDirectory())
                {
                    continue;
                }
                
                PATH_CACHE.put(url, path);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        
        //        String[] fileobjArray = filepath.split(",");
        //        
        //        if (fileobjArray.length == 0)
        //        {
        //            return;
        //        }
        //        
        //        for (int i = 0; i < fileobjArray.length; i++)
        //        {
        //            String fileobj = fileobjArray[i];
        //            if (TextUtils.isEmpty(fileobj))
        //            {
        //                continue;
        //            }
        //            
        //            String[] fileinfo = fileobj.split("\\|");
        //            if (fileinfo.length < 2)
        //            {
        //                continue;
        //            }
        //            
        //            String url = fileinfo[0];
        //            String path = fileinfo[1];
        //            if (TextUtils.isEmpty(url) || TextUtils.isEmpty(path))
        //            {
        //                continue;
        //            }
        //            
        //            File f = new File(path);
        //            if (f.exists() == false || f.isDirectory())
        //            {
        //                continue;
        //            }
        //            
        //            PATH_CACHE.put(url, path);
        //        }
        
    }
    
    public synchronized void cacheFilePath(Context ctx, String url, String path)
    {
        if (ctx == null || PATH_CACHE == null || TextUtils.isEmpty(url)
                || TextUtils.isEmpty(path))
        {
            return;
        }
        
        PATH_CACHE.put(url, path);
        
        JSONArray filesArray = new JSONArray();
        try
        {
            for (String keyUrl : PATH_CACHE.keySet())
            {
                if (TextUtils.isEmpty(keyUrl))
                {
                    continue;
                }
                
                String valPath = PATH_CACHE.get(keyUrl);
                if (TextUtils.isEmpty(valPath))
                {
                    continue;
                }
                
                JSONObject file = new JSONObject();
                
                file.put(KEY, keyUrl);
                file.put(KEY_FILE_PATH, valPath);
                
                filesArray.put(file);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        //        StringBuffer httpfile = new StringBuffer();
        //        for (String keyUrl : PATH_CACHE.keySet())
        //        {
        //            if (TextUtils.isEmpty(keyUrl))
        //            {
        //                continue;
        //            }
        //            
        //            String valPath = PATH_CACHE.get(keyUrl);
        //            if (TextUtils.isEmpty(valPath))
        //            {
        //                continue;
        //            }
        //            
        //            httpfile.append(keyUrl).append("\\|").append(valPath).append(",");
        //        }
        
        PersistentUtil.getInstance().write(ctx,
                KEY_HTTP_FILE_PATH,
                filesArray.toString());
    }
    
    public String getFilePath(String url)
    {
        if (PATH_CACHE == null || TextUtils.isEmpty(url))
        {
            return "";
        }
        
        return PATH_CACHE.get(url);
    }
    
}
