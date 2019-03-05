package com.icos.anshulsingh.sih;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;

public class GetPostReqData extends AppCompatActivity {

    EditText et1,et2,et3,et4,et5,et6,et7,et8,et9;
    Button btnaddJSON;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_post_req_data);

//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);
        et5 = findViewById(R.id.et5);
        et6 = findViewById(R.id.et6);
        et7 = findViewById(R.id.et7);
        et8 = findViewById(R.id.et8);
        et9 = findViewById(R.id.et9);

        btnaddJSON = findViewById(R.id.btnaddJSON);


        btnaddJSON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = "";
                String s1 = et1.getText().toString();
                String s2 = et2.getText().toString();
                String s3 = et3.getText().toString();
                String s4 = et4.getText().toString();
                String s5 = et5.getText().toString();
                String s6 = et6.getText().toString();
                String s7 = et7.getText().toString();
                String s8 = et8.getText().toString();
                String s9 = et9.getText().toString();


                result = s1+","+s2+","+s3+","+s4+","+s5+","+s6+","+s7+","+s8+","+s9;
                sharedPreferences = getSharedPreferences("myPref" , Context.MODE_PRIVATE);
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.putString("values",result).commit();
                Intent i = new Intent(GetPostReqData.this,Dashboard.class);
                startActivity(i);

            }
        });

    }
}
