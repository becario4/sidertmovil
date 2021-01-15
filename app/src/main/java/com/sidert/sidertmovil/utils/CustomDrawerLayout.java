package com.sidert.sidertmovil.utils;

import android.content.Context;
//import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.drawerlayout.widget.DrawerLayout;

public class CustomDrawerLayout extends DrawerLayout {

    private boolean isLockedMode = false;

    public CustomDrawerLayout(Context context) {
        super(context);
    }

    public CustomDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return !isLockedMode && super.onInterceptTouchEvent(ev);
    }

    public void setLocked(boolean lock) {
        isLockedMode = lock;
    }

    public boolean isLocked() {
        return isLockedMode;
    }
}