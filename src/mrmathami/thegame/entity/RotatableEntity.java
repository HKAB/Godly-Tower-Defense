package mrmathami.thegame.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public interface RotatableEntity extends GameEntity {
    void rotate(GraphicsContext graphicsContext, Image image, double screenPosX, double screenPosY, double angle);
    void rotate(GraphicsContext graphicsContext, Image[] image, double screenPosX, double screenPosY, double angle);
}
