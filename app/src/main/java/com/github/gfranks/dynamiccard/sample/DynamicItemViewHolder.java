package com.github.gfranks.dynamiccard.sample;

import android.view.View;
import android.widget.TextView;

import com.github.gfranks.dynamiccard.adapter.holder.DynamicCardContentViewHolder;

public class DynamicItemViewHolder extends DynamicCardContentViewHolder {

    public DynamicItemViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(DynamicItem item, final RecyclerViewAdapter.OnItemClickListener onItemClickListener) {
        ((TextView) itemView.findViewById(R.id.dynamic_item_text)).setText(item.getText());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(getParentViewHolder().getAdapterPosition());
                }
            }
        });
    }
}
