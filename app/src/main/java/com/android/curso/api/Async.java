package com.android.curso.api;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.entity.ByteArrayEntity;

/**
 * Created by ana.riquelme on 22/11/2016.
 */

public class Async {
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static final int TIMEOUT = 60000;

    public static void get(String url, RequestParams params, boolean requiredAuth, Context ctx, AsyncHttpResponseHandler responseHandler) {
        client.setTimeout(60000);
        client.get(url, params, responseHandler);
    }


    public static void post(Context context, String url, JSONObject jsonParams, AsyncHttpResponseHandler responseHandler) {
        client.setTimeout(TIMEOUT);
        try {
            ByteArrayEntity entity = new ByteArrayEntity(jsonParams.toString().getBytes("UTF-8"));
            client.post(context, url, entity, "application/json", responseHandler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
