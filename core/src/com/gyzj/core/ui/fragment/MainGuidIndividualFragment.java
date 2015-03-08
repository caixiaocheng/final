package com.gyzj.core.ui.fragment;

import com.example.core.R;
import com.gyzj.core.ui.activity.WorkerCountCenterActivity;
import com.gyzj.core.ui.activity.WorkerProjectCenterActivity;
import com.gyzj.core.util.Constant;
import com.gyzj.core.util.PersistentUtil;
import com.gyzj.core.util.activity.BaseActivityUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainGuidIndividualFragment extends Fragment {
	private View view; // ���ؽ���
	private TextView tv_wokerName,user_count;
	private TextView tv_workerLevel;
	private TextView tv_wokerRemain;
	private ImageView img_log;
	private RadioButton button_projecting, button_projected;
	private FragmentManager frg_manager;
	private FragmentTransaction frg_transaction;
	DisplayImageOptions options; // DisplayImageOptions����������ͼƬ��ʾ����

	private Fragment currentFragment = new Fragment();
	Fragment projectedFrag, projectingFrag;    //Ƕ��fragment
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
	}
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		view = inflater.inflate(R.layout.service_individual_fragment,
				container, false);
		
		
		user_count = (TextView) view.findViewById(R.id.user_count);
		user_count.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent();
				in.setClass(getActivity(), WorkerCountCenterActivity.class);
				BaseActivityUtil.startActivity(getActivity(), in, false, false);
			}
		});
		tv_wokerName = (TextView) view.findViewById(R.id.name);
		// tv_wokerRemain = (TextView) view.findViewById(R.id.remain);
       
		button_projected = (RadioButton) view.findViewById(R.id.user_projected);
		button_projected.setChecked(true);
		img_log = (ImageView) view.findViewById(R.id.worker_headImg);
		
		button_projected.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// BaseActivityUtil.startActivity(getActivity(),
				// WorkerCountCenterActivity.class, false);
				if (currentFragment == projectedFrag)
					return;
				projectedFrag = new IndividualProjectedFragment();
				currentFragment = projectedFrag;
				frg_transaction = frg_manager.beginTransaction();
				frg_transaction.replace(R.id.project_fragment, projectedFrag);
				frg_transaction.commit();
			}
		});
		button_projecting = (RadioButton) view
				.findViewById(R.id.user_projecting);
		button_projecting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// BaseActivityUtil.startActivity(getActivity(),
				// WorkerProjectCenterActivity.class, false);

				if (currentFragment == projectingFrag)
					return;
				projectingFrag = new IndividualProjectingFragment();
				currentFragment = projectingFrag;

				frg_transaction = frg_manager.beginTransaction();
				frg_transaction.replace(R.id.project_fragment, projectingFrag);
				frg_transaction.commit();
			}
		});
		options = new DisplayImageOptions.Builder()
		//.showStubImage(R.drawable.ic_stub) // ����ͼƬ�����ڼ���ʾ��ͼƬ
		//.showImageForEmptyUri(R.drawable.ic_empty) // ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
		.showImageOnFail(R.drawable.ic_error) // ����ͼƬ���ػ��������з���������ʾ��ͼƬ
		.cacheInMemory(true) // �������ص�ͼƬ�Ƿ񻺴����ڴ���
		.cacheOnDisc(true) // �������ص�ͼƬ�Ƿ񻺴���SD����
		.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
		.build(); // �������ù���DisplayImageOption����
		String remain = PersistentUtil.getInstance().readString(getActivity(),
				"remaining", "12");
		String name = PersistentUtil.getInstance().readString(getActivity(),
				"userName", "hehe");
		String level = PersistentUtil.getInstance().readString(getActivity(),
				"level", "hehe");
		String headImg = PersistentUtil.getInstance().readString(getActivity(),
				"headImg", "hehe");
		
		tv_wokerName.setText(name);
		ImageLoader.getInstance().displayImage(
				Constant.HTTP_REQ_PICTURE_PREFIX +headImg, img_log, options,
				null);

		// tv_wokerRemain.setText(remain);
		frg_manager = getChildFragmentManager();

		return view;

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		frg_transaction = frg_manager.beginTransaction();
		projectedFrag = new IndividualProjectedFragment();
		currentFragment = projectedFrag;
		frg_transaction.replace(R.id.project_fragment, projectedFrag);
		frg_transaction.commit();
	}
	private void initView(){
		
	}

}
