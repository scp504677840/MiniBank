package com.scp.minibank.activity;

import org.litepal.LitePalApplication;

import com.thinkland.sdk.util.CommonFun;


public class CaptchaApplication extends LitePalApplication {
	@Override
	public void onCreate() {
		super.onCreate();
		CommonFun.initialize(getApplicationContext(), false);
	}
}
