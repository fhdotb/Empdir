package com.gmail.otb.fhd.mobileappcoursework.activity;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.gmail.otb.fhd.mobileappcoursework.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class GoogleMapActivity extends FragmentActivity implements GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {


    public String  OfficeID;
    public String photo;
    public String jobTitle;
    public String supervisor;
    public String name;
    public String manager;
    public String phone;
    public String building;
    public String userEmail;
    public  Bundle extras;
    public Context context;
    private GoogleMap mMap;


    private static final LatLng Building58 = new LatLng(50.913758, -1.3309683);
    private static final LatLng Building59 = new LatLng(50.9347783, -1.3930161);
    private static final LatLng MainComps = new LatLng(50.9347783, -1.3930161);


    private Marker Build58;
    private Marker Build59;
    private Marker MainCompus;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);

        extras = getIntent().getExtras();
        if (extras != null) {
            userEmail = extras.getString("userEmail");
            OfficeID = extras.getString("OfficeID");
            photo = extras.getString("photo");
            jobTitle = extras.getString("jobTitle");
            name = extras.getString("name");
            supervisor = extras.getString("supervisor");
            building = extras.getString("building");
            phone = extras.getString("phone");
            manager = extras.getString("manager");
        }

        context = this;

        initGui();
    }


    public void initGui() {

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap)
    {

        mMap = googleMap;

        // Add some markers to the map, and add a data object to each marker.
        Build58 = mMap.addMarker(new MarkerOptions().position(Building58).title("Building 58"));

        Build58.setTag(0);

        Build59 = mMap.addMarker(new MarkerOptions()
                .position(Building59)
                .title("Building 59"));
        Build59.setTag(0);

        MainCompus = mMap.addMarker(new MarkerOptions()
                .position(MainComps)
                .title("Building 42"));
        MainCompus.setTag(0);

        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);
    }

    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this,
                    marker.getTitle() +
                            " has been clicked " + clickCount + " times.",
                    Toast.LENGTH_SHORT).show();
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

}
