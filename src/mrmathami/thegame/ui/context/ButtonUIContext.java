package mrmathami.thegame.ui.context;

public class ButtonUIContext extends AbstractUIContext {

    private String towerType;

    public ButtonUIContext (long createdTick, double posX, double posY, double width, double height, String towerType) {
        super(createdTick, posX, posY, width, height);
        this.towerType = towerType;
    }

    public String getTowerType() {
        return towerType;
    }
}
