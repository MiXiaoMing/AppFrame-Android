package com.appframe.library.component;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.appframe.library.R;

// 防快速点击
public class AFButton extends Button {

    /*设置默认反应时间间隔*/
    private Integer clickPeriod = 0;


    public AFButton(Context context) {
        this(context, null);
    }

    public AFButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    // 获得自定义的样式属性
    public AFButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AFButton, defStyle, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.AFButton_clickPeriod) {
                clickPeriod = a.getInt(R.styleable.AFButton_clickPeriod, 0);
                setOnTouchListener(onTouchListener);
            }
        }
        a.recycle();
    }

    private Boolean dispatchTouchEve = false;//默认下发事件
    private long lastClickTime = 0;

    private OnTouchListener onTouchListener = new OnTouchListener() {
        @SuppressLint("ClickableViewAccessibility")
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {

                long time = System.currentTimeMillis();
                if (time - lastClickTime < clickPeriod) {
                    dispatchTouchEve = true;//停止下发事件
                } else {
                    dispatchTouchEve = false;//下发事件
                }
                lastClickTime = time;
            }
            return dispatchTouchEve;
        }
    };
}
