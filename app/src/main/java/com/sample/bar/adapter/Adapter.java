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
        ContactBean data = getData(position);
        return data.getType();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolders(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = getInflater().inflate(R.layout.rv_session, parent, false);
            return new SessionHolder(view);
        } else {
            View view = getInflater().inflate(R.layout.rv_item, parent, false);
            return new ItemHolder(view);
        }
    }

    @Override
    public void onBindViewHolders(RecyclerViewHolder holder, ContactBean contactBean, int position) {

        if (holder instanceof SessionHolder) {
            holder.getTextView(R.id.tv).setText(String.valueOf(contactBean.getInitial()));
        } else if (holder instanceof ItemHolder) {
            holder.getTextView(R.id.tv).setText(contactBean.getName());
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }


    private static class SessionHolder extends RecyclerViewHolder {

        public SessionHolder(View itemView) {
            super(itemView);
        }
    }

    private static class ItemHolder extends RecyclerViewHolder {

        public ItemHolder(View itemView) {
            super(itemView);
        }
    }
}
