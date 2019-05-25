package com.example.programmingknowledge.mybalance_v11;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.qap.ctimelineview.TimelineRow;


public class TimelineViewAdapter extends ArrayAdapter<TimelineRow> {

    private Context context;
    private Resources res;
    private List<TimelineRow> RowDataList;
    private String AND;


    public TimelineViewAdapter(Context context, int resource, ArrayList<TimelineRow> objects, boolean orderTheList) {
        super(context, resource, objects);
        this.context = context;
        res = context.getResources();
        AND = res.getString(R.string.AND);
        if (orderTheList)
            this.RowDataList = rearrangeByDate(objects);
        else
            this.RowDataList = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TimelineRow row = RowDataList.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.ctimeline_row, null);

        TextView rowDate = (TextView) view.findViewById(R.id.crowDate);
        TextView rowTitle = (TextView) view.findViewById(R.id.crowTitle);
        TextView rowDescription = (TextView) view.findViewById(R.id.crowDesc);
        ImageView rowImage = (ImageView) view.findViewById(R.id.crowImg);
        View rowUpperLine = (View) view.findViewById(R.id.crowUpperLine);
        View rowLowerLine = (View) view.findViewById(R.id.crowLowerLine);


        final float scale = getContext().getResources().getDisplayMetrics().density;


        if (position == 0 && position == RowDataList.size() - 1) {
            rowUpperLine.setVisibility(View.INVISIBLE);
            rowLowerLine.setVisibility(View.INVISIBLE);
        } else if (position == 0) {
            int pixels = (int) (row.getBellowLineSize() * scale + 0.5f);

            rowUpperLine.setVisibility(View.INVISIBLE);
            rowLowerLine.setBackgroundColor(row.getBellowLineColor());
            rowLowerLine.getLayoutParams().width = pixels;
        } else if (position == RowDataList.size() - 1) {
            int pixels = (int) (RowDataList.get(position - 1).getBellowLineSize() * scale + 0.5f);

            rowLowerLine.setVisibility(View.INVISIBLE);
            rowUpperLine.setBackgroundColor(RowDataList.get(position - 1).getBellowLineColor());
            rowUpperLine.getLayoutParams().width = pixels;
        } else {
            int pixels = (int) (row.getBellowLineSize() * scale + 0.5f);
            int pixels2 = (int) (RowDataList.get(position - 1).getBellowLineSize() * scale + 0.5f);

            rowLowerLine.setBackgroundColor(row.getBellowLineColor());
            rowUpperLine.setBackgroundColor(RowDataList.get(position - 1).getBellowLineColor());
            rowLowerLine.getLayoutParams().width = pixels;
            rowUpperLine.getLayoutParams().width = pixels2;
        }

        SimpleDateFormat transFormat = new SimpleDateFormat("a hh시 mm분");

        rowDate.setText(transFormat.format(row.getDate()));
        if (row.getDateColor() != 0)
            rowDate.setTextColor(row.getDateColor());
        if (row.getTitle() == null)
            rowTitle.setVisibility(View.GONE);
        else {
            rowTitle.setText(row.getTitle());
            if (row.getTitleColor() != 0)
                rowTitle.setTextColor(row.getTitleColor());
        }
        if (row.getDescription() == null)
            rowDescription.setVisibility(View.GONE);
        else {
            rowDescription.setText(row.getDescription());
            if (row.getDescriptionColor() != 0)
                rowDescription.setTextColor(row.getDescriptionColor());
        }


        if (row.getImage() != null) {
            rowImage.setImageBitmap(row.getImage());
        }

        int pixels = (int) (row.getImageSize() * scale + 0.5f);
        rowImage.getLayoutParams().width = pixels;
        rowImage.getLayoutParams().height = pixels;

        View backgroundView = view.findViewById(R.id.crowBackground);
        if (row.getBackgroundColor() == 0)
            backgroundView.setBackground(null);
        else {
            if (row.getBackgroundSize() == -1) {
                backgroundView.getLayoutParams().width = pixels;
                backgroundView.getLayoutParams().height = pixels;
            } else {
                int BackgroundPixels = (int) (row.getBackgroundSize() * scale + 0.5f);
                backgroundView.getLayoutParams().width = BackgroundPixels;
                backgroundView.getLayoutParams().height = BackgroundPixels;
            }
            GradientDrawable background = (GradientDrawable) backgroundView.getBackground();
            if (background != null) {
                background.setColor(row.getBackgroundColor());
            }
        }


        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) rowImage.getLayoutParams();
        marginParams.setMargins(0, (int) (pixels / 2) * -1, 0, (pixels / 2) * -1);


        return view;
    }

    private ArrayList<TimelineRow> rearrangeByDate(ArrayList<TimelineRow> objects) {
        if (objects.get(0) == null) return objects;
        int size = objects.size();
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                if(objects.get(i).getDate()!= null && objects.get(j).getDate() != null)
                    if (objects.get(i).getDate().compareTo(objects.get(j).getDate()) >= 0)
                        Collections.swap(objects, i, j);
            }

        }
        return objects;
    }

}