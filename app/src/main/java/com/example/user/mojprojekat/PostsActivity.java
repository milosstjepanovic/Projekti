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
import android.view.MenuItem;
import android.view.View;

public class PostsActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                mDrawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {

                    case R.id.createPost:
                        Intent i = new Intent(PostsActivity.this, CreatePostActivity.class);
                        startActivity(i);
                        break;

                    case R.id.readPost:
                        Intent i1 = new Intent(PostsActivity.this, ReadPostActivity.class);
                        startActivity(i1);
                        break;

                    case R.id.settingsPost:
                        Intent i2 = new Intent(PostsActivity.this, SettingsActivity.class);
                        startActivity(i2);
                        break;
                }

                return true;
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

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



    /**

    public void btnStartCreatePostActivity(View view) {
        Intent i = new Intent(this, CreatePostActivity.class);
        startActivity(i);
    }

    public void btnStartReadPostActivity(View view) {
        Intent i = new Intent(this, ReadPostActivity.class);
        startActivity(i);
    }

    public void btnStartSettingsActivity(View view) {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }
     **/
}
