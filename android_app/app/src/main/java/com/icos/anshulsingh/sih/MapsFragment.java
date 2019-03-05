package com.icos.anshulsingh.sih;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback {

    public GoogleMap map;
    public double currentLongitude;
    public double currentLatitude;
    Integer iter = 0;
    ArrayList<accident_data> list = new ArrayList<>();
    ArrayList<accident_data> labels = new ArrayList<>();

    public MapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_maps, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);
        return v;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        map = googleMap;


        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        map.setMyLocationEnabled(true);


//        getDeviceLocation();
//        Log.d("hey",String.valueOf(currentLatitude));
//        Log.d("hey",String.valueOf(currentLongitude));
        LatLng pp1 = new LatLng(currentLatitude, currentLongitude);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                pp1, 16f

        ));


        FirebaseDatabase.getInstance().getReference().child("Accident Data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    final accident_data val = data.getValue(accident_data.class);
                    labels.add(val);

                }
                Log.d("heysize",String.valueOf(labels.size()));

                LatLng loc = new LatLng(1.3521, 103.8198);
                map.addMarker(new MarkerOptions().position(loc));


                showonmap();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getDeviceLocation() {
        //get location manger instance
        final LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


        //check if we have permission to access device location
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }




        //add location change listener with update duration 2000 millicseconds or 10 meters
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, new LocationListener() {
            public void onLocationChanged(Location location) {
//                if (location == null) {
//                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                        // TODO: Consider calling
//                        //    ActivityCompat#requestPermissions
//                        // here to request the missing permissions, and then overriding
//                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                        //                                          int[] grantResults)
//                        // to handle the case where the user grants the permission. See the documentation
//                        // for ActivityCompat#requestPermissions for more details.
//                        return;
//                    }
//                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                }
//                if (location != null){
//                    currentLatitude = location.getLatitude();
//                    currentLongitude = location.getLongitude();
//
//
//                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }

        });


        //get last known location to start with
        Location myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        currentLatitude = myLocation.getLatitude();
        currentLongitude = myLocation.getLongitude();
    }

    void showonmap(){
        for(int i=0;i<labels.size();i++)
        {
            if(labels.get(i).acc_val < 30){

                LatLng loc = new LatLng(labels.get(i).lat, labels.get(i).lon);
                map.addMarker(new MarkerOptions().position(loc)
                        .title(labels.get(i).lat+","+labels.get(i).lon).icon(BitmapDescriptorFactory.fromResource(R.drawable.safe_updated)));

            }

            if(labels.get(i).acc_val > 30 && labels.get(i).acc_val < 60){

                LatLng loc = new LatLng(labels.get(i).lat, labels.get(i).lon);
                map.addMarker(new MarkerOptions().position(loc)
                        .title(labels.get(i).lat+","+labels.get(i).lon).icon(BitmapDescriptorFactory.fromResource(R.drawable.moderate_updated)));

            }

            if(labels.get(i).acc_val > 60){

                LatLng loc = new LatLng(labels.get(i).lat, labels.get(i).lon);
                map.addMarker(new MarkerOptions().position(loc)
                        .title(labels.get(i).lat+","+labels.get(i).lon).icon(BitmapDescriptorFactory.fromResource(R.drawable.danger)));

            }
        }


    }

}
