package com.brapeba.suptraining;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joanmi on 09-Nov-15.
 */
public class SessionsListF extends Fragment
{

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static List<Session> afterDel= new ArrayList<Session>();
    private static List<Session> undoDel= new ArrayList<Session>();
    private static ListView cListView;
    private static LinearLayout linL;
    private static SessionsListFCustomAdapter adapter;
    private View.OnClickListener mOnClickListener;
    private SparseBooleanArray checked = new SparseBooleanArray();
    private int dSize=0;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SessionsListF init(int sectionNumber)
    {
        SessionsListF fragment = new SessionsListF();
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
        View rootView = inflater.inflate(R.layout.sessionslist_f, container, false);
        cListView = (ListView) rootView.findViewById(android.R.id.list);
        cListView.setSelector(R.drawable.activatedbackground);
        cListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        cListView.setItemsCanFocus(false);
        cListView.setVerticalScrollBarEnabled(true);
        linL = (LinearLayout) rootView.findViewById(R.id.lltop);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        cListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
            }
        });

        mOnClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // undo
                for (Session i : undoDel)
                {
                    Constants.allSessions.add(i);
                }
                SaveSessions.dumpMemToInternalStorage(Constants.allSessions, getActivity());
                undoDel.clear();
                adapter = new SessionsListFCustomAdapter(getActivity(),Constants.allSessions);
                cListView.setAdapter(adapter);
                cListView.requestLayout();
            }
        };

        FloatingActionButton fabd = (FloatingActionButton) view.findViewById(R.id.fabld);
        fabd.setOnClickListener(new View.OnClickListener()
        {
                @Override
                public void onClick(View view)
                {
                    undoDel.clear();
                    checked=cListView.getCheckedItemPositions();
                    dSize=0;
                    for (int i = 0; i < checked.size(); i++)
                    {
                        if (checked.valueAt(i))
                        {
                            undoDel.add(Constants.allSessions.get(checked.keyAt(i-dSize))); //i-dSize because index reduces by deletions as i increases
                            Constants.allSessions.remove(checked.keyAt(i-dSize));
                            dSize++;
                        }
                    }
                    SaveSessions.dumpMemToInternalStorage(Constants.allSessions, getActivity());
                    refreshTab(getActivity()); // it reloads allSessions from storage
                    Snackbar.make(view, dSize+" "+getString(R.string.string4), Snackbar.LENGTH_LONG).setAction(getString(R.string.undo), mOnClickListener).show();
                }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        refreshTab(getActivity());
    }

    public static void refreshTab(Activity activity)
    {
        Constants.allSessions= SaveSessions.readFromInternalStorage(activity);
        if (Constants.allSessions==null) { Constants.allSessions=new ArrayList<Session>(); }
        adapter = new SessionsListFCustomAdapter(activity,Constants.allSessions);
        cListView.setAdapter(adapter);
        cListView.requestLayout();
    }
}
