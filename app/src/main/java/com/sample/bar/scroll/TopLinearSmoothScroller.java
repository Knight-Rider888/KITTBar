package com.sample.bar.scroll;

import android.content.Context;

import androidx.recyclerview.widget.LinearSmoothScroller;

public class TopLinearSmoothScroller extends LinearSmoothScroller {

    public TopLinearSmoothScroller(Context context) {
        super(context);
    }

    @Override
    protected int getVerticalSnapPreference() {
        return SNAP_TO_START;
    }
}
