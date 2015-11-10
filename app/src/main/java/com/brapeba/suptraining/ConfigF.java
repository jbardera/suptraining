package com.brapeba.suptraining;

/**
        * Created by joanmi on 10-Nov-15.
        */

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.SecureRandom;



/**
 * Created by joanmi on 05-Nov-15.
 */

public class ConfigF extends Fragment implements ConfigElementCustomAdapter.MyCustomRowButtonListener2
{
        private static ListView cListView;
        private static LinearLayout linL;
        private static ConfigElementCustomAdapter adapter;
        private FloatingActionButton fab;
        private TextView tempTV;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ConfigF init(int sectionNumber)
        {
            ConfigF fragment = new ConfigF();
            Bundle args = new Bundle();
            args.putInt(Constants.ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public void onCustomRowButtonClick(Element selectedItem, int position, int increment, View view)
        {
            //listener of custom adapter->to process updates!
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
            adapter = new ConfigElementCustomAdapter(getActivity(),Constants.table,ConfigF.this);
            // cListView.setAdapter(adapter); // NO NEED HERE AS IT IS @OnResume via refreshTab()
            cListView.setChoiceMode(ListView.CHOICE_MODE_NONE);
            fab = (FloatingActionButton) view.findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if (!SaveC.saveToInternalStorage(Constants.session, getActivity()))
                    {
                        // returned false -> handle error
                    }
                    SessionsListF.refreshTab(getActivity());
                    Snackbar.make(getView(), getString(R.string.saving) + " " + Constants.session.getName(), Snackbar.LENGTH_SHORT).show();
                    // once the session is saved we create a new one:
                    Start.newSession(getActivity());
                    tempTV.setText(getString(R.string.session) + " #" + Constants.session.getName() + " \n" + Constants.session.getDate().toLocaleString());
                    refreshTab(getActivity(), ConfigF.this);
                    Toast.makeText(getActivity(), getString(R.string.string2), Toast.LENGTH_LONG).show();
                }
            });
        }

        public static void refreshTab(Activity activity,ConfigElementCustomAdapter.MyCustomRowButtonListener2 listener)
        {
            Constants.session.setName(new BigInteger(32, new SecureRandom()).toString(16));
            ((TextView)activity.findViewById(R.id.stheader)).setText(activity.getString(R.string.session)+" #"+Constants.session.getName() + " \n" + Constants.session.getDate().toLocaleString());
            adapter = new ConfigElementCustomAdapter(activity,Constants.table,listener);
            cListView.setAdapter(adapter);
            cListView.requestLayout();
        }

        @Override public void onResume()
        {
            super.onResume();
            refreshTab(getActivity(), ConfigF.this);
        }
    }

