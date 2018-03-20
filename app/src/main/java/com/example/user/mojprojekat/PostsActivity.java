package com.example.user.mojprojekat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PostsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
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
}
