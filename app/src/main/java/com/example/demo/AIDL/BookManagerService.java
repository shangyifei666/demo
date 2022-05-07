package com.example.demo.AIDL;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;

import com.example.demo.IBookManager;
import com.example.demo.IOnNewBookArrivedListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookManagerService extends Service {
    private static final String TAG = "BMS_SERVICE";

    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<Book>();
    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList = new RemoteCallbackList<IOnNewBookArrivedListener>();  //自动管理IBinder回调类

    public BookManagerService() {
    }

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            SystemClock.sleep(5 * 1000);  //模拟耗时操作
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
//            if (!mListenerList.contains(listener)){
//                mListenerList.add(listener);
//            } else {
//                Log.d(TAG, "already exists.");
//            }
//            Log.d(TAG, "registerListener, size:" + mListenerList.size());
            mListenerList.register(listener);
            final int N = mListenerList.beginBroadcast();
            Log.d(TAG, "registerListener, size:" + N);
            mListenerList.finishBroadcast();
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
//            if (mListenerList.contains(listener)){
//                mListenerList.remove(listener);
//                Log.d(TAG, "unregister listener succeed.");
//            } else {
//                Log.d(TAG, "not found, can not unregister.");
//            }
//            Log.d(TAG, "registerListener, size:" + mListenerList.size());
            mListenerList.unregister(listener);
            final int N = mListenerList.beginBroadcast();
            Log.d(TAG, "registerListener, size:" + N);
            mListenerList.finishBroadcast();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1,"Android"));
        mBookList.add(new Book(2,"IOS"));
        new Thread(new ServiceWorker()).start();
    }

    @Override
    public void onDestroy() {
        mIsServiceDestoryed.set(true);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private void onNewBookArrived(Book book)throws RemoteException{
        mBookList.add(book);
//        Log.d(TAG, "onNewBookArrived, notfy listeners:" + mListenerList.size());
//        for (IOnNewBookArrivedListener listener : mListenerList){
//             listener.onNewBookArrived(book);
//        }
        final int N = mListenerList.beginBroadcast();
        for (int i=0; i<N; i++){
            IOnNewBookArrivedListener l = mListenerList.getBroadcastItem(i);
                if (l != null){
                    l.onNewBookArrived(book);
                }
        }
        mListenerList.finishBroadcast();
    }

    private class ServiceWorker implements Runnable{
        @Override
        public void run() {
            while (!mIsServiceDestoryed.get()){
                try {
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId = mBookList.size() + 1;
                Book newBook = new Book(bookId, "new book#" + bookId);
                try {
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
