package mrmathami.thegame.ui.menu;

public class PlayButton extends AbstractMenuButton {
    private String imageUri;
    private String outFocusImageUri;
    private String onFocusImageUri;

    public PlayButton(long createdTick, double posX, double posY, double width, double height, String imageUri, String focusImageUri) {
        super(createdTick, posX, posY, width, height);
        this.imageUri = imageUri;
        this.outFocusImageUri = imageUri;
        this.onFocusImageUri = focusImageUri;
    }

    public String getImageUri() {
        return imageUri;
    }

    @Override
    public String onClick() {
        return "";
    }

    @Override
    public void onFocus() {
        this.imageUri = this.onFocusImageUri;
    }

    @Override
    public void outFocus() {
        this.imageUri = this.outFocusImageUri;
    }
}
