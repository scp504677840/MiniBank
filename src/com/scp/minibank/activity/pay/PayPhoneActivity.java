package com.scp.minibank.activity.pay;

import android.os.Bundle;
import android.view.Window;

import com.scp.minibank.R;
import com.scp.minibank.activity.BaseActivity;

public class PayPhoneActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pay_phone);
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}
}
