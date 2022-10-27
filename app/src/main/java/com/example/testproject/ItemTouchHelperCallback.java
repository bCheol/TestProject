package com.example.testproject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback{

    final ListenerItemTouchHelper listenerItemTouchHelper;

    public ItemTouchHelperCallback(ListenerItemTouchHelper itemTouchHelperListener) {
        this.listenerItemTouchHelper = itemTouchHelperListener;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT|ItemTouchHelper.UP|ItemTouchHelper.DOWN;
        //    int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags,0);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return listenerItemTouchHelper.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

    public boolean isLongPressDragEnabled() {
        return true;
    }
}