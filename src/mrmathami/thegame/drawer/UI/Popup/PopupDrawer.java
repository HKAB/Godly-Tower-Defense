package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import mrmathami.thegame.Config;
import mrmathami.thegame.entity.UIEntity;
import mrmathami.thegame.ui.popup.components.ClosePopupButton;
import mrmathami.thegame.ui.popup.components.PopupPane;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PopupDrawer {
    @Nonnull private static final Map<Class<? extends UIEntity>, UIEntityDrawer> UI_POPUP_ENTITY_DRAWER_MAP = new HashMap<>(Map.ofEntries(
            Map.entry(ClosePopupButton.class, new ClosePopupButtonDrawer()),
            Map.entry(PopupPane.class, new PopupPaneDrawer())
    ));
    private GraphicsContext graphicsContext;
    private Collection<UIEntity> popupEntities;
    public PopupDrawer(@Nonnull GraphicsContext graphicsContext, @Nonnull Collection<UIEntity> popupEntities) {
        this.graphicsContext = graphicsContext;
        this.popupEntities = popupEntities;
    }

    public void render() throws FileNotFoundException {
        graphicsContext.clearRect(0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        Color bgColor = Color.rgb(178, 190, 195,0.6);
        graphicsContext.setFill(bgColor);
        graphicsContext.fillRect(0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        for (UIEntity uiEntity:
             popupEntities) {
            UI_POPUP_ENTITY_DRAWER_MAP.get(uiEntity.getClass()).draw(0, graphicsContext, uiEntity, uiEntity.getPosX()*64, uiEntity.getPosY()*64, uiEntity.getWidth(),  uiEntity.getHeight(), 0);
        }
    }
}
