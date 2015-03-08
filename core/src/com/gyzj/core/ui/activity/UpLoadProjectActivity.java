package com.gyzj.core.ui.activity;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.core.R;
import com.gyzj.core.widget.*;
import com.gyzj.core.enable.http.HttpHandle;
import com.gyzj.core.util.Constant;
import com.gyzj.core.util.DialogUtil;
import com.gyzj.core.util.DisplayUtil;
import com.gyzj.core.util.Keys;
import com.gyzj.core.util.PersistentUtil;
import com.gyzj.core.util.ToastUtil;
import com.gyzj.core.util.activity.BaseActivityUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.qiniu.android.http.HttpManager;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;

public class UpLoadProjectActivity extends Activity {
	private TextView tv_name, tv_time;
	String pro_name, pro_time, detail, pro_id;
	private EditText et_detial;
	private Button btn_confirm;
	private AsyncHttpClient ahc;
	private LinearLayout hlvThumb;
	private final int INDEX_THUMB = 0;
	int windowWidth;
	private ArrayList<String> thumbList = new ArrayList<String>();

	final CountDownLatch signal = new CountDownLatch(1);
	private HttpManager httpManager;
	private ResponseInfo info;
	private JSONObject resp;
	private String token;
	private StringBuffer sb = new StringBuffer();
	private String cover;
	private    int  j = 0;
	private int count;
	boolean isFirst=true;
    private Dialog dia;
	protected void setUp() throws Exception {
		httpManager = new HttpManager();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload_project_activity);
		hlvThumb = (LinearLayout) findViewById(R.id.hlv_goodsmanager_simple_thumb);
		addThumb(0);
		btn_confirm = (Button) findViewById(R.id.btn_confirm);
		tv_name = (TextView) findViewById(R.id.up_pro_name);
		tv_time = (TextView) findViewById(R.id.up_pro_time);
		pro_name = getIntent().getStringExtra("pro_name");
		pro_time = getIntent().getStringExtra("pro_time");
		pro_id = getIntent().getStringExtra("pro_id");
		dia = DialogUtil.getLoadDialog(UpLoadProjectActivity.this, "饥渴上传中。。。。");
		ahc = new AsyncHttpClient();
		String timeStr = pro_time;

		if (timeStr != null) {

			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date date;
			try {
				date = format.parse(timeStr);
				int nian = date.getYear();
				int yue_temp = date.getMonth() + 1;
				int yue = yue_temp > 12 ? 1 : yue_temp;
				int ri = date.getDay();
				int shi = date.getHours();
				int fen = date.getMinutes();
				tv_time.setText(nian + 1900 + "年" + yue + "月" + ri + "日");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		et_detial = (EditText) findViewById(R.id.pro_detial);
		detail = et_detial.getText().toString().trim();
		if (pro_name != null && pro_time != null) {
			tv_name.setText(pro_name);
		}
		btn_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*
				 * if (TextUtils.isEmpty(detail)) {
				 * ToastUtil.toastshort(UpLoadProjectActivity.this, "请输入项目信息");
				 * return; } if (thumbList.size() < 1) {
				 * ToastUtil.toastshort(UpLoadProjectActivity.this,
				 * "请至少输入一张图片"); return; }
				 */
				Upload();

			}
		});

	}

	private void Upload() {

		RequestParams rp = new RequestParams();

		rp.put(Keys.KEY_METHOD, "getUpToken");

		ahc.post(this, Constant.HTTP_REQ_URL_PREFIX, rp,
				new JsonHttpResponseHandler(Constant.UNICODE) {
					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
						dia.show();
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) {
						super.onSuccess(statusCode, headers, response);
						
						try {
							token = response.getString("list");

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						for (int i = 0; i < thumbList.size(); i++) {
							
								
							
							UploadManager uploadManager = new UploadManager();
							String url = thumbList.get(i);
							File file = new File(url);
							boolean boo = file.exists();
							uploadManager.put(file, null, token,
									new UpCompletionHandler() {
										@Override
										public void complete(String key,
												ResponseInfo info,
												JSONObject response) {
											// Log.i("qiniu", info);
											
											String url;
											try {
												url = response.getString("hash");
												if (isFirst) {
													cover = url;
													isFirst=false;
												}
												
													sb.append(url + ",");
												
												count++;

											} catch (JSONException e) {
												// TODO Auto-generated catch
												// block
												e.printStackTrace();
											}
											if (count == thumbList.size()) {
												RequestParams rp = new RequestParams();
												rp.put(Keys.KEY_METHOD, "modifyProject");
												rp.put("mark", detail);
												rp.put("projectImg", sb.substring(0,sb.length()-1));
												rp.put("coverImg", cover);
												rp.put("status", "A");
												rp.put("projectId", pro_id);
												
												
												ahc.post(
														UpLoadProjectActivity.this,
														Constant.HTTP_REQ_URL_PREFIX,
														rp,
														new JsonHttpResponseHandler(
																Constant.UNICODE) {
															@Override
															public void onStart() {
																// TODO
																// Auto-generated
																// method stub
																super.onStart();
																if(dia.isShowing()){
																	dia.dismiss();
																}
																ToastUtil.toastshort(UpLoadProjectActivity.this,"伤心的时候不要听慢歌");

																
															}
															public void onSuccess(int statusCode, Header[] headers, JSONArray response){
																ToastUtil.toastshort(UpLoadProjectActivity.this,"最后的幸福");
															};
															public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
																ToastUtil.toastshort(
																		UpLoadProjectActivity.this,
																		"最后的遗憾");
															};
														});
											}

										}
									}, null);
						}

					}

					@Override
					public void onFailure(int arg0, Header[] arg1, String arg2,
							Throwable throwable) {
						super.onFailure(arg0, arg1, arg2, throwable);
					}

				});


	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	private ImageView addThumb(int id) {
		try {
			final DownloadImageView view = (DownloadImageView) LayoutInflater
					.from(this).inflate(R.layout.widget_goodsaddthumb, null);
			view.setId(id);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					DisplayUtil.dip2px(this, 150),
					DisplayUtil.dip2px(this, 150));
			int margin = DisplayUtil.dip2px(this, 10);
			lp.setMargins(margin, margin, 0, margin);
			hlvThumb.addView(view, lp);

			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(final View v) {
					if (TextUtils.isEmpty(view.getURL())) {

						selectPic(INDEX_THUMB, true);
					}

					else {
						Builder b = new AlertDialog.Builder(
								UpLoadProjectActivity.this);
						b.setTitle("删除");
						b.setMessage("您确定要删除吗？");
						b.setNegativeButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										ImageView imageView = (ImageView) v;
										thumbList.remove(imageView.getId());
										hlvThumb.removeView(imageView);

										addThumb(0);

									}
								});
						b.setNeutralButton("取消", null);
						b.create().show();
					}
				}

			});

			return view;

		} catch (Exception e) {

			return null;
		}
	}

	private void selectPic(int requestCode, boolean isThumb) {

		Intent it = new Intent(this, SettingAuthorSelectPicActivty.class);
		it.putExtra("type", requestCode);
		it.putExtra("isThumb", isThumb);

		// getActivity().startActivityForResult(it, requestCode);
		startActivityForResult(it, requestCode);
		// overridePendingTransition(R.anim.push_up_in, 0);

	}

	private Bitmap compressPicFile(final String filename) {
		try {
			File f = new File(filename);
			if (f.exists() == false) {
				ToastUtil.toastshort(this, "文件不存在");
				return null;
			}

			Options option = new Options();
			option.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(filename, option);
			option.inSampleSize = new BigDecimal(option.outWidth).divide(
					new BigDecimal(windowWidth), BigDecimal.ROUND_HALF_EVEN)
					.intValue();
			option.inJustDecodeBounds = false;
			Bitmap bit = BitmapFactory.decodeFile(filename, option);

			return bit;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

		windowWidth = wm.getDefaultDisplay().getWidth();

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			switch (requestCode) {

			case INDEX_THUMB: {
				if (data == null) {
					return;
				}
				int width = 0;
				int height = 0;

				ArrayList files = data.getStringArrayListExtra("files");
				for (int i = 0; i < files.size(); i++) {
					String filename = (String) files.get(i);
					Bitmap bit = compressPicFile(filename);
					if (i == 0) {
						width = bit.getWidth();
						height = bit.getHeight();

					}
					if (bit != null && hlvThumb.getChildCount() > 0) {
						DownloadImageView div = (DownloadImageView) hlvThumb
								.getChildAt(hlvThumb.getChildCount() - 1);
						div.setURL(filename);
						Bitmap thumb = ThumbnailUtils.extractThumbnail(bit,
								width, height,
								ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
						div.setBackgroundDrawable(new BitmapDrawable(thumb));
						div.setImageBitmap(null);
						if (hlvThumb.getChildCount() < 6) {
							addThumb(0);
						}
						thumbList.add(filename);
					}

					// 如果图片没有达到上限
				}

				break;
			}

			default:
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
