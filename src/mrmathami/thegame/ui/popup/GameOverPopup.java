package mrmathami.thegame.ui.popup;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import mrmathami.thegame.Config;
import mrmathami.thegame.GameController;
import mrmathami.thegame.entity.UIEntity;
import mrmathami.thegame.ui.popup.components.PopupButton;
import mrmathami.thegame.ui.popup.components.PopupImage;
import mrmathami.thegame.ui.popup.components.PopupLabel;

import java.util.Collection;
import java.util.Iterator;

public class GameOverPopup extends AbstractPopup implements CanControlGame {
    private GameController gameController = null;
    public GameOverPopup(long createdTick, double posX, double posY, double width, double height, StackPane stackPane) {
        super(createdTick, posX, posY, width, height, stackPane);
        getPopupEntities().add(new PopupLabel(0, (Config.SCREEN_WIDTH/2.0)/Config.TILE_SIZE, (Config.SCREEN_HEIGHT/2.0 + 150)/Config.TILE_SIZE, 150, Color.BLACK, "GAME OVER"));
        getPopupEntities().add(new PopupImage(0, (Config.SCREEN_WIDTH/2.0)/Config.TILE_SIZE, (Config.SCREEN_HEIGHT/2.0 - 256)/Config.TILE_SIZE, "res/menu/sad.png"));
        PopupButton backMenuButton = new PopupButton(0, 0, 0, (Config.SCREEN_WIDTH/2.0)/Config.TILE_SIZE, (Config.SCREEN_HEIGHT/2.0 + 150 + 20)/Config.TILE_SIZE, 20, " \ueab5 ");
        getPopupEntities().add(backMenuButton);

        getPopupCanvas().setOnMouseClicked(mouseEvent -> {

            Collection<UIEntity> UIEntities = getPopupEntities();
            double mousePosX = mouseEvent.getX();
            double mousePosY = mouseEvent.getY();
            Iterator<UIEntity> iterator = UIEntities.iterator();
            while (iterator.hasNext()){
                UIEntity entity = iterator.next();
                double startX = (entity.getPosX()) * Config.TILE_SIZE;
                double startY = (entity.getPosY()) * Config.TILE_SIZE;
                double endX = startX + entity.getWidth() * Config.TILE_SIZE;
                double endY = startY + entity.getHeight() * Config.TILE_SIZE;
                if (Double.compare(mousePosX, startX) >= 0 && Double.compare(mousePosX, endX) <= 0
                        && Double.compare(mousePosY, startY) >= 0 && Double.compare(mousePosY, endY) <= 0) {
                    if (entity == backMenuButton) {
                        getStackPane().getChildren().remove(getPopupCanvas());
//                        gameController.start();
                            gameController.moveToMenuScene();
                        break;
                    }
                }
            }
        });
    }

    @Override
    public void onClick() {

    }

    @Override
    public void onFocus() {}

    @Override
    public void outFocus() {

    }


    @Override
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }
}
