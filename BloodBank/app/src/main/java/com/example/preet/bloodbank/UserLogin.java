package com.example.preet.bloodbank;

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


public class UserLogin extends AppCompatActivity {
    EditText edtUSer,edtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        edtUSer=(EditText)findViewById(R.id.etUserID);
        edtPass=(EditText)findViewById(R.id.etPassword);
    }
    public void onLogin(View view)
    {
        String username=edtUSer.getText().toString();
        String password=edtPass.getText().toString();
        String type="login";
        BackgroundWorker backgroundworker=new BackgroundWorker(this);
        backgroundworker.execute(type, username, password);
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
            String login_url="http://192.168.56.1/project1/login.php";
            if(type.equals("login"))
            {
                try {
                    String user_name=params[1];
                    String password=params[2];
                    URL url=new URL(login_url);
                    HttpURLConnection httpurlconnection=(HttpURLConnection)url.openConnection();
                    httpurlconnection.setRequestMethod("POST");
                    httpurlconnection.setDoOutput(true);
                    httpurlconnection.setDoInput(true);

                    OutputStream outputstream=httpurlconnection.getOutputStream();
                    BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputstream,"UTF-8"));

                    String post_data= URLEncoder.encode("user_name", "UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
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
            if(result.equals("success")) {
                Toast.makeText(UserLogin.this, result, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserLogin.this, Home.class);
                startActivity(intent);
            }
            else
            {
                // alertDialog.setMessage(result);
                //alertDialog.show();
                Toast.makeText(UserLogin.this, result, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }





}
