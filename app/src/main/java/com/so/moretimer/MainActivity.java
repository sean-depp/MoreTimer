package com.so.moretimer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Timer mTimer1;
    private Timer mTimer2;
    private TimerTask mTask1;
    private TimerTask mTask2;
    @SuppressLint("StaticFieldLeak")
    private static TextView mTvTime1;
    private TextView mTvTime2;
    @SuppressLint("StaticFieldLeak")
    private static TextView mTvTime3;
    @SuppressLint("StaticFieldLeak")
    private static TextView mTvTime4;
    private TextView mTvTime5;
    private MyThread mThread;
    private MyRunnable mRunnable;

    private static class MyHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;

        private MyHandler(MainActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case 1:
                        mTvTime1.setText(getTime());
                        break;

                    case 2: {
                        mTvTime3.setText(getTime());
                        this.removeMessages(2);
                        Message message = this.obtainMessage(2);
                        this.sendMessageDelayed(message, 1000);
                    }
                    break;

                    case 3:
                        this.removeMessages(2);
                        break;

                    case 4: {
                        mTvTime4.setText(getTime());
                        this.removeMessages(4);
                        Message message = this.obtainMessage(4);
                        this.sendMessage(message);
                    }
                    break;

                    case 5:
                        this.removeMessages(4);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private final MyHandler mHandler = new MyHandler(this);

//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 1:
//                    mTvTime1.setText(getTime());
//                    break;
//
//                case 2: {
//                    mTvTime3.setText(getTime());
//                    mHandler.removeMessages(2);
//                    Message message = mHandler.obtainMessage(2);
//                    mHandler.sendMessageDelayed(message, 1000);
//                }
//                break;
//
//                case 3:
//                    mHandler.removeMessages(2);
//                    break;
//
//                case 4: {
//                    mTvTime4.setText(getTime());
//                    mHandler.removeMessages(4);
//                    Message message = mHandler.obtainMessage(4);
//                    mHandler.sendMessage(message);
//                }
//                break;
//
//                case 5:
//                    mHandler.removeMessages(4);
//                    break;
//                default:
//                    break;
//            }
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    /**
     * 初始化自定义ui
     */
    private void initUI() {
        mTvTime1 = (TextView) findViewById(R.id.tv_time1);
        mTvTime2 = (TextView) findViewById(R.id.tv_time2);
        mTvTime3 = (TextView) findViewById(R.id.tv_time3);
        mTvTime4 = (TextView) findViewById(R.id.tv_time4);
        mTvTime5 = (TextView) findViewById(R.id.tv_time5);

        CardView cvStart1 = (CardView) findViewById(R.id.cv_start1);
        CardView cvStop1 = (CardView) findViewById(R.id.cv_stop1);
        CardView cvStart2 = (CardView) findViewById(R.id.cv_start2);
        CardView cvStop2 = (CardView) findViewById(R.id.cv_stop2);
        CardView cvStart3 = (CardView) findViewById(R.id.cv_start3);
        CardView cvStop3 = (CardView) findViewById(R.id.cv_stop3);
        CardView cvStart4 = (CardView) findViewById(R.id.cv_start4);
        CardView cvStop4 = (CardView) findViewById(R.id.cv_stop4);
        CardView cvStart5 = (CardView) findViewById(R.id.cv_start5);
        CardView cvStop5 = (CardView) findViewById(R.id.cv_stop5);

        cvStart1.setOnClickListener(this);
        cvStop1.setOnClickListener(this);
        cvStart2.setOnClickListener(this);
        cvStop2.setOnClickListener(this);
        cvStart3.setOnClickListener(this);
        cvStop3.setOnClickListener(this);
        cvStart4.setOnClickListener(this);
        cvStop4.setOnClickListener(this);
        cvStart5.setOnClickListener(this);
        cvStop5.setOnClickListener(this);
    }

    /**
     * 获取时间
     *
     * @return 格式化后的时间
     */
    private static String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
        return format.format(new Date());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cv_start1:
                if (mTimer1 == null && mTask1 == null) {
                    mTimer1 = new Timer();
                    mTask1 = new TimerTask() {
                        @Override
                        public void run() {
                            Message message = mHandler.obtainMessage(1);
                            mHandler.sendMessage(message);
                        }
                    };
                    mTimer1.schedule(mTask1, 0, 1000);
                }
                break;

            case R.id.cv_stop1:
                if (mTimer1 != null) {
                    mTimer1.cancel();
                    mTimer1 = null;
                }
                if (mTask1 != null) {
                    mTask1.cancel();
                    mTask1 = null;
                }
                break;

            case R.id.cv_start2:
                if (mTimer2 == null && mTask2 == null) {
                    mTimer2 = new Timer();
                    mTask2 = new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mTvTime2.setText(getTime());
                                }
                            });
                        }
                    };
                    mTimer2.schedule(mTask2, 0, 1000);
                }
                break;

            case R.id.cv_stop2:
                if (mTimer2 != null) {
                    mTimer2.cancel();
                    mTimer2 = null;
                }
                if (mTask2 != null) {
                    mTask2.cancel();
                    mTask2 = null;
                }
                break;

            case R.id.cv_start3: {
                Message message = mHandler.obtainMessage(2);
                mHandler.sendMessage(message);
            }
            break;

            case R.id.cv_stop3: {
                Message message = mHandler.obtainMessage(3);
                mHandler.sendMessage(message);
            }
            break;

            case R.id.cv_start4: {
                startThread();
            }
            break;

            case R.id.cv_stop4: {
                stopThread();
            }
            break;

            case R.id.cv_start5: {
                if (mRunnable == null) {
                    mRunnable = new MyRunnable();
                    mHandler.postDelayed(mRunnable, 0);
                }
            }
            break;

            case R.id.cv_stop5: {
                mHandler.removeCallbacks(mRunnable);
                mRunnable = null;
            }
            break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!mTask1.cancel()) {
            mTask1.cancel();
            mTimer1.cancel();
            mTimer1 = null;
        }
        if (!mTask2.cancel()) {
            mTask2.cancel();
            mTimer2.cancel();
            mTimer2 = null;
        }
    }

    private class MyThread extends Thread {

        private boolean stop = false;

        public void run() {
            while (!stop) {
                try {
                    Message message = mHandler.obtainMessage(4);
                    mHandler.sendMessage(message);
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 启动线程
     */
    private void startThread() {
        if (mThread == null) {
            mThread = new MyThread();
            mThread.start();
        }
    }

    /**
     * 停止线程
     */
    private void stopThread() {
        if (mThread != null) {
            mThread.stop = true;
            mThread = null;
            Message message = mHandler.obtainMessage(5);
            mHandler.sendMessage(message);
        }
    }

    private class MyRunnable implements Runnable {
        @Override
        public void run() {
            mTvTime5.setText(getTime());
            mHandler.postDelayed(this, 1000);
        }
    }
}
