package com.scp.minibank.activity.setting;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.scp.minibank.R;
import com.scp.minibank.activity.BaseActivity;
import com.scp.minibank.activity.MainActivity;
import com.scp.minibank.utils.HttpUtil;

/**
 * 设置账户
 * 
 * @author 宋春鹏
 *
 */
public class SettingAccountActivity extends BaseActivity implements
		OnClickListener, OnCheckedChangeListener {
	private ActionBar actionBar;
	private Intent intent;
	private EditText ed_AcountNumber;// 账户名
	private EditText ed_AcountPwd;// 支付密码
	private EditText ed_AcountBalance;// 余额
	private RadioGroup rg_AccountFrozen;// 是否冻结
	private RadioGroup rg_AccountMode;// 存储方式
	private Button btn_AccountCommit;// 提交
	private String accountFrozen;// 是否冻结文本
	private String accountMode;// 存储方式文本
	private int userinfoId;// 用户ID
	// 网络相关
	private HttpUtil httpUtil;
	private String POST = "POST";
	private static final String accountInsertUrl = "http://192.168.43.94:8080/MiniBank/accountAction!accountInsert.action";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_account);
		initData();
		initView();
		initEvent();
	}

	private void initData() {
		httpUtil = new HttpUtil();
		intent = getIntent();
		userinfoId = intent.getIntExtra("userinfoId", -1);
	}

	private void initView() {
		actionBar = getActionBar();
		actionBar.setTitle("创建账户");
		actionBar.setDisplayShowHomeEnabled(false);// 关闭主页图标显示
		actionBar.setDisplayHomeAsUpEnabled(true);// 开启图片导航
		ed_AcountNumber = (EditText) findViewById(R.id.account_number_edittxet);
		ed_AcountPwd = (EditText) findViewById(R.id.account_pwd_edittxet);
		ed_AcountBalance = (EditText) findViewById(R.id.account_balance_edittxet);
		rg_AccountFrozen = (RadioGroup) findViewById(R.id.account_frozen_radiogroup);
		rg_AccountMode = (RadioGroup) findViewById(R.id.account_mode_radiogroup);
		btn_AccountCommit = (Button) findViewById(R.id.account_commit);
	}

	private void initEvent() {
		btn_AccountCommit.setOnClickListener(this);
		rg_AccountFrozen.setOnCheckedChangeListener(this);
		rg_AccountMode.setOnCheckedChangeListener(this);
	}

	@Override
	public void onBackPressed() {
		myFinish();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			myFinish();
			break;

		default:
			break;
		}
		return true;
	}

	// 销毁该活动并传值
	private void myFinish() {
		intent = new Intent();
		intent.putExtra("isHasAccount", false);
		setResult(RESULT_OK, intent);
		finish();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.account_commit:
			AccountAsyncTask asyncTask = new AccountAsyncTask();
			asyncTask.execute(accountInsertUrl);
			break;

		default:
			break;
		}
	}

	class AccountAsyncTask extends AsyncTask<String, Void, Boolean> {
		private boolean isSuccess;
		private Builder dialog;

		@Override
		protected Boolean doInBackground(String... params) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("account.accountNumber", ed_AcountNumber.getText()
					.toString().trim());
			hashMap.put("account.accountPwd", ed_AcountPwd.getText().toString()
					.trim());
			hashMap.put("account.accountBalance", ed_AcountBalance.getText()
					.toString().trim());
			SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss.SSSZ");
			hashMap.put("account.accountTime", sdf.format(new Date()));
			hashMap.put("account.accountFrozen", accountFrozen);
			hashMap.put("account.accountMode", accountMode);
			hashMap.put("account.roi.roiId", 1);
			hashMap.put("account.userinfo.userinfoId", userinfoId);
			String json = httpUtil.sendAndGetData(accountInsertUrl, POST,
					hashMap);
			// 解析JSON
			try {
				JSONObject jsonObject = new JSONObject(json);
				isSuccess = jsonObject.getBoolean("isSuccess");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return isSuccess;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			dialog = new AlertDialog.Builder(SettingAccountActivity.this);
			if (result) {
				dialog.setMessage("添加账户成功");
				dialog.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								intent = new Intent(
										SettingAccountActivity.this,
										MainActivity.class);
								SettingAccountActivity.this
										.startActivity(intent);
								dialog.dismiss();
							}
						});
			} else {
				dialog.setMessage("添加账户失败，请稍后再试");
				dialog.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});
			}
			dialog.show();
		}

	}

	@Override
	public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
		RadioButton radioButton = null;
		switch (radioGroup.getId()) {
		case R.id.account_frozen_radiogroup:
			radioButton = (RadioButton) findViewById(checkedId);
			accountFrozen = radioButton.getText().toString();
			break;
		case R.id.account_mode_radiogroup:
			radioButton = (RadioButton) findViewById(checkedId);
			accountMode = radioButton.getText().toString();
			break;
		default:
			break;
		}
	}
}
