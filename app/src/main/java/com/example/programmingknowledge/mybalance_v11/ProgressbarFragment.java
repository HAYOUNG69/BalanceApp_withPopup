package com.example.programmingknowledge.mybalance_v11;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import org.w3c.dom.Text;

public class ProgressbarFragment extends Fragment {

    //DatabaseHelper myDB;
    //myDB가 daily DB

    FragmentManager fm;
    FragmentTransaction tran;
    HomeFragment frag2;

    Button bt1;
    Button button2;
    Button btnAdd;
    View view;

    TextView tv1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        //DE
        final DBHelper helper = new DBHelper(container.getContext());


        view = inflater.inflate(R.layout.fragment_progressbar, container, false);

        bt1 = (Button) view.findViewById(R.id.button);
        button2 = (Button) view.findViewById(R.id.button2);

        btnAdd = (Button)view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDailybalance(v,helper);
            }
        });

        frag2 = new HomeFragment();
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFrag(1);
            }

        });

        final RoundCornerProgressBar progress1 = (RoundCornerProgressBar) view.findViewById(R.id.progressBar1);
        progress1.setProgressColor(Color.parseColor("#ff363636"));
        progress1.setProgressBackgroundColor(Color.parseColor("#ff454344"));
        progress1.setMax(100);
        //progress1.setProgress(75);

        int progressColor1 = progress1.getProgressColor();
        int backgroundColor1 = progress1.getProgressBackgroundColor();

        final RoundCornerProgressBar progress2 = (RoundCornerProgressBar) view.findViewById(R.id.progressBar2);
        progress2.setProgressColor(Color.parseColor("#ffff8800"));
        progress2.setProgressBackgroundColor(Color.parseColor("#7fff8800"));
        progress2.setMax(100);


        int progressColor2 = progress2.getProgressColor();
        int backgroundColor2 = progress2.getProgressBackgroundColor();

        final RoundCornerProgressBar progress3 = (RoundCornerProgressBar) view.findViewById(R.id.progressBar3);
        progress3.setProgressColor(Color.parseColor("#ff0099cc"));
        progress3.setProgressBackgroundColor(Color.parseColor("#7f0099cc"));
        progress3.setMax(100);


        int progressColor3 = progress3.getProgressColor();
        int backgroundColor3 = progress3.getProgressBackgroundColor();


        final RoundCornerProgressBar progress4 = (RoundCornerProgressBar) view.findViewById(R.id.progressBar4);
        progress4.setProgressColor(Color.parseColor("#ff9933cc"));
        progress4.setProgressBackgroundColor(Color.parseColor("#7f9933cc"));
        progress4.setMax(100);


        int progressColor4 = progress4.getProgressColor();
        int backgroundColor4 = progress4.getProgressBackgroundColor();


        final RoundCornerProgressBar progress5 = (RoundCornerProgressBar) view.findViewById(R.id.progressBar5);
        progress5.setProgressColor(Color.parseColor("#ffff4444"));
        progress5.setProgressBackgroundColor(Color.parseColor("#7fff4444"));
        progress5.setMax(100);


        int progressColor5 = progress5.getProgressColor();
        int backgroundColor5 = progress5.getProgressBackgroundColor();

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = helper.getWritableDatabase();

                Cursor cursor1 = db.rawQuery("select * from tb_goalbalance where week='월' ",null);
                Cursor cursor2 = db.rawQuery("select * from tb_dailybalance where week='월' ",null);

                int[] goal = new int[5];
                int[] measured = new int[5];

                if(cursor1.getCount()==0 || cursor2.getCount()==0){
                    showMessage("Error","Nothing found");
                    return;
                }
                while(cursor1.moveToNext()){
                    goal[0] = cursor1.getInt(cursor1.getColumnIndex("sleep"));
                    goal[1] = cursor1.getInt(cursor1.getColumnIndex("work"));
                    goal[2] = cursor1.getInt(cursor1.getColumnIndex("study"));
                    goal[3] = cursor1.getInt(cursor1.getColumnIndex("exercise"));
                    goal[4] = cursor1.getInt(cursor1.getColumnIndex("leisure"));

                    for(int i=0;i<4;i++){
                        if(goal[i]==0){
                            goal[i]=1;
                        }
                    }
                }

                while(cursor2.moveToNext()){
                    measured[0] = cursor2.getInt(cursor2.getColumnIndex("sleep"));
                    measured[1] = cursor2.getInt(cursor2.getColumnIndex("work"));
                    measured[2] = cursor2.getInt(cursor2.getColumnIndex("study"));
                    measured[3] = cursor2.getInt(cursor2.getColumnIndex("exercise"));
                    measured[4] = cursor2.getInt(cursor2.getColumnIndex("leisure"));

                    //나누는값이 0 이 안되게

                }

                //buffer이용 => string에서 하면 좋을듯
//                StringBuffer buffer = new StringBuffer();
//                while(cursor.moveToNext()){
//                    buffer.append(cursor.getInt(cursor.getColumnIndex("sleep"))+"\n");
//                    buffer.append(cursor.getInt(cursor.getColumnIndex("work"))+"\n");
//                    buffer.append(cursor.getInt(cursor.getColumnIndex("study"))+"\n");
//                    buffer.append(cursor.getInt(cursor.getColumnIndex("exercise"))+"\n");
//                    buffer.append(cursor.getInt(cursor.getColumnIndex("leisure"))+"\n");
//                }
//                showMessage("Data",buffer.toString());

                //데이터 1개 처리시
//                if(cursor!=null && cursor.getCount() > 0)
//                {
//                    if (cursor.moveToFirst())
//                    {
//                        do {
//                            b_work = cursor.getInt(0);
//                        } while (cursor.moveToNext());
//                    }
//                }

                db.close();

                progress1.setProgress(measured[0]/goal[0]*100);
                progress2.setProgress(measured[1]/goal[1]*100);
                progress3.setProgress(measured[2]/goal[2]*100);
                progress4.setProgress(measured[3]/goal[3]*100);
                progress5.setProgress(measured[4]/goal[4]*100);
            }
        });
        return view;
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                setFrag(1);
                break;
        }

    }

    public void setFrag(int n) {
        fm = getFragmentManager();
        tran = fm.beginTransaction();
        switch (n) {
            case 1:
                tran.replace(R.id.fragment_container, frag2);
                tran.commit();
                break;
            //main_frame자리에 현재 frame이름
        }


    }

    public void setDailybalance(View v, DBHelper helper) {



        //db 추가하기
        SQLiteDatabase db = helper.getWritableDatabase();

        db.execSQL("insert into tb_dailybalance (date,week,sleep, work, study, exercise, leisure, other, recommend) values ('2019-05-14','월',2,1,2,1,2,0,'운동 부족')");
        db.close();


    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


}
