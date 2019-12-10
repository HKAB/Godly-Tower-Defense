package mrmathami.thegame.ui.popup.components;

import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import mrmathami.thegame.entity.UIEntity;

public class PopupLabel implements UIEntity {
    private String text;
    private Color color;
    private TextAlignment textAlignment = TextAlignment.CENTER;
    private double fontSize;
    private double posX;
    private double posY;
    private double createdTick;
    public PopupLabel(long createdTick, double posX, double posY, double fontSize, Color color, String text) {
        this.posX = posX;
        this.posY = posY;
        this.text = text;
        this.fontSize = fontSize;
        this.color = color;
    }

    public PopupLabel(long createdTick, double posX, double posY, double fontSize, Color color, TextAlignment textAlignment, String text) {
        this.posX = posX;
        this.posY = posY;
        this.text = text;
        this.fontSize = fontSize;
        this.color = color;
        this.textAlignment = textAlignment;
    }

    @Override
    public long getCreatedTick() {
        return 0;
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
        return 0;
    }

    @Override
    public double getHeight() {
        return 0;
    }

    @Override
    public void onClick() {

    }

    @Override
    public void onFocus() {

    }

    @Override
    public void outFocus() {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getFontSize() {
        return fontSize;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public TextAlignment getTextAlignment() {
        return textAlignment;
    }

    public void setTextAlignment(TextAlignment textAlignment) {
        this.textAlignment = textAlignment;
    }

    public void setFontSize(double fontSize) {
        this.fontSize = fontSize;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }
}
