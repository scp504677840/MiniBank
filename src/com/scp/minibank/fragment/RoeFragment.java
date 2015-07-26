package com.scp.minibank.fragment;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

import com.scp.minibank.R;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class RoeFragment extends Fragment implements LineChartOnValueSelectListener {
	private View view;
	private Context mContext;
	private LineChartView mChartView;

	private ValueShape shape = ValueShape.CIRCLE;// CIRCLE:Բ��;DIAMOND:����;SQUARE:������
	private boolean isCubic = true;// �Ƿ�������
	private boolean isFilled = false;// �Ƿ����
	private boolean hasLabels = true;// �Ƿ���ʾ��ֵ
	private boolean hasLabelsOnlyForSelected = false;// ������ѡ�еĵ������ʾ��ֵ
	private boolean hasLines = true;// ��ʾ��
	private boolean hasPoints = true;// ��ʾ��
	private LineChartData data;
	private boolean hasAxes = true;
	private boolean hasAxesNames = true;
	private ArrayList<Float> listLevel;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_roe, container, false);
		initView();
		initData();
		initEvent();
		return view;
	}
	
	public RoeFragment(Context context) {
		mContext = context;
	}
	private void initEvent() {
		mChartView.setOnValueTouchListener(this);
	}

	private void initData() {
		generateData();
	}


	// ��ʼ�����
	private void initView() {
		mChartView = (LineChartView) view.findViewById(R.id.chart_linechart);
		listLevel = new ArrayList<Float>();
		listLevel.add(50.00f);
		listLevel.add(55.00f);
		listLevel.add(65.00f);
		listLevel.add(70.00f);
		listLevel.add(75.00f);
		listLevel.add(80.00f);
	}

	// ��������
	private void generateData() {
		List<Line> lines = new ArrayList<Line>();
		for (int i = 0; i < 1; i++) {
			// ����Դ
			List<PointValue> values = new ArrayList<PointValue>();
			for (int j = 0; j < listLevel.size(); j++) {
				values.add(new PointValue(j, listLevel.get(j)));
			}
			// ������
			Line line = new Line(values);
			// �����е���ɫ
			line.setColor(ChartUtils.COLORS[i]);
			// ��״
			line.setShape(shape);
			// �Ƿ�������
			line.setCubic(isCubic);
			// ���
			line.setFilled(isFilled);
			// ��ǩ
			line.setHasLabels(hasLabels);
			// ������ѡ�е����ñ�ǩ
			line.setHasLabelsOnlyForSelected(hasLabelsOnlyForSelected);
			// ����
			line.setHasLines(hasLines);
			// ��
			line.setHasPoints(hasPoints);
			lines.add(line);
		}

		data = new LineChartData();
		data.setLines(lines);

		if (hasAxes) {
			Axis axisX = new Axis();
			Axis axisY = new Axis().setHasLines(true);
			if (hasAxesNames) {
				axisX.setName("����");
				axisY.setName("�����");
			}
			data.setAxisXBottom(axisX);
			data.setAxisYLeft(axisY);
		} else {
			data.setAxisXBottom(null);
			data.setAxisYLeft(null);
		}

		data.setBaseValue(Float.NEGATIVE_INFINITY);
		mChartView.setLineChartData(data);
	}

	// �����¼�
	@Override
	public void onValueDeselected() {
	}

	@Override
	public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
	}

}
