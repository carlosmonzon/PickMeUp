package com.belatrix.pickmeup.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.belatrix.pickmeup.R;
import com.belatrix.pickmeup.adapter.ViewPagerAdapter;
import com.belatrix.pickmeup.fragment.AllRoutesFragment;
import com.belatrix.pickmeup.fragment.MyRoutesFragment;
import com.belatrix.pickmeup.model.MyRoute;
import com.belatrix.pickmeup.model.MyUser;
import com.belatrix.pickmeup.model.RouteDto;
import com.belatrix.pickmeup.rest.PickMeUpFirebaseClient;
import com.belatrix.pickmeup.rest.ServiceGenerator;
import com.belatrix.pickmeup.util.DataConverter;
import com.belatrix.pickmeup.util.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private Toolbar toolbar;

    private TabLayout tabLayout;

    private ViewPager viewPager;

    private MyUser mUser;

    private AllRoutesFragment mAllRoutesFragment;

    private MyRoutesFragment mMyRoutesFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        mUser = SharedPreferenceManager.readMyUser(this);
        //toolbar manejará el toolbar del HomeActivity
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Esta linea es para dar soporte al Back Button (<-) | False = inactivo | True = activo
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        //tabLayout tendrá el control de los tabs
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        //Codigo para la opcion del floatingActionButton. + la funcionalidad despues que se hace click.
        FloatingActionButton fabNewRoute = (FloatingActionButton) findViewById(R.id.fab);
        if (fabNewRoute != null) {
            fabNewRoute.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent act = new Intent(getApplicationContext(), RouteActivity.class);
                    startActivity(act);
                }
            });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            getRoute();
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Would you like to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplication(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // user doesn't want to logout
                    }
                })
                .show();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(this, ContactDetailsActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_logout) {
            // Handle the camera action
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Se añade los fragments al adaptar y luego al viewPager. (Se debe agregar el fragment y su titulo
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mAllRoutesFragment = new AllRoutesFragment();
        mMyRoutesFragment = new MyRoutesFragment();
        adapter.addFragment(mAllRoutesFragment, "Todas las rutas");
        adapter.addFragment(mMyRoutesFragment, "Mis rutas");
        viewPager.setAdapter(adapter);
    }

    private void updateRouteFragments(List<MyRoute> routes) {
        if (mAllRoutesFragment != null && mMyRoutesFragment != null) {
            mAllRoutesFragment.updateRouteList(routes);
            mMyRoutesFragment.updateRouteList(routes, mUser);
        }
    }

    public void getRoute() {

        Call<Map<String, RouteDto>> call = ServiceGenerator.createService(PickMeUpFirebaseClient.class).getRoutes();

        call.enqueue(new Callback<Map<String, RouteDto>>() {

            @Override
            public void onResponse(Call<Map<String, RouteDto>> call, Response<Map<String, RouteDto>> response) {
                Map<String, RouteDto> mapRoutes = response.body();
                List<MyRoute> routes = new ArrayList<>();

                try {
                    for (Map.Entry<String, RouteDto> entryRoute : mapRoutes.entrySet()) {
                        MyRoute route = DataConverter.convertRouteData(entryRoute.getKey(), entryRoute.getValue());
                        routes.add(route);
                    }
                    //todo: refactor code
                    updateRouteFragments(routes);
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<Map<String, RouteDto>> call, Throwable t) {
                // Toast.makeText(HomeActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
