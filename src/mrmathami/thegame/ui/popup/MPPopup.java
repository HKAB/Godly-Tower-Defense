package mrmathami.thegame.ui.popup;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import mrmathami.thegame.Config;
import mrmathami.thegame.net.MPGameController;
import mrmathami.thegame.entity.UIEntity;
import mrmathami.thegame.net.MPConfig;
import mrmathami.thegame.net.MPSocketController;
import mrmathami.thegame.ui.popup.components.*;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;

public class MPPopup extends AbstractPopup {

    private PopupLabel messageLabel = null;
    public MPPopup(long createdTick, double posX, double posY, double width, double height, StackPane stackPane) {
        super(createdTick, posX, posY, width, height, stackPane);
        getPopupComponents().add(new PopupPane(0,
                posX/Config.TILE_SIZE,
                posY/Config.TILE_SIZE, width, height));
        getPopupComponents().add(new PopupLabel(0,
                (Config.SCREEN_WIDTH/2.0)/Config.TILE_SIZE,
                (Config.SCREEN_HEIGHT/2.0 - 160)/Config.TILE_SIZE,
                42, Color.BLACK, "MULTI-PLAYER"));
        // START: Input field
        getPopupComponents().add(new PopupLabel(0,
                (Config.SCREEN_WIDTH/2.0 - 150 - 155)/Config.TILE_SIZE,
                (Config.SCREEN_HEIGHT/2.0 - 60 + 10)/Config.TILE_SIZE,
                27, Color.BLACK, TextAlignment.LEFT, "ADDRESS"));
        getPopupComponents().add(new PopupLabel(0,
                (Config.SCREEN_WIDTH/2.0 - 150 - 155)/Config.TILE_SIZE,
                (Config.SCREEN_HEIGHT/2.0 + 10)/Config.TILE_SIZE,
                30, Color.BLACK, TextAlignment.LEFT, "PORT"));
        PopupInput popupAddressInput = new PopupInput(0,
                Config.SCREEN_WIDTH/2.0/Config.TILE_SIZE,
                (Config.SCREEN_HEIGHT/2.0 - 60 - 27)/Config.TILE_SIZE,
                300, 50, 25);
        PopupInput popupPortInput = new PopupInput(0,
                Config.SCREEN_WIDTH/2.0/Config.TILE_SIZE,
                (Config.SCREEN_HEIGHT/2.0 - 27)/Config.TILE_SIZE,
                300, 50, 25);
        PopupButton clientButton =  new PopupButton(0, 0, 0,
                (Config.SCREEN_WIDTH/2.0 + 150 - 20*3/2.0 - 5)/Config.TILE_SIZE,
                (Config.SCREEN_HEIGHT/2.0 + 60 - 10)/Config.TILE_SIZE,
                20, " \uecf9 ");
        PopupButton serverButton = new PopupButton(0, 0, 0,
                (Config.SCREEN_WIDTH/2.0 + 150 - 20*3*2)/Config.TILE_SIZE,
                (Config.SCREEN_HEIGHT/2.0 + 60 - 10)/Config.TILE_SIZE,
                20, " \uef0e ");
        // END: Input field
        PopupButton closeButton = new PopupButton(0, 0, 0,
                (Config.SCREEN_WIDTH - posX - 30)/Config.TILE_SIZE,
                (posY + 10)/Config.TILE_SIZE, 20,
                "\ueee4");
        popupAddressInput.setText(MPConfig.DEFAULT_SERVER_HOST);
        popupPortInput.setText(Integer.toString(MPConfig.DEFAULT_LISTEN_PORT));
        getPopupComponents().add(closeButton);
        getPopupComponents().add(popupAddressInput);
        getPopupComponents().add(popupPortInput);
        getPopupComponents().add(clientButton);
        getPopupComponents().add(serverButton);

        getPopupCanvas().setOnMouseClicked(mouseEvent -> {
            popupAddressInput.setFocus(false);
            popupPortInput.setFocus(false);
            // To avoid ConcurrentModificationException caused by adding error message.
            CopyOnWriteArrayList<UIEntity> UIEntities = new CopyOnWriteArrayList<>(getPopupComponents());;
            double mousePosX = mouseEvent.getX();
            double mousePosY = mouseEvent.getY();
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
                        if (entity.hashCode() == clientButton.hashCode()) {
                            String address = popupAddressInput.getText().toLowerCase();
                            if (isNumeric(popupPortInput.getText())) {
                                int port = Integer.parseInt(popupPortInput.getText());
                                try {
                                    if (isValidPort(port)) {
                                        if (isValidAddress(address)) {
                                            try {
                                                MPSocketController.setCurrentInstance(new MPSocketController(address, port));
                                                moveToMPScene();
                                            } catch (IOException e) {
                                                showErrorMessage("Failed to connect to the remote host");
                                            }
                                        } else {
                                            showErrorMessage("Invalid address");
                                        }
                                    } else {
                                        showErrorMessage("Invalid port number");
                                    }
                                } catch (NumberFormatException e) {
                                    showErrorMessage("Invalid port number");
                                }
                            } else {
                                showErrorMessage("Invalid port number");
                            }
                        } else if (entity.hashCode() == serverButton.hashCode()) {
                            if (isNumeric(popupPortInput.getText())) {
                                int port = Integer.parseInt(popupPortInput.getText());
                                try {
                                    if (isValidPort(port)) {
                                        try {
                                            MPSocketController.setCurrentInstance(new MPSocketController(port));
                                            moveToMPScene();
                                        } catch (IOException e) {
                                            showErrorMessage("Port already in use");
                                        }
                                    } else {
                                        showErrorMessage("Invalid port number");
                                    }
                                } catch (NumberFormatException e) {
                                    showErrorMessage("Invalid port number");
                                }
                            } else {
                                showErrorMessage("Invalid port number");
                            }
                        } else if (entity == closeButton) {
                            backToMain();
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
            if (popupAddressInput.isFocus())
                elementFocus = popupAddressInput;
            else if (popupPortInput.isFocus())
                elementFocus = popupPortInput;
            if (elementFocus != null)
            {
                if (keyCode == KeyCode.BACK_SPACE) {
                    if (elementFocus.getText().length() > 0)
                        elementFocus.setText(elementFocus.getText().substring(0, elementFocus.getText().length() - 1));
                }
                if (elementFocus.getText().length() < 16) {
                    if (keyCode.isDigitKey() || keyCode == KeyCode.PERIOD || keyCode.isLetterKey()) {
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

    private void backToMain() {
        getStackPane().getChildren().remove(getPopupCanvas());
    }

    private void moveToMPScene() {
        getStackPane().getChildren().remove(getPopupCanvas());
        Canvas gameCanvas = new Canvas(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        GraphicsContext graphicsContext = gameCanvas.getGraphicsContext2D();
        MPGameController gameController = null;
        try {
            gameController = new MPGameController(graphicsContext, getStackPane());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        gameCanvas.setFocusTraversable(true);
        gameCanvas.setOnMouseClicked(gameController::mouseClickHandler);
        gameCanvas.setOnMouseMoved(gameController::mouseMoveHandler);
        gameCanvas.setOnKeyPressed(gameController::keyDownHandler);
        getStackPane().getChildren().add(gameCanvas);
        gameController.start();
    }

    private void showErrorMessage(String text) {
        if (this.messageLabel == null) {
            this.messageLabel = new PopupLabel(0,
                    (Config.SCREEN_WIDTH/2.0)/Config.TILE_SIZE,
                    (Config.SCREEN_HEIGHT/2.0 + 180)/Config.TILE_SIZE,
                    27, Color.RED, text);
            getPopupComponents().add(this.messageLabel);
            return;
        }
        this.messageLabel.setColor(Color.RED);
        this.messageLabel.setText(text);
    }

    private void showNotificationMessage(String text) {
        if (this.messageLabel == null) {
            this.messageLabel = new PopupLabel(0,
                    (Config.SCREEN_WIDTH/2.0)/Config.TILE_SIZE,
                    (Config.SCREEN_HEIGHT/2.0 + 180)/Config.TILE_SIZE,
                    27, Color.DODGERBLUE, text);
            getPopupComponents().add(this.messageLabel);
            return;
        }
        this.messageLabel.setColor(Color.DODGERBLUE);
        this.messageLabel.setText(text);
    }

    private void clearMessage() {
        if (this.messageLabel != null) {
            this.messageLabel.setText("");
        }
    }

    @Override
    public void onClick() { }

    @Override
    public void onFocus() { }

    @Override
    public void outFocus() { }

    private boolean isNumeric(String strNum) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    private boolean isValidAddress(String address) {
        // We allow all the hosts so no need to check
        return true;
    }

    private boolean isValidPort(int port) {
        return port > 0 && port < 65536;
    }
}
