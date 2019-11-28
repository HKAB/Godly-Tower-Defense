package mrmathami.thegame.ui.popup;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import mrmathami.thegame.Config;
import mrmathami.thegame.net.MPGameController;
import mrmathami.thegame.entity.UIEntity;
import mrmathami.thegame.net.MPConfig;
import mrmathami.thegame.net.MPSocketController;
import mrmathami.thegame.ui.popup.components.*;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MPPopup extends AbstractPopup {

    public MPPopup(long createdTick, double posX, double posY, double width, double height, StackPane stackPane) {
        super(createdTick, posX, posY, width, height, stackPane);
        getPopupEntities().add(new PopupPane(0, posX/Config.TILE_SIZE, posY/Config.TILE_SIZE, width, height));
        PopupInput popupIPInput = new PopupInput(0, Config.SCREEN_WIDTH/2.0/Config.TILE_SIZE, (Config.SCREEN_HEIGHT/2.0 - 60)/Config.TILE_SIZE, 300, 50, 25);
        PopupInput popupPortInput = new PopupInput(0, Config.SCREEN_WIDTH/2.0/Config.TILE_SIZE, (Config.SCREEN_HEIGHT/2.0)/Config.TILE_SIZE, 300, 50, 25);
        getPopupEntities().add(new PopupLabel(0, (Config.SCREEN_WIDTH/2.0 - 150 - 110)/Config.TILE_SIZE, (Config.SCREEN_HEIGHT/2.0 - 60 + 37)/Config.TILE_SIZE, 27, "IP ADDRESS"));
        getPopupEntities().add(new PopupLabel(0,  (Config.SCREEN_WIDTH/2.0 - 150 - 60)/Config.TILE_SIZE, (Config.SCREEN_HEIGHT/2.0 + 37)/Config.TILE_SIZE, 30, "PORT"));
        PopupButton closeButton = new PopupButton(0, 0, 0, (Config.SCREEN_WIDTH - posX - 30)/Config.TILE_SIZE, (posY + 10)/Config.TILE_SIZE, 20, "\ueee4");
        getPopupEntities().add(closeButton);
        getPopupEntities().add(popupIPInput);
        getPopupEntities().add(popupPortInput);
        popupIPInput.setText(MPConfig.DEFAULT_SERVER_HOST);
        popupPortInput.setText(Integer.toString(MPConfig.DEFAULT_LISTEN_PORT));
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
                    if (entity instanceof PopupButton) {
                        if (entity.hashCode() == clientButton.hashCode()) {
                            System.out.println("Connecting.....");
                            String address = popupIPInput.getText().toLowerCase();
                            if (isNumeric(popupPortInput.getText())) {
                                int port = Integer.parseInt(popupPortInput.getText());
                                if (isValidAddress(address) && isValidPort(port)) {
                                    try {
                                        MPSocketController.setCurrentInstance(new MPSocketController(address, port));
                                        moveToMPScene();
                                    } catch (IOException e) {
                                        System.out.println("Failed to connect to the remote host");
                                        System.exit(1);
                                    }
                                }
                            } else {
                                System.out.println("Invalid host or port");
                            }
                        }
                        else if (entity.hashCode() == serverButton.hashCode()) {
                            System.out.println("Creating.....");
                            if (isNumeric(popupPortInput.getText())) {
                                int port = Integer.parseInt(popupPortInput.getText());
                                if (isValidPort(port)) {
                                    try {
                                        MPSocketController.setCurrentInstance(new MPSocketController(port));
                                        moveToMPScene();
                                    } catch (IOException e) {
                                        System.out.println("Failed to listen on 127.0.0.1:" + port);
                                        System.exit(1);
                                    }
                                } else {
                                    System.out.println("Invalid port number");
                                }
                            } else {
                                System.out.println("Invalid port number");
                            }
                        }
                        else if (entity == closeButton) {
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
            gameController = new MPGameController(graphicsContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        gameCanvas.setFocusTraversable(false);
        gameCanvas.setOnMouseClicked(gameController::mouseClickHandler);
        gameCanvas.setOnMouseMoved(gameController::mouseMoveHandler);
        gameCanvas.setOnKeyPressed(gameController::keyDownHandler);
        super.getStackPane().getChildren().add(gameCanvas);
        gameController.start();
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
