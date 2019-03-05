package com.icos.anshulsingh.sih;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    Integer a = 0;
    JSONObject jsonObject;
    Integer checker = 0,checkit=0;
    TextView name_header, username_header,prediction;
    SharedPreferences sharedPreferences;
    Integer check = 0;
    TextView leftSpeed, rightSpeed, netSpeed;
    public Double currentLongitude = 0.0;
    public Double currentLatitude = 0.0;
    MapsFragment mapsFragment;
    Handler h = new Handler();
    Runnable runnable;
    ArrayList<accident_data> labels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        prediction = findViewById(R.id.prediction);
        sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "0");
        String username = sharedPreferences.getString("username", "0");


//        for(int i=0;i<100;i++) {
//            Double rangeMin = 25.0;
//            Double rangeMax = 26.0;
//            Random r = new Random();
//            double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
//            accident_data ac = new accident_data();
//            ac.lat = randomValue;
//
//            rangeMin = 85.0;
//            rangeMax = 86.0;
//            randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
//            ac.lon = randomValue;
//
//            rangeMin = 20.0;
//            rangeMax = 80.0;
//            randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
//            ac.acc_val = randomValue;
//
//
//            FirebaseDatabase.getInstance().getReference().child("Accident Data").push().setValue(ac);
//
//        }

        leftSpeed = findViewById(R.id.leftSpeed);
        rightSpeed = findViewById(R.id.rightSpeed);
        netSpeed = findViewById(R.id.netSpeed);

//        postData();
        try {
            HttpPost();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerLayout = navigationView.getHeaderView(0);
        name_header = headerLayout.findViewById(R.id.name_header);
        username_header = headerLayout.findViewById(R.id.username_header);

        name_header.setText(name);
        username_header.setText(username);

        mapsFragment = new MapsFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.mainLayout, mapsFragment).commit();

        Log.d("hey", "Size of labels" + String.valueOf(labels.size()));
        runnable = new Runnable() {
            @Override
            public void run() {
                getLocation();


                Double leastDist = 10000000.0;
                Double acc_prob = 0.0;
                Double leastLat = 10000.0;
                Double leastLon = 10000.0;
                for (int i = 0; i < labels.size(); i++) {


                    Double distance = Math.pow((labels.get(i).lat - currentLatitude), 2) +
                            Math.pow((labels.get(i).lon - currentLongitude), 2);


                    if (distance < leastDist) {
                        leastLat = labels.get(i).lat;
                        leastLon = labels.get(i).lon;
                        leastDist = distance;
                        acc_prob = labels.get(i).acc_val;
                    }


//                    Double distance = (labels.get(i).lat-currentLatitude)*2
//                            + (labels.get(i).lon-currentLongitude)*2;
//
//                    if(distance < leastDist)
//                    {
//                        leastDist = distance;
//                        acc_prob = labels.get(i).acc_val;
//                    }
//
//                    Log.d("heya",labels.get(i).acc_val.toString());

                }



                LatLng loc = new LatLng(leastLat, leastLon);
                mapsFragment.map.addMarker(new MarkerOptions().position(loc)
                        .title("Nearest data point accident prob: "+acc_prob+"%"));


//                Log.d("hey","Least : "+leastDist);
//
                Log.d("current", leastLat.toString());
                Log.d("current", leastLon.toString());
                Log.d("current","Lat"+currentLatitude.toString());
                Log.d("current","Lon"+currentLongitude.toString());
//
                Log.d("current", acc_prob.toString());
//
//                Log.d("hey","Prob: "+acc_prob.toString());
//
//
//
//                Log.d("hey1",acc_prob.toString());

                if (leastDist > 1) {

                    prediction.setText("Pay Attention While Driving");
//                    addNotification("Pay Attention While Driving");

                } else {

                    if (acc_prob < 30) {


                        prediction.setText("In Safe Zone");
//                        addNotification("In Safe Zone");

                    }

                    if (acc_prob > 30 && acc_prob < 60) {

                        prediction.setText("Risky Area");
//                        addNotification("Risky Area");

                    }

                    if (acc_prob > 60) {

                        if(a == 0){
                            a++;
                            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.danger_sound);
                                mp.start();
                        }
                        if (check == 0) {
                            check = 1;
//                            alert();
                        }

                        checker++;
//                        if(checker < 50) {
                        if (check == 2) {

                        } else {
                            if (checker % 10 == 0) {
//                                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.danger_sound);
//                                mp.start();
//                        }
                            }
                        }
                        prediction.setText("Danger Zone");
                        addNotification("Danger Zone");
                    }


                }


                h.postDelayed(runnable, 1000);
            }
        };

        FirebaseDatabase.getInstance().getReference().child("Accident Data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    final accident_data val = data.getValue(accident_data.class);
                    labels.add(val);

                }

                h.postDelayed(runnable, 10000);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {

            SharedPreferences settings = Dashboard.this.getSharedPreferences("myPref", Context.MODE_PRIVATE);
            settings.edit().clear().commit();
            Intent i = new Intent(Dashboard.this, Signin.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_proj) {

            Intent i = new Intent(Dashboard.this, Projection.class);
            startActivity(i);


        } else if (id == R.id.nav_dashboard) {
            Intent i = new Intent(Dashboard.this,Dashboard.class);
            startActivity(i);
            finish();


        } else if (id == R.id.nav_share) {

            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.whatsapp");
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Try this new app to drive safe in fog");
            startActivity(whatsappIntent);

        } else if (id == R.id.nav_signout) {

            SharedPreferences settings = Dashboard.this.getSharedPreferences("myPref", Context.MODE_PRIVATE);
            settings.edit().clear().commit();
            Intent i = new Intent(Dashboard.this, Signin.class);
            startActivity(i);
            finish();
        }else if(id == R.id.nav_addcoord){
            Intent i = new Intent(Dashboard.this,InsertPoint.class);
            startActivity(i);

        }else if(id == R.id.nav_postreq){

            Intent i = new Intent(Dashboard.this,GetPostReqData.class);
            startActivity(i);
        }
//        else if(id == R.id.nav_view){
//            Intent i = new Intent(Dashboard.this,CircleAnimate.class);
//            startActivity(i);
//        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getDeviceLocation() {
        //get location manger instance
        LocationManager locationManager = (LocationManager) Dashboard.this.getSystemService(Context.LOCATION_SERVICE);

        //check if we have permission to access device location
        if (ActivityCompat.checkSelfPermission(Dashboard.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(Dashboard.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        //add location change listener with update duration 2000 millicseconds or 10 meters
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, new LocationListener() {
            public void onLocationChanged(Location location) {
                currentLongitude = location.getLongitude();
                currentLatitude = location.getLatitude();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }

        });


        //get last known locati on to start with
        Location myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        currentLatitude = myLocation.getLatitude();
        currentLongitude = myLocation.getLongitude();
    }


    private void addNotification(String str) {
        Log.d("hey","notific");
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                        .setContentTitle("Alert")
                        .setContentText(str);

        Intent notificationIntent = new Intent(this, Dashboard.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    public void postData() {

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("https://alertme123.herokuapp.com/api");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("exp", "0.21531516644139326,0.15005541484228635,0.7914562008951398,-2.,39.,-62.,76.,55.,65."));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

//            HashMap<String,String> maps = new HashMap<>();
//            JSONObject jsonObject = new JSONObject((Map) nameValuePairs);
//            maps.put("exp", "0.21531516644139326,0.15005541484228635,0.7914562008951398,-2.,39.,-62.,76.,55.,65.");
//            httppost.setEntity(new UrlEncodedFormEntity((List<? extends NameValuePair>) maps));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            String responseBody = getResponseBody(response);
            Log.d("hey", responseBody);


        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

    }

    public static String getResponseBody(HttpResponse response) {

        String response_text = null;
        HttpEntity entity = null;
        try {
            entity = response.getEntity();
            response_text = _getResponseBody(entity);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            if (entity != null) {
                try {
                    entity.consumeContent();
                } catch (IOException e1) {
                }
            }
        }

        return response_text;
    }


    public static String _getResponseBody(final HttpEntity entity) throws IOException, ParseException {

        if (entity == null) {
            throw new IllegalArgumentException("HTTP entity may not be null");
        }

        InputStream instream = entity.getContent();

        if (instream == null) {
            return "";
        }

        if (entity.getContentLength() > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(

                    "HTTP entity too large to be buffered in memory");
        }

        String charset = getContentCharSet(entity);

        if (charset == null) {

            charset = HTTP.DEFAULT_CONTENT_CHARSET;

        }

        Reader reader = new InputStreamReader(instream, charset);

        StringBuilder buffer = new StringBuilder();

        try {

            char[] tmp = new char[1024];

            int l;

            while ((l = reader.read(tmp)) != -1) {

                buffer.append(tmp, 0, l);

            }

        } finally {

            reader.close();

        }

        return buffer.toString();

    }

    public static String getContentCharSet(final HttpEntity entity) throws ParseException {

        if (entity == null) {
            throw new IllegalArgumentException("HTTP entity may not be null");
        }

        String charset = null;

        if (entity.getContentType() != null) {

            HeaderElement values[] = entity.getContentType().getElements();

            if (values.length > 0) {

                NameValuePair param = values[0].getParameterByName("charset");

                if (param != null) {

                    charset = param.getValue();

                }

            }

        }

        return charset;

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
        Log.d("hey", s);
        StringBuilder LSpeed = new StringBuilder(), RSpeed = new StringBuilder(), NSpeed = new StringBuilder();

        int j = 0, k = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '"') {
                j++;
                i++;
            }
            if (j == 3) {
                LSpeed.append(s.charAt(i));
            }
            if (j == 7) {
                RSpeed.append(s.charAt(i));
            }


        }



        Double speedL = Double.valueOf(String.valueOf(LSpeed));
        Double speedR = Double.valueOf(String.valueOf(RSpeed));

        Double pie = 3.14;
        Double Q = Math.atan(speedL/speedR)*180/pie;




        leftSpeed.setText(Q.toString()+"°");
        Double totalSpeed = Math.sqrt((speedL*speedL)+ (speedR * speedR));
        netSpeed.setText(totalSpeed.toString() + "km/hr");

        if(Q>0 && Q<90){
            rightSpeed.setText("N-E");

        }
        if(Q>90 && Q<180){
            rightSpeed.setText("N-W");
        }
        if(Q>180 && Q< 270){
            rightSpeed.setText("S-W");
        }
        if(Q>270 && Q<360 ){
            rightSpeed.setText("S-E");
        }
        if(Q == 0 && Q==360)
        {
            rightSpeed.setText("E");
        }

        if(Q == 90)
        {
            rightSpeed.setText("N");
        }

        if(Q ==180)
        {
            rightSpeed.setText("W");
        }

        if(Q == 270)
        {
            rightSpeed.setText("S");
        }

//        for (int c; (c = in.read()) >= 0;){
//            Log.d("hey",String.valueOf(c));
//            System.out.print((char)c);}
        // 5. return response message
        Log.d("hey", conn.getResponseMessage());
        return conn.getResponseMessage() + "";

    }

    private JSONObject buidJsonObject() throws JSONException {

        jsonObject = new JSONObject();
        sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        String values = sharedPreferences.getString("values", "0.215315166441393,0.15005541484228635,0.7914562008951398,-9.,60.,-62.,90.,55.,65");

        jsonObject.accumulate("exp", values);

        Log.d("hey", jsonObject.toString());
        return jsonObject;
    }

    private void setPostRequestContent(HttpURLConnection conn,
                                       JSONObject jsonObject) throws IOException {

        String s = conn.getOutputStream().toString();
        Log.d("hey", s);
        OutputStream os = conn.getOutputStream();

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(jsonObject.toString());
        Log.i(MainActivity.class.toString(), jsonObject.toString());
        Log.d("hey", jsonObject.toString());
        writer.flush();
        writer.close();
        os.close();
    }


//    public void alert() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
//
//        // Set the alert dialog title
//        builder.setTitle("Alert");
//
//        // Display a message on alert dialog
//        builder.setMessage("You're in Danger Zone Drive Safe");
//
//
//        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                check = 0;
//
//            }
//        });
//
//        builder.setNegativeButton("Disable", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                check = 2;
//            }
//        });
//
//
//        // Finally, make the alert dialog using builder
//        AlertDialog dialog = builder.create();
//
//        // Display the alert dialog on app interface
//        dialog.show();
//
//
//    }

    void getLocation() {
        // Get user location
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Keep track of user location.
        // Use callback/listener since requesting immediately may return null location.
        // IMPORTANT: TO GET GPS TO WORK, MAKE SURE THE LOCATION SERVICES ON YOUR PHONE ARE ON.
        // FOR ME, THIS WAS LOCATED IN SETTINGS > LOCATION.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, new Listener());
        // Have another for GPS provider just in case.
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, new Listener());
        // Try to request the location immediately
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location == null){
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        if (location != null){
            handleLatLng(location.getLatitude(), location.getLongitude());
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
            Log.d("hey","CurrLat"+currentLatitude);
            Log.d("hey","CurrLon"+currentLongitude);
            if(checkit < 1) {
                checkit++;
                LatLng pp1 = new LatLng(currentLatitude, currentLongitude);
                mapsFragment.map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        pp1, 16f

                ));
            }

        }



    }
    private void handleLatLng(double latitude, double longitude){
        Log.v("TAG", "(" + latitude + "," + longitude + ")");
    }
    private class Listener implements LocationListener {
        public void onLocationChanged(Location location) {
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            handleLatLng(latitude, longitude);
        }

        public void onProviderDisabled(String provider){}
        public void onProviderEnabled(String provider){}
        public void onStatusChanged(String provider, int status, Bundle extras){}
    }




}

