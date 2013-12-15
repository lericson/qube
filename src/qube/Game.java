package qube;

import java.util.HashMap;
import qube.items.*;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.08.10
 */

public class Game 
{
    private Parser parser;
    private Coordinate coord;
    private Room[][][] rooms;
    private static Room outsideCubeRoom;
    private static Item ominousNote;
    private static HashMap<String, Coordinate> movements;
    static {
        outsideCubeRoom = new Room(null, new Trap(
            "You suddenly realize there is nothing " +
            "but air beneath you.",
            "By some divine intervention you begin to levitate.",
            "You fall endlessly, hurtling towards the center " +
            "of a black hole. As you reach the event horizon " +
            "you start to feel the gravitational force " +
            "differential tear your body literally to bits " + 
            "and you are smushed onto the 2D plane that is " +
            "the black hole's surface."));
        ominousNote = new Note("note", "The torn up note is scribbled with " +
                                       "what appears to be fecal matter. It " +
                                       "reads:\n\n" +
                                       "  its in the panels . . .\n" +
                                       "  { x, y, z } ⊂ ℙ ⇒ trap");
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
        this(3);
    }

    public Game(int n)
    {
        createRooms(n);
        coord = new Coordinate(n / 2, n / 2, n / 2);
        getRoom().addItem(ominousNote);
        parser = new Parser();
    }

    public static void main(String args[])
    {
        Game g = new Game();
        g.play();
    }

    private void createRooms(int n)
    {
        rooms = new Room[n][n][n];
        for (int z = 0; z < n; z++) {
            for (int y = 0; y < n; y++) {
                for (int x = 0; x < n; x++) {
                    Trap trap = new Trap("something", "went ok", "went to shit");
                    Item panels = new Note("panels", "here we would have the angränsande rooms");
                    rooms[z][y][x] = new Room(new Coordinate(x, y, z), trap, panels);
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
                    UI.printSlow(((room == null) ? "-" : (room.isTrapped() ? "T" : "S") + room.getRoomNumber()) + " ");
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
            UI.lineFeed();
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
        UI.printlnSlow("              _    _____  \n" +
                       "   __ _ _   _| |__|___ /  \n" +
                       "  / _` | | | | '_ \\ |_ \\  \n" +
                       " | (_| | |_| | |_) |__) | \n" +
                       "  \\__, |\\__,_|_.__/____/  \n" +
                       "     |_|                  \n");

        UI.printlnSlow("-- Alderson awakens and finds himself in a cube-shaped " +
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
            UI.printPrompt(cap);
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

        coord = coord.add(movement);

        UI.printPrompt("You crawl through the hatch . . .");

        Room room = getRoom();

        for (String d : room.getTrapDescription()) {
            UI.printPrompt(d);
        }

        if (room.isTrapped()) {
            UI.printPrompt("You die.");
            throw new GameEnded();
        }

        UI.printPrompt(room.getShortDescription());
        for (String cap : room.getCapabilities().values()) {
            UI.printPrompt(cap);
        }
    }

    private Room getRoom() {    
        try {
            return rooms[coord.getZ()][coord.getY()][coord.getX()];
        } catch (IndexOutOfBoundsException  e) {
            return outsideCubeRoom;
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

        if (item instanceof Lookable) {
            Lookable lookableItem = (Lookable)item;
            UI.printPrompt(lookableItem.getLookString());
        } else {
            UI.printlnSlow("cant look at it etc");
        }
    }
}
