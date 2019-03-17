package com.example.chaitrali.datastorage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SQLiteActivity extends AppCompatActivity {

    private EditText editText;
    private String output;
    private Button saveBlog;
    private Button cancelButton;
    public int counter = 0;
    public static final String outputfile = "Chai.txt";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy-hh:mm a");
    public static final String file = "Test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite2);

        saveBlog = findViewById(R.id.button1);

        saveBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMessage(view);
            }
        });


        cancelButton = findViewById(R.id.button2);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCancel(view);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences(file, Context.MODE_PRIVATE);
        counter = sp.getInt("counter", 0);
    }


    public void saveMessage(View view) {
        editText = findViewById(R.id.e1);
        String message = editText.getText().toString();

        if (message.isEmpty()) {
            Toast.makeText(this, "Please enter some message", Toast.LENGTH_SHORT).show();
        } else {

            DataController dc = new DataController(getBaseContext());
            dc.open();

            long returnVal = dc.addData(message);
            Log.d("tag", "Data Added? " + returnVal);
            dc.close();


            if (returnVal != -1) {
                Toast.makeText(this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();

                counter++;
                SharedPreferences sp = getSharedPreferences(file, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("SQL_Counter", counter);
                editor.commit();
                output = "\n\nSQLite " + counter + ", " + dateFormat.format(new Date());
                Log.d("tag", "Message: " + output);

                FileOutputStream fos = null;

                try {
                    fos = openFileOutput(outputfile, MODE_APPEND);
                    fos.write(output.getBytes());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }

    }


    public void onCancel(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
