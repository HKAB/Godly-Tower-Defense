package mrmathami.thegame.ui.ingame.context;

import mrmathami.thegame.Config;
import mrmathami.thegame.entity.UIEntity;

public abstract class AbstractUIContext implements UIEntity {

    private long createdTick;
    private double posX;
    private double posY;
    private double width;
    private double height;

    //the always displayed value
    private long money;
    private long targetHealth;
    private long currentWave;
    private long countdown;

    public AbstractUIContext (long createdTick, long money, long targetHealth, long currentWave, long countdown) {
        this.createdTick = createdTick;
        this.posX = Config.UI_CONTEXT_POS_X;
        this.posY = Config.UI_CONTEXT_POS_Y;
        this.width = Config.UI_CONTEXT_WIDTH;
        this.height = Config.UI_CONTEXT_HEIGHT;

        this.money = money;
        this.targetHealth = targetHealth;
        this.currentWave = currentWave;
        this.countdown = countdown;
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

    public long getMoney() {
        return money;
    }

    public long getTargetHealth() {
        return targetHealth;
    }

    public long getCurrentWave() {
        return currentWave;
    }

    public long getCountdown() {
        return countdown;
    }

    public void fieldUpdate(long money, long targetHealth, long currentWave, long countdown) {
        this.money = money;
        this.targetHealth = targetHealth;
        this.currentWave = currentWave;
        this.countdown = countdown;
    }

    @Override
    public String onClick() {
        //Literally nothing to be done here
        return "";
    }
    @Override
    public void onFocus() {
        //Literally nothing to be done here
    }
    @Override
    public void outFocus() {
        //Literally nothing to be done here
    }
}
