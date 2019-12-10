package mrmathami.thegame;

import mrmathami.thegame.entity.UIEntity;
import mrmathami.thegame.ui.ingame.button.*;

import java.util.ArrayList;
import java.util.Collection;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public final class GameUI {
    private Collection<UIEntity> entities;

    public GameUI(String path) {
        this.entities = new ArrayList<UIEntity>();
        prepareGameUI(path);
    }

    private void prepareGameUI (String path) {
        try (final InputStream stream = GameUI.class.getResourceAsStream(path)) {
            if (stream == null) throw new IOException("Resource not found! Resource name: " + path);
            final Scanner scanner = new Scanner(stream);
            while (scanner.hasNext()) {
                final String value = scanner.next();
                final double assetX = scanner.nextDouble();
                final double assetY = scanner.nextDouble();
                final double x = scanner.nextDouble();
                final double y = scanner.nextDouble();
                final double w = scanner.nextDouble();
                final double h = scanner.nextDouble();

                switch (value) {
                    case "BackButton":
                        addButton(new BackButton(0, assetX, assetY, x, y, w, h));
                        break;
                    case "PauseButton":
                        addButton(new PauseButton(0, assetX, assetY, x, y, w, h));
                        break;
                    case "UpgradeButton":
                        addButton(new UpgradeButton(0, assetX, assetY, x, y, w, h));
                        break;
                    case "SellButton":
                        addButton(new SellButton(0, assetX, assetY, x, y, w, h));
                        break;
                    case "TowerButton":
                        final String towerType = scanner.next();
                        addButton(new TowerButton(0, assetX, assetY, x, y, w, h, towerType));
                        break;
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
