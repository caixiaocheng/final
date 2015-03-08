package com.gyzj.core.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;

/**
 * ϵͳ����
 * 
 * @author  �̲�
 * @date  2014-10-10
 * @see  [�����/����]
 */
public final class SysProperty
{
    private static final SysProperty INSTANCE = new SysProperty();
    
    private SysProperty()
    {
        
    }
    
    public static SysProperty getInstance()
    {
        return INSTANCE;
    }
    
    /**
     * app�汾��
     */
    private String appVersion;
    
    /**
     * ��ǰ�û��ĵ�¼����
     */
    private String token;
    
    /**
     * ��ǰ�û�����ҵid
     */
    private String corpid;
    
//    public boolean isCheckedAutoUpdate = false;
    
    public String getAppVersion(Context ctx)
    {
        //��ȡAPP�汾��Ϣ
        PackageManager packageManager = ctx.getPackageManager();
        PackageInfo packInfo;
        try
        {
            packInfo = packageManager.getPackageInfo(ctx.getPackageName(), 0);
            appVersion = packInfo.versionName;
        }
        catch (NameNotFoundException e)
        {
            e.printStackTrace();
            appVersion = "1.0";
        }
        
        return appVersion;
    }
    
   
    
   
    
    
    
    public String getDeviceType()
    {
        return "Android";
    }
    
    public void setIsAuthored(Context ctx, boolean isAuthored)
    {
        if (ctx != null)
        {
            PersistentUtil.getInstance().write(ctx, "isAuthored", isAuthored);
        }
    }
    
    public boolean getIsAuthored(Context ctx)
    {
        if (ctx != null)
        {
            return PersistentUtil.getInstance().readBoolean(ctx, "isAuthored",false);
        }
        else
        {
            return false;
        }
        
    }
}
