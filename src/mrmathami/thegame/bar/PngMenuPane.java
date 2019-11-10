package mrmathami.thegame.bar;

public class PngMenuPane extends AbstractPane {
    private String imageUri;
    public PngMenuPane(long createdTick, double posX, double posY, double width, double height, String imageUri) {
        super(createdTick, posX, posY, width, height);
        this.imageUri = imageUri;
    }

    public String getImageUri() {
        return imageUri;
    }

    @Override
    public void onClick() { }
}
