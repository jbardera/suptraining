package com.brapeba.suptraining;

/**
 * Created by joanmi on 05-Nov-15.
 */
/**
 * @author      Joanmi Bardera <joanmibb@gmail.com>
 * @version     1.0
 * @since       2015-11-05
 */

import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ListElementCustomAdapter extends BaseAdapter
{
    public Context mContext;
    public List<Element> data;
    private LayoutInflater mInflater = null;
    private MyCustomRowButtonListener mRowButtonListener;

    public ListElementCustomAdapter(Context context, List<Element> counts, MyCustomRowButtonListener listener)
    {
        super();
        mContext = context;
        mRowButtonListener = listener;
        data = counts;
        mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    static class ViewHolder
    {
        TextView name;
        TextView timesdone;
        Button button1, button2;
    }


    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;

        if(convertView==null)
        {
            convertView = mInflater.inflate(R.layout.elementlist, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.lname);
            holder.timesdone = (TextView) convertView.findViewById(R.id.lclicks);
            holder.button1 = (Button) convertView.findViewById(R.id.badd1);
            holder.button2 = (Button) convertView.findViewById(R.id.badd2);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(getItem(position).getName());
        holder.button1.setText("+"+Constants.listElementsData.get(getItem(position).getCode()).getInc());
        holder.timesdone.setText(String.valueOf(getItem(position).getTimesdone()));
        final ViewHolder holdertemp=holder;
        holder.button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mRowButtonListener.onCustomRowButtonClick(getItem(position),position,Constants.listElementsData.get(getItem(position).getCode()).getInc(),holdertemp.button1);
                holdertemp.timesdone.setText(String.valueOf(getItem(position).getTimesdone()));
            }
        });
        holder.button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final AlertDialog aldVal = new AlertDialog.Builder(mContext, R.style.AppTheme_PopupOverlay)
                        .setPositiveButton(mContext.getString(R.string.sum), null)
                        .setNegativeButton(mContext.getString(R.string.cancel), null)
                        .setTitle(mContext.getString(R.string.string1))
                                //.setMessage(getString(R.string.string9))
                        .create();
                final EditText etVal = new EditText(mContext);
                etVal.setHint(String.valueOf(Constants.listElementsData.get(getItem(position).getCode()).getInc()));
                etVal.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
                etVal.setFilters(new InputFilter[]{new InputFilterMinMax(0, 9999)});
                aldVal.setView(etVal);
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
                                if (etVal.getText().toString().isEmpty())
                                {
                                    etVal.setText(String.valueOf(Constants.listElementsData.get(getItem(position).getCode()).getInc()));
                                }
                                try
                                {
                                    mRowButtonListener.onCustomRowButtonClick(getItem(position), position, Integer.parseInt(etVal.getText().toString()), holdertemp.button2);
                                    //Constants.listElementsData.get(getItem(position).getCode()).getInc();
                                    holdertemp.timesdone.setText(String.valueOf(getItem(position).getTimesdone()));
                                } catch (Exception e)
                                {
                                }
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

        return convertView;
    }

    public interface MyCustomRowButtonListener
    {
        void onCustomRowButtonClick(Element selectedItem, int position, int increment, View view);
    }

    public int getCount()
    {
        return data.size();
    }

    public Element getItem(int position)
    {
        return data.get(position);
    }
    public long getItemId(int position)
    {
        return data.indexOf(getItem(position));
    }
}

