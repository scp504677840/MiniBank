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
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * 转账结果查询
 * 
 * @author 宋春鹏
 *
 */
public class MainTransferResultActivity extends BaseActivity {
	private ActionBar actionBar;
	private TextView accountName;
	private TextView time;
	private TextView isSuccess;
	// 网络相关
	private HttpUtil httpUtil;
	private String POST = "POST";
	private static final String transferUrl = "http://192.168.43.94:8080/MiniBank/tradingAction!tradingDel.action";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_transfer_result);
		initData();
		initView();
	}

	private void initData() {
		httpUtil = new HttpUtil();
	}

	private void initView() {
		actionBar = getActionBar();
		actionBar.setTitle("转账结果查询");
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(true);
		accountName = (TextView) findViewById(R.id.transfer_accountName);
		time = (TextView) findViewById(R.id.transfer_time);
		isSuccess = (TextView) findViewById(R.id.transfer_result_success);
		new TransferAsyncTask().execute(transferUrl);
	}

	@SuppressWarnings("rawtypes")
	class TransferAsyncTask extends AsyncTask<String, Void, List> {

		@SuppressWarnings({ "unchecked" })
		@Override
		protected List doInBackground(String... params) {
			List list = new ArrayList();
			String json = httpUtil.getHttpData(transferUrl, POST, null);
			// 解析JSON
			try {
				JSONObject jsonObject = new JSONObject(json);
				String tAccount = jsonObject.getString("tradingAccount");
				String tTime = jsonObject.getString("tradingTime");
				boolean tradingIs = jsonObject.getBoolean("tradingIs");
				list.add(tAccount);
				list.add(tTime);
				list.add(tradingIs);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return list;
		}

		@Override
		protected void onPostExecute(List result) {
			super.onPostExecute(result);
			accountName.setText(result.get(0).toString());
			time.setText(result.get(1).toString());
			if((Boolean) result.get(2)){
				isSuccess.setText("成功");
			}else{
				isSuccess.setText("失败");
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
}
