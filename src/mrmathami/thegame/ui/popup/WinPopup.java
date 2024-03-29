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

public class WinPopup extends AbstractPopup implements CanControlGame {
    GameController gameController = null;
    public WinPopup(long createdTick, double posX, double posY, double width, double height, StackPane stackPane) {
        super(createdTick, posX, posY, width, height, stackPane);
        getPopupComponents().add(new PopupLabel(0,
                (width/2.0)/Config.TILE_SIZE,
                (height/2.0 + 125)/Config.TILE_SIZE,
                100, Color.BLACK, "WIN"));
        getPopupComponents().add(new PopupImage(0,
                (width/2.0)/Config.TILE_SIZE,
                (height/2.0 - 256)/Config.TILE_SIZE,
                "res/stage/popup/images/sleepy.png"));
        PopupButton nextButton = new PopupButton(0, 0, 0,
                (width/2.0)/Config.TILE_SIZE,
                (height/2.0 + 150 + 20)/Config.TILE_SIZE,
                20, " \ueab8 ");
        getPopupComponents().add(nextButton);

        getPopupCanvas().setOnMouseClicked(mouseEvent -> {
            Collection<UIEntity> UIEntities = getPopupComponents();
            double mousePosX = mouseEvent.getX();
            double mousePosY = mouseEvent.getY();
            Iterator<UIEntity> iterator = UIEntities.iterator();
            while (iterator.hasNext()){
                UIEntity entity = iterator.next();
                double startX = (entity.getPosX()) * Config.TILE_SIZE;
                double startY = (entity.getPosY()) * Config.TILE_SIZE;
                double endX = startX + entity.getWidth();
                double endY = startY + entity.getHeight();
                if (Double.compare(mousePosX, startX) >= 0 && Double.compare(mousePosX, endX) <= 0
                        && Double.compare(mousePosY, startY) >= 0 && Double.compare(mousePosY, endY) <= 0) {
                    //TODO: Event handle
                    if (entity instanceof PopupButton)
                    {
                        if (entity == nextButton)
                        {
                            getStackPane().getChildren().remove(getPopupCanvas());
                            this.gameController.nextMap();
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
    public void onFocus() {}

    @Override
    public void outFocus() {

    }

    @Override
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }
}
