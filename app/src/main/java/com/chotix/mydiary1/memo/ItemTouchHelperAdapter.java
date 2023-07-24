package com.chotix.mydiary1.memo;

public interface ItemTouchHelperAdapter {
    void onItemMove(int fromPosition, int toPosition);

    void onItemSwap(int position);

    void onItemMoveFinish();
}
