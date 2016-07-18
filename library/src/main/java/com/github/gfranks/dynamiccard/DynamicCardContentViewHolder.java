package com.github.gfranks.dynamiccard;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class DynamicCardContentViewHolder extends RecyclerView.ViewHolder {

    public DynamicCardContentViewHolder(View itemView) {
        super(itemView);
    }

    public DynamicCardViewHolder getParentViewHolder() {
        return (DynamicCardViewHolder) itemView.getTag();
    }
}
