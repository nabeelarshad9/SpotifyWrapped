package com.example.spotify_wrapper20.ui.login;

import android.app.AlertDialog;
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
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.Calendar; // To get the dates for different holidays.

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.spotify_wrapper20.MainActivity;
import com.example.spotify_wrapper20.R;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

public class LoginActivity extends AppCompatActivity {

    //private static final String CLIENT_ID = "35f8937fe1a046eb8e2bf8339bf59b17";
    //public static boolean isNightModeOn;

    private static final String CLIENT_ID = "c374f36771d14d4db7961a58b24ca5be";
    private static final String REDIRECT_URI = "spotify-wrapper://auth";
    private static final int AUTH_REQUEST_CODE = 1;

    private static String accessToken,accessCode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//      hide action bar
        getSupportActionBar().hide();

        // Following code sets the up the Calendar data which will be used for the holiday backgrounds.
        LinearLayout layout = findViewById(R.id.loginLayout);

        int backgroundResource;

        backgroundResource = R.drawable.loginbackground2;

        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Checks if it's Christmas Day (December 31st)
        if (month == Calendar.DECEMBER && dayOfMonth == 25){
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO){
                backgroundResource = R.drawable.christmas_background;
            }
            else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
                backgroundResource = R.drawable.dark_christmas_background;
            }
            else {
                backgroundResource = R.drawable.christmas_background;
            }
        }
        // Checks if it's Halloween (October 31st)
        else if (month == Calendar.OCTOBER && dayOfMonth == 31) {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO){
                backgroundResource = R.drawable.halloween_background;
            }
            else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
                backgroundResource = R.drawable.dark_halloween_background;
            }
            else {
                backgroundResource = R.drawable.halloween_background;
            }
        }
        // Checks if it's New Year's Day (January 1st)
        else if (month == Calendar.JANUARY && dayOfMonth == 1) {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO){
                backgroundResource = R.drawable.new_year_background;
            }
            else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
                backgroundResource = R.drawable.dark_new_year_background;
            }
            else {
                backgroundResource = R.drawable.new_year_background;
            }
        }
        // Checks if it's Valentine's Day (February 14th)
        else if (month == Calendar.FEBRUARY && dayOfMonth == 14) {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO){
                backgroundResource = R.drawable.valentines_day_background;
            }
            else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
                backgroundResource = R.drawable.dark_valentines_day_background;
            }
            else {
                backgroundResource = R.drawable.valentines_day_background;
            }
        }
        // Checks if it's Fourth of July (July 4th)
        else if (month == Calendar.JULY && dayOfMonth == 4) {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO){
                backgroundResource = R.drawable.fourth_of_july_background;
            }
            else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
                backgroundResource = R.drawable.dark_fourth_of_july_background;
            }
            else {
                backgroundResource = R.drawable.fourth_of_july_background;
            }
        }
        else
        {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO){
//                backgroundResource = R.drawable.christmas_background;
                backgroundResource = R.drawable.loginbackground2;

            }
            else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
//                backgroundResource = R.drawable.dark_christmas_background;
                backgroundResource = R.drawable.loginbgdark;
            }
            else {
//                backgroundResource = R.drawable.christmas_background;
                backgroundResource = R.drawable.loginbackground2;
            }
        }


        // adding if statements because dark mode login background wasn't working with this code
//        if (!((month == Calendar.DECEMBER && dayOfMonth == 25)||(month == Calendar.OCTOBER && dayOfMonth == 31)||(month == Calendar.JANUARY && dayOfMonth == 1)||(month == Calendar.FEBRUARY && dayOfMonth == 14)||(month == Calendar.JULY && dayOfMonth == 4))){
//        if (!((month == Calendar.APRIL && dayOfMonth == 11)||(month == Calendar.OCTOBER && dayOfMonth == 31)||(month == Calendar.JANUARY && dayOfMonth == 1)||(month == Calendar.FEBRUARY && dayOfMonth == 14)||(month == Calendar.JULY && dayOfMonth == 4))){
//            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO){
//                backgroundResource = R.drawable.loginbackground2;
//            }
//            else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
//                backgroundResource = R.drawable.loginbgdark;
//            }
//            else {
//                backgroundResource = R.drawable.loginbackground2;
//            }
//        }


        // Sets the holiday background.
        layout.setBackgroundResource(backgroundResource);

        // dark
        ImageButton button = findViewById(R.id.darkturn);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO){
            MainActivity.isNightModeOn = false;
            //button.setText("Enable Dark Mode");
            button.setImageDrawable(getResources().getDrawable(R.drawable.baseline_dark_mode_24));

        } else if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            MainActivity.isNightModeOn = true;
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
                if (MainActivity.isNightModeOn){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    //button.setText("Enable Dark Mode");
                    button.setImageDrawable(getResources().getDrawable(R.drawable.baseline_dark_mode_24));
                    MainActivity.isNightModeOn = false;
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    MainActivity.isNightModeOn = true;
                    //button.setText("Disable Dark Mode");
                    button.setImageDrawable(getResources().getDrawable(R.drawable.baseline_bedtime_off_24));

                }
            }
        });

        // end dark



        // Initialize the views
        Button logInButton = findViewById(R.id.loginButton);


//        // Initialize the views
//        Button connectSpotifyButton = findViewById(R.id.connectButton);
        // Initialize the views for creating a new account.
        Button createNewAccountButton = findViewById(R.id.createNewAccount);


        // Set click listener for the loginButton.
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              Toast.makeText(this, "Inside Initiate LogIn", Toast.LENGTH_SHORT).show();
                EditText inEmail = findViewById(R.id.edit_email);
                EditText inUsername = findViewById(R.id.username);
                EditText inPassword = findViewById(R.id.password);
                String username = inUsername.getText().toString();
                String password = inPassword.getText().toString();
                if (username.equals("")){
                    Toast.makeText(LoginActivity.this,"Username can not be blank", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.equals("")){
                    Toast.makeText(LoginActivity.this,"Password can not be blank", Toast.LENGTH_SHORT).show();
                    return;
                }
                Boolean isValidCredential = new AccountHandler(LoginActivity.this).validateLoginCredentials(username, password);
                if (!isValidCredential) {
                    Toast.makeText(LoginActivity.this, "Please enter valid login and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                initiateSpotifyAuthentication();


//                LayoutInflater inflater = (LayoutInflater) LoginActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View viewInput = inflater.inflate(R.layout.edit_delete_account,null,false);
////                EditText inEmail = viewInput.findViewById(R.id.edt_edit_email);
////                EditText inUsername = viewInput.findViewById(R.id.edt_edit_username);
////                EditText inPassword = viewInput.findViewById(R.id.edt_edit_password);
//
//                View blurredBackground = LayoutInflater.from(LoginActivity.this).inflate(R.layout.blur_background, null);
//                ViewGroup rootView = getWindow().getDecorView().findViewById(android.R.id.content);
//                rootView.addView(blurredBackground);
//
//                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this)
//                        .setView(viewInput)
//                        .setTitle("Edit / Delete Account")
//                        .setPositiveButton("Confirm", null)
//                        .show();
//                Button positiveButton = alertDialog.getButton(alertDialog.BUTTON_POSITIVE);
//                positiveButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String email = inEmail.getText().toString();
//                        String username = inUsername.getText().toString();
//                        String password = inPassword.getText().toString();
//                        Account account = new Account(email,username, password);
//                        if (email.equals("")){
//                            Toast.makeText(LoginActivity.this,"Email can not be blank", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        if (username.equals("")){
//                            Toast.makeText(LoginActivity.this,"Username can not be blank", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        if (password.equals("")){
//                            Toast.makeText(LoginActivity.this,"Password can not be blank", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        boolean isInserted =  new AccountHandler(LoginActivity.this).create(account);
//                        if (isInserted){
//                            Toast.makeText(LoginActivity.this,"New Account Created Successfully", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            Toast.makeText(LoginActivity.this,"New Account Creation Failed", Toast.LENGTH_SHORT).show();
//                        }
//                        alertDialog.dismiss();
//                        //dialog.cancel();
//                    }
//                });
//                alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialog) {
//                        rootView.removeView(blurredBackground);
//                    }
//                });
            }
        });
        ;

        // Set click listener for the "Connect Spotify Account" button.
//        connectSpotifyButton.setOnClickListener(v -> initiateSpotifyAuthentication());
        //Toast.makeText(this, accessToken, Toast.LENGTH_SHORT).show();

        // Set click listener for the "Create New Spotify Account" button.
        createNewAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) LoginActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewInput = inflater.inflate(R.layout.create_new_account,null,false);
                EditText inEmail = viewInput.findViewById(R.id.add_email);
                EditText inUsername = viewInput.findViewById(R.id.create_username);
                EditText inPassword = viewInput.findViewById(R.id.create_password);

                View blurredBackground = LayoutInflater.from(LoginActivity.this).inflate(R.layout.blur_background, null);
                ViewGroup rootView = getWindow().getDecorView().findViewById(android.R.id.content);
                rootView.addView(blurredBackground);

                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this)
                        .setView(viewInput)
                        .setTitle("Create Account")
                        .setPositiveButton("Save", null)
                        .show();
                Button positiveButton = alertDialog.getButton(alertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String email = inEmail.getText().toString();
                                String username = inUsername.getText().toString();
                                String password = inPassword.getText().toString();
                                Account account = new Account(email,username, password);
                                if (email.equals("")){
                                    Toast.makeText(LoginActivity.this,"Email can not be blank", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (username.equals("")){
                                    Toast.makeText(LoginActivity.this,"Username can not be blank", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (password.equals("")){
                                    Toast.makeText(LoginActivity.this,"Password can not be blank", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                boolean isInserted =  new AccountHandler(LoginActivity.this).create(account);
                                if (isInserted){
                                    Toast.makeText(LoginActivity.this,"New Account Created Successfully", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(LoginActivity.this,"New Account Creation Failed", Toast.LENGTH_SHORT).show();
                                }
                                alertDialog.dismiss();
                                //dialog.cancel();
                            }
                        });
                alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        rootView.removeView(blurredBackground);
                    }
                });
            }
        });
    }

    private void initiateSpotifyAuthentication() {
        //Toast.makeText(this, "Inside", Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, CLIENT_ID, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, REDIRECT_URI, Toast.LENGTH_SHORT).show();

        // Set up the Spotify authentication request
        AuthorizationRequest request = new AuthorizationRequest.Builder(
                CLIENT_ID,
                AuthorizationResponse.Type.TOKEN,
                REDIRECT_URI
        )
                .setScopes(new String[]{"user-read-private", "user-read-email", "user-top-read", "user-read-recently-played", "playlist-read", "playlist-read-private"})
                .build();

        // Open the Spotify login activity
        AuthorizationClient.openLoginActivity(this, AUTH_REQUEST_CODE, request);
        //Toast.makeText(this, String.valueOf(AUTH_REQUEST_CODE), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check if the result is from the Spotify authentication request
        //Toast.makeText(this, String.valueOf(AUTH_REQUEST_CODE), Toast.LENGTH_SHORT).show();
        if (requestCode == AUTH_REQUEST_CODE) {
            // Parse the authorization response
            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, data);
            accessCode = response.getCode();
            //Toast.makeText(this, String.valueOf(response.getType()), Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, String.valueOf(accessCode), Toast.LENGTH_SHORT).show();
            if (response.getType() == AuthorizationResponse.Type.TOKEN) {
                // Authentication successful
                accessToken = response.getAccessToken();
                // Proceed with your application logic, e.g., navigate to the main activity
                navigateToMainActivity();
            } else if (response.getType() == AuthorizationResponse.Type.ERROR) {
                // Authentication error occurred
                Toast.makeText(this, "Authentication failed: " + response.getError(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void navigateToMainActivity() {
        //Toast.makeText(this, "Inside Navigate to Main", Toast.LENGTH_SHORT).show();
        // Start the main activity or navigate to the home page
        //Toast.makeText(this, String.valueOf(accessToken), Toast.LENGTH_SHORT).show();
        EditText inUsername = findViewById(R.id.username);
        EditText inPassword = findViewById(R.id.password);
        String username = inUsername.getText().toString();
        String password = inPassword.getText().toString();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("accessToken", accessToken);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        startActivity(intent);
    }

//    private void initiateLogIn() {
////        Toast.makeText(this, "Inside Initiate LogIn", Toast.LENGTH_SHORT).show();
//        EditText inUsername = findViewById(R.id.username);
//        EditText inPassword = findViewById(R.id.password);
//        String username = inUsername.getText().toString();
//        String password = inPassword.getText().toString();
//        if (username.equals("")){
//            Toast.makeText(LoginActivity.this,"Username can not be blank", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (password.equals("")){
//            Toast.makeText(LoginActivity.this,"Password can not be blank", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        Boolean isValidCredential = new AccountHandler(LoginActivity.this).validateLoginCredentials(username, password);
//        if (isValidCredential) {
//            Toast.makeText(this, "Inside Initiate LogIn Valid", Toast.LENGTH_SHORT).show();
////            Intent intent = new Intent(this, AccountEdit.class);
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//            intent.putExtra("username", username);
//            intent.putExtra("password", password);
//            startActivity(intent);
//        } else {
//            Toast.makeText(this, "Please enter valid login and password", Toast.LENGTH_SHORT).show();
//        }
//    }

    public static String getAccessToken() {
        return accessToken;
    }

    public String getAccessCode() {
        return accessCode;
    }






}

