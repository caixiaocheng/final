package com.gyzj.core.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;

/**
 * 
 * 图片工具类
 * 
 * @author 程才
 * @date 2014年10月10日
 */
public class BitmapUtil {
	public static Bitmap scale(final Bitmap bitmap, final float targetWidth,
			final float targetHeight) {
		int height = bitmap.getHeight();
		int width = bitmap.getWidth();
		float scaleWidth = targetWidth / width;
		float scaleHeight = targetHeight / height;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		return resizedBitmap;
	}

	public static Options getSacleOptions(InputStream is, int targetWidth) {
		Options options = new Options();
		if (targetWidth > 0) {
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(is, null, options);
			options.inSampleSize = 1;
			if (options.outWidth > targetWidth) {
				options.inSampleSize = (int) (Float.valueOf(options.outWidth) / Float
						.valueOf(targetWidth));
			}
			options.inInputShareable = true;
			options.inPurgeable = true;
			options.inJustDecodeBounds = false;
		}

		return options;
	}

	public static byte[] bmpToByteArray(final Bitmap bmp, int quality,
			final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.JPEG, quality, output);
		if (needRecycle) {
			bmp.recycle();
		}

		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {

		// Raw height and width of image
		final int height =400 ;
		final int width = 400;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}

	public  static Options getOption(String url, int reqWidth, int reqHeight) {
		
			
			
		Options options = null;
		try {
			
			options = new Options();

			options.inJustDecodeBounds = true;
		
			BitmapFactory.decodeFile(url, options);

			options.inSampleSize = calculateInSampleSize(options, reqWidth,
					reqHeight);

			options.inInputShareable = true;
			options.inPurgeable = true;
			options.inJustDecodeBounds = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		return options;

	}
}
