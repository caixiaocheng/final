package com.gyzj.core.ui.activity;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.core.R;
import com.google.gson.Gson;
import com.gyzj.core.bean.WorkerBean;
import com.gyzj.core.enable.http.HttpHandle;
import com.gyzj.core.util.Constant;
import com.gyzj.core.util.DialogUtil;
import com.gyzj.core.util.Keys;
import com.gyzj.core.util.ToastUtil;
import com.gyzj.core.util.ViewHolder;
import com.gyzj.core.util.activity.BaseActivityUtil;
import com.gyzj.core.util.cache.ACache;
import com.gyzj.core.widget.AutoListView;
import com.gyzj.core.widget.AutoListView.OnLoadListener;
import com.gyzj.core.widget.AutoListView.OnRefreshListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author 程才
 * @date 2014、10、17
 * @detail 工友信息列表
 * 
 */
public class WorkerListActivity extends Activity implements OnRefreshListener,
		OnLoadListener {
	private AutoListView lv_workerlist;
	private WorkerListAdapter workerAdapter;
	private Dialog dialog;
	private RequestHandle reqhandle;
	private AsyncHttpClient ahc;
	private List<WorkerBean> worker_list = new ArrayList<WorkerBean>();
	private LinearLayout line_progress;
	private int count = 0; // 总条数
	private int pageNo = 1;
	private int pageCount;// 总页数
	private ACache cache;
	private int pagesize; //
	private JSONArray array_cache = new JSONArray();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.worker_list_activity);
		cache = ACache.get(this);

		workerAdapter = new WorkerListAdapter();
		line_progress = (LinearLayout) findViewById(R.id.progress);
		dialog = DialogUtil.getLoadDialog(this, "亲，请稍后！");
	
		lv_workerlist.setAdapter(workerAdapter);
		lv_workerlist.setOnRefreshListener(this);
		lv_workerlist.setOnLoadListener(this);
		lv_workerlist.setOnItemClickListener(new OnItemClickListener() {

			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(WorkerListActivity.this,
						WorkerDetailActivity.class);
				intent.putExtra("userid", worker_list.get(position).getUserId());
				BaseActivityUtil.startActivity(WorkerListActivity.this, intent,
						false, false);

			}
		});
		ahc = new AsyncHttpClient();

		getData(AutoListView.LOAD, pageNo, true);
	}

	private void getData(final int type, final int pageNo, boolean isFirstTime) {
		final List<WorkerBean> worker_temp_list = new ArrayList<WorkerBean>();
		/*JSONArray array = cache.getAsJSONArray("workerlist");
		int cache_count = (Integer) cache.getAsObject("count");
		int cache_pageNo = (Integer) cache.getAsObject("pageNo");
		if (isFirstTime && array != null) {
			

			try {

				Gson gson = new Gson();

				for (int i = 0; i < array.length(); i++) {
					WorkerBean worker = new WorkerBean();
					String obj = array.getJSONObject(i).toString();
					worker = gson.fromJson(obj, WorkerBean.class);
					worker_temp_list.add(worker);

				}

				lv_workerlist.onLoadComplete();
				worker_list.addAll(worker_temp_list);

				workerAdapter.setList(worker_list);

				workerAdapter.notifyDataSetChanged();
				ToastUtil.toastshort(this, "从缓存中拿");
				line_progress.setVisibility(View.GONE);
				if(worker_list.size()==count)
					lv_workerlist.setResultSize(2);			     
				this.pageNo =cache_pageNo;
				

				return;
			} catch (JSONException e) {

				e.printStackTrace();
			}

		}
*/
		RequestParams rp = new RequestParams();

		rp.put(Keys.KEY_METHOD, "getUserInfo");
		rp.put("pageNo", pageNo + "");

		reqhandle = ahc.post(this, Constant.HTTP_REQ_URL_PREFIX, rp,
				new JsonHttpResponseHandler(Constant.UNICODE) {
					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();

						// dialog.show();
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) {
						super.onSuccess(statusCode, headers, response);

						try {
							String str_count = (String) response.optString("count");
							String str_pagesize = (String) response
									.optString("pageSize");

							count = Integer.parseInt(str_count);
							cache.put("count", count);
							pagesize = Integer.parseInt(str_pagesize);
							pageCount = count % pagesize == 0 ? count/ pagesize : (int)( count / pagesize )+ 1;

							JSONArray array = response.getJSONArray("list");
							Gson gson = new Gson();

							for (int i = 0; i < array.length(); i++) {
								WorkerBean worker = new WorkerBean();
								String obj = array.getJSONObject(i).toString();
								worker = gson.fromJson(obj, WorkerBean.class);
								worker_temp_list.add(worker);

							}
							if (type == AutoListView.REFRESH) {
								lv_workerlist.onRefreshComplete(); // 刷新成功后改变状态
								worker_list.clear(); // 清除以前的数据
								worker_list.addAll(worker_temp_list);
								workerAdapter.setList(worker_list); // 刷新适配器
								workerAdapter.notifyDataSetChanged();
								line_progress.setVisibility(View.GONE);

								array_cache = toJsonArray(worker_list);

								cache.put("workerlist", array_cache);
								cache.put("pageNo", pageNo);

								//lv_workerlist.setResultSize(10);
								return;
							}
						//	lv_workerlist.onLoadComplete();
							worker_list.addAll(worker_temp_list);

							workerAdapter.setList(worker_list);

							workerAdapter.notifyDataSetChanged();
							line_progress.setVisibility(View.GONE);
							
							
							 
							//array_cache = toJsonArray(worker_list);
							//cache.put("workerlist", array_cache);
							//cache.put("pageNo", pageNo);

						} catch (JSONException e) {

							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(int arg0, Header[] arg1, String arg2,
							Throwable throwable) {
						super.onFailure(arg0, arg1, arg2, throwable);

						if (type == AutoListView.REFRESH) {
							lv_workerlist.onRefreshComplete();
						} else {
							lv_workerlist.onLoadComplete();
							lv_workerlist.setFaliedState();

						}
						HttpHandle hh = new HttpHandle();
						hh.handleFaile(WorkerListActivity.this, throwable);
						if (dialog.isShowing()) {
							dialog.dismiss();

						}
					}

				});

	}

	@SuppressWarnings("unused")
	private class WorkerListAdapter extends BaseAdapter {
		List<WorkerBean> list = null;

		public List<WorkerBean> getList() {
			return list;
		}

		public void setList(List<WorkerBean> list) {
			this.list = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list == null ? 0 : list.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int count, View view, ViewGroup arg2) {
			if (list == null) {
				return null;
			}
			if (view == null) {
				view = getLayoutInflater().inflate(R.layout.worker_list_item,
						null);
			}
			/*WorkerBean woker = list.get(count);
			ImageView iv_headImge = ViewHolder.get(view, R.id.worker_headImg);
			if (count == 0) {

				iv_headImge.setBackgroundResource(R.drawable.gongyoulog);
			}
			TextView tv_niceName = ViewHolder.get(view, R.id.worker_nickname);
			tv_niceName.setText("姓名：" + woker.getNickName());
			TextView tv_level = ViewHolder.get(view, R.id.worker_level);
			tv_level.setText("工友等级：" + woker.getLevel());
			TextView tv_workFrequency = ViewHolder.get(view,
					R.id.worker_workFrequency);
			tv_workFrequency.setText("出工次数：" + woker.getWorkFrequency());*/
			return view;
		}

	}

	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		pageNo++;
		getData(AutoListView.LOAD, pageNo, false);

	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		pageNo = 1;
		getData(AutoListView.REFRESH, pageNo, false);
	}

	private JSONArray toJsonArray(List<WorkerBean> list) throws JSONException {
		JSONArray array = new JSONArray();
		Gson gson = new Gson();
		for (int i = 0; i < list.size(); i++) {
			array.put(new JSONObject(gson.toJson(list.get(i))));
		}
		return array;
	}
}
