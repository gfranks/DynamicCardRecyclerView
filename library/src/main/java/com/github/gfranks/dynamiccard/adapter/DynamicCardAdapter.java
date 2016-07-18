package com.github.gfranks.dynamiccard.adapter;

import android.support.annotation.MenuRes;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.gfranks.dynamiccard.DynamicCardCallback;
import com.github.gfranks.dynamiccard.DynamicTouchHelper;
import com.github.gfranks.dynamiccard.R;
import com.github.gfranks.dynamiccard.adapter.holder.DynamicCardViewHolder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class DynamicCardAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<DynamicCardViewHolder> {

    private static final int VIEW_TYPE_ADD = 100;

    private List<T> mItems;
    private DynamicCardCallback mCallback;
    private boolean mEditMode;

    public DynamicCardAdapter(RecyclerView recyclerView, T[] items) {
        this(recyclerView, Arrays.asList(items));
    }

    public DynamicCardAdapter(RecyclerView recyclerView, List<T> items) {
        mItems = items;
        new ItemTouchHelper(new DynamicTouchHelper(this)).attachToRecyclerView(recyclerView);
    }

    public boolean supportsAddingDynamicCards() {
        return true;
    }

    public boolean displayPopupMenuOptions() {
        return true;
    }

    public void setDynamicCardCallback(DynamicCardCallback callback) {
        mCallback = callback;
    }

    public boolean isEditMode() {
        return mEditMode;
    }

    public final void edit() {
        mEditMode = true;
        notifyDataSetChanged();
    }

    public final void cancel() {
        mEditMode = false;
        notifyDataSetChanged();
    }

    public final void addItem(T item) {
        mItems.add(item);
        notifyItemInserted(mItems.size() - (isEditMode() ? 1 : 0));
    }

    public final void removeItem(T item) {
        removeItem(mItems.indexOf(item));
    }

    public final void removeItem(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    public final void moveItem(T item, int toPosition) {
        int firstPosition = mItems.indexOf(item);
        Collections.swap(mItems, firstPosition, toPosition);
        notifyItemMoved(firstPosition, toPosition);

        if (mCallback != null) {
            mCallback.onDynamicCardPositionChanged(this, firstPosition, toPosition);
        }
    }

    public final void moveItem(int fromPosition, int toPosition) {
        Collections.swap(mItems, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);

        if (mCallback != null) {
            mCallback.onDynamicCardPositionChanged(this, fromPosition, toPosition);
        }
    }

    public @MenuRes int onCreateDynamicCardPopupMenu(int position) {
        return R.menu.dynamic_card;
    }

    public boolean onDynamicCardPopupMenuItemClick(int position, MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_delete) {
            removeItem(position);
        } else if (itemId == R.id.action_move_to_top) {
            moveItem(position, 0);
        } else if (itemId == R.id.action_move_up) {
            moveItem(position, position - 1);
        } else if (itemId == R.id.action_move_down) {
            moveItem(position, position + 1);
        } else if (itemId == R.id.action_move_to_bottom) {
            moveItem(position, mItems.size() - 1);
        }

        return true;
    }

    @Override
    public final int getItemCount() {
        return mItems.size() + (isEditMode() && supportsAddingDynamicCards() ? 1 : 0);
    }

    public final T getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (isEditMode() && supportsAddingDynamicCards() && position == mItems.size()) {
            return VIEW_TYPE_ADD;
        }
        return super.getItemViewType(position);
    }

    @Override
    public final DynamicCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DynamicCardViewHolder dynamicCardViewHolder = new DynamicCardViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_dynamic_card_recyclerview_item, parent, false), this);
        if (viewType != VIEW_TYPE_ADD) {
            VH holder = onCreateDynamicCardViewHolder(dynamicCardViewHolder, dynamicCardViewHolder.getDynamicCardContentContainer(), viewType);
            dynamicCardViewHolder.getDynamicCardContentContainer().addView(holder.itemView);
            dynamicCardViewHolder.getDynamicCardContentContainer().setTag(holder);
        }
        return dynamicCardViewHolder;
    }

    @Override
    public final void onBindViewHolder(DynamicCardViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_ADD) {
            holder.itemView.findViewById(R.id.dynamic_card_view_add).setVisibility(View.VISIBLE);
            holder.itemView.findViewById(R.id.dynamic_card_view_add).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mCallback != null) {
                        mCallback.onAddNewDynamicCard(DynamicCardAdapter.this);
                    }
                }
            });
        } else {
            holder.itemView.findViewById(R.id.dynamic_card_view_add).setVisibility(View.GONE);
            onBindDynamicCardViewHolder(holder, (VH) holder.getDynamicCardContentContainer().getTag(), position);
            if (displayPopupMenuOptions()) {
                holder.getMenuPopupButton().setVisibility(isEditMode() ? View.VISIBLE : View.GONE);
            } else {
                holder.getMenuPopupButton().setVisibility(View.GONE);
            }
        }
    }

    public abstract VH onCreateDynamicCardViewHolder(DynamicCardViewHolder dynamicCardViewHolder, ViewGroup parent, int viewType);
    public abstract void onBindDynamicCardViewHolder(DynamicCardViewHolder dynamicCardViewHolder, VH holder, int position);
}
