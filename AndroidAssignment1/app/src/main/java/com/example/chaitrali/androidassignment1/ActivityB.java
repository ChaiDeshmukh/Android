package com.example.chaitrali.androidassignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ActivityB extends AppCompatActivity {


    int counter = MainActivity.getCounterValue();
    private Button buttonB;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

        buttonB = findViewById(R.id.buttonB);
        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("tag", "Activity B: Started");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("tag", "Activity B: Resumed");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d("tag", "Activity B: Paused");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        counter++;
        Log.d("tag", "Activity B: ReStarted");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d("tag", "Activity B: Stopped");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("tag", "Activity B: Destroyed");
    }
}
