package com.gyzj.core.ui.fragment;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.core.R;
import com.gyzj.core.enable.http.HttpHandle;
import com.gyzj.core.ui.activity.LoginActivity;
import com.gyzj.core.ui.activity.MainGuidActivity;
import com.gyzj.core.ui.activity.RegisterActivity;
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

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class LoginFragment extends Fragment implements OnClickListener{
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
	private View view;  //���ؽ��� 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.login_activity, container, false);
		dia = DialogUtil.getLoadDialog(getActivity(), "��¼�У����Եȡ�");

		tvForgetPWD = (TextView) view.findViewById(R.id.tv_login_forget_pwd);
		tvForgetPWD.setOnClickListener(this);

		btnRegister = (Button) view.findViewById(R.id.btn_register);
		btnRegister.setOnClickListener(this);

		btnLogin = (Button) view.findViewById(R.id.btn_login);
		btnLogin.setOnClickListener(this);

		// ��¼�˺�
		etLoginName = (DeleteEditText) view.findViewById(R.id.et_login_name);
		String name = PersistentUtil.getInstance().readString(getActivity(),PersistentUtil.KEY_LOGIN_NAME, "");
		etLoginName.setText(name);

		// ����
		etLoginPWD = (DeleteEditText) view.findViewById(R.id.et_login_pwd);
		String pwd = PersistentUtil.getInstance().readString(getActivity(),PersistentUtil.KEY_LOGIN_PWD, "");
		etLoginPWD.setText(pwd);

		// ��ס����
		cbLoginRememberpwd = (CheckBox) view.findViewById(R.id.cb_login_rememberpwd);
		boolean checked = PersistentUtil.getInstance().readBoolean(getActivity(),
				PersistentUtil.KEY_LOGIN_REMEMBER_PWD, false);
		cbLoginRememberpwd.setChecked(checked);

		dialog = DialogUtil.getLoadDialog(getActivity(), "�ף����Ժ�");
		ahc = new AsyncHttpClient();
		
		
		return view;
	      
		
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
			BaseActivityUtil.startActivity(getActivity(), RegisterActivity.class, false);
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
	 * ��¼
	 * q
	 * @return void [��������˵��]
	 * @exception throws [Υ������] [Υ��˵��]
	 */
	private void login() {
		// ���
		final String name = etLoginName.getText().toString().trim();
		final String pwd = etLoginPWD.getText().toString();
      
		if (TextUtils.isEmpty(name)) {
			ToastUtil.toastshort(getActivity(), getString(R.string.login_enter_username));
			return;
		}

		if (TextUtils.isEmpty(pwd)) {
			ToastUtil.toastshort(getActivity(), getString(R.string.login_enter_pwd));
			return;
		}
		  

		RequestParams rp = new RequestParams();
		rp.put(Keys.KEY_PHONE, name);
		rp.put(Keys.KEY_PWD,pwd);
	     
	    rp.put(Keys.KEY_METHOD, "login");
		

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
						
						int status = response.optInt("code");
						
						switch (status) {
						case 1:
							dialog.dismiss();
							ToastUtil.toastshort(getActivity(), "�û���������");
							break;
						case 2:
							dialog.dismiss();
							JSONArray array;
							try {
								array = response.getJSONArray("list");
								JSONObject object = array.getJSONObject(0);
								String name = object.getString("userName");
								String level = object.getString("level");
								String remain = object.getString("remaining");
								String userId = object.getString("userId");
								String headImg = object.getString("headImg");
								PersistentUtil.getInstance().write(getActivity(),"userName",name);
								PersistentUtil.getInstance().write(getActivity(),"level",level);
								PersistentUtil.getInstance().write(getActivity(),"remaining",remain);
								PersistentUtil.getInstance().write(getActivity(),"userId",userId);
								PersistentUtil.getInstance().write(getActivity(),"headImg",headImg);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							PersistentUtil.getInstance().write(getActivity(),
									PersistentUtil.KEY_LOGIN_PWD, pwd);
							PersistentUtil.getInstance().write(getActivity(),
					                PersistentUtil.KEY_LOGIN_NAME,
					                name);
							MainGuidIndividualFragment individualFrag = new MainGuidIndividualFragment();
							getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_guaid_add_fragment,
						individualFrag).commit();
							

							break;
						case 3:
							dialog.dismiss();
							ToastUtil.toastshort(getActivity(), "�������");

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
                        hh.handleFaile(getActivity(), throwable);
                        if (dialog.isShowing())
                        {
                            dialog.dismiss();
                        }
                    }
                    
                });

		// ��¼����
		
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



}
