package mrmathami.thegame.ui.popup;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import mrmathami.thegame.Config;
import mrmathami.thegame.audio.GameAudio;
import mrmathami.thegame.entity.UIEntity;
import mrmathami.thegame.ui.popup.components.*;

import java.util.Collection;
import java.util.Iterator;

public class SettingPopup extends AbstractPopup {
    public SettingPopup(long createdTick, double posX, double posY, double width, double height, StackPane stackPane) {
        super(createdTick, posX, posY, width, height, stackPane);
        // TODO: make this more easier to calculate position
        double padding = 10;
        getPopupComponents().add(new PopupPane(0, posX/Config.TILE_SIZE, posY/Config.TILE_SIZE, width, height));
        //                                                                                                                 30 here is size of button
        PopupButton closeButton = new PopupButton(0, 0, 0, (Config.SCREEN_WIDTH - posX - 30)/Config.TILE_SIZE, (posY + padding)/Config.TILE_SIZE, 20, "\ueee4");
        getPopupComponents().add(closeButton);
        getPopupComponents().add(new PopupLabel(0, (Config.SCREEN_WIDTH/2.0)/Config.TILE_SIZE, (posY + 80)/Config.TILE_SIZE, 42, Color.rgb(0, 0, 0), "SETTING"));

        getPopupComponents().add(new PopupLabel(0, (posX + width/3)/Config.TILE_SIZE, (posY + height/3)/Config.TILE_SIZE, 30, Color.rgb(0, 0, 0), "Music"));
        PopupButton volumeDownButton = new PopupButton(0, 0, 0, (posX + width/3 + 150/2 + padding)/Config.TILE_SIZE, (posY + height/3 - 30)/Config.TILE_SIZE, 20, "\uef99");
        getPopupComponents().add(volumeDownButton);
        PopupSlideBar popupSlideBar = new PopupSlideBar((posX + width/3 + 150/2 + padding + 30 + padding)/Config.TILE_SIZE, (posY + height/3 - 30)/Config.TILE_SIZE, 300, 40, GameAudio.getInstance().getMainVolume()*100);
        getPopupComponents().add(popupSlideBar);
        PopupButton volumeUpButton = new PopupButton(0, 0, 0, (posX + width/3 + 150/2 + padding + 30 + padding + 300 + 30 + padding)/Config.TILE_SIZE, (posY + height/3 - 30)/Config.TILE_SIZE, 20, "\uefc1");
        getPopupComponents().add(volumeUpButton);
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
                    if (entity == closeButton) {
                        getStackPane().getChildren().remove(getPopupCanvas());
                        break;
                    }
                    else if (entity == volumeUpButton)
                    {
                        popupSlideBar.setInitialValue(popupSlideBar.getInitialValue() + 10);
                        GameAudio.getInstance().setMainVolume(popupSlideBar.getInitialValue()/100);
                    }
                    else if (entity == volumeDownButton)
                    {
                        popupSlideBar.setInitialValue(popupSlideBar.getInitialValue() - 10);
                        GameAudio.getInstance().setMainVolume(popupSlideBar.getInitialValue()/100);
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
