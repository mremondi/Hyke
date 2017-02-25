package cbbhackscolby.hyke.network;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.eventbus.EventBus;

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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1000, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 1000, this);
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