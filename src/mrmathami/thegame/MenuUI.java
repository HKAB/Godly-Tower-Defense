package mrmathami.thegame;

import javafx.scene.paint.Color;
import mrmathami.thegame.bar.button.PlayButton;
import mrmathami.thegame.bar.RectMenuPane;
import mrmathami.thegame.entity.UIEntity;

import java.util.ArrayList;
import java.util.Collection;

public final class MenuUI {
    private Collection<UIEntity> entities;

    public MenuUI() {
        this.entities = new ArrayList<>();
        prepareMainMenu();
    }

    /**
     * Prepare main menu for the game,
     * IMPORTANT: Remember to maintain the add order, which affect the draw order of the UI.
     */
    private void prepareMainMenu() {
        PlayButton playButton = new PlayButton(0,
                Config.SCREEN_WIDTH/2 - 81,
                Config.SCREEN_HEIGHT/2 - 100,
                162, 98, "/menu/button_play.png");
        RectMenuPane menuPane = new RectMenuPane(0,
                Config.SCREEN_WIDTH/2 - 150,
                Config.SCREEN_HEIGHT/2 - 200,
                300, 400, Color.rgb(0xCC, 0xBE, 0xA3));

        addEntity(menuPane);
        addEntity(playButton);
    }

    public Collection<UIEntity> getEntities() {
        return this.entities;
    }

    public void addEntity(UIEntity entity) {
        entities.add(entity);
    }
}
