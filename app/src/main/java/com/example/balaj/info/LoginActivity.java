package com.example.balaj.info;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {
    EditText name;
    Button searchbt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name=(EditText) findViewById(R.id.nametxt);
        searchbt=(Button)findViewById(R.id.searchbtn);


    }

    public void OnSearch(View view){

        String na=name.getText().toString();
        String type="login";

        BackWorker backWorker=new BackWorker(this);
        backWorker.execute(type,na);
    }
}
class BackWorker extends AsyncTask<String,Void ,String> {

    Context context;
    AlertDialog alertDialog;
    BackWorker (Context ctx)
    {
        context=ctx;

    }
    @Override
    protected String doInBackground(String... params) {
        String type= params[0];
        String log_url="https://programmer8870.000webhostapp.com/test/login.php";
        if(type.equals("login")) {
            try {
                String names = params[1];
                URL url = new URL(log_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("names", "UTF-8") + "=" + URLEncoder.encode(names, "UTF-8");
                bufferWriter.write(post_data);
                bufferWriter.flush();
                bufferWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
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
        alertDialog=new AlertDialog.Builder(context).create();
        alertDialog.setTitle("LOGIN STATUS");
    }

    @Override
    protected void onPostExecute(String result) {
        //   alertDialog.setMessage(result);
        //alertDialog.show();
        //  Toast.makeText(context,result,Toast.LENGTH_LONG).show();
        if (result.equals("not matched")){
            Toast.makeText(context,result,Toast.LENGTH_SHORT).show();

        }else {
            Intent logIntent = new Intent(context, ViewActivity.class);
            logIntent.putExtra("res", result);
            context.startActivity(logIntent);
        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}


