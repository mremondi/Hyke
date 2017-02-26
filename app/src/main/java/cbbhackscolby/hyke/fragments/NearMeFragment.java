package cbbhackscolby.hyke.fragments;

import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;

import cbbhackscolby.hyke.R;
import cbbhackscolby.hyke.network.HykeLocationManager;

public class NearMeFragment extends Fragment implements OnMapReadyCallback {

    private HashMap<String, Marker> markerUserIdHashMap;
    private LatLng location;
    private MapView mapView;
    private GoogleMap googleMap;
    private HykeLocationManager hykeLocationManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.near_me_fragment, null, false);

        markerUserIdHashMap = new HashMap<>();

        hykeLocationManager = new HykeLocationManager(this.getContext());
        location = hykeLocationManager.getLastKnownLocation();

        // Listen to everyone in your group's location

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String uid = user.getUid();
        SharedPreferences prefs = getActivity().getSharedPreferences("USER_DATA", 0);
        String group_id = prefs.getString("GROUP_ID", "");

        GeoFire geoFire = new GeoFire(FirebaseDatabase.getInstance().getReference().child("group_locations").child(group_id));
        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(location.latitude, location.longitude), 1.6);
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                // do nothing
                addFriendMarker(key, location);
            }

            @Override
            public void onKeyExited(String key) {
                Toast.makeText(getContext(), "One of your members has left your map.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
                animateFriendMarker(key, location);
            }

            @Override
            public void onGeoQueryReady() {
                // not sure what this does
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {
                // do nothing
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
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(this.location, (float)12));

        // get locations from fire base
    }

    public void addFriendMarker(String uid, GeoLocation location){
        LatLng latLng = new LatLng(location.latitude, location.longitude);
        markerUserIdHashMap.put(uid, googleMap.addMarker(new MarkerOptions().position(latLng).title(uid)));
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
            googleMap.setMyLocationEnabled(true);
            if (location != null){
                loadLocationByLatLng();
            }
//            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//                @Override
//                public void onInfoWindowClick(Marker marker) {
//                    Intent i = new Intent(getContext(), RestaurantView.class);
//                    i.putExtra(RestaurantView.RESTAURANT_ID, markerRestaurantHashMap.get(marker).getObjectID());
//                    startActivity(i);
//                }
//            }); COULD MAYBE DO SOMETHING COOL HERE?
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