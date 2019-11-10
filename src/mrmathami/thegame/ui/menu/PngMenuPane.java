package mrmathami.thegame.ui.menu;

public class PngMenuPane extends AbstractMenuPane {
    private String imageUri;
    public PngMenuPane(long createdTick, double posX, double posY, double width, double height, String imageUri) {
        super(createdTick, posX, posY, width, height);
        this.imageUri = imageUri;
    }

    public String getImageUri() {
        return imageUri;
    }

    @Override
    public String onClick() { return ""; }

    @Override
    public void onFocus() {

    }

    @Override
    public void outFocus() {

    }
}
