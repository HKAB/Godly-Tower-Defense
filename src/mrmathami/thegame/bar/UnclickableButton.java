package mrmathami.thegame.bar;

public class UnclickableButton extends AbstractButton {
    public UnclickableButton(long createdTick, double assetPosX, double assetPosY, double posX, double posY, double width, double height) {
        super(createdTick, assetPosX, assetPosY, posX, posY, width, height);
    }

    @Override
    public void onClick() {
        //Nothing happens, btw
    }

    @Override
    public void onFocus() {
    }

    @Override
    public void outFocus() {
    }
}
