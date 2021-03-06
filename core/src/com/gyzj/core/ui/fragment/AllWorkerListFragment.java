package com.gyzj.core.ui.fragment;

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
import com.gyzj.core.ui.activity.Project_show_activity;
import com.gyzj.core.ui.activity.WorkerDetailActivity;
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
import com.gyzj.core.widget.PullToRefreshView;
import com.gyzj.core.widget.PullToRefreshView.OnFooterRefreshListener;
import com.gyzj.core.widget.PullToRefreshView.OnHeaderRefreshListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author 程才
 * @date 2014、10、13
 * @detail 所有工友列表界面
 * 
 */
public class AllWorkerListFragment extends Fragment implements
		OnHeaderRefreshListener, OnFooterRefreshListener {

	private View view; // 加载界面
	private Boolean isFullDate = false;
	private int count = 0; // 总条数
	private int pageNo = 1;
	DisplayImageOptions options; // DisplayImageOptions是用于设置图片显示的类
	private AsyncHttpClient ahc; // 异步处理
	private RequestHandle reqhandle;
	private LinearLayout line_progress;
	private List<WorkerBean> worker_list = new ArrayList<WorkerBean>();

	PullToRefreshView mPullToRefreshView;
	GridView mGridView;
	private WorkerListAdapter adapter;
	private String classifyId;
	private JSONArray array_cache = new JSONArray();
	private ACache cache;
	private String img_url;
    private Dialog dia;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.worker_list_activity, null, false);

		inflater = (LayoutInflater) getActivity().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
        dia = DialogUtil.getLoadDialog(getActivity(), "么么哒，稍后。。");
		Bundle bundle = getArguments();
		if (bundle != null) {
			classifyId = bundle.getString("categoryId");
		}
		mPullToRefreshView = (PullToRefreshView) view
				.findViewById(R.id.main_pull_refresh_view);
		mGridView = (GridView) view.findViewById(R.id.gridview);
		mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				WorkerBean bean = worker_list.get(position);
				Intent intent = new Intent();
				intent.setClass(getActivity(),WorkerDetailActivity.class);
				intent.putExtra("userid", bean.getUserId());
				intent.putExtra("head_img",Constant.HTTP_REQ_PICTURE_PREFIX+worker_list.get(position).getHeadImg());
				BaseActivityUtil.startActivity(getActivity(), intent, false, false);
			}
		});
		
		options = new DisplayImageOptions.Builder()
		//.showStubImage(R.drawable.ic_stub) // 设置图片下载期间显示的图片
		//.showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
		.showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
		.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
		.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
		.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
		.build(); // 创建配置过得DisplayImageOption对象
		
		adapter = new WorkerListAdapter();
		mGridView.setAdapter(adapter);
		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);
      //  mPullToRefreshView.setFootViewGone();
		line_progress = (LinearLayout) view.findViewById(R.id.progress);
		ahc = new AsyncHttpClient();
		cache = ACache.get(getActivity());
		mPullToRefreshView.onHeaderRefreshComplete();

		getData(AutoListView.LOAD, pageNo, true);

		return view;

	}

	private void getData(final int type, final int pageNo, boolean isFirstTime) {
		final List<WorkerBean> worker_temp_list = new ArrayList<WorkerBean>();

		JSONArray array = cache.getAsJSONArray("workerlist"); // 判断缓存中是否有数据，有则从缓存中拿
		String str_count = (String) cache.getAsString("count");

		if (isFirstTime && array != null && classifyId == null) {

			try {

				Gson gson = new Gson();

				for (int i = 0; i < array.length(); i++) {
					WorkerBean worker = new WorkerBean();
					String obj = array.getJSONObject(i).toString();
					worker = gson.fromJson(obj, WorkerBean.class);
					worker_temp_list.add(worker);

				}

				worker_list.addAll(worker_temp_list);

				adapter.setList(worker_list);
				mPullToRefreshView.setFootViewVisible();
				mPullToRefreshView.onFooterRefreshComplete();
				adapter.notifyDataSetChanged();
				ToastUtil.toastshort(getActivity(), "从缓存中拿");
				line_progress.setVisibility(View.GONE);
				count = Integer.parseInt(str_count);
				if (worker_list.size() >= count) {
					isFullDate = true;
				}
				return;
			} catch (JSONException e) {

				e.printStackTrace();
			}

		}

		RequestParams rp = new RequestParams();
		if (classifyId != null) {
			rp.put("categoryId", classifyId);
		}
		rp.put(Keys.KEY_METHOD, "getUserInfo");
		rp.put("pageNo", pageNo + "");

		reqhandle = ahc.post(getActivity(), Constant.HTTP_REQ_URL_PREFIX, rp,
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
                                if(dia.isShowing()){
                                	dia.dismiss();
                                }
						try {

							String str_count = (String) response
									.optString("count");
							String str_pagesize = (String) response
									.optString("pageSize");

							count = Integer.parseInt(str_count);

							cache.put("count", str_count);

							JSONArray array = response.getJSONArray("list");
							Gson gson = new Gson();

							for (int i = 0; i < array.length(); i++) {
								WorkerBean worker = new WorkerBean();
								String obj = array.getJSONObject(i).toString();
								worker = gson.fromJson(obj, WorkerBean.class);
								worker_temp_list.add(worker);

							}
							if (type == AutoListView.REFRESH) {
								worker_list.clear(); // 清除以前的数据
								worker_list.addAll(worker_temp_list);
								adapter.setList(worker_list); // 刷新适配器
								mPullToRefreshView.onHeaderRefreshComplete();
								adapter.notifyDataSetChanged();
								// line_progress.setVisibility(View.GONE);

								array_cache = toJsonArray(worker_list);

								if (classifyId == null) {
									cache.put("workerlist", array_cache);
								}
								if (worker_list.size() >= count) {
									isFullDate = true;

								} else {
									isFullDate = false;
								}

								return;
							}
							worker_list.addAll(worker_temp_list);
							if (worker_list.size() >= count) {
								isFullDate = true;

							}

							adapter.setList(worker_list);
							mPullToRefreshView.setFootViewVisible();
							mPullToRefreshView.onFooterRefreshComplete();
							adapter.notifyDataSetChanged();
							line_progress.setVisibility(View.GONE);
							array_cache = toJsonArray(worker_list);
							if (classifyId == null) {
								cache.put("workerlist", array_cache);

							}

						} catch (JSONException e) {

							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(int arg0, Header[] arg1, String arg2,
							Throwable throwable) {
						super.onFailure(arg0, arg1, arg2, throwable);
						if(dia.isShowing()){
                        	dia.dismiss();
                        }
						if (type == AutoListView.REFRESH) {
							mPullToRefreshView.onHeaderRefreshComplete();
						} else {
							// mPullToRefreshView.setFootFailureState();
							mPullToRefreshView.onFooterRefreshComplete();
						}
						HttpHandle hh = new HttpHandle();
						hh.handleFaile(getActivity(), throwable);

					}

				});
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (reqhandle != null)
			reqhandle = null;
	}

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
				view = getActivity().getLayoutInflater().inflate(
						R.layout.worker_list_item, null);
			}

			WorkerBean woker = list.get(count);
			ImageView iv_headImge = ViewHolder.get(view, R.id.woker_log);
		    img_url = Constant.HTTP_REQ_PICTURE_PREFIX+woker.getHeadImg();
			ImageLoader.getInstance().displayImage(img_url, iv_headImge,
					options, null);
			TextView tv_userName = ViewHolder.get(view, R.id.worker_name);
			tv_userName.setText( woker.getUserName());
			
			
			TextView tv_infor = ViewHolder.get(view, R.id.worker_infor);
			tv_infor.setText("["+ woker.getCategoryId()+"]");
			
			TextView tv_location = ViewHolder.get(view, R.id.worker_location);
			tv_location.setText( "上次在 "+woker.getWorkSpace());
			

			return view;
		}

	}

	private JSONArray toJsonArray(List<WorkerBean> list) throws JSONException {
		JSONArray array = new JSONArray();
		Gson gson = new Gson();
		for (int i = 0; i < list.size(); i++) {
			array.put(new JSONObject(gson.toJson(list.get(i))));
		}
		return array;
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		// TODO Auto-generated method stub
		if (isFullDate) {
			mPullToRefreshView.onFooterRefreshComplete();
			ToastUtil.toastshort(getActivity(), "木有数据啦。。");
			return;
		}
		pageNo++;
		getData(AutoListView.LOAD, pageNo, false);
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		// TODO Auto-generated method stub

		pageNo = 1;
		getData(AutoListView.REFRESH, pageNo, false);
	}

}
