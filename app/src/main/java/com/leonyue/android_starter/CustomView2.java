package com.leonyue.android_starter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.util.MeasureUnit;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dj.yue on 2017/9/30.
 * This is A Project for personal Studying.
 */

public class CustomView2 extends View implements Runnable{
    private Paint mPaint;
    private int raduis;
    private int radius2 = 100;

    public CustomView2(Context context) {
        super(context);
    }

    public CustomView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.LTGRAY);
        mPaint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.LTGRAY);
        canvas.drawCircle(this.getWidth() / 2,this.getHeight() / 2,raduis,mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawCircle(this.getWidth() / 2 + 50, this.getHeight() / 2 + 50, radius2, mPaint);
    }

    public synchronized void setRaduis(int raduis) {
        this.raduis = raduis;

        ///< redraw
        invalidate();
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (radius2 <= 200) {
                    radius2 += 10;
//                    invalidate();
                    postInvalidate();
                } else {
                    radius2 = 0;
                }
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
