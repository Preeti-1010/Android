package com.example.preet.bloodbank;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button b1, b2;
    RadioButton rdbtn1, rdbtn2;
    RadioGroup rg1;
    TextView t1;
    EditText ed1, ed2, ed3, ed4, ed5, ed6;
    Spinner spin, spin2;
    RegisterDb myDb;
    String[] bloodgroup = {"O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"};
    String[] city = {"Gkp", "Lko", "Delhi", "Mumbai", "Pune", "Kanpur", "Allahabad", "Banaras"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        addlistener();


        b1 = (Button) findViewById(R.id.btnsub);
        ed1 = (EditText) findViewById(R.id.editText3);
        ed2 = (EditText) findViewById(R.id.editText4);
        ed3 = (EditText) findViewById(R.id.editText5);
        ed4 = (EditText) findViewById(R.id.editText6);
        ed5 = (EditText) findViewById(R.id.editText7);
        ed6 = (EditText) findViewById(R.id.editText8);
        t1 = (TextView) findViewById(R.id.txt1);
        t1.setVisibility(View.VISIBLE);

        isValid(ed2.getText().toString());
        checkPassWordAndConfirmPassword(ed5.getText().toString(),ed6.getText().toString());

        spin = (Spinner) findViewById(R.id.spinner);
        //  spin.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        spin.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, bloodgroup);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);

        spin2 = (Spinner) findViewById(R.id.spinner2);
        spin2.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, city);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(adapter1);

        b2 = (Button) findViewById(R.id.btncancel);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed1.getText().toString().length() != 0) {
                    ed1.setText("");
                }
                if (ed2.getText().toString().length() != 0) {
                    ed2.setText("");
                }
                if (ed3.getText().toString().length() != 0) {
                    ed3.setText("");
                }
                if (ed4.getText().toString().length() != 0) {
                    ed4.setText("");
                }
                if (ed5.getText().toString().length() != 0) {
                    ed5.setText("");
                }
                if (ed6.getText().toString().length() != 0) {
                    ed6.setText("");
                }
            }
        });

        myDb = new RegisterDb(getBaseContext());


    }

    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public static boolean isValid(String Email)
    {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = Email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches())
        {
            return true;
        }
        else{
            return false;
        }
    }


    public boolean checkPassWordAndConfirmPassword(String Password,String Confirmpassword)
    {
        boolean pstatus = false;
        if (Confirmpassword != null && Password != null)
        {
            if (Password.equals(Confirmpassword))
            {
                pstatus = true;
            }
        }
        return pstatus;
    }

    private void addlistener() {
        rg1 = (RadioGroup) findViewById(R.id.rg);
        t1 = (TextView) findViewById(R.id.txt1);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radio = rg1.getCheckedRadioButtonId();
                rdbtn1 = (RadioButton) findViewById(radio);
                t1.setText(rdbtn1.getText());
                rdbtn2 = (RadioButton) findViewById(radio);
                t1.setText(rdbtn2.getText());
            }
        });
    }

    public void OnReg(View view) {
        String FullName = ed1.getText().toString();
        String Email = ed2.getText().toString();
        String Bloodgroup = spin.getSelectedItem().toString();
        String Gender = spin.getSelectedItem().toString();
        String Age = ed3.getText().toString();
        String City = spin.getSelectedItem().toString();
        String Mobileno = ed4.getText().toString();
        String Password = ed5.getText().toString();
        String Confirmpassword = ed6.getText().toString();




        String type="register";

        BackgroundWorker backgroundworker=new BackgroundWorker(this);
        backgroundworker.execute(type,FullName,Email,Bloodgroup,Gender,Age,City,Mobileno,Password,Confirmpassword);

    }
    public class BackgroundWorker extends AsyncTask<String,Void,String> {
        Context context;
        AlertDialog alertDialog;
        BackgroundWorker(Context ctx)
        {
            context=ctx;
        }

        @Override
        protected String doInBackground(String... params) {
            String type=params[0];
            String register_url="http://192.168.56.1/project1/bloodbank/registration.php";
            if(type.equals("register"))
            {
                try {
                    String FullName=params[1];
                    String Email=params[2];
                    String Bloodgroup=params[3];
                    String Gender=params[4];
                    String Age=params[5];
                    String City=params[6];
                    String Mobileno=params[7];
                    String Password=params[8];
                    String Confirmpassword=params[9];


                    URL url=new URL(register_url);
                    HttpURLConnection httpurlconnection=(HttpURLConnection)url.openConnection();
                    httpurlconnection.setRequestMethod("POST");
                    httpurlconnection.setDoOutput(true);
                    httpurlconnection.setDoInput(true);

                    OutputStream outputstream=httpurlconnection.getOutputStream();
                    BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputstream,"UTF-8"));

                    String post_data= URLEncoder.encode("FullName", "UTF-8")+"="+URLEncoder.encode(FullName,"UTF-8")+"&"
                            +URLEncoder.encode("Email", "UTF-8")+"="+URLEncoder.encode(Email,"UTF-8")+"&"
                            +URLEncoder.encode("Bloodgroup", "UTF-8")+"="+URLEncoder.encode(Bloodgroup,"UTF-8")+"&"
                            +URLEncoder.encode("Gender", "UTF-8")+"="+URLEncoder.encode(Gender,"UTF-8")+"&"
                            +URLEncoder.encode("Age","UTF-8")+"="+URLEncoder.encode(Age,"UTF-8")+"&"
                            +URLEncoder.encode("City", "UTF-8")+"="+URLEncoder.encode(City,"UTF-8")+"&"
                            +URLEncoder.encode("Mobileno", "UTF-8")+"="+URLEncoder.encode(Mobileno,"UTF-8")+"&"
                            +URLEncoder.encode("Password", "UTF-8")+"="+URLEncoder.encode(Password,"UTF-8")+"&"
                            +URLEncoder.encode("Confirmpassword", "UTF-8")+"="+ URLEncoder.encode(Confirmpassword, "UTF-8");

                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputstream.close();

                    InputStream inputStream=httpurlconnection.getInputStream();
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                    String result="";
                    String line="";
                    while ((line=bufferedReader.readLine())!=null)
                    {
                        result=line;

                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpurlconnection.disconnect();
                    return result;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            //  alertDialog=new AlertDialog.Builder(context).create();
            //  alertDialog.setTitle("Login Status");

        }

        @Override
        protected void onPostExecute(String result) {
            //  alertDialog.setMessage(result);
            //   alertDialog.show();
            if(result.equals("")) {
                Toast.makeText(Registration.this, "Thanks for Registration", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Registration.this, Login.class);
                startActivity(intent);
            }
            else
            {
                // alertDialog.setMessage(result);
                //alertDialog.show();
                Toast.makeText(Registration.this, "Problem in registration... please check data", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}















