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
    private Collection<UIEntity> entities;

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
            while (true) {
                final String value = scanner.next();
                if (value.equals("EndOfFile")) break;
                else if (value.equals("RectMenuPane")) {
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
                else {
                    final double assetX = scanner.nextDouble();
                    final double assetY = scanner.nextDouble();
                    final double x = scanner.nextDouble();
                    final double y = scanner.nextDouble();
                    final double w = scanner.nextDouble();
                    final double h = scanner.nextDouble();
                    addEntity(new MenuButton(0, assetX, assetY, x, y, w, h, value));
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Collection<UIEntity> getEntities() {
        return this.entities;
    }

    public void addEntity(UIEntity entity) {
        entities.add(entity);
    }
}
