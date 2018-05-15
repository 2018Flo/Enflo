package com.enflo.login_fragments.createSteps;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.enflo.R;
import com.enflo.interfaces.OnDataPass;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class createFragment3 extends Fragment {

    private static final String ARG_BOOLEAN = "ARG_bool";

    LayoutInflater layoutInflater;
    Boolean signIn;


    public static createFragment3 newInstance(Boolean b){
        createFragment3 fragment = new createFragment3();
        Bundle args = new Bundle();
        args.putBoolean(ARG_BOOLEAN, b);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;

        signIn = getArguments().getBoolean(ARG_BOOLEAN);

        if(signIn){
            view = inflater.inflate(R.layout.fragment_create_account_attendee, container, false);
            initializeattend(view);
        }
        else{
            view = inflater.inflate(R.layout.fragment_create_account_promoter, container, false);
//            initializepromo(view);
        }

        return view;
    }

    private void initializeattend(View view){
        RecyclerView recyclerView = view.findViewById(R.id.attend_recyclerview);
        adapt a = new adapt(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.create_attend_select))));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(a);
    }

    public class adapt extends RecyclerView.Adapter<adapt.MyViewHolder>{

        private List<String> mselection;
        public adapt(List<String> selector){
            mselection = selector;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.tag_item, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.item.setText(mselection.get(position));
        }

        @Override
        public int getItemCount() {
            return mselection.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{

            TextView item;

            public MyViewHolder(View itemView) {
                super(itemView);
                item = itemView.findViewById(R.id.item);
            }
        }
    }
}
