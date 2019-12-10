package mrmathami.thegame.drawer.UI.InGame;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import mrmathami.thegame.Config;
import mrmathami.thegame.drawer.Entity.GameDrawer;
import mrmathami.thegame.drawer.UI.UIEntityDrawer;
import mrmathami.thegame.ui.button.AbstractButton;
import mrmathami.thegame.ui.ingame.button.SellButton;
import mrmathami.thegame.ui.ingame.button.TowerButton;
import mrmathami.thegame.entity.UIEntity;
import mrmathami.thegame.ui.ingame.button.UpgradeButton;

import javax.annotation.Nonnull;
import java.io.File;

public class GameButtonDrawer implements UIEntityDrawer {

    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull UIEntity entity,
                     double screenPosX, double screenPosY, double screenWidth, double screenHeight, double fieldZoom) {
        Image img = GameDrawer.getButtonImage();
        PixelReader reader = img.getPixelReader();

        AbstractButton button = (AbstractButton)entity;

        WritableImage buttonImage = new WritableImage(reader,
                (int)button.getAssetPosX() * (int)fieldZoom,
                (int)button.getAssetPosY() * (int)fieldZoom,
                (int)screenWidth,
                (int)screenHeight);
        graphicsContext.drawImage(buttonImage, screenPosX, screenPosY);

        if (entity instanceof TowerButton) {
            img = GameDrawer.getSheetImage();
            reader = img.getPixelReader();

            TowerButton towerButton = (TowerButton)entity;
            int maxTileWidth = (int)Math.round(img.getWidth()/ Config.TILE_SIZE);
            int maxTileHeight = (int)Math.round(img.getHeight()/Config.TILE_SIZE);

            buttonImage = new WritableImage(reader,
                    (towerButton.getGID() - 1) % maxTileWidth * (int)screenWidth,
                    Math.round((towerButton.getGID() - 1) / maxTileWidth) * (int)screenHeight,
                    (int)screenWidth,
                    (int)screenHeight);
            graphicsContext.drawImage(buttonImage, screenPosX, screenPosY);
        }

        if ((entity instanceof TowerButton) || (entity instanceof UpgradeButton) || (entity instanceof SellButton)) {
            String keyBinding = "";
            if (entity instanceof TowerButton) keyBinding = ((TowerButton) entity).getKeyBinding();
            else if (entity instanceof UpgradeButton) keyBinding = "Z";
            else if (entity instanceof SellButton) keyBinding = "X";
            
            if (!keyBinding.equals("")) {
                graphicsContext.setFill(Color.rgb(116, 185, 255));
                graphicsContext.fillRect(screenPosX, screenPosY + screenHeight - 10, 10, 10);

                graphicsContext.setStroke(Color.rgb(0, 0, 0));
                graphicsContext.setLineWidth(1);
                graphicsContext.strokeRect(screenPosX, screenPosY + screenHeight - 10, 10, 10);

                graphicsContext.setFill(Color.rgb(0, 0, 0));
                graphicsContext.setTextAlign(TextAlignment.CENTER);
                graphicsContext.setTextBaseline(VPos.BOTTOM);
                graphicsContext.setFont(Font.loadFont(new File("res/font/Tomorrow-Regular.ttf").toURI().toString(), 8));
                graphicsContext.fillText(keyBinding, screenPosX + 5, screenPosY + screenHeight);
            }
        }
    }
}
