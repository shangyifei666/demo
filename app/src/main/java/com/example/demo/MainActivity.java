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
import com.example.demo.UI.LeakCanaryTestActivity;
import com.example.demo.UI.StepsActivity;
import com.example.demo.UI.TimingActivity;
import com.example.demo.UI.ViewHolderActivity;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btn_okhttp)
    private Button btnOkhttp;
    @BindView(R.id.btn_flowlayout)
    private Button btnFlowLayout;
    @BindView(R.id.btn_viewholder)
    private Button btnRecyclerHolder;
    @BindView(R.id.btn_timing)
    private Button btnTiming;
    @BindView(R.id.btn_steps)
    private Button btnSteps;
    @BindView(R.id.btn_ipc)
    private Button btnIpc;
    @BindView(R.id.btn_kotlin)
    private Button btnKotlin;
    @BindView(R.id.btn_leakcanary_test)
    private Button btnLeakCanaryTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        btnOkhttp.setOnClickListener(this);
        btnFlowLayout.setOnClickListener(this);
        btnRecyclerHolder.setOnClickListener(this);
        btnTiming.setOnClickListener(this);
        btnSteps.setOnClickListener(this);
        btnIpc.setOnClickListener(this);
        btnKotlin.setOnClickListener(this);
        btnLeakCanaryTest.setOnClickListener(this);
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
                break;

            case R.id.btn_kotlin:
                Intent ktcIntent = new Intent(MainActivity.this, KotlinActivity.class);
                startActivity(ktcIntent);
                break;

            case R.id.btn_leakcanary_test:
                Intent leakCanaryIntent = new Intent(MainActivity.this, LeakCanaryTestActivity.class);
                startActivity(leakCanaryIntent);
                break;
        }
    }
}
