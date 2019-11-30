package mrmathami.thegame.ui.popup;

import javafx.scene.layout.StackPane;
import mrmathami.thegame.Config;
import mrmathami.thegame.entity.UIEntity;
import mrmathami.thegame.ui.popup.components.*;

import java.util.Collection;
import java.util.Iterator;

public class CreditPopup extends AbstractPopup {
    public CreditPopup(long createdTick, double posX, double posY, double width, double height, StackPane stackPane) {
        super(createdTick, posX, posY, width, height, stackPane);
        // TODO: make this more easier to calculate position
        getPopupEntities().add(new PopupPane(0, posX/Config.TILE_SIZE, posY/Config.TILE_SIZE, width, height));
//        getPopupEntities().add(new ClosePopupButton(0, 0, 0, (Config.SCREEN_WIDTH - posX - 32.0)/Config.TILE_SIZE, posY/Config.TILE_SIZE));
        getPopupEntities().add(new PopupButton(0, 0, 0, (Config.SCREEN_WIDTH - posX - 30)/Config.TILE_SIZE, (posY + 10)/Config.TILE_SIZE, 20, "\ueee4"));

        PopupImage author1 = new PopupImage(0, (posX + width/6)/Config.TILE_SIZE, (posY + 64)/Config.TILE_SIZE, "res/menu/ductung.jpg");
        getPopupEntities().add(author1);
        getPopupEntities().add(new PopupLabel(0,(posX + width/6)/Config.TILE_SIZE, (posY + 64 + author1.getHeight() + 27)/Config.TILE_SIZE, 27, "Le Duc Tung"));
        PopupImage badgeAuthor1 = new PopupImage(0, (posX + width/6)/Config.TILE_SIZE, (posY + 64 + author1.getHeight() + 27 + 10)/Config.TILE_SIZE, "res/menu/badge.png");
        getPopupEntities().add(badgeAuthor1);

        PopupImage author2 = new PopupImage(0, (posX + width/2)/Config.TILE_SIZE, (posY + 64)/Config.TILE_SIZE, "res/menu/bh.jpg");
        getPopupEntities().add(author2);
        getPopupEntities().add(new PopupLabel(0,(posX + width/2)/Config.TILE_SIZE, (posY + 64 + author2.getHeight() + 27)/Config.TILE_SIZE, 27, "Le Tran Hai Tung"));
        PopupImage badgeAuthor2 = new PopupImage(0, (posX + width/2)/Config.TILE_SIZE, (posY + 64 + author2.getHeight() + 27 + 10)/Config.TILE_SIZE, "res/menu/badge.png");
        getPopupEntities().add(badgeAuthor2);

        PopupImage author3 = new PopupImage(0, (posX + 5*width/6)/Config.TILE_SIZE, (posY + 64)/Config.TILE_SIZE, "res/menu/truong.jpg");
        getPopupEntities().add(author3);
        getPopupEntities().add(new PopupLabel(0,(posX + 5*width/6)/Config.TILE_SIZE, (posY + 64 + author3.getHeight() + 27)/Config.TILE_SIZE, 27, "Nguyen Phu Truong"));
        PopupImage badgeAuthor3 = new PopupImage(0, (posX + 5*width/6)/Config.TILE_SIZE, (posY + 64 + author3.getHeight() + 27 + 10)/Config.TILE_SIZE, "res/menu/badge.png");
        getPopupEntities().add(badgeAuthor3);

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
                    if (entity instanceof PopupButton) {
                        getStackPane().getChildren().remove(getPopupCanvas());
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
    public void onFocus() {

    }

    @Override
    public void outFocus() {

    }
}
