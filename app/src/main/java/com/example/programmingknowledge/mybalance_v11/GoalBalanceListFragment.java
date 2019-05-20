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


        //목표 밸런스 설정창으로 이동
        View root = inflater.inflate(R.layout.fragment_goal_balance_list, container, false);
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

        // Inflate the layout for this fragment
        return root;
    }
}