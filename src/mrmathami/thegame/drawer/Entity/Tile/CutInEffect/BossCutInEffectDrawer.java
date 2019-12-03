package mrmathami.thegame.drawer.Entity.Tile.CutInEffect;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import mrmathami.thegame.drawer.Entity.EntityDrawer;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.tile.cutineffect.BossCutInEffect;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;

public class BossCutInEffectDrawer implements EntityDrawer {
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) throws FileNotFoundException {
        Image img = new Image(getClass().getResourceAsStream(((BossCutInEffect)entity).getImageURI()));
        graphicsContext.drawImage(img, screenPosX, screenPosY);
    }
}
