package com.brapeba.suptraining;

/**
        * Created by joanmi on 05-Nov-15.
        */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ConfigF extends Fragment
{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ConfigF init(int sectionNumber)
    {
        ConfigF fragment = new ConfigF();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ConfigF()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.empty_f, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.empty_label);
        for (int i=0;i<Constants.table.size();i++)
            textView.append(Constants.table.get(i).getName()+" +"+Constants.listElementsData.get(Constants.table.get(i).getCode()).getInc()+"\n");
        return rootView;
    }
}
