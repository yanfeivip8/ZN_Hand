package com.github.flyyan.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

public class PageViewAdapter extends FragmentPagerAdapter{

	private List<Fragment> fragments;
	public PageViewAdapter(FragmentManager fm , List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments ;
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}
	
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return  view == ((Fragment) object).getView();
	}
	
	@Override
	public void destroyItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
		super.destroyItem(container, position, object);
	}

}
