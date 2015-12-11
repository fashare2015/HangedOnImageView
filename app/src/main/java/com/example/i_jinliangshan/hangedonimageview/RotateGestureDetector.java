package com.example.i_jinliangshan.hangedonimageview;

import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by jinliangshan on 2015/12/10.
 */
public class RotateGestureDetector {
    private OnRotateListener mListener;
    private float preDegree, curDegree;
    private float focusX = 0, focusY = 0;

    public RotateGestureDetector(OnRotateListener l, float x, float y) {
        mListener = l;
        focusX = x;
        focusY = y;
    }

    public void onTouchEvent(MotionEvent ev){
        Log.d("Rotate: onTouchEvent:", ev.getAction() + "");

        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("ACTION_DOWN", "ACTION_DOWN");
                preDegree = getDegree(ev);
                break;

            case MotionEvent.ACTION_UP:
                Log.d("ACTION_UP", "ACTION_UP");
                //preDegree = getDegree(ev);
                break;

            case MotionEvent.ACTION_MOVE:
                Log.d("ACTION_MOVE", "ACTION_MOVE");
                curDegree = getDegree(ev);
                if (mListener != null)
                    mListener.onRotate(preDegree - curDegree, focusX, focusY);
                preDegree = curDegree;      //!!!
                break;

            default:
                break;
        }

    }

    private float getDegree(MotionEvent ev) {   //��ʱ�룬�Ƕ�Ϊ��
        float dx, dy, ans;
        dx = ev.getX(0) - focusX;
        dy = -(ev.getY(0) - focusY);    //y���෴��ȡ��
        ans = (float)Math.toDegrees( Math.atan( dy/dx ) );
        return dx>0? ans: ans + 180;    //ͬ����б�ʣ��������180�ȣ���������Ļ���dx����0������180��ͻ�䡣
    }
}

interface OnRotateListener {
    void onRotate(float degrees, float focusX, float focusY);
}
