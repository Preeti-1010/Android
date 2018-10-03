package com.example.preet.bloodbank;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

public class DonarsList extends AppCompatActivity {
    Button b1, b2;
    RadioButton rdbtn1, rdbtn2;
    RadioGroup rg1;
    TextView t1,tv;
    EditText ed1, ed2, ed3, ed4, ed5, ed6;
    Spinner spin, spin2;
    AutoCompleteTextView autocomplete;
    DonarsDb myDb;
    String[] bloodgroup = {"O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"};
    String[] city = {"Gkp", "Lko", "Delhi", "Mumbai", "Pune", "Kanpur", "Allahabad", "Banaras"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donars_list);

        autocomplete=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);
        String[] arr = { "India", "Indonesia","America", "American", "United States","United Kingdom"};



        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, arr);

        autocomplete.setThreshold(2);
        autocomplete.setAdapter(adapter);


       // viewAllData();

        b1 = (Button) findViewById(R.id.btnsub);
        ed1 = (EditText) findViewById(R.id.editText3);
        ed2 = (EditText) findViewById(R.id.editText4);
        ed3 = (EditText) findViewById(R.id.editText5);
        ed4 = (EditText) findViewById(R.id.editText6);
        ed5 = (EditText) findViewById(R.id.editText7);
        ed6 = (EditText) findViewById(R.id.editText8);
        t1 = (TextView) findViewById(R.id.txt1);
        tv = (TextView) findViewById(R.id.txt);
        t1.setVisibility(View.VISIBLE);
        myDb = new DonarsDb(this);

    }
  /*  public void viewAllData() {
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
                            buffer.append("emp_id: " + res.getString(0) + "\n");
                            buffer.append("FullNamE: " + res.getString(1) + "\n");
                            buffer.append("Email: " + res.getString(2) + "\n");
                            buffer.append("Bloodgroup: " + res.getString(3) + "\n");
                            buffer.append("Gender: " + res.getString(4) + "\n");
                            buffer.append("Age: " + res.getString(5) + "\n");
                            buffer.append("City: " + res.getString(6) + "\n");
                            buffer.append("Mobileno: " + res.getString(7) + "\n");
                            buffer.append("Password: " + res.getString(8) + "\n");
                            buffer.append("Confirmpassword: " + res.getString(9) + "\n");
                            tv.setText(buffer.toString());
                        }
                    }

                } catch (SQLException e1) {
                    String error = e1.toString();
                    Dialog d = new Dialog(DonarsList.this);
                    d.setTitle("Thankyou");
                    TextView tv = new TextView(DonarsList.this);
                    tv.setText(error);
                    d.setContentView(tv);
                    d.show();
                    // Toast.makeText(Example.this, e1.toString(), Toast.LENGTH_LONG).show();
                } catch (Exception e) {

                    String error = e.toString();
                    Dialog d = new Dialog(DonarsList.this);
                    d.setTitle("Thank you");
                    TextView tv = new TextView(DonarsList.this);
                    tv.setText(error);
                    d.setContentView(tv);
                    d.show();

                }

            }
        });

    }*/

    public void viewAll(View view)
    {
        String FullName = ed1.getText().toString();
        String Email = ed2.getText().toString();
        String Bloodgroup = spin.getSelectedItem().toString();
        String Gender = spin.getSelectedItem().toString();
        String Age = ed3.getText().toString();
        String City = spin.getSelectedItem().toString();
        String Mobileno = ed4.getText().toString();
        String Password = ed5.getText().toString();
        String Confirmpassword = ed6.getText().toString();

        String type="view";
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
        private Dialog loadingDialog;
        @Override
        protected String doInBackground(String... params) {
            String type=params[0];
            String login_url="http://192.168.56.1/project1/bloodbank/donarlist.php";
            if(type.equals("login"))
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
                    URL url=new URL(login_url);
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
            //   alertDialog=new AlertDialog.Builder(context).create();
            // alertDialog.setTitle("Login Status");
            //  loadingDialog = ProgressDialog.show(Login.this, "Please wait", "Authenticating...");
        }

        @Override
        protected void onPostExecute(String result) {
            //  alertDialog.setMessage(result);
            //   alertDialog.show();

            if(result.equals("success")) {
                Toast.makeText(DonarsList.this, result, Toast.LENGTH_SHORT).show();

            }
            else
            {
                // alertDialog.setMessage(result);
                //alertDialog.show();
                Toast.makeText(DonarsList.this, result, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}


