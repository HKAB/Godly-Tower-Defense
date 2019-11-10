package mrmathami.thegame.ui.menu;

import mrmathami.thegame.ui.AbstractButton;

public abstract class AbstractMenuButton extends AbstractButton {
    protected AbstractMenuButton(long createdTick, double posX, double posY, double width, double height) {
        super(createdTick, posX, posY, width, height);
    }
}
