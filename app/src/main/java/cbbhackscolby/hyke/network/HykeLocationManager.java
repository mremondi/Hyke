package cbbhackscolby.hyke.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;

import cbbhackscolby.hyke.models.Loc;

/**
 * Created by mremondi on 2/25/17.
 */

public class HykeLocationManager implements LocationListener {

    private Context context;
    private LocationManager locationManager;

    public HykeLocationManager(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public void startLocationMonitoring() throws SecurityException {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, this);
    }


    public void stopLocationMonitoring() throws SecurityException {
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    public LatLng getLastKnownLocation(){
        try {
            try{
                return new LatLng(
                        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude(),
                        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude());
            }catch (NullPointerException e1) {
                return new LatLng(
                        locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude(),
                        locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude());
            }
        } catch (SecurityException e){
            return null;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String uid = auth.getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");
        ref.child(uid).child("location").setValue(new Loc(location.getLatitude(), location.getLongitude()));

        SharedPreferences prefs = context.getSharedPreferences("USER_DATA", 0);
        String group_id = prefs.getString("GROUP_ID", "");
        Log.d("group_id", group_id);
        FirebaseDatabase.getInstance().getReference()
                .child("group_locations")
                .child(group_id)
                .child(uid)
                .setValue(new Loc(location.getLatitude(), location.getLongitude()));

        EventBus.getDefault().post(location);
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
}