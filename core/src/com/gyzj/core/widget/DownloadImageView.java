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
 * 会自己下载图片的imageview
 * 
 * @author 陈晓磊/Michael Chen
 * @date 2014年5月9日
 */
public class DownloadImageView extends ImageView {

	private Context ctx;

	private String url;

	/**
	 * 请求句柄
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
	 * 加载图片
	 * 
	 * @param url
	 *            要加载的路径
	 * @param option
	 *            这个参数先别用，还没测试稳定
	 * @param loadWatcher
	 *            加载监控类
	 */
	
	/**
	 * 直接结束加载
	 */
	public void finishLoad() {
		if (requestHandle != null && requestHandle.isFinished() == false
				&& requestHandle.isCancelled() == false) {
			requestHandle.cancel(true);
		}
	}

	/**
	 * 选项类
	 */
	public static class Options {
		public int targetWidth;

		public int targetHeigth;
	}

	/**
	 * 加载观察者回调
	 * 
	 * @author 陈晓磊/Michael Chen
	 * @date 2014年5月16日
	 */
	public interface LoadWatcher {
		/**
		 * 加载完成
		 */
		public void onLoadFinish();
	}

}
