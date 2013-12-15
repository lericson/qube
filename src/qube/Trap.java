package qube;

/**
 * Represents a textual trap that is either set or not.
 *
 * There is a lead on text that is the same for both death and survival so
 * as to inspire tension in the test subject.
 */
public class Trap
{
    String lead, survive, death;

    public Trap(String lead, String survive, String death)
    {
        this.lead = lead;
        this.survive = survive;
        this.death = death;
    }

    /**
     * Get trap description corresponding to whether trap is set or not.
     */
    public String[] getDescription(boolean set)
    {
        String[] desc = new String[2];
        desc[0] = lead;
        desc[1] = set ? death : survive;
        return desc;
    }
}