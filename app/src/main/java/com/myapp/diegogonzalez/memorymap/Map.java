package com.myapp.diegogonzalez.memorymap;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Map extends Fragment {

    static final LatLng Current = new LatLng(38.9283, -77.1753);
    private GoogleMap map;
    private MySQLiteHelper admin;

    public Map() {
        // Required empty public constructor
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        map = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map)).getMap();

        // initialize sql admin
        admin = new MySQLiteHelper(this.getActivity());

        // set markers
        setMarkers();

        // Move the camera instantly to hamburg with a zoom of 15.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(Current, 15));

        // Zoom in, animating the camera.
        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setUpMapIfNeeded() {
        if (map == null) {
            map = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

            // set up markers
            setMarkers();

            // Move the camera instantly to hamburg with a zoom of 15.
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(Current, 15));

            // Zoom in, animating the camera.
            map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        }
    }

    public void setMarkers() {
        List<Memory> memories = admin.getAllMemories();
        for (Memory m: memories) {
            String title = m.getTitle();
            String note = m.getNote();
            LatLng position = new LatLng
                    (Double.parseDouble(m.getLatitude()), Double.parseDouble(m.getLongitude()));
            Marker temp = map.addMarker(new MarkerOptions()
            .position(position)
            .title(title)
            .snippet(note));
        }
    }

}
