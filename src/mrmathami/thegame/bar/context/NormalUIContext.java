package mrmathami.thegame.bar.context;

public class NormalUIContext extends AbstractUIContext {

    public NormalUIContext (long createdTick, double posX, double posY, double width, double height) {
        super(createdTick, posX, posY, width, height);
    }

    @Override
    public String onClick() {
        return "";
    }

    @Override
    public void onFocus() {

    }

    @Override
    public void outFocus() {

    }
}
