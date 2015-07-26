package com.scp.minibank.adpter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * ����ҳ������
 * 
 * @author �δ���
 *
 */
public class PagerAdapter extends android.support.v4.view.PagerAdapter {

	private Context context;
	private List<ImageView> list;

	public PagerAdapter(Context context, List<ImageView> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewGroup) container).removeView(list.get(position));
	}

	// ����View
	@Override
	public Object instantiateItem(View container, int position) {
		((ViewGroup) container).addView(list.get(position));
		return list.get(position);
	}

	// �жϵ�ǰ��View�Ƿ���������Ҫ��View
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0 == arg1);
	}
}
