package mrmathami.thegame.bar.button;

public class PauseButton extends AbstractIngameButton {
    public PauseButton (long createdTick, double assetPosX, double assetPosY, double posX, double posY, double width, double height) {
        super(createdTick, assetPosX, assetPosY, posX, posY, width, height);
    }

    @Override
    public String onClick () {
        return "";
    }
}