package com.gyzj.core.ui.fragment;

import com.example.core.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;

public class MainGuidContactFragment extends Fragment {
	private View view;  //∑µªÿΩÁ√Ê 
	private Button button;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.contact_activity, container, false);
		button = (Button) view.findViewById(R.id.call);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setAction("android.intent.action.CALL");
				intent.addCategory("android.intent.category.DEFAULT");
				intent.setData(Uri.parse("tel:" + "4006620001"));
				startActivity(intent);
				
			}
		});
		return view;
	      
		
	}

}
