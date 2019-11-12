package mrmathami.thegame.ui.button;

public class UpgradeButton extends AbstractIngameButton {
    public UpgradeButton (long createdTick, double assetPosX, double assetPosY, double posX, double posY, double width, double height) {
        super(createdTick, assetPosX, assetPosY, posX, posY, width, height);
    }

    @Override
    public String onClick () {
        return "";
    }
}