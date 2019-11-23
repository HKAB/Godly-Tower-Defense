package mrmathami.thegame.ui.ingame.context;

import mrmathami.thegame.Config;

import java.util.ArrayList;
import java.util.List;

public class ContextArea {
    private AbstractUIContext upperContext;
    private AbstractUIContext lowerContext;

    private double posX;
    private double posY;
    private double width;
    private double height;

    public ContextArea (double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
        this.width = Config.UI_CONTEXT_WIDTH;
        this.height = Config.UPPER_UI_CONTEXT_HEIGHT + Config.LOWER_UI_CONTEXT_HEIGHT;

        this.upperContext = null;
        this.lowerContext = null;
    }

    public double[] getUpperContextPos() {
        double[] pos = new double[] {posX, posY, Config.UI_CONTEXT_WIDTH, Config.UPPER_UI_CONTEXT_HEIGHT};
        return pos;
    }

    public double[] getLowerContextPos() {
        double[] pos = new double[] {posX, posY + Config.UPPER_UI_CONTEXT_HEIGHT, Config.UI_CONTEXT_WIDTH, Config.LOWER_UI_CONTEXT_HEIGHT};
        return pos;
    }

    public AbstractUIContext getUpperContext() {
        return upperContext;
    }

    public AbstractUIContext getLowerContext() {
        return lowerContext;
    }

    public void setUpperContext(AbstractUIContext upperContext) {
        this.upperContext = upperContext;
    }

    public void setLowerContext(AbstractUIContext lowerContext) {
        this.lowerContext = lowerContext;
    }

    public void updateContext (long money, long targetHealth, long currentWave, long countdown) {
        if (upperContext != null) ((NormalUIContext) upperContext).fieldUpdate(money, targetHealth, currentWave, countdown);
        if (lowerContext != null) lowerContext.setMoney(money);
    }

    public void updateMPContext (long money, long targetHealth, long enemyHealth, long currentWave, long countdown) {
        if (upperContext != null) ((MPNormalUIContext) upperContext).fieldUpdate(money, targetHealth, enemyHealth, currentWave, countdown);
        if (lowerContext != null) lowerContext.setMoney(money);
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public List<AbstractUIContext> getUIContextsList() {
        List<AbstractUIContext> UIContexts = new ArrayList<AbstractUIContext>();
        UIContexts.add(upperContext);
        UIContexts.add(lowerContext);
        return UIContexts;
    }
}
