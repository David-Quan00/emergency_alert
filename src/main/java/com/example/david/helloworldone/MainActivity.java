package com.example.david.helloworldone;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.content.Intent;

import android.content.pm.PackageManager;

import android.telephony.SmsManager;
import android.location.LocationManager;
import android.location.LocationListener;
import android.location.Location;
import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.Manifest;
import android.widget.TextView;
import android.media.AudioManager;
import android.media.MediaPlayer;

import eu.bitm.NominatimReverseGeocoding.Address;
import eu.bitm.NominatimReverseGeocoding.NominatimReverseGeocodingJAPI;


public class MainActivity extends AppCompatActivity {

    //final TextView textViewToChange = (TextView) findViewById(R.id.textView4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //GPSToggle();

        Button medical_id_btn = (Button) findViewById(R.id.medical_id_btn);
        medical_id_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent starting = new Intent(getApplicationContext(), User_Profile.class);
                startActivity(starting);
            }
        });

        Button settings_btn = (Button) findViewById(R.id.settings_btn);
        settings_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent starting = new Intent(getApplicationContext(),Settings_Page.class);
                startActivity(starting);
            }
        });

        Button call_btn = (Button) findViewById(R.id.call_btn);
        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent starting = new Intent(getApplicationContext(),Call_Page.class);
                startActivity(starting);
            }
        });
    }


    protected void GPSToggle() {

        obtainAllPermissions();
        registerLocationListener();
        //textViewToChange.setText("We're waiting for location updates!");

        //these functions have been tested
        //playHelpSound();
        //sendSMS("+14156098078", "BostonHacksFirstTextMessage");       //FIXME: change the phone number


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//asks for permissions and returns true if the user has granted permissions (although not necessarily from granting it when being asked just now)
    public boolean obtainAllPermissions()
    {
        int MY_PERMISSIONS_ALL_NECESSARY_PERMISSIONS = 1;
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                MY_PERMISSIONS_ALL_NECESSARY_PERMISSIONS);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }
        return false;
    }

    public void registerLocationListener()
    {
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                //textViewToChange.setText(location.getLatitude() + " " + location.getLongitude());
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            //textViewToChange.setText("Location manager registered");
            System.out.println("Location manager registered");
        }
    }

    public void setVolumeMax()
    {
        AudioManager am = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, am.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
    }

    public void playHelpSound(){
        try {
            setVolumeMax();
            MediaPlayer mp = MediaPlayer.create(this, R.raw.helpsound);
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendSMS(String phoneNumber, String message)
    {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }

    public Address locationToAddress(double lat, double lon)
    {
        return new NominatimReverseGeocodingJAPI().getAdress(lat, lon);
    }

    public Address locationToAddress(Location input)
    {
        return locationToAddress(input.getLatitude(), input.getLongitude());
    }
}
