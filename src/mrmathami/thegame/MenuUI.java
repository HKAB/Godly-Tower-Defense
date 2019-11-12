package mrmathami.thegame;

import javafx.scene.paint.Color;
import mrmathami.thegame.ui.menu.*;
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
        RectMenuPane menuPane = new RectMenuPane(0,
                Config.SCREEN_WIDTH/2 - 150,
                Config.SCREEN_HEIGHT/2 - 200,
                300, 400, Color.rgb(0xCC, 0xBE, 0xA3));
        addEntity(menuPane);

        PlayButton playButton = new PlayButton(0,
                Config.SCREEN_WIDTH/2 - 80,
                Config.SCREEN_HEIGHT/2 - 150,
                162, 98,
                "/menu/button_play_outfocus.png", "/menu/button_play_onfocus.png");
        addEntity(playButton);

        OptionsButton optionsButton = new OptionsButton(0,
                Config.SCREEN_WIDTH/2 - 80,
                Config.SCREEN_HEIGHT/2 - 20,
                161, 66,
                "/menu/button_options_outfocus.png", "/menu/button_options_onfocus.png");
        addEntity(optionsButton);

        CreditsButton creditsButton = new CreditsButton(0,
                Config.SCREEN_WIDTH/2 - 80,
                Config.SCREEN_HEIGHT/2 + 80,
                161, 66,
                "/menu/button_credits_outfocus.png", "/menu/button_credits_onfocus.png");
        addEntity(creditsButton);
    }

    public Collection<UIEntity> getEntities() {
        return this.entities;
    }

    public void addEntity(UIEntity entity) {
        entities.add(entity);
    }
}
