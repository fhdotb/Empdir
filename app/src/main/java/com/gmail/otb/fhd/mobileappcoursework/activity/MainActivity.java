package com.gmail.otb.fhd.mobileappcoursework.activity;




import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gmail.otb.fhd.mobileappcoursework.R;
import com.gmail.otb.fhd.mobileappcoursework.fragment.HomeFragment;
import com.gmail.otb.fhd.mobileappcoursework.fragment.PhotosFragment;
import com.gmail.otb.fhd.mobileappcoursework.fragment.SettingsFragment;
import com.gmail.otb.fhd.mobileappcoursework.fragment.SupervisorFragment;
import com.gmail.otb.fhd.mobileappcoursework.utills.CircleTransform;


public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    // urls to load navigation header background image
    // and profile image
    private static final String urlNavHeaderBg = "https://api.androidhive.info/images/nav-menu-header-bg.jpg";
    private  String urlProfileImg ;

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "Employees List";
    private static final String TAG_PHOTOS = "Buildings map";
    private static final String TAG_MOVIES = "movies";
    private static final String TAG_SETTINGS = "settings";
    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    private String userEmail;
    private String OfficeID;
    private String photo;
    private String jobTitle;
    private String supervisor;
    private String name;
    private String building;

    private  Bundle extras;


    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);


        extras = getIntent().getExtras();
        if (extras != null) {
            userEmail = extras.getString("userEmail");
            OfficeID = extras.getString("OfficeID");
            photo = extras.getString("photo");
            jobTitle = extras.getString("jobTitle");
            name = extras.getString("name");
            supervisor = extras.getString("supervisor");
            building= extras.getString("building");

            Log.d("current user building :", building);


            Log.d("if a current user is supervisor :", supervisor);
        }

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        if(supervisor != null) {

            if (supervisor.trim().equals("0")) // case if the user is supervise
            {
                fab.setVisibility(View.GONE);
            } else {
                fab.setVisibility(View.VISIBLE);
            }
        }



            // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        txtName.setText(name);
        txtWebsite.setText(jobTitle);

        if(photo != null)
        {
            Glide.with(this).load(photo)
                    .thumbnail(0.5f)
                    .crossFade()
                    .transform(new CircleTransform(this))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgProfile);
            imgProfile.setColorFilter(null);
        }



        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);


        urlProfileImg = photo;
        // load nav menu header data
         loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if(supervisor.trim().equals("1")) // case if the user is supervise
        {
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_movies).setVisible(false);

        }
        else {
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_movies).setVisible(true);
        }


        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }




    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeader() {
        // name, website
        txtName.setText(name);
        txtWebsite.setText(jobTitle);

        // loading header background image
        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

        // Loading profile image
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);

        // showing dot next to notifications label
        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    @SuppressLint("LongLogTag")
    private Fragment getHomeFragment() {

        Bundle bundle = new Bundle();
        bundle.putString("userEmail",userEmail);
        bundle.putString("OfficeID",OfficeID);
        bundle.putString("supervisor",supervisor);
        bundle.putString("building",building);


        Log.d("send OfficeID to fragmment ",OfficeID);

        switch (navItemIndex) {
            case 0:
                // home
                HomeFragment homeFragment = new HomeFragment();
                homeFragment.setArguments(bundle);
                return homeFragment;
            case 1:
                // photos
                PhotosFragment photosFragment = new PhotosFragment();
                photosFragment.setArguments(bundle);
                return photosFragment;
            case 2:
                // Supervisor
                SupervisorFragment supervisorFragment = new SupervisorFragment();
                supervisorFragment.setArguments(bundle);
                return supervisorFragment;
            case 4:
                // settings fragment
                SettingsFragment settingsFragment = new SettingsFragment();
                settingsFragment.setArguments(bundle);
                return settingsFragment;
            default:
                return new HomeFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_photos:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_PHOTOS;
                        break;
                    case R.id.nav_movies:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_MOVIES;
                        break;
                    case R.id.nav_settings:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_SETTINGS;
                        break;
                    case R.id.nav_about_us:
                        // launch new intent instead of loading fragment
                       // startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                       // drawer.closeDrawers();
                        return true;
                    case R.id.nav_privacy_policy:
                        // launch new intent instead of loading fragment
                        //startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
                        //drawer.closeDrawers();
                        return true;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_home);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    // show or hide the fab
    private void toggleFab() {
        if (navItemIndex == 0)
            fab.show();
        else
            fab.hide();
    }
}
