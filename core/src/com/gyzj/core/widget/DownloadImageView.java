package com.gyzj.core.widget;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;

import org.apache.http.Header;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.core.R;
import com.gyzj.core.util.cache.FileCache;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

/**
 * ���Լ�����ͼƬ��imageview
 * 
 * @author ������/Michael Chen
 * @date 2014��5��9��
 */
public class DownloadImageView extends ImageView {

	private Context ctx;

	private String url;

	/**
	 * ������
	 */
	private RequestHandle requestHandle;
	
	
	

	public void setURL(String url) {
		this.url = url;
	}

	public String getURL() {
		return url;
	}
	
	
	
	

	public DownloadImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		ctx = context;
	}

	public DownloadImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		ctx = context;
	}

	public DownloadImageView(Context context) {
		super(context);
		ctx = context;
	}


	
	
	
	
	
	

	/**
	 * ����ͼƬ
	 * 
	 * @param url
	 *            Ҫ���ص�·��
	 * @param option
	 *            ��������ȱ��ã���û�����ȶ�
	 * @param loadWatcher
	 *            ���ؼ����
	 */
	
	/**
	 * ֱ�ӽ�������
	 */
	public void finishLoad() {
		if (requestHandle != null && requestHandle.isFinished() == false
				&& requestHandle.isCancelled() == false) {
			requestHandle.cancel(true);
		}
	}

	/**
	 * ѡ����
	 */
	public static class Options {
		public int targetWidth;

		public int targetHeigth;
	}

	/**
	 * ���ع۲��߻ص�
	 * 
	 * @author ������/Michael Chen
	 * @date 2014��5��16��
	 */
	public interface LoadWatcher {
		/**
		 * �������
		 */
		public void onLoadFinish();
	}

}
