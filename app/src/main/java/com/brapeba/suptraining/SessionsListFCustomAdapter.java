package com.brapeba.suptraining;

/**
 * Created by joanmi on 09-Nov-15.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SessionsListFCustomAdapter extends BaseAdapter
{
    public Context mContext;
    public List<Session> data;
    private LayoutInflater mInflater = null;

    public SessionsListFCustomAdapter(Context context,List<Session> mySessions)
    {
        super();
        mContext = context;
        data = mySessions;
        mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    static class ViewHolder
    {
        TextView name;
        TextView date;
    }


    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;

        if(convertView==null)
        {
            convertView = mInflater.inflate(R.layout.sessionslist, null);
            holder = new ViewHolder();
            holder.date = (TextView) convertView.findViewById(R.id.ldate);
            holder.name = (TextView) convertView.findViewById(R.id.lname);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.date.setText(getItem(position).getDate().toLocaleString());
        holder.name.setText((getItem(position).getName()).split("#")[1]);

        return convertView;
    }

    public int getCount()
    {
        return data.size();
    }

    public Session getItem(int position)
    {
        return data.get(position);
    }
    public long getItemId(int position)
    {
        return data.indexOf(getItem(position));
    }
}


