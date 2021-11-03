package com.sample.bar.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.sample.bar.R;

import java.util.ArrayList;
import java.util.List;

import knight.rider.kitt.adapter.SimpleListAdapter;
import knight.rider.kitt.adapter.attr.LoadState;
import knight.rider.kitt.adapter.holder.RecyclerViewHolder;

public class NameAdapter extends SimpleListAdapter<String> {

    public NameAdapter(Context context) {
        super(context, R.layout.item_name, false);
    }

    @Override
    public void onBindViewHolders(RecyclerViewHolder holder, int viewType, String s, int position) {
        holder.getTextView(R.id.tv).setText(s);
    }

    public void searchName(List<String> all, String n) {

        if (TextUtils.isEmpty(n)) {
            clearAll();
        } else {
            List<String> newArr = new ArrayList<>();

            for (int i = 0; i < all.size(); i++) {
                if (all.get(i).contains(n)) {
                    newArr.add(all.get(i));
                }
            }

            clearAll();
            addData(newArr);
        }

        if (getAttachDataSize() == 0)
            setLoadState(LoadState.LOAD_NO_DATA);
        else
            setLoadState(LoadState.LOAD_COMPLETE);
    }
}
