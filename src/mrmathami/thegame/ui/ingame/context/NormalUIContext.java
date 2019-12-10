package mrmathami.thegame.ui.ingame.context;

public class NormalUIContext extends AbstractUIContext {
    private long money;
    private long targetHealth;

    public NormalUIContext (long createdTick, double[] pos, long money, long targetHealth) {
        super(createdTick, pos);
        this.money = money;
        this.targetHealth = targetHealth;
    }

    public void fieldUpdate(long money, long targetHealth) {
        this.money = money;
        this.targetHealth = targetHealth;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public long getTargetHealth() {
        return targetHealth;
    }

    public void setTargetHealth(long targetHealth) {
        this.targetHealth = targetHealth;
    }
}
