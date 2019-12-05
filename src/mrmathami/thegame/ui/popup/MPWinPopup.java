package mrmathami.thegame.ui.popup;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import mrmathami.thegame.Config;
import mrmathami.thegame.GameController;
import mrmathami.thegame.entity.UIEntity;
import mrmathami.thegame.net.MPGameController;
import mrmathami.thegame.ui.popup.components.PopupButton;
import mrmathami.thegame.ui.popup.components.PopupImage;
import mrmathami.thegame.ui.popup.components.PopupInput;
import mrmathami.thegame.ui.popup.components.PopupLabel;

import java.util.Collection;
import java.util.Iterator;

public class MPWinPopup extends AbstractPopup implements CanControlGame {
    private MPGameController mpGameController;
    public MPWinPopup(long createdTick, double posX, double posY, double width, double height, StackPane stackPane) {
        super(createdTick, posX, posY, width, height, stackPane);
        PopupButton backButton = new PopupButton(0, 0, 0, (width/2.0)/Config.TILE_SIZE, (height/2.0 + 150 + 20)/Config.TILE_SIZE, 20, " \ueab8 ");
        getPopupEntities().add(new PopupLabel(0, (width/2.0)/Config.TILE_SIZE, (height/2.0 + 125)/Config.TILE_SIZE, 100, Color.BLACK, "WIN"));
        getPopupEntities().add(new PopupImage(0, (width/2.0)/Config.TILE_SIZE, (height/2.0 - 256)/Config.TILE_SIZE, "res/menu/sleepy.png"));
        getPopupEntities().add(backButton);

        getPopupCanvas().setOnMouseClicked(mouseEvent -> {
            double mousePosX = mouseEvent.getX();
            double mousePosY = mouseEvent.getY();
            Collection<UIEntity> UIEntities = getPopupEntities();
            for (UIEntity entity : UIEntities) {
                double startX = (entity.getPosX()) * Config.TILE_SIZE;
                double startY = (entity.getPosY()) * Config.TILE_SIZE;
                double endX = startX + entity.getWidth();
                double endY = startY + entity.getHeight();
                if (Double.compare(mousePosX, startX) >= 0 && Double.compare(mousePosX, endX) <= 0
                        && Double.compare(mousePosY, startY) >= 0 && Double.compare(mousePosY, endY) <= 0) {
                    if (entity instanceof PopupInput) {
                        ((PopupInput) entity).setFocus(true);
                        break;
                    }
                    if (entity instanceof PopupButton) {
                        if (entity.hashCode() == backButton.hashCode()) {
                            getStackPane().getChildren().remove(getPopupCanvas());
                            mpGameController.moveToMenuScene();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onClick() {

    }

    @Override
    public void onFocus() {

    }

    @Override
    public void outFocus() {

    }

    @Override
    public void setGameController(GameController gameController) {

    }

    public void setGameController(MPGameController gameController) {
        this.mpGameController = gameController;
    }
}
