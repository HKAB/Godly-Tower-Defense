package mrmathami.thegame;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import mrmathami.thegame.bar.AbstractButton;
import mrmathami.thegame.bar.NormalButton;
import mrmathami.thegame.bar.UnclickableButton;
import mrmathami.thegame.drawer.GameDrawer;
import mrmathami.thegame.entity.UIEntity;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public final class GameUI {
    private Collection<UIEntity> entities;

    public GameUI(String path) {
        this.entities = new ArrayList<UIEntity>();
        try (final InputStream stream = GameUI.class.getResourceAsStream(path)) {
            if (stream == null) throw new IOException("Resource not found! Resource name: " + path);
            final Scanner scanner = new Scanner(stream);
            while (true) {
                final String value = scanner.next();
                if (value.equals("EndOfFile")) break;
                else {
                    final double assetX = scanner.nextDouble() * Config.TILE_SIZE;
                    final double assetY = scanner.nextDouble() * Config.TILE_SIZE;
                    final double x = scanner.nextDouble() * Config.TILE_SIZE;
                    final double y = scanner.nextDouble() * Config.TILE_SIZE;
                    final double w = scanner.nextDouble() * Config.TILE_SIZE;
                    final double h = scanner.nextDouble() * Config.TILE_SIZE;

                    if (value.equals("MoneyButton")) {
                        entities.add(new UnclickableButton(0, assetX, assetY, x, y, w, h));
                    }
                    else {
                        entities.add(new NormalButton(0, assetX, assetY, x, y, w, h));
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Collection<UIEntity> getEntities() {
        return entities;
    }

    public void addButton(UIEntity entity) {
        entities.add(entity);
    }
}
