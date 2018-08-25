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

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import data.HttpClientWeather;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 500;
    public static final int MY_PERMISSIONS_REQUEST_INTERNET = 501;
    public static final String API_URL_FORECAST = "http://api.openweathermap.org/data/2.5/forecast?";
    public static final String API_URL_WEATHER = "http://api.openweathermap.org/data/2.5/weather?";
    public static final String API_WEATHERFORECAST_KEY = "184abd8cc39ccb3cb938fa28067c1885";//on free account

    TextView d1Day, d2Day, d3Day, d4Day, d5Day;
    ImageView d1Weather, d2Weather, d3Weather, d4Weather, d5Weather;
    TextView d1Temperature, d2Temperature, d3Temperature, d4Temperature, d5Temperature;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        d1Temperature = findViewById(R.id.d1Temperature);
        d2Temperature = findViewById(R.id.d2Temperature);
        d3Temperature = findViewById(R.id.d3Temperature);
        d4Temperature = findViewById(R.id.d4Temperature);
        d5Temperature = findViewById(R.id.d5Temperature);

        d1Day = findViewById(R.id.d1Day);
        d2Day = findViewById(R.id.d2Day);
        d3Day = findViewById(R.id.d3Day);
        d4Day = findViewById(R.id.d4Day);
        d5Day = findViewById(R.id.d5Day);

        d1Weather = findViewById(R.id.d1Weather);
        d2Weather = findViewById(R.id.d2Weather);
        d3Weather = findViewById(R.id.d3Weather);
        d4Weather = findViewById(R.id.d4Weather);
        d5Weather = findViewById(R.id.d5Weather);

        requestLocationPermissions();
        requestInternetPermissions();
        beginLocationRequirements();
        //refresh5DaysWeather();



    }

    public void requestLocationPermissions(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                Toast.makeText(this, "Explanation: We need access", Toast.LENGTH_SHORT).show();
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
        else{
            Toast.makeText(this, "Permission to location already granted", Toast.LENGTH_SHORT).show();
        }
    }

    public void requestInternetPermissions(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
                Toast.makeText(this, "Explanation: We need access", Toast.LENGTH_SHORT);
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_INTERNET);
            }
        }
        else{
            Toast.makeText(this, "Permission to internet already granted", Toast.LENGTH_SHORT).show();
        }
    }

    public void beginLocationRequirements() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                //refresh5DaysWeather();
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)  {
            requestLocationPermissions();
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        String locationProvider = LocationManager.NETWORK_PROVIDER;
        locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        //refresh5DaysWeather(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude());


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "LOCATION PERMISSIONS WERE GRANTED", Toast.LENGTH_SHORT).show();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_INTERNET: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "INTERNET PERMISSIONS WERE GRANTED", Toast.LENGTH_SHORT).show();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Internet Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }



    public static void setForecastData(String URL_Forecast) throws JSONException {

        String JSONForecast = HttpClientWeather.getJSONData(URL_Forecast);
        JSONObject obj = new JSONObject(JSONForecast);
        JSONArray listArray = obj.getJSONArray("list"); //gives all weather forecasts 0 - 39
        for (int i = 0; i < listArray.length(); i+=8){  // loops through each weather record from 0 - 39
            JSONObject listObj = listArray.getJSONObject(i); //creates obj ref to current weather obj in list
            JSONArray weatherArray = listObj.getJSONArray("weather"); // creates arr ref to weather array
            JSONObject weatherObj = weatherArray.getJSONObject(0); //points to first and only ref of object in weatherList
            String weather = weatherObj.getString("main");
            String iconCode = weatherObj.getString("icon");
            JSONObject mainObj = listObj.getJSONObject("main");
            double temperature = mainObj.getDouble("temp");
            Log.d("JOSN:","Date time: "+listObj.getString("dt_txt"));
            Log.d("JOSN:","Day");
            Log.d("JOSN:","Temperature: "+Math.round(temperature - 273.15));//convert to Celcius
            Log.d("JOSN:","Weather: "+weather);
            Log.d("JOSN:","ICON code: "+iconCode);
        }

    }

    public static void setWeatherData(String URL_Weather) throws JSONException {
        String JSONWeather = HttpClientWeather.getJSONData(URL_Weather);
        JSONObject obj = new JSONObject(JSONWeather);
        JSONArray arr = obj.getJSONArray("weather");
        JSONObject weatherObj = arr.getJSONObject(0);
        System.out.println("Weather:"+weatherObj.getString("main"));
        JSONObject mainObj = obj.getJSONObject("main");
        Log.d("JOSN:","minTemp:"+mainObj.getDouble("temp_min"));
        Log.d("JOSN:","maxTemp:"+mainObj.getDouble("temp_max"));
        Log.d("JOSN:","Temp:"+mainObj.getDouble("temp"));
    }
    public void refresh5DaysWeather(){

        try {
            setWeatherData("http://api.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=184abd8cc39ccb3cb938fa28067c1885");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            setForecastData("http://api.openweathermap.org/data/2.5/forecast?lat=35&lon=139&appid=184abd8cc39ccb3cb938fa28067c1885");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //Log.d("JSONData>>",HttpClientWeather.getJSONWeatherData(23, 23));

}
