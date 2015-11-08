
/**
 * Created by joanmi on 05-Nov-15.
 */

package com.brapeba.suptraining;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class StartF extends Fragment implements ListElementCustomAdapter.MyCustomRowButtonListener
{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static List<Element> allCounts;
    private static List<Element> afterDel= new ArrayList<Element>();
    private static List<Element> undoDel= new ArrayList<Element>();
    private static ListView cListView;
    private static LinearLayout linL;
    private static ListElementCustomAdapter adapter;
    private View.OnClickListener mOnClickListener;
    private FloatingActionButton fab;
    private TextView tempTV;
    private String lastSum="0";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static StartF init(int sectionNumber)
    {
        StartF fragment = new StartF();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCustomRowButtonClick(Element selectedItem, int position, int increment, View view)
    {
        selectedItem.sumTimes(increment);
        //Toast.makeText(this,"You have selected "+selectedItem, Toast.LENGTH_SHORT).show();
    }

    @Override public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.start_f, container, false);
        cListView = (ListView) rootView.findViewById(android.R.id.list);
        linL = (LinearLayout) rootView.findViewById(R.id.lltop);
        cListView.setChoiceMode(ListView.CHOICE_MODE_NONE);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        tempTV=(TextView)view.findViewById(R.id.stheader);
        tempTV.setText(Constants.session.getName()+" \n"+Constants.session.getDate());
        adapter = new ListElementCustomAdapter(getActivity(),Constants.table,this);
        // cListView.setAdapter(adapter); // NO NEED HERE AS IT IS @OnResume via refreshTab()
        cListView.setChoiceMode(ListView.CHOICE_MODE_NONE);

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
            }
        });
    }

    public static void refreshTab(Activity activity,ListElementCustomAdapter.MyCustomRowButtonListener listener)
    {
        adapter = new ListElementCustomAdapter(activity,Constants.table,listener);
        cListView.setAdapter(adapter);
        cListView.requestLayout();
    }

    @Override public void onResume()
    {
        super.onResume();
        refreshTab(getActivity(),StartF.this);
    }
}
