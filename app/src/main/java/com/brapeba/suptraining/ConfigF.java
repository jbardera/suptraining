package com.brapeba.suptraining;

/**
 * Created by joanmi on 10-Nov-15.
 */

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

public class ConfigF extends Fragment implements ConfigElementCustomAdapter.MyCustomRowButtonListener2
{
    private static ListView cListView;
    private static ConfigElementCustomAdapter adapter;
    public FloatingActionButton fab;
    private ImageButton addButton;

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
        // updating the setting
        Constants.listElementsData.put(selectedItem.getCode(), tempElementsData);
        // updating the table, to show/do not show this element accordingly
        if (istoggled) Constants.tableSession.add(selectedItem); else Constants.tableSession.remove(selectedItem);
        //notice we have overriden <equals> method at <Element.java> class to work <remove(selectedItem)>!!
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.config_f, container, false);
        cListView = (ListView) rootView.findViewById(android.R.id.list);
        cListView.setChoiceMode(ListView.CHOICE_MODE_NONE);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ConfigElementCustomAdapter(getActivity(), Constants.tableConfig, ConfigF.this);
        // cListView.setAdapter(adapter); // NO NEED HERE AS IT IS @OnResume via refreshTab()
        cListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        cListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id)
            {
                final int pos = position;
                final ElementsData thisElementData = Constants.listElementsData.get(Constants.tableConfig.get(pos).getCode());
                //Toast.makeText(getActivity(), "Element #" + pos + "=" + thisElementData.getName(), Toast.LENGTH_SHORT).show();
                final AlertDialog aldVal = new AlertDialog.Builder(getActivity(), R.style.AppTheme_PopupOverlay)
                        .setPositiveButton(getString(R.string.ok), null)
                        .setNegativeButton(getString(R.string.cancel), null)
                        .setTitle(getString(R.string.string8))
                                //.setMessage(getString(R.string.string8))
                        .create();
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View adView = inflater.inflate(R.layout.configdialog, null);
                final EditText qetName = (EditText) adView.findViewById(R.id.qadname);
                final EditText qetUnits = (EditText) adView.findViewById(R.id.qadunits);
                final EditText qetInc = (EditText) adView.findViewById(R.id.qadinc);
                qetInc.setInputType(InputType.TYPE_CLASS_NUMBER);
                qetName.setHint(thisElementData.getName());
                qetUnits.setHint(thisElementData.getUnit());
                qetInc.setHint(Integer.toString(thisElementData.getInc()));
                aldVal.setView(adView);
                aldVal.setOnShowListener(new DialogInterface.OnShowListener()
                {
                    @Override
                    public void onShow(DialogInterface dialog)
                    {
                        final Button btnAccept = aldVal.getButton(AlertDialog.BUTTON_POSITIVE);
                        btnAccept.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                if (!qetName.getText().toString().isEmpty())
                                    thisElementData.setName(qetName.getText().toString());
                                if (!qetUnits.getText().toString().isEmpty())
                                    thisElementData.setUnit(qetUnits.getText().toString());
                                if (!qetInc.getText().toString().isEmpty())
                                    thisElementData.setInc(Integer.valueOf(qetInc.getText().toString()));
                                Constants.listElementsData.put(Constants.tableConfig.get(pos).getCode(), thisElementData);
                                refreshTab(getActivity(), ConfigF.this);
                                aldVal.dismiss();
                            }
                        });
                        final Button btnDecline = aldVal.getButton(DialogInterface.BUTTON_NEGATIVE);
                        btnDecline.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                aldVal.dismiss();
                            }
                        });
                    }
                });
                aldVal.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                aldVal.show();
            }
        });
        fab = (FloatingActionButton) view.findViewById(R.id.cfabld);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View view)
            {
                Snackbar.make(getView(), getString(R.string.saving) + "...", Snackbar.LENGTH_SHORT).show();
                saveElementsData();
            }
        });
        addButton=(ImageButton)view.findViewById(R.id.baddni);
        addButton.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View view)
            {
                final AlertDialog aldVal = new AlertDialog.Builder(getActivity(), R.style.AppTheme_PopupOverlay)
                        .setPositiveButton(getString(R.string.ok), null)
                        .setNegativeButton(getString(R.string.cancel), null)
                        .setTitle(getString(R.string.string7))
                                //.setMessage(getString(R.string.string8))
                        .create();
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View adView = inflater.inflate(R.layout.configdialog, null);
                final EditText qetName = (EditText) adView.findViewById(R.id.qadname);
                final EditText qetUnits = (EditText) adView.findViewById(R.id.qadunits);
                final EditText qetInc = (EditText) adView.findViewById(R.id.qadinc);
                qetInc.setInputType(InputType.TYPE_CLASS_NUMBER);
                qetName.setHint(getString(R.string.string10));
                qetUnits.setHint(getString(R.string.units));
                qetInc.setHint("100");
                aldVal.setView(adView);
                aldVal.setOnShowListener(new DialogInterface.OnShowListener()
                {
                    @Override
                    public void onShow(DialogInterface dialog)
                    {
                        final Button btnAccept = aldVal.getButton(AlertDialog.BUTTON_POSITIVE);
                        btnAccept.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                String newEName=qetName.getText().toString();
                                String newEUnits=qetUnits.getText().toString();
                                String newEInc=qetInc.getText().toString();
                                if (newEName.isEmpty()) newEName=getString(R.string.string10);
                                if (newEUnits.isEmpty()) newEUnits=getString(R.string.units);
                                if (newEInc.isEmpty()) newEInc="100";
                                ElementsData newElementsData=new ElementsData(newEName,newEUnits,Integer.valueOf(newEInc));
                                Constants.tableConfig.add(new Element(Constants.listElementsData.size()));  //adding new code=Constants.listElementsData.size() to listview
                                Constants.listElementsData.put(Constants.listElementsData.size(), newElementsData); //adding the elementdata to the list (stored)
                                adapter.notifyDataSetChanged();
                                refreshTab(getActivity(), ConfigF.this);
                                aldVal.dismiss();
                            }
                        });
                        final Button btnDecline = aldVal.getButton(DialogInterface.BUTTON_NEGATIVE);
                        btnDecline.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                aldVal.dismiss();
                            }
                        });
                    }
                });
                aldVal.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                aldVal.show();
            }
        });
    }

    private static void refreshTab(Activity activity, ConfigElementCustomAdapter.MyCustomRowButtonListener2 listener1)
    {
        adapter = new ConfigElementCustomAdapter(activity, Constants.tableConfig, listener1);
        cListView.setAdapter(adapter);
        cListView.requestLayout();
    }

    private Boolean saveElementsData()
    {
        return SaveListElementsData.dumpMemToInternalStorage(Constants.listElementsData, getActivity());
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

