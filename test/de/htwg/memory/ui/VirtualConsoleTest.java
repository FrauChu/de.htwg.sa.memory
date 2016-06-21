package de.htwg.memory.ui;

import static org.junit.Assert.*;

import javax.swing.ImageIcon;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class VirtualConsoleTest {
    VirtualConsole vc;

    @Before
    public void setUp() throws Exception {
        vc = new VirtualConsole();
    }

    @Test
    public void testPrint() {
        Object[] testObjects = new Object[]{"Hallo", new ImageIcon(), "", "Hallo", new ImageIcon()};
        vc.print(testObjects[0]);
        vc.print(testObjects[1]);
        vc.println();
        vc.println(testObjects[3]);
        vc.println(testObjects[4]);
        StringBuilder sb = new StringBuilder();
        sb.append(testObjects[0]).append(testObjects[1]).append("\n");
        sb.append(testObjects[3]).append("\n").append(testObjects[4]).append("\n");
        assertEquals(sb.toString(), vc.getCurrentText());
    }

    @Test
    public void testClear() {
        assertEquals("", vc.getCurrentText());
        vc.print("a");
        assertFalse("".equals(vc.getCurrentText()));
        vc.clear();
        assertEquals("", vc.getCurrentText());
    }

    @After
    public void tearDown() throws Exception {
        vc.kill();
    }

}
