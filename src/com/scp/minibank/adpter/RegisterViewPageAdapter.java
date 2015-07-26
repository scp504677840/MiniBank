package com.scp.minibank.adpter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class RegisterViewPageAdapter extends FragmentPagerAdapter {
	private FragmentManager fm;
	private List<Fragment> fragments;

	public RegisterViewPageAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fm = fm;
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

}
