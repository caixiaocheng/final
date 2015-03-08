package com.gyzj.core.enable.http;

import java.util.concurrent.TimeoutException;

import com.gyzj.core.util.ToastUtil;

import android.content.Context;


/**
 * 相应处理处理
 * 
 * @author  程才
 * @date  2014年10月15日
 */
public class HttpHandle
{
    public void handleFaile(Context context,Throwable throwable)
    {
        if (context == null || throwable == null)
        {
            return;
        }
        
        if (throwable instanceof TimeoutException)
        {
            ToastUtil.toastshort(context, "连接超时，请到网络环境良好的环境下重试");
        }
        else
        {
            ToastUtil.toastshort(context, "请求失败，请检查是否连接网络");
        }
    }
}
