package com.example.programmingknowledge.mybalance_v11;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;





        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;

        import android.content.Context;
        import android.content.Intent;
        import android.content.DialogInterface;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.Bundle;
        import android.support.constraint.ConstraintLayout;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.Snackbar;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.CardView;
        import android.support.v7.widget.Toolbar;
        import android.support.v7.app.AlertDialog;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;


public class GoalBalanceListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //db읽기
        DBHelper helper = new DBHelper(container.getContext());
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select sleep, work, study, exercise, leisure, other, week from tb_goalbalance", null);


        View root = inflater.inflate(R.layout.fragment_goal_balance_list, container, false);

        //목표 밸런스 설정창으로 이동
        CardView cv = (CardView) root.findViewById(R.id.cardView01);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SettingGoalBalanceFragment();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace( R.id.fragment_container, fragment );
                fragmentTransaction.commit();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.AddGoalBalance);
        fab.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Fragment fragment = new SettingGoalBalanceFragment();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace( R.id.fragment_container, fragment );
                fragmentTransaction.commit();
            }
        });

        //카드뷰 오래 누르면 카드뷰 삭제
        CardView cardview = root.findViewById(R.id.cardView01);
        // AlertDialog 빌더를 이용해 종료시 발생시킬 창을 띄운다
        final AlertDialog.Builder alBuilder = new AlertDialog.Builder(container.getContext());
        cardview.setOnLongClickListener(new View.OnLongClickListener() {

            public boolean onLongClick(final View view) {
                alBuilder.setMessage("삭제하시겠습니까?");

                // "예" 버튼을 누르면 실행되는 리스너
                alBuilder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //RelativeLayout relative = (RelativeLayout) root.findViewById(R.id.relative);
                        //relative.removeView(view);
                        //db삭제 구현하기(아래)

                    }
                });
                // "아니오" 버튼을 누르면 실행되는 리스너
                alBuilder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return; // 아무런 작업도 하지 않고 돌아간다
                    }
                });
                alBuilder.setTitle("목표 밸런스 삭제");
                alBuilder.show(); // AlertDialog.Bulider로 만든 AlertDialog를 보여준다.

                return false;
            }

        });

        // Inflate the layout for this fragment
        return root;
    }
}