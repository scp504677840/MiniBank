package com.scp.minibank.activity;

import java.util.ArrayList;
import java.util.List;

import com.scp.minibank.R;
import com.scp.minibank.activity.login.LoginActivity;
import com.scp.minibank.activity.register.RegisterSendCaptchaActivity;
import com.scp.minibank.utils.ViewPagerCompat;
import com.scp.minibank.utils.ViewPagerCompat.OnPageChangeListener;
import com.scp.minibank.utils.ZoomOutPageTransformer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * 引导页
 * 
 * @author 宋春鹏
 *
 */
public class GuideActivity extends Activity implements OnClickListener,
		OnPageChangeListener {
	private ViewPagerCompat mViewPagerCompat;// ViewPager
	private PagerAdapter adapter;// 适配器
	private int[] imgIds = null;// 页面数据
	private List<ImageView> imageViews;// ViewPager数据源
	private Button btnLogin;// 登录
	private Button btnRegister;// 注册
	private SharedPreferences sharedPreferences;// SharedPreferences
	private Editor edit;// 启动编辑
	private boolean isFirst;// 是否是第一次进入应用
	private int mPosition;// 当前页面位置

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_guide);
		initData();
		initView();
		initEvent();
	}

	// 初始化控件
	private void initView() {
		/********************* 控件相关 *********************/
		mViewPagerCompat = (ViewPagerCompat) findViewById(R.id.guide_viewpager);
		btnLogin = (Button) findViewById(R.id.guide_tologin);
		btnRegister = (Button) findViewById(R.id.guide_toregister);
		/********************* 其它相关 *********************/
		sharedPreferences = getSharedPreferences("isFirst", MODE_PRIVATE);
		isFirst = sharedPreferences.getBoolean("isFirst", true);
		// 不是第一次进入应用
		if (!isFirst) {
			imgIds = new int[] { R.drawable.guide_img_hyomin3 };
			btnLogin.setVisibility(View.VISIBLE);
			btnRegister.setVisibility(View.VISIBLE);
		}
		imageViews = new ArrayList<ImageView>();
		for (int i = 0; i < imgIds.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setImageResource(imgIds[i]);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageViews.add(imageView);
		}
		adapter = new com.scp.minibank.adpter.PagerAdapter(this, imageViews);
		mViewPagerCompat.setPageTransformer(true, new ZoomOutPageTransformer());// 设置动画
		mViewPagerCompat.setAdapter(adapter);
		// 当引导页大于1时，我们给它设置页面改变事件，也就是用户是第一次进入应用
		if (imgIds.length > 1) {
			mViewPagerCompat.setOnPageChangeListener(this);
		}
		// 是否是第一次进入
		edit = sharedPreferences.edit();
		edit.putBoolean("isFirst", false);
		edit.commit();
		edit.clear();
	}

	// 初始化事件
	private void initEvent() {
		btnLogin.setOnClickListener(this);
		btnRegister.setOnClickListener(this);
	}

	// 初始化数据
	private void initData() {
		imgIds = new int[] { R.drawable.guide_img_hyomin1,
				R.drawable.guide_img_hyomin4 };
	}

	// 点击事件
	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.guide_tologin:
			intent = new Intent(GuideActivity.this, LoginActivity.class);
			break;
		case R.id.guide_toregister:
			intent = new Intent(GuideActivity.this, RegisterSendCaptchaActivity.class);
			break;

		default:
			break;
		}
		if (intent != null) {
			startActivity(intent);
			finish();
		}
	}

	/**
	 * 当页面被滑动时 position：当前页面，及你点击滑动的页面 positionOffset：当前页面偏移的百分比
	 * positionOffsetPixels：当前页面偏移的像素位置
	 */
	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
	}

	/**
	 * 当前新的页面被选中时
	 */
	@Override
	public void onPageSelected(int position) {
		mPosition = position;
		if (imgIds.length - 1 == position) {
			btnLogin.setVisibility(View.VISIBLE);
			btnRegister.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 当滑动状态进行改变时 1:开始滑动 2:停止滑动 0:什么都没做
	 */
	@Override
	public void onPageScrollStateChanged(int state) {
		switch (state) {
		case 1:// 开始滑动
			btnLogin.setVisibility(View.GONE);
			btnRegister.setVisibility(View.GONE);
			break;
		case 2:// 停止滑动
			break;
		case 0:// 什么都没做
			if (mPosition == imgIds.length - 1) {
				btnLogin.setVisibility(View.VISIBLE);
				btnRegister.setVisibility(View.VISIBLE);
			}
			break;

		default:
			break;
		}
	}

}
