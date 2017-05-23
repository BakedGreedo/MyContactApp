package com.example.mottp7116.mycontactapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName;
    EditText editAge;
    EditText editAddress;
    EditText editSearch;
    Button btnaddData;
    Button btnviewData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        //add the layout bars
        editName = (EditText) findViewById(R.id.editText_Name);
        editAge = (EditText) findViewById(R.id.editText_Age);
        editAddress = (EditText) findViewById(R.id.editText_Address);
        editSearch = (EditText) findViewById(R.id.editText_search);
    }


    public void addData(View v) {

        boolean isInserted = myDb.insertData(editName.getText().toString(),editAge.getText().toString(),editAddress.getText().toString());
        Context context = getApplicationContext();
        CharSequence text1 = "Data Added!";
        CharSequence text2 = "Failed!";
        int duration = Toast.LENGTH_SHORT;

        if (isInserted == true) {
            Log.d("MyContact", "Success inserting data");
            //insert Toast message here
            Toast toast = Toast.makeText(context,text1,duration);
            toast.show();


        } else {
            Log.d("MyContact", "Failure inserting data");
            //insert Toast message here
            Toast toast = Toast.makeText(context,text2,duration);
            toast.show();

        }
    }

    public void viewData(View v) {

        Context context = getApplicationContext();
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            showMessage("Error", "No data is found in the database");
            //output a message using Log.d and toast
            return;
        }

        StringBuffer buffer = new StringBuffer();
        //setup a loop with the Cursor (res) using moveToNext
        for (int i = 0; i < res.getColumnCount(); i++) {
            buffer.append(res.getColumnName(i));
            buffer.append("\t");
        }
        buffer.append("\n");
        res.moveToFirst();
        do {

            for (int i = 0; i < res.getColumnCount(); i++) {
                buffer.append(res.getString(i));
                buffer.append("\t");
            }

            buffer.append("\n");
        }
        while (res.moveToNext()) ;
        showMessage("Data", buffer.toString());
    }


    public void Search(View v)
    {

        Cursor c = myDb.getAllData();
        StringBuffer b = new StringBuffer();

        if (c != null)
        {
            c.moveToFirst();

            for (int i = 0; i < c.getCount(); i++)
            {
                if (c.getString(1).equals(editSearch.getText().toString()))
                {
                    for (int j = 0; j < c.getColumnNames().length; j++)
                    {
                        b.append(c.getString(j) + "\n");
                    }
                    b.append("\n");
                }
                c.moveToNext();
            }
            showMessage("Contact Name: " + editSearch.getText().toString(), b.toString());
        }
    }






    private void showMessage(String title, String message) {
        //AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
