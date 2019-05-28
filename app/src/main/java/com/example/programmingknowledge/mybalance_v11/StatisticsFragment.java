package com.example.programmingknowledge.mybalance_v11;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
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

        int MAX_PAGE=3;
        Fragment cur_fragment=new Fragment();

        public StatisticsFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            setHasOptionsMenu(true);
            View rootView = inflater.inflate(R.layout.fragment_statistics, container, false);

            chart = (ColumnChartView) rootView.findViewById(R.id.chart);
            //fragment_statistics.setOnValueTouchListener(new ValueTouchListener());   //차트를 선택했을때 값이 팝업으로 뜨는 함수
            chart.setZoomEnabled(false);

            /*ViewPager pager=(ViewPager)rootView.findViewById(R.id.chart_viewpager);
            //캐싱을 해놓을 프래그먼트 개수
            pager.setOffscreenPageLimit(3);
            //getSupportFragmentManager로 프래그먼트 참조가능
            //ChartPagerAdapter adapter = new ChartPagerAdaptergetSupportFragmentManager());
            ChartPagerAdapter adapter = new ChartPagerAdapter(null);
            adapter.addItem(inflater.inflate(R.layout.fragment_chart));
            adapter.addItem(fragment2);
            adapter.addItem(fragment3);
            pager.setAdapter(adapter);*/

            //db읽기
            DBHelper helper = new DBHelper(container.getContext());
            SQLiteDatabase db = helper.getWritableDatabase();

            generateStackedData(db);
            setAverageTime(db);

            return rootView;
        }

     /*
    //어댑터 안에서 각각의 아이템을 데이터로서 관리한다
    class ChartPagerAdapter extends PagerAdapter {
        LayoutInflater inflater;

        public ChartPagerAdapter(LayoutInflater inflater) {
            // TODO Auto-generated constructor stub

            //전달 받은 LayoutInflater를 멤버변수로 전달
            this.inflater=inflater;
        }

        //PagerAdapter가 가지고 있는 View의 개수를 리턴
        //보통 보여줘야하는 이미지 배열 데이터의 길이를 리턴
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return 3; //일단 3이라고 적어둠
        }

        //ViewPager가 현재 보여질 Item(View객체)를 생성할 필요가 있는 때 자동으로 호출
        //쉽게 말해, 스크롤을 통해 현재 보여져야 하는 View를 만들어냄.
        //첫번째 파라미터 : ViewPager
        //두번째 파라미터 : ViewPager가 보여줄 View의 위치(가장 처음부터 0,1,2,3...)
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub

            View chartview=null;

            //새로운 View 객체를 Layoutinflater를 이용해서 생성
            chartview= inflater.inflate(R.layout.fragment_chart, null);

            //chartview 내부 객체들 값 세팅하는 부분..?

            //ViewPager에 만들어 낸 View 추가
            container.addView(chartview);

            //Image가 세팅된 View를 리턴
            return chartview;
        }

        //화면에 보이지 않은 View는 파괴를 해서 메모리를 관리함.
        //첫번째 파라미터 : ViewPager
        //두번째 파라미터 : 파괴될 View의 인덱스(가장 처음부터 0,1,2,3...)
        //세번째 파라미터 : 파괴될 객체(더 이상 보이지 않은 View 객체)
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub

            //ViewPager에서 보이지 않는 View는 제거
            //세번째 파라미터가 View 객체 이지만 데이터 타입이 Object여서 형변환 실시
            container.removeView((View)object);

        }

        //instantiateItem() 메소드에서 리턴된 Ojbect가 View가  맞는지 확인하는 메소드
        @Override
        public boolean isViewFromObject(View v, Object obj) {
            // TODO Auto-generated method stub
            return v==obj;
        }
    }
    */
    
    private void generateStackedData(SQLiteDatabase db) {
        int categoryNum = 5;   //스택의 개수
        int dayNum = 7;   //일주일(7일) 그래프
        // Column can have many stacked subcolumns, here I use 4 stack subcolumn in each of 4 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        List<AxisValue> axisValues = new ArrayList<AxisValue>();    //'월화수목금토일' 표시 그래프에 뜨게
        List<String> weeks = new ArrayList<String>(Arrays.asList("월","화","수","목","금","토","일"));
        for(int i=0; i<dayNum; i++){
            axisValues.add(i, new AxisValue(i).setLabel(weeks.get(i)));
            columns.add(null);
        }
        for (int i=6; i>=0; i--) {
            values = new ArrayList<SubcolumnValue>();
            Cursor cursor = db.rawQuery("select * from tb_dailybalance where week='"+weeks.get(i)+"' ", null);
            if (cursor.getCount()!=0){
                cursor.moveToNext();
                    values.add(new SubcolumnValue(cursor.getFloat(cursor.getColumnIndex("sleep")), getResources().getColor(R.color.sleep)));
                    values.add(new SubcolumnValue(cursor.getFloat(cursor.getColumnIndex("work")), getResources().getColor(R.color.work)));
                    values.add(new SubcolumnValue(cursor.getFloat(cursor.getColumnIndex("study")), getResources().getColor(R.color.study)));
                    values.add(new SubcolumnValue(cursor.getFloat(cursor.getColumnIndex("exercise")), getResources().getColor(R.color.exercise)));
                    values.add(new SubcolumnValue(cursor.getFloat(cursor.getColumnIndex("leisure")), getResources().getColor(R.color.leisure)));
                    values.add(new SubcolumnValue(cursor.getFloat(cursor.getColumnIndex("other")), getResources().getColor(R.color.lightGray)));
            }
            Column column = new Column(values);
            //column.setHasLabels(hasLabels);
            //column.setHasLabelsOnlyForSelected(hasLabelForSelected);
            columns.set(i,column);
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
