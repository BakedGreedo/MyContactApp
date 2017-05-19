package com.example.mottp7116.mycontactapp;

import android.content.Context;
import android.database.Cursor;
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
    Button btnAddData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        editName = (EditText) findViewById(R.id.editText_Name);
        editAge = (EditText) findViewById(R.id.editText_Age);
        editAddress = (EditText) findViewById(R.id.editText_Address);
    }

    public void addData(View v) {
        boolean isInserted = myDb.insertData(editName.getText().toString(),editAge.getText().toString(),editAddress.getText().toString());
        Context context = getApplicationContext();
        CharSequence text1 = "Data Added!";
        CharSequence text2 = "Failed to Insert Data!";
        int duration = Toast.LENGTH_SHORT;

        if (isInserted == true) {
            Log.d("MyContact", "Success inserting data");
            Toast toast = Toast.makeText(context,text1,duration);
            toast.show();
        }
        else {
            Log.d("MyContact", "Failure inserting data");
            Toast toast = Toast.makeText(context,text2,duration);
            toast.show();
        }
    }

    public void viewData(View v) {
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            showMessage("Error", "No data is found in the data base");
            //output message using Log.d and toast
            return;
        }
        StringBuffer buffer = new StringBuffer();
        for(int i = 0; i < res.getColumnCount(); i++) {
            buffer.append(res.getColumnName(i));
            buffer.append("\t");
        }
        buffer.append("\n");
        res.moveToFirst();
        do{
            for(int i= 0; i < res.getColumnCount(); i++) {
                buffer.append(res.getString(i));
                buffer.append("\t");
            }
            buffer.append("\n");
        }
        while(res.moveToNext());
        showMessage("Data", buffer.toString());
    }

    private void showMessage(String title, String message) {
        //AlertDialog.Builder
    }


}
