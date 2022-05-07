package com.example.demo.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class TimingView extends View {

    private TimingCallback listenner;
    private int num;
    private int maxNum;
    private boolean autoTiming;

    public TimingView(Context context) {
        super(context);
    }

    public TimingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TimingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(int beginNum, int maxNum, boolean autoTiming, TimingCallback listenner){
        this.num = beginNum;
        this.maxNum = maxNum;
        this.autoTiming = autoTiming;
        this.listenner = listenner;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
//        paint.setColor(Integer.parseInt("#CC8A38"));
        RectF rectF = new RectF(100,100,100,100);
        canvas.drawRoundRect(rectF,0, 0, paint);
    }
}
