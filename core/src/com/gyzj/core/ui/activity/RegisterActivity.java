package com.gyzj.core.ui.activity;

import org.apache.http.Header;
import org.json.JSONObject;

import com.example.core.R;
import com.gyzj.core.enable.http.HttpHandle;
import com.gyzj.core.util.Constant;
import com.gyzj.core.util.DialogUtil;
import com.gyzj.core.util.Keys;
import com.gyzj.core.util.PersistentUtil;
import com.gyzj.core.util.SysProperty;
import com.gyzj.core.util.ToastUtil;
import com.gyzj.core.util.activity.BaseActivityUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 注册界面
 * 
 * @author 程才
 * @date 2014-10-10
 */
public class RegisterActivity extends Activity {

	private AsyncHttpClient ahc;

	private RequestHandle reqhandle;

	private RequestHandle reqComhandle;

	private Dialog dia;

	private Button btn_reg;
	String getCode = null;
	private String name, pass;// 用户名密码
	private EditText et_phone, et_pass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_info_activity);
		dia = DialogUtil.getLoadDialog(this, null);
		et_phone = (EditText) findViewById(R.id.et_reg_phone);
		et_pass = (EditText) findViewById(R.id.et_reg_password);
		dia = DialogUtil.getLoadDialog(this, "亲 ，请稍后");
		ahc= new AsyncHttpClient();
		btn_reg = (Button) findViewById(R.id.bt_reg);
		btn_reg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				regist();
			}
		});
		

		

	}

	/**
	 * 注册
	 * 
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 */
	private void regist() {
		// 检查
		String name = et_phone.getText().toString().trim();
		final String pwd = et_pass.getText().toString().trim();

		if (TextUtils.isEmpty(name)) {
			ToastUtil.toastshort(this,"请输入手机号码");
			return;
		}

		if (TextUtils.isEmpty(pwd)) {
			ToastUtil.toastshort(this,"请输入登录密码");
			return;
		}
		dia.show();
		RequestParams rp = new RequestParams();
		rp.put(Keys.KEY_PHONE, name);
		rp.put(Keys.KEY_PWD, pwd);
		rp.put(Keys.KEY_METHOD, "regist");

		reqhandle = ahc.post(this,
				Constant.HTTP_REQ_URL_PREFIX, rp,
				new JsonHttpResponseHandler(Constant.UNICODE) {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) {
						super.onSuccess(statusCode, headers, response);
                        dia.dismiss();
						int status = response.optInt("code");
						switch (status) {
						case 1:
							ToastUtil.toastshort(RegisterActivity.this, "注册成功");
							finish();
							overridePendingTransition(R.anim.push_right_in,
									R.anim.push_right_out);
							break;
						case 2:
							ToastUtil.toastshort(RegisterActivity.this, "已经存在");

							break;
						case 3:
							ToastUtil.toastshort(RegisterActivity.this, "注册失败");

							break;

						default:
							break;
						}

					}

					/*@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
						super.onFailure(arg0, arg1, arg2, arg3);
						dia.dismiss();
						ToastUtil.toastshort(RegisterActivity.this, "连接失败");
					}*/
				});

		
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
		overridePendingTransition(R.anim.push_right_in,
				R.anim.push_right_out);
	}

}
