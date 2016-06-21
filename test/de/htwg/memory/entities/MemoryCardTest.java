package de.htwg.memory.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Image;

import javax.swing.ImageIcon;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MemoryCardTest {
    MemoryCard memoryCard;

    @Before
    public void setUp() throws Exception {
        memoryCard = new MemoryCard();
    }

    @Test
    public void testGetCardValue() {
        assertEquals(0, memoryCard.getCardValue());
        memoryCard.setCardValue(1);
        assertEquals(1, memoryCard.getCardValue());
        memoryCard.setCardValue(5);
        assertEquals(5, memoryCard.getCardValue());
        memoryCard.setCardValue(0);
        assertEquals(0, memoryCard.getCardValue());
    }

    @Test
    public void testGetPicture() {
        Image value1 = new ImageIcon(new byte[]{0, 1, 2, 3, 4, 5, 6, 7}).getImage();
        Image value2 = new ImageIcon(new byte[]{3, 1, 3, 2, 5, 1, 3, 7}).getImage();
        memoryCard.setCardValue(1);
        memoryCard.setSolved(true);
        assertNotNull(memoryCard.getPicture());
        memoryCard.setPicture(value1);
        assertEquals(value1, memoryCard.getPicture());
        memoryCard.setPicture(value2);
        memoryCard.setVisible(true);
        assertEquals(value2, memoryCard.getPicture());
        memoryCard.setPicture(null);
        assertNotNull(memoryCard.getPicture());
    }

    @Test
    public void testIsVisible() {
        assertFalse(memoryCard.isVisible());
        assertTrue(memoryCard.setVisible(true));
        assertFalse(memoryCard.setVisible(true));
        assertTrue(memoryCard.isVisible());
        assertTrue(memoryCard.setVisible(false));
        assertTrue(memoryCard.setVisible(false));
        assertFalse(memoryCard.isVisible());
    }

    @Test
    public void testIsSolved() {
        assertFalse(memoryCard.isSolved());
        memoryCard.setSolved(true);
        assertTrue(memoryCard.isSolved());
        memoryCard.setSolved(false);
        assertFalse(memoryCard.isSolved());
    }

    @Test
    public void testCompareTo() {
        memoryCard.setCardValue(5);
        MemoryCard less = new MemoryCard(1);
        MemoryCard same = memoryCard.clone();
        MemoryCard more = new MemoryCard(10);

        assertEquals(0, memoryCard.compareTo(memoryCard));
        assertEquals(0, memoryCard.compareTo(same));
        assertEquals(0, same.compareTo(memoryCard));
        assertTrue(memoryCard.compareTo(more) < 0);
        assertTrue(more.compareTo(memoryCard) > 0);
        assertTrue(memoryCard.compareTo(less) > 0);
        assertTrue(less.compareTo(memoryCard) < 0);
    }

    @Test
    public void testToString() {
        memoryCard.setCardValue(1);
        String n = memoryCard.toString();
        memoryCard.setVisible(true);
        String v = memoryCard.toString();
        memoryCard.setVisible(false);
        memoryCard.setSolved(true);
        String s = memoryCard.toString();
        assertFalse(n.equals(v));
        assertFalse(n.equals(s));
        assertTrue(s.equals(v));
    }

    @Test
    public void testPick() {
        MemoryCardEventListener listener = new MemoryCardEventListener() {
            @Override
            public void picked(IMemoryCard mc) {
                assertEquals(memoryCard, mc);
            }
        };
        memoryCard.addListener(listener);
        memoryCard.pick();
        memoryCard.removeListener(listener);
    }

    @After
    public void breakDown() throws Exception {

    }
}
