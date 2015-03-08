package com.gyzj.core.ui.activity;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.core.R;
import com.gyzj.core.enable.http.HttpHandle;
import com.gyzj.core.util.Constant;
import com.gyzj.core.util.DialogUtil;
import com.gyzj.core.util.Keys;
import com.gyzj.core.util.PersistentUtil;
import com.gyzj.core.util.ToastUtil;
import com.gyzj.core.util.activity.BaseActivityUtil;
import com.gyzj.core.widget.DeleteEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 登录界面
 * 
 * @author 程才
 * @date 2014-10-10
 */
public class LoginActivity extends Activity implements OnClickListener {

	private TextView tvForgetPWD;

	private Button btnRegister;

	private Button btnLogin;

	private DeleteEditText etLoginName;

	private DeleteEditText etLoginPWD;

	private AsyncHttpClient ahc;

	private CheckBox cbLoginRememberpwd;

	private RequestHandle reqhandle;

	private Dialog dialog;

	private Dialog dia;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);

		dia = DialogUtil.getLoadDialog(this, "登录中，请稍等。");

		tvForgetPWD = (TextView) findViewById(R.id.tv_login_forget_pwd);
		tvForgetPWD.setOnClickListener(this);

		btnRegister = (Button) findViewById(R.id.btn_register);
		btnRegister.setOnClickListener(this);

		btnLogin = (Button) findViewById(R.id.btn_login);
		btnLogin.setOnClickListener(this);

		// 登录账号
		etLoginName = (DeleteEditText) findViewById(R.id.et_login_name);
		String name = PersistentUtil.getInstance().readString(this,PersistentUtil.KEY_LOGIN_NAME, "");
		etLoginName.setText(name);

		// 密码
		etLoginPWD = (DeleteEditText) findViewById(R.id.et_login_pwd);
		String pwd = PersistentUtil.getInstance().readString(this,PersistentUtil.KEY_LOGIN_PWD, "");
		etLoginPWD.setText(pwd);

		// 记住密码
		cbLoginRememberpwd = (CheckBox) findViewById(R.id.cb_login_rememberpwd);
		boolean checked = PersistentUtil.getInstance().readBoolean(this,
				PersistentUtil.KEY_LOGIN_REMEMBER_PWD, false);
		cbLoginRememberpwd.setChecked(checked);

		dialog = DialogUtil.getLoadDialog(this, "亲，请稍后");
		ahc = new AsyncHttpClient();
		
		

	}
	

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.tv_login_forget_pwd: {
			/*
			 * BaseActivityUtil.startActivity(this, ForgetPWDActivity.class,
			 * false);
			 */
			break;
		}
		case R.id.btn_register: {
			BaseActivityUtil.startActivity(this, RegisterActivity.class, true);
			break;
		}
		case R.id.btn_login: {
			/*
			 * BaseActivityUtil.startActivity(this, MainGuidActivity.class,
			 * false);
			 */
			login();
		}
		default: {
			break;
		}
		}

	}

	/**
	 * 登录
	 * q
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 */
	private void login() {
		// 检查
		final String name = etLoginName.getText().toString().trim();
		final String pwd = etLoginPWD.getText().toString();
      
		if (TextUtils.isEmpty(name)) {
			ToastUtil.toastshort(this, getString(R.string.login_enter_username));
			return;
		}

		if (TextUtils.isEmpty(pwd)) {
			ToastUtil.toastshort(this, getString(R.string.login_enter_pwd));
			return;
		}
		  

		RequestParams rp = new RequestParams();
		rp.put(Keys.KEY_PHONE, name);
		rp.put(Keys.KEY_PWD,pwd);
	     
	    rp.put(Keys.KEY_METHOD, "login");

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
						
						int status = response.optInt("code");
						JSONArray array;
						try {
							array = response.getJSONArray("list");
							JSONObject object = array.getJSONObject(0);
							String name = object.getString("userName");
							String level = object.getString("level");
							String remain = object.getString("remaining");
							String userId = object.getString("userId");
							PersistentUtil.getInstance().write(LoginActivity.this,"userName",name);
							PersistentUtil.getInstance().write(LoginActivity.this,"level",level);
							PersistentUtil.getInstance().write(LoginActivity.this,"remaining",remain);
							PersistentUtil.getInstance().write(LoginActivity.this,"userId",userId);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						switch (status) {
						case 1:
							dialog.dismiss();
							ToastUtil.toastshort(LoginActivity.this, "用户名不存在");
							break;
						case 2:
							dialog.dismiss();
							PersistentUtil.getInstance().write(LoginActivity.this,
									PersistentUtil.KEY_LOGIN_PWD, pwd);
							PersistentUtil.getInstance().write(LoginActivity.this,
					                PersistentUtil.KEY_LOGIN_NAME,
					                name);
							BaseActivityUtil.startActivity(LoginActivity.this,
									MainGuidActivity.class, true);

							break;
						case 3:
							dialog.dismiss();
							ToastUtil.toastshort(LoginActivity.this, "密码错误");

							break;

						default:
							break;
						}

					}

					@Override
                    public void onFailure(int arg0, Header[] arg1, String arg2,
                            Throwable throwable)
                    {
                        super.onFailure(arg0, arg1, arg2, throwable);
                       HttpHandle hh = new HttpHandle();
                        hh.handleFaile(LoginActivity.this, throwable);
                        if (dialog.isShowing())
                        {
                            dialog.dismiss();
                        }
                    }
                    
                });

		// 记录密码
		
		 dia.setOnCancelListener(new OnCancelListener()
	        {
	            @Override
	            public void onCancel(DialogInterface dialog)
	            {
	                if (reqhandle != null && reqhandle.isFinished() == false)
	                {
	                    reqhandle=null;
	                }
	                
	               
	            }
	        });
	}

	/**
	 * 菜单、返回键响应
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 调用双击退出函数
			exitBy2Click();
		}
		return false;
	}

	/**
	 * 双击退出函数
	 */
	private static Boolean isExit = false;

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

		} else {
			this.finish();
			System.exit(0);
		}
	}
	
}
