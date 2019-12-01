package mrmathami.thegame.ui.popup;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import mrmathami.thegame.Config;
import mrmathami.thegame.entity.UIEntity;
import mrmathami.thegame.entity.tile.effect.AlertEffect;
import mrmathami.thegame.ui.popup.components.*;

import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.Iterator;

public class MPPopup extends AbstractPopup {

    public MPPopup(long createdTick, double posX, double posY, double width, double height, StackPane stackPane) {
        super(createdTick, posX, posY, width, height, stackPane);
        getPopupEntities().add(new PopupPane(0, posX/Config.TILE_SIZE, posY/Config.TILE_SIZE, width, height));
        PopupInput popupIPInput = new PopupInput(0, Config.SCREEN_WIDTH/2.0/Config.TILE_SIZE, (Config.SCREEN_HEIGHT/2.0 - 60)/Config.TILE_SIZE, 300, 50, 25);
        PopupInput popupPortInput = new PopupInput(0, Config.SCREEN_WIDTH/2.0/Config.TILE_SIZE, (Config.SCREEN_HEIGHT/2.0)/Config.TILE_SIZE, 300, 50, 25);
        getPopupEntities().add(new PopupLabel(0, (Config.SCREEN_WIDTH/2.0 - 150 - 40)/Config.TILE_SIZE, (Config.SCREEN_HEIGHT/2.0 - 60 + 27)/Config.TILE_SIZE, 27, "IP"));
        getPopupEntities().add(new PopupLabel(0,  (Config.SCREEN_WIDTH/2.0 - 150 - 60)/Config.TILE_SIZE, (Config.SCREEN_HEIGHT/2.0 + 27)/Config.TILE_SIZE, 30, "PORT"));
        PopupButton closeButton = new PopupButton(0, 0, 0, (Config.SCREEN_WIDTH - posX - 30)/Config.TILE_SIZE, (posY + 10)/Config.TILE_SIZE, 20, "\ueee4");
        getPopupEntities().add(closeButton);
        getPopupEntities().add(popupIPInput);
        getPopupEntities().add(popupPortInput);
        PopupButton clientButton =  new PopupButton(0, 0, 0, (Config.SCREEN_WIDTH/2.0 + 150 - 20*3/2.0 - 5)/Config.TILE_SIZE, (Config.SCREEN_HEIGHT/2.0 + 60)/Config.TILE_SIZE, 20, " \uecf9 ");
        getPopupEntities().add(clientButton);
        PopupButton serverButton = new PopupButton(0, 0, 0, (Config.SCREEN_WIDTH/2.0 + 150 - 20*3*2)/Config.TILE_SIZE, (Config.SCREEN_HEIGHT/2.0 + 60)/Config.TILE_SIZE, 20, " \uef0e ");
        getPopupEntities().add(serverButton);

        getPopupCanvas().setOnMouseClicked(mouseEvent -> {
            popupIPInput.setFocus(false);
            popupPortInput.setFocus(false);
            Collection<UIEntity> UIEntities = getPopupEntities();
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
                    if (entity instanceof PopupInput) {
                        ((PopupInput)entity).setFocus(true);
                        break;
                    }
                    //TODO: Event handle
                    if (entity instanceof PopupButton)
                    {
                        if (entity.hashCode() == clientButton.hashCode())
                        {
                            System.out.println("Connecting.....");
                        }
                        else if (entity.hashCode() == serverButton.hashCode())
                        {
                            System.out.println("Creating.....");
                        }
                        else if (entity == closeButton)
                        {
                            getStackPane().getChildren().remove(getPopupCanvas());
                        }
                    }
                }
            }
        });


        getPopupCanvas().setFocusTraversable(false);
        getPopupCanvas().requestFocus();
        getPopupCanvas().setOnKeyPressed(e -> {
            final KeyCode keyCode = e.getCode();
            PopupInput elementFocus = null;
            if (popupIPInput.isFocus())
                elementFocus = popupIPInput;
            else if (popupPortInput.isFocus())
                elementFocus = popupPortInput;
            if (elementFocus != null)
            {
                if (keyCode == KeyCode.BACK_SPACE) {
                    if (elementFocus.getText().length() > 0)
                        elementFocus.setText(elementFocus.getText().substring(0, elementFocus.getText().length() - 1));
                }
                if (elementFocus.getText().length() < 16) {
                    if (keyCode.isDigitKey() || keyCode == KeyCode.PERIOD) {
                        if (keyCode.getCode() >= 96 && keyCode.getCode() <= 105)
                            elementFocus.setText(elementFocus.getText() + Character.toString(keyCode.getCode() - 48));
                        else
                            elementFocus.setText(elementFocus.getText() + (keyCode.getChar()));
                    } else if (keyCode.getCode() == 110) {
                        elementFocus.setText(elementFocus.getText() + ".");
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
}
