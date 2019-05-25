package com.example.programmingknowledge.mybalance_v11;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.qap.ctimelineview.TimelineRow;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TimelineFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    TextView dateNow;

    //Create Timeline Rows List
    private ArrayList<TimelineRow> timelineRowsList = new ArrayList<>();
    ArrayAdapter<TimelineRow> myAdapter;

    public static TimelineFragment newInstance(String param1) {
        TimelineFragment fragment = new TimelineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_home,container,false);
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        final DBHelper helper = new DBHelper(container.getContext());

        //위에 날짜 띄우기
        dateNow = (TextView)view.findViewById(R.id.nowDate);
        String date = mParam1.substring(5);
        System.out.println(date);
        if (date.equals(new SimpleDateFormat("MM/dd").format(new Date())))
            dateNow.setText("오늘");
        else
            dateNow.setText(date);


        //db 데이터 불러오기
        putData(view, helper);

        // Create the Timeline Adapter
        myAdapter = new TimelineViewAdapter(getActivity(), 0, timelineRowsList,
                //if true, list will be sorted by date
                false);
        // Get the ListView and Bind it with the Timeline Adapter
        ListView myListView = (ListView) view.findViewById(R.id.timeline_listView);
        myListView.setAdapter(myAdapter);

        return view;
    }

    //timeline 리스트 만들기
    private TimelineRow createTimelineRow(int id, String place, String category, String starttime) {
        //카테고리 이미지 분류
        int categoryNum;
        String color;
        switch (category) {
            case "수면":
                categoryNum = 0;
                color = "#414766";
                break;
            case "일":
                categoryNum = 1;
                color = "#F98583";
                break;
            case "공부":
                categoryNum = 2;
                color = "#B4CC65";
                break;
            case "운동":
                categoryNum = 3;
                color = "#FBB06D";
                break;
            case "여가":
                categoryNum = 4;
                color = "#C7ACEE";
                break;
            default:
                categoryNum = 5;
                color = "#888888";
        }

        //날짜 String -> Date
        SimpleDateFormat sdfformat = new SimpleDateFormat("HH:mm:ss");
        Date date = null;
        try {
            date = sdfformat.parse(starttime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Create new timeline row (pass your Id)
        TimelineRow myRow = new TimelineRow(id);

        // 날짜설정
        myRow.setDate(date);
        // 카테고리 설정
        myRow.setTitle(category);
        // 장소 설정
        myRow.setDescription(place);
        // 이미지 설정
        myRow.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.category_0 + categoryNum));
        // To set row Below Line Color (optional)
        myRow.setBellowLineColor(Color.parseColor(color));
        // To set row Below Line Size in dp (optional)
        myRow.setBellowLineSize(6);
        // To set row Image Size in dp (optional)
        myRow.setImageSize(30);
        // To set background color of the row image (optional)
        myRow.setBackgroundColor(Color.parseColor(color));
        // To set the Background Size of the row image in dp (optional)
        myRow.setBackgroundSize(60);
        // To set row Date text color (optional)
        myRow.setDateColor(Color.argb(255, 0, 0, 0));
        // To set row Title text color (optional)
        myRow.setTitleColor(Color.argb(255, 0, 0, 0));
        // To set row Description text color (optional)
        myRow.setDescriptionColor(Color.argb(255, 0, 0, 0));

        return myRow;
    }




    //DB에서 데이터 불러오기
    private void putData(View v, DBHelper helper) {
        String date = mParam1;
        System.out.println(date);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_timeline where date=?",
                new String[]{date});
        int i = 0;

        if(cursor.getCount()==0) return;

        while(cursor.moveToNext()) {
            String place = cursor.getString(cursor.getColumnIndex("place"));
            String category = cursor.getString(cursor.getColumnIndex("category"));
            String starttime = cursor.getString(cursor.getColumnIndex("starttime"));
            System.out.println(place);

            timelineRowsList.add(createTimelineRow(i, place, category, starttime));
            i++;
        }

        db.close();
    }
}
