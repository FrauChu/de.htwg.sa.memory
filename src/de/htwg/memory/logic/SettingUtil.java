package de.htwg.memory.logic;

import java.awt.Image;

public final class SettingUtil {
	private SettingUtil() { }
	
	private static String hiddenValue = " ? ";
	private static int numberOfCardsToMatch = 2;
	
	public static String getHiddenValue() {
		return hiddenValue;
	}
	public static Image getHiddenImage() {
		return Util.createImageFromString(getHiddenValue());
	}
	public static int getNumberOfCardsToMatch() {
		return numberOfCardsToMatch;
	}
}
