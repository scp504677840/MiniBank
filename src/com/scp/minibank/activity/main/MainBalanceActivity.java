package com.scp.minibank.activity.main;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.TextView;

/**
 * ”‡∂Ó
 * 
 * @author ÀŒ¥∫≈Ù
 *
 */
public class MainBalanceActivity extends BaseActivity implements
		OnClickListener {
	private ActionBar actionBar;
	private TextView accountName;
	private TextView balance;
	private Button okBtn;
	// Õ¯¬Áœ‡πÿ
	private HttpUtil httpUtil;
	private String balanceUrl = "http://192.168.43.94:8080/MiniBank/accountAction!accountBalance.action";
	private String POST = "POST";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_balance);
		initData();
		initView();
		initEvent();
	}

	private void initData() {
		httpUtil = new HttpUtil();
	}

	private void initView() {
		actionBar = getActionBar();
		actionBar.setTitle("”‡∂Ó≤È—Ø");
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(true);
		accountName = (TextView) findViewById(R.id.main_balance_accountname_text);
		balance = (TextView) findViewById(R.id.main_balance_text);
		okBtn = (Button) findViewById(R.id.main_balance_ok_btn);
		new BalanceAsyncTask().execute(balanceUrl);
	}

	private void initEvent() {
		okBtn.setOnClickListener(this);
	}

	class BalanceAsyncTask extends AsyncTask<String, Void, List<String>> {

		private List<String> balanceNumber = new ArrayList<String>();// ”‡∂Ó

		@Override
		protected List<String> doInBackground(String... params) {
			String httpData = httpUtil.getHttpData(params[0], POST, null);
			// Ω‚ŒˆJSON
			try {
				JSONObject jsonObject = new JSONObject(httpData);
				String accountNumber = jsonObject.getString("accountNumber");
				String accountBalance = jsonObject.getString("accountBalance");
				balanceNumber.add(accountNumber);
				balanceNumber.add(accountBalance);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return balanceNumber;
		}

		@Override
		protected void onPostExecute(List<String> result) {
			super.onPostExecute(result);
			accountName.setText(result.get(0));
			balance.setText(result.get(1));
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
		case R.id.main_balance_ok_btn:
			finish();
			break;

		default:
			break;
		}
	}
}
