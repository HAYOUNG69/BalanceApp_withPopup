package com.example.programmingknowledge.mybalance_v11;

import android.content.ContentValues;
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
import android.widget.Toast;

import org.qap.ctimelineview.TimelineRow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment {

    Button button;
    FragmentManager fm;
    FragmentTransaction tran;
    ProgressbarFragment frag1;

    //Create Timeline Rows List
    private ArrayList<TimelineRow> timelineRowsList = new ArrayList<>();
    ArrayAdapter<TimelineRow> myAdapter;
    //DBHelper helper = new DBHelper(getContext());


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
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

        //DB데이터 넣기
        setData(helper);

        //DB에서 데이터 불러와서 timelineList에 저장


        // Add to the List
        timelineRowsList.add(createTimelineRow(0));


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

    private TimelineRow createTimelineRow(int id) {

        // Create new timeline row (pass your Id)
        TimelineRow myRow = new TimelineRow(id);

        // To set the row Date (optional)
        myRow.setDate(new Date());
        // To set the row Title (optional)
        myRow.setTitle("Work");
        // To set the row Description (optional)
        myRow.setDescription("starbucks");
        // To set the row bitmap image (optional)
        myRow.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.work));
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
    private void setData(DBHelper helper) {
        SQLiteDatabase db = helper.getWritableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String place = "우리집";
        String category = "home";
        Date starttime = new Date();
        Date endtime = new Date();
        String week = "화";

        ContentValues initialValues = new ContentValues();
        initialValues.put("place", place);
        initialValues.put("category", category);
        initialValues.put("startime", dateFormat.format(starttime));
        initialValues.put("endtime", dateFormat.format(endtime));
        initialValues.put("week", week);
        long newRowId = db.insert("todaycount",
                null,
                initialValues);

    }

}
