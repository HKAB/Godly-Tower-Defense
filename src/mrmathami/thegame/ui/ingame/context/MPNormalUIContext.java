package mrmathami.thegame.ui.ingame.context;

public class MPNormalUIContext extends AbstractUIContext {
    private long money;
    private long targetHealth;
    private long opponentHealth;

    public MPNormalUIContext (long createdTick, double[] pos, long money, long targetHealth, long opponentHealth) {
        super(createdTick, pos);
        this.money = money;
        this.targetHealth = targetHealth;
        this.opponentHealth = opponentHealth;
    }

    public void fieldUpdate(long money, long targetHealth, long opponentHealth) {
        this.money = money;
        this.targetHealth = targetHealth;
        this.opponentHealth = opponentHealth;
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

    public long getOpponentHealth() {
        return opponentHealth;
    }

    public void setOpponentHealth(long opponentHealth) {
        this.opponentHealth = opponentHealth;
    }
}