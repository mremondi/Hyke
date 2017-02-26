package cbbhackscolby.hyke.fragments;

import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.HashMap;
import cbbhackscolby.hyke.R;
import cbbhackscolby.hyke.models.User;
import cbbhackscolby.hyke.network.HykeLocationManager;

public class NearMeFragment extends Fragment implements OnMapReadyCallback {

    private HashMap<String, Marker> markerUserIdHashMap;
    private LatLng location;
    private MapView mapView;
    private GoogleMap googleMap;
    private HykeLocationManager hykeLocationManager;
    public boolean firstZoom = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.near_me_fragment, null, false);

        markerUserIdHashMap = new HashMap<>();
        hykeLocationManager = new HykeLocationManager(this.getContext());
        location = hykeLocationManager.getLastKnownLocation();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        final String uid = auth.getCurrentUser().getUid();
        SharedPreferences prefs = getActivity().getSharedPreferences("USER_DATA", 0);
        String group_id = prefs.getString("GROUP_ID", "");

        GeoFire geoFire = new GeoFire(FirebaseDatabase.getInstance().getReference().child("group_locations").child(group_id));
        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(location.latitude, location.longitude), 50);
        Log.d("GeoQuery","" + geoQuery.getCenter().latitude + ", " + geoQuery.getCenter().longitude );
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                Log.d("on key entered", ""+ location.latitude);
                if(key.equals(uid)){}
                else {
                    addFriendMarker(key, location);
                }
            }

            @Override
            public void onKeyExited(String key) {
                //Toast.makeText(getContext(), "One of your members has left your map.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
                Log.d("on key moved", "" + location.latitude);
                if(key.equals(uid)){

                } else {
                    if (markerUserIdHashMap.containsKey(key)) {
                        animateFriendMarker(key, location);
                    } else {
                        addFriendMarker(key, location);
                    }
                }
            }

            @Override
            public void onGeoQueryReady() {
                // not sure what this does
                Log.d("HERE", "In on ready");
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {
                Log.d("ERROR", error.getMessage());
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    @Subscribe
    public void onLocationReceived(Location location) {
        this.location = new LatLng(location.getLatitude(), location.getLongitude());
        loadLocationByLatLng();
    }

    public void loadLocationByLatLng(){
        if (firstZoom){
            firstZoom = false;
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(this.location, (float)19));
        }
        else{
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(this.location, googleMap.getCameraPosition().zoom));
        }
    }

    public void addFriendMarker(final String uid, GeoLocation location){
        final LatLng latLng = new LatLng(location.latitude, location.longitude);
        FirebaseDatabase.getInstance().getReference("users").child(uid).child("distress").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if ((boolean)dataSnapshot.getValue() == true){
                    FirebaseDatabase.getInstance().getReference("users").child(uid).child("fullName").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            markerUserIdHashMap.put(uid, googleMap.addMarker(new MarkerOptions().position(latLng)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                                    .title(dataSnapshot.getValue().toString())));
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    markerUserIdHashMap.put(uid, googleMap.addMarker(new MarkerOptions().position(latLng)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).title(uid)));                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void animateFriendMarker(String uid, GeoLocation location){
        final double lat = location.latitude;
        final double lng = location.longitude;
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final long DURATION_MS = 3000;
        final Interpolator interpolator = new AccelerateDecelerateInterpolator();
        final Marker marker = markerUserIdHashMap.get(uid);
        final LatLng startPosition = marker.getPosition();
        handler.post(new Runnable() {
            @Override
            public void run() {
                float elapsed = SystemClock.uptimeMillis() - start;
                float t = elapsed/DURATION_MS;
                float v = interpolator.getInterpolation(t);

                double currentLat = (lat - startPosition.latitude) * v + startPosition.latitude;
                double currentLng = (lng - startPosition.longitude) * v + startPosition.longitude;
                marker.setPosition(new LatLng(currentLat, currentLng));

                // if animation is not finished yet, repeat
                if (t < 1) {
                    handler.postDelayed(this, 16);
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        try {
            googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this.getContext(), R.raw.style_json));
            googleMap.setMyLocationEnabled(true);
            if (location != null){
                loadLocationByLatLng();
            }
        }catch (SecurityException e){}
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}