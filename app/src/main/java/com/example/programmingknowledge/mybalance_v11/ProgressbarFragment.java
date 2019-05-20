package com.example.programmingknowledge.mybalance_v11;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

public class ProgressbarFragment extends Fragment {

    Button bt1;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_progressbar, container, false);

        bt1 = (Button)view.findViewById(R.id.button);

        final RoundCornerProgressBar progress1 = (RoundCornerProgressBar)view.findViewById(R.id.progressBar1);
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
                progress1.setProgress(75);
                progress2.setProgress(40);
                progress3.setProgress(100);
                progress4.setProgress(120);
                progress5.setProgress(50);
            }
        });




        return view;
    }



}
