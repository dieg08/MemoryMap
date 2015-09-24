package com.myapp.diegogonzalez.memorymap.Fragments;


import android.annotation.TargetApi;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.myapp.diegogonzalez.memorymap.R;
import com.myapp.diegogonzalez.memorymap.Utils.Memory;
import com.myapp.diegogonzalez.memorymap.Utils.MySQLiteHelper;
import com.myapp.diegogonzalez.memorymap.Utils.NetworkUtil;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Map extends Fragment {

    private LatLng current;
    private double latitude;
    private double longitude;
    private LocationManager locationManager;
    private GoogleMap map;
    private MySQLiteHelper admin;

    public Map() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        map = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map)).getMap();

        // initialize fields
        admin = new MySQLiteHelper(this.getActivity());
        locationManager = (LocationManager) getActivity().getApplicationContext()
                .getSystemService(Context.LOCATION_SERVICE);
        getMyLocation();

        // setting camera to current location
        current = new LatLng(latitude, longitude);

        if (map != null) {
            // set markers
            setMarkers();

            // Move the camera instantly to hamburg with a zoom of 15.
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 15));

            // Zoom in, animating the camera.
            map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {

        if (map != null) {
            // set up markers
            setMarkers();

            // Move the camera instantly to current location with a zoom of 15.
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 15));

            // Zoom in, animating the camera.
            map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        }
    }

    public void setMarkers() {
        List<Memory> memories = admin.getAllMemories();
        if (memories != null) {
            for (Memory m : memories) {
                String title = m.getTitle();
                String note = m.getNote();
                LatLng position = new LatLng
                        (Double.parseDouble(m.getLatitude()), Double.parseDouble(m.getLongitude()));
                if (map != null) {
                    Marker temp = map.addMarker(new MarkerOptions()
                            .position(position)
                            .title(title)
                            .snippet(note));
                }
            }
        }
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

    public void setCurrent(int id) {
        Memory temp = admin.getMemory(id);
        Toast.makeText(getActivity(), "(" + temp.getLatitude() + ", " + temp.getLongitude() +")", Toast.LENGTH_SHORT).show();
        current = new LatLng(Double.parseDouble(temp.getLatitude()), Double.parseDouble(temp.getLongitude()));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 25));
    }
}
