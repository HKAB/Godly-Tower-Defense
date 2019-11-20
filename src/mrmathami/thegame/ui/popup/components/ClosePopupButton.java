package mrmathami.thegame.ui.popup;

import mrmathami.thegame.ui.AbstractButton;

public class ClosePopupButton extends AbstractButton {

    protected ClosePopupButton(long createdTick, double assetPosX, double assetPosY, double posX, double posY, double width, double height) {
        super(createdTick, assetPosX, assetPosY, posX, posY, width, height);
    }

    @Override
    public String onClick() {
        return null;
    }
}
