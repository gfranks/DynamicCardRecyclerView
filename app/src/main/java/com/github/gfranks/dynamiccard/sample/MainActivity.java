package com.github.gfranks.dynamiccard.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.github.gfranks.dynamiccard.DynamicCardCallback;
import com.github.gfranks.dynamiccard.adapter.DynamicCardAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DynamicCardCallback {

    private RecyclerViewAdapter mAdapter;
    private List<DynamicItem> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.dynamic_list);
        mAdapter = new RecyclerViewAdapter(recyclerView, getSampleItems());
        mAdapter.setDynamicCardCallback(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_edit) {
            if (mAdapter.isEditMode()) {
                mAdapter.cancel();
                item.setTitle(R.string.edit);
            } else {
                mAdapter.edit();
                item.setTitle(R.string.done);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAddNewDynamicCard(DynamicCardAdapter adapter) {
        mAdapter.addItem(new DynamicItem("Added Item " + mAdapter.getItemCount()));
    }

    @Override
    public void onDynamicCardPositionChanged(DynamicCardAdapter adapter, int fromPosition, int toPosition) {
    }

    private List<DynamicItem> getSampleItems() {
        if (mItems == null) {
            mItems = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                mItems.add(new DynamicItem("Item " + (i + 1)));
            }
        }
        return mItems;
    }
}
