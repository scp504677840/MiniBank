package com.scp.minibank.activity.register;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.scp.minibank.R;
import com.scp.minibank.activity.BaseActivity;
import com.scp.minibank.activity.GuideActivity;
import com.scp.minibank.activity.MainActivity;
import com.scp.minibank.utils.HttpUtil;
import com.thinkland.sdk.sms.SMSCaptcha;
import com.thinkland.sdk.util.BaseData.ResultCallBack;

public class RegisterSendCaptchaActivity extends BaseActivity implements
		ResultCallBack, OnClickListener, OnFocusChangeListener, Runnable {

	private SMSCaptcha smsCaptcha;// 验证码
	private EditText phoneNumber;// 手机号
	private Button registerBtn;// 注册
	private Button sendCaptchaBtn;// 发送验证码
	private EditText getCaptchaEdit;// 填写收到的验证码
	private boolean isSuccess = false;// 是否验证成功
	private EditText password;// 新密码
	private EditText rePassword;// 重复新密码
	private ImageView toolTip;// 验证验证码是否正确
	private ImageView pwdToolTip;// 验证密码是否输入正确
	private ImageView rePwdToolTip;// 验证密码和重复密码是否一致
	private Thread thread;// 处理验证的线程
	private Message message;// 处理验证的消息类
	// 常量
	private int verify = VERIFY_PHONE;// 验证默认码
	private static final int VERIFY_PHONE = 0;// 验证手机号
	private static final int VERIFY_CAPTCHA = 1;// 验证验证码
	private static final int VERIFY_PWD = 2;// 验证新密码
	private static final int VERIFY_REPWD = 3;// 验证重复密码
	// 网络相关
	private HttpUtil httpUtil;
	private String POST = "POST";// POST请求
	private String GET = "GET";// GET请求
	private String isRegisterPhoneNumberUrl = "http://192.168.43.94:8080/MiniBank/loginAction!loginFindNumber.action";// 验证手机号是否被注册链接
	private String RegisterPhoneNumberInsertUrl = "http://192.168.43.94:8080/MiniBank/loginAction!loginInsert.action";// 验证手机号是否被注册链接
	private boolean isRegister = false;// 手机号是否已经注册
	private boolean results = false;// 验证码验证返回结果

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_sendcaptcha);
		initData();
		initView();
		initEvent();
	}

	private void initData() {
		smsCaptcha = SMSCaptcha.getInstance();
		httpUtil = new HttpUtil();
	}

	private void initView() {
		phoneNumber = (EditText) findViewById(R.id.register_sendcaptcha_edittext);
		registerBtn = (Button) findViewById(R.id.register_sendcaptcha_nextBtn);
		sendCaptchaBtn = (Button) findViewById(R.id.register_sendcaptcha_sendBtn);
		getCaptchaEdit = (EditText) findViewById(R.id.register_sendcaptcha_getcaptcha);
		password = (EditText) findViewById(R.id.register_sendcaptcha_password);
		rePassword = (EditText) findViewById(R.id.register_sendcaptcha_repassword);
		toolTip = (ImageView) findViewById(R.id.register_sendcaptcha_getcaptcha_toolTipIV);
		pwdToolTip = (ImageView) findViewById(R.id.register_sendcaptcha_password_toolTipIV);
		rePwdToolTip = (ImageView) findViewById(R.id.register_sendcaptcha_repassword_toolTipIV);
	}

	private void initEvent() {
		registerBtn.setOnClickListener(this);
		sendCaptchaBtn.setOnClickListener(this);
		getCaptchaEdit.setOnFocusChangeListener(this);
		password.setOnFocusChangeListener(this);
		rePassword.setOnFocusChangeListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.register_sendcaptcha_sendBtn:
			if (!"".equals(phoneNumber.getText().toString().trim()
					.replace("\\s*", ""))
					&& phoneNumber.getText().toString().trim()
							.replace("\\s*", "").length() == 11) {
				if (!isRegisterPhoneNumber()) {
					smsCaptcha.sendCaptcha(phoneNumber.getText().toString()
							.trim(), this);
					countDownTimer.start();
				} else {
					Toast.makeText(RegisterSendCaptchaActivity.this, "手机号已经存在",
							0).show();
				}
			} else {
				Toast.makeText(RegisterSendCaptchaActivity.this, "请填正确写手机号", 0)
						.show();
			}
			break;
		case R.id.register_sendcaptcha_nextBtn:
			if (isSuccess && isNull(phoneNumber) && isNull(getCaptchaEdit)
					&& isNull(password) && isNull(rePassword)) {
				// 完成注册
				registerIng();
			} else {
				Toast.makeText(this, "请检查未完成项！", 0).show();
			}
			break;

		default:
			break;
		}
	}

	// 注册账号
	private void registerIng() {
		// 提交至网络
		new Thread() {
			public void run() {
				// 获取手机号及密码
				final String phoneNo = phoneNumber.getText().toString().trim()
						.replace("\\s*", "");
				final String passwords = password.getText().toString().trim()
						.replace("\\s*", "");
				HashMap<String, Object> hashMap = new HashMap<String, Object>();
				hashMap.put("login.loginName", phoneNo);
				hashMap.put("login.loginPwd", passwords);
				String jsonData = httpUtil.sendAndGetData(
						RegisterPhoneNumberInsertUrl, POST, hashMap);
				try {
					JSONObject jsonObject = new JSONObject(jsonData);
					if (jsonObject.getString("isRegister").equals("YES")) {
						isRegister = true;
						Intent intent = new Intent(
								RegisterSendCaptchaActivity.this,
								MainActivity.class);
						intent.putExtra("phoneNumber", phoneNo);
						RegisterSendCaptchaActivity.this.startActivity(intent);
					} else {
						isRegister = false;
						Toast.makeText(RegisterSendCaptchaActivity.this,
								"注册失败", 0).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			};
		}.start();
	}

	// 验证验证码是否正确
	private boolean isCaptcha() {
		String captcha = getCaptchaEdit.getText().toString().trim()
				.replace("\\s*", "");
		String phoneNo = phoneNumber.getText().toString().trim()
				.replace("\\s*", "");
		smsCaptcha.commitCaptcha(phoneNo, captcha, new ResultCallBack() {

			@Override
			public void onResult(int code, String reason, String result) {
				switch (code) {
				case 0:
					isSuccess = true;
					results = true;
					break;

				default:
					isSuccess = false;
					break;
				}
				verify = VERIFY_CAPTCHA;
				thread = new Thread(RegisterSendCaptchaActivity.this);
				thread.start();
			}
		});
		return results;
	}

	// 验证手机号是否被注册
	@SuppressWarnings("static-access")
	private boolean isRegisterPhoneNumber() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					HashMap<String, Object> hashMap = new HashMap<String, Object>();
					hashMap.put("login.loginName", phoneNumber.getText()
							.toString());
					String jsonData = httpUtil.sendAndGetData(
							isRegisterPhoneNumberUrl, POST, hashMap);
					JSONObject jsonObject = new JSONObject(jsonData);
					if (jsonObject.getString("isRegister").equals("YES")) {
						isRegister = true;
					} else {
						isRegister = false;
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}).start();
		try {
			new Thread().sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return isRegister;
	}

	// 验证文本编辑框是否为空
	private boolean isNull(EditText editText) {
		if ("".equals(editText.getText().toString().trim())) {
			return false;
		}
		return true;
	}

	// 倒计时
	CountDownTimer countDownTimer = new CountDownTimer(30000, 1000) {

		@Override
		public void onTick(long millisUntilFinished) {
			phoneNumber.setEnabled(false);
			sendCaptchaBtn.setClickable(false);
			sendCaptchaBtn.setText(millisUntilFinished / 1000 + "秒后重发");
		}

		@Override
		public void onFinish() {
			phoneNumber.setEnabled(true);
			sendCaptchaBtn.setClickable(true);
			sendCaptchaBtn.setText("获取验证码");
		}
	};

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		switch (v.getId()) {
		case R.id.register_sendcaptcha_getcaptcha:
			if (!hasFocus) {
				isCaptcha();
			}
			break;
		case R.id.register_sendcaptcha_password:
			if (!hasFocus) {
				if (password.getText().toString().trim().length() > 8) {
					isSuccess = true;
				} else {
					isSuccess = false;
				}
				verify = VERIFY_PWD;
				thread = new Thread(RegisterSendCaptchaActivity.this);
				thread.start();
			}
			break;
		case R.id.register_sendcaptcha_repassword:
			if (!hasFocus) {
				if (rePassword.getText().toString().trim()
						.equals(password.getText().toString().trim())
						&& !"".equals(rePassword.getText().toString().trim())) {
					isSuccess = true;
				} else {
					isSuccess = false;
				}
				verify = VERIFY_REPWD;
				thread = new Thread(RegisterSendCaptchaActivity.this);
				thread.start();
			}
			break;

		default:
			break;
		}
	}

	/**
	 * code:返回码: 服务器: 0 成功; 1 错误; 本地: -2 本地网络异常; -3 服务器网络异常;-4 解析错误;-5初始化异常
	 * reason:返回信息 成功或错误原因. result:返回结果,JSON格式.错误或者无返回值时为空.
	 */
	@Override
	public void onResult(int code, String reason, String result) {
		switch (code) {
		case 0:
			Toast.makeText(this, "验证码已经发送，请注意查收！", 0).show();
			break;
		case -1:
			Toast.makeText(this, "请检查手机号是否正确，稍后再试！", 0).show();
			break;
		case -2:
			Toast.makeText(this, "本地网络异常,请稍后再试！", 0).show();
			break;
		case -3:
			Toast.makeText(this, "服务器网络异常,请稍后再试！", 0).show();
			break;
		case -4:
			Toast.makeText(this, "初始化异常,请稍后再试！", 0).show();
			break;
		case -5:
			Toast.makeText(this, "成功" + result, 0).show();
			break;

		default:
			break;
		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case VERIFY_PHONE:
				break;
			case VERIFY_CAPTCHA:
				if (isSuccess) {
					toolTip.setImageResource(R.drawable.register_sendcaptcha_success);
				} else {
					toolTip.setImageResource(R.drawable.register_sendcaptcha_fail);
				}
				break;
			case VERIFY_PWD:
				if (isSuccess) {
					pwdToolTip
							.setImageResource(R.drawable.register_sendcaptcha_success);
				} else {
					pwdToolTip
							.setImageResource(R.drawable.register_sendcaptcha_fail);
				}
				break;
			case VERIFY_REPWD:
				if (isSuccess) {
					rePwdToolTip
							.setImageResource(R.drawable.register_sendcaptcha_success);
				} else {
					rePwdToolTip
							.setImageResource(R.drawable.register_sendcaptcha_fail);
				}
				break;

			default:
				break;
			}
		}

	};

	// 返回键
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(RegisterSendCaptchaActivity.this,
				GuideActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void run() {
		message = new Message();
		message.what = verify;
		handler.sendMessage(message);
	}

}
