package com.scp.minibank.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.scp.minibank.R;
import com.scp.minibank.activity.pay.PayGameActivity;
import com.scp.minibank.activity.pay.PayHomeActivity;
import com.scp.minibank.activity.pay.PayPhoneActivity;
import com.scp.minibank.activity.pay.PayPowerActivity;
import com.scp.minibank.activity.pay.PayQQActivity;
import com.scp.minibank.activity.pay.PayWaterActivity;

public class PayFragment extends Fragment implements OnClickListener {
	private View view;
	private Context mContext;
	// 六个充值模块
	private LinearLayout payPhoneLL;// 手机充值
	private LinearLayout payQQLL;// QQ充值
	private LinearLayout payGameLL;// 游戏充值
	private LinearLayout payHomeLL;// 物业
	private LinearLayout payPowerLL;// 电费
	private LinearLayout payWaterLL;// 水费

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_pay, container, false);
		initData();
		initView();
		initEvent();
		return view;
	}

	public PayFragment(Context context) {
		mContext = context;
	}

	private void initView() {
		payPhoneLL = (LinearLayout) view.findViewById(R.id.fragment_pay_phone);
		payQQLL = (LinearLayout) view.findViewById(R.id.fragment_pay_qq);
		payGameLL = (LinearLayout) view.findViewById(R.id.fragment_pay_game);
		payHomeLL = (LinearLayout) view.findViewById(R.id.fragment_pay_home);
		payPowerLL = (LinearLayout) view.findViewById(R.id.fragment_pay_power);
		payWaterLL = (LinearLayout) view.findViewById(R.id.fragment_pay_water);
	}

	private void initData() {
	}

	private void initEvent() {
		payPhoneLL.setOnClickListener(this);
		payQQLL.setOnClickListener(this);
		payGameLL.setOnClickListener(this);
		payHomeLL.setOnClickListener(this);
		payPowerLL.setOnClickListener(this);
		payWaterLL.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.fragment_pay_phone:
			intent = new Intent(mContext, PayPhoneActivity.class);
			break;
		case R.id.fragment_pay_qq:
			intent = new Intent(mContext, PayQQActivity.class);
			break;
		case R.id.fragment_pay_game:
			intent = new Intent(mContext, PayGameActivity.class);
			break;
		case R.id.fragment_pay_home:
			intent = new Intent(mContext, PayHomeActivity.class);
			break;
		case R.id.fragment_pay_power:
			intent = new Intent(mContext, PayPowerActivity.class);
			break;
		case R.id.fragment_pay_water:
			intent = new Intent(mContext, PayWaterActivity.class);
			break;

		default:
			break;
		}
		if (intent != null) {
			mContext.startActivity(intent);
		}
	}
}
