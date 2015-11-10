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
import java.util.Map;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
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
import android.widget.ToggleButton;

public class ConfigElementCustomAdapter extends BaseAdapter
{
    public Context mContext;
    public List<Element> data;
    private LayoutInflater mInflater = null;
    private MyCustomRowButtonListener2 mRowButtonListener;
    private ElementsData tempElementData;

    public ConfigElementCustomAdapter(Context context, List<Element> counts, MyCustomRowButtonListener2 listener)
    {
        super();
        mContext = context;
        mRowButtonListener = listener;
        data = counts;
        mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    static class ViewHolder
    {
        TextView name,units,increment;
        com.brapeba.suptraining.ToggleButton tb1;
    }


    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;

        if(convertView==null)
        {
            convertView = mInflater.inflate(R.layout.configlist, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.lcname);
            holder.units = (TextView) convertView.findViewById(R.id.lcunits);
            holder.increment = (TextView) convertView.findViewById(R.id.lcinc);
            holder.tb1 = (com.brapeba.suptraining.ToggleButton) convertView.findViewById(R.id.tb1);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(getItem(position).getName());
        tempElementData=Constants.listElementsData.get(data.get(position).getCode()); //getting value of toShow for this element
        if (tempElementData.getToShow()) holder.tb1.toggleOn(); else holder.tb1.toggleOff(); //displaying the togglebutton accordingly
        holder.units.setText(tempElementData.getUnit());
        holder.increment.setText(Integer.toString(tempElementData.getInc()));

        final ViewHolder holdertemp=holder;
        final com.brapeba.suptraining.ToggleButton temptb1 = holder.tb1;
        holder.tb1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                temptb1.toggle();
                //mRowButtonListener.onCustomRowButtonClick(getItem(position), position, 0, holdertemp.???);
            }
        });

        return convertView;
    }

    public interface MyCustomRowButtonListener2
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

