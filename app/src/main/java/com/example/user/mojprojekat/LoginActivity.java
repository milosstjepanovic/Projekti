package com.example.user.mojprojekat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import model.User;
import retrofit2.Callback;
import retrofit2.Response;
import util.RetrofitClient;
import util.UserService;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail;
    EditText etPassword;
    Button btnLogin;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = etEmail.getText().toString().trim();
                final String password = etPassword.getText().toString().trim();

                if(!validateLoginInputs(email, password))
                    return;

                UserService userService = RetrofitClient.retrofit.create(UserService.class);
                retrofit2.Call<List<User>> call = userService.getUsers();

                call.enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(retrofit2.Call<List<User>> call, Response<List<User>> response) {
                        List<User> users = new ArrayList<>();
                        try {
                            users = response.body();
                            //Toast.makeText(getApplicationContext(), users.get(0).getName(), Toast.LENGTH_LONG).show();

                            User user = validateUser(users, email, password);

                            if (user == null) {
                                Toast.makeText(getApplicationContext(), "User not found!", Toast.LENGTH_LONG).show();
                                return;
                            } else {
                                //zapisuje u sharedPreferences
                                SharedPreferences sharedPreferences = getSharedPreferences("sp", MODE_PRIVATE);
                                Editor editor = sharedPreferences.edit();
                                editor.putInt("userId", user.getId());
                                editor.putString("userEmail", user.getEmail());
                                editor.commit();


                                Intent i = new Intent(LoginActivity.this, PostsActivity.class);
                                //i.putExtra("loggedinUserEmail", email);
                                startActivity(i);
                                finish();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(retrofit2.Call<List<User>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private boolean validateLoginInputs(String email, String password){

            if(email == null || email.trim().length() == 0){
                Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
                return false;
            }
            if(password == null || password.trim().length() == 0){
                Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
    }

    private User validateUser(List<User> users, String email, String password) {
        for (User user : users) {
            if (email.equals(user.getEmail()) && password.equals(user.getPassword())) {
                return user;
            }
        }
        return null;
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

    public void btnStartPostsActivity(View view) {
        /*Intent i = new Intent(this, PostsActivity.class);
        startActivity(i);*/

     /*   UserTestService userTestService = UserTestService.retrofit.create(UserTestService.class);
        retrofit2.Call<List<User>> call = userTestService.getUsers();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(retrofit2.Call<List<User>> call, Response<List<User>> response) {
                List<User> user = new ArrayList<>();
                try {
                    user = response.body();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), user.get(0).getName(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(retrofit2.Call<List<User>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Greskaaaaa", Toast.LENGTH_LONG).show();
            }
        });*/

    }
}
