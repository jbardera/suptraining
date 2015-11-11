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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ConfigF extends Fragment implements ConfigElementCustomAdapter.MyCustomRowButtonListener2
{
    private static ListView cListView;
    private static ConfigElementCustomAdapter adapter;
    public FloatingActionButton fab;

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

    public void onCustomRowButtonClick(Element selectedItem, int position, Boolean istoggled)
    {
        //listener of custom adapter->to process toggle button!
        //tempElementData.setToShow(temptb1.isChecked());
        ElementsData tempElementsData = Constants.listElementsData.get(selectedItem.getCode());
        tempElementsData.setToShow(istoggled);
        Constants.listElementsData.put(selectedItem.getCode(), tempElementsData);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.start_f, container, false);
        cListView = (ListView) rootView.findViewById(android.R.id.list);
        cListView.setChoiceMode(ListView.CHOICE_MODE_NONE);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ConfigElementCustomAdapter(getActivity(), Constants.table, ConfigF.this);
        // cListView.setAdapter(adapter); // NO NEED HERE AS IT IS @OnResume via refreshTab()
        cListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        cListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id)
            {
                Toast.makeText(getActivity(), "Element #" + position + "=" + Constants.listElementsData.get(Constants.table.get(position).getCode()).getName(), Toast.LENGTH_SHORT).show();
                //cListView.getCheckedItemPosition();
                //cListView.getCheckedItemCount(); //to be 1!!
            }
        });
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                  saveElementsData();
            }
        });
    }

    private static void refreshTab(Activity activity, ConfigElementCustomAdapter.MyCustomRowButtonListener2 listener1)
    {
        adapter = new ConfigElementCustomAdapter(activity, Constants.table, listener1);
        cListView.setAdapter(adapter);
        cListView.requestLayout();
    }

    private Boolean saveElementsData()
    {
        Snackbar.make(getView(), getString(R.string.saving) + "...", Snackbar.LENGTH_SHORT).show();
        return SaveListElementsData.dumpMemToInternalStorage(Constants.listElementsData,getActivity());
    }

    @Override
    public void onPause()
    {
        super.onPause();
        saveElementsData();  //to avoid losing changes if user doesn't click on save fab
    }

    @Override
    public void onResume()
    {
        super.onResume();
        refreshTab(getActivity(), ConfigF.this);
    }
}

