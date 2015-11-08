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

public class EmptyF extends Fragment
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
    public static EmptyF init(int sectionNumber)
    {
        EmptyF fragment = new EmptyF();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public EmptyF()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.empty_f, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.empty_label);
        textView.setText(getString(R.string.app_name, getArguments().getInt(ARG_SECTION_NUMBER)));
        return rootView;
    }
}
