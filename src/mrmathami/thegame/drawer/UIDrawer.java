package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import mrmathami.thegame.Config;
import mrmathami.thegame.MenuUI;
import mrmathami.thegame.bar.NormalButton;
import mrmathami.thegame.entity.UIEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class UIDrawer {
    /**
     * Map each entity type with corresponding drawer.
     */
    @Nonnull private static final Map<Class<? extends UIEntity>, UIEntityDrawer> UI_ENTITY_DRAWER_MAP = new HashMap<> (Map.ofEntries(
        Map.entry(NormalButton.class, new ButtonDrawer())
    ));

    @Nonnull private final GraphicsContext graphicsContext;
    @Nonnull private MenuUI menuUI;
    private transient double fieldStartPosX = Float.NaN;
    private transient double fieldStartPosY = Float.NaN;
    private transient double fieldZoom = Float.NaN;

    public UIDrawer(@Nonnull GraphicsContext graphicsContext, @Nonnull MenuUI menuUI) {
        this.graphicsContext = graphicsContext;
        this.menuUI = menuUI;
    }

    @Nullable
    private static UIEntityDrawer getUIEntityDrawer(@Nonnull UIEntity entity) {
        return UI_ENTITY_DRAWER_MAP.get(entity.getClass());
    }

    public final double getFieldStartPosX() {
        return fieldStartPosX;
    }

    public final double getFieldStartPosY() {
        return fieldStartPosY;
    }

    public final double getFieldZoom() {
        return fieldZoom;
    }

    public void setMenuUI(@Nonnull MenuUI menuUI) {
        this.menuUI = menuUI;
    }

    public final void setFieldViewRegion(double fieldStartPosX, double fieldStartPosY, double fieldZoom) {
        this.fieldStartPosX = fieldStartPosX;
        this.fieldStartPosY = fieldStartPosY;
        this.fieldZoom = fieldZoom;
    }

    public final void render() throws FileNotFoundException {
        final MenuUI menuUI = this.menuUI;
        final double fieldZoom = this.fieldZoom;
        final double fieldStartPosX = this.fieldStartPosX;
        final double fieldStartPosY = this.fieldStartPosY;
        final List<UIEntity> entities = new ArrayList<>(menuUI.getEntities());

        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0.0, 0.0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);

        for (UIEntity e : entities) {
            final UIEntityDrawer drawer = getUIEntityDrawer(e);
            if (drawer != null) {
                drawer.draw(0, graphicsContext, e, e.getPosX(), e.getPosY(), e.getHeight(), e.getWidth(), fieldZoom);
            }
        }
    }
}
