package com.example.chaitrali.androidassignment1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private Button button1;
    private Button button2;
    private Button button3;
    public static int counter;
    public TextView textView2;
    public String counterValue;

    public static int getCounterValue() {

        return counter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Activity B
        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityB();
            }
        });

        //Dialog Box
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(view);
            }
        });


        textView2 = (TextView) findViewById(R.id.textView2);


        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
                System.exit(0);
            }
        });


    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        counterValue = textView2.getText().toString();
        System.out.println("counterValue: " + counterValue);
        outState.putString("restartCounter", counterValue);
        Log.d("tag", "Activity A: onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        textView2.setText("Restart Counter: " + savedInstanceState.getString("restartCounter"));
        Log.d("tag", "Activity A: onRestoreInstanceState");
    }

    public void openDialog(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.getContext().setTheme(R.style.MyAlertDialogTheme);
        alert.setMessage("Simple Dialog");
        alert.setPositiveButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.create().show();
    }


    public void openActivityB() {
        Intent intent = new Intent(this, ActivityB.class);
        startActivity(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();

        Log.d("tag", "Activity A: Started");
    }

    @Override
    protected void onResume() {
        super.onResume();
        textView2.setText("Restart Counter: " + String.valueOf(counter));
        Log.d("tag", "Activity A: Resumed");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d("tag", "Activity A: Paused");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        counter++;
        Log.d("tag", "Activity A: ReStarted");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d("tag", "Activity A: Stopped");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("tag", "Activity A: Destroyed");
    }

}
