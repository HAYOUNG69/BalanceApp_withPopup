package com.example.programmingknowledge.mybalance_v11;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SettingsFragment extends Fragment {
        @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            //네..드디어 됩니다 미친 이거하느라 몇시간썼냐
            View root = inflater.inflate(R.layout.fragment_settings, container, false);
            TextView tv = (TextView) root.findViewById(R.id.GoToGoalBalanceListTv);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new GoalBalanceListFragment();

                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace( R.id.fragment_container, fragment );
                    fragmentTransaction.commit();
                }
            });

            return root;
    }

}
