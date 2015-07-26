package com.scp.minibank.fragment;

import com.scp.minibank.R;
import com.scp.minibank.activity.main.MainBalanceActivity;
import com.scp.minibank.activity.main.MainCorssTransferActivity;
import com.scp.minibank.activity.main.MainDetailsActivity;
import com.scp.minibank.activity.main.MainTransferActivity;
import com.scp.minibank.activity.main.MainTransferPersonActivity;
import com.scp.minibank.activity.main.MainTransferResultActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MainFragment extends Fragment implements OnClickListener {
	private View view;
	private Context mContext;
	// 六个LinearLayout
	private LinearLayout transferLL;// 行内转账
	private LinearLayout crossTransferLL;// 跨行转账
	private LinearLayout transferPersonLL;// 转账人管理
	private LinearLayout transferResultLL;// 转账结果查询
	private LinearLayout balanceLL;// 余额查询
	private LinearLayout detailsLL;// 详细信息查询

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_main, container, false);
		initData();
		initView();
		initEvent();
		return view;
	}

	public MainFragment(Context context) {
		mContext = context;
	}

	private void initView() {
		/**************** 六个LinearLayout ****************/
		transferLL = (LinearLayout) view
				.findViewById(R.id.fragment_main_transfer);
		crossTransferLL = (LinearLayout) view
				.findViewById(R.id.fragment_main_cross_transfer);
		transferPersonLL = (LinearLayout) view
				.findViewById(R.id.fragment_main_transfer_person);
		transferResultLL = (LinearLayout) view
				.findViewById(R.id.fragment_main_transfer_result);
		balanceLL = (LinearLayout) view
				.findViewById(R.id.fragment_main_balance);
		detailsLL = (LinearLayout) view
				.findViewById(R.id.fragment_main_details);
	}

	private void initData() {
	}

	private void initEvent() {
		transferLL.setOnClickListener(this);
		crossTransferLL.setOnClickListener(this);
		transferPersonLL.setOnClickListener(this);
		transferResultLL.setOnClickListener(this);
		balanceLL.setOnClickListener(this);
		detailsLL.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.fragment_main_transfer:
			intent = new Intent(mContext, MainTransferActivity.class);
			break;
		case R.id.fragment_main_cross_transfer:
			intent = new Intent(mContext, MainCorssTransferActivity.class);
			break;
		case R.id.fragment_main_transfer_person:
			intent = new Intent(mContext, MainTransferPersonActivity.class);
			break;
		case R.id.fragment_main_transfer_result:
			intent = new Intent(mContext, MainTransferResultActivity.class);
			break;
		case R.id.fragment_main_balance:
			intent = new Intent(mContext, MainBalanceActivity.class);
			break;
		case R.id.fragment_main_details:
			intent = new Intent(mContext, MainDetailsActivity.class);
			break;

		default:
			break;
		}
		if (intent != null) {
			mContext.startActivity(intent);
		}
	}
}
