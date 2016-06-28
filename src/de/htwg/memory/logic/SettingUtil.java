package de.htwg.memory.logic;

import java.awt.Image;
import java.util.concurrent.TimeUnit;

import akka.util.Timeout;

public final class SettingUtil {
    private SettingUtil() {
    }

    private static String hiddenValue = " ? ";
    private static int numberOfCardsToMatch = 2;
	private static final Timeout TIMEOUT = new Timeout(5, TimeUnit.SECONDS);

    public static String getHiddenValue() {
        return hiddenValue;
    }

    public static Image getHiddenImage() {
        return Util.createImageFromString(getHiddenValue());
    }

    public static void setNumberOfCardsToMatch(int value) {
        numberOfCardsToMatch = value;
    }

    public static int getNumberOfCardsToMatch() {
        return numberOfCardsToMatch;
    }
    
    public static Timeout getTimeout() {
    	return TIMEOUT;
    }
}
