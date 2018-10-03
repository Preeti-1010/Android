package com.example.preet.bloodbank;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class Login extends AppCompatActivity {
    EditText edttxt, edttxt1;
    Button b1, b2,b3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        b1 = (Button) findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button2);
        b3=(Button)findViewById(R.id.button3);
        edttxt = (EditText) findViewById(R.id.editText);
        edttxt1 = (EditText) findViewById(R.id.editText2);

        final String Email = edttxt.getText().toString();
        final String Password = edttxt1.getText().toString();

       /* b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edttxt.getText().toString().length() == 0) {
                    edttxt.setError("Please Enter Email");
                }
                if (edttxt1.getText().toString().length() == 0) {
                    edttxt1.setError("Enter Password");
                } else {
                    Thread timerThread = new Thread() {
                        public void run() {
                            try {
                                sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } finally {
                                Intent intent = new Intent(Login.this, Home.class);
                                startActivity(intent);
                            }
                        }
                    };
                    timerThread.start();
                }
            }
        });*/
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edttxt.getText().toString().length() != 0) {
                    edttxt.setText("");
                }
                if (edttxt1.getText().toString().length() != 0) {
                    edttxt1.setText("");
                }
            }
        });
    }
        public void regis(View view){
Intent intent=new Intent(Login.this,Registration.class);
            startActivity(intent);
          }

    public void onLogin(View view)
    {
        String Email=edttxt.getText().toString();
        String Password=edttxt1.getText().toString();
        String type="login";
        BackgroundWorker backgroundworker=new BackgroundWorker(this);
        backgroundworker.execute(type, Email, Password);
    }


    public class BackgroundWorker extends AsyncTask<String,Void,String> {
        Context context;
        AlertDialog alertDialog;
        BackgroundWorker(Context ctx)
        {
            context=ctx;
        }
        private Dialog loadingDialog;
        @Override
        protected String doInBackground(String... params) {
            String type=params[0];
            String login_url="http://192.168.56.1/project1/bloodbank/login.php";
            if(type.equals("login"))
            {
                try {
                    String Email=params[1];
                    String Password=params[2];
                    URL url=new URL(login_url);
                    HttpURLConnection httpurlconnection=(HttpURLConnection)url.openConnection();
                    httpurlconnection.setRequestMethod("POST");
                    httpurlconnection.setDoOutput(true);
                    httpurlconnection.setDoInput(true);

                    OutputStream outputstream=httpurlconnection.getOutputStream();
                    BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputstream,"UTF-8"));

                    String post_data= URLEncoder.encode("Email", "UTF-8")+"="+URLEncoder.encode(Email,"UTF-8")+"&"
                            +URLEncoder.encode("Password","UTF-8")+"="+URLEncoder.encode(Password,"UTF-8");

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
          //   alertDialog=new AlertDialog.Builder(context).create();
             // alertDialog.setTitle("Login Status");
          //  loadingDialog = ProgressDialog.show(Login.this, "Please wait", "Authenticating...");
        }

        @Override
        protected void onPostExecute(String result) {
            //  alertDialog.setMessage(result);
            //   alertDialog.show();

            if(result.equals("success")) {
                Toast.makeText(Login.this, result, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login.this, Home.class);
                startActivity(intent);
            }
            else
            {
                // alertDialog.setMessage(result);
                //alertDialog.show();
                Toast.makeText(Login.this, result, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}



