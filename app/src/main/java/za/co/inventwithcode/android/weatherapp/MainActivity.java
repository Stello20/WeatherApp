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
import android.view.View;
import android.webkit.ConsoleMessage;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import data.HttpClientWeather;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.security.acl.LastOwnerException;

import data.HttpClientWeather;

public class MainActivity extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 500;
    public static final String API_URL_Forecast5 = "api.openweathermap.org/data/2.5/forecast?";
    public static final String API_URL_Weather = "api.openweathermap.org/data/2.5/weather?";

    TextView d1Day, d2Day, d3Day, d4Day, d5Day;
    ImageView d1Weather, d2Weather, d3Weather, d4Weather, d5Weather;
    TextView d1Temperature, d2Temperature, d3Temperature, d4Temperature, d5Temperature;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        d1Temperature = (TextView) findViewById(R.id.d1Temperature);
        d2Temperature = (TextView) findViewById(R.id.d2Temperature);
        d3Temperature = (TextView) findViewById(R.id.d3Temperature);
        d4Temperature = (TextView) findViewById(R.id.d4Temperature);
        d5Temperature = (TextView) findViewById(R.id.d5Temperature);

        d1Day = (TextView) findViewById(R.id.d1Day);
        d2Day = (TextView) findViewById(R.id.d2Day);
        d3Day = (TextView) findViewById(R.id.d3Day);
        d4Day = (TextView) findViewById(R.id.d4Day);
        d5Day = (TextView) findViewById(R.id.d5Day);

        d1Weather = (ImageView) findViewById(R.id.d1Weather);
        d2Weather = (ImageView) findViewById(R.id.d2Weather);
        d3Weather = (ImageView) findViewById(R.id.d3Weather);
        d4Weather = (ImageView) findViewById(R.id.d4Weather);
        d5Weather = (ImageView) findViewById(R.id.d5Weather);

        requestLocationPermissions();
        beginLocationRequirements();



    }

    public void requestLocationPermissions(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                Toast.makeText(this, "Explaination: We need access", Toast.LENGTH_SHORT);
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
        else{
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    public void beginLocationRequirements(){
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                refresh5DaysWeather(location.getLatitude(), location.getLongitude());
                Log.d("Location: ", "LocationChanged > " + location.toString());
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.d("Location: ", "StatusChanged > ");
            }

            public void onProviderEnabled(String provider) {
                Log.d("Location: ", "Provider Enabled > ");
            }

            public void onProviderDisabled(String provider) {
                Log.d("Location: ", "Provider Disabled > " );
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermissions();
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        String locationProvider = LocationManager.NETWORK_PROVIDER;
        locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        refresh5DaysWeather(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude());


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "PERMISSIONS WERE GRANTED", Toast.LENGTH_SHORT).show();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
    public void refresh5DaysWeather(double lat, double lon){
        d1Temperature.setText(lat+"__"+lon);//Remove this
        //d1Day.setText(HttpClientWeather.getJSONData(API_URL_Forecast5+"lat="+lat+"&lon="+lon));
        Log.d("Location: ", "Refreshed > ");

    }

    //Log.d("JSONData>>",HttpClientWeather.getJSONWeatherData(23, 23));

}
