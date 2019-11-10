package mrmathami.thegame.ui.menu;

public class OptionsButton extends AbstractMenuButton {
    private String imageUri;
    private String outFocusImageUri;
    private String onFocusImageUri;

    public OptionsButton(long createdTick, double posX, double posY, double width, double height, String imageUri, String focusImageUri) {
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
