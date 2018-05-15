package com.enflo.login_fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.enflo.LoginActivity;
import com.enflo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotFragment extends Fragment implements OnClickListener {

    ImageView back;
    EditText email;
    Button help;
    FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot, container, false);
        initialize(view);
        auth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.forgot_back:
                ((LoginActivity)getActivity()).getSupportFragmentManager().popBackStack();
                break;
            case R.id.forgot_submit:
                passReset(email);
                break;
        }
    }

    private void initialize(View view){
        back = view.findViewById(R.id.forgot_back);
        back.setOnClickListener(this);
        email = view.findViewById(R.id.forgot_email);
        help = view.findViewById(R.id.forgot_submit);
        help.setOnClickListener(this);
    }

    private void passReset(EditText text){
        auth.sendPasswordResetEmail(text.getText().toString()).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(), "Recovery Email has been sent.",
                                    Toast.LENGTH_SHORT).show();
                            ((LoginActivity)getActivity()).getSupportFragmentManager().popBackStack();
                        }
                        else{
                            Toast.makeText(getContext(), task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }
}
