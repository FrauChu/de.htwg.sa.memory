package de.htwg.memory.logic;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.Test;

public class SettingUtilTest {
    @Test
    public void testGetHiddenValue() {
        assertEquals(" ? ", SettingUtil.getHiddenValue());
    }

    @Test
    public void testGetNumberOfCardsToMatch() {
        assertEquals(3, SettingUtil.getNumberOfCardsToMatch());
    }

    @Test
    public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<SettingUtil> constructor = SettingUtil.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
