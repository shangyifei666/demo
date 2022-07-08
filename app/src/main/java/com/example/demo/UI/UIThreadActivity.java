package com.example.demo.UI;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 在子线程实现UI倒计时
 */
public class UIThreadActivity extends AppCompatActivity {

    @BindView(R.id.tv_ui_time)
    TextView tvTime;
    @BindView(R.id.btn_ui_start)
    Button btnStart;
    @BindView(R.id.btn_ui_stop)
    Button btnStop;

    private Unbinder unbinder;

    private final String THREAD_ID = "Child_Thread";
    private final Activity  context = UIThreadActivity.this;

    private HandlerThread thread;
    private Handler handler;

    public TextView textView;
    public int time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uithread);
        unbinder = ButterKnife.bind(this);
        Log.d("CreatThread : ", String.valueOf(Thread.currentThread()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
        btnStart.setOnClickListener(l -> {
            startTime();
        });
        btnStop.setOnClickListener(l -> {
            stopTime();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (thread != null){
            thread.quit();
        }

    }

    private void startTime(){
        handler.post(runnable);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (textView == null){
                textView = getTextView();
            }
            textView.setText(String.valueOf(time));
            Log.d("textViewThread : ", String.valueOf(Thread.currentThread()));
            time++;
            handler.postDelayed(this, 1000);
        }
    };

    private void stopTime(){
        handler.removeCallbacks(runnable);
    }

    private void init(){
        thread = new HandlerThread(THREAD_ID);
        thread.start();
        handler = new Handler(thread.getLooper());
    }

    private TextView getTextView(){
        TextView tv = new TextView(context);
        tv.setBackgroundColor(Color.GRAY);  //背景灰色
        tv.setGravity(Gravity.CENTER);  //居中展示
        tv.setTextSize(30);

        WindowManager manager = context.getWindowManager();
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0,0,
                WindowManager.LayoutParams.FIRST_SUB_WINDOW,
                WindowManager.LayoutParams.TYPE_TOAST,
                PixelFormat.TRANSPARENT);
        manager.addView(tv, params);
        return tv;
    }

}