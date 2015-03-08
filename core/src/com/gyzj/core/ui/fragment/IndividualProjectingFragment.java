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
import com.gyzj.core.ui.activity.UpLoadProjectActivity;
import com.gyzj.core.ui.activity.WorkerCountCenterActivity;
import com.gyzj.core.util.Constant;
import com.gyzj.core.util.DialogUtil;
import com.gyzj.core.util.Keys;
import com.gyzj.core.util.PersistentUtil;
import com.gyzj.core.util.ToastUtil;
import com.gyzj.core.util.activity.BaseActivityUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class IndividualProjectingFragment extends Fragment {
	private View view; // 返回界面
	private AsyncHttpClient ahc;
	private String userId,headImg;
	private ListView list;
	DisplayImageOptions options; // DisplayImageOptions是用于设置图片显示的类

	private List<ProjectBean> list_project = new ArrayList<ProjectBean>();
	private UserProjectListAdapter userProjectAdapter = new UserProjectListAdapter();
	TextView tv_project_name, tv_project_time,user_count;
	private Dialog dialog;
	public TextView tv_Thiscancle, tv_Thisconfirm;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.user_project_center_activity,
				container, false);
		list = (ListView) view.findViewById(R.id.user_project_list);
		headImg = PersistentUtil.getInstance().readString(getActivity(),
				"headImg", "1");
		userId = PersistentUtil.getInstance().readString(getActivity(),
				"userId", "1");
		ahc = new AsyncHttpClient();
		dialog = DialogUtil.getLoadDialog(getActivity(), "取消中");
		getData();
		return view;

	}

	private void getData() {

		RequestParams rp = new RequestParams();

		rp.put(Keys.KEY_METHOD, "getProInfo");
		rp.put(Keys.KEY_USERID, userId);
		rp.put("status", "N");

		ahc.post(getActivity(), Constant.HTTP_REQ_URL_PREFIX, rp,
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
								project.setProjectName(obj
										.getString("projectName"));
								project.setStartTime(obj.getString("startTime"));
								project.setCoverImg(obj.getString("coverImg"));
								project.setProjectId(obj.getString("projectId"));
								JSONArray img_array = obj.optJSONArray("imgs");

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
						R.layout.projecting_item, null);
			}
			final ProjectBean bean = list.get(count);

			TextView tv_confirm = (TextView) view.findViewById(R.id.projecting_confirm);
			tv_confirm.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.putExtra("pro_name", bean.getProjectName());
					intent.putExtra("pro_time", bean.getStartTime());
					intent.putExtra("pro_id", bean.getProjectId());
					intent.setClass(getActivity(), UpLoadProjectActivity.class);
					BaseActivityUtil.startActivity(getActivity(), intent,
							false, false);
				}
			});
			TextView tv_cancle = (TextView) view.findViewById(R.id.projecting_cancle);
			tv_cancle.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Cancle(bean.getProjectId());
					tv_Thiscancle =(TextView) v;

				}
			});

			tv_project_name = (TextView) view
					.findViewById(R.id.projecting_name);

			tv_project_time = (TextView) view
					.findViewById(R.id.projecting_time);

			tv_project_name.setText(bean.getProjectName());

			String timeStr = bean.getStartTime();
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
				tv_project_time.setText(nian + 1900 + "年" + yue + "月" + ri
						+ "日" + shi + ":" + fen);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// tv_project_time.setText(thisTime);
			return view;
		}

	}

	public void Cancle(String projectId) {

		RequestParams rp = new RequestParams();

		rp.put(Keys.KEY_METHOD, "modifyProject");
		rp.put("projectId", projectId);
		rp.put("status", "D");

		ahc.post(getActivity(), Constant.HTTP_REQ_URL_PREFIX, rp,
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
						if (dialog.isShowing()) {
							dialog.dismiss();
						}
						tv_Thiscancle.setText("已取消");
						getData();

					}

					@Override
					public void onFailure(int arg0, Header[] arg1, String arg2,
							Throwable throwable) {
						super.onFailure(arg0, arg1, arg2, throwable);
						if (dialog.isShowing()) {
							dialog.dismiss();
						}
						// HttpHandle hh = new HttpHandle();
						// hh.handleFaile(UpLoadProjectActivity.this,
						// throwable);
						ToastUtil.toastshort(getActivity(), "提交失败");

					}

				});

	}

}
