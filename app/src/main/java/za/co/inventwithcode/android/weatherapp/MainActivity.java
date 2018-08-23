package za.co.inventwithcode.android.weatherapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.widget.TextView;
import android.widget.Toast;
import data.HttpClientWeather;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;

import data.HttpClientWeather;

public class MainActivity extends AppCompatActivity {

    TextView d1Temperature, d2Temperature, d3Temperature, d4Temperature, d5Temperature;
    TextView d1Day, d2Day, d3Day, d4Day, d5Day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        d1Temperature = (TextView) findViewById(R.id.d1Temperature);
        d2Temperature = (TextView) findViewById(R.id.d2Temperature);
        d3Temperature = (TextView) findViewById(R.id.d3Temperature);
        d4Temperature = (TextView) findViewById(R.id.d4Temperature);
        d5Temperature = (TextView) findViewById(R.id.d5Temperature);

        d2Day.setText("Tuesday");


        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

// Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                //Refresh5DaysWeather(location);
                Toast.makeText(MainActivity.this, location.getLatitude()+"", Toast.LENGTH_SHORT).show();
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                Toast.makeText(MainActivity.this, "Status Changed", Toast.LENGTH_SHORT).show();
            }

            public void onProviderEnabled(String provider) {
                Toast.makeText(MainActivity.this, "Provider Enabled", Toast.LENGTH_SHORT).show();
            }

            public void onProviderDisabled(String provider) {
                Toast.makeText(MainActivity.this, "Provider Disabled", Toast.LENGTH_SHORT).show();
            }

        };


// Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Location Access required to update weather forcasts", Toast.LENGTH_LONG);
        }
        else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, locationListener);
            Toast.makeText(this, "Location requested", Toast.LENGTH_SHORT).show();
        }
    }

    public void Refresh5DaysWeather(Location loc){
        d1Temperature.setText("21");
    }

    //Log.d("JSONData>>",HttpClientWeather.getJSONWeatherData(23, 23));

}
