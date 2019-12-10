package mrmathami.thegame.entity;

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
     * Handle mouse click
     */
    void onClick();

    /**
     * Handle mouse hover
     */
    void onFocus();

    /**
     * Handle mouse unhover
     */
    void outFocus();
}
