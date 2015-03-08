package com.gyzj.core.ui.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.core.R;
import com.gyzj.core.bean.ProjectBean;
import com.gyzj.core.enable.http.HttpHandle;
import com.gyzj.core.ui.activity.LoginActivity;
import com.gyzj.core.ui.activity.Project_show_activity;
import com.gyzj.core.ui.activity.WorkerDetailActivity;
import com.gyzj.core.util.Constant;
import com.gyzj.core.util.Keys;
import com.gyzj.core.util.PersistentUtil;
import com.gyzj.core.util.activity.BaseActivityUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.Data;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class IndividualProjectedFragment extends Fragment {
	private View view; // 返回界面
	private RequestHandle reqhandle;
	private AsyncHttpClient ahc;
	private String userId;
	DisplayImageOptions options; // DisplayImageOptions是用于设置图片显示的类

	private List<ProjectBean> list_project = new ArrayList<ProjectBean>();
	private UserProjectListAdapter userProjectAdapter = new UserProjectListAdapter();
	TextView tv_project_name, tv_project_place, tv_project_time;
	private ListView list;
	private ImageView img;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	//	view = inflater.inflate(R.layout.dialog_comm, container, false);

		view = inflater.inflate(R.layout.user_project_center_activity,
				container, false);
		list = (ListView) view.findViewById(R.id.user_project_list);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				ProjectBean pro = list_project.get(position);
				intent.putExtra("time", pro.getStartTime());
				intent.putExtra("name", pro.getProjectName());
				intent.setClass(getActivity(), Project_show_activity.class);
				intent.putStringArrayListExtra("img", pro.getImgList());

				BaseActivityUtil.startActivity(getActivity(), intent, false,
						false);

				// BaseActivityUtil.startActivity(WorkerDetailActivity.this,
				// Project_show_activity.class, false);
			}
		});
		ahc = new AsyncHttpClient();

		options = new DisplayImageOptions.Builder()
		// .showStubImage(R.drawable.ic_stub) // 设置图片下载期间显示的图片
		// .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
		// .showImageOnFail(R.drawable.ic_error) //
		// 设置图片加载或解码过程中发生错误显示的图片
		// .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).build(); // 创建配置过得DisplayImageOption对象

		userId = PersistentUtil.getInstance().readString(getActivity(),
				"userId", "1");
		getData();

		return view;

	}

	private void getData() {

		RequestParams rp = new RequestParams();

		rp.put(Keys.KEY_METHOD, "getProInfo");
		rp.put(Keys.KEY_USERID, userId);
		rp.put("status", "A");

		reqhandle = ahc.post(getActivity(), Constant.HTTP_REQ_URL_PREFIX, rp,
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

						try {
							JSONArray array = response.getJSONArray("list");
							for (int i = 0; i < array.length(); i++) {
								ProjectBean project = new ProjectBean();
								JSONObject obj = array.optJSONObject(i);
								project.setPlace(obj.getString("place"));
								project.setProjectName(obj
										.getString("projectName"));
								project.setStartTime(obj.getString("startTime"));
								project.setPayable(obj.getString("payable"));
								project.setCoverImg(obj.getString("coverImg"));
								JSONArray img_array = obj.optJSONArray("imgs");
								ArrayList<String> list_img = new ArrayList<String>();

								for (int j = 0; j < img_array.length(); j++) {

									String img = img_array.getString(j);
									list_img.add(img);

								}
								project.setImgList(list_img);

								list_project.add(project);
							}
							userProjectAdapter.setList(list_project);
							list.setAdapter(userProjectAdapter);

						} catch (JSONException e) {

							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(int arg0, Header[] arg1, String arg2,
							Throwable throwable) {
						super.onFailure(arg0, arg1, arg2, throwable);
						HttpHandle hh = new HttpHandle();
						hh.handleFaile(getActivity(), throwable);

					}

				});

	}

	private class UserProjectListAdapter extends BaseAdapter {
		List<ProjectBean> list = null;

		public List<ProjectBean> getList() {
			return list;
		}

		public void setList(List<ProjectBean> list) {
			this.list = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
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
			if (view == null) {
				view = getActivity().getLayoutInflater().inflate(
						R.layout.user_project_item, null);
			}
			ProjectBean bean = list.get(count);
			tv_project_name = (TextView) view
					.findViewById(R.id.project_detail_name);
			tv_project_place = (TextView) view
					.findViewById(R.id.project_detail_place);
			tv_project_time = (TextView) view
					.findViewById(R.id.project_detail_starttime);
			img = (ImageView) view.findViewById(R.id.user_img);
			String img_url = bean.getCoverImg();
			ImageLoader.getInstance().displayImage(
					Constant.HTTP_REQ_PICTURE_PREFIX + img_url, img, options,
					null);
			tv_project_name.setText(bean.getProjectName());
			tv_project_place.setText(bean.getPayable() + "元");

			String timeStr = bean.getStartTime();
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date date;
			try {
				date = format.parse(timeStr);
				int nian = date.getYear();
				int yue_temp = date.getMonth() + 1;
				int yue = yue_temp > 12 ? 1 : yue_temp;
				tv_project_time.setText(nian + 1900 + "年" + yue + "月");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// tv_project_time.setText(thisTime);
			return view;
		}

	}

}
