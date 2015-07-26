package com.scp.minibank.activity.main;

import org.json.JSONException;
import org.json.JSONObject;

import com.scp.minibank.R;
import com.scp.minibank.activity.BaseActivity;
import com.scp.minibank.utils.HttpUtil;

import android.app.ActionBar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ����ת��
 * 
 * @author �δ���
 *
 */
public class MainTransferActivity extends BaseActivity implements
		OnClickListener {
	private ActionBar actionBar;
	private EditText userName;
	private EditText accountNumber;
	private Button okBtn;
	// �������
	private HttpUtil httpUtil;
	private String POST = "POST";
	private String transferUrl = "http://192.168.43.94:8080/MiniBank/accountAction!accountTransfer.action";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_transfer);
		initData();
		initView();
		initEvent();
	}

	private void initData() {
		httpUtil = new HttpUtil();
	}

	private void initView() {
		actionBar = getActionBar();
		actionBar.setTitle("����ת��");
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(true);
		userName = (EditText) findViewById(R.id.main_transfer_username_text);
		accountNumber = (EditText) findViewById(R.id.main_transfer_accountNO);
		okBtn = (Button) findViewById(R.id.main_transfer_ok_btn);
	}

	private void initEvent() {
		okBtn.setOnClickListener(this);
	}

	class TransferAsyncTask extends AsyncTask<String, Void, Boolean> {

		private boolean transfer;

		@Override
		protected Boolean doInBackground(String... params) {
			String json = httpUtil.getHttpData(transferUrl, POST, null);
			// ����JSON
			try {
				JSONObject jsonObject = new JSONObject(json);
				transfer = jsonObject.getBoolean("transfer");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return transfer;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (result) {
				Toast.makeText(MainTransferActivity.this, "ת�˳ɹ�", 0).show();
				MainTransferActivity.this.finish();
			}
		}

	}

	@Override
	public void onBackPressed() {
		finish();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;

		default:
			break;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_transfer_ok_btn:
			new TransferAsyncTask().execute(transferUrl);
			break;

		default:
			break;
		}
	}
}
