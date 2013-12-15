package qube.items;

public class Note
extends Item
implements Lookable
{
    String text;

    public Note(String identifier, String text)
    {
        super(identifier);
        this.text = text;
    }

    public String getLookString()
    {
        return this.text;
    }
}
