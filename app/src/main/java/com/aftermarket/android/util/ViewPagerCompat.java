package com.aftermarket.android.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class ViewPagerCompat extends ViewPager {
    public ViewPagerCompat(Context context) {
        super(context);
    }
    public ViewPagerCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if(v.getClass().getName().equals("com.com.tencent.tencentmap.mapsdk.maps.MapView")) {
            return true;
        }
        return super.canScroll(v, checkV, dx, x, y);

    }
}
