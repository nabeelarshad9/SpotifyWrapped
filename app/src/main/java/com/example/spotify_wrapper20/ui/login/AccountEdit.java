package com.example.spotify_wrapper20.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.spotify_wrapper20.MainActivity;
import com.example.spotify_wrapper20.R;
import com.example.spotify_wrapper20.databinding.ActivityMainBinding;
import com.example.spotify_wrapper20.ui.AccountEdit.AccountEditViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AccountEdit  extends AppCompatActivity {
    private ActivityMainBinding binding;
    private AccountEditViewModel viewModel;
    EditText edtUsername, edtPassword, edtEmail;
    Button bDelete, bSave, bLogout;

    LinearLayout linearLayout;
    private String Username;
    private String Password;
    private String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Username = getIntent().getStringExtra("username");
        Log.d("AccountEdit", "inside AccountEdit: " + Username);
        Password = getIntent().getStringExtra("password");
        MainActivity ma = new MainActivity();
        viewModel = new ViewModelProvider(ma).get(AccountEditViewModel.class);
        viewModel.setUserName(Username);
        viewModel.setPassword(Password);
        //Toast.makeText(this, accessToken, Toast.LENGTH_SHORT).show();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_wrapped, R.id.navigation_history, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

//        setContentView(R.layout.activity_account_edit);
//
//        Intent intent = getIntent();
//
//        linearLayout = findViewById(R.id.btn_holder);
//
//        edtUsername = findViewById(R.id.edt_edit_username);
//        edtDescription = findViewById(R.id.edt_edit_description);
//
//        bCancel = findViewById(R.id.btnCancel);
//        bSave = findViewById(R.id.btnSave);
//        bCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

//        bSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TD td = new TD(edtTitle.getText().toString(),edtDescription.getText().toString());
//                td.setId(intent.getIntExtra("id",1));
//                if (new ToDOHandler(editTD.this).upd(td)){
//                    Toast.makeText(editTD.this,"To Do Item Updated Successfully",Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(editTD.this,"To Do Item Update Failed",Toast.LENGTH_SHORT).show();
//                }
//                onBackPressed();
//            }
//        });
//
//
//        edtTitle.setText(intent.getStringExtra("title"));
//        edtDescription.setText(intent.getStringExtra("description"));
    }

//    @Override

//    public void onBackPressed(){
//        bSave.setVisibility(View.GONE);
//        bCancel.setVisibility(View.GONE);
//        TransitionManager.beginDelayedTransition(linearLayout);
//        super.onBackPressed();
//    }




    public String getUserName() {
        return Username;
    }
    public String getPassword() {
        return Password;
    }
}