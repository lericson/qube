package qube;

/**
 * User interface (or test subject interface)
 */
public class UI
{
    public static final int WRAP_AFTER = 74;

    private static void printSleep(char c, int sleepMask) {
        int sleep;

        switch (c & sleepMask) {
            case '\n':
            case '.':
            case ':':
            case '?':
            case '!':
                sleep = 250; break;
            case ',':
            case ';':
                sleep = 100; break;
            default:
                sleep = 50;
        }

        sleep += (int)(sleep * (0.5 - Math.random()));

        System.out.print(c);

        if (System.getenv().containsKey("NOSLEEP")) {
            return;
        }

        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
        }
    }

    public static void printSlow(String text, String indent)
    {
        int runLength = 0;
        for (char c : text.toCharArray()) {
            c = (runLength >= WRAP_AFTER && c == ' ') ? '\n' : c;
            runLength = (c != '\n') ? (runLength + 1) : 0;
            printSleep(c, 0xff);
            if (c == '\n') {
                System.out.print(indent);
            }
        }
    }

    public static void lineFeed()
    {
        printSleep('\n', 0xff);
    }

    public static void printSlow(String text)
    {
        printSlow(text, "");
    }

    public static void printlnSlow(String text, String indent)
    {
        printSlow(text, indent);
        lineFeed();
    }

    public static void printlnSlow(String text)
    {
        printlnSlow(text, "");
    }

    public static void printPrompt(String prompt)
    {
        printlnSlow("-> " + prompt, "   ");
        lineFeed();
    }
}