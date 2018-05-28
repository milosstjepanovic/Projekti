package com.example.user.mojprojekat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import model.Post;
import model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import util.PostService;
import util.RetrofitClient;

public class CreatePostActivity extends AppCompatActivity {

    ImageView imageView;
    Button button;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;

    //bolji nacin
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 420;

    private boolean permissionGranted = false;

    private FusedLocationProviderClient fusedLocationProviderClient;

    private Location deviceLocation;


/*
    private LocationManager locationManager;
    private LocationListener locationListener;
    private static final String locationProvider = LocationManager.GPS_PROVIDER;
    private Geocoder geocoder;
    */



    private Post post;

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

       /*
        imageView = (ImageView) findViewById(R.id.imageView);
        button = (Button) findViewById(R.id.photo);

        */
       /* button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
*/
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

        // po miljusevom
        getLocationPermission();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);






        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);


        // izvlacim id i mail iz sharedPref sacuvane u login activity
        SharedPreferences sharedPreferences = getSharedPreferences("sp", MODE_PRIVATE);
        Integer id = sharedPreferences.getInt("userId", 0);
        String email = sharedPreferences.getString("userEmail", null);

        // postavljam mail i sliku u header.. mora ovde nakon inicijalizacije navigationView-a
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

                    case R.id.readPost:
                        Intent i = new Intent(CreatePostActivity.this, PostsActivity.class);
                        startActivity(i);
                        break;

                    case R.id.createPost:
                        Toast.makeText(getApplicationContext(), "You are currently in that page", Toast.LENGTH_SHORT).show();
                        /*Intent i1 = new Intent(CreatePostActivity.this, CreatePostActivity.class);
                        startActivity(i1);*/
                        break;

                    case R.id.settingsPost:
                        Intent i2 = new Intent(CreatePostActivity.this, SettingsActivity.class);
                        startActivity(i2);
                        break;

                    case R.id.logout:
                        Intent i3 = new Intent(CreatePostActivity.this, LoginActivity.class);
                        startActivity(i3);
                        finish();
                        break;
                }

                return false;
            }
        });
    }



        //post = new Post();

/* prvi nacin
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                post.setLatitude(location.getLatitude());
                post.setLongitude(location.getLongitude());
                post.setLocation(location);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        geocoder = new Geocoder(this);*/



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_appbaritem, menu);

        MenuItem addItem = menu.findItem(R.id.action_new);
        MenuItem refreshItem = menu.findItem(R.id.action_refresh);
        MenuItem settingItem = menu.findItem(R.id.action_settings);
        MenuItem delleteItem = menu.findItem(R.id.action_delete);

        addItem.setVisible(false);
        refreshItem.setVisible(false);
        settingItem.setVisible(false);
        delleteItem.setVisible(false);

        return true;
    }



    //@TargetApi(Build.VERSION_CODES.O)
    //@RequiresApi(api = Build.VERSION_CODES.O)
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.action_accept:

                createPost();
                return true;




              /*  prvi nacin
                //Toast.makeText(this, "Save post", Toast.LENGTH_SHORT).show();
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, 99);

                } else {
                    locationManager.requestSingleUpdate(locationProvider, locationListener, null);
                    Location lastKnowLocation = locationManager.getLastKnownLocation(locationProvider);
                    post.setLocation(lastKnowLocation);
                    post.setLatitude(lastKnowLocation.getLatitude());
                    post.setLongitude(lastKnowLocation.getLongitude());
                }
*/


                // uzimam podatke iz polja koje je korisnik uneo



            case R.id.action_cancel:
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), PostsActivity.class);
                startActivity(i);
                return true;


            //ukoliko treba da stoji u appbaru!!

//            case R.id.action_settings:
//                Intent i = new Intent(this, SettingsActivity.class);
//                startActivity(i);
//                return true;
        }

        return super.onOptionsItemSelected(menuItem);
    }

    private void createPost() {
        TextView tvTitle = findViewById(R.id.etTitlePost);
        TextView tvDescription = findViewById(R.id.etDescriptionPost);

        String title = tvTitle.getEditableText().toString();
        String description = tvDescription.getEditableText().toString();

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(getApplicationContext(), "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        post = new Post();
        User user = new User();

        SharedPreferences sharedPreferences = getSharedPreferences("sp", MODE_PRIVATE);
        Integer userId = sharedPreferences.getInt("userId", 0);

        user.setId(userId);
        post.setTitle(title);
        post.setDescription(description);
        post.setAuthor(user);



        //po miljusevom

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(CreatePostActivity.this);
        try {
            if (permissionGranted) {
                Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
                locationTask.addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // ako je lokacija pronadjena , pisemo je u post i saljemo na server
                            Toast.makeText(CreatePostActivity.this, "Device located" , Toast.LENGTH_SHORT).show();
                            deviceLocation = (Location) task.getResult();
                            //uzimam iz result-a duzinu i sirinu
                            post.setLatitude(45.242407);
                            post.setLongitude(19.843103);
                            //post.setLocation(deviceLocation);
                            sendPostToServer(post);
                            // ako lokaciju nismo mogli naci, nikom nista, ne pisemo je nigde al saljemo na server;
                        } else {
                            Toast.makeText(CreatePostActivity.this, "Can't locate your device", Toast.LENGTH_SHORT).show();
                            sendPostToServer(post);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Toast.makeText(CreatePostActivity.this, "security exception", Toast.LENGTH_SHORT).show();
        }




/*   po zeminom nastavljaju se ove linije ovde

        PostService postService = RetrofitClient.retrofit.create(PostService.class);
        Call<Post> call = postService.createPost(post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Post post = response.body();

                if (post == null) {
                    Toast.makeText(getApplicationContext(), "Somenthing went wrong, post = null", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Post successful created", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), PostsActivity.class);
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error while creating post!", Toast.LENGTH_LONG).show();
            }
        });



*/

    }



    private void sendPostToServer(Post post) {
        PostService postService = RetrofitClient.retrofit.create(PostService.class);
        Call<Post> call = postService.createPost(post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Post post = response.body();

                if (post == null) {
                    Toast.makeText(getApplicationContext(), "Somenthing went wrong, post = null", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Post successful created", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), PostsActivity.class);
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error while creating post!", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }



    //po miljusevom

    private void getLocationPermission(){
        String [] permissions = {FINE_LOCATION, COARSE_LOCATION};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "location permission granted", Toast.LENGTH_SHORT).show();
            permissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE : {
                // ako je grantResults veci od 0 znaci da je nesto dozvoljeno, nesto je 'grant-ovano'
                if(grantResults.length > 0){
                    // prolazimo kroz niz permisija, proveravamo da li su dozvoljeni
                    for(int i=0; i < grantResults.length; i++){
                        // ako korisnik ne dozvoli i jednu permisiju (mora sve da dozvoli) permission nije granted i ne dobijamo lokaciju
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            permissionGranted = false;
                            return;
                        }
                    }
                    permissionGranted = true;
                }
            }
        }
    }

/*  po zeminom

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 99: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        locationManager.requestSingleUpdate(locationProvider, locationListener, null);
                        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
                        post.setLocation(lastKnownLocation);
                    } catch (SecurityException exc) {
                        post.setLocation(null);
                    }
                } else { post.setLocation(null);}

                return;
            }

        }
    }

*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
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
    }


}
