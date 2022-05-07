package com.example.demo.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.demo.R;

import java.io.IOException;

import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Httpdemo extends AppCompatActivity implements View.OnClickListener {

    private Button btnReq;
    private TextView tvRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_httpdemo);

        init();
    }

    private void init(){
        btnReq = findViewById(R.id.btn_request);
        btnReq.setOnClickListener(this);
        tvRes = findViewById(R.id.tv_respond);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_request:
                requestOkhttp();
                break;
        }
    }

    private void requestOkhttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://www.baidu.com")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    showResponds(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showResponds(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvRes.setText(response);
            }
        });
    }
}
