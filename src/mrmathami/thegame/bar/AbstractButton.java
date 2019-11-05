package mrmathami.thegame.bar;

import mrmathami.thegame.GameEntities;
import mrmathami.thegame.entity.AbstractEntity;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.UIEntity;

public abstract class AbstractButton implements UIEntity {
    private String pngName;
    private final long createdTick;
    private double posX;
    private double posY;
    private double width;
    private double height;

    protected AbstractButton(long createdTick, double posX, double posY, double width, double height, String pngName) {
        this.createdTick = createdTick;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.pngName = pngName + ".png";
    }
    public abstract void onClick();

    public String getPngName() {
        return pngName;
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
}
