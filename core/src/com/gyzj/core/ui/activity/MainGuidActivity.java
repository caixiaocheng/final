package com.gyzj.core.ui.activity;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.core.R;
import com.gyzj.core.ui.fragment.AllWorkerListFragment;
import com.gyzj.core.ui.fragment.LoginFragment;
import com.gyzj.core.ui.fragment.MainGuidContactFragment;
import com.gyzj.core.ui.fragment.MainGuidIndividualFragment;
import com.gyzj.core.ui.fragment.MainGuidLocationFragment;
import com.gyzj.core.util.PersistentUtil;
import com.gyzj.core.util.activity.BaseActivityUtil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author 程才
 * @date 2014、10、17
 * @detai 登陆后主界面
 * 
 */
public class MainGuidActivity extends FragmentActivity implements
		OnClickListener {
	private FragmentManager frg_manager;
	private FragmentTransaction frg_transaction;
	private Fragment currentFragment = new Fragment();
	Fragment classFrag, locationFrag, individualFrag, contactFrag,
			loginFragment;
	private RadioButton btn_location, btn_individual, btn_contact,
			btn_classify;
	private HashMap<String, SoftReference<List>> Cache; // 缓存信息
	private TextView tv_right, tv_left;
   	String name = null;
    String pwd = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_guid_activity);
		
		name = PersistentUtil.getInstance().readString(this,PersistentUtil.KEY_LOGIN_NAME, "");
		pwd = PersistentUtil.getInstance().readString(this,PersistentUtil.KEY_LOGIN_PWD, "");
		tv_left = (TextView) findViewById(R.id.title_left);
		tv_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("chengcai", "chengcai");
				intent.setClass(MainGuidActivity.this,
						ServerClassifyDialogActivity.class);

				startActivityForResult(intent, 1);
				MainGuidActivity.this.overridePendingTransition(
						R.anim.push_right_in, R.anim.push_right_out);

			}
		});


		btn_classify = (RadioButton) findViewById(R.id.btn_classify);
		btn_classify.setOnClickListener(this);
		btn_classify.setChecked(true);

		btn_contact = (RadioButton) findViewById(R.id.btn_contact);
		btn_contact.setOnClickListener(this);

		btn_individual = (RadioButton) findViewById(R.id.btn_invidual);
		btn_individual.setOnClickListener(this);

		btn_location = (RadioButton) findViewById(R.id.btn_location);
		btn_location.setOnClickListener(this);
		frg_manager = getSupportFragmentManager();

		frg_transaction = frg_manager.beginTransaction();
		classFrag = new AllWorkerListFragment();

		currentFragment = classFrag;
		frg_transaction.replace(R.id.main_guaid_add_fragment, classFrag);
		frg_transaction.commit();
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub

		int id = view.getId();
		switch (id) {
		case R.id.btn_classify:

			if (currentFragment == classFrag)
				return;
			if (!btn_classify.isChecked()) {
				btn_classify.setChecked(true);
			}
			classFrag = new AllWorkerListFragment();
			currentFragment = classFrag;

			frg_transaction = frg_manager.beginTransaction();
			frg_transaction.replace(R.id.main_guaid_add_fragment, classFrag);
			frg_transaction.commit();
			break;
		case R.id.btn_location:
			if (currentFragment == locationFrag)
				return;
			locationFrag = new MainGuidLocationFragment();
			currentFragment = locationFrag;

			frg_transaction = frg_manager.beginTransaction();
			frg_transaction.replace(R.id.main_guaid_add_fragment, locationFrag);
			frg_transaction.commit();
			break;
		case R.id.btn_invidual:
			if (currentFragment == individualFrag)
				return;
			locationFrag = new LoginFragment();
			individualFrag = new MainGuidIndividualFragment();
			frg_transaction = frg_manager.beginTransaction();
			currentFragment = individualFrag;
			//name==null&&pwd==null
			if (true) {
			frg_transaction.replace(R.id.main_guaid_add_fragment,locationFrag);
			} else {
				frg_transaction.replace(R.id.main_guaid_add_fragment,individualFrag);
			}
			frg_transaction.commit();
			break;
		case R.id.btn_contact:
			if (currentFragment == contactFrag)
				return;
			contactFrag = new MainGuidContactFragment();
			currentFragment = contactFrag;

			frg_transaction = frg_manager.beginTransaction();
			frg_transaction.replace(R.id.main_guaid_add_fragment, contactFrag);
			frg_transaction.commit();
			break;

		default:
			break;
		}

	}

	@Override
	public void closeContextMenu() {
		// TODO Auto-generated method stub
		super.closeContextMenu();
	}

	public void setClassifyCache(HashMap<String, SoftReference<List>> Cache) {

		// TODO Auto-generated method stub
		this.Cache = Cache;
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);

		if (arg2 == null) {
			return;
		}
		btn_classify.setChecked(false);
		String categoryid = arg2.getStringExtra("categoryId");
		classFrag = new AllWorkerListFragment();
		Bundle bundle = new Bundle();

		bundle.putString("categoryId", categoryid);

		classFrag.setArguments(bundle);

		frg_transaction = frg_manager.beginTransaction();
		frg_transaction.replace(R.id.main_guaid_add_fragment, classFrag);
		frg_transaction.commit();
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
