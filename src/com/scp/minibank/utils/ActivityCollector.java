package com.scp.minibank.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

/**
 * ���������
 * 
 * @author
 *
 */
public class ActivityCollector {
	public static List<Activity> activities = new ArrayList<Activity>();

	/**
	 * ��ӻ
	 * 
	 * @param activity
	 *            �
	 */
	public static void addActivity(Activity activity) {
		activities.add(activity);
	}

	/**
	 * ���һ���
	 * 
	 * @param activity
	 *            �
	 */
	public static void removeActivity(Activity activity) {
		activities.remove(activity);
	}

	/**
	 * �������л
	 */
	public static void finishAll() {
		for (Activity activity : activities) {
			if (!activity.isFinishing()) {
				activity.finish();
			}
		}
	}

}
