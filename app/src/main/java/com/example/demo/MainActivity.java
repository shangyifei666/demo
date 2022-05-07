package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.demo.UI.FlowLayoutActivity;
import com.example.demo.UI.Httpdemo;
import com.example.demo.UI.IpcActivity;
import com.example.demo.UI.Kotlin.KotlinActivity;
import com.example.demo.UI.StepsActivity;
import com.example.demo.UI.TimingActivity;
import com.example.demo.UI.ViewHolderActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnOkhttp;
    private Button btnFlowLayout;
    private Button btnRecyclerHolder;
    private Button btnTiming;
    private Button btnSteps;
    private Button btnIpc;
    private Button btnKotlin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        btnOkhttp = findViewById(R.id.btn_okhttp);
        btnOkhttp.setOnClickListener(this);
        btnFlowLayout = findViewById(R.id.btn_flowlayout);
        btnFlowLayout.setOnClickListener(this);
        btnRecyclerHolder = findViewById(R.id.btn_viewholder);
        btnRecyclerHolder.setOnClickListener(this);
        btnTiming = findViewById(R.id.btn_timing);
        btnTiming.setOnClickListener(this);
        btnSteps = findViewById(R.id.btn_steps);
        btnSteps.setOnClickListener(this);
        btnIpc = findViewById(R.id.btn_ipc);
        btnIpc.setOnClickListener(this);
        btnKotlin = findViewById(R.id.btn_kotlin);
        btnKotlin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_okhttp:
                Intent okhttpIntent = new Intent(MainActivity.this, Httpdemo.class);
                startActivity(okhttpIntent);
                break;

            case R.id.btn_flowlayout:
                Intent flowIntent = new Intent(MainActivity.this, FlowLayoutActivity.class);
                startActivity(flowIntent);
                break;

            case R.id.btn_viewholder:
                Intent viewHolderIntent = new Intent(MainActivity.this, ViewHolderActivity.class);
                startActivity(viewHolderIntent);
                break;

            case R.id.btn_timing:
                Intent timingIntent = new Intent(MainActivity.this, TimingActivity.class);
                startActivity(timingIntent);
                break;

            case R.id.btn_steps:
                Intent stepsIntent = new Intent(MainActivity.this, StepsActivity.class);
                startActivity(stepsIntent);
                break;

            case R.id.btn_ipc:
                Intent ipcIntent = new Intent(MainActivity.this, IpcActivity.class);
                startActivity(ipcIntent);

            case R.id.btn_kotlin:
                Intent ktcIntent = new Intent(MainActivity.this, KotlinActivity.class);
                startActivity(ktcIntent);
        }
    }
}
