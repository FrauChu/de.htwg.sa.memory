package de.htwg.memory.entities;

import java.awt.Color;
import java.awt.Image;
import java.util.LinkedList;
import java.util.List;

import de.htwg.memory.logic.SettingUtil;
import de.htwg.memory.logic.Util;

public class MemoryCard implements Comparable<MemoryCard>, Cloneable, IMemoryCard {
    private int cardValue;
    private Image picture;
    private boolean visible;
    private boolean solved;
    private List<MemoryCardEventListener> listeners = new LinkedList<>();

    public MemoryCard() {
        this(0);
    }

    public MemoryCard(int cardValue) {
        this(cardValue, null);
    }

    public MemoryCard(int cardValue, Image picture) {
        this.cardValue = cardValue;
        this.picture = picture;
        this.visible = false;
        this.solved = false;
    }

    public void setCardValue(int value) {
        this.cardValue = value;
    }

    public int getCardValue() {
        return this.cardValue;
    }

    public void setPicture(Image value) {
        this.picture = value;
    }

    public Image getPicture() {
        if (isVisible() || isSolved()) {
            if (picture == null)
                return Util.createImageFromString(this.toString(), new Color(0x00FF0000, true), Color.BLACK);
            return picture;
        } else
            return SettingUtil.getHiddenImage();
    }

    public boolean isVisible() {
        return visible;
    }

    @Override
    public boolean setVisible(boolean visible) {
        if (visible && !this.visible && !this.solved) {
            this.visible = visible;
            return true;
        } else if (visible) {
            return false;
        }
        this.visible = visible;
        return true;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public void addListener(MemoryCardEventListener listener) {
        listeners.add(listener);
    }

    public void removeListener(MemoryCardEventListener listener) {
        listeners.remove(listener);
    }

    public void pick() {
        for (final MemoryCardEventListener listener : listeners) {
            listener.picked(this);
        }
    }

    @Override
    public int compareTo(MemoryCard o) {
        return this.getCardValue() - o.getCardValue();
    }

    @Override
    public MemoryCard clone() {
        return new MemoryCard(cardValue, picture);
    }

    @Override
    public String toString() {
        if (cardValue == 0)
            return " X ";
        if (visible || solved) {
            return " " + String.valueOf(cardValue) + " ";
        }
        return SettingUtil.getHiddenValue();
    }
}