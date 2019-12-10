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
import mrmathami.thegame.entity.UIEntity;
import mrmathami.thegame.ui.ingame.context.TowerUIContext;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class TowerUIContextDrawer implements UIEntityDrawer {
    private final Map<String, Integer> ICON_GID = new HashMap<>(Map.ofEntries(
            Map.entry("money", 0),
            Map.entry("lives", 1),
            Map.entry("wave", 2),
            Map.entry("countdown", 3),
            Map.entry("firepower", 4),
            Map.entry("level", 5),
            Map.entry("speed", 6),
            Map.entry("opponent", 7),
            Map.entry("upgrade", 8),
            Map.entry("sell", 9),
            Map.entry("buy", 10)
    ));
    private final long LINE_HEIGHT = 40;
    private final long TEXT_TAB = 30;
    private final long EDGE_SIZE = 10;
    private final long FONT_SIZE = 25;
    private final long OFF_SET = 3;

    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull UIEntity entity,
                     double screenPosX, double screenPosY, double screenWidth, double screenHeight, double fieldZoom) {
        Image img = GameDrawer.getContextIconImage();
        PixelReader reader = img.getPixelReader();

        TowerUIContext context = (TowerUIContext)entity;

        final double leftIconPosX = screenPosX + EDGE_SIZE;
        final double rightIconPosX = screenPosX + context.getWidth() * fieldZoom / 2;
        final double leftTextPosX = leftIconPosX + TEXT_TAB;
        final double rightTextPosX = rightIconPosX + TEXT_TAB;
        //double linePosY = screenPosY + EDGE_SIZE;
        double linePosY = screenPosY;
        WritableImage icon;

        graphicsContext.setTextAlign(TextAlignment.LEFT);
        graphicsContext.setTextBaseline(VPos.TOP);
        graphicsContext.setFont(Font.loadFont(new File("res/font/Tomorrow-Regular.ttf").toURI().toString(), FONT_SIZE));

        /**
         * Level
         */
        icon = new WritableImage(reader, (int)(getIconGID("level") * fieldZoom), 0, (int)Config.TILE_SIZE, (int)Config.TILE_SIZE);
        graphicsContext.drawImage(icon, leftIconPosX, linePosY);
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(Long.toString(context.getTower().getLevel() + 1) + " / 3", leftTextPosX, linePosY - OFF_SET, 75);

        linePosY += LINE_HEIGHT;

        /**
         * Firepower
         */
        icon = new WritableImage(reader, (int)(getIconGID("firepower") * fieldZoom), 0, (int)Config.TILE_SIZE, (int)Config.TILE_SIZE);
        graphicsContext.drawImage(icon, leftIconPosX, linePosY);
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(Long.toString(context.getTower().getFirepower()), leftTextPosX, linePosY - OFF_SET, 75);

        /**
         * Speed
         */
        icon = new WritableImage(reader, (int)(getIconGID("speed") * fieldZoom), 0, (int)Config.TILE_SIZE, (int)Config.TILE_SIZE);
        graphicsContext.drawImage(icon, rightIconPosX, linePosY);
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(Long.toString(context.getTower().getSpeed()), rightTextPosX, linePosY - OFF_SET, 75);

        linePosY += LINE_HEIGHT;

        /**
         * Upgrade price
         */
        icon = new WritableImage(reader, (int)(getIconGID("upgrade") * fieldZoom), 0, (int)Config.TILE_SIZE, (int)Config.TILE_SIZE);
        graphicsContext.drawImage(icon, leftIconPosX, linePosY);
        if (context.getTower().getUpgradePrice() > context.getMoney()) graphicsContext.setFill(Color.RED);
        else graphicsContext.setFill(Color.WHITE);
        if (context.getTower().getUpgradePrice() != 0) {
            graphicsContext.fillText(context.getTower().getUpgradePrice() + "$", leftTextPosX, linePosY - OFF_SET, 75);
        }
        else {
            graphicsContext.fillText("N/A", leftTextPosX, linePosY, 75);
        }

        /**
         * Sell price
         */
        icon = new WritableImage(reader, (int)(getIconGID("sell") * fieldZoom), 0, (int)Config.TILE_SIZE, (int)Config.TILE_SIZE);
        graphicsContext.drawImage(icon, rightIconPosX, linePosY);
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(context.getTower().getSellPrice() + "$", rightTextPosX, linePosY - OFF_SET, 75);
    }

    private int getIconGID (String icon) {
        return ICON_GID.get(icon);
    }
}
