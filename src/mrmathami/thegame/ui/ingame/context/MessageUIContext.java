package mrmathami.thegame.ui.ingame.context;

public class MessageUIContext extends AbstractUIContext {
    private final String message = "Right click or press ESC\nto cancel.";

    public MessageUIContext (long createdTick, double[] pos) {
        super(createdTick, pos);
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void setMoney(long money) {

    }
}