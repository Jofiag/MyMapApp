package com.jofiagtech.mymapapp;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{
    private static final String TAG = "MapLoc";
    private GoogleMap mMap;

    private LatLng sydney;
    private LatLng medicineSchool;
    private LatLng lawSchool;

    private Marker sydneyMarker;
    private Marker medicineSchoolMarker;
    private Marker lawSchoolMarker;
    private Marker userMarker;

    private ArrayList<Marker> mMarkerList;

    private LocationManager mLocationManager;
    private LocationListener mLocationListener;

    private LatLng mUserLatlng;

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
        mUserLatlng = getUserLatLng();

        /*if (mUserLatlng != null)
            userLocation = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
        else
            Toast.makeText(this, "Getting position failed !", Toast.LENGTH_LONG).show();*/

        mMarkerList = new ArrayList<>();


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
        medicineSchoolMarker = setMarker(medicineSchool, "Medicine school", BitmapDescriptorFactory.HUE_GREEN, 0.8f);
        lawSchoolMarker = setMarker(lawSchool, "Law school", BitmapDescriptorFactory.HUE_BLUE, 0.8f);
        sydneyMarker = setMarker(sydney, "Sydney", BitmapDescriptorFactory.HUE_RED, 0.8f);

        mMarkerList.add(medicineSchoolMarker);
        mMarkerList.add(lawSchoolMarker);
        mMarkerList.add(sydneyMarker);

        if (mUserLatlng != null){
            userMarker = setMarker(mUserLatlng, "My position", 0.0f, 0.8f);
            mMarkerList.add(userMarker);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userMarker.getPosition(), 8));
        }
        else
            Toast.makeText(MapsActivity.this, "Getting this phone Location failed !!!", Toast.LENGTH_LONG).show();



        for (Marker marker : mMarkerList) {
            Log.d(TAG, "onMapReady: " + marker.getTitle());
        }


        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney)); // Without zooming
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10));// Zooming 1 to 20
    }

    private Marker setMarker(LatLng latLng, String title, Float iconColor, Float transparencyLevel)
    {
        Marker marker;

        marker = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(title) // The title of the icon (pointer)
                .icon(BitmapDescriptorFactory.defaultMarker(iconColor))// The color of the pointer
                .alpha(transparencyLevel));//The visibility of the pointer 0.1 to 0.8

        return marker;
    }

    private void setUserLocation(LatLng latLng){
        mUserLatlng = latLng;
    }
    private LatLng getUserLatLng()
    {
        final LatLng usr;
        mLocationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        mLocationListener = new LocationListener()
        {
            @Override
            public void onLocationChanged(Location location) {
                setUserLocation(new LatLng(location.getLatitude(), location.getLongitude()));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras)
            {

            }

            @Override
            public void onProviderEnabled(String provider)
            {

            }

            @Override
            public void onProviderDisabled(String provider)
            {

            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
        else{
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                0, 0, mLocationListener);

            Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return new LatLng(location.getLatitude(), location.getLongitude());
        }

        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int accessFineLocation = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if (accessFineLocation == PackageManager.PERMISSION_GRANTED)
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        0,0, mLocationListener);
        }
    }
}
