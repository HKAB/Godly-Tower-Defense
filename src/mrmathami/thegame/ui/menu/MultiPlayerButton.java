package mrmathami.thegame.ui.menu;

import mrmathami.thegame.ui.button.AbstractButton;

public class MultiPlayerButton extends AbstractButton {
    private String command;

    public MultiPlayerButton(long createdTick, double assetPosX, double assetPosY, double posX, double posY, double width, double height, String command) {
        super(createdTick, assetPosX, assetPosY, posX, posY, width, height);
        this.command = command;
    }

    @Override
    public void onClick () {
    }
}
