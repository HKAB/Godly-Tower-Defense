package mrmathami.thegame.ui.popup;


import mrmathami.thegame.GameController;

public interface CanControlGame {
    // This game controller can be either GameController or MPGameController so I'll leave it as an Object,
    // remember to type cast it to what you want
    void setGameController(GameController gameController);
}
