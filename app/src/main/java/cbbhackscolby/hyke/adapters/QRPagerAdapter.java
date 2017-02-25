package cbbhackscolby.hyke.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cbbhackscolby.hyke.fragments.QRReaderFragment;
import cbbhackscolby.hyke.fragments.QRCodeFragment;

/**
 * Created by mremondi on 2/24/17.
 */

public class QRPagerAdapter extends FragmentPagerAdapter {

    public QRPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new QRReaderFragment();
            case 1:
                return new QRCodeFragment();
            default:
                return new QRReaderFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return QRReaderFragment.TITLE;
            case 1:
                return QRCodeFragment.TITLE;
            default:
                return "unknown";
        }
    }
}
