package com.example.programmingknowledge.mybalance_v11;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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


            //db읽기
            DBHelper helper = new DBHelper(container.getContext());
            SQLiteDatabase db = helper.getWritableDatabase();

            //몇번째 주,월인지 구하는 코드
            TextView averageNotice = rootView.findViewById(R.id.averageNotice);
            //averageNotice.setText();

            generateStackedData(db);
            setAverageTime(db);

            return rootView;
        }

    private void generateStackedData(SQLiteDatabase db) {
        int categoryNum = 5;   //스택의 개수
        int dayNum = 7;   //일주일(7일) 그래프
        // Column can have many stacked subcolumns, here I use 4 stack subcolumn in each of 4 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        List<AxisValue> axisValues = new ArrayList<AxisValue>();    //'월화수목금토일' 표시 그래프에 뜨게
        List<String> weeks = new ArrayList<String>(Arrays.asList("월","화","수","목","금","토","일"));
        for (int i = 0; i < dayNum; ++i) {
            values = new ArrayList<SubcolumnValue>();
            Cursor cursor = db.rawQuery("select * from tb_dailybalance where week='"+weeks.get(i)+"' ", null);
            if (cursor.getCount()!=0){
                values.add(new SubcolumnValue(cursor.getColumnIndex("sleep"), getResources().getColor(R.color.sleep)));
                values.add(new SubcolumnValue(cursor.getColumnIndex("work"), getResources().getColor(R.color.work)));
                values.add(new SubcolumnValue(cursor.getColumnIndex("study"), getResources().getColor(R.color.study)));
                values.add(new SubcolumnValue(cursor.getColumnIndex("exercise"), getResources().getColor(R.color.exercise)));
                values.add(new SubcolumnValue(cursor.getColumnIndex("leisure"), getResources().getColor(R.color.leisure)));
                values.add(new SubcolumnValue(cursor.getColumnIndex("other"), getResources().getColor(R.color.lightGray)));
            }
            Column column = new Column(values);
            //column.setHasLabels(hasLabels);
            //column.setHasLabelsOnlyForSelected(hasLabelForSelected);
            columns.add(column);

            axisValues.add(i, new AxisValue(i).setLabel(weeks.get(i)));
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

    private void setAverageTime(SQLiteDatabase db){
        //int a = Calendar.;
        Cursor cursor = db.rawQuery("select * from tb_dailybalance where date='2019-05-14' ", null);

    }
}
