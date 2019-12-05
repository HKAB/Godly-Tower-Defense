package mrmathami.thegame.ui.popup;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import mrmathami.thegame.Config;
import mrmathami.thegame.GameController;
import mrmathami.thegame.ui.popup.components.PopupImage;
import mrmathami.thegame.ui.popup.components.PopupLabel;

public class AfterCreditPopup extends AbstractPopup implements CanControlGame{
    GameController gameController = null;
    public AfterCreditPopup(long createdTick, double posX, double posY, double width, double height, StackPane stackPane) {
        super(createdTick, posX, posY, width, height, stackPane);
        PopupLabel afterCreditText = new PopupLabel(0,
                (Config.SCREEN_WIDTH/2.0)/Config.TILE_SIZE,
                (Config.SCREEN_HEIGHT/2.0 + 150)/Config.TILE_SIZE,
                50,
                Color.rgb(0, 0, 0),
                "Life is more fun if you play games\n\nAuthor\nNguyen Phu Truong\nLe Tran Hai Tung\nLe Duc Tung\n\nWith the help of\nKenney.nl\nflaticon.com\nfreesound.org\n\n");
        PopupImage patreonImage = new PopupImage(0, (Config.SCREEN_WIDTH/2.0)/Config.TILE_SIZE, 1250.0/Config.TILE_SIZE, "res/stage/patreot.png");
        PopupImage scrollImage = new PopupImage(0, (Config.SCREEN_WIDTH - 32.0)/Config.TILE_SIZE, 10.0/Config.TILE_SIZE, "res/stage/scroll.png");
        getPopupEntities().add(patreonImage);
        getPopupEntities().add(scrollImage);
        getPopupEntities().add(afterCreditText);
        PopupLabel afterCreditText1 = new PopupLabel(0,
                (Config.SCREEN_WIDTH/2.0)/Config.TILE_SIZE,
                (1250.0 + patreonImage.getHeight() + 50 + 10)/Config.TILE_SIZE,
                50,
                Color.rgb(0, 0, 0),
                "https://www.patreon.com/hkab\n\n\nThank YOU for playing!");
        getPopupEntities().add(afterCreditText1);

        getPopupCanvas().setOnScroll(event -> {
            afterCreditText.setPosY(afterCreditText.getPosY() + event.getDeltaY()*0.005);
            patreonImage.setPosY(patreonImage.getPosY() + event.getDeltaY()*0.005);
            afterCreditText1.setPosY(afterCreditText1.getPosY() + event.getDeltaY()*0.005);
            if (afterCreditText1.getPosY() < (Config.SCREEN_HEIGHT - 50.0)/Config.TILE_SIZE) {
                scrollImage.setImage("res/stage/esc.png");
            } else {
                scrollImage.setImage("res/stage/scroll.png");
            }
        });

        getPopupCanvas().setFocusTraversable(false);
        getPopupCanvas().requestFocus();
        getPopupCanvas().setOnKeyPressed(e -> {
            final KeyCode keyCode = e.getCode();
            if (keyCode == KeyCode.ESCAPE)
            {
                getStackPane().getChildren().remove(getPopupCanvas());
                gameController.moveToMenuScene();
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
