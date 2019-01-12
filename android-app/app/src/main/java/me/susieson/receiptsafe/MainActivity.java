package me.susieson.receiptsafe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements OverviewFragment.OnFragmentInteractionListener,
        ReceiptsFragment.OnFragmentInteractionListener, AnalyticsFragment.OnFragmentInteractionListener {

    FragmentPagerAdapter mAdapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.view_pager);
        mAdapterViewPager = new TabPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapterViewPager);
    }

    public static class TabPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 4;

        public TabPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return OverviewFragment.newInstance();
                case 1:
                    return ReceiptsFragment.newInstance();
                case 2:
                    return AnalyticsFragment.newInstance();
                case 3:
                    return new SettingsFragment();
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Overview";
                case 1:
                    return "Receipts";
                case 2:
                    return "Analytics";
                case 3:
                    return "Settings";
                default:
                    return null;
            }
        }

    }
}
