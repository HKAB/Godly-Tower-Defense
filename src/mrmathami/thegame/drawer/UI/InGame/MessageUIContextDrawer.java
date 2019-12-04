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
import mrmathami.thegame.ui.ingame.context.ButtonUIContext;
import mrmathami.thegame.ui.ingame.context.MessageUIContext;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class MessageUIContextDrawer implements UIEntityDrawer {
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
    private final long FONT_SIZE = 22;

    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull UIEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double fieldZoom) {
        Image img = GameDrawer.getContextIconImage();
        PixelReader reader = img.getPixelReader();

        MessageUIContext context = (MessageUIContext) entity;

        final double linePosX = screenPosX + EDGE_SIZE;
        double linePosY = screenPosY;
        WritableImage icon;

        graphicsContext.setTextAlign(TextAlignment.LEFT);
        graphicsContext.setTextBaseline(VPos.TOP);
        graphicsContext.setFont(Font.loadFont(new File("res/ui/Tomorrow-Italic.ttf").toURI().toString(), FONT_SIZE));

        graphicsContext.fillText(context.getMessage(), linePosX, linePosY, screenWidth - 2 * EDGE_SIZE);
    }

    private int getIconGID (String icon) {
        return ICON_GID.get(icon);
    }
}