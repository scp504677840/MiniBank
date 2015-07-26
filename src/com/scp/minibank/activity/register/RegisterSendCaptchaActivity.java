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

	private SMSCaptcha smsCaptcha;// ��֤��
	private EditText phoneNumber;// �ֻ���
	private Button registerBtn;// ע��
	private Button sendCaptchaBtn;// ������֤��
	private EditText getCaptchaEdit;// ��д�յ�����֤��
	private boolean isSuccess = false;// �Ƿ���֤�ɹ�
	private EditText password;// ������
	private EditText rePassword;// �ظ�������
	private ImageView toolTip;// ��֤��֤���Ƿ���ȷ
	private ImageView pwdToolTip;// ��֤�����Ƿ�������ȷ
	private ImageView rePwdToolTip;// ��֤������ظ������Ƿ�һ��
	private Thread thread;// ������֤���߳�
	private Message message;// ������֤����Ϣ��
	// ����
	private int verify = VERIFY_PHONE;// ��֤Ĭ����
	private static final int VERIFY_PHONE = 0;// ��֤�ֻ���
	private static final int VERIFY_CAPTCHA = 1;// ��֤��֤��
	private static final int VERIFY_PWD = 2;// ��֤������
	private static final int VERIFY_REPWD = 3;// ��֤�ظ�����
	// �������
	private HttpUtil httpUtil;
	private String POST = "POST";// POST����
	private String GET = "GET";// GET����
	private String isRegisterPhoneNumberUrl = "http://192.168.43.94:8080/MiniBank/loginAction!loginFindNumber.action";// ��֤�ֻ����Ƿ�ע������
	private String RegisterPhoneNumberInsertUrl = "http://192.168.43.94:8080/MiniBank/loginAction!loginInsert.action";// ��֤�ֻ����Ƿ�ע������
	private boolean isRegister = false;// �ֻ����Ƿ��Ѿ�ע��
	private boolean results = false;// ��֤����֤���ؽ��

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
					Toast.makeText(RegisterSendCaptchaActivity.this, "�ֻ����Ѿ�����",
							0).show();
				}
			} else {
				Toast.makeText(RegisterSendCaptchaActivity.this, "������ȷд�ֻ���", 0)
						.show();
			}
			break;
		case R.id.register_sendcaptcha_nextBtn:
			if (isSuccess && isNull(phoneNumber) && isNull(getCaptchaEdit)
					&& isNull(password) && isNull(rePassword)) {
				// ���ע��
				registerIng();
			} else {
				Toast.makeText(this, "����δ����", 0).show();
			}
			break;

		default:
			break;
		}
	}

	// ע���˺�
	private void registerIng() {
		// �ύ������
		new Thread() {
			public void run() {
				// ��ȡ�ֻ��ż�����
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
								"ע��ʧ��", 0).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			};
		}.start();
	}

	// ��֤��֤���Ƿ���ȷ
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

	// ��֤�ֻ����Ƿ�ע��
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

	// ��֤�ı��༭���Ƿ�Ϊ��
	private boolean isNull(EditText editText) {
		if ("".equals(editText.getText().toString().trim())) {
			return false;
		}
		return true;
	}

	// ����ʱ
	CountDownTimer countDownTimer = new CountDownTimer(30000, 1000) {

		@Override
		public void onTick(long millisUntilFinished) {
			phoneNumber.setEnabled(false);
			sendCaptchaBtn.setClickable(false);
			sendCaptchaBtn.setText(millisUntilFinished / 1000 + "����ط�");
		}

		@Override
		public void onFinish() {
			phoneNumber.setEnabled(true);
			sendCaptchaBtn.setClickable(true);
			sendCaptchaBtn.setText("��ȡ��֤��");
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
	 * code:������: ������: 0 �ɹ�; 1 ����; ����: -2 ���������쳣; -3 �����������쳣;-4 ��������;-5��ʼ���쳣
	 * reason:������Ϣ �ɹ������ԭ��. result:���ؽ��,JSON��ʽ.��������޷���ֵʱΪ��.
	 */
	@Override
	public void onResult(int code, String reason, String result) {
		switch (code) {
		case 0:
			Toast.makeText(this, "��֤���Ѿ����ͣ���ע����գ�", 0).show();
			break;
		case -1:
			Toast.makeText(this, "�����ֻ����Ƿ���ȷ���Ժ����ԣ�", 0).show();
			break;
		case -2:
			Toast.makeText(this, "���������쳣,���Ժ����ԣ�", 0).show();
			break;
		case -3:
			Toast.makeText(this, "�����������쳣,���Ժ����ԣ�", 0).show();
			break;
		case -4:
			Toast.makeText(this, "��ʼ���쳣,���Ժ����ԣ�", 0).show();
			break;
		case -5:
			Toast.makeText(this, "�ɹ�" + result, 0).show();
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

	// ���ؼ�
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
