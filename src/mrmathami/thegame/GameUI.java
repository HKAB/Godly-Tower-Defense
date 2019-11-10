package mrmathami.thegame;

import mrmathami.thegame.entity.UIEntity;
import mrmathami.thegame.ui.button.*;

import java.util.ArrayList;
import java.util.Collection;
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
                    final double assetX = scanner.nextDouble();
                    final double assetY = scanner.nextDouble();
                    final double x = scanner.nextDouble();
                    final double y = scanner.nextDouble();
                    final double w = scanner.nextDouble();
                    final double h = scanner.nextDouble();
                    String towerType = "";
                    if (value.equals("TowerButton")) {
                        towerType = scanner.next();
                    }

                    if (value.equals("MoneyButton")) {
                        entities.add(new UnclickableButton(0, assetX, assetY, x, y, w, h));
                    }
                    else if (value.equals("BackButton")) {
                        entities.add(new BackButton(0, assetX, assetY, x, y, w, h));
                    }
                    else if (value.equals("PauseButton")) {
                        entities.add(new PauseButton(0, assetX, assetY, x, y, w, h));
                    }
                    else if (value.equals("TowerButton")) {
                        entities.add(new TowerButton(0, assetX, assetY, x, y, w, h, towerType));
                    }
                    else if (value.equals("UpgradeButton")) {
                        entities.add(new UpgradeButton(0, assetX, assetY, x, y, w, h));
                    }
                    else if (value.equals("SellButton")) {
                        entities.add(new SellButton(0, assetX, assetY, x, y, w, h));
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
