package knight.rider.kitt.bar.listener;

import knight.rider.kitt.bar.attr.Tab;

public interface OnBottomBarEventListener {

    void onEvent(int tabIndex, boolean isRepeat, boolean isBindFragment, Tab tab);
}
