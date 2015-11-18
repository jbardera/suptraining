package com.brapeba.suptraining;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joanmi on 09-Nov-15.
 */
public class StatsF extends Fragment
{
    static ArrayList<LineChart> chart= new ArrayList<LineChart>();
    static LineChart chart1;
    static LineData data;
    static LinearLayout rl;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static StatsF init(int sectionNumber)
    {
        StatsF fragment = new StatsF();
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.stats_f, container, false);
        rl = (LinearLayout) rootView.findViewById(R.id.statsLayout);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }

    static void drawCharts(Activity activity)
    {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int gYSize=displaymetrics.heightPixels/4;
        ArrayList<String> xAxisValues=getXAxisValues();
        for (int i=0;i<Constants.tableSession.size();i++)
        {
            chart1=new LineChart(activity);
            data = new LineData(xAxisValues, getDataSet(Constants.tableSession.get(i).getCode()));
            chart1.setData(data);
            chart1.setMinimumHeight(gYSize);
            XAxis xAxis = chart1.getXAxis();
            YAxis yAxis = chart1.getAxisLeft();
            xAxis.setTextColor(R.color.colorPrimaryDark);
            yAxis.setTextColor(R.color.colorPrimaryDark);
            xAxis.setDrawGridLines(false);
            yAxis.setDrawGridLines(false);
            chart1.setDrawGridBackground(false);
            chart1.setBackgroundColor(Color.TRANSPARENT);
            chart1.setDescription("");
            chart.add(chart1);
            rl.addView(chart1);
        }
    }

    private static ArrayList<LineDataSet> getDataSet(int elementCode)
    {
        ArrayList<Entry> valueSet1 = new ArrayList<>();
        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        for (int i = 0; i < Constants.allSessions.size(); i++)
        {
            List<Element> tempListElements = Constants.allSessions.get(i).getElements();
            if (elementCode>tempListElements.size())
            {
                //means there is not this element code in this session -> set to 0!
                Entry v1e1 = new Entry(0, i);
                valueSet1.add(v1e1);
            } else
            {
                Boolean found=false;
                for (int j=0;j<tempListElements.size();j++)
                {
                    if (tempListElements.get(j).getCode()==elementCode)
                    {
                        Entry v1e1 = new Entry(tempListElements.get(j).getTimesdone(), i);
                        valueSet1.add(v1e1);
                        found=true;
                        j=tempListElements.size(); //to end the loop
                    }
                }
                if (!found)
                {
                    Entry v1e1 = new Entry(0, i);
                    valueSet1.add(v1e1);
                }
            }
        }
        LineDataSet lineDataSet1 = new LineDataSet(valueSet1, Constants.listElementsData.get(elementCode).getName());
        lineDataSet1.setColor(R.color.colorPrimary);
        lineDataSet1.setFillColor(R.color.colorPrimary);
        lineDataSet1.setDrawFilled(true);
        lineDataSet1.setDrawCubic(true);
        dataSets.add(lineDataSet1);
        return dataSets;
    }

    private static ArrayList<String> getXAxisValues()
    {
        ArrayList<String> xAxis = new ArrayList<>();
        for (int i=0;i<Constants.allSessions.size();i++)
        {
            xAxis.add(String.valueOf(i+1));
        }
        return xAxis;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        refreshTab(getActivity());
    }

    public static void refreshTab(Activity activity)
    {
        // initializing all charts
        rl.invalidate();
        drawCharts(activity);
        rl.requestLayout();
        for (int i=0;i<chart.size();i++) chart.get(i).invalidate();
    }
}
