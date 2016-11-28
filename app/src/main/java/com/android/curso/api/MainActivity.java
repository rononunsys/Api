package com.android.curso.api;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import cz.msebera.android.httpclient.Header;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    ProgressDialog pDialogUpdate = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button= (Button) findViewById(R.id.button);
        Button buttonLib= (Button) findViewById(R.id.button_lib);
        textView = (TextView) findViewById(R.id.textview);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task task = new Task(MainActivity.this);
                task.execute();
            }
        });

        buttonLib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestParams params = new RequestParams();
                params.put("callback","JSON_CALLBACK");

                Async.get("http://ergast.com/api/f1/2013/driverStandings.json",params,false,MainActivity.this, new AsyncHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        pDialogUpdate = new ProgressDialog(MainActivity.this);
                        pDialogUpdate.setMessage("Conectando...");
                        pDialogUpdate.show();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        if(pDialogUpdate.isShowing()){
                            pDialogUpdate.dismiss();
                        }
                        String response = new String(responseBody);
                        writeResponse(response);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        if(pDialogUpdate.isShowing()){
                            pDialogUpdate.dismiss();
                        }
                    }
                });
            }
        });
    }

    public void writeResponse(String response){
        textView.setText(response);
    }
}
