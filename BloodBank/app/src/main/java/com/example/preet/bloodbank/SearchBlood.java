package com.example.preet.bloodbank;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class SearchBlood extends AppCompatActivity {
    AutoCompleteTextView autocomplete;
    TextView txtData;
    Button btnSearch1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_blood);

        StrictMode.enableDefaults();
        txtData = (TextView) findViewById(R.id.nameView);
btnSearch1=(Button)findViewById(R.id.btnsearch);
        autocomplete = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        String[] arr = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O-", "O+"};


        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, arr);

        autocomplete.setThreshold(2);
        autocomplete.setAdapter(adapter);
       // getdata();


    }

    public void getdata(View view)
    {
        String result = "";
        InputStream is = null;
        String searchstr=autocomplete.getText().toString();
        try {
            Toast.makeText(this,searchstr,Toast.LENGTH_SHORT).show();
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.56.1/project1/bloodbank/searchblood.php");
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();


            OutputStream outputstream;
            List<NameValuePair> nameValuepair=new ArrayList<NameValuePair>(0);
            nameValuepair.add(new BasicNameValuePair("searchstr",searchstr));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuepair));

     httpclient.execute(httppost);


            Log.e("log_tag", "connection success");
            //   Toast.makeText(getApplicationContext(), "pass", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection" + e.toString());
            Toast.makeText(getApplicationContext(),"Hello"+ e.toString(), Toast.LENGTH_SHORT).show();

        }
        //convert response to string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
                //  Toast.makeText(getApplicationContext(), "Input Reading pass", Toast.LENGTH_SHORT).show();
            }
            is.close();

            result = sb.toString();
        } catch (Exception e) {
            Log.e("log_tag", "Error converting result" + e.toString());
            Toast.makeText(getApplicationContext(),"Hi"+ e.toString(), Toast.LENGTH_SHORT).show();

        }

        //parse json data
        try {
            JSONArray jArray = new JSONArray(result);
            StringBuffer buffer=new StringBuffer();
            for (int i = 0; i < jArray.length(); i++) {

                JSONObject json_data = jArray.getJSONObject(i);
           buffer.append("Blood Group: "+json_data.getString("Bloodgroupname"));
                buffer.append("Units: "+json_data.getString("No. of bottles"));
                buffer.append("Status: "+json_data.getString("Available"));
                txtData.setText(buffer.toString());


            }
        }
        catch (JSONException e) {
            Log.e("log_tag", "Error parsing data" + e.toString());
            Toast.makeText(SearchBlood.this,"Hi Hi"+ e.toString(), Toast.LENGTH_LONG).show();

            //Toast.makeText(getApplicationContext(), "JsonArray fail", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            Log.e("log_tag", "Error parsing data" + e.toString());
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
