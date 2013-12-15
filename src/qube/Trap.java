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

    public static Trap gravityTrap()
    {
        return new Trap(
            "You suddenly realize there is nothing " +
            "but air beneath you . . .",
            "By some divine intervention you begin to levitate.",
            "You fall endlessly, hurtling towards the center " +
            "of a black hole. As you reach the event horizon " +
            "you start to feel the gravitational force " +
            "differential tear your body literally to bits " + 
            "and you are smushed onto the 2D plane that is " +
            "the black hole's surface."));
    }

    public static List<Trap> defaultTraps() {
        traps = new ArrayList<Trap>();
        traps.add(new Trap("A foul odour enters your nostrils . . .",
                           "But you realize you forgot to close your mouth.",
                           "You begin to lose consciousness."));
        traps.add(new Trap("A paradox enters your train of thought . . .",
                           "But you realize how meaningless it all is.",
                           "This statement is false."));
        return traps;
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