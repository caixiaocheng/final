package com.gyzj.core.ui.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.core.R;
import com.gyzj.core.bean.ProjectBean;
import com.gyzj.core.bean.WorkerBean;
import com.gyzj.core.enable.http.HttpHandle;
import com.gyzj.core.util.Constant;
import com.gyzj.core.util.DialogUtil;
import com.gyzj.core.util.Keys;
import com.gyzj.core.util.PersistentUtil;
import com.gyzj.core.util.ViewHolder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class WorkerProjectCenterActivity extends Activity {
	private RequestHandle reqhandle;
	private AsyncHttpClient ahc;
	private Dialog dialog;
	TextView tv_project_name,tv_project_place,tv_project_time;
	private String userId ;
	private ListView list ;
	private List<ProjectBean> list_project = new ArrayList<ProjectBean>();
	private UserProjectListAdapter userProjectAdapter = new UserProjectListAdapter();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_project_center_activity);
		userId = PersistentUtil.getInstance().readString(this, "userId", "1");
		tv_project_name = (TextView) findViewById(R.id.project_detail_name);
		tv_project_place = (TextView) findViewById(R.id.project_detail_place);
		tv_project_time = (TextView) findViewById(R.id.project_detail_starttime);
		dialog = DialogUtil.getLoadDialog(this, "亲，请稍后！");
		list = (ListView) findViewById(R.id.user_project_list);
		
		ahc = new AsyncHttpClient();
		getData();
		
		
	}
	private void getData() {

		RequestParams rp = new RequestParams();

		rp.put(Keys.KEY_METHOD, "getProInfo");
		rp.put(Keys.KEY_USERID, userId);

		reqhandle = ahc.post(this, Constant.HTTP_REQ_URL_PREFIX, rp,
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

						try {
						    JSONArray array = response.getJSONArray("list");
						    for(int i = 0;i<array.length();i++){
						    	ProjectBean project = new ProjectBean();
						    	JSONObject obj = array.optJSONObject(i);
						    	project.setPlace(obj.getString("place"));
						    	project.setProjectName(obj.getString("projectName"));
						    	project.setStartTime(obj.getString("startTime"));
						    	list_project.add(project);
						    }
						    userProjectAdapter.setList(list_project);
						    list.setAdapter(userProjectAdapter);
						   
						}catch (JSONException e) {

							e.printStackTrace();
						}
						dialog.dismiss();

					}

					@Override
                    public void onFailure(int arg0, Header[] arg1, String arg2,
                            Throwable throwable)
                    {
                        super.onFailure(arg0, arg1, arg2, throwable);
                        HttpHandle hh = new HttpHandle();
                        hh.handleFaile(WorkerProjectCenterActivity.this, throwable);
                        if (dialog.isShowing())
                        {
                            dialog.dismiss();
                        }
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
				view = getLayoutInflater().inflate(R.layout.user_project_item,
						null);
			}
			ProjectBean bean  = list.get(count);
			tv_project_name = (TextView) view.findViewById(R.id.project_detail_name);
			tv_project_place = (TextView) view.findViewById(R.id.project_detail_place);
			tv_project_time = (TextView)view.findViewById(R.id.project_detail_starttime);
			tv_project_name.setText("项目名称："+bean.getProjectName());
			tv_project_place.setText("项目地点："+bean.getPlace());
			tv_project_time.setText("项目开始时间："+bean.getStartTime());
			return view;
		}

	}


}
