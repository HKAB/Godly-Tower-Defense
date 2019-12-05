package mrmathami.thegame.drawer.UI;

import javafx.scene.canvas.GraphicsContext;

import mrmathami.thegame.entity.UIEntity;

import javax.annotation.Nonnull;

public interface UIEntityDrawer {
	void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull UIEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom);
}