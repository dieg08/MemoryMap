package com.myapp.diegogonzalez.memorymap;


import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewMemory extends Fragment {

    private MySQLiteHelper admin;
    private LocationManager locationManager;
    private Location location;
    private double latitude;
    private double longitude;

    public NewMemory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_memory, container, false);
        admin = new MySQLiteHelper(this.getActivity());
        Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addMemory();
            }
        });
        locationManager = (LocationManager) getActivity().getApplicationContext()
                .getSystemService(Context.LOCATION_SERVICE);
        return view;
    }

    public void addMemory() {
        // Get Location
        getMyLocation();

        // Get EditText views
        EditText txt = (EditText) getView().findViewById(R.id.editText);
        EditText txt2 = (EditText) getView().findViewById(R.id.editText2);

        // Extract Strings from EditText views
        String title = txt.getText().toString();
        String note = txt2.getText().toString();
        String lat = "" + latitude;
        String lon = "" + longitude;

        // Buid memory object
        Memory memory = new Memory();
        memory.setTitle(title);
        memory.setNote(note);
        memory.setLatitude(lat);
        memory.setLongitude(lon);

        // Add memory
        admin.addMemory(memory);

        // Show Toast
        Toast.makeText(getActivity(), "Memory added!",
                Toast.LENGTH_SHORT).show();
        // Update ListView fragment
        String tagName = "android:switcher:" + R.id.pager + ":" + 1; // Your pager name & tab no of Second Fragment
        Memories f2 = (Memories) getActivity().getSupportFragmentManager().findFragmentByTag(tagName);
        f2.listMemories();

        // Update Map fragment
        String mpTag = "android:switcher:" + R.id.pager + ":" + 2;
        Map map = (Map) getActivity().getSupportFragmentManager().findFragmentByTag(mpTag);
        map.setMarkers();

        // Clearing editText boxes
        txt.setText("");
        txt2.setText("");
    }

    public void getMyLocation() {
        List<String> providers = locationManager.getProviders(true);

        Location l = null;
        for (int i = 0; i < providers.size(); i++) {
            l = locationManager.getLastKnownLocation(providers.get(i));
            if (l != null)
                break;
        }
        if (l != null) {
            latitude = l.getLatitude();
            longitude = l.getLongitude();
        }
    }
}
