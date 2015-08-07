package com.myapp.diegogonzalez.memorymap;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Memories extends Fragment {

    private View view;
    private ArrayAdapter<String> arrayAdapter;
    private MySQLiteHelper admin;

    public Memories() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        admin = new MySQLiteHelper(this.getActivity());
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_memories, container, false);
        listMemories();
        return view;
    }

    public void listMemories(){
        ListView list = (ListView) view.findViewById(R.id.listView);
        ArrayList<String> entries = new ArrayList<String>();
        List<Memory> memories = admin.getAllMemories();
        for (Memory m: memories) {
            entries.add(m.toString());
        }
        arrayAdapter =
                new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1, entries);
        list.setAdapter(arrayAdapter);
    }
}
