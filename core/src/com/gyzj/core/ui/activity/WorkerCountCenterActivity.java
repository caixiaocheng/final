package com.gyzj.core.ui.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.crypto.spec.IvParameterSpec;

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
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

public class WorkerCountCenterActivity extends Activity {
	private TextView tv_workerLevel;
	private TextView tv_wokerRemain;
	private TextView tv_projectName, tv_projectPlace, tv_projectPay,
			tv_projectTime,tv_total;
	private RequestHandle reqhandle;
	private AsyncHttpClient ahc;
	private Dialog dialog;
	private RadioButton btn_starTime, btn_endTime;
	private List<ProjectBean> list = new ArrayList<ProjectBean>();
	
	
	
	private static final int SHOW_DATAPICK = 0;
	private static final int DATE_DIALOG_ID = 1;
	private static final int SHOW_TIMEPICK = 2;
	private static final int TIME_DIALOG_ID = 3;
	private int mYear;
	private int mMonth;
	private int mDay;
	private ListView lv_count;
	private WorkerListAdapter adapter = new WorkerListAdapter();
	private String total;
	private ImageView im_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.count_center_activity);

		tv_projectName = (TextView) findViewById(R.id.project_name);
		tv_projectPay = (TextView) findViewById(R.id.project_payable);
		tv_projectPlace = (TextView) findViewById(R.id.project_place);
        lv_count = (ListView) findViewById(R.id.user_count_list);
        tv_total = (TextView) findViewById(R.id.total);
        lv_count.setAdapter(adapter);
		
		String remain = PersistentUtil.getInstance().readString(this,
				"remaining", "12");
		String name = PersistentUtil.getInstance().readString(this, "userName",
				"hehe");
		String level = PersistentUtil.getInstance().readString(this, "level",
				"hehe");
		im_back = (ImageView) findViewById(R.id.title_back);
		im_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_right_out);
			}
		});
		dialog = DialogUtil.getLoadDialog(this, "亲，请稍后！");
		ahc = new AsyncHttpClient();
		getData();
	}

	

	

	private void getData() {

		RequestParams rp = new RequestParams();
        String userId = PersistentUtil.getInstance().readString(this, Keys.KEY_USERID, "5");
		rp.put(Keys.KEY_METHOD, "proAccountInfo");
		rp.put(Keys.KEY_TYPE, "1");
		rp.put(Keys.KEY_USERID,userId);

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
							total = response.getString("total");
							tv_total.setText("总收入："+total);
							JSONArray array = response.getJSONArray("list");

							for (int i = 0; i < array.length(); i++) {
								JSONObject project = array.getJSONObject(i);
								ProjectBean pro = new ProjectBean();
								pro.setProjectName(project
										.getString("projectName"));
								pro.setPlace(project.getString("place"));
								pro.setPayable(project.getString("payable"));
								pro.setStartTime(project.getString("payTime"));
								list.add(pro);
							}

							adapter.setList(list);
							adapter.notifyDataSetChanged();
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
						hh.handleFaile(WorkerCountCenterActivity.this,
								throwable);
						if (dialog.isShowing()) {
							dialog.dismiss();
						}
					}

				});

	}
	
	private class WorkerListAdapter extends BaseAdapter {
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
				view = getLayoutInflater().inflate(
						R.layout.project_list_itme, null);
			}

			ProjectBean woker =  list.get(count);
			TextView tv_userName = ViewHolder.get(view, R.id.project_name);
			 tv_userName.setText(woker.getProjectName());
			
			
			TextView tv_infor = ViewHolder.get(view, R.id.project_place);
			tv_infor.setText(woker.getPlace());
			
			TextView tv_location = ViewHolder.get(view, R.id.project_payable);
			tv_location.setText(woker.getPayable());
			

			return view;
		}

	}

	
	
	
	

	

	


	
	
	
	
	
	

}
