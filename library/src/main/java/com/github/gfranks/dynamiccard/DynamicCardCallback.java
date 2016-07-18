package com.github.gfranks.dynamiccard;

public interface DynamicCardCallback {

    void onAddNewDynamicCard(DynamicCardAdapter adapter);
    void onDynamicCardAdded(DynamicCardAdapter adapter, int position);
    void onDynamicCardPositionChanged(DynamicCardAdapter adapter, int fromPosition, int toPosition);
    void onDynamicCardRemoved(DynamicCardAdapter adapter, int position);
}
