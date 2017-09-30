package com.leonyue.android_starter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private CustomView2 mCustomView;
    private int radius;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mCustomView.setRaduis(radius);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mCustomView = (CustomView2) findViewById(R.id.cv);


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (radius <= 200) {
                            radius += 10;
                            mHandler.obtainMessage().sendToTarget();
                        } else {
                            radius = 0;
                        }

                        Thread.sleep(40);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(mCustomView).start();

//        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.content_main_constraintLayout);
//        LayoutInflater layoutInflater = LayoutInflater.from(this);
//        layoutInflater.inflate(R.layout.sample_custom_view,layout);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
