package com.gyzj.core.ui.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.core.R;
import com.gyzj.core.bean.ProjectBean;
import com.gyzj.core.bean.WorkerBean;
import com.gyzj.core.bean.WorkerDetailBean;
import com.gyzj.core.bean.categoryName;
import com.gyzj.core.enable.http.HttpHandle;
import com.gyzj.core.util.Constant;
import com.gyzj.core.util.DialogUtil;
import com.gyzj.core.util.Keys;
import com.gyzj.core.util.ToastUtil;
import com.gyzj.core.util.ViewHolder;
import com.gyzj.core.util.activity.BaseActivityUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author 程才
 * @date 2014、10、17
 * @detail 工友信息详情
 * 
 */
public class WorkerDetailActivity extends Activity {
	private ListView lv_workerlist;
	private Dialog dialog;
	private RequestHandle reqhandle;
	private AsyncHttpClient ahc;
	private String userId;
	private GridView grid;
	private ArrayList<HashMap<String, Object>> projectItem;
	private List<ProjectBean> projectList = new ArrayList<ProjectBean>();
	private Map<String, Object> project;
	TextView tv_workerName, tv_woker, tv_workerLoc, tv_wokerPlace,
			tv_workerClassify, tv_workerInfo;
	private List<WorkerDetailBean> workerdetail_list = new ArrayList<WorkerDetailBean>();
	ArrayList<String> list = new ArrayList<String>();
	private ListView user_list;
	private String head_img;
	private ImageView iv_headImg,title_back;
	private ProjectListAdapter proAdapter = new ProjectListAdapter();
	DisplayImageOptions options; // DisplayImageOptions是用于设置图片显示的类
    private ArrayList<String> list_img = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.worker_detail_activity);
		title_back= (ImageView) findViewById(R.id.title_back);
		title_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_right_out);
			}
		});
		user_list = (ListView) findViewById(R.id.individual_project_list);
		user_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				ProjectBean pro = projectList.get(position);
				intent.putExtra("time", pro.getPayTime());
				intent.putExtra("name", pro.getProjectName());
				intent.setClass(WorkerDetailActivity.this,
						Project_show_activity.class);
				intent.putStringArrayListExtra("img", pro.getImgList());

				BaseActivityUtil.startActivity(WorkerDetailActivity.this,
						intent, false, false);
						

				//BaseActivityUtil.startActivity(WorkerDetailActivity.this, Project_show_activity.class, false);
			}
		});
		Intent intent = getIntent();
		userId = intent.getStringExtra("userid");
		head_img = intent.getStringExtra("head_img");
		project = new HashMap<String, Object>();
		tv_workerName = (TextView) findViewById(R.id.woker_name1);
		tv_workerClassify = (TextView) findViewById(R.id.worker_classify);
		tv_workerLoc = (TextView) findViewById(R.id.worker_loc);
		iv_headImg = (ImageView) findViewById(R.id.worker_headImg);
		options = new DisplayImageOptions.Builder()
		// .showStubImage(R.drawable.ic_stub) // 设置图片下载期间显示的图片
		// .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
				// .showImageOnFail(R.drawable.ic_error) //
				// 设置图片加载或解码过程中发生错误显示的图片
				// .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).build(); // 创建配置过得DisplayImageOption对象

		dialog = DialogUtil.getLoadDialog(this, "亲，请稍后！");
		ImageLoader.getInstance().displayImage(head_img, iv_headImg, options,
				null);
		ahc = new AsyncHttpClient();

		getData();

	}

	public void initDatas() {

	}

	private void getData() {

		RequestParams rp = new RequestParams();

		rp.put(Keys.KEY_METHOD, "getUserById");
		rp.put(Keys.KEY_USERID, userId);

		reqhandle = ahc.post(this, Constant.HTTP_REQ_URL_PREFIX, rp,
				new JsonHttpResponseHandler(Constant.UNICODE) {
					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
						//dialog.show();
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) {
						super.onSuccess(statusCode, headers, response);

						try {

							JSONObject obj = response.getJSONObject("list");
							tv_workerLoc.setText(obj.optString("workSpace"));
							tv_workerClassify.setText("["
									+ obj.optString("categoryId") + "]");
							tv_workerName.setText(obj.optString("userName"));
							
							JSONArray array = obj.getJSONArray("proList");
							for (int i = 0; i < array.length(); i++) {
								JSONObject project = array.getJSONObject(i);
								ProjectBean pro = new ProjectBean();
								pro.setProjectName(project
										.getString("projectName"));
								pro.setPlace(project.getString("place"));
								pro.setProjectId(project.getString("projectId"));
								pro.setCoverImg(project.getString("coverImg"));
								pro.setPayTime(project.getString("payTime"));
								JSONArray img_array = project
										.optJSONArray("imgs");
							     ArrayList<String> list_img = new ArrayList<String>();

								for (int j = 0; j < img_array.length(); j++) {
									
									String img = img_array.getString(j);
									list_img.add(img);

								}
								pro.setImgList(list_img);

								projectList.add(pro);
							}
							proAdapter.setList(projectList);
							user_list.setAdapter(proAdapter);

							// initDatas();
							dialog.dismiss();
						} catch (JSONException e) {

							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(int arg0, Header[] arg1, String arg2,
							Throwable throwable) {
						super.onFailure(arg0, arg1, arg2, throwable);
						HttpHandle hh = new HttpHandle();
						hh.handleFaile(WorkerDetailActivity.this, throwable);
						if (dialog.isShowing()) {
							dialog.dismiss();
						}
					}

				});

	}

	@SuppressWarnings("unused")
	private class ProjectListAdapter extends BaseAdapter {
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
				view = getLayoutInflater().inflate(
						R.layout.individual_project_item, null);
			}
			ProjectBean pro = projectList.get(count);
			ImageView iv_coverImg = ViewHolder.get(view, R.id.coverImg);
			String img_url = Constant.HTTP_REQ_PICTURE_PREFIX
					+ pro.getCoverImg();
			ImageLoader.getInstance().displayImage(img_url, iv_coverImg,
					options, null);
			TextView pay_time = ViewHolder.get(view, R.id.pro_detail_starttime);
			String timeStr = pro.getPayTime();
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date date;
			try {
				date = format.parse(timeStr);
				int nian = date.getYear();
				int yue_temp = date.getMonth() + 1;
				int yue = yue_temp > 12 ? 1 : yue_temp;
				pay_time.setText(nian + 1900 + "年" + yue + "月");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TextView pro_name = ViewHolder.get(view, R.id.pro_name);
			pro_name.setText(pro.getProjectName());

			return view;
		}

	}
}
