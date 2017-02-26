package cbbhackscolby.hyke;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import cbbhackscolby.hyke.fragments.CreateGroupFragment;
import cbbhackscolby.hyke.fragments.DistressFragment;
import cbbhackscolby.hyke.fragments.HomeFragment;
import cbbhackscolby.hyke.fragments.JoinGroupFragment;
import cbbhackscolby.hyke.fragments.MessagesFragment;
import cbbhackscolby.hyke.fragments.NearMeFragment;
import cbbhackscolby.hyke.fragments.ToDoFragment;
import cbbhackscolby.hyke.network.HykeLocationManager;

public class HomeActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_LOCATION_PERMISSION = 401;

    private String[] menuOptions;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    HykeLocationManager hykeLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        menuOptions = getResources().getStringArray(R.array.menu_options);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, menuOptions));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        Fragment fragment = new HomeFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();


        hykeLocationManager = new HykeLocationManager(this);
        requestNeededPermission();
    }


    public void requestNeededPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, "Hyke requires usage of location services", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);
        }
        else{
            hykeLocationManager.startLocationMonitoring();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_LOCATION_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "FINE_LOC perm granted", Toast.LENGTH_SHORT).show();
                    hykeLocationManager.startLocationMonitoring();
                } else {
                    Toast.makeText(this, "FINE_LOC perm NOT granted", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        // Create a new fragment and specify the planet to show based on position
        Fragment fragment = null;
        if (menuOptions[position].equals("Home")){
            fragment = new HomeFragment();
        }
//        else if (menuOptions[position].equals("ToDo")){
//            fragment = new ToDoFragment();
//        }
        else if (menuOptions[position].equals("Create Group")){
            fragment = new CreateGroupFragment();
        }
        else if (menuOptions[position].equals("Join Group")){
            fragment = new JoinGroupFragment();
        }
        else if (menuOptions[position].equals("Distress")){
            fragment = new DistressFragment();
        }
        else if (menuOptions[position].equals("Messages")){
            fragment = new MessagesFragment();
        }
        else if(menuOptions[position].equals("NearMe")){
            fragment = new NearMeFragment();
        }

        FragmentTransaction trans = this.getSupportFragmentManager().beginTransaction();
        trans.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        trans.replace(R.id.content_frame, fragment, "tag");
        trans.addToBackStack(null);
        trans.commit();

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

}


