package com.gyzj.core.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;

/**
 * ���������
 * 
 * @author  ������/Michael Chen
 * @date  2014��5��23��
 */
public class CameraUtil
{
    public static boolean checkCheckCamera(Context context)
    {
        //�������ͷ
        int nums = Camera.getNumberOfCameras();
        if(context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA) == false || nums < 1)
        {
            return false;
        }
        
        return true;
    }
}
