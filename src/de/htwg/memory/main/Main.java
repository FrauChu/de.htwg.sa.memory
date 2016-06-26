package de.htwg.memory.main;

import java.io.File;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.htwg.memory.logic.IController;
import de.htwg.memory.ui.GUI;
import de.htwg.memory.ui.TUI;

public final class Main {
    private static Injector injector = Guice.createInjector(new MainModule());
    private static IController controller = null;

    private Main() { }

    public static void main(String[] args) {
        IController c = getControllerInstance();
        c.loadStaticBoard(5, 4);
        c.resetGame();

        boolean someUiStartet = false;

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.equalsIgnoreCase("-t") || arg.equalsIgnoreCase("--tui")) {
                new TUI();
                someUiStartet = true;
            } else if (arg.equalsIgnoreCase("-g") || arg.equalsIgnoreCase("--gui")) {
                new GUI();
                someUiStartet = true;
            } else if (arg.equalsIgnoreCase("-b") || arg.equalsIgnoreCase("--board")) {
                if (args.length > i + 2) {
                    int width = Integer.parseInt(args[++i]);
                    int height = Integer.parseInt(args[++i]);
                    c.loadStaticBoard(width, height);
                } else {
                    System.out.println("Error! " + arg + " needs 2 parameters. Width and height.");
                }
            } else if (arg.equalsIgnoreCase("-l") || arg.equalsIgnoreCase("--load")) {
                if (args.length > i + 1) {
                    File toLoad = new File(args[++i]);
                    c.loadBoard(toLoad);
                } else {
                    System.out.println("Error! " + arg + " needs a parameter. Path of game-file.");
                }
            }
        }

        if (!someUiStartet) {
            new TUI();
        }
    }

    public static IController getControllerInstance() {
        if (controller == null)
            controller = injector.getInstance(IController.class);

        return controller;
    }
}
