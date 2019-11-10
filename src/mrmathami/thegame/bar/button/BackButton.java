package mrmathami.thegame.bar.button;

public class BackButton extends AbstractIngameButton {
    public BackButton (long createdTick, double assetPosX, double assetPosY, double posX, double posY, double width, double height) {
        super(createdTick, assetPosX, assetPosY, posX, posY, width, height);
    }

    @Override
    public String onClick () {
        return "";
    }
}
