package mrmathami.thegame.ui.popup.components;

import mrmathami.thegame.Config;
import mrmathami.thegame.ui.button.AbstractButton;

public class PopupButton extends AbstractButton {
    private String content = "";
    private int fontSize;
    public PopupButton(long createdTick, double assetPosX, double assetPosY, double posX, double posY, int fontSize, String content) {
        super(createdTick, assetPosX, assetPosY, posX - fontSize*1.0*content.length()/Config.TILE_SIZE, posY, fontSize*content.length(), fontSize*2);
        this.content = content;
        this.fontSize = fontSize;
    }

    @Override
    public void onClick() {

    }

    public String getContent() {
        return content;
    }

    public int getFontSize() {
        return fontSize;
    }
}
