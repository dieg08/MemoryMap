package com.myapp.diegogonzalez.memorymap;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ViewPager vp = (ViewPager) findViewById(R.id.pager);
        vp.setOffscreenPageLimit(3);

        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                if (position == 0) {
                    return "New Memory";
                } else if (position == 1) {
                    return "Memories";
                } else {
                    return "Map";
                }
            }



            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return new NewMemory();
                } else if (position == 1) {
                    return new Memories();
                } else {
                    //Fragment fragmentMap = new SupportMapFragment();
                    //return fragmentMap;
                    return new Map();
                }
            }
        });

    }
}
