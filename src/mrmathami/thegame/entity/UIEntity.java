package mrmathami.thegame.entity;

import javafx.scene.image.WritableImage;

/**
 * A game entity
 */
public interface UIEntity {
    /**
     * @return entity created tick count
     */
    long getCreatedTick();

    /**
     * @return field pos x
     */
    double getPosX();

    /**
     * @return field pos y
     */
    double getPosY();

    /**
     * @return field width
     */
    double getWidth();

    /**
     * @return field height
     */
    double getHeight();

    /**
     * Handle mouse event
     */
    void onClick();
    void onFocus();
    void outFocus();
}
