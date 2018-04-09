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

public class MainActivity extends AppCompatActivity {

    EditText name,mail,dob;
    TextView log;
    Button sub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=(EditText)findViewById(R.id.name);
        mail=(EditText)findViewById(R.id.mail);
        dob=(EditText) findViewById(R.id.dob);
        log=(TextView)findViewById(R.id.loginlbl);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intea=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intea);
            }
        });

    }
    public void OnStore(View view){

        String na=name.getText().toString();
//        String date=String.valueOf(dob.getText().toString());
        String email=mail.getText().toString();
        String date=dob.getText().toString();
        String type="store";

        BackgroundWorker backgroundWorker=new BackgroundWorker(this);
        backgroundWorker.execute(type,na,email,date);
        name.setText("");
        mail.setText("");
        dob.setText("");

    }
}

class BackgroundWorker extends AsyncTask<String,Void,String> {

    Context context;
    AlertDialog alertDialog;
    BackgroundWorker (Context ctx)
    {
        context=ctx;

    }
    @Override
    protected String doInBackground(String... params) {
        String type= params[0];
        String log_url="https://programmer8870.000webhostapp.com/test/store.php";
        if(type.equals("store")) {
            try {
                String names = params[1];
                String emailid = params[2];
                String date_birth = params[3];
                URL url = new URL(log_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("names", "UTF-8") + "=" + URLEncoder.encode(names, "UTF-8") + "&" + URLEncoder.encode("emailid", "UTF-8") + "=" + URLEncoder.encode(emailid, "UTF-8")+ "&" + URLEncoder.encode("date_birth", "UTF-8") + "=" + URLEncoder.encode(date_birth, "UTF-8");
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
        Toast.makeText(context,result,Toast.LENGTH_LONG).show();


       // Intent logIntent = new Intent(LoginActivity.this, Fee2Activity.class);
        //logIntent.putExtra("re",result);
       // startActivity(logIntent);

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}

