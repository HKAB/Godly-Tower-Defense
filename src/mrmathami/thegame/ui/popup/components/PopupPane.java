package mrmathami.thegame.ui.popup;

import mrmathami.thegame.ui.menu.AbstractMenuPane;

public class PopupPane extends AbstractMenuPane {
    protected PopupPane(long createdTick, double posX, double posY, double width, double height) {
        super(createdTick, posX, posY, width, height);
    }

    @Override
    public String onClick() {
        return null;
    }

    @Override
    public void onFocus() {

    }

    @Override
    public void outFocus() {

    }
}
