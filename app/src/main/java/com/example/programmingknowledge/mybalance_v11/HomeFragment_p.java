package com.example.programmingknowledge.mybalance_v11;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import static com.example.programmingknowledge.mybalance_v11.HomeFragment.subDate;

public class HomeFragment_p extends Fragment {

    private ViewPager mViewPager;
    FragmentManager fm;
    FragmentTransaction tran;
    HomeFragment frag1;
    Button button;
    int page;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_home_p, container, false);
        final DBHelper helper = new DBHelper(container.getContext());

        //페이지 갯수 세기
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(date) from tb_dailybalance",null);
        cursor.moveToFirst();
        page = cursor.getInt(0);

        mViewPager = (ViewPager) view.findViewById(R.id.pager_progressbar);
        HomeFragment_p.MyPagerAdapter adapter = new HomeFragment_p.MyPagerAdapter(getChildFragmentManager(), page);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(page - 1);

        //progressbar로 전환
        button = (Button) view.findViewById(R.id.change_timeline);
        frag1 = new HomeFragment();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFrag(0);
            }
        });

        return view;
    }


    public void setFrag(int n) {
        fm = getFragmentManager();
        tran = fm.beginTransaction();
        switch (n) {
            case 0:
                tran.replace(R.id.fragment_container, frag1);
                tran.commit();
                break;
            //main_frame자리에 현재 frame이름
        }
    }

    //히스토리 pagerAdapter
    private static class MyPagerAdapter extends FragmentStatePagerAdapter {
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
                    frag = ProgressbarFragment.newInstance(formatDate);
                    return frag;
                }
            }
            return null;
        }
        @Override
        public int getCount () {
            return page;
        }
    }
}

