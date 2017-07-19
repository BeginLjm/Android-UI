package com.beginlu.qqlistitem;

import android.graphics.RectF;

/**
 * Created by lujm on 17/7/18.
 */

public class QQListItemButton {
    private int backgroundColor = 0x12312312;
    private int textColor = 0xFFFFFFFF;
    private int padding = 200;
    private String text = "";
    private int textSize = 40;
    private boolean hint = false;
    protected boolean isClick = false;
    protected RectF rectF;

    public QQListItemButton() {
    }

    public QQListItemButton(int backgroundColor, int textColor, int padding, String text, int textSize, boolean hint) {
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.padding = padding;
        this.text = text;
        this.textSize = textSize;
        this.hint = hint;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isHint() {
        return hint;
    }

    public void setHint(boolean hint) {
        this.hint = hint;
    }
}
