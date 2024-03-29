package mrmathami.thegame;

import javafx.scene.paint.Color;
import mrmathami.thegame.ui.menu.*;
import mrmathami.thegame.entity.UIEntity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public final class MenuUI {
    private static Collection<UIEntity> entities;

    public MenuUI(String path) {
        this.entities = new ArrayList<>();
        prepareMainMenu(path);
    }

    /**
     * Prepare main menu for the game,
     * IMPORTANT: Remember to maintain the add order, which affect the draw order of the UI.
     */
    private void prepareMainMenu(String path) {
        try (final InputStream stream = GameUI.class.getResourceAsStream(path)) {
            if (stream == null) throw new IOException("Resource not found! Resource name: " + path);
            final Scanner scanner = new Scanner(stream);
            while (scanner.hasNext()) {
                final String buttonType = scanner.next();
                if (buttonType.equals("RectMenuPane")) {
                    final double x = scanner.nextDouble();
                    final double y = scanner.nextDouble();
                    final double w = scanner.nextDouble();
                    final double h = scanner.nextDouble();
                    final int r = scanner.nextInt();
                    final int g = scanner.nextInt();
                    final int b = scanner.nextInt();
                    RectMenuPane menuPane = new RectMenuPane(0, x, y, w, h, Color.rgb(r, g, b));
                    addEntity(menuPane);
                }
                else if (buttonType.equals("SinglePlayerButton")){
                    final double assetX = scanner.nextDouble();
                    final double assetY = scanner.nextDouble();
                    final double x = scanner.nextDouble();
                    final double y = scanner.nextDouble();
                    final double w = scanner.nextDouble();
                    final double h = scanner.nextDouble();
                    addEntity(new SinglePlayerButton(0, assetX, assetY, x, y, w, h, buttonType));
                }
                else if (buttonType.equals("MultiPlayerButton")){
                    final double assetX = scanner.nextDouble();
                    final double assetY = scanner.nextDouble();
                    final double x = scanner.nextDouble();
                    final double y = scanner.nextDouble();
                    final double w = scanner.nextDouble();
                    final double h = scanner.nextDouble();
                    addEntity(new MultiPlayerButton(0, assetX, assetY, x, y, w, h, buttonType));
                }
                else if (buttonType.equals("SettingsButton")){
                    final double assetX = scanner.nextDouble();
                    final double assetY = scanner.nextDouble();
                    final double x = scanner.nextDouble();
                    final double y = scanner.nextDouble();
                    final double w = scanner.nextDouble();
                    final double h = scanner.nextDouble();
                    addEntity(new SettingsButton(0, assetX, assetY, x, y, w, h, buttonType));
                }
                else if (buttonType.equals("CreditsButton")){
                    final double assetX = scanner.nextDouble();
                    final double assetY = scanner.nextDouble();
                    final double x = scanner.nextDouble();
                    final double y = scanner.nextDouble();
                    final double w = scanner.nextDouble();
                    final double h = scanner.nextDouble();
                    addEntity(new CreditsButton(0, assetX, assetY, x, y, w, h, buttonType));
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Collection<UIEntity> getEntities() {
        return entities;
    }

    public void addEntity(UIEntity entity) {
        entities.add(entity);
    }
}
