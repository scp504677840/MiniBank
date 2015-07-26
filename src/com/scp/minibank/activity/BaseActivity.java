package com.scp.minibank.activity;

import com.scp.minibank.utils.ActivityCollector;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Activity����
 * 
 * @author �δ���
 *
 */
public class BaseActivity extends FragmentActivity {
	private long time = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ر�����
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ��ȡ��ǰʵ��������
		Log.i("BaseActivity", getClass().getSimpleName());
		ActivityCollector.addActivity(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}

	@Override
	public void onBackPressed() {
		if (System.currentTimeMillis() - time < 3000) {
			time = 0;
			ActivityCollector.finishAll();
		} else {
			time = System.currentTimeMillis();
			Toast.makeText(this, "�ٰ�һ�η��ؼ��˳�����", Toast.LENGTH_SHORT).show();
		}
	}

}
