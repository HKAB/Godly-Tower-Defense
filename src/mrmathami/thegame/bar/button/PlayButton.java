package mrmathami.thegame.bar.button;

public class PlayButton extends AbstractButton {
    private String imageUri;

    public PlayButton(long createdTick, double posX, double posY, double width, double height, String imageUri) {
        super(createdTick, posX, posY, width, height);
        this.imageUri = imageUri;
    }

    public String getImageUri() {
        return imageUri;
    }

    @Override
    public String onClick() {
        System.out.println(getImageUri() + " Clicked");
        return "";
    }

    @Override
    public void onFocus() {

    }

    @Override
    public void outFocus() {

    }
}
