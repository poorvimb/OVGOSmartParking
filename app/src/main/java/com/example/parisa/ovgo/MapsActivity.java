package com.example.parisa.ovgo;

import androidx.annotation.NonNull;


import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {


    private GoogleMap mMap;
    LatLng latLngOfG9 = new LatLng(52.1395, 11.6418);
    LatLng latLngOfFin = new LatLng(52.1393, 11.6457);
    Marker lotMarker, finMarker; //Markers on two parking areas at OVGU campus
    LocationManager locationManager;
    LocationListener locationListener;
    LatLng currentLocation;

    int distanceFromSettings;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //checking to see if the permission is granted and then updating the location.
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        /*
        This part gets the maximum distance value that user gives in the settings fragment.
        This value is used to show the parking zones within the radius that user wants.
         */
        SharedPreferences preferences = getSharedPreferences("com.example.parisa.ovgo",Context.MODE_PRIVATE);
        distanceFromSettings = preferences.getInt("distanceFromSettings",10); //default value is 10 KM.
        Log.i("infoSettings", String.valueOf(distanceFromSettings));


        lotMarker = mMap.addMarker(new MarkerOptions().position(latLngOfG9).title("Parking zone 1").icon(BitmapDescriptorFactory.fromResource(R.drawable.placemarker)).zIndex(1.0f));
        finMarker = mMap.addMarker(new MarkerOptions().position(latLngOfFin).title("Parking zone 2").icon(BitmapDescriptorFactory.fromResource(R.drawable.placemarker)));


        mMap.setOnMarkerClickListener(this);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngOfG9, 15));
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {


            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Log.i("User Location", lastKnownLocation.toString());
            // Add a marker in current location.
            LatLng currentLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        }

    }

    /*
    This method defines how the app should react when the user clicks a marker on the app
    which is in this case will redirect the user to the parking lot information and status
    of the parking sensors.
     */

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.equals(lotMarker)) {
            Intent intent = new Intent(getApplicationContext(), ParkingLotActivity.class);
            startActivity(intent);
            return true;
        } else if (marker.equals(finMarker)) {
            Toast.makeText(MapsActivity.this, "This parking area is not active.", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    /* This method calculates the exact distance between two points on the map
    and in this context it is used to calculate distance between current location and the
    location of parking lots to be used to make the zones within this radius visible for
    the user.
     */

    public double calculationByDistance(@NotNull LatLng StartP, @NotNull LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }


}
