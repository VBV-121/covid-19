package com.vaibhav.covid19.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;

import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.vaibhav.covid19.R;

public class HomeFragment extends Fragment {

    protected LocationManager locationManager;
    private Button tapButton;
    private FrameLayout fl;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        TextView t1 = root.findViewById(R.id.t1);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.kiratcommunications.com"));
                startActivity(browserIntent);
            }
        });
        isLocationEnabled();
        fl = (FrameLayout) root.findViewById(R.id.fragment_container);

        tapButton = (Button) root.findViewById(R.id.tapButton);

        tapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLoc();
            }
        });

        return root;
    }



    private void getLoc() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions();
            return;
        }
        final Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        final LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {

                // getting location of user
                final double latitude = location.getLatitude();
                final double longitude = location.getLongitude();
                // do something with Latlng

                try {
                    fl.removeAllViews();
                }catch (Exception e){
                    Log.i("error",e.getMessage());
                }
                Bundle b = new Bundle();
                b.putString("latitude", String.valueOf(latitude));
                b.putString("longitude",String.valueOf(longitude));

                FormFragment ff = new FormFragment();
                ff.setArguments(b);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, ff);
                fragmentTransaction.commit();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
                // do something
            }

            @Override
            public void onProviderDisabled(String provider) {
                // notify user "GPS or Network provider" not available
            }
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 500, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 500, locationListener);



//        Intent intent = new Intent(this,formFragment.class);
//        //Toast.makeText(this, "temp Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude(), Toast.LENGTH_SHORT).show();
//        Log.i("temp Latitude:",String.valueOf(location.getLatitude()));
//        intent.putExtra("Latitude",String.valueOf(location.getLatitude()));
//        intent.putExtra("Longitude",String.valueOf(location.getLongitude()));
//        startActivity(intent);
    }


    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                getActivity(),
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                1
        );
    }

}
