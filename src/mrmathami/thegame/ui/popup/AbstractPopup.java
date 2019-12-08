package mrmathami.thegame.ui.popup;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import mrmathami.thegame.Config;
import mrmathami.thegame.entity.UIEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPopup implements UIEntity {
    private final long createdTick;
    private double posX;
    private double posY;
    private double width;
    private double height;
    private StackPane stackPane;
    private List<UIEntity> popupEntities;
    private Canvas popupCanvas;

    protected AbstractPopup(long createdTick, double posX, double posY, double width, double height, StackPane stackPane) {
        this.createdTick = createdTick;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.stackPane = stackPane;
        this.popupEntities = new ArrayList<>();
        this.popupCanvas = new Canvas(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        stackPane.getChildren().add(this.popupCanvas);
    }

    public List<UIEntity> getPopupEntities() {
        return popupEntities;
    }

    public Canvas getPopupCanvas() {
        return popupCanvas;
    }

    public abstract void onClick();

    @Override
    public long getCreatedTick() {
        return createdTick;
    }

    @Override
    public double getPosX() {
        return posX;
    }

    @Override
    public double getPosY() {
        return posY;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    public StackPane getStackPane() {
        return stackPane;
    }

    public void setStackPane(StackPane stackPane) {
        this.stackPane = stackPane;
    }
}
