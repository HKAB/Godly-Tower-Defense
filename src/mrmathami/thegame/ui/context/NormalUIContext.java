package mrmathami.thegame.ui.context;

public class NormalUIContext extends AbstractUIContext {

    private long money;
    private long targetHealth;

    public NormalUIContext (long createdTick, double posX, double posY, double width, double height, long money, long targetHealth) {
        super(createdTick, posX, posY, width, height);
        this.money = money;
        this.targetHealth = targetHealth;
    }

    public long getMoney() {
        return money;
    }

    public long getTargetHealth() {
        return targetHealth;
    }
}
