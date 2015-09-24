package com.myapp.diegogonzalez.memorymap.Adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.Toast;

import com.myapp.diegogonzalez.memorymap.Fragments.Map;
import com.myapp.diegogonzalez.memorymap.Fragments.Memories;
import com.myapp.diegogonzalez.memorymap.Fragments.NewMemory;

import java.util.HashMap;

/**
 * Created by Diego Gonzalez on 9/16/2015.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    private int tabNumber;
    private HashMap<Integer, Fragment> fragMap;
    private Context context;

    public PagerAdapter(FragmentManager fm, int tabNumber, Context context) {
        super(fm);
        this.tabNumber = tabNumber;
        this.context = context;
        fragMap = new HashMap<Integer, Fragment>();
    }

    @Override
    public void notifyDataSetChanged() {
        Memories temp = (Memories) fragMap.get(1);
        Map temp2= (Map) fragMap.get(2);
        if (temp != null) {
            temp.listMemories();
        } else {
            Toast.makeText(context, "Memories tab could not be retrieved", Toast.LENGTH_SHORT).show();
        }
        if (temp2 != null) {
            temp2.setMarkers();
        } else {
            Toast.makeText(context, "Map tab could not be retrieved", Toast.LENGTH_SHORT).show();
        }
        super.notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (fragMap.containsKey(0)) {
                    return fragMap.get(0);
                } else {
                    NewMemory tab1 = new NewMemory();
                    fragMap.put(0, tab1);
                    return tab1;
                }
            case 1:
                if (fragMap.containsKey(1)) {
                    Memories temp = (Memories) fragMap.get(1);
                    temp.listMemories();
                    return temp;
                } else {
                    Memories tab2 = new Memories();
                    fragMap.put(1, tab2);
                    return tab2;
                }
            case 2:
                if (fragMap.containsKey(2)) {
                    return fragMap.get(2);
                } else {
                    Map tab3 = new Map();
                    fragMap.put(2, tab3);
                    return tab3;
                }
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabNumber;
    }

    public void setCamera(int id) {
        Map temp2= (Map) fragMap.get(2);
        if (temp2 != null) {
            temp2.setCurrent(id);
        } else {
            Toast.makeText(context, "Map tab could not be retrieved", Toast.LENGTH_SHORT).show();
        }
    }
}
