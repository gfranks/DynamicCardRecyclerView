package com.github.gfranks.dynamiccard;

import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class DynamicCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private DynamicCardAdapter mAdapter;

    public DynamicCardViewHolder(View itemView, DynamicCardAdapter adapter) {
        super(itemView);
        mAdapter = adapter;
        itemView.findViewById(R.id.dynamic_card_view_popup).setOnClickListener(this);
    }

    public CardView getCardView() {
        return (CardView) itemView.findViewById(R.id.dynamic_card_view);
    }

    public ViewGroup getDynamicCardContentContainer() {
        return (ViewGroup) itemView.findViewById(R.id.dynamic_card_view_content);
    }

    public View getMenuPopupButton() {
        return itemView.findViewById(R.id.dynamic_card_view_popup);
    }

    @Override
    public void onClick(View view) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        popup.getMenuInflater().inflate(mAdapter.onCreateDynamicCardPopupMenu(getAdapterPosition()), popup.getMenu());
        popup.setOnMenuItemClickListener(this);

        MenuItem moveToTop = popup.getMenu().findItem(R.id.action_move_to_top);
        MenuItem moveUp = popup.getMenu().findItem(R.id.action_move_up);
        MenuItem moveToBottom = popup.getMenu().findItem(R.id.action_move_to_bottom);
        MenuItem moveDown = popup.getMenu().findItem(R.id.action_move_down);
        if (getAdapterPosition() == mAdapter.getItemCount() - (mAdapter.supportsAddingDynamicCards() ? 2 : 1)) {
            if (moveToBottom != null) {
                moveToBottom.setVisible(false);
            }
            if (moveDown != null) {
                moveDown.setVisible(false);
            }
        } else if (getAdapterPosition() == 0) {
            if (moveToTop != null) {
                moveToTop.setVisible(false);
            }
            if (moveUp != null) {
                moveUp.setVisible(false);
            }
        } else {
            if (moveToTop != null) {
                moveToTop.setVisible(true);
            }
            if (moveUp != null) {
                moveUp.setVisible(true);
            }
            if (moveToBottom != null) {
                moveToBottom.setVisible(true);
            }
            if (moveDown != null) {
                moveDown.setVisible(true);
            }
        }

        popup.show();
    }

    public boolean onMenuItemClick(MenuItem item) {
        return mAdapter.onDynamicCardPopupMenuItemClick(getAdapterPosition(), item);
    }
}
