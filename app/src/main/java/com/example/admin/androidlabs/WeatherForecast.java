package com.example.admin.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import org.xml.sax.Parser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.TextView;

public class WeatherForecast extends Activity {
    protected static final String ACTIVITY_NAME = "WeatherForecast";
    private TextView min_temperature;
    private TextView max_temperature;
    private TextView cur_temperature;
    private ProgressBar loadingimage;
    private ImageView weather_map;
    private static final String URLString = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric.";
    static Bitmap weather_icon = null;
    private XmlPullParser parser;

    double temperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        loadingimage = (ProgressBar) findViewById(R.id.progressBar);
        loadingimage.setVisibility(View.VISIBLE);
        min_temperature =(TextView) findViewById(R.id.min_temperature);
        max_temperature = (TextView) findViewById(R.id.max_temperature);
        cur_temperature = (TextView) findViewById(R.id.temperature);
        weather_map = (ImageView)findViewById(R.id.imageView);
        new ForecastQuery().execute(URLString);
    }
    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        String min_tem ;
        String max_tem ;
        String cur_tem ;
        Bitmap weather;
        String iconName ;
        String bitmapURL;

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection conn = null;
            try {
                URL url = new URL(URLString );
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(conn.getInputStream(), null);
                parser.nextTag();

                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    String tag = parser.getName();
                    if (tag.equals("temperature")) {
                        cur_tem = parser.getAttributeValue(null, "value" );
                        this.publishProgress(25);
                        min_tem = parser.getAttributeValue(null, "min" );
                        this.publishProgress(50);
                        max_tem= parser.getAttributeValue(null, "max" );
                        this.publishProgress(75);}
                    else if (tag.equals("weather")) {
                        iconName = parser.getAttributeValue(null, "icon");
                    }
                }

             String bitmapName = iconName + ".png";
                if (fileExistance(bitmapName)) {
                    weather = readImage(bitmapName);
                   Log.i(ACTIVITY_NAME,"this file has already been downloaded in the internal disk");
                } else {
                    bitmapURL = "http://openweathermap.org/img/w/" + bitmapName;
                    weather = getImage(new URL(bitmapURL), bitmapName);
                   Log.i(ACTIVITY_NAME,"this file needs to be downloaded");
                }
                this.publishProgress(100);
                return "success";

            } catch (MalformedURLException e) {
                return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
           }

        public void setTemperature(double tem ){
            temperature = tem - 273.15;
        }
        public double getTemperature(){
            return temperature;
        }

        protected void onProgressUpdate(Integer... value) {
            loadingimage.setProgress(value[0]);
            loadingimage.setVisibility(View.VISIBLE);
        }

        protected void onPostExecute(String s) {
            setTemperature(Double.parseDouble(cur_tem));
            double cur_tem_double = getTemperature();
            cur_tem = String.format("%.2f",cur_tem_double);
            cur_temperature.setText("Current temperature: " + cur_tem  +" celsius");
            setTemperature(Double.parseDouble(min_tem));
            double min_tem_double = getTemperature();
            min_tem = String.format("%.2f",min_tem_double);
            min_temperature.setText("Min temperature: " + min_tem +" celsius");
            setTemperature(Double.parseDouble(max_tem));
            double max_tem_double = getTemperature();
            max_tem = String.format("%.2f",max_tem_double);
            max_temperature.setText("Max temperature: " + max_tem +" celsius");
            weather_map.setImageBitmap(weather);
            loadingimage.setVisibility(View.INVISIBLE);
        }

        public Bitmap getImage(URL url ,String fname) {
            HttpURLConnection connection = null;
            fname = iconName + ".png" ;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    Bitmap weather_icon= BitmapFactory.decodeStream(connection.getInputStream());
                    FileOutputStream outputStream = openFileOutput(fname, Context.MODE_PRIVATE);
                    weather_icon.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return weather_icon;
        }
        public boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }
        public Bitmap readImage(String imagefile) {
            Bitmap bm = null;
            FileInputStream fis = null;
            try {
                fis = openFileInput(imagefile);
                bm = BitmapFactory.decodeStream(fis);
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bm;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }
    public void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

    }
