package com.gyzj.core.ui.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.Header;

import com.example.core.R;
import com.google.gson.JsonArray;
import com.gyzj.core.bean.categoryName;
import com.gyzj.core.enable.http.HttpHandle;
import com.gyzj.core.util.Constant;
import com.gyzj.core.util.Keys;
import com.gyzj.core.util.ToastUtil;
import com.gyzj.core.util.cache.ACache;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ServerClassifyDialogActivity extends Activity {
	private AsyncHttpClient ahc;
	private RequestHandle reqhandle;
	private List<HashMap<String, Object>> ServerItem;
	private List<categoryName> categoryList = new ArrayList<categoryName>();
	private ListView lv_sever_classifyt;
	private TextView tv_left, tv_right;
	private ImageView iv_center;
	private ACache cache;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.server_classify_dialog_activity);
		ahc = new AsyncHttpClient();
		tv_left = (TextView) findViewById(R.id.title_left);
		tv_left.setVisibility(View.GONE);
		tv_right = (TextView) findViewById(R.id.title_right);
		tv_right.setText("确认");
		tv_right.setTextColor(Color.RED);
		iv_center = (ImageView) findViewById(R.id.title_center);
		iv_center.setVisibility(View.GONE);
		lv_sever_classifyt = (ListView) findViewById(R.id.server_classfiy_list);
		cache = ACache.get(this);
		getData();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	public void initDatas() {

		ServerItem = new ArrayList<HashMap<String, Object>>();

		for (int i = 0; i < categoryList.size(); i++) {
			categoryName cate = categoryList.get(i);
			HashMap<String, Object> map = new HashMap<String, Object>();

			map.put("ItemText", cate.getCategoryName());
			map.put("count", "(" + cate.getCount() + "人)");
			ServerItem.add(map);
		}
		SimpleAdapter saImageItems = new SimpleAdapter(this, ServerItem,
				R.layout.server_classify_item, new String[] { "ItemText",
						"count" }, new int[] { R.id.server_classify_item_name,
						R.id.server_classify_item_number });

		lv_sever_classifyt.setAdapter(saImageItems);
		lv_sever_classifyt.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				ImageView iv_choice = (ImageView) view
						.findViewById(R.id.classify_choice);
				iv_choice.setVisibility(View.VISIBLE);
				Intent in = new Intent();
				in.putExtra("categoryId", categoryList.get(position)
						.getCategoryId());
				setResult(1, in);
				finish();
				overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
			}
		});
		lv_sever_classifyt
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});

	}

	private void getData() {
		JSONArray classify_cache = cache.getAsJSONArray("classify_cache");
		if (classify_cache != null) {

			try {

				categoryName category = null;
				for (int i = 0; i < classify_cache.length(); i++) {
					category = new categoryName();
					JSONObject obj = classify_cache.getJSONObject(i);
					category.setCategoryName(obj.getString("categoryName"));
					// category.setImg(obj.getString("img"));
					category.setSort(obj.getString("sort"));
					// category.setStatus(obj.getString("status"));
					category.setCategoryId(obj.getString("categoryId"));
					category.setCount(obj.getString("count"));
					categoryList.add(category);
				}

				initDatas();
				ToastUtil.toastshort(ServerClassifyDialogActivity.this, "从缓存中拿");

			} catch (JSONException e) {

				e.printStackTrace();
			}
			return;
		}
		RequestParams rp = new RequestParams();
		rp.put(Keys.KEY_METHOD, "allCategory");

		reqhandle = ahc.post(Constant.HTTP_REQ_URL_PREFIX, rp,
				new JsonHttpResponseHandler(Constant.UNICODE) {
					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();

					}

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) {
						super.onSuccess(statusCode, headers, response);

						if (reqhandle == null)
							return;
						try {
							JSONArray array = response.getJSONArray("list");
							cache.put("classify_cache", array);
							categoryName category = null;
							for (int i = 0; i < array.length(); i++) {
								category = new categoryName();
								JSONObject obj = array.getJSONObject(i);
								category.setCategoryName(obj
										.getString("categoryName"));
								// category.setImg(obj.getString("img"));
								category.setSort(obj.getString("sort"));
								// category.setStatus(obj.getString("status"));
								category.setCategoryId(obj
										.getString("categoryId"));
								category.setCount(obj.getString("count"));
								categoryList.add(category);
							}
							initDatas();

						} catch (JSONException e) {

							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(int arg0, Header[] arg1, String arg2,
							Throwable throwable) {
						super.onFailure(arg0, arg1, arg2, throwable);
						HttpHandle hh = new HttpHandle();
						hh.handleFaile(ServerClassifyDialogActivity.this,
								throwable);

					}

				});

	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:

			finish();
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:

			break;
		case MotionEvent.ACTION_MOVE:

			break;
		}
		return super.onTouchEvent(ev);
	}

}
