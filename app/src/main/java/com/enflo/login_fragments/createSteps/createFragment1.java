package com.enflo.login_fragments.createSteps;

import android.support.v7.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.enflo.R;
import com.enflo.interfaces.OnDataPass;
import com.enflo.interfaces.imageConverter;
import com.enflo.interfaces.onClickListener;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

public class createFragment1 extends Fragment implements View.OnClickListener  {

    OnDataPass dataPasser;

    EditText username, email, pass;
    ImageView photo;
    Button conti;

    private final int newRequestCode = 20;
    private final int oldRequestCode = 30;


    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_account_1, container, false);

        username = view.findViewById(R.id.create_username);
        email = view.findViewById(R.id.create_email);
        photo = view.findViewById(R.id.create_photo);
        photo.setOnClickListener(this);
        pass = view.findViewById(R.id.create_password);
        conti = view.findViewById(R.id.create_submit);
        conti.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_submit:
//                if(username.getText().length() == 0 || email.getText().length() == 0 || pass.getText().length() == 0){
                dataPasser.firstSet(username.getText().toString(),
                        email.getText().toString(), pass.getText().toString());
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.pager, new createFragment2())
                        .addToBackStack("").commit();
//                }
                break;
            case R.id.create_photo:
                alertDialog().show();
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(this.newRequestCode == requestCode && resultCode == RESULT_OK){
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            Bitmap circularBitmap = imageConverter.getRoundedCornerBitmap(bitmap, 100);
            photo.setImageBitmap(circularBitmap);
        }
        if(this.oldRequestCode == requestCode && resultCode == RESULT_OK){
            try {
                Uri image = data.getData();
                InputStream imageStream = getActivity().getContentResolver().openInputStream(image);
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                Bitmap circularBitmap = imageConverter.getRoundedCornerBitmap(bitmap, 100);
                photo.setImageBitmap(circularBitmap);
            }
            catch (FileNotFoundException e){
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public AlertDialog alertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Dialog);
        builder.setMessage("Where would you like to pick photo from?");
        builder.setPositiveButton("New Photo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(photoCaptureIntent, newRequestCode);
            }
        });
        builder.setNegativeButton("Camera Roll", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, oldRequestCode);
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });



        AlertDialog dialog = builder.create();
//        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
//        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
//        dialog.getButton(DialogInterface.BUTTON_NEUTRAL).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        return dialog;
    }

}
