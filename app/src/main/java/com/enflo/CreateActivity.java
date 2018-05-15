package com.enflo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.enflo.interfaces.OnDataPass;
import com.enflo.interfaces.onClickListener;
import com.enflo.login_fragments.createSteps.createFragment1;
import com.enflo.login_fragments.createSteps.createFragment2;
import com.enflo.login_fragments.createSteps.createFragment3;
import com.enflo.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;

public class CreateActivity extends AppCompatActivity implements OnDataPass, View.OnClickListener {

    private FirebaseAuth mAuth;
    private ImageView back;
    private Boolean select;
    private int count = 0;
    private User user;
    private FirebaseStorage storage;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create);

        select = false;

        user = new User();

        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        back = findViewById(R.id.create_back);
        back.setOnClickListener(this);

        nextFrag(count);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.create_back:
//                getSupportFragmentManager().popBackStack();
                onBackPressed();
                break;
        }
    }

    private void accountCreator(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //TODO: add firestore stuff before intents
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),
                                    task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void nextFrag(int i){
        Fragment [] fragments = {new createFragment1(), new createFragment2(), createFragment3.newInstance(select)};

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.pager, fragments[i])
                .addToBackStack("poop")
                .commit();
    }

    @Override
    public void onDataPass(Boolean data) {
        select = data;
    }

    @Override
    public void onBackPressed(){
        if(getSupportFragmentManager().getBackStackEntryCount() == 1){
            Intent intent = new Intent(CreateActivity.this, LoginActivity.class);
            startActivity(intent);
            CreateActivity.this.finish();
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public void firstSet(String username, String email, String pass) {
        user.setUsername(username);
        user.setEmail(email);
        user.setPass(pass);
    }

    @Override
    public void secondSet(int selector) {
        user.setAccountType(selector);
    }

    @Override
    public void thirdSetAttend(String[] types) {
        user.setAttendTypes(types);
    }

    @Override
    public void thirdSetPromo(String comp, String location, String[] tags) {
        user.setConpany(comp);
        user.setLocation(location);
        user.setTags(tags);
    }

    @Override
    public void onClickActivity(View view) {

    }
}

