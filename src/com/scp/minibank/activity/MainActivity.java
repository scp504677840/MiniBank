package com.scp.minibank.activity;

import java.util.ArrayList;
import java.util.List;

import com.scp.minibank.R;
import com.scp.minibank.activity.setting.SettingAccountActivity;
import com.scp.minibank.adpter.MyFragmentPagerAdapter;
import com.scp.minibank.fragment.MainFragment;
import com.scp.minibank.fragment.PayFragment;
import com.scp.minibank.fragment.RoeFragment;
import com.scp.minibank.fragment.UserFragment;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

/**
 * ���
 * 
 * @author �δ���
 *
 */
public class MainActivity extends BaseActivity implements OnClickListener,
		OnPageChangeListener {
	// ViewPager���
	private ViewPager mViewPager;
	private MyFragmentPagerAdapter mfpAdapter;// ������
	private List<Fragment> fragments = new ArrayList<Fragment>();// ����Դ
	// �ĸ�LinearLayout
	private LinearLayout mainLinearLayout;
	private LinearLayout payLinearLayout;
	private LinearLayout roeLinearLayout;
	private LinearLayout userLinearLayout;
	// �ĸ�ͼƬ��ť
	private ImageButton mainImgBtn;
	private ImageButton payImgBtn;
	private ImageButton roeImgBtn;
	private ImageButton userImgBtn;
	// �ĸ��ı�
	private TextView mainTV;
	private TextView payTV;
	private TextView roeTV;
	private TextView userTV;
	// �ĸ�fragment
	private Fragment mainFragment;
	private Fragment payFragment;
	private Fragment roeFragment;
	private Fragment userFragment;
	// ����
	private static final int SELECT_MAIN = 0;
	private static final int SELECT_PAY = 1;
	private static final int SELECT_ROE = 2;
	private static final int SELECT_USER = 3;
	// ListView���
	private ListView SlidingMenu;
	private static final String TEXT_COLOR = "#56ABE4";// �ı���ɫ
	// ��¼�ɹ���ע��ɹ���ת�������
	private Intent intent;
	private static int userinfoId;// �û�ID
	private boolean isHasAccount = false;// ����û��Ƿ�ӵ���˻�
	private SharedPreferences sharedPreferences;
	private Editor edit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_main);
		initData();
		initView();
		initEvent();
	}

	// ��ʼ���ؼ�
	private void initView() {
		/**************** ViewPager��� ****************/
		mViewPager = (ViewPager) findViewById(R.id.main_fragment);
		mfpAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),
				fragments);
		mViewPager.setAdapter(mfpAdapter);
		/**************** �ĸ�LinearLayout ****************/
		mainLinearLayout = (LinearLayout) findViewById(R.id.main_bottom_main);
		payLinearLayout = (LinearLayout) findViewById(R.id.main_bottom_pay);
		roeLinearLayout = (LinearLayout) findViewById(R.id.main_bottom_roe);
		userLinearLayout = (LinearLayout) findViewById(R.id.main_bottom_user);
		/**************** �ĸ�ͼƬ��ť ****************/
		mainImgBtn = (ImageButton) findViewById(R.id.main_bottom_main_imgbtn);
		payImgBtn = (ImageButton) findViewById(R.id.main_bottom_pay_imgbtn);
		roeImgBtn = (ImageButton) findViewById(R.id.main_bottom_roe_imgbtn);
		userImgBtn = (ImageButton) findViewById(R.id.main_bottom_user_imgbtn);
		/**************** �ĸ��ı� ****************/
		mainTV = (TextView) findViewById(R.id.main_bottom_main_text);
		payTV = (TextView) findViewById(R.id.main_bottom_pay_text);
		roeTV = (TextView) findViewById(R.id.main_bottom_roe_text);
		userTV = (TextView) findViewById(R.id.main_bottom_user_text);
		/**************** �໬�˵� ****************/
		SlidingMenu = (ListView) findViewById(R.id.main_listview);
		// Ĭ��ѡ�е�һ��
		setSelect(SELECT_MAIN);
	}

	// ��ʼ���¼�
	private void initEvent() {
		mainLinearLayout.setOnClickListener(this);
		payLinearLayout.setOnClickListener(this);
		roeLinearLayout.setOnClickListener(this);
		userLinearLayout.setOnClickListener(this);
		mViewPager.setOnPageChangeListener(this);
	}

	// ��ʼ������
	private void initData() {
		intent = getIntent();
		if (intent != null) {
			userinfoId = intent.getIntExtra("userinfoId", -1);
		}
		sharedPreferences = getSharedPreferences("isHasAccount", MODE_PRIVATE);
		isHasAccount = sharedPreferences.getBoolean("isFirstHasAccount", false);
		mainFragment = new MainFragment(this);
		payFragment = new PayFragment(this);
		roeFragment = new RoeFragment(this);
		userFragment = new UserFragment(this,userinfoId);
		fragments.add(mainFragment);
		fragments.add(payFragment);
		fragments.add(roeFragment);
		fragments.add(userFragment);
		// ����û��Ƿ�ӵ���˻�
		checkAccount();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_bottom_main:
			setSelect(SELECT_MAIN);
			break;
		case R.id.main_bottom_pay:
			setSelect(SELECT_PAY);
			break;
		case R.id.main_bottom_roe:
			setSelect(SELECT_ROE);
			break;
		case R.id.main_bottom_user:
			setSelect(SELECT_USER);
			break;

		default:
			break;
		}
	}

	// �����е�ͼƬ��ť��ͼƬ��Ϊδѡ��״̬
	private void resetImgBtn() {
		mainImgBtn.setImageResource(R.drawable.main_bottom_main_off);
		payImgBtn.setImageResource(R.drawable.main_bottom_pay_off);
		roeImgBtn.setImageResource(R.drawable.main_bottom_roe_off);
		userImgBtn.setImageResource(R.drawable.main_bottom_user_off);
		mainTV.setTextColor(Color.BLACK);
		payTV.setTextColor(Color.BLACK);
		roeTV.setTextColor(Color.BLACK);
		userTV.setTextColor(Color.BLACK);
	}

	// ��ѡ�е�TAB
	private void setSelect(int position) {
		resetImgBtn();
		int currentItem = SELECT_MAIN;
		switch (position) {
		case SELECT_MAIN:
			currentItem = SELECT_MAIN;
			mainImgBtn.setImageResource(R.drawable.main_bottom_main_on);
			mainTV.setTextColor(Color.parseColor(TEXT_COLOR));
			break;
		case SELECT_PAY:
			currentItem = SELECT_PAY;
			payImgBtn.setImageResource(R.drawable.main_bottom_pay_on);
			payTV.setTextColor(Color.parseColor(TEXT_COLOR));
			break;
		case SELECT_ROE:
			currentItem = SELECT_ROE;
			roeImgBtn.setImageResource(R.drawable.main_bottom_roe_on);
			roeTV.setTextColor(Color.parseColor(TEXT_COLOR));
			break;
		case SELECT_USER:
			currentItem = SELECT_USER;
			userImgBtn.setImageResource(R.drawable.main_bottom_user_on);
			userTV.setTextColor(Color.parseColor(TEXT_COLOR));
			break;

		default:
			break;
		}
		mViewPager.setCurrentItem(currentItem);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int position) {
		setSelect(position);
	}

	/**
	 * ����û��Ƿ�ӵ���˻�
	 */
	private void checkAccount() {
		if (!isHasAccount) {
			Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle("�������˻�");
			dialog.setMessage("����δӵ���˻����Ƿ����ڴ�����");
			dialog.setCancelable(false);
			dialog.setPositiveButton("ȷ��",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							// �Ƿ��ǵ�һ�ν���
							edit = sharedPreferences.edit();
							edit.putBoolean("isFirstHasAccount", true);
							edit.commit();
							edit.clear();
							intent = new Intent(MainActivity.this,
									SettingAccountActivity.class);
							intent.putExtra("userinfoId", userinfoId);
							MainActivity.this.startActivityForResult(intent,
									MyNumber.SETTING_ACCOUNT);
						}
					});
			dialog.setNegativeButton("ȡ��",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// �Ƿ��ǵ�һ�ν���
							edit = sharedPreferences.edit();
							edit.putBoolean("isFirstHasAccount", true);
							edit.commit();
							edit.clear();
							dialog.dismiss();
						}
					});
			dialog.show();
		}
	}
	
	
	
}
