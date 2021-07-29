package com.sample.bar.adapter;

import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 作者： mr.Wang
 * RecyclerView.ViewHolder
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> mCacheViews;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        mCacheViews = new SparseArray<>();
    }


    public final ImageView getImageView(int id) {
        return getView(id);
    }

    public final TextView getTextView(int id) {
        return getView(id);
    }

    public final EditText getEditText(int id) {
        return getView(id);
    }

    public final Button getButton(int id) {
        return getView(id);
    }

    public final ImageButton getImageButton(int id) {
        return getView(id);
    }

    public final CheckBox getCheckBox(int id) {
        return getView(id);
    }

    public final ProgressBar getProgressBar(int id) {
        return getView(id);
    }

    public final LinearLayout getLinearLayout(int id) {
        return getView(id);
    }

    public final RelativeLayout getRelativeLayout(int id) {
        return getView(id);
    }

    public final FrameLayout getFrameLayout(int id) {
        return getView(id);
    }

    public final Switch getSwitch(int id) {
        return getView(id);
    }

    public final ToggleButton getToggleButton(int id) {
        return getView(id);
    }

    public final <T extends View> T getView(int resId) {
        View view = mCacheViews.get(resId);
        if (view == null) {
            view = itemView.findViewById(resId);
            mCacheViews.put(resId, view);
        }
        return (T) view;
    }
}
