package qube;

/**
 * An immutable 3D coordinate in the game world.
 */
public class Coordinate
{
    int x, y, z;

    Coordinate(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Coordinate add(Coordinate other) {
        return new Coordinate(this.x + other.getX(),
                              this.y + other.getY(),
                              this.z + other.getZ());
    }

    public String toString() {
        return Integer.toBinaryString(x) + " " +
               Integer.toBinaryString(y) + " " +
               Integer.toBinaryString(z);
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getZ() { return z; }
}