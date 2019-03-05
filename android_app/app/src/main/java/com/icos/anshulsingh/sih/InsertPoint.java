package com.icos.anshulsingh.sih;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class InsertPoint extends AppCompatActivity {

    EditText etLatIn,etLonIn,etPorbIn;
    Button btnAddMyCoord,btnAddCoord,btnsee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_point);

//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etLatIn = findViewById(R.id.etLatIn);
        etLonIn = findViewById(R.id.etLonIn);
        etPorbIn = findViewById(R.id.etPorbIn);

        btnAddCoord = findViewById(R.id.btnAddCoord);
        btnAddMyCoord = findViewById(R.id.btnAddMyCoord);
        btnsee = findViewById(R.id.btnsee);

        btnsee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InsertPoint.this,Dashboard.class);
                startActivity(i);
                finish();
            }
        });

        btnAddMyCoord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accident_data ac=new accident_data();
                ac.lat = 25.6205546;
                ac.lon = 85.1741373;
                ac.acc_val= Double.valueOf(etPorbIn.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("Accident Data").push().setValue(ac);
                Toast.makeText(InsertPoint.this, "Added", Toast.LENGTH_SHORT).show();

            }
        });

        btnAddCoord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accident_data ac= new accident_data();
                ac.lat = Double.valueOf(etLatIn.getText().toString());
                ac.lon = Double.valueOf(etLonIn.getText().toString());
                ac.acc_val = Double.valueOf(etPorbIn.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("Accident Data").push().setValue(ac);
                Toast.makeText(InsertPoint.this, "Added", Toast.LENGTH_SHORT).show();


            }
        });



    }
}
