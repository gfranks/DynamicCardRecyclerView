package com.github.gfranks.dynamiccard;

import com.github.gfranks.dynamiccard.adapter.DynamicCardAdapter;

public interface DynamicCardCallback {

    void onAddNewDynamicCard(DynamicCardAdapter adapter);
    void onDynamicCardPositionChanged(DynamicCardAdapter adapter, int fromPosition, int toPosition);
}
