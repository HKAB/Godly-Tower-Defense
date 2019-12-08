package mrmathami.thegame.ui.ingame.context;

import mrmathami.thegame.entity.UIEntity;

public abstract class AbstractUIContext implements UIEntity {
    private final int POS_X = 0;
    private final int POS_Y = 1;
    private final int WIDTH = 2;
    private final int HEIGHT = 3;

    private long createdTick;
    private double posX;
    private double posY;
    private double width;
    private double height;

    public AbstractUIContext (long createdTick, double[] pos) {
        this.createdTick = createdTick;
        this.posX = pos[POS_X];
        this.posY = pos[POS_Y];
        this.width = pos[WIDTH];
        this.height = pos[HEIGHT];
    }

    @Override
    public long getCreatedTick() {
        return createdTick;
    }

    @Override
    public double getPosX() {
        return posX;
    }

    @Override
    public double getPosY() {
        return posY;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public void onClick() {
        //Literally nothing to be done here
    }
    @Override
    public void onFocus() {
        //Literally nothing to be done here
    }
    @Override
    public void outFocus() {
        //Literally nothing to be done here
    }

    public abstract void setMoney(long money);
}
