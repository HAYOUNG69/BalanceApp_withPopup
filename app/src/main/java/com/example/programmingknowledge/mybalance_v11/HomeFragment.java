package com.example.programmingknowledge.mybalance_v11;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {

    private ViewPager mViewPager;
    FragmentManager fm;
    FragmentTransaction tran;
    ProgressbarFragment frag1;
    Button button;
    Button button2;
    int page;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        final DBHelper helper = new DBHelper(container.getContext());

        //db insert 버튼
        button = (Button)view.findViewById(R.id.insert);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData(v, helper);
            }
        });

        //페이지 갯수 세기
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(distinct date) from tb_timeline",null);
        cursor.moveToFirst();
        page = cursor.getInt(0);

        mViewPager = (ViewPager)view.findViewById(R.id.pager);
        HomeFragment.MyPagerAdapter adapter = new HomeFragment.MyPagerAdapter(getChildFragmentManager(), page);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(page - 1);

        //progressbar로 전환
        button2 = (Button)view.findViewById(R.id.button);
        frag1 = new ProgressbarFragment();
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFrag(0);
            }
        });

        return view;
    }

    //DB에 데이터 넣기
    private void setData(View v, DBHelper helper) {
        SQLiteDatabase db = helper.getWritableDatabase();
        //db.execSQL("delete from tb_timeline");
        db.execSQL("insert into tb_timeline (date, place, category, starttime, endtime) values (?,?,?,?,?)",
                new String[]{"2019/05/22", "한강동아아파트", "수면", "01:15:16", "06:18:26"});
        db.execSQL("insert into tb_timeline (date, place, category, starttime, endtime) values (?,?,?,?,?)",
                new String[]{"2019/05/23", "경기대학교", "공부", "09:05:00", "11:55:12"});
        db.execSQL("insert into tb_timeline (date, place, category, starttime, endtime) values (?,?,?,?,?)",
                new String[]{"2019/05/24", "헬스장", "운동", "15:27:35", "18:46:33"});
        db.close();
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

    //히스토리 pagerAdapter
    private  static class MyPagerAdapter extends FragmentStatePagerAdapter {
        int page;

        public MyPagerAdapter(FragmentManager fm, int n) {
            super(fm);
            this.page = n;
        }

        //타임라인 히스토리 페이지 설정
        @Override
        public Fragment getItem(int position) {
            Fragment frag;
            Date date = new Date();
            SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy/MM/dd");
            String formatDate = sdfdate.format(date);

            for (int i = 0; i < page; i++) {
                if (position == i) {

                    try {
                        formatDate = subDate(formatDate, (i+1-page));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    frag = TimelineFragment.newInstance(formatDate);
                    return frag;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return page;
        }
    }

    //날짜 빼는 메소드
    private static String subDate(String dt, int d) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        Calendar cal = Calendar.getInstance();
        Date date = format.parse(dt);
        cal.setTime(date);
        cal.add(Calendar.DATE, d);

        return format.format(cal.getTime());
    }


}

