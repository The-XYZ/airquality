package xyz.airquality;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import xyz.airquality.Fragments.DirectionsFragment;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    HomeFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        fragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        final android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment);
        transaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.settings:
                Intent i = new Intent(MainActivity.this, ComparisonActivity.class);
                startActivity(i);
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        updatePosition(menuItem);
                        return true;
                    }
                });
    }

    private void updatePosition(final MenuItem menuItem) {
        Fragment fragment = null;
        Intent intent = null;
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_directions:
                fragment = new DirectionsFragment();
                break;
            case R.id.nav_improve:
                fragment = new ImproveQualityFragment();
                break;
        }

        if (fragment != null) {
            menuItem.setChecked(true);
            mDrawerLayout.closeDrawers();
            FragmentManager fragmentManager = getSupportFragmentManager();
            final android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    transaction.commit();
                }
            }, 350);
        }
    }

    @Override
    public void onBackPressed() {
        if (fragment.panelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
            fragment.panelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            fragment.stationsAdapter.clearComparison();
        } else
        super.onBackPressed();
    }
}
