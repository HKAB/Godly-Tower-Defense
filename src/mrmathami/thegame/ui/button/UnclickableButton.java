package mrmathami.thegame.ui.button;

public class UnclickableButton extends AbstractIngameButton {
    public UnclickableButton(long createdTick, double assetPosX, double assetPosY, double posX, double posY, double width, double height) {
        super(createdTick, assetPosX, assetPosY, posX, posY, width, height);
    }

    @Override
    public String onClick() {
        //Nothing happens, btw
        return "";
    }

    @Override
    public void onFocus() {
    }

    @Override
    public void outFocus() {
    }
}
