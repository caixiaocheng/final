package com.gyzj.core.ui.fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.spec.OAEPParameterSpec;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.core.R;
import com.google.gson.Gson;
import com.gyzj.core.bean.ProjectBean;
import com.gyzj.core.bean.WorkerBean;
import com.gyzj.core.enable.http.HttpHandle;
import com.gyzj.core.ui.activity.WorkerDetailActivity;
import com.gyzj.core.util.Constant;
import com.gyzj.core.util.DialogUtil;
import com.gyzj.core.util.Keys;
import com.gyzj.core.util.ToastUtil;
import com.gyzj.core.util.ViewHolder;
import com.gyzj.core.util.activity.BaseActivityUtil;
import com.gyzj.core.widget.AutoListView;
import com.gyzj.core.widget.DeleteEditText;
import com.gyzj.core.widget.SearcheditText;
import com.gyzj.core.widget.SearcheditText.OnSearchClickListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MainGuidLocationFragment extends Fragment implements OnSearchClickListener {
	private View view; // 返回界面
	private SearcheditText edit;
	private AsyncHttpClient ahc;
	private RequestHandle reqhandle;
	private GridView grid;
	private List<WorkerBean> worker_list = new ArrayList<WorkerBean>();
	private WorkerListAdapter adapter;
	private String img_url;
	private String content;
	DisplayImageOptions options; // DisplayImageOptions是用于设置图片显示的类
    private Dialog dialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.service_location_fragment, container,
				false);
		edit = (SearcheditText) view.findViewById(R.id.server_sarch);
		dialog = DialogUtil.getLoadDialog(getActivity(), "搜索中。。。");
		grid = (GridView) view.findViewById(R.id.gridview);
		adapter = new WorkerListAdapter();
		grid.setAdapter(adapter);
		grid.setOnItemClickListener(new OnItemClickListener() {

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
		edit.setOnDeleteClickListener(this);
		options = new DisplayImageOptions.Builder()
		//.showStubImage(R.drawable.ic_stub) // 设置图片下载期间显示的图片
		//.showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
		.showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
		.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
		.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
		.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
		.build(); // 创建配置过得DisplayImageOption对象

		/*
		 * edit = (EditText) view.findViewById(R.id.server_sarch); Drawable
		 * mClearDrawable = edit.getCompoundDrawables()[2];
		 */
		ahc = new AsyncHttpClient();
		/*
		 * for(int i=0;i<3;i++){
		 * 
		 * uploadGoodsImage(); }
		 */
		

		return view;

	}

	private void getData(String content) {
		worker_list.clear();
		content = edit.getText().toString().trim();
		if(TextUtils.isEmpty(content)){
			ToastUtil.toastshort(getActivity(),"请输入搜索内容");
			return;
		}
		final List<WorkerBean> worker_temp_list = new ArrayList<WorkerBean>();

		RequestParams rp = new RequestParams();

		rp.put(Keys.KEY_METHOD, "getSearchInfo");
		rp.put("searchName", content);

		reqhandle = ahc.post(getActivity(), Constant.HTTP_REQ_URL_PREFIX, rp,
				new JsonHttpResponseHandler(Constant.UNICODE) {
					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();

						dialog.show();
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) {
						super.onSuccess(statusCode, headers, response);
						if(dialog.isShowing()){
							dialog.dismiss();
						}

						try {

							JSONArray array = response.getJSONArray("infoList");
							Gson gson = new Gson();
                            if(array.length()<1){
                            	ToastUtil.toastshort(getActivity(), "数据库中无记录");
                            }
							for (int i = 0; i < array.length(); i++) {
								WorkerBean worker = new WorkerBean();
								String obj = array.getJSONObject(i).toString();
								worker = gson.fromJson(obj, WorkerBean.class);
								worker_temp_list.add(worker);

							}

							worker_list.addAll(worker_temp_list);

							adapter.setList(worker_list);

							adapter.notifyDataSetChanged();

						} catch (JSONException e) {
							ToastUtil.toastshort(getActivity(), "数据库中无记录");
							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(int arg0, Header[] arg1, String arg2,
							Throwable throwable) {
						if(dialog.isShowing()){
							dialog.dismiss();
						}
						super.onFailure(arg0, arg1, arg2, throwable);

						HttpHandle hh = new HttpHandle();
						hh.handleFaile(getActivity(), throwable);

					}

				});
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
			img_url = Constant.HTTP_REQ_PICTURE_PREFIX + woker.getHeadImg();
			ImageLoader.getInstance().displayImage(img_url, iv_headImge,
					options, null);
			TextView tv_userName = ViewHolder.get(view, R.id.worker_name);
			tv_userName.setText(woker.getUserName());

			TextView tv_infor = ViewHolder.get(view, R.id.worker_infor);
			tv_infor.setText("[" + woker.getCategoryId() + "]");

			TextView tv_location = ViewHolder.get(view, R.id.worker_location);
			tv_location.setText("上次在 " + woker.getWorkSpace());

			return view;
		}

	}

	@Override
	public void onDeleteClick() {
		// TODO Auto-generated method stub
		getData(content);
	}

}
