package com.android.curso.api;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ana.riquelme on 21/11/2016.
 */

public class Task extends AsyncTask<Void,Void,String> {
    private final MainActivity activity;
    private ProgressDialog dialog;
    public Task(MainActivity activity){
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(activity);
        dialog.setMessage("Conectando...");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected String doInBackground(Void... voids) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String strinJson;
        //Uri uri = Uri.parse("http://api.geonames.org/citiesJSON?north=44.1&south=-9.9&east=-22.4&west=55.2&lang=de&username=demo");
        Uri uri = Uri.parse("http://api.geonames.org/citiesJSON").buildUpon()
                .appendQueryParameter("north","44.1")
                .appendQueryParameter("south","9.9")
                .appendQueryParameter("east","22.4")
                .appendQueryParameter("west","55.2")
                .appendQueryParameter("lang","de")
                .appendQueryParameter("username","demo")
                .build();

        try {
            URL url = new URL(uri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if(inputStream == null){
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = reader.readLine()) != null){
                buffer.append(line + "\n");
            }

            if(buffer.length() == 0){
                return null;
            }

            strinJson = buffer.toString();
            return strinJson;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        if(s != null){
            activity.writeResponse(s);
        }
        dialog.dismiss();
    }
}
