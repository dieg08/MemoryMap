package com.myapp.diegogonzalez.memorymap.Fragments;


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

import com.myapp.diegogonzalez.memorymap.Utils.Memory;
import com.myapp.diegogonzalez.memorymap.Utils.MySQLiteHelper;
import com.myapp.diegogonzalez.memorymap.R;

import java.util.List;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewMemory extends Fragment {

    private MySQLiteHelper admin;
    private LocationManager locationManager;
    private Button button;
    private double latitude;
    private double longitude;
    private EditText title;
    private EditText note;
    private static final String SHOWCASE_ID = "new memory";

    public NewMemory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_memory, container, false);

        // Initialize variables
        admin = new MySQLiteHelper(this.getActivity());
        locationManager = (LocationManager) getActivity().getApplicationContext()
                .getSystemService(Context.LOCATION_SERVICE);
        button = (Button) view.findViewById(R.id.button);
        title = (EditText) view.findViewById(R.id.editText);
        note =(EditText) view.findViewById(R.id.editText2);


        // Set the onClickListener for the button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMemory();
            }
        });

        // Method to show app tutorial
        presentShowcaseView(1000);
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

        // Letting user know memory adding was successful
        Toast.makeText(getActivity(), "Memory added!", Toast.LENGTH_SHORT).show();

        // Clearing editText boxes
        txt.setText("");
        txt2.setText("");
    }

    public void getMyLocation() {
        Location l = null;

        // Returns a list of location providers from the phone
        List<String> providers = locationManager.getProviders(true);

        // From the list of location providers, get the last know location
        for (int i = 0; i < providers.size(); i++) {
            l = locationManager.getLastKnownLocation(providers.get(i));
            if (l != null)
                break;
        }

        // Set Lat and long fields
        if (l != null) {
            latitude = l.getLatitude();
            longitude = l.getLongitude();
        }
    }

    private void presentShowcaseView(int delay) {
        // Used tutorial screen in app
        ShowcaseConfig config = new ShowcaseConfig();

//        // Setting the delay time before the tutorial pops up
//        config.setDelay(delay);
//        config.setMaskColor(getResources().getColor(R.color.textColorPrimary));
//        config.setContentTextColor(getResources().getColor(R.color.colorAccent));
//        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(getActivity(), SHOWCASE_ID);
//
//        sequence.setConfig(config);
//
//        sequence.addSequenceItem(title,
//                "Enter the title of your memory", "Got it");
//
//        sequence.addSequenceItem(note,
//                "Enter your full memory here", "Got it");
//
//        sequence.addSequenceItem(button,
//                "This button saves your memory", "Got it");
//
//        sequence.start();

        new MaterialShowcaseView.Builder(getActivity())
                .setTarget(button)
                .setDismissText("Got it")
                .setContentText("This button saves your memory")
                .setContentTextColor(getResources().getColor(R.color.colorAccent))
                .setMaskColour(getResources().getColor(R.color.textColorPrimary))
                .setDelay(delay)
                .singleUse(SHOWCASE_ID)
                .show();
    }
}
