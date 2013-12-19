package qube;

/**
 * User interface (or test subject interface)
 */
public class UI
{
    public static final int WRAP_AFTER = 74;

    public static void printGameLogo()
    {
        System.out.println(
            "                 _    _____  \n" +
            "      __ _ _   _| |__|___ /  \n" +
            "     / _` | | | | '_ \\ |_ \\  \n" +
            "    | (_| | |_| | |_) |__) | \n" +
            "     \\__, |\\__,_|_.__/____/  \n" +
            "        |_|                  \n");
    }

    private static void sleep(int milis)
    {
        if (System.getenv().containsKey("NOSLEEP")) {
            return;
        }

        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
        }
    }

    private static void printSleep(char c) {
        int milis;

        switch (c) {
            case '\n':
            case '.':
            case ':':
            case '?':
            case '!':
                milis = 200; break;
            case ',':
            case ';':
            case '-':
                milis = 120; break;
            default:
                milis = 35;
        }

        milis += (int)(milis * (0.5 - Math.random()));

        sleep(milis);
        System.out.print(c);
    }

    public static void print(String text, String indent)
    {
        int runLength = 0;
        for (char c : text.toCharArray()) {
            c = (runLength >= WRAP_AFTER && c == ' ') ? '\n' : c;
            runLength = (c != '\n') ? (runLength + 1) : 0;
            printSleep(c);
            if (c == '\n') {
                System.out.print(indent);
            }
        }
    }

    public static void lineFeed()
    {
        printSleep('\n');
    }

    public static void print(String text)
    {
        print(text, "");
    }

    public static void println(String text, String indent)
    {
        print(text, indent);
        lineFeed();
    }

    public static void println(String text)
    {
        println(text, "");
    }

    public static void printPrompt(String prompt)
    {
        println("-> " + prompt, "   ");
        lineFeed();
    }

    public static void printContinue(String prompt)
    {
        println("   " + prompt, "   ");
        lineFeed();
    }

    private static String ordinal(int n)
    {
        String[] sufixes = new String[] { "th", "st", "nd", "rd", "th",
                                          "th", "th", "th", "th", "th" };
        switch (n) {
            case  1: return "first";
            case  2: return "second";
            case  3: return "third";
            case  4: return "fourth";
            case  5: return "fifth";
            case  6: return "sixth";
            case  7: return "seventh";
            case  8: return "eighth";
            case  9: return "ninth";
            case 10: return "tenth";
            case 11: return "eleventh";
            case 12: return "twelfth";
        }
        switch (n % 100) {
            case 11:
            case 12:
            case 13: return n + "th";
            default: return n + sufixes[n % 10];
        }
    }

    public static void printRespawn(int nRespawns)
    {
        printPrompt("You die.");
        sleep(5000);
        printContinue("God has other plans for you, however.");
        if (nRespawns > 1) {
            printContinue(". . . For the " + ordinal(nRespawns) + " time.");
        }
    }
}
