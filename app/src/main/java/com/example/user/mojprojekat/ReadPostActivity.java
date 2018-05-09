package com.example.user.mojprojekat;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import fragments.MyFragments;
import tools.FragmentTransition;
import tools.Mokap;

public class ReadPostActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_post);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);


//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setIcon(R.drawable.ic_launcher_news1);
//        actionBar.setHomeButtonEnabled(true);

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setIcon(R.drawable.ic_launcher_news1);
            //actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
            actionBar.setHomeButtonEnabled(true);
        }



        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        TextView tvDescription = (TextView) findViewById(R.id.tvDescription);
        ImageView ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        TextView tvAuthor = (TextView) findViewById(R.id.tvAuthor);


        tvTitle.setText(getIntent().getStringExtra("title"));
        tvDescription.setText(getIntent().getStringExtra("description"));

        ivPhoto.setImageResource(R.drawable.lepa_lukic);

//        switch (ivPhoto) {
//            case :
//
//                break;
//        }

        tvAuthor.setText(getIntent().getStringExtra("author".toString()));



        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                mDrawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {

                    case R.id.post:
                        Intent i = new Intent(ReadPostActivity.this, PostsActivity.class);
                        startActivity(i);
                        break;

                    case R.id.createPost:
                        Intent i1 = new Intent(ReadPostActivity.this, CreatePostActivity.class);
                        startActivity(i1);
                        break;

                    case R.id.settingsPost:
                        Intent i2 = new Intent(ReadPostActivity.this, SettingsActivity.class);
                        startActivity(i2);
                        break;
                }

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_appbaritem, menu);

        MenuItem addItem = menu.findItem(R.id.action_new);
        MenuItem refreshItem = menu.findItem(R.id.action_refresh);
        MenuItem settingItem = menu.findItem(R.id.action_settings);
        MenuItem acceptItem = menu.findItem(R.id.action_accept);
        MenuItem cancelItem = menu.findItem(R.id.action_cancel);

        addItem.setVisible(false);
        refreshItem.setVisible(false);
        settingItem.setVisible(false);
        acceptItem.setVisible(false);
        cancelItem.setVisible(false);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.action_delete:
                Toast.makeText(this, "Delete post", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(menuItem);
    }


//    @Override
//    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
//        return super.onCreateView(parent, name, context, attrs);
//    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
