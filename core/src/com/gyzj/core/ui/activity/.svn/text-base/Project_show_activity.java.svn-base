package com.gyzj.core.ui.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.core.R;
import com.gyzj.core.util.Constant;
import com.gyzj.core.util.ToastUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Project_show_activity extends Activity {
	private ViewPager vp;
	private View view;
	private List<View> list;
	private ImageView iv_flash, iv1,iv;
	LinearLayout line;
	private View view1;
	private String time, name;
	private List img_list;
	DisplayImageOptions options; // DisplayImageOptions是用于设置图片显示的类
	private TextView tv_time, tv_name;
	private Handler handle = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			AlphaAnimation ani = new AlphaAnimation(1.0f, 0.0F);
			ani.setDuration(1000);
			ani.start();
			iv_flash.setAnimation(ani);
			iv_flash.setVisibility(View.GONE);

		}																										
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.project_show_activity);
		iv_flash = (ImageView) findViewById(R.id.img_flash);
        line = (LinearLayout) findViewById(R.id.text);
		options = new DisplayImageOptions.Builder()
		// .showStubImage(R.drawable.ic_stub) // 设置图片下载期间显示的图片
		// .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
		// .showImageOnFail(R.drawable.ic_error) //
		// 设置图片加载或解码过程中发生错误显示的图片
		// .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).build(); // 创建配置过得DisplayImageOption对象

		iv1 = (ImageView) findViewById(R.id.project_back);
		iv1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_right_out);
			}
		});
		tv_name = (TextView) findViewById(R.id.show_name);
		tv_time = (TextView) findViewById(R.id.show_time);
		
		
		vp = (ViewPager) findViewById(R.id.viewpager);
		list = new ArrayList<View>();
		
		name = getIntent().getStringExtra("name");
		tv_name.setText(name);
		time = getIntent().getStringExtra("time");
		  if(time!=null){
			  
			  
			  String timeStr = time;
			  SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			  Date date;
			  try {
				  date = format.parse(timeStr);
				  int nian = date.getYear();
				  int yue_temp = date.getMonth()+1;
				  int yue = yue_temp>12?1:yue_temp;
				  tv_time.setText(nian+1900+"年"+yue+"月");
			  } catch (ParseException e) {
				  // TODO Auto-generated catch block
				  e.printStackTrace();
			  }
		  }
		
		
		
		
		img_list = getIntent().getStringArrayListExtra("img");

		view1 = getLayoutInflater().inflate(R.layout.activity_main, null);
		handle.sendEmptyMessageDelayed(1, 2000);
		if (img_list != null) {
			for (int i = 0; i < img_list.size(); i++) {
				view = getLayoutInflater().inflate(R.layout.project_show_item,
						null);
				iv = (ImageView) view.findViewById(R.id.img_item);
				ImageLoader.getInstance().displayImage(
						Constant.HTTP_REQ_PICTURE_PREFIX + img_list.get(i), iv,
						options, null);

				iv.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						if (line.getVisibility() == View.VISIBLE) {
							// AlphaAnimation animation = new
							// AlphaAnimation(1.0F, 0F);
							TranslateAnimation animation = new TranslateAnimation(
									0, 0, 1, 100);
							animation.setDuration(100);
							animation.start();
							line.setAnimation(animation);
							line.setVisibility(View.GONE);

						} else {
							/*
							 * TranslateAnimation tra= new TranslateAnimation(0,
							 * 0, 0.0F, 1.0F); tra.setDuration(5000);
							 * //tra.start(); tv.setAnimation(tra);
							 * tv.startAnimation(tra);
							 */
							TranslateAnimation animation = new TranslateAnimation(
									0, 0, 100, 1);
							animation.setDuration(100);
							animation.start();
							line.setAnimation(animation);

							line.setVisibility(View.VISIBLE);
						}

						// if (tv.VISIBLE==View.INVISIBLE) {
						// tv.setVisibility(View.VISIBLE);

						// }

					}
				});

				list.add(view);
			}
		}
		MyPagerAdapter adapter = new MyPagerAdapter(list);
		vp.setAdapter(adapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * ViewPager适配器
	 */
	public class MyPagerAdapter extends PagerAdapter {
		public List<View> mListViews;

		public MyPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(mListViews.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(mListViews.get(arg1), 0);
			return mListViews.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
	}

}
