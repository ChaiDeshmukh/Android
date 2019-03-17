package com.example.chaitrali.datastorage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.audiofx.AudioEffect;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
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
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class SP_Activity extends AppCompatActivity {


    public static final String file = "Test";
    public static final String outputfile = "Chai.txt";
    private EditText bookName;
    private EditText authorName;
    private EditText description;
    private Button saveButton;
    private Button cancelButton;
    public int counter = 0;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy-hh:mm a");
    public String bName;
    public String aName;
    public String desc;
    public static String message="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_);

        saveButton = findViewById(R.id.b1);
        cancelButton = findViewById(R.id.b2);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save(view);
            }
        });


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

    public void save(View view) {

        bookName = findViewById(R.id.e1);
        authorName = findViewById(R.id.e2);
        description = findViewById(R.id.e3);

        bName = bookName.getText().toString();
        aName = authorName.getText().toString();
        desc = description.getText().toString();


        if (bName.isEmpty() || aName.isEmpty() || desc.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields...", Toast.LENGTH_SHORT).show();
            Log.d("tag","BookName: "+bName);
            Log.d("tag","Author Name: "+aName);

        } else {

            counter++;
            SharedPreferences sp = getSharedPreferences(file, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();

            editor.putInt("counter", counter);
            editor.putString("BookName", bName);
            editor.putString("AuthorName", aName);
            editor.putString("Description", desc);
            editor.commit();
            message = "\n\nSaved Preference " + counter + ": " + dateFormat.format(new Date())+
                    " \nBookName: "+ bName+
                    "\nAuthorName: "+aName+
                    "\nDescription: "+desc;
            Log.d("tag", "Message: "+message);

            FileOutputStream fos = null;

            try{
                fos = openFileOutput(outputfile, MODE_APPEND);
                fos.write(message.getBytes());
            }catch(FileNotFoundException e)
            {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                if(fos!=null)
                {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


            Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
        }
    }


    public void onCancel(View view)
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
