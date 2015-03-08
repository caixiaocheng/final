package com.gyzj.core.ui.activity;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.core.R;
import com.gyzj.core.bean.ProjectBean;
import com.gyzj.core.enable.http.HttpHandle;
import com.gyzj.core.util.Constant;
import com.gyzj.core.util.DialogUtil;
import com.gyzj.core.util.Keys;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;

public class WorkerProjectDetailActivity extends Activity {
	private RequestHandle reqhandle;
	private AsyncHttpClient ahc;
	private Dialog dialog;
	TextView tv_project_name,tv_project_place,tv_project_time;
	private String projectId ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.project_detail_activity);
		projectId = getIntent().getStringExtra("projectId");
		tv_project_name = (TextView) findViewById(R.id.project_detail_name);
		tv_project_place = (TextView) findViewById(R.id.project_detail_place);
		tv_project_time = (TextView) findViewById(R.id.project_detail_starttime);
		dialog = DialogUtil.getLoadDialog(this, "亲，请稍后！");
		ahc = new AsyncHttpClient();
		getData();
		
		
	}
	private void getData() {

		RequestParams rp = new RequestParams();

		rp.put(Keys.KEY_METHOD, "findProject");
		rp.put(Keys.KEY_PROJECTID, projectId);

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
						    JSONObject obj = response.getJSONObject("list");
							tv_project_place.setText("项目地点："+obj.getString("place"));
							tv_project_name.setText("项目名称："+obj.getString("projectName"));
							tv_project_time.setText("项目开始时间："+obj.getString("startTime"));
						
						} catch (JSONException e) {

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
                        hh.handleFaile(WorkerProjectDetailActivity.this, throwable);
                        if (dialog.isShowing())
                        {
                            dialog.dismiss();
                        }
                    }
                    
                });

	}


}
