package com.example.i_jinliangshan.hangedonimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by jinliangshan on 2015/12/10.
 */
public class HangedOnImageView extends ImageView {
    private float mDegree;
    private Matrix mMatrix = null;
    private RotateGestureDetector mRotateGestureDetector;

    private Bitmap originBmp;

    public HangedOnImageView(Context context){ this(context, null); }

    public HangedOnImageView(Context context, AttributeSet attr){
        super(context, attr);
        init();
    }

    private void init() {
        reset();
        //mMatrix = new Matrix(getImageMatrix());     //!!!

        DisplayMetrics dm =getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;

        float drawableWidth, drawableHeight;
        //drawableWidth = getDrawableWidth(getDrawable());
        //drawableHeight = getDrawableHeight(getDrawable());
        mRotateGestureDetector = new RotateGestureDetector(new OnRotateListener() {
            @Override
            public void onRotate(float degrees, float focusX, float focusY) {
                Log.d("onRotate", mDegree + " " + focusX + " " + focusY);
                mDegree += degrees;
                mDegree = mDegree % 360;

                if(mMatrix == null)
                    mMatrix = new Matrix(getImageMatrix());
                mMatrix.set(getImageMatrix());
                mMatrix.postRotate(mDegree, focusX, focusY);
                //mMatrix.postRotate(30, focusX, focusY);
                setImageMatrix(mMatrix);        //ִ�б任

                //transferedBmp = Bitmap.createBitmap(originBmp, 0, 0, originBmp.getWidth(), originBmp.getHeight(), mMatrix, true);
                //setImageBitmap(transferedBmp);
            }
        }, w_screen/2, h_screen/2);
    }

    public void reset(){
        originBmp = ((BitmapDrawable)getDrawable()).getBitmap();
        mDegree = 0;
    }

    private int getDrawableWidth(Drawable d) {
        int width = d.getIntrinsicWidth();
        if (width <= 0) width = d.getMinimumWidth();
        if (width <= 0) width = d.getBounds().width();
        return width;
    }

    private int getDrawableHeight(Drawable d) {
        int height = d.getIntrinsicHeight();
        if (height <= 0) height = d.getMinimumHeight();
        if (height <= 0) height = d.getBounds().height();
        return height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("onDraw", (mMatrix == null) + "");
        if(mMatrix != null)
            canvas.drawBitmap(originBmp, mMatrix, null);
        else
            super.onDraw(canvas);
    }

    /*
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d("Hanged: ", "dispatchTouchEvent:");
        mRotateGestureDetector.onTouchEvent(event);

        return true;
        //return super.dispatchTouchEvent(event);
    }
    */

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("Hanged: onTouchEvent:", "");
        mRotateGestureDetector.onTouchEvent(event);

        return true;
        //return super.onTouchEvent(event);
    }
}
