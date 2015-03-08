package com.gyzj.core.util.activity;

import com.example.core.R;

import android.app.Activity;
import android.content.Intent;


/**
 * ����activity������
 * 
 * @author �̲�
 * @date  2014��10��10��
 */
public class BaseActivityUtil
{
    /**
     * �޲�������activity
     * @param source ��ǰactivity
     * @param target Ҫ��ת��activity
     * @param isNeedFinish �Ƿ���Ҫ�ͷŵ�ǰactivity
     */
    @SuppressWarnings("rawtypes")
    public static void startActivity(Activity source,Class target,boolean isNeedFinish)
    {
        startActivity(source, target, isNeedFinish, false);
    }
    
    /**
     * �޲������ص�activity
     * @param source ��ǰactivity
     * @param target Ҫ��ת��activity
     * @param isNeedFinish �Ƿ���Ҫ�ͷŵ�ǰactivity
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
     * ������������acitivity
     * @param activity ��ǰacitivity
     * @param intent ��ת�Ͳ�����intent
     * @param isNeedFinish �Ƿ���Ҫ�ͷŵ�ǰactivity
     * @param isBack �Ƿ���back ��ϵ����������
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
     * �򵥷���
     * ���ڽ���������ǰactivity
     * @param activity ��ǰactivity
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
     * ����������ת����
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
     * ���÷�����ת����
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
     * �ϵ�����
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
     * �µ�����
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
