package com.example.balaj.info;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity {
   TextView name,mail,db;
    Button ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        name=(TextView) findViewById(R.id.viewname);
        mail=(TextView) findViewById(R.id.viewemail);
        db=(TextView)findViewById(R.id.viewdob);
        ok=(Button)findViewById(R.id.okbtn);

        String res[] = (getIntent().getStringExtra("res")).split("#");
        name.setText(res[0]);
        mail.setText(res[1]);
        db.setText(res[2]);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte=new Intent(ViewActivity.this,MainActivity.class);
                startActivity(inte);
            }
        });

    }
}
