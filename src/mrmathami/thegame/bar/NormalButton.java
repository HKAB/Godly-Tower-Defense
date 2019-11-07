package mrmathami.thegame.bar;

public class NormalButton extends AbstractButton {
    public NormalButton(long createdTick, double posX, double posY, double width, double height, String pngName) {
        super(createdTick, posX, posY, width, height, pngName);
    }

    @Override
    public void onClick() {
        System.out.println(getPngName() + " Clicked");
    }
}
