package com.github.gfranks.dynamiccard.sample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.gfranks.dynamiccard.adapter.DynamicCardAdapter;
import com.github.gfranks.dynamiccard.adapter.holder.DynamicCardViewHolder;

import java.util.List;

public class RecyclerViewAdapter extends DynamicCardAdapter<DynamicItem, DynamicItemViewHolder> {

    public RecyclerViewAdapter(RecyclerView recyclerView, List<DynamicItem> items) {
        super(recyclerView, items);
    }

    @Override
    public boolean supportsAddingDynamicCards() {
        return false;
    }

    @Override
    public DynamicItemViewHolder onCreateDynamicCardViewHolder(DynamicCardViewHolder dynamicCardViewHolder, ViewGroup parent, int viewType) {
        return new DynamicItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_dynamic_recycler_view_item, parent, false));
    }

    @Override
    public void onBindDynamicCardViewHolder(DynamicCardViewHolder dynamicCardViewHolder, DynamicItemViewHolder holder, int position) {
        ((TextView) holder.itemView.findViewById(R.id.dynamic_item_text)).setText(getItem(position).getText());
    }
}
