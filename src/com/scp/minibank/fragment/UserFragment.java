package com.scp.minibank.fragment;

import com.scp.minibank.R;
import com.scp.minibank.activity.user.UserNameActivity;
import com.scp.minibank.activity.user.UserNickNameActivity;
import com.scp.minibank.activity.user.UserRealActivity;
import com.scp.minibank.activity.user.UserTelActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UserFragment extends Fragment implements OnClickListener {
	private View view;
	private Context mContext;
	// 6��RelativeLayout
	private RelativeLayout iconRL;// ͷ��
	private RelativeLayout nameRL;// ����
	private RelativeLayout nicknameRL;// �ǳ�
	private RelativeLayout telRL;// �绰
	private RelativeLayout sexRL;// �Ա�
	private RelativeLayout realRL;// ʵ����֤
	// 6���Ҳ�����
	private ImageView iconIV;
	private TextView nameTv;
	private TextView nicknameTv;
	private TextView telTv;
	private TextView sexTv;
	private TextView realTv;
	// �û����
	private int userinfoId;// �û�ID

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_user, container, false);
		initView();
		initData();
		initEvent();
		return view;
	}

	public UserFragment(Context context, int loginId) {
		mContext = context;
		this.userinfoId = userinfoId;
	}

	private void initView() {
		/**************** 6��RelativeLayout ****************/
		iconRL = (RelativeLayout) view.findViewById(R.id.user_icon);
		nameRL = (RelativeLayout) view.findViewById(R.id.user_name);
		nicknameRL = (RelativeLayout) view.findViewById(R.id.user_nickname);
		telRL = (RelativeLayout) view.findViewById(R.id.user_tel);
		sexRL = (RelativeLayout) view.findViewById(R.id.user_sex);
		realRL = (RelativeLayout) view.findViewById(R.id.user_real);
		/**************** 6���Ҳ����� ****************/
		iconIV = (ImageView) view.findViewById(R.id.user_icon_imageview);
		nameTv = (TextView) view.findViewById(R.id.user_name_textview);
		nicknameTv = (TextView) view.findViewById(R.id.user_nickname_textview);
		telTv = (TextView) view.findViewById(R.id.user_tel_textview);
		sexTv = (TextView) view.findViewById(R.id.user_sex_textview);
		realTv = (TextView) view.findViewById(R.id.user_real_textview);
	}

	private void initData() {
	}

	private void initEvent() {
		/**************** 6��RelativeLayout ****************/
		iconRL.setOnClickListener(this);
		nameRL.setOnClickListener(this);
		nicknameRL.setOnClickListener(this);
		telRL.setOnClickListener(this);
		sexRL.setOnClickListener(this);
		realRL.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.user_icon:
			// TODO
			break;
		case R.id.user_name:
			intent = new Intent(mContext, UserNameActivity.class);
			break;
		case R.id.user_nickname:
			intent = new Intent(mContext, UserNickNameActivity.class);
			break;
		case R.id.user_tel:
			intent = new Intent(mContext, UserTelActivity.class);
			break;
		case R.id.user_sex:
			// TODO
			break;
		case R.id.user_real:
			intent = new Intent(mContext, UserRealActivity.class);
			break;

		default:
			break;
		}
		if (intent != null) {
			mContext.startActivity(intent);
		}
	}
}
