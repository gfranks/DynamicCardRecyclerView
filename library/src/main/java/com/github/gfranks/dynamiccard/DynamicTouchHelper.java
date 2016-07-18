package com.github.gfranks.dynamiccard;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.github.gfranks.dynamiccard.adapter.DynamicCardAdapter;

public class DynamicTouchHelper extends ItemTouchHelper.SimpleCallback {

    private static final int DRAG_FLAGS = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
    private static final int SWIPE_FLAGS = 0;

    private DynamicCardAdapter mAdapter;

    public DynamicTouchHelper(DynamicCardAdapter movieAdapter){
        super(DRAG_FLAGS, SWIPE_FLAGS);
        this.mAdapter = movieAdapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (mAdapter.isEditMode() && (!mAdapter.supportsAddingDynamicCards() || (mAdapter.supportsAddingDynamicCards()
                && viewHolder.getAdapterPosition() != mAdapter.getItemCount() - 1))) {
            mAdapter.moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        }
        return mAdapter.isEditMode();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(mAdapter.isEditMode() && (!mAdapter.supportsAddingDynamicCards()
                || (mAdapter.supportsAddingDynamicCards() && viewHolder.getAdapterPosition() != mAdapter.getItemCount() - 1))
                ? DRAG_FLAGS : 0, SWIPE_FLAGS);
    }
}
