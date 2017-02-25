package cbbhackscolby.hyke;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;

import cbbhackscolby.hyke.adapters.BottomBarAdapter;


/**
 * Homepage for our application
 * Contains weather widget, button to begin hike
 */
public class MainActivity extends AppCompatActivity {
    BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("HERE", "here1");

        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        BottomBarAdapter bottomBarAdapter = new BottomBarAdapter(bottomBar, this);
        bottomBarAdapter.setUpBottomBar();

        Log.d("HERE", "here1");


        LinearLayout weatherLayout = (LinearLayout) findViewById(R.id.weatherRow);

        ImageView ivWeatherIcon = (ImageView) findViewById(R.id.ivWeatherIcon);
        ivWeatherIcon.setImageDrawable(getDrawable(R.drawable.ic_wb_sunny_black_24dp));

        TextView tvLocation = (TextView) weatherLayout.findViewById(R.id.tvLocation);
        tvLocation.setText("Brunswick");

        Log.d("HERE", "here1");


        ImageButton homeButton = (ImageButton) findViewById(R.id.homeScreenButton);
        homeButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent i = new Intent(getApplicationContext(), QRViewPager.class);
                startActivity(i);
                return false;
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, 1);
            }
        }




    }
}
