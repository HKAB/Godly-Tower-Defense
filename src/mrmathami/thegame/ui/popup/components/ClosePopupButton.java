package mrmathami.thegame.ui.popup.components;

import mrmathami.thegame.Config;
import mrmathami.thegame.ui.AbstractButton;

public class ClosePopupButton extends AbstractButton {

    public ClosePopupButton(long createdTick, double assetPosX, double assetPosY, double posX, double posY) {
        super(createdTick, assetPosX, assetPosY, posX - 32.0/Config.TILE_SIZE, posY, 64, 64);
    }

    @Override
    public String onClick() {
        return null;
    }
}
