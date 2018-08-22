package data;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClientWeather {


    public static final String API_URL_Forcast5 = "api.openweathermap.org/data/2.5/forecast?lat=";
    public static final String API_URL_Weather = "api.openweathermap.org/data/2.5/weather?lat=";

    public String getJSONWeatherData(int lat, int lon) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;

        try{
            connection =(HttpURLConnection)(new URL(API_URL_Weather+lat+"&lon="+lon)).openConnection();
        }
    }
}
