package cbbhackscolby.hyke.fragments;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;

import cbbhackscolby.hyke.R;
import cbbhackscolby.hyke.models.User;
import cbbhackscolby.hyke.network.HykeLocationManager;

public class NearMeFragment extends Fragment implements OnMapReadyCallback {

    private HashMap<Marker, User> markerUserHashMap;
    private LatLng location;
    private MapView mapView;
    private GoogleMap googleMap;
    private HykeLocationManager hykeLocationManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.near_me_fragment, null, false);

        markerUserHashMap = new HashMap<>();

        hykeLocationManager = new HykeLocationManager(this.getContext());
        location = hykeLocationManager.getLastKnownLocation();

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

    public void addFriendMarker(User user){
//        LatLng latLng = new LatLng(user.getLoc().getCoordinates()[1],
//                user.getLoc().getCoordinates()[0]);
//        markerRestaurantHashMap.put(googleMap.addMarker(new MarkerOptions()
//                .position(latLng)
//                .title(user.getRestaurantName())), user);
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