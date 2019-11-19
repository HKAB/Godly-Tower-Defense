package mrmathami.thegame.ui.ingame.button;

public class ContextButton extends AbstractButton {
    private String command;

    public ContextButton (long createdTick, double assetPosX, double assetPosY, double posX, double posY, double width, double height, String command) {
        super(createdTick, assetPosX, assetPosY, posX, posY, width, height);
        this.command = command;
    }

    @Override
    public String onClick () {
        return command;
    }
}
