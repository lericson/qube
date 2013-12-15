package qube;

import java.util.*;
import qube.items.*;

/**
 * Class Room - a room in an adventure game.
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.08.10
 */

public class Room 
{
    private Coordinate coord;
    private Trap trap;
    private HashMap<String, Item> items;

    private static int[] primePowers = { 1, 2, 3, 4, 5, 7, 8, 9, 11, 13, 16,
                                         17, 21, 23, 25, 27, 29, 31, 32, 41,
                                         49, 64, 81, 121, 125, 128, 169, 243 };

    private boolean isPrimePower(int i)
    {
        return 0 <= Arrays.binarySearch(primePowers, getRoomNumber());
    }

    public Room(Coordinate coord, Trap trap, Iterable<Item> items) 
    {
        this.coord = coord;
        this.trap = trap;
        this.items = new HashMap<String, Item>();
        for (Item item : items) {
            addItem(item);
        }
    }

    public Room(Coordinate coord, Trap trap, Item... items)
    {
        this(coord, trap, Arrays.asList(items));
    }

    public static Room outsideCubeRoom()
    {
        return new Room(null, Trap.gravityTrap());
    }

    public boolean isTrapped()
    {
        if (coord == null) {
            return true;
        }

        return !isPrimePower(getRoomNumber());
    }

    public int getRoomNumber() {
        return (coord.getX() << 4)
             | (coord.getY() << 2)
             | (coord.getZ() << 0);
    }

    public String[] getTrapDescription() {
        return trap.getDescription(isTrapped());
    }

    public void addItem(Item item)
    {
        items.put(item.getIdentifier(), item);
    }

    public Item removeItem(String identifier)
    {
        return items.remove(identifier);
    }

    public Item getItem(String identifier)
    {
        return items.get(identifier);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return "You are in a cube-shaped room.";
    }

    private String getLookables()
    {
        String r = "";
        for (Item item : items.values()) {
            if (item instanceof Lookable) {
                r += item.getIdentifier().toUpperCase() + ", ";
            }
        }
        return r.substring(0, r.length() - 2);
    }

    /**
     * @return An array of capability descriptions of the room.
     */
    public Map<String, String> getCapabilities()
    {
        HashMap<String, String> caps = new HashMap<String, String>();
        caps.put("go", "You can GO through hatchways EAST, WEST, NORTH, " +
                                                    "SOUTH, UP, DOWN.");
        caps.put("look", "You can LOOK at " + getLookables() + " in this room.");
        return caps;
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        // FIXME
        return "(all exits)";
        /*
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
        */
    }

    public Coordinate getCoordinate() {
        return coord;
    }
}

