package cbbhackscolby.hyke;

import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cbbhackscolby.hyke.adapters.QRPagerAdapter;

public class QRViewPager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrview_pager);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.qqrViewPager);
        mViewPager.setAdapter(new QRPagerAdapter(getSupportFragmentManager()));

        PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pagerTabStrip);
        pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.colorPrimary));

        ((ViewPager.LayoutParams) pagerTabStrip.getLayoutParams()).isDecor = true;
    }
}
