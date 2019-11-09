package mrmathami.thegame;

import mrmathami.thegame.bar.PlayButton;
import mrmathami.thegame.entity.UIEntity;

import java.util.ArrayList;
import java.util.Collection;

public final class MenuUI {
    private Collection<UIEntity> entities;

    public MenuUI() {
        this.entities = new ArrayList<>();
        prepareMainMenu();
    }

    private void prepareMainMenu() {
        PlayButton playButton = new PlayButton(0,
                Config.SCREEN_WIDTH/2 - 64,
                Config.SCREEN_HEIGHT/2 - 64,
                128, 64, "/menu/play_button.png");
        addEntity(playButton);
    }

    public Collection<UIEntity> getEntities() {
        return this.entities;
    }

    public void addEntity(UIEntity entity) {
        entities.add(entity);
    }
}
