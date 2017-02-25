package cbbhackscolby.hyke;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.roughike.bottombar.BottomBar;
import cbbhackscolby.hyke.adapters.BottomBarAdapter;

public class DistressActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distress);


        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        BottomBarAdapter bottomBarAdapter = new BottomBarAdapter(bottomBar, this);
        bottomBarAdapter.setUpBottomBar();

    }
}
