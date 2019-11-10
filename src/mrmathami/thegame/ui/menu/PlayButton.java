package mrmathami.thegame.ui.menu;

public class PlayButton extends AbstractMenuButton {
    private String imageUri;
    private String outFocusImageUri;
    private String onFocusImageUri;

    public PlayButton(long createdTick, double posX, double posY, double width, double height, String imageUri, String onFocusImageUri) {
        super(createdTick, posX, posY, width, height);
        this.imageUri = imageUri;
        this.outFocusImageUri = imageUri;
        this.onFocusImageUri = onFocusImageUri;
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
        System.out.println("Onfocus called");
        this.imageUri = this.onFocusImageUri;
    }

    @Override
    public void outFocus() {
        System.out.println("Outfocus called");
        this.imageUri = this.outFocusImageUri;
    }
}
