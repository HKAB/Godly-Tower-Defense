package mrmathami.thegame;

import mrmathami.thegame.bar.button.*;
import mrmathami.thegame.entity.UIEntity;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public final class GameUI {
    private Collection<AbstractButton> entities;

    public GameUI(String path) {
        this.entities = new ArrayList<AbstractButton>();
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


                    switch (value) {
                        case "BackButton":
                            entities.add(new BackButton(0, assetX, assetY, x, y, w, h));
                            break;
                        case "PauseButton":
                            entities.add(new PauseButton(0, assetX, assetY, x, y, w, h));
                            break;
                        case "TowerButton":
                            final String towerType = scanner.next();
                            entities.add(new TowerButton(0, assetX, assetY, x, y, w, h, towerType));
                            break;
                        case "UpgradeButton":
                            entities.add(new UpgradeButton(0, assetX, assetY, x, y, w, h));
                            break;
                        case "SellButton":
                            entities.add(new SellButton(0, assetX, assetY, x, y, w, h));
                            break;
//                        case "MoneyButton":
//                            entities.add(new UnclickableButton(0, assetX, assetY, x, y, w, h));
//                            break;
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Collection<AbstractButton> getEntities() {
        return entities;
    }

    public void addButton(AbstractButton entity) {
        entities.add(entity);
    }
}
