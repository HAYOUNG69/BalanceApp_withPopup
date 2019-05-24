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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.qap.ctimelineview.TimelineRow;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment {

    Button button;
    Button button2; ///////////
    FragmentManager fm;
    FragmentTransaction tran;
    ProgressbarFragment frag1;

    //Create Timeline Rows List
    private ArrayList<TimelineRow> timelineRowsList = new ArrayList<>();
    ArrayAdapter<TimelineRow> myAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_home,container,false);
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        final DBHelper helper = new DBHelper(container.getContext());


        button = (Button)view.findViewById(R.id.button);
        frag1 = new ProgressbarFragment();
        //setFrag(0);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFrag(0);
            }
        });

        //db insert 버튼
        button2 = (Button)view.findViewById(R.id.insert);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData(v, helper);
            }
        });

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

    public void onClick(View v){
        switch (v.getId()){
            case R.id.button:
                setFrag(0);
                break;
        }
    }
    public void setFrag(int n){
        fm = getFragmentManager();
        tran = fm.beginTransaction();
        switch (n){
            case 0:
                tran.replace(R.id.fragment_container, frag1);
                tran.commit();
                break;
                        //main_frame자리에 현재 frame이름
        }
    }

    private TimelineRow createTimelineRow(int id, String place, String category, String starttime) {
        //카테고리 이미지 분류
        int categoryNum;
        switch (category) {
            case "수면":
                categoryNum = 0;
                break;
            case "일":
                categoryNum = 1;
                break;
            case "공부":
                categoryNum = 2;
                break;
            case "운동":
                categoryNum = 3;
                break;
            case "여가":
                categoryNum = 4;
                break;
            default:
                categoryNum = 5;
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
        myRow.setBellowLineColor(Color.argb(255, 0, 0, 0));
        // To set row Below Line Size in dp (optional)
        myRow.setBellowLineSize(6);
        // To set row Image Size in dp (optional)
        myRow.setImageSize(30);
        // To set background color of the row image (optional)
        myRow.setBackgroundColor(Color.argb(255, 170, 170, 170));
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


    //DB에 데이터 넣기
    private void setData(View v, DBHelper helper) {
        SQLiteDatabase db = helper.getWritableDatabase();
        //db.execSQL("delete from tb_todaycount");
        db.execSQL("insert into tb_timeline (date, place, category, starttime, endtime) values (?,?,?,?,?)",
                new String[]{"2019-05-21", "한강동아아파트", "수면", "01:15:16", "06:18:26"});
        db.execSQL("insert into tb_timeline (date, place, category, starttime, endtime) values (?,?,?,?,?)",
                new String[]{"2019-05-22", "경기대학교", "공부", "09:05:00", "11:55:12"});
        db.execSQL("insert into tb_timeline (date, place, category, starttime, endtime) values (?,?,?,?,?)",
                new String[]{"2019-05-23", "헬스장", "운동", "15:27:35", "18:46:33"});
        db.close();
    }

    //DB에서 데이터 불러오기
    private void putData(View v, DBHelper helper) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_timeline",null);
        int i = 0;

        if(cursor.getCount()==0) return;

        while(cursor.moveToNext()) {
            String place = cursor.getString(cursor.getColumnIndex("place"));
            String category = cursor.getString(cursor.getColumnIndex("category"));
            String starttime = cursor.getString(cursor.getColumnIndex("starttime"));

            timelineRowsList.add(createTimelineRow(i, place, category, starttime));
            i++;
        }

        db.close();
    }

}
