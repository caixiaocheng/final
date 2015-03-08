package com.gyzj.core.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

/**
 * @author ³Ì²Å
 * @date 2014¡¢10.10
 *
 */
public class ViewPagerAdapter extends PagerAdapter{
	private List<ImageView> imageViews;
	
	public ViewPagerAdapter(List<ImageView> imageViews){
		this.imageViews = imageViews;
	}
	@Override
	public int getCount() {
		return imageViews.size();
	}

	@Override
	public Object instantiateItem(View arg0, int arg1) {
		((ViewPager) arg0).addView(imageViews.get(arg1));
		return imageViews.get(arg1);
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView((View) arg2);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
}
