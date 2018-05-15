package com.enflo.login_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.enflo.CreateActivity;
import com.enflo.LoginActivity;
import com.enflo.MainActivity;
import com.enflo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
public class loginfragment extends Fragment implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText email, pass;
    private TextView create, forgot;
    private Button sign;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mAuth = FirebaseAuth.getInstance();
        initialize(view);

        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login_sign:
                signIn(email, pass);
                break;
            case R.id.login_create:
                Intent intent = new Intent(getActivity(), CreateActivity.class);
                startActivity(intent);
                break;
            case R.id.login_forgot:
                ((LoginActivity)getActivity()).showContent(new forgotFragment());

                //TODO: Create "Forgot Pass" Layout
        }
    }

    private void signIn(EditText email, EditText pass){
        mAuth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT
                                ).show();
                        }
                    }
                });
    }

    private void initialize(View view){
        email = view.findViewById(R.id.login_email);
        pass = view.findViewById(R.id.login_password);
        create = view.findViewById(R.id.login_create);
        create.setOnClickListener(this);
        forgot = view.findViewById(R.id.login_forgot);
        forgot.setOnClickListener(this);
        sign = view.findViewById(R.id.login_sign);
        sign.setOnClickListener(this);
    }
}
