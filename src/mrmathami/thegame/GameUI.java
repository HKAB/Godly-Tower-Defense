package mrmathami.thegame;

import mrmathami.thegame.bar.AbstractButton;
import mrmathami.thegame.bar.NormalButton;

import java.util.ArrayList;
import java.util.List;

public final class GameUI {
    private List<AbstractButton> buttons;

    public GameUI() {
        NormalButton button = new NormalButton(0, 150, 150, 1, 1, "button_home");
        this.buttons = new ArrayList<AbstractButton>();
        this.buttons.add(button);
    }

    public List<AbstractButton> getButtons() {
        return buttons;
    }

    public void addButton(AbstractButton button) {
        buttons.add(button);
    }
}
