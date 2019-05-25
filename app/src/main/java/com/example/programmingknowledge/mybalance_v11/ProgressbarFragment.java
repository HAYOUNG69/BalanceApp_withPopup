package com.example.programmingknowledge.mybalance_v11;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import org.w3c.dom.Text;

public class ProgressbarFragment extends Fragment {

    FragmentManager fm;
    FragmentTransaction tran;
    HomeFragment frag2;

    Button bt1;
    Button button2;
    Button btnAdd;
    View view;
    ImageView imageView;

    TextView textView8, textView9, textView10, textView11, textView12;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        //DE
        final DBHelper helper = new DBHelper(container.getContext());


        view = inflater.inflate(R.layout.fragment_progressbar, container, false);


        bt1 = (Button) view.findViewById(R.id.button);
        button2 = (Button) view.findViewById(R.id.button2);

        btnAdd = (Button) view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDailybalance(v, helper);
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

                Cursor cursor1 = db.rawQuery("select * from tb_goalbalance where week='토' ", null);
                Cursor cursor2 = db.rawQuery("select * from tb_dailybalance where week='토' ", null);

                float[] goal = new float[5];
                float[] measured = new float[5];
                float[] result = new float[5];

                if (cursor1.getCount() == 0 || cursor2.getCount() == 0) {
                    showMessage("Error", "Nothing found");
                    return;
                }
                while (cursor1.moveToNext()) {
                    goal[0] = cursor1.getFloat(cursor1.getColumnIndex("sleep"));
                    goal[1] = cursor1.getFloat(cursor1.getColumnIndex("work"));
                    goal[2] = cursor1.getFloat(cursor1.getColumnIndex("study"));
                    goal[3] = cursor1.getFloat(cursor1.getColumnIndex("exercise"));
                    goal[4] = cursor1.getFloat(cursor1.getColumnIndex("leisure"));

                    //나누는값이 0 이 안되게
                    for (int i = 0; i < 4; i++) {
                        if (goal[i] == 0) {
                            goal[i] = 1;
                        }
                    }
                }

                while (cursor2.moveToNext()) {
                    measured[0] = cursor2.getFloat(cursor2.getColumnIndex("sleep"));
                    measured[1] = cursor2.getFloat(cursor2.getColumnIndex("work"));
                    measured[2] = cursor2.getFloat(cursor2.getColumnIndex("study"));
                    measured[3] = cursor2.getFloat(cursor2.getColumnIndex("exercise"));
                    measured[4] = cursor2.getFloat(cursor2.getColumnIndex("leisure"));
                }

                db.close();

                result[0] = measured[0] / goal[0] * 100;
                result[1] = measured[1] / goal[1] * 100;
                result[2] = measured[2] / goal[2] * 100;
                result[3] = measured[3] / goal[3] * 100;
                result[4] = measured[4] / goal[4] * 100;

                //추천활동 정하기
                float min = 0;
                float max = 100;
                int recommend_min = 0;
                int recommend_max=0;
                //recommend는 0~4 를 통해 카테고리 알려줌

                for(int i=0;i<result.length;i++) {
                    if(max<result[i]) {
                        //max의 값보다 array[i]이 크면 max = array[i]
                        max = result[i];
                        recommend_max = i;
                    }

                    if(min>result[i]) {
                        //min의 값보다 array[i]이 작으면 min = array[i]
                        min = result[i];
                        recommend_min = i;
                    }


                    //추천활동이 카테고리별 과잉(너무 많이 함)에 대한 결과일 경우 +5를 해준다.(setRecommend로 넘기기 쉽게)
                    if(max-100 > 100-min)
                        recommend_min = recommend_max+5;



                }
                setRecommend(recommend_min);


                textView8 = (TextView) view.findViewById(R.id.textView8);
                textView8.setText("" + result[0]);
                textView9 = (TextView) view.findViewById(R.id.textView9);
                textView9.setText("" + result[1]);
                textView10 = (TextView) view.findViewById(R.id.textView10);
                textView10.setText("" + result[2]);
                textView11 = (TextView) view.findViewById(R.id.textView11);
                textView11.setText("" + result[3]);
                textView12 = (TextView) view.findViewById(R.id.textView12);
                textView12.setText("" + result[4]);

                progress1.setProgress(result[0]);
                progress2.setProgress(result[1]);
                progress3.setProgress(result[2]);
                progress4.setProgress(result[3]);
                progress5.setProgress(result[4]);
            }
        });
        return view;
    }

    private void setRecommend(int recommend) {


        imageView = (ImageView)view.findViewById(R.id.imageView);

        switch (recommend){
            case 0:
                //수면 부족
                imageView.setImageResource(R.drawable.sleep_l);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent (Intent.ACTION_VIEW, Uri.parse("https://brunch.co.kr/search?q=숙면+도움&profileId="));
                        startActivity(intent);
                    }
                });
                break;

            case 1:
                //일 부족
                imageView.setImageResource(R.drawable.work_l);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent (Intent.ACTION_VIEW, Uri.parse("https://brunch.co.kr/search?q=업무+효율&profileId="));
                        startActivity(intent);
                    }
                });

                break;
            case 2:
                //공부 부족
                imageView.setImageResource(R.drawable.study_l);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent (Intent.ACTION_VIEW, Uri.parse("https://brunch.co.kr/search?q=공부+집중&profileId="));
                        startActivity(intent);
                    }
                });

                break;
            case 3:
                //운동 부족
                imageView.setImageResource(R.drawable.exercise_l);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent (Intent.ACTION_VIEW, Uri.parse("https://brunch.co.kr/search?q=운동+장점&profileId="));
                        startActivity(intent);
                    }
                });

                break;
            case 4:
                //여가 부족
                imageView.setImageResource(R.drawable.leisure_l);


                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent (Intent.ACTION_VIEW, Uri.parse("https://brunch.co.kr/search?q=휴식+도움&profileId="));
                        startActivity(intent);
                    }
                });
                break;

            case 5:
                //잠 많음
                imageView.setImageResource(R.drawable.sleep_m);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent (Intent.ACTION_VIEW, Uri.parse("https://brunch.co.kr/search?q=잠+중독&profileId="));
                        startActivity(intent);
                    }
                });

                break;
            case 6:
                //업무 많음
                imageView.setImageResource(R.drawable.work_m);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent (Intent.ACTION_VIEW, Uri.parse("https://brunch.co.kr/search?q=업무+도움&profileId="));
                        startActivity(intent);
                    }
                });
                break;
            case 7:
                //공부 많음
                imageView.setImageResource(R.drawable.study_m);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent (Intent.ACTION_VIEW, Uri.parse("https://brunch.co.kr/search?q=공부+효율적&profileId="));
                        startActivity(intent);
                    }
                });
                break;
            case 8:
                //운동 많음
                imageView.setImageResource(R.drawable.exercise_m);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent (Intent.ACTION_VIEW, Uri.parse("https://brunch.co.kr/search?q=운동+중독&profileId="));
                        startActivity(intent);
                    }
                });
                break;

            case 9:
                //여가 많음
                imageView.setImageResource(R.drawable.leisure_m);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent (Intent.ACTION_VIEW, Uri.parse("https://brunch.co.kr/search?q=계획적+생활&profileId="));
                        startActivity(intent);
                    }
                });
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

        db.execSQL("insert into tb_dailybalance (date,week,sleep, work, study, exercise, leisure, other, recommend) values ('2019-05-14','토',0,1,2,4,8,0,'운동 부족')");
        db.close();
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


}
