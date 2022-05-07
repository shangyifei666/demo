package com.example.demo.View;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {

    private int mHorizontalSpacing = 10;
    private int mVerticalSpacing = 5;

    private List<List<View>> allLines; //记录所有行
    private List<Integer> lineHeights = new ArrayList<>(); //记录所有行高

    public FlowLayout(Context context) {
        super(context);
    }

    //反射
    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initMeasureParam(){
        allLines = new ArrayList<>();
        lineHeights = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int lineWidthUsed = 0; //记录这一行已经使用多宽size
        int lineHeight = 0; //一行的行高
        int parentNeededWidth = 0; //measure过程中，子View要求父ViewGroup的宽
        int parentNeededHeight = 0; //measure过程中，子View要求父ViewGroup的高

        initMeasureParam();

        List<View> lineViews = new ArrayList<>(); //保存一行中的所有View

        int selfwidth = MeasureSpec.getSize(widthMeasureSpec);
        int selfHeight = MeasureSpec.getSize(heightMeasureSpec);

            //度量孩子
        int childCount = getChildCount();
        for (int i=0; i<childCount; i++){
            View childView = getChildAt(i);
            LayoutParams childLP = childView.getLayoutParams();

            int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec,getPaddingLeft()+getPaddingRight(),childLP.width);
            int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec,getPaddingTop()+getPaddingBottom(),childLP.height);

            childView.measure(childWidthMeasureSpec,childHeightMeasureSpec);

            //获取子View的宽高
            int childMesauredWidth = childView.getMeasuredWidth();
            int childMesauredHeight = childView.getMeasuredHeight();

            //判断是否需要换行
            if (childMesauredWidth+lineWidthUsed+mHorizontalSpacing > selfwidth){
                allLines.add(lineViews);
//                Log.d("行数：", String.valueOf(allLines.size()));
                lineHeights.add(lineHeight);

                //换行后，判断当前行需要的宽和高
                parentNeededHeight = parentNeededHeight + lineHeight + mVerticalSpacing;
                parentNeededWidth = Math.max(parentNeededWidth, lineWidthUsed + mHorizontalSpacing);

                lineViews = new ArrayList<>();
                lineWidthUsed = 0;
                lineHeight = 0;
            }

            //View属于每行layout，要记录每行的View
            lineViews.add(childView);
            //每行都有自己的宽高
            lineWidthUsed = lineWidthUsed + childMesauredWidth + mHorizontalSpacing;
            lineHeight = Math.max(lineHeight,childMesauredHeight);
        }
        allLines.add(lineViews);
        lineHeights.add(lineHeight);
        parentNeededHeight = parentNeededHeight + lineHeight + mVerticalSpacing;
        parentNeededWidth = Math.max(parentNeededWidth, lineWidthUsed + mHorizontalSpacing);

        //根据子View的度量结果，重新度量自己ViewGroup
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int realWidth = (widthMode == MeasureSpec.EXACTLY) ? selfwidth : parentNeededWidth;
        int realHeight = (heightMode == MeasureSpec.EXACTLY) ? selfHeight : parentNeededHeight;

        //度量自己
        setMeasuredDimension(realWidth,realHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int lineCount = allLines.size();
        Log.d("lineCount", String.valueOf(lineCount));

        int curL = getPaddingLeft();
        int curT = getPaddingTop();
        for (int i=0; i<lineCount; i++){
            List<View> lineViews = allLines.get(i);
            int lineHeight = lineHeights.get(i);
//            Log.d("lineViews"+i, String.valueOf(lineViews.size()));
//            Log.d("lineHeight", String.valueOf(lineHeight));

            for (int j=0; j<lineViews.size(); j++){
                View view = lineViews.get(j);
                int left = curL;
                int top = curT;
                int right = left + view.getMeasuredWidth();
                int bottom = top + view.getMeasuredHeight();

                view.layout(left,top,right,bottom);
                curL = right + mHorizontalSpacing;
            }
            curL = getPaddingLeft();
            curT = curT + lineHeight + mVerticalSpacing;
        }
    }
}
