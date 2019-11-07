package mrmathami.thegame;

import mrmathami.thegame.bar.NormalButton;
import mrmathami.thegame.entity.UIEntity;

import java.util.ArrayList;
import java.util.Collection;

public final class GameUI {
    private Collection<UIEntity> entities;

    public GameUI() {
        NormalButton button = new NormalButton(0, 150, 150, 64, 64, "button_home");
        NormalButton dragable = new NormalButton(0, 600, 150, 64, 64, "button_play");
        this.entities = new ArrayList<UIEntity>();
        addEntity(button);
        addEntity(dragable);
    }

    public Collection<UIEntity> getEntities() {
        return this.entities;
    }

    public void addEntity(UIEntity entity) {
        entities.add(entity);
    }
}
