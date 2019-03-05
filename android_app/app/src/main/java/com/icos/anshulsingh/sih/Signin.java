package com.icos.anshulsingh.sih;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;

public class Signin extends AppCompatActivity {

    ProgressDialog loading;
    SharedPreferences sharedPreferences;
    EditText etUsernameSignin,etNameSignin;
    Button btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        etNameSignin = findViewById(R.id.etNameSignin);
        etUsernameSignin = findViewById(R.id.etUserNameSignin);
        btnSignIn = findViewById(R.id.btnSignin);

        loading = new ProgressDialog(Signin.this);
        loading.setTitle("Signing In");
        loading.setMessage("Please Wait");
        loading.setCancelable(true);
        sharedPreferences = getSharedPreferences("myPref" , Context.MODE_PRIVATE);
        String findStatus = sharedPreferences.getString("name","0");
        if(findStatus.equals("0")){

        }else{
                Intent i = new Intent(Signin.this,Dashboard.class);
                startActivity(i);
                finish();

        }
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.show();
                String username = etUsernameSignin.getText().toString();
                String name = etNameSignin.getText().toString();


                sharedPreferences = getSharedPreferences("myPref" , Context.MODE_PRIVATE);
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.putString("name",name).commit();
                editor.putString("username",username).commit();
                person p= new person(name,username);
                FirebaseDatabase.getInstance().getReference().child("Users").child(username).setValue(p);
                Intent i = new Intent(Signin.this,Dashboard.class);
                loading.dismiss();
                startActivity(i);
                finish();
            }
        });





    }
}
