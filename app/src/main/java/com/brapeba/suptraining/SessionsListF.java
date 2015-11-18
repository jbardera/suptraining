package com.brapeba.suptraining;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.SparseBooleanArray;
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
        //cListView.setItemsCanFocus(false);
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
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
            }
        });

        cListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                final int pos = cListView.getCount() - position - 1; //for reverse order
                final Session thisSession = Constants.allSessions.get(pos);
                final AlertDialog sesVal = new AlertDialog.Builder(getActivity(), R.style.AppTheme_PopupOverlay)
                        .setPositiveButton(getString(R.string.ok), null)
                                //.setNegativeButton(getString(R.string.cancel), null)
                        .setTitle(thisSession.getName() + "\n" + thisSession.getDate().toLocaleString())
                                //.setMessage(getString(R.string.string8))
                        .create();
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final TextView textV = new TextView(getActivity());
                for (int i = 0; i < thisSession.getElements().size(); i++)
                {
                    textV.append(" "+String.format("%04d",thisSession.getElements().get(i).getTimesdone()) + " "
                            + Constants.listElementsData.get(thisSession.getElements().get(i).getCode()).getName()+"\n");
                }
                sesVal.setView(textV);
                sesVal.setOnShowListener(new DialogInterface.OnShowListener()
                {
                    @Override
                    public void onShow(DialogInterface dialog)
                    {
                        final Button btnAccept = sesVal.getButton(AlertDialog.BUTTON_POSITIVE);
                        btnAccept.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                sesVal.dismiss();
                            }
                        });
                    }
                });
                sesVal.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                sesVal.show();
                return true;
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
