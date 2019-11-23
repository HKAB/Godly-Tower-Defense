package mrmathami.thegame.ui.ingame.context;

public class MPNormalUIContext extends AbstractUIContext {
    private long money;
    private long targetHealth;
    private long currentWave;
    private long countdown;
    private long opponentHealth;

    public MPNormalUIContext (long createdTick, double[] pos, long money, long targetHealth, long opponentHealth, long currentWave, long countdown) {
        super(createdTick, pos);
        this.money = money;
        this.targetHealth = targetHealth;
        this.opponentHealth = opponentHealth;
        this.currentWave = currentWave;
        this.countdown = countdown;
    }

    public void fieldUpdate(long money, long targetHealth, long opponentHealth, long currentWave, long countdown) {
        this.money = money;
        this.targetHealth = targetHealth;
        this.opponentHealth = opponentHealth;
        this.currentWave = currentWave;
        this.countdown = countdown;
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

    public long getCurrentWave() {
        return currentWave;
    }

    public void setCurrentWave(long currentWave) {
        this.currentWave = currentWave;
    }

    public long getCountdown() {
        return countdown;
    }

    public void setCountdown(long countdown) {
        this.countdown = countdown;
    }
}