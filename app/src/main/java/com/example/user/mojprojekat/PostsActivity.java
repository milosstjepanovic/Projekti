package com.example.user.mojprojekat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import adapters.PostAdapter;
import de.hdodenhof.circleimageview.CircleImageView;
import fragments.MyFragments;
import model.Post;
import model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tools.FragmentTransition;
import tools.Mokap;
import util.UserService;

public class PostsActivity extends AppCompatActivity {

    private ArrayList<Post> posts;
    private ListView listView;

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        //actionBar.setDisplayHomeAsUpEnabled(true);

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setIcon(R.drawable.ic_launcher_news1);
            //actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
            actionBar.setHomeButtonEnabled(true);
        }

        //instancira se myfragments i time se prikaze lista postova
        //FragmentTransition.to(MyFragments.newInstance(), this, false);



        listView = (ListView) findViewById(R.id.lvPosts1);

        posts = Mokap.getPosts();

        PostAdapter postAdapter = new PostAdapter(this, R.layout.post_list, posts);
        listView.setAdapter(postAdapter);


        sortPostBy(posts);

        /*posts = Mokap.posts;


        sortPostBy(posts);


        PostAdapter adapter = new PostAdapter(this, posts);
        listView = findViewById(R.id.lvPosts);
        listView.setAdapter(adapter);
*/


        //videti gde treba da se ubaci da radi

    /*    String email = getIntent().getStringExtra("loggedinUserEmail");

        TextView headerEmail = (TextView) findViewById(R.id.headerEmail);
        headerEmail.setText(email);

*/



        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);


        String email = getIntent().getStringExtra("loggedinUserEmail");

        TextView headerEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.headerEmail);
        headerEmail.setText(email);

        CircleImageView headerUserPicture = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.headerUserPicture);
        if (email.equals("milos@gmail.com")) {
            headerUserPicture.setImageDrawable(getResources().getDrawable(R.drawable.milos));
        } else {
            headerUserPicture.setImageDrawable(getResources().getDrawable(R.drawable.marko));
        }


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

                    case R.id.logout:
                        Intent i3 = new Intent(PostsActivity.this, LoginActivity.class);
                        startActivity(i3);
                        finish();
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


              //ukoliko treba da stoji u appbaru!!

//            case R.id.action_settings:
//                Intent i = new Intent(this, SettingsActivity.class);
//                startActivity(i);
//                return true;
        }

        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }


    public void sortPostBy(ArrayList<Post> posts) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String sortPostBy = sp.getString("pref_sort_post", "default");


        if (sortPostBy.equals("Date")) {
            Collections.sort(posts, new Comparator<Post>() {
                @Override
                public int compare(Post post1, Post post2) {
                    return post1.getDate().compareTo(post2.getDate());
                }
            });
        } else if (sortPostBy.equals("Popularity")) {
            Collections.sort(posts, new Comparator<Post>() {
                @Override
                public int compare(Post post2, Post post1) {
                    if (post1.getPopularity() > post2.getPopularity()) {
                        return 1;
                    } else if (post1.getPopularity() < post2.getPopularity()) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        } else {
            Toast.makeText(this, "Sorting went wrong, posts unsorted!\n" + sortPostBy, Toast.LENGTH_LONG).show();
        }
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

        posts = Mokap.getPosts();
        sortPostBy(posts);

        PostAdapter adapter = new PostAdapter(this, R.layout.post_list, posts);
        listView = findViewById(R.id.lvPosts1);
        listView.setAdapter(adapter);
    }



}
