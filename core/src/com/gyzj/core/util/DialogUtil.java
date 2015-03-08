package com.gyzj.core.util;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.view.View.OnClickListener;

import com.example.core.R;
import com.gyzj.core.widget.Common2BtnDialog;
import com.gyzj.core.widget.LoadingProgressDialog;

/**
 * 提示框工具类
 * 
 * @author  程才
 * @date  2014-4-3
 */
public final class DialogUtil
{
    private DialogUtil()
    {
        
    }
    
    /**
     * 创建等待狂
     * @param ctx 上下文
     * @param iconId 图片id
     */
    public static Dialog getWaittingDialog(Context ctx, int iconId)
    {
        return getNoBtnDialog(ctx,
                ctx.getString(R.string.dialog_waitting_title),
                ctx.getString(R.string.dialog_waitting_msg),
                iconId);
    }
    
    /**
     * 没按钮的框
     * @param ctx 上下文
     * @param title 标题
     * @param msg   消息内容
     * @param iconId 图片
     * @return 窗体对象
     */
    public static Dialog getNoBtnDialog(Context ctx, String title, String msg,
            int iconId)
    {
        Builder builder = new Builder(ctx);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setIcon(R.drawable.ic_launcher);
        return builder.create();
    }
    
    /**
     * 创建等待框
     * @param ctx
     * @param title
     * @param message
     */
    public static LoadingProgressDialog getLoadDialog(Context ctx,
            String message)
    {
        return new LoadingProgressDialog(ctx, message);
    }
    
    public static Common2BtnDialog getCommon2BtnDialog(Context ctx,
            String title, String msg, String leftContent, String rightContent,
            OnClickListener onLeftClick, OnClickListener onRightClick)
    {
        if (ctx != null)
        {
            Common2BtnDialog dialog = new Common2BtnDialog(ctx, title, msg,
                    leftContent, rightContent, onLeftClick, onRightClick);
            //            dialog.setInfo(title, msg, onLeftClick, onRightClick);
            return dialog;
        }
        else
        {
            return null;
        }
    }
}
