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
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ConfigElementCustomAdapter extends BaseAdapter
{
    public Context mContext;
    public List<Element> data;
    private LayoutInflater mInflater = null;
    private MyCustomRowButtonListener2 mRowButtonListener;
    private ElementsData tempElementData;

    public ConfigElementCustomAdapter(Context context, List<Element> counts, MyCustomRowButtonListener2 listener1)
    {
        super();
        mContext = context;
        mRowButtonListener = listener1;
        data = counts;
        mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    static class ViewHolder
    {
        EditText name;
        TextView units,increment;
        com.brapeba.suptraining.ToggleButton tb1;
    }


    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;

        if(convertView==null)
        {
            convertView = mInflater.inflate(R.layout.configlist, null);
            holder = new ViewHolder();
            holder.name = (EditText) convertView.findViewById(R.id.lcname);
            holder.units = (EditText) convertView.findViewById(R.id.lcunits);
            holder.increment = (EditText) convertView.findViewById(R.id.lcinc);
            holder.increment.setInputType(InputType.TYPE_CLASS_NUMBER);
            holder.tb1 = (com.brapeba.suptraining.ToggleButton) convertView.findViewById(R.id.tb1);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        tempElementData=Constants.listElementsData.get(data.get(position).getCode()); //getting value of toShow for this element
        holder.name.setText(tempElementData.getName());
        if (tempElementData.getToShow()) holder.tb1.toggleOn(); else holder.tb1.toggleOff(); //displaying the togglebutton accordingly
        holder.units.setText(tempElementData.getUnit());
        holder.increment.setText(Integer.toString(tempElementData.getInc()));
        //setting listeners for edittexts
        holder.name.addTextChangedListener(new CustomTextWatcher(holder.name.getText().toString(), holder.units.getText().toString(), Integer.parseInt(holder.increment.getText().toString()), data.get(position).getCode()));
        holder.units.addTextChangedListener(new CustomTextWatcher(holder.name.getText().toString(), holder.units.getText().toString(), Integer.parseInt(holder.increment.getText().toString()), data.get(position).getCode()));
        holder.increment.addTextChangedListener(new CustomTextWatcher(holder.name.getText().toString(), holder.units.getText().toString(), Integer.parseInt(holder.increment.getText().toString()), data.get(position).getCode()));
        //setting listener for togglebutton
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

    private class CustomTextWatcher implements TextWatcher
    {

        private String name;
        private String units;
        private int inc;
        private int  code;

        public CustomTextWatcher(String s1, String s2, int i1, int code)
        {
            this.name = s1;
            this.units=s2;
            this.inc=i1;
            this.code = code;
        }

        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            Toast.makeText(mContext, "onTextChanged", Toast.LENGTH_SHORT).show();
            tempElementData.setName(this.name);
            tempElementData.setInc(this.inc);
            tempElementData.setUnit(this.units);
            Constants.listElementsData.put(this.code,tempElementData);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
            Toast.makeText(mContext, "beforeTextChanged", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void afterTextChanged(Editable arg0)
        {
            Toast.makeText(mContext, "afterTextChanged", Toast.LENGTH_SHORT).show();
        }
    }

    public interface MyCustomRowButtonListener2
    {
        void onCustomRowButtonClick(Element selectedItem, int position, int action, View view);
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

