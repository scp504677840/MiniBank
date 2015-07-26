package com.scp.minibank.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * �������ӹ�����
 * 
 * @author �δ���
 *
 */
public class HttpUtil {

	/**
	 * Get������������
	 * 
	 * @param url
	 * @return
	 */
	public static String requestGet(String url) {
		HttpGet geturl = new HttpGet(url);
		HttpClient client = new DefaultHttpClient();
		try {
			HttpResponse res = client.execute(geturl);
			HttpEntity entity = res.getEntity();
			String rst = EntityUtils.toString(entity);
			return rst;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * POST������������
	 * 
	 * @param url
	 * @return
	 */
	public static String requestPost(String url, List<BasicNameValuePair> params) {
		HttpPost post = new HttpPost(url);
		HttpClient client = new DefaultHttpClient();
		try {
			post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse res = client.execute(post);
			HttpEntity entity = res.getEntity();
			String rst = EntityUtils.toString(entity);
			return rst;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * ��������
	 * 
	 * @param url
	 *            �����ַ
	 * @param method
	 *            ����ʽGET����POST
	 * @param hashMap
	 *            ������
	 */
	public void sendRequestWithHttpURLConnection(final String url,
			String method, final HashMap<String, Object> hashMap) {
		HttpURLConnection connection = null;
		try {
			URL mUrl = new URL(url);
			connection = (HttpURLConnection) mUrl.openConnection();
			connection.setRequestMethod(method);
			PrintWriter out = new PrintWriter(new OutputStreamWriter(
					connection.getOutputStream(), "utf-8"));
			StringBuffer dataBuffer = new StringBuffer();// ƴ�Ӳ���
			Set<String> keySet = hashMap.keySet();// ��ȡ���е�key
			Iterator<String> iterator = keySet.iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				dataBuffer.append(key + "=" + hashMap.get(key) + "&");
			}
			dataBuffer.delete(dataBuffer.length() - 1, dataBuffer.length());
			out.print(dataBuffer);
			out.flush();
			out.close();
			connection.setConnectTimeout(8000);
			connection.setReadTimeout(8000);
			InputStream in = connection.getInputStream();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	/**
	 * ��ȡ����
	 * 
	 * @param url
	 *            �����ַ
	 * @param method
	 *            ����ʽGET����POST
	 * @param hashMap
	 *            �����������ΪNULL�Ļ���ʾ���������
	 * @return JSON����
	 */
	public String getHttpData(final String url, String method,
			HashMap<String, Object> hashMap) {
		StringBuilder response = null;
		HttpURLConnection connection = null;
		try {
			URL mUrl = new URL(url);
			connection = (HttpURLConnection) mUrl.openConnection();
			connection.setRequestMethod(method);
			if (hashMap != null) {
				PrintWriter out = new PrintWriter(new OutputStreamWriter(
						connection.getOutputStream(), "utf-8"));
				StringBuffer dataBuffer = new StringBuffer();// ƴ�Ӳ���
				Set<String> keySet = hashMap.keySet();// ��ȡ���е�key
				Iterator<String> iterator = keySet.iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					dataBuffer.append(key + "=" + hashMap.get(key) + "&");
				}
				dataBuffer.delete(dataBuffer.length() - 1, dataBuffer.length());
				out.print(dataBuffer);
				out.flush();
				out.close();
			}
			connection.setConnectTimeout(8000);
			connection.setReadTimeout(8000);
			InputStream in = connection.getInputStream();
			// ����Ի�ȡ�������������ж�ȡ
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return response.toString();
	}

	/**
	 * ���Ͳ���ȡ����
	 * 
	 * @param url
	 *            �����ַ
	 * @param method
	 *            ����ʽ
	 * @param hashMap
	 *            ������
	 * @return JSON����
	 */
	public String sendAndGetData(String url, String method,
			final HashMap<String, Object> hashMap) {
		StringBuilder response = null;
		HttpURLConnection connection = null;
		try {
			URL mUrl = new URL(url);
			connection = (HttpURLConnection) mUrl.openConnection();
			connection.setRequestMethod(method);
			connection.setUseCaches(false);
			PrintWriter out = new PrintWriter(new OutputStreamWriter(
					connection.getOutputStream(), "utf-8"));
			StringBuffer dataBuffer = new StringBuffer();// ƴ�Ӳ���
			Set<String> keySet = hashMap.keySet();// ��ȡ���е�key
			Iterator<String> iterator = keySet.iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				dataBuffer.append(key + "=" + hashMap.get(key) + "&");
			}
			dataBuffer.delete(dataBuffer.length() - 1, dataBuffer.length());
			out.print(dataBuffer);
			out.flush();
			out.close();
			connection.setConnectTimeout(8000);
			connection.setReadTimeout(8000);
			InputStream in = connection.getInputStream();
			// ����Ի�ȡ�������������ж�ȡ
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return response.toString();
	}
}
