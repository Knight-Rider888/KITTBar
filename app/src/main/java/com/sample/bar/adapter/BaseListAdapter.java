package com.sample.bar.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseListAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    // 数据源
    private final List<T> mData;

    // Attach的RecyclerView
    private RecyclerView mRecyclerView;

    // 上下文对象
    private final Context mContext;

    // 布局填充器
    private final LayoutInflater mInflater;


    /**
     * 构造方法，默认支持加载状态的样式
     *
     * @param context The context to use.  Usually your {@link android.app.Activity} object.
     */
    public BaseListAdapter(Context context) {
        mData = new ArrayList<>();
        this.mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    // ******** 获取item的数量 ********
    @Override
    public final int getItemCount() {
        return mData.size();
    }

    // ******** 获取item的type类型 ********
    @Override
    public final int getItemViewType(int position) {

        // 返回数据源对应的真实position
        return getItemViewsType(position);
    }


    // 多布局的ViewType
    public abstract int getItemViewsType(int position);


    // ******** 创建Holder ********
    @NonNull
    @Override
    public final RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        // 用户自定义的Holder
        RecyclerViewHolder viewHolder = onCreateViewHolders(parent, viewType);

        // 如果有需要重写点击事件，可在onBindViewHolders()进行覆盖
        viewHolder.itemView.setOnClickListener(this);
        viewHolder.itemView.setOnLongClickListener(this);
        return viewHolder;


    }

    // 创建多布局的ViewHolder
    public abstract RecyclerViewHolder onCreateViewHolders(ViewGroup parent, int viewType);


    // ******** 绑定显示数据 ********
    @Override
    public final void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {


        // 需要子类去实现 具体操作
        onBindViewHolders(holder, mData.get(position), position);

    }


    // 绑定数据
    public abstract void onBindViewHolders(RecyclerViewHolder holder, T t, int position);


    // 表格布局 头、脚布局占一行 添加加载更多监听
    @CallSuper
    @Override
    public final void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }


    /**
     * 添加数据源
     *
     * @param data collection containing elements to be added to this list
     */
    public final void addData(List<T> data) {

        if (data == null)
            return;

        int start = mData.size();

        mData.addAll(data);
        notifyItemRangeInserted(start, data.size());
    }


    /**
     * 清空数据源
     */
    @SuppressLint("NotifyDataSetChanged")
    public final void clearAll() {
        mData.clear();
        notifyDataSetChanged();
    }

    /**
     * 移除指定位置数据源
     *
     * @param position Position of the item that has now been removed
     */
    public final void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }


    /**
     * 获取数据源的方法,尽量不要使用此方法操作数据
     * 如内部提供的方法不能够满足，再使用此方式获取数据源进行操作
     */
    public final List<T> getAttachData() {
        return mData;
    }


    /**
     * 获取指定位置的实体类
     */
    public final T getData(int position) {
        return mData.get(position);
    }

    /**
     * 获取数据源的长度
     */
    public final int getAttachDataSize() {
        return mData.size();
    }

    /**
     * 获取上下文对象
     */
    public final Context getAttachContext() {
        return mContext;
    }


    /**
     * 获取RecyclerView
     */
    public final RecyclerView getAttachRecyclerView() {
        return mRecyclerView;
    }


    /**
     * 获取布局填充墙
     */
    public final LayoutInflater getInflater() {
        return mInflater;
    }


}
