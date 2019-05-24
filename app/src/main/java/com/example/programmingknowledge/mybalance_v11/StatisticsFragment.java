package com.example.programmingknowledge.mybalance_v11;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.view.ColumnChartView;

public class StatisticsFragment extends Fragment {
    /*public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_column_chart,container,false);
    }
*/
        private static final int DEFAULT_DATA = 0;

        private ColumnChartView chart;
        private ColumnChartData data;
        private boolean hasAxes = true;
        private boolean hasAxesNames = true;
        private boolean hasLabels = true;
        private boolean hasLabelForSelected = true;
        private int dataType = DEFAULT_DATA;


        public StatisticsFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            setHasOptionsMenu(true);
            View rootView = inflater.inflate(R.layout.fragment_statistics, container, false);

            chart = (ColumnChartView) rootView.findViewById(R.id.chart);
            //fragment_statistics.setOnValueTouchListener(new ValueTouchListener());   //차트를 선택했을때 값이 팝업으로 뜨는 함수
            chart.setZoomEnabled(false);

            generateStackedData();

            return rootView;
        }

    private void generateStackedData() {
        int categoryNum = 5;   //스택의 개수
        int dayNum = 7;   //일주일(7일) 그래프
        // Column can have many stacked subcolumns, here I use 4 stack subcolumn in each of 4 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        List<AxisValue> axisValues = new ArrayList<AxisValue>();    //'월화수목금토일' 표시 그래프에 뜨게
        String[] months = new String[]{"월","화","수","목","금","토","일"};
        for (int i = 0; i < dayNum; ++i) {
            values = new ArrayList<SubcolumnValue>();
                values.add(new SubcolumnValue(8, getResources().getColor(R.color.sleep)));
                values.add(new SubcolumnValue(8, getResources().getColor(R.color.work)));
                values.add(new SubcolumnValue(3, getResources().getColor(R.color.study)));
                values.add(new SubcolumnValue(2, getResources().getColor(R.color.exercise)));
                values.add(new SubcolumnValue(2, getResources().getColor(R.color.leisure)));
                values.add(new SubcolumnValue(1, getResources().getColor(R.color.lightGray)));

            Column column = new Column(values);
            //column.setHasLabels(hasLabels);
            //column.setHasLabelsOnlyForSelected(hasLabelForSelected);
            columns.add(column);

            axisValues.add(i, new AxisValue(i).setLabel(months[i]));
        }


        data = new ColumnChartData(columns);

        // Set stacked flag.
        data.setStacked(true);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);

            axisX.setValues(axisValues);

            axisX.setTextColor(Color.BLACK);
            axisY.setTextColor(Color.BLACK);

            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        chart.setColumnChartData(data);
    }
}
