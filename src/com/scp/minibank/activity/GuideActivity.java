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
 * ����ҳ
 * 
 * @author �δ���
 *
 */
public class GuideActivity extends Activity implements OnClickListener,
		OnPageChangeListener {
	private ViewPagerCompat mViewPagerCompat;// ViewPager
	private PagerAdapter adapter;// ������
	private int[] imgIds = null;// ҳ������
	private List<ImageView> imageViews;// ViewPager����Դ
	private Button btnLogin;// ��¼
	private Button btnRegister;// ע��
	private SharedPreferences sharedPreferences;// SharedPreferences
	private Editor edit;// �����༭
	private boolean isFirst;// �Ƿ��ǵ�һ�ν���Ӧ��
	private int mPosition;// ��ǰҳ��λ��

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_guide);
		initData();
		initView();
		initEvent();
	}

	// ��ʼ���ؼ�
	private void initView() {
		/********************* �ؼ���� *********************/
		mViewPagerCompat = (ViewPagerCompat) findViewById(R.id.guide_viewpager);
		btnLogin = (Button) findViewById(R.id.guide_tologin);
		btnRegister = (Button) findViewById(R.id.guide_toregister);
		/********************* ������� *********************/
		sharedPreferences = getSharedPreferences("isFirst", MODE_PRIVATE);
		isFirst = sharedPreferences.getBoolean("isFirst", true);
		// ���ǵ�һ�ν���Ӧ��
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
		mViewPagerCompat.setPageTransformer(true, new ZoomOutPageTransformer());// ���ö���
		mViewPagerCompat.setAdapter(adapter);
		// ������ҳ����1ʱ�����Ǹ�������ҳ��ı��¼���Ҳ�����û��ǵ�һ�ν���Ӧ��
		if (imgIds.length > 1) {
			mViewPagerCompat.setOnPageChangeListener(this);
		}
		// �Ƿ��ǵ�һ�ν���
		edit = sharedPreferences.edit();
		edit.putBoolean("isFirst", false);
		edit.commit();
		edit.clear();
	}

	// ��ʼ���¼�
	private void initEvent() {
		btnLogin.setOnClickListener(this);
		btnRegister.setOnClickListener(this);
	}

	// ��ʼ������
	private void initData() {
		imgIds = new int[] { R.drawable.guide_img_hyomin1,
				R.drawable.guide_img_hyomin4 };
	}

	// ����¼�
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
	 * ��ҳ�汻����ʱ position����ǰҳ�棬������������ҳ�� positionOffset����ǰҳ��ƫ�Ƶİٷֱ�
	 * positionOffsetPixels����ǰҳ��ƫ�Ƶ�����λ��
	 */
	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
	}

	/**
	 * ��ǰ�µ�ҳ�汻ѡ��ʱ
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
	 * ������״̬���иı�ʱ 1:��ʼ���� 2:ֹͣ���� 0:ʲô��û��
	 */
	@Override
	public void onPageScrollStateChanged(int state) {
		switch (state) {
		case 1:// ��ʼ����
			btnLogin.setVisibility(View.GONE);
			btnRegister.setVisibility(View.GONE);
			break;
		case 2:// ֹͣ����
			break;
		case 0:// ʲô��û��
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
