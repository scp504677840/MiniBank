package com.scp.minibank.activity.user;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.scp.minibank.R;
import com.scp.minibank.activity.BaseActivity;

/**
 * 电话
 * 
 * @author 宋春鹏
 *
 */
public class UserTelActivity extends BaseActivity {
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_tel);
		initData();
		initView();
		initEvent();
	}

	private void initData() {
	}

	private void initView() {
		actionBar = getActionBar();
		actionBar.setTitle("创建账户");
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	private void initEvent() {
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;

		default:
			break;
		}
		return true;
	}
}
