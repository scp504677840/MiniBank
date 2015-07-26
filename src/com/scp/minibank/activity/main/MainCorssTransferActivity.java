package com.scp.minibank.activity.main;

import com.scp.minibank.R;
import com.scp.minibank.activity.BaseActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

/**
 * ¿çÐÐ×ªÕË
 * 
 * @author ËÎ´ºÅô
 *
 */
public class MainCorssTransferActivity extends BaseActivity {
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_corss_transfer);
		initData();
		initView();
		initEvent();
	}

	private void initData() {
	}

	private void initView() {
		actionBar = getActionBar();
		actionBar.setTitle("¿çÐÐ×ªÕË");
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
