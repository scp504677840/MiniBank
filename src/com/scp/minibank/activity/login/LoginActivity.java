package com.scp.minibank.activity.login;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.scp.minibank.R;
import com.scp.minibank.activity.BaseActivity;
import com.scp.minibank.activity.GuideActivity;
import com.scp.minibank.activity.MainActivity;
import com.scp.minibank.utils.HttpUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements OnClickListener {
	// 登录相关
	private EditText etAccount;
	private EditText etPwd;
	private Button btnLogin;
	// 网络相关
	private HttpUtil httpUtil;
	private static final String POST = "POST";
	private String loginUrl = "http://192.168.43.94:8080/MiniBank/loginAction!loginUser.action";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_login);
		initData();
		initView();
		initEvent();
	}

	private void initData() {
		httpUtil = new HttpUtil();
	}

	private void initView() {
		etAccount = (EditText) findViewById(R.id.login_account_edittext);
		etPwd = (EditText) findViewById(R.id.login_password_edittext);
		btnLogin = (Button) findViewById(R.id.login_tologin_button);
	}

	private void initEvent() {
		btnLogin.setOnClickListener(this);
	}

	// 获取文本输入框内容
	private String getEditText(EditText editText) {
		String temp = editText.getText().toString().trim();
		if (!"".equals(temp)) {
			return temp;
		}
		return null;
	}

	// 返回键
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(LoginActivity.this, GuideActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_tologin_button:
			btnLogin.setClickable(false);
			String account = getEditText(etAccount);
			String pwd = getEditText(etPwd);
			if (account != null && pwd != null && account.length() == 11
					&& pwd.length() > 7) {
				loginUser(account, pwd);
			} else {
				handler.sendEmptyMessage(0);
			}
			break;

		default:
			break;
		}
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(LoginActivity.this, "请检查及密码是否输入完整", 0).show();
				btnLogin.setClickable(true);
				break;
			case 1:
				Toast.makeText(LoginActivity.this, "用户名或密码错误", 0).show();
				btnLogin.setClickable(true);
				break;

			default:
				break;
			}
		}

	};

	// 执行登录
	private void loginUser(final String account, final String pwd) {
		new Thread() {

			public void run() {
				HashMap<String, Object> hashMap = new HashMap<String, Object>();
				hashMap.put("login.loginName", account);
				hashMap.put("login.loginPwd", pwd);
				String jsonData = httpUtil.sendAndGetData(loginUrl, POST,
						hashMap);
				if (!jsonData.contains("isLogin") && !"".equals(jsonData)
						&& jsonData != null) {
					try {
						JSONObject jsonObject = new JSONObject(jsonData);
						int userinfoId = jsonObject.getInt("userinfoId");
						Intent intent = new Intent(LoginActivity.this,
								MainActivity.class);
						intent.putExtra("userinfoId", userinfoId);
						LoginActivity.this.startActivity(intent);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					handler.sendEmptyMessage(1);
				}
			};
		}.start();
	}

}
