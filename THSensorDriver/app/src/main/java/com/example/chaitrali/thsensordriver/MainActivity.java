package com.example.chaitrali.thsensordriver;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.Random;


/*
* author: Chaitrali Deshmukh
* */

public class MainActivity extends AppCompatActivity {


    //declaring necessary variables
    private ProgressBar progressBar;
    private EditText input;
    private Button button1;
    private Button button2;
    private TextView tempView;
    private TextView output;
    private TextView humidityView;
    private TextView activityView;
    private String appendText = "";
    private TestAsyncTask task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressBar = findViewById(R.id.bar);
        input = (EditText) findViewById(R.id.e4);

        button1 = findViewById(R.id.b1);
        //starting the async task on Generate button
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                startAsyncTask(view);
            }
        });


        button2 = findViewById(R.id.b2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task.cancel(true);
                Toast.makeText(MainActivity.this, "Async Task Stopped", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        progressBar = findViewById(R.id.bar);

    }

    public void startAsyncTask(View view) {
         task = new TestAsyncTask(this);

        if (input.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(this, "Please input some value..", Toast.LENGTH_SHORT).show();
        } else {
            task.execute(input.getText().toString());
        }
    }

    private class TestAsyncTask extends AsyncTask<String, String, String> {

        private WeakReference<MainActivity> activityWeakReference;

        TestAsyncTask(MainActivity activity) {
            activityWeakReference = new WeakReference<MainActivity>(activity);
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            tempView = findViewById(R.id.e1);
            output = findViewById(R.id.outputScreen);
            humidityView = findViewById(R.id.e3);
            activityView = findViewById(R.id.e2);

            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            activity.progressBar.setVisibility(View.VISIBLE);  //progress bar set to visible
        }

        @Override
        protected String doInBackground(String... params) {

            int intValue = Integer.parseInt(params[0]);
            Log.d("tag", "Value passed: " + intValue);

            Random rand = new Random();

            int temp = 0;
            int humidity = 0;
            int activity = 0;

            //Generating the random values
            for (int r = 1; r <= intValue; r++) {
                temp = rand.nextInt((100 - 25) + 1) + 25;
                humidity = rand.nextInt((100 - 40) + 1) + 40;
                activity = rand.nextInt((500 - 1) + 1) + 1;

                publishProgress(String.valueOf(r), String.valueOf(temp), String.valueOf(humidity), String.valueOf(activity));

                if (isCancelled()) {
                    break;
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return "Sensor Reading Finished";
        }


        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            activity.progressBar.setProgress(Integer.parseInt(values[0]));

            tempView.setText(values[1] + "F");
            humidityView.setText(values[2] + "%");
            activityView.setText(values[3]);

            String outputStr = "\n\nOutput" + values[0] + ": " +
                    "\nTemperature: " + values[1] +
                    "\nHumidity: " + values[2] +
                    "\nActivity: " + values[3];

            appendText = appendText + outputStr;
            output.setMovementMethod(new ScrollingMovementMethod());
            output.setText(appendText);

        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
            activity.progressBar.setProgress(0);
            activity.progressBar.setVisibility(View.INVISIBLE);

        }


        @Override
        protected void onCancelled() {
            super.onCancelled();
            input.setText("");
            tempView.setText("");
            humidityView.setText("");
            activityView.setText("");
            output.setText("");
            progressBar.setProgress(0);
            progressBar.setVisibility(View.INVISIBLE);

        }
    }
}
