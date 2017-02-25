package cbbhackscolby.hyke.adapters;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import cbbhackscolby.hyke.DistressActivity;
import cbbhackscolby.hyke.MainActivity;
import cbbhackscolby.hyke.MessagesActivity;
import cbbhackscolby.hyke.R;
import cbbhackscolby.hyke.ToDoActivity;

/**
 * Created by mremondi on 9/19/16.
 */
public class BottomBarAdapter {

    private BottomBar mBottomBar;
    private AppCompatActivity activity;

    public BottomBarAdapter(BottomBar bottomBar, AppCompatActivity activity){
        this.mBottomBar = bottomBar;
        this.activity = activity;
    }

    public void setUpBottomBar(){
        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                Log.d("MENU ID", "" + tabId);
                Log.d("RID ", "" + R.id.nav_bar_home);
                if (tabId == R.id.nav_bar_distress) {
                    Intent i = new Intent(activity.getApplicationContext(), DistressActivity.class);
                    activity.startActivity(i);
                }
                else if(tabId == R.id.nav_bar_home) {
                    Intent i = new Intent(activity.getApplicationContext(), MainActivity.class);
                    activity.startActivity(i);
                }
                else if (tabId == R.id.nav_bar_todo) {
                    Intent i = new Intent(activity.getApplicationContext(), ToDoActivity.class);
                    activity.startActivity(i);
                }
                else if (tabId == R.id.nav_bar_messages) {
                    Intent i = new Intent(activity.getApplicationContext(), MessagesActivity.class);
                    activity.startActivity(i);
                }
            }
        });

        mBottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                if (tabId == R.id.nav_bar_distress) {
                    Intent i = new Intent(activity.getApplicationContext(), DistressActivity.class);
                    activity.startActivity(i);
                }
                else if(tabId == R.id.nav_bar_home) {
                    Intent i = new Intent(activity.getApplicationContext(), MainActivity.class);
                    activity.startActivity(i);
                }
                else if (tabId == R.id.nav_bar_todo) {
                    Intent i = new Intent(activity.getApplicationContext(), ToDoActivity.class);
                    activity.startActivity(i);
                }
                else if (tabId == R.id.nav_bar_messages) {
                    Intent i = new Intent(activity.getApplicationContext(), MessagesActivity.class);
                    activity.startActivity(i);
                }
            }
        });

    }
}