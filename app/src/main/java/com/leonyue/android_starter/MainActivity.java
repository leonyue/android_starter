package com.leonyue.android_starter;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

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

    static {
        System.loadLibrary("jnidemo");
    }

    public static native String getStringFromJni();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("DEBUG",getStringFromJni());

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


        //属性动画
        //ValueAnimator
        int curColor = this.getWindow().getStatusBarColor();
        int color = Color.RED;
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(),curColor,color);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                MainActivity.this.getWindow().setStatusBarColor((Integer) animation.getAnimatedValue());
            }
        });
        colorAnimation.setDuration(3000).setStartDelay(1000);
        colorAnimation.start();

        //ObjectAnimator 是ValueAnimator的子类 一般不需要注册UpdateListener,但需要提供属性的setter方法，如下的progress
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("DEBUG", String.valueOf(progressBar.getMax()));
                //代码
                int progress = progressBar.getProgress() == 100 ? 0 : 100;
                ObjectAnimator objectAnimator = ObjectAnimator.ofInt(progressBar,"progress",progress);
                objectAnimator.setDuration(2000);
                objectAnimator.start();
                //xml 属性动画放在animator文件夹
                AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(MainActivity.this,R.animator.scale);
                animatorSet.setTarget(progressBar);
                animatorSet.start();

                animatorSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Intent intent = new Intent(MainActivity.this,TransitionActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }
        });


        Button gotoBaiduMap = (Button) findViewById(R.id.gotoBaiduMap);
        Log.d("DEBUG","goto map is"+gotoBaiduMap.toString());
        gotoBaiduMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,BaiduMapActivity.class));
            }
        });

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
