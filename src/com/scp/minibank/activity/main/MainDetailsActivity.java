package com.scp.minibank.activity.main;

import com.scp.minibank.R;
import com.scp.minibank.activity.BaseActivity;

import android.os.Bundle;
import android.view.Window;

/**
 * œÍœ∏–≈œ¢
 * 
 * @author ÀŒ¥∫≈Ù
 *
 */
public class MainDetailsActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_details);
	}

	@Override
	public void onBackPressed() {
		finish();
	}
}
