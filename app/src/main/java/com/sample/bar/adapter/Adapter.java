package com.sample.bar.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.sample.bar.R;
import com.sample.bar.data.ContactBean;

public class Adapter extends BaseListAdapter<ContactBean> {

    /**
     * 构造方法，默认支持加载状态的样式
     *
     * @param context The context to use.  Usually your {@link Activity} object.
     */
    public Adapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewsType(int position) {
        return 0;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolders(ViewGroup parent, int viewType) {
        View view = getInflater().inflate(R.layout.rv_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolders(RecyclerViewHolder holder, ContactBean contactBean, int position) {

        holder.getTextView(R.id.session).setText(String.valueOf(contactBean.getPhonebook_label()));
        holder.getTextView(R.id.session).setVisibility(letterCompareSection(position) ? View.GONE : View.VISIBLE);
        holder.getTextView(R.id.tv).setText(String.valueOf(contactBean.getDisplay_name()));
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }

    private boolean letterCompareSection(int position) {

        if (position == 0) {
            return false;
        }
        String letter1 = getData(position).getPhonebook_label();
        String letter2 = getData(position - 1).getPhonebook_label();
        return letter1.equals(letter2);
    }

    private static class ItemHolder extends RecyclerViewHolder {

        public ItemHolder(View itemView) {
            super(itemView);
        }
    }
}
