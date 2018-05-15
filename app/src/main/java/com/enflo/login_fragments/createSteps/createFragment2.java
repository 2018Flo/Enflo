package com.enflo.login_fragments.createSteps;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.enflo.R;
import com.enflo.interfaces.OnDataPass;

public class createFragment2 extends Fragment implements View.OnClickListener {

    Button attend, promo;
    OnDataPass dataPasser;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.fragment_create_account_2, container, false);

        attend = view.findViewById(R.id.create2_attend);
        attend.setOnClickListener(this);
        promo = view.findViewById(R.id.create2_promo);
        promo.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.create2_attend:
                passData(true);
                setUserType(0);
                nextfragement(createFragment3.newInstance(true));
                break;
            case R.id.create2_promo:
                passData(false);
                setUserType(1);
                nextfragement(createFragment3.newInstance(false));
                break;
        }
    }

    private void passData(Boolean data){
        dataPasser.onDataPass(data);
    }

    private void setUserType(int i){
        dataPasser.secondSet(i);
    }

    private void nextfragement(Fragment fragment){
       getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.pager, fragment)
                .addToBackStack("")
                .commit();
    }
}