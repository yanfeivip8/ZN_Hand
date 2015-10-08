package com.github.flyyan.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;

import com.github.flyyan.R;
import com.github.flyyan.adapter.PageViewAdapter;
import com.github.flyyan.fragment.BranchFragment;
import com.github.flyyan.fragment.CommitFragment;
import com.github.flyyan.fragment.ContributorFragment;
import com.github.flyyan.fragment.ReleaseFragment;

@SuppressLint("NewApi") 
public class ReposDetailActivity extends FragmentActivity implements
		OnPageChangeListener, TabListener {

	private ViewPager viewPager;

	private List<Fragment> fragments = new ArrayList<Fragment>();

	private PageViewAdapter adapter ;
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.repos_details);

		viewPager = (ViewPager) findViewById(R.id.vp_repos_details_main);

		String url = this.getIntent().getExtras().getString("url");
		fragments.add(new CommitFragment(url));
		fragments.add(new BranchFragment());
		fragments.add(new ContributorFragment());
		fragments.add(new ReleaseFragment());
		
		
		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		getActionBar().setBackgroundDrawable(
				getResources().getDrawable(R.drawable.title_bar_shape));
		// 初始化TAB属性
		String[] tabName = null;
		String[] temTabName = { "commits","branch",  "contributor ", "release" };
		int [] tabIds = {R.drawable.bar_icon_commits ,R.drawable.bar_icon_branch ,  R.drawable.bar_icon_contributor , R.drawable.bar_icon_release};
		tabName = temTabName;

		for (int i = 0; i < tabName.length; i++) {
			ActionBar.Tab tab = getActionBar().newTab();
//			tab.setCustomView(R.layout.custom_title_bar);
			tab.setIcon(tabIds[i]);
			tab.setText(tabName[i]);
			tab.setTabListener(this);
			tab.setTag(i);
			getActionBar().addTab(tab);
		}
		adapter = new PageViewAdapter(getSupportFragmentManager(), fragments);
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(this);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		getActionBar().getTabAt(arg0).select();
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		if (tab.getTag() == null)
			return;
		//选中tab,滑动选项卡
		int index = ((Integer) tab.getTag()).intValue();
		if (viewPager != null && viewPager.getChildCount() > 0
				&& fragments.size() > index)
			viewPager.setCurrentItem(index);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		
	}
}