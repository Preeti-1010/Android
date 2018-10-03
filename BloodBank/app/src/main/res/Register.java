package com.arg.roopranajn.helloandroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
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

public class Register extends AppCompatActivity {
    EditText edtName,edtSurname,edtAge,edtUSer,edtPass;
    String name,surname,age,username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtName=(EditText)findViewById(R.id.etName);
        edtSurname=(EditText)findViewById(R.id.etsurName);
        edtAge=(EditText)findViewById(R.id.etAge);
        edtUSer=(EditText)findViewById(R.id.etUsername);
        edtPass=(EditText)findViewById(R.id.etPassword);

    }

    public void OnReg(View view)
    {
        name=edtName.getText().toString();
        surname=edtSurname.getText().toString();
        age=edtAge.getText().toString();
        username=edtUSer.getText().toString();
        password=edtPass.getText().toString();

        String type="register";

        BackgroundWorker backgroundworker=new BackgroundWorker(this);
        backgroundworker.execute(type,name,surname,age,username,password);

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
            String register_url="http://192.168.56.1/project1/reg.php";
            if(type.equals("register"))
            {
                try {
                    String name=params[1];
                    String surname=params[2];
                    String age=params[3];
                    String username=params[4];
                    String password=params[5];

                    URL url=new URL(register_url);
                    HttpURLConnection httpurlconnection=(HttpURLConnection)url.openConnection();
                    httpurlconnection.setRequestMethod("POST");
                    httpurlconnection.setDoOutput(true);
                    httpurlconnection.setDoInput(true);

                    OutputStream outputstream=httpurlconnection.getOutputStream();
                    BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputstream,"UTF-8"));

                    String post_data= URLEncoder.encode("name", "UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"
                            +URLEncoder.encode("surname", "UTF-8")+"="+URLEncoder.encode(surname,"UTF-8")+"&"
                            +URLEncoder.encode("age", "UTF-8")+"="+URLEncoder.encode(age,"UTF-8")+"&"
                            +URLEncoder.encode("user_name", "UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                            +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");

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
            if(result.equals("Thanks for Registering yourself")) {
                Toast.makeText(Register.this, result, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Register.this, Home.class);
                startActivity(intent);
            }
            else
            {
                // alertDialog.setMessage(result);
                //alertDialog.show();
                Toast.makeText(Register.this, result, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
