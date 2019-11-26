package mrmathami.thegame.ui.popup;

import javafx.scene.layout.StackPane;
import mrmathami.thegame.Config;
import mrmathami.thegame.ui.popup.components.ClosePopupButton;
import mrmathami.thegame.ui.popup.components.PopupButton;
import mrmathami.thegame.ui.popup.components.PopupImage;
import mrmathami.thegame.ui.popup.components.PopupLabel;

public class GameOverPopup extends AbstractPopup {

    public GameOverPopup(long createdTick, double posX, double posY, double width, double height, StackPane stackPane) {
        super(createdTick, posX, posY, width, height, stackPane);
        getPopupEntities().add(new PopupLabel(0, (Config.SCREEN_WIDTH/2.0)/Config.TILE_SIZE, (Config.SCREEN_HEIGHT/2.0 + 150)/Config.TILE_SIZE, 150, "GAME OVER"));
        getPopupEntities().add(new PopupImage(0, (Config.SCREEN_WIDTH/2.0)/Config.TILE_SIZE, (Config.SCREEN_HEIGHT/2.0 - 256)/Config.TILE_SIZE, "res/menu/sad.png"));
        PopupButton backMenuButton = new PopupButton(0, 0, 0, (Config.SCREEN_WIDTH/2.0 + 32)/Config.TILE_SIZE, (Config.SCREEN_HEIGHT/2.0 + 150 + 20)/Config.TILE_SIZE, 20, "<-");
        getPopupEntities().add(backMenuButton);
    }

    @Override
    public void onClick() {

    }

    @Override
    public void onFocus() {}

    @Override
    public void outFocus() {

    }
}
