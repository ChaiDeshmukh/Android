package com.example.chaitrali.androidassignment2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final static int requestCall = 2;
    private EditText meditNumber;
    private EditText url;
    private ImageView call;
    private Button button;
    private Button button1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        meditNumber = findViewById(R.id.editNumber);
        call = findViewById(R.id.phoneImage);

        url = findViewById(R.id.editURL);
        Log.d("tag", url.getText().toString());

        button = findViewById(R.id.launchButton);

        button1 = findViewById(R.id.close);


        //make a phone call
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                makePhoneCall();
            }
        });


        //Open URL
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String str = url.getText().toString();
                Log.d("tag", "String Value After: " + str);


                if (str.trim().length() > 0) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(str));
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter proper URL", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Close APP
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void makePhoneCall() {
        String number = meditNumber.getText().toString();
        if (number.trim().length() > 0) {


            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, requestCall);

            } else

            {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        } else {
            Toast.makeText(MainActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == requestCall) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            }
        }
    }
}
