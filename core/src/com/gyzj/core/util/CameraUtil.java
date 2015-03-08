package com.gyzj.core.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;

/**
 * 照相机工具
 * 
 * @author  陈晓磊/Michael Chen
 * @date  2014年5月23日
 */
public class CameraUtil
{
    public static boolean checkCheckCamera(Context context)
    {
        //检查摄像头
        int nums = Camera.getNumberOfCameras();
        if(context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA) == false || nums < 1)
        {
            return false;
        }
        
        return true;
    }
}
