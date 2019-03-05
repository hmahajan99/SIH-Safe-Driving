package com.icos.anshulsingh.sih;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Projection extends AppCompatActivity {

    TextView tvX,tvY,speed_net;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projection);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvX= findViewById(R.id.tvX);
        tvY= findViewById(R.id.tvY);
        speed_net = findViewById(R.id.speed_net);
        try {
            HttpPost();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    private String HttpPost() throws IOException, JSONException {

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        String result = "";

        URL url = new URL("https://alertme123.herokuapp.com/api");

        // 1. create HttpURLConnection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

        // 2. build JSON object
        JSONObject jsonObject = buidJsonObject();

        // 3. add JSON content to POST request body
        setPostRequestContent(conn, jsonObject);

        // 4. make POST request to the given URL
        conn.connect();


        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        String s = ((BufferedReader) in).readLine();
        Log.d("hey",s);
        StringBuilder LSpeed = new StringBuilder() , RSpeed = new StringBuilder() , NSpeed = new StringBuilder();

        int j=0,k=0;

        for(int i=0;i<s.length();i++){
            if(s.charAt(i) == '"'){
                j++;
                i++;
            }
            if(j==3){
                LSpeed.append(s.charAt(i));
            }
            if(j == 7){
                RSpeed.append(s.charAt(i));
            }




        }
        tvX.setText(LSpeed+"km/hr");
        tvY.setText(RSpeed+"km/hr");

        Double speedL = Double.valueOf(String.valueOf(LSpeed));
        Double speedR = Double.valueOf(String.valueOf(RSpeed));

        Double totalSpeed = Math.sqrt(speedL*speedL + speedR*speedR);
        speed_net.setText(totalSpeed.toString()+"km/hr");






//        for (int c; (c = in.read()) >= 0;){
//            Log.d("hey",String.valueOf(c));
//            System.out.print((char)c);}
        // 5. return response message
        Log.d("hey",conn.getResponseMessage());
        return conn.getResponseMessage()+"";

    }

    private JSONObject buidJsonObject() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("exp","0.215315166441393,0.15005541484228635,0.7914562008951398,-9.,60.,-62.,90.,55.,65");


        Log.d("hey",jsonObject.toString());
        return jsonObject;
    }

    private void setPostRequestContent(HttpURLConnection conn,
                                       JSONObject jsonObject) throws IOException {

        String s = conn.getOutputStream().toString();
        Log.d("hey",s);
        OutputStream os = conn.getOutputStream();

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(jsonObject.toString());
        Log.i(MainActivity.class.toString(), jsonObject.toString());
        Log.d("hey",jsonObject.toString());
        writer.flush();
        writer.close();
        os.close();
    }



}
