package com.icos.anshulsingh.sih;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CircleAnimate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_animate);

        Circle circle = (Circle) findViewById(R.id.circle);

        CircleAngleAnimate animation = new CircleAngleAnimate(circle, 360);
        animation.setDuration(1000);
        circle.startAnimation(animation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }
}
