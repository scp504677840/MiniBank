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
import android.view.Window;
import android.widget.TextView;

/**
 * 转账人管理
 * 
 * @author Administrator
 *
 */
public class MainTransferPersonActivity extends BaseActivity {
	private ActionBar actionBar;
	private TextView accountNumber;
	// 网络相关
	private HttpUtil httpUtil;
	private String POST = "POST";
	private String personUrl = "http://192.168.43.94:8080/MiniBank/tradingAction!tradingAll.action";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_transfer_person);
		initData();
		initView();
	}

	private void initData() {
		httpUtil = new HttpUtil();
	}

	private void initView() {
		actionBar = getActionBar();
		actionBar.setTitle("转账人管理");
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(true);
		accountNumber = (TextView) findViewById(R.id.main_transferPerson_accountNo_text);
		new PersonAsyncTask().execute(personUrl);
	}

	class PersonAsyncTask extends AsyncTask<String, Void, String> {

		private String accountNo;

		@Override
		protected String doInBackground(String... params) {
			String json = httpUtil.getHttpData(params[0], POST, null);
			// 解析JSON
			try {
				JSONObject jsonObject = new JSONObject(json);
				accountNo = jsonObject.getString("accountNumber");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return accountNo;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			accountNumber.setText(result);
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
}
