package qube;

import java.util.*;
import qube.items.*;

public class Game 
{
    public static final int CUBE_SIDE_LENGTH = 3;

    Parser parser;
    Coordinate coord;
    Coordinate respawn;
    int nRespawns;
    Room[][][] rooms;
    static HashMap<String, Coordinate> movements;
    static Item ominousNote = new Note("note",
        "The torn up note is scribbled with what appears to be fecal " +
        "matter. It reads:\n\n" +
        "  its in the panels . . .\n" +
        "  { x, y, z } ⊂ ℙ^α ⇒ trap");

    static {
        movements = new HashMap<String, Coordinate>();
        movements.put("east",   new Coordinate(+1, 0, 0));
        movements.put("west",   new Coordinate(-1, 0, 0));
        movements.put("south",  new Coordinate(0, +1, 0));
        movements.put("north",  new Coordinate(0, -1, 0));
        movements.put("up",     new Coordinate(0, 0, +1));
        movements.put("down",   new Coordinate(0, 0, -1));
    }

    private class GameEnded extends Throwable { }

    public Game()
    {
        this(CUBE_SIDE_LENGTH);
    }

    public Game(int n)
    {
        createCube(n);
        coord = new Coordinate(n / 2, n / 2, n / 2);
        getRoom().addItem(ominousNote);
        parser = new Parser();
    }

    public static void main(String args[])
    {
        Game g = new Game();
        g.play();
    }

    /**
     * Create a n^3 cube of rooms with appropriate data.
     */
    private void createCube(int n)
    {
        rooms = new Room[n][n][n];
        for (int z = 0; z < n; z++) {
            for (int y = 0; y < n; y++) {
                for (int x = 0; x < n; x++) {
                    Coordinate coord = new Coordinate(x, y, z);
                    Item panels = new Panels(coord);
                    Trap trap = Trap.randomTrap();
                    rooms[z][y][x] = new Room(coord, trap, panels);
                }
            }
        }
    }

    /**
     * Dump room configuration.
     */
    private void dumpRooms()
    {
        for (Room[][] roomSurface : rooms) {
            for (Room[] roomLine : roomSurface) {
                for (Room room : roomLine) {
                    UI.print(((room == null) ? "-" : (room.isTrapped() ? "T" : "S") + room.getRoomNumber()) + " ");
                }
                UI.lineFeed();
            }
            UI.lineFeed();
        }
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        while (true) {
            Command command = parser.getCommand();
            try {
                processCommand(command);
            } catch (GameEnded e) {
                break;
            }
        }
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        UI.printGameLogo();

        UI.println("-- Alderson awakens and finds himself in a cube-shaped " +
                       "room with a hatch in each wall and in the floor and " +
                       "ceiling.\n\nOpening some of the hatches, he finds " +
                       "passages to rooms that are identical except for " +
                       "their colors. He enters an orange room and, without " +
                       "warning, is sliced to pieces by a wire grill.\n\n" +
                       ". . .\n", "   ");

        UI.printPrompt("You awaken and find yourself in a cube-shaped " +
                       "room with a hatch in each wall and in the floor " +
                       "and ceiling.");

        for (String cap : getRoom().getCapabilities().values()) {
            UI.printContinue(cap);
        }
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private void processCommand(Command command) throws GameEnded
    {
        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                UI.printPrompt("I can't do that, Dave . . .");
                break;

            case HELP:
                UI.printPrompt("There is no helping in the cube.");
                break;

            case LOOK:
                lookAt(command);
                break;

            case GO:
                goRoom(command);
                break;

            case QUIT:
                throw new GameEnded();
        }
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) throws GameEnded
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            UI.printPrompt("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        Coordinate movement = movements.get(direction);

        if (movement == null) {
            UI.printPrompt(String.format("Go %s? There's no %s.", direction, direction));
            return;
        }

        respawn = coord;
        coord = coord.add(movement);

        UI.printPrompt("You crawl through the hatch . . .");

        Room room = getRoom();

        for (String d : room.getTrapDescription()) {
            UI.printContinue(d);
        }

        if (room.isTrapped()) {
            respawn();
            room = getRoom();
        }

        UI.printPrompt(room.getShortDescription());
        for (String cap : room.getCapabilities().values()) {
            UI.printContinue(cap);
        }

        respawn = coord;
    }

    private void respawn()
    {
        UI.printRespawn(++nRespawns);
        coord = respawn;
    }

    private Room getRoom()
    {
        try {
            return rooms[coord.getZ()][coord.getY()][coord.getX()];
        } catch (IndexOutOfBoundsException  e) {
            return Room.outsideCubeRoom();
        }
    }

    private void lookAt(Command command)
    {
        if (!command.hasSecondWord()) {
            UI.printPrompt("Look at what?");
            return;
        }

        String identifier = command.getSecondWord();
        Item item = getRoom().getItem(identifier);

        if (item == null) {
            UI.printPrompt("There is no " + identifier + " in the room.");
            return;
        }

        if (!(item instanceof Lookable)) {
            UI.printPrompt("You can't see how " + identifier + " looks.");
            return;
        }

        Lookable lookableItem = (Lookable)item;
        UI.printPrompt(lookableItem.getLookString());
    }
}
