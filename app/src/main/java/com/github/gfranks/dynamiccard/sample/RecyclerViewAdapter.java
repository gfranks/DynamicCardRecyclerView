package com.github.gfranks.dynamiccard.sample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.gfranks.dynamiccard.adapter.DynamicCardAdapter;
import com.github.gfranks.dynamiccard.adapter.holder.DynamicCardViewHolder;

import java.util.List;

public class RecyclerViewAdapter extends DynamicCardAdapter<DynamicItem, DynamicItemViewHolder> {

    private OnItemClickListener mOnItemClickListener;

    public RecyclerViewAdapter(RecyclerView recyclerView, List<DynamicItem> items, OnItemClickListener onItemClickListener) {
        super(recyclerView, items);
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public boolean supportsAddingDynamicCards() {
        return true;
    }

    @Override
    public DynamicItemViewHolder onCreateDynamicCardViewHolder(DynamicCardViewHolder dynamicCardViewHolder, ViewGroup parent, int viewType) {
        return new DynamicItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_dynamic_recycler_view_item, parent, false));
    }

    @Override
    public void onBindDynamicCardViewHolder(DynamicCardViewHolder dynamicCardViewHolder, DynamicItemViewHolder holder, int position) {
        holder.bind(getItem(position), mOnItemClickListener);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
