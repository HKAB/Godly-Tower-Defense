package mrmathami.thegame.drawer.UI.Popup;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import mrmathami.thegame.Config;
import mrmathami.thegame.drawer.UI.Popup.Components.*;
import mrmathami.thegame.drawer.UI.UIEntityDrawer;
import mrmathami.thegame.entity.UIEntity;
import mrmathami.thegame.ui.popup.components.*;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PopupDrawer {
    @Nonnull private static final Map<Class<? extends UIEntity>, UIEntityDrawer> UI_POPUP_ENTITY_DRAWER_MAP = new HashMap<>(Map.ofEntries(
            Map.entry(PopupPane.class, new PopupPaneDrawer()),
            Map.entry(PopupLabel.class, new PopupLabelDrawer()),
            Map.entry(PopupImage.class, new PopupImageDrawer()),
            Map.entry(PopupInput.class, new PopupInputDrawer()),
            Map.entry(PopupButton.class, new PopupButtonDrawer()),
            Map.entry(PopupSlideBar.class, new PopupSlideBarDrawer()),
            Map.entry(PopupText.class, new PopupTextDrawer())
    ));
    private GraphicsContext graphicsContext;
    private Collection<UIEntity> popupEntities;
    public PopupDrawer(@Nonnull GraphicsContext graphicsContext, @Nonnull Collection<UIEntity> popupEntities) {
        this.graphicsContext = graphicsContext;
        this.popupEntities = popupEntities;
    }

    public void render() {
        graphicsContext.clearRect(0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        Color bgColor = Color.rgb(178, 190, 195,0.6);
        graphicsContext.setFill(bgColor);
        graphicsContext.fillRect(0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        for (UIEntity uiEntity: popupEntities) {
            UI_POPUP_ENTITY_DRAWER_MAP.get(uiEntity.getClass()).draw(0, graphicsContext, uiEntity,
                    uiEntity.getPosX()*Config.TILE_SIZE,
                    uiEntity.getPosY()*Config.TILE_SIZE,
                    uiEntity.getWidth(),  uiEntity.getHeight(), 0);
        }
    }
}
