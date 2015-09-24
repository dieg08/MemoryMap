package com.myapp.diegogonzalez.memorymap.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.myapp.diegogonzalez.memorymap.Adapters.MyAdapter;
import com.myapp.diegogonzalez.memorymap.Adapters.PagerAdapter;
import com.myapp.diegogonzalez.memorymap.R;
import com.myapp.diegogonzalez.memorymap.Utils.Memory;
import com.myapp.diegogonzalez.memorymap.Utils.MySQLiteHelper;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Memories extends Fragment {

    private View view;
    private MyAdapter mAdapter;
    private MySQLiteHelper admin;

    public Memories() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_memories, container, false);

        // Initialize parameters
        admin = new MySQLiteHelper(this.getActivity());

        listMemories();
        return view;
    }

    public void listMemories(){
        ListView list = (ListView) view.findViewById(R.id.listView);
        ArrayList<Memory> memories = admin.getAllMemories();

        // Initialize adapter
        mAdapter = new MyAdapter(view.getContext(), memories);

        // Set Adapter
        list.setAdapter(mAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewPager pager = (ViewPager) getActivity().findViewById(R.id.pager);
                PagerAdapter adapter = (PagerAdapter) pager.getAdapter();
                adapter.setCamera(position + 1);
                pager.setCurrentItem(2);

            }
        });
    }
}
