package com.gyzj.core.util.activity;

import com.example.core.R;

import android.app.Activity;
import android.content.Intent;


/**
 * 基础activity工具类
 * 
 * @author 程才
 * @date  2014年10月10日
 */
public class BaseActivityUtil
{
    /**
     * 无参数启动activity
     * @param source 当前activity
     * @param target 要跳转的activity
     * @param isNeedFinish 是否需要释放当前activity
     */
    @SuppressWarnings("rawtypes")
    public static void startActivity(Activity source,Class target,boolean isNeedFinish)
    {
        startActivity(source, target, isNeedFinish, false);
    }
    
    /**
     * 无参数返回到activity
     * @param source 当前activity
     * @param target 要跳转的activity
     * @param isNeedFinish 是否需要释放当前activity
     */
    @SuppressWarnings("rawtypes")
    public static void back2Activity(Activity source,Class target,boolean isNeedFinish)
    {
        startActivity(source, target, isNeedFinish, true);
    }
    
    @SuppressWarnings("rawtypes")
    private static void startActivity(Activity source,Class target,boolean isNeedFinish,boolean isBack)
    {
        if (source == null || target == null)
        {
            return;
        }
        
        if (isNeedFinish)
        {
            source.finish();
        }
        
        Intent it = new Intent(source,target);
        source.startActivity(it);
        
        if (!isBack)
        {
            source.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }
        else
        {
            source.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        }
    }
    
    /**
     * 带参数的启动acitivity
     * @param activity 当前acitivity
     * @param intent 跳转和参数的intent
     * @param isNeedFinish 是否需要释放当前activity
     * @param isBack 是否是back 关系到动画播放
     */
    public static void startActivity(Activity activity,Intent intent,boolean isNeedFinish,boolean isBack)
    {
        if (intent == null || activity == null)
        {
            return;
        }
        
        if (isNeedFinish)
        {
            activity.finish();
        }
        
        activity.startActivity(intent);
        
        if (!isBack)
        {
            activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }
        else
        {
            activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        }
    }
    
    /**
     * 简单返回
     * 用在仅仅结束当前activity
     * @param activity 当前activity
     */
    public static void simpleBack(Activity activity)
    {
        if (activity == null)
        {
            return;
        }
        
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
    
    /**
     * 设置启动跳转动画
     */
    public static void setStartTransition(Activity activity)
    {
        if (activity == null)
        {
            return;
        }
        
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
    
    /**
     * 设置返回跳转动画
     */
    public static void setBackTransition(Activity activity)
    {
        if (activity == null)
        {
            return;
        }
        
        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
    
    /**
     * 上弹动画
     */
    public static void setUpTransition(Activity activity)
    {
        if (activity == null)
        {
            return;
        }
        
        activity.overridePendingTransition(R.anim.push_up_in, 0);
    }
    
    /**
     * 下弹动画
     */
    public static void setDownTransition(Activity activity)
    {
        if (activity == null)
        {
            return;
        }
        
        activity.overridePendingTransition(R.anim.push_down_out, R.anim.push_down_out);
    }
    
}
