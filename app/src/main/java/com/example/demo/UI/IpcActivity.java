package com.example.demo.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.demo.AIDL.Book;
import com.example.demo.AIDL.BookManagerService;
import com.example.demo.IBookManager;
import com.example.demo.IOnNewBookArrivedListener;
import com.example.demo.R;

import java.util.List;

public class IpcActivity extends AppCompatActivity {
    private static final String TAG = "IpcActivity";
    private static final int MESSAGE_NEW_BOOK_ARRIVED = 1;
    private Boolean isBind = false;


    private Button btn;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private IBookManager bookManager;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case MESSAGE_NEW_BOOK_ARRIVED:
                    Log.d(TAG, "receive new book : " + msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc);

        initView();
    }

    private void initView(){
        /**
         * 开启服务
         */
        btn = findViewById(R.id.btn_ipc);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService();
            }
        });

        /**
         * 关闭服务
         */
        btn2 = findViewById(R.id.btn_ipc2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService();
            }
        });

        /**
         * 开启监听
         */
        btn3 = findViewById(R.id.btn_ipc3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerListener();
            }
        });

        /**
         * 关闭监听
         */
        btn4 = findViewById(R.id.btn_ipc4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unregisterListener();
            }
        });

        /**
         * 模拟耗时操作
         */
        btn5 = findViewById(R.id.btn_ipc5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBookList();
            }
        });
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bookManager = IBookManager.Stub.asInterface(service);
            try {
                List<Book> list = bookManager.getBookList();
                Log.i(TAG,"query book list, list type:" + list.getClass().getCanonicalName());
                Log.i(TAG, "query book list:" + list.toString());
                Book newBook = new Book(3,"开发艺术探索");
                bookManager.addBook(newBook);
                Log.i(TAG,"add book:" + newBook);
                List<Book> newList = bookManager.getBookList();
                Log.i(TAG, "query book list:" + newList.toString());

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bookManager = null;
            Log.e(TAG, "binder died.");
        }
    };

    private IOnNewBookArrivedListener mOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            handler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED, newBook).sendToTarget();
        }
    };

    private void startService(){
        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        isBind = true;
    }

    private void stopService(){
        if (isBind){
            if (bookManager != null && bookManager.asBinder().isBinderAlive()){
                Log.i(TAG, "unregister listener:" + mOnNewBookArrivedListener);
                try {
                    bookManager.unregisterListener(mOnNewBookArrivedListener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            unbindService(mConnection);
            isBind = false;
        } else {
            Log.e(TAG, "is not binding.");
        }
    }

    private void registerListener(){
        if (bookManager != null){
            try {
                bookManager.registerListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void unregisterListener(){
        if (bookManager != null && bookManager.asBinder().isBinderAlive()){
            Log.i(TAG, "unregister listener:" + mOnNewBookArrivedListener);
            try {
                bookManager.unregisterListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void getBookList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (bookManager != null){
                    try {
                        bookManager.getBookList();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        stopService();
        super.onDestroy();
    }
}
