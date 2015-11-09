
/**
 * Created by joanmi on 05-Nov-15.
 */

package com.brapeba.suptraining;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.math.BigInteger;
import java.security.SecureRandom;
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
    private String myName;

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
        tempTV.setText(Constants.session.getName()+" \n"+Constants.session.getDate().toLocaleString());
        adapter = new ListElementCustomAdapter(getActivity(),Constants.table,this);
        // cListView.setAdapter(adapter); // NO NEED HERE AS IT IS @OnResume via refreshTab()
        cListView.setChoiceMode(ListView.CHOICE_MODE_NONE);

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                    //let's save the count: asking for a name
                    final AlertDialog fbuilder = new AlertDialog.Builder(getActivity(), R.style.AppTheme_PopupOverlay)
                            .setPositiveButton(getString(R.string.save), null)
                            .setNegativeButton(getString(R.string.cancel), null)
                            .create();
                    final EditText edtName = new EditText(getActivity());
                    myName = new BigInteger(32, new SecureRandom()).toString(16);
                    edtName.setHint(myName);
                    fbuilder.setView(edtName);
                    fbuilder.setTitle(getString(R.string.string3));
                    fbuilder.setOnShowListener(new DialogInterface.OnShowListener()
                    {
                        @Override
                        public void onShow(DialogInterface dialog)
                        {
                            final Button btnAccept = fbuilder.getButton(AlertDialog.BUTTON_POSITIVE);
                            btnAccept.setOnClickListener(new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View v)
                                {
                                    if (edtName.getText().toString().isEmpty())
                                    {
                                        edtName.setText(myName);
                                    }
                                    //now saving it!
                                    fbuilder.dismiss();
                                    Constants.session.setName(edtName.getText().toString());
                                    tempTV.setText(Constants.session.getName() + " \n" + Constants.session.getDate().toLocaleString());
                                    if (!SaveC.saveToInternalStorage(Constants.session, getActivity()))
                                    {
                                        // returned false -> handle error
                                    }
                                    SessionsListF.refreshTab(getActivity());
                                    Snackbar.make(getView(), getString(R.string.saving), Snackbar.LENGTH_SHORT).show();
                                }
                            });

                            final Button btnDecline = fbuilder.getButton(DialogInterface.BUTTON_NEGATIVE);
                            btnDecline.setOnClickListener(new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View v)
                                {
                                    fbuilder.dismiss();
                                }
                            });
                        }
                    });
                    fbuilder.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                    fbuilder.show();
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
