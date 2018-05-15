package com.enflo;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.enflo.login_fragments.loginfragment;
import com.google.firebase.FirebaseApp;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.content_login,
                new loginfragment()).addToBackStack(null).commit();
    }


    public void showContent(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_login, fragment)
                .addToBackStack(getPackageName()).commit();
    }
}
