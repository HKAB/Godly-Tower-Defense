package mrmathami.thegame.ui.popup;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import mrmathami.thegame.Config;
import mrmathami.thegame.entity.UIEntity;
import mrmathami.thegame.ui.popup.components.*;

import javax.swing.*;
import java.util.Collection;
import java.util.Iterator;

public class CreditPopup extends AbstractPopup {
    private double LOGO_POS_X = 202;
    private double LOGO_POS_Y = 29;
    private double TEXT_POS_X = 379;
    private double TEXT_POS_Y = 8;
    private double IMAGE_POS_Y = 213;
    private double NAME_POS_Y = 433;
    private String MESSAGE =
            "Tower Defense: The Game is a fun project, created by Team Admin \n" +
            "- a passionate dev team from Ha Noi, Viet Nam. After days of coding\n" +
            "and debuging in frustration and rage, sometimes they want to give\n" +
            "up, but with the endless passion for coding and the game itself, they\n" +
            "have overcome all the difficulties and challenges to finish the game...\n" +
            "\n" +
            "Thank you for playing!";

    public CreditPopup(long createdTick, double posX, double posY, double width, double height, StackPane stackPane) {
        super(createdTick, posX, posY, width, height, stackPane);
        // TODO: make this more easier to calculate position
        getPopupEntities().add(new PopupPane(0, posX/Config.TILE_SIZE, posY/Config.TILE_SIZE, width, height));
//        getPopupEntities().add(new ClosePopupButton(0, 0, 0, (Config.SCREEN_WIDTH - posX - 32.0)/Config.TILE_SIZE, posY/Config.TILE_SIZE));
        getPopupEntities().add(new PopupButton(0, 0, 0, (Config.SCREEN_WIDTH - posX - 30)/Config.TILE_SIZE, (posY + 10)/Config.TILE_SIZE, 20, "\ueee4"));

        PopupImage logo = new PopupImage(0, (posX + LOGO_POS_X) / Config.TILE_SIZE, (posY + LOGO_POS_Y) / Config.TILE_SIZE, "res/menu/credits/logo.png");
        getPopupEntities().add(logo);

        PopupText message = new PopupText(0, (posX + TEXT_POS_X) / Config.TILE_SIZE, (posY + TEXT_POS_Y) / Config.TILE_SIZE, 23, Color.BLACK, MESSAGE);
        getPopupEntities().add(message);

        PopupImage authorImage = null;
        PopupLabel authorName = null;

        authorImage = new PopupImage(0, (posX + width / 6) / Config.TILE_SIZE, (posY + IMAGE_POS_Y) / Config.TILE_SIZE, "res/menu/credits/ultoxtung.png");
        getPopupEntities().add(authorImage);
        authorName = new PopupLabel(0,(posX + width / 6) / Config.TILE_SIZE, (posY + NAME_POS_Y + 27) / Config.TILE_SIZE, 27, Color.BLACK, "Le Duc Tung");
        getPopupEntities().add(authorName);

        authorImage = new PopupImage(0, (posX + 3 * width / 6) / Config.TILE_SIZE, (posY + IMAGE_POS_Y) / Config.TILE_SIZE, "res/menu/credits/tacbliw.png");
        getPopupEntities().add(authorImage);
        authorName = new PopupLabel(0,(posX + 3 * width / 6) / Config.TILE_SIZE, (posY + NAME_POS_Y + 27) / Config.TILE_SIZE, 27, Color.BLACK, "Le Tran Hai Tung");
        getPopupEntities().add(authorName);

        authorImage = new PopupImage(0, (posX + 5 * width / 6) / Config.TILE_SIZE, (posY + IMAGE_POS_Y) / Config.TILE_SIZE, "res/menu/credits/hkab.png");
        getPopupEntities().add(authorImage);
        authorName = new PopupLabel(0,(posX + 5 * width / 6) / Config.TILE_SIZE, (posY + NAME_POS_Y + 27) / Config.TILE_SIZE, 27, Color.BLACK, "Nguyen Phu Truong");
        getPopupEntities().add(authorName);

        getPopupCanvas().setOnMouseClicked(mouseEvent -> {

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
