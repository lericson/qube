package qube.items;

import qube.*;

public class Panels
extends Item
implements Lookable
{
    Coordinate coord;

    /**
     * Construct panels lookable for coord.
     */
    public Panels(Coordinate coord)
    {
        super("panels");
        this.coord = coord;
    }

    public String getLookString()
    {
        return "(TODO: output coords of other rooms " + coord + ")";
    }
}