package com.jofiagtech.mymapapp;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{
    private GoogleMap mMap;

    private LatLng sydney;
    private LatLng medicineSchool;
    private LatLng lawSchool;

    private Marker sydneyMarker;
    private Marker medicineSchoolMarker;
    private Marker lawSchoolMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        sydney = new LatLng(-34, 151);
        medicineSchool = new LatLng(45.7591014, 3.0871082);
        lawSchool = new LatLng(45.7591337, 3.0717873);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID); //Type of the map

        /*
        * medicineSchoolMarker = mMap.addMarker(new MarkerOptions()
                .position(medicineSchool)
                .title("Medicine School")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        lawSchoolMarker = mMap.addMarker(new MarkerOptions()
                .position(lawSchool)
                .title("Law School")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        * */
        setMarker(medicineSchoolMarker, medicineSchool, "Medicine school", BitmapDescriptorFactory.HUE_GREEN, null);
        setMarker(lawSchoolMarker, lawSchool, "Law school", BitmapDescriptorFactory.HUE_BLUE, null);
        setMarker(sydneyMarker, sydney, "Sydney", 0.8f, null);


        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney)); // Without zooming

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10));// Zooming 1 to 20
    }

    private void setMarker(Marker marker, LatLng latLng, String title, Float iconColor, Float zoomLevel)
    {
        marker = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(title) // The title of the icon (pointer)
                .icon(BitmapDescriptorFactory.defaultMarker(iconColor))// The color of the pointer
                .alpha(zoomLevel));//The visibility of the pointer 0.1 to 0.8
    }
}
