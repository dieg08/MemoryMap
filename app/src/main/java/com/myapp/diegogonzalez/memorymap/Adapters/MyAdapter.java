package com.myapp.diegogonzalez.memorymap.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.myapp.diegogonzalez.memorymap.R;
import com.myapp.diegogonzalez.memorymap.Utils.Memory;

import java.util.ArrayList;

/**
 * Created by Diego Gonzalez on 8/13/2015.
 */
public class MyAdapter extends BaseAdapter {

    /* Application context holder*/
    private Context context;
    /* List of all memories*/
    private ArrayList<Memory> memories;
    /* The inflator tool to inflate a view*/
    private LayoutInflater inflater;

    public MyAdapter(Context context, ArrayList<Memory> memories) {
        this.context = context;
        this.memories = memories;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return memories.size();
    }

    @Override
    public Object getItem(int position) {
        return memories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Memory memory = memories.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(memory.getId() + ". " + memory.getTitle());

        return convertView;
    }

    private class ViewHolder {
        TextView title;

        public ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.memory_title);
        }
    }
}
