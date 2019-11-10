package mrmathami.thegame.bar.button;

public class UpgradeButton extends AbstractButton {
    public UpgradeButton (long createdTick, double assetPosX, double assetPosY, double posX, double posY, double width, double height) {
        super(createdTick, assetPosX, assetPosY, posX, posY, width, height);
    }

    @Override
    public String onClick () {
        return "";
    }
}