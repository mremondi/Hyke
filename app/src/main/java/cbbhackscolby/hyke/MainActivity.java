package cbbhackscolby.hyke;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Homepage for our application
 * Contains weather widget, button to begin hike
 */
public class MainActivity extends AppCompatActivity {

    TextView tvLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LinearLayout weatherLayout = (LinearLayout) findViewById(R.id.weatherRow);

        ImageView ivWeatherIcon = (ImageView) findViewById(R.id.ivWeatherIcon);
        ivWeatherIcon.setImageDrawable(getDrawable(R.drawable.ic_wb_sunny_black_24dp));

        TextView tvLocation = (TextView) weatherLayout.findViewById(R.id.tvLocation);
        tvLocation.setText("Brunswick");

        ImageButton homeButton = (ImageButton) findViewById(R.id.homeScreenButton);
        homeButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });




    }
}
