package com.qdwang.demo.main;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.qdwang.demo.R;
import com.qdwang.demo.WelcomeActivity;
import com.qdwang.demo.base.BaseActivity;
import com.qdwang.demo.main.fragment.DashFragment;
import com.qdwang.demo.main.fragment.HomeFragment;
import com.qdwang.demo.main.fragment.PersonFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.drawer_layout)DrawerLayout drawer;
    @BindView(R.id.nav_view)NavigationView navigationView;
    @BindView(R.id.navigation) BottomNavigationView navigation;
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.frame_layout)FrameLayout frameLayout;

    private HomeFragment homeFragment;
    private DashFragment dashFragment;
    private PersonFragment personFragment;

    private final static int FRAGMENT_HOME = 0x01;
    private final static int FRAGMENT_DASH= 0x02;
    private final static int FRAGMENT_PERSON= 0x03;
    private int mIndex = FRAGMENT_HOME;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            =new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    showFragment(FRAGMENT_HOME);
                    return true;
                case R.id.navigation_dashboard:
                    showFragment(FRAGMENT_DASH);
                    return true;
                case R.id.navigation_notifications:
                    showFragment(FRAGMENT_PERSON);
                    return true;
            }
            return false;
        }
    };

    private NavigationView.OnNavigationItemSelectedListener mNavigationItemListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id=item.getItemId();

            if (id == R.id.nav_camera) {
                // Handle the camera action
            } else if (id == R.id.nav_gallery) {

            } else if (id == R.id.nav_slideshow) {
                WelcomeActivity.toActivity(MainActivity.this);
            } else if (id == R.id.nav_manage) {

            } else if (id == R.id.nav_share) {

            } else if (id == R.id.nav_send) {

            }
            drawer.closeDrawer(GravityCompat.START);
            return false;
        }
    };

    @Override
    protected void createView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigationView.setNavigationItemSelectedListener(mNavigationItemListener);
        initView();
    }

    private void initView() {
        showFragment(mIndex);
    }

    /**
     * 切换fragmeng
     * @param index
     */
    private void showFragment(int index){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragments(transaction);
        mIndex = index;
        switch (index){
            case FRAGMENT_HOME:
                if (homeFragment == null) {
                    homeFragment = HomeFragment.newInstance();
                    transaction.add(R.id.frame_layout, homeFragment, "home");
                } else {
                    transaction.show(homeFragment);
                }
                break;
            case FRAGMENT_DASH:
                if (dashFragment == null) {
                    dashFragment = DashFragment.newInstance();
                    transaction.add(R.id.frame_layout, dashFragment, "dash");
                } else {
                    transaction.show(dashFragment);
                }
                break;
            case FRAGMENT_PERSON:
                if (personFragment == null) {
                    personFragment = PersonFragment.newInstance();
                    transaction.add(R.id.frame_layout, personFragment, "person");
                } else {
                    transaction.show(personFragment);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 隐藏所有的Fragment
     */
    private void hideFragments(FragmentTransaction transaction) {
        if(homeFragment != null){
            transaction.hide(homeFragment);
        }
        if(dashFragment != null){
            transaction.hide(dashFragment);
        }
        if(personFragment != null){
            transaction.hide(personFragment);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
