package mrmathami.thegame.bar;

public class NormalButton extends AbstractButton {
    public NormalButton(long createdTick, double posX, double posY, double width, double height, String imageUri) {
        super(createdTick, posX, posY, width, height, imageUri);
    }

    @Override
    public void onClick() {
        System.out.println(getImageUri() + " Clicked");
    }
}
