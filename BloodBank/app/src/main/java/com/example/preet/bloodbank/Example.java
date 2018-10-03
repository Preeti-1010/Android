package com.example.preet.bloodbank;

import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class Example extends AppCompatActivity {
    TextView t1, txtView, tv;
    EditText ed1, ed2,ed3;
    Button b1, b2, b3, b4;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        t1 = (TextView) findViewById(R.id.textView12);
        ed1 = (EditText) findViewById(R.id.editText9);
        ed2 = (EditText) findViewById(R.id.editText);
        ed3 = (EditText) findViewById(R.id.editText10);
        b1 = (Button) findViewById(R.id.button6);
        b2 = (Button) findViewById(R.id.button7);
        b3 = (Button) findViewById(R.id.button8);
        b4 = (Button) findViewById(R.id.button9);
        tv = (TextView) findViewById(R.id.txt);

        myDb = new DatabaseHelper(this);
        addData();
        viewAllData();
        Update();
        Delete();
    }

    public void addData() {
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studname = ed1.getText().toString();
                String studsurname = ed2.getText().toString();

                boolean isinserted = false;
                Toast.makeText(Example.this, studname + "" + studsurname, Toast.LENGTH_LONG).show();
                try {
                    isinserted = myDb.insertData(studname, studsurname);
                } catch (SQLException e1) {

                    String error = e1.toString();
                    Dialog d = new Dialog(Example.this);
                    d.setTitle("Thankyou");
                    TextView tv = new TextView(Example.this);
                    tv.setText(error);
                    d.setContentView(tv);
                    d.show();
                    // Toast.makeText(Example.this, e1.toString(), Toast.LENGTH_LONG).show();
                } catch (Exception e) {

                    String error = e.toString();
                    Dialog d = new Dialog(Example.this);
                    d.setTitle("Thank you");
                    TextView tv = new TextView(Example.this);
                    tv.setText(error);
                    d.setContentView(tv);
                    d.show();

                }
                if (isinserted == true) {
                    Toast.makeText(Example.this, "Student Data is Inserted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Example.this, "Student Data  is not Inserted", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    public void viewAllData() {
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                    Cursor res = myDb.getAllData();

                    if (res.getCount() == 0) {
                        Toast.makeText(getBaseContext(), "No Data Found", Toast.LENGTH_LONG).show();
                    } else {
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("ID: " + res.getString(0) + "\n");
                            buffer.append("NAME: " + res.getString(1) + "\n");
                            buffer.append("SUR NAME: " + res.getString(2) + "\n");
                            tv.setText(buffer.toString());
                        }
                    }

                }
                catch (SQLException e1){
                    String error = e1.toString();
                    Dialog d = new Dialog(Example.this);
                    d.setTitle("Thankyou");
                    TextView tv = new TextView(Example.this);
                    tv.setText(error);
                    d.setContentView(tv);
                    d.show();
                    // Toast.makeText(Example.this, e1.toString(), Toast.LENGTH_LONG).show();
                }
                catch (Exception e) {

                    String error = e.toString();
                    Dialog d = new Dialog(Example.this);
                    d.setTitle("Thank you");
                    TextView tv = new TextView(Example.this);
                    tv.setText(error);
                    d.setContentView(tv);
                    d.show();

                }

            }
        });

    }

    public void Update() {
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isupdated=false;
                String studname=ed1.getText().toString();
                String studsurname=ed2.getText().toString();
                String Id=ed3.getText().toString();
               // Toast.makeText(Example.this, "Student Data is nofdgdfg", Toast.LENGTH_LONG).show();
                try {
                    isupdated = myDb.updateData(Id,studname,studsurname);
                    if(isupdated==true){
                        Toast.makeText(Example.this, "Student Data is Updated", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(Example.this, "Student Data is not updated", Toast.LENGTH_LONG).show();
                    }
                }
                catch (SQLException e1) {

                    String error = e1.toString();
                    Dialog d = new Dialog(Example.this);
                    d.setTitle("Thankyou");
                    TextView tv = new TextView(Example.this);
                    tv.setText(error);
                    d.setContentView(tv);
                    d.show();
                    // Toast.makeText(Example.this, e1.toString(), Toast.LENGTH_LONG).show();
                } catch (Exception e) {

                    String error = e.toString();
                    Dialog d = new Dialog(Example.this);
                    d.setTitle("Thank you");
                    TextView tv = new TextView(Example.this);
                    tv.setText(error);
                    d.setContentView(tv);
                    d.show();

                }


            }
        });
    }
    public void Delete() {
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String Id=ed3.getText().toString();
                int result=myDb.deletedata(Id);
                if(result==1)
                {
                    Toast.makeText(Example.this, "Student Data is deleted", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(Example.this, "Student Data is not deleted", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}