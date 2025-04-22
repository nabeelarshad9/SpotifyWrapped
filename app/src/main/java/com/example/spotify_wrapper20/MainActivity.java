package com.example.spotify_wrapper20;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.spotify_wrapper20.ui.wrapped.WrappedViewModel;
import com.example.spotify_wrapper20.ui.login.Account;
import com.example.spotify_wrapper20.ui.login.AccountHandler;
import com.example.spotify_wrapper20.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.spotify_wrapper20.databinding.ActivityMainBinding;
import com.google.firebase.messaging.FirebaseMessaging;
import androidx.annotation.NonNull;

public class MainActivity extends AppCompatActivity {
    public static boolean isNightModeOn;

    private ActivityMainBinding binding;
    private WrappedViewModel viewModel;
    private String accessToken;
    private String username;
    private String password;
    Animation scaleUp, scaleDown;

    EditText MsgToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//      hide action bar
        getSupportActionBar().hide();


        //Toast.makeText(this, "Inside Main", Toast.LENGTH_SHORT).show();
        accessToken = getIntent().getStringExtra("accessToken");
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        viewModel = new ViewModelProvider(this).get(WrappedViewModel.class);
        viewModel.setAccessToken(accessToken);
        //Toast.makeText(this, accessToken, Toast.LENGTH_SHORT).show();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_wrapped, R.id.navigation_history)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        ImageButton editdeleteButton = findViewById(R.id.edit_delete);
        ImageButton logoutfromHome = findViewById(R.id.logout);
//        MsgToken = findViewById(R.id.msgToken); // to view token
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            System.out.println("Fetching FCM registration token failed");
                            return; }
                        // Get new FCM registration token
                        String token = task.getResult();
                        // Log and toast
                        //String msg = getString(R.string.msg_token_fmt, token);
                        System.out.println(token);
                        //Toast.makeText(MainActivity.this, "Your Device Registration Token is "+ token, Toast.LENGTH_SHORT).show();
//                        MsgToken.setText(token);// to view token
                    }
                });
        // dark
        ImageButton button = findViewById(R.id.darkmain);
        scaleUp = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this,R.anim.scale_down);


        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO){
            isNightModeOn = false;
            //button.setText("Enable Dark Mode");
            button.setImageDrawable(getResources().getDrawable(R.drawable.baseline_dark_mode_24));

        } else if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            isNightModeOn = true;
            //button.setText("Disable Dark Mode");
            button.setImageDrawable(getResources().getDrawable(R.drawable.baseline_bedtime_off_24));

        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
                button.startAnimation(myAnim);
                if (isNightModeOn){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    button.setImageDrawable(getResources().getDrawable(R.drawable.baseline_dark_mode_24));
                    isNightModeOn = false;

                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    isNightModeOn = true;
                    button.setImageDrawable(getResources().getDrawable(R.drawable.baseline_bedtime_off_24));

                }
            }
        });
        // end dark
        logoutfromHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
        editdeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Opens the edit/delete pop-up window:
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewInput = inflater.inflate(R.layout.edit_delete_account,null,false);

                EditText edtId = viewInput.findViewById(R.id.edit_id);;

                EditText edtEmail = viewInput.findViewById(R.id.edit_email);
                EditText edtUsername = viewInput.findViewById(R.id.edit_username);
                EditText edtPassword = viewInput.findViewById(R.id.edit_password);

                AccountHandler accountHandler = new AccountHandler(getApplicationContext());
//                String username = edtUsername.getText().toString();
//                String password = edtPassword.getText().toString();
                Account account = accountHandler.readAccountInfo(username,password);
                edtEmail.setText(account.getEmail());
                edtUsername.setText(username);
                edtPassword.setText(password);
                edtId.setText(String.valueOf(account.getId()));
//                View blurredBackground = LayoutInflater.from(getApplicationContext().inflate(R.layout.blur_background, null));
                View blurredBackground = LayoutInflater.from(getApplicationContext()).inflate(R.layout.blur_background, null);

                ViewGroup rootView = MainActivity.this.getWindow().getDecorView().findViewById(android.R.id.content);
                rootView.addView(blurredBackground);

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setView(viewInput)
                        .setTitle("Edit / Delete Account")
                        .setPositiveButton("Save", null)
                        .show();
                Button positiveButton = alertDialog.getButton(alertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String edtemail = edtEmail.getText().toString();
                        String edtusername = edtUsername.getText().toString();
                        String edtpassword = edtPassword.getText().toString();
                        Integer edtid =  Integer.parseInt(edtId.getText().toString());
                        Account account = new Account(edtemail,edtusername, edtpassword);
                        if (edtemail.equals("")){
                            Toast.makeText(getApplicationContext(),"Email cannot be blank", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (edtusername.equals("")){
                            Toast.makeText(getApplicationContext(),"Username cannot be blank", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (edtpassword.equals("")){
                            Toast.makeText(getApplicationContext(),"Password cannot be blank", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        boolean isUpdated =  new AccountHandler(getApplicationContext()).upd(account,edtid);
                        if (isUpdated){
                            Toast.makeText(getApplicationContext(),"Account Information Updated Successfully", Toast.LENGTH_SHORT).show();
                            username = edtusername;
                            password = edtpassword;
                            alertDialog.dismiss();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Account Information Update Failed", Toast.LENGTH_SHORT).show();
                        }
//                        alertDialog.dismiss();
                        //dialog.cancel();
                    }
                });
                alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        rootView.removeView(blurredBackground);
                    }
                });
                Button deleteButton = viewInput.findViewById(R.id.buttonDelete);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String idText = edtId.getText().toString();
                        int edtid =  Integer.parseInt(idText);
                        boolean isDeleted =  new AccountHandler(getApplicationContext()).del(edtid);
                        if (isDeleted){
                            Toast.makeText(getApplicationContext(),"Account Information Deleted Successfully", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Account Information Delete Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Button logoutButton = viewInput.findViewById(R.id.buttonLogout);
                logoutButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        //inUsername.setText("");
                        //inPassword.setText("");
                    }
                });
            }

        });;
    }
    public String getAccessToken() {
        return accessToken;
    }
}