package com.gyzj.core;

import org.apache.http.Header;
import org.json.JSONObject;

import com.example.core.R;
import com.gyzj.core.ui.activity.LoginActivity;
import com.gyzj.core.ui.activity.MainGuidActivity;
import com.gyzj.core.util.DialogUtil;
import com.gyzj.core.util.activity.BaseActivityUtil;
import com.gyzj.core.util.cache.ACache;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * 初始界面
 * 
 * @author 程才、
 * @date 2014-10-24
 */
public class MainActivity extends Activity {

   private ImageView iv_splash;
   private ACache cache;



	@SuppressLint("HandlerLeak")
	private Handler handle = new Handler() {
		public void handleMessage(android.os.Message msg) {

			BaseActivityUtil.startActivity(MainActivity.this,
					MainGuidActivity.class, true);

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		DialogUtil.getNoBtnDialog(MainActivity.this, "工友之家内部测试", "微笑要带眼泪才耐看,且看且找错", R.drawable.alter).show();
		
        iv_splash=(ImageView) findViewById(R.id.bg_splash);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.alpha_splash);
        iv_splash.startAnimation(animation);
		handle.sendEmptyMessageDelayed(1, 7000);
		cache = ACache.get(this);
		cache.clear();       //每次app打开时，清除缓存
	}

}
