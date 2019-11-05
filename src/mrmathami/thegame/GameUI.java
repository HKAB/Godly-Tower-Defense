package mrmathami.thegame;

import mrmathami.thegame.bar.NormalButton;
import mrmathami.thegame.entity.UIEntity;

import java.util.ArrayList;
import java.util.Collection;

public final class GameUI {
    private Collection<UIEntity> entities;

    public GameUI() {
        NormalButton button = new NormalButton(0, 150, 150, 64, 64, "button_home");
        this.entities = new ArrayList<UIEntity>();
        this.entities.add(button);
    }

    public Collection<UIEntity> getEntities() {
        return this.entities;
    }

    public void addButton(UIEntity entity) {
        entities.add(entity);
    }
}
