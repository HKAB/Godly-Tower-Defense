package mrmathami.thegame.ui.popup.components;

import javafx.scene.image.Image;
import mrmathami.thegame.Config;
import mrmathami.thegame.entity.UIEntity;
import mrmathami.thegame.ui.menu.AbstractMenuPane;

import java.io.File;

/**
 * Remember: Image is set to: posX - this.image.getWidth()/2/Config.TILE_SIZE;
 * Same effect as setTextAlign but for image
 */
public class PopupImage implements UIEntity {
    private double posX;
    private double posY;
    private double createdTick;
    private Image image;
    private double width;
    private double height;
    public PopupImage(long createdTick, double posX, double posY, String imgPath) {
        this.image = new Image(new File(imgPath).toURI().toString());
        this.posX = posX - this.image.getWidth()/2/Config.TILE_SIZE;
        this.posY = posY;
        this.width = this.image.getWidth();
        this.height = this.image.getHeight();
    }

    @Override
    public long getCreatedTick() {
        return 0;
    }

    @Override
    public double getPosX() {
        return posX;
    }

    @Override
    public double getPosY() {
        return posY;
    }

    @Override
    public double getWidth() {
        return this.width;
    }

    @Override
    public double getHeight() {
        return this.height;
    }

    @Override
    public String onClick() {
        return null;
    }

    @Override
    public void onFocus() {

    }

    @Override
    public void outFocus() {

    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
