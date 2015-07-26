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

	private ValueShape shape = ValueShape.CIRCLE;// CIRCLE:圆形;DIAMOND:菱形;SQUARE:正方形
	private boolean isCubic = true;// 是否是曲线
	private boolean isFilled = false;// 是否填充
	private boolean hasLabels = true;// 是否显示数值
	private boolean hasLabelsOnlyForSelected = false;// 仅仅对选中的点进行显示数值
	private boolean hasLines = true;// 显示线
	private boolean hasPoints = true;// 显示点
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


	// 初始化组件
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

	// 绘制数据
	private void generateData() {
		List<Line> lines = new ArrayList<Line>();
		for (int i = 0; i < 1; i++) {
			// 数据源
			List<PointValue> values = new ArrayList<PointValue>();
			for (int j = 0; j < listLevel.size(); j++) {
				values.add(new PointValue(j, listLevel.get(j)));
			}
			// 定义行
			Line line = new Line(values);
			// 设置行的颜色
			line.setColor(ChartUtils.COLORS[i]);
			// 形状
			line.setShape(shape);
			// 是否是曲线
			line.setCubic(isCubic);
			// 填充
			line.setFilled(isFilled);
			// 标签
			line.setHasLabels(hasLabels);
			// 仅仅对选中的设置标签
			line.setHasLabelsOnlyForSelected(hasLabelsOnlyForSelected);
			// 折线
			line.setHasLines(hasLines);
			// 点
			line.setHasPoints(hasPoints);
			lines.add(line);
		}

		data = new LineChartData();
		data.setLines(lines);

		if (hasAxes) {
			Axis axisX = new Axis();
			Axis axisY = new Axis().setHasLines(true);
			if (hasAxesNames) {
				axisX.setName("币种");
				axisY.setName("买入价");
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

	// 触摸事件
	@Override
	public void onValueDeselected() {
	}

	@Override
	public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
	}

}
