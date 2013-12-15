# QuB3

## Grejer för framtiden

- Roterande rum
- Bryggan
- Taket?

## Mål

Kom till rum 00 00 00, gå till negativ

## Koordinatsystem

Tillämpa högerhandsregeln!

- Positiv x-riktning är öster
- Positiv y-riktning är norr
- Positiv z-riktning är uppåt

## Så här kan det se ut

                 _    _____
      __ _ _   _| |__|___ /
     / _` | | | | '_ \ |_ \
    | (_| | |_| | |_) |__) |
     \__, |\__,_|_.__/____/
        |_|

    -> You awaken and find yourself in a cube-shaped room with a hatch in each of the
       four walls, as well as the floor and the ceiling.

    -> You can open hatchways to the east, west, north, south, above, below.

    -> You can look at the panels in the room.

    <- look panels

    // User is in room 10 01 10
    // Coordinates are zz yy xx

    -> Panel on east hatchway: 10 01 11
                west hatchway: 10 01 01
               north hatchway: 10 00 10
               south hatchway: 10 10 10
               above hatchway: 01 01 10
               below hatchway: 11 01 10

    <- open east

    -> You open the hatch.
       The panel in the hatchway reads 100110.
       The room ahead is colored blue.

     -> go east

    <- You crawl through the hatch . . .
       As you step onto the floor,
       a chainsaw rips you apart.

       You die.

    grid(4):
    [[000000, 000001, 000010, 000011],
     [000100, 000101, 000110, 000111],
     [001000, 001001, 001010, 001011],
     [001100, 001101, 001110, 001111]],
    [[010000, 010001, 010010, 010011],
     [010100, 010101, 010110, 010111],
     [011000, 011001, 011010, 011011],
     [011100, 011101, 011110, 011111]],
    [[100000, 100001, 100010, 100011],
     [100100, 100101, 100110, 100111],
     [101000, 101001, 101010, 101011],
     [101100, 101101, 101110, 101111]],
    [[110000, 110001, 110010, 110011],
     [110100, 110101, 110110, 110111],
     [111000, 111001, 111010, 111011],
     [111100, 111101, 111110, 111111]]

    ```python
    >>> list(sorted({p**i for p in (1, 2, 3, 5, 7, 11, 13, 17, 21, 23, 29, 31, 41) for i in xrange(1, 10)}))[:30]
    [1, 2, 3, 4, 5, 7, 8, 9, 11, 13, 16, 17, 21, 23, 25, 27, 29, 31, 32, 41, 49, 64, 81, 121, 125, 128, 169, 243, 256, 289]
    ```

A man named Alderson (Julian Richings) awakens and finds himself in a cube-shaped room with a hatch in each wall and in the floor and ceiling. Opening some of the hatches, he finds passages to rooms that are identical except for their colors. He enters an orange room and, without warning, is sliced to pieces by a wire grill.

In another such room, five people - Quentin, Worth, Holloway, Rennes and Leaven - meet. None know where they are, how they got there, or why. Quentin, having almost been killed by one, informs them that some cubes contain traps. Assuming they are triggered by motion detectors, Rennes tests each by throwing his boot in first. Leaven notices numbers inscribed in the passageways between rooms. Quentin, a policeman, recognizes "the Wren" as an escape artist renowned for getting out of jails. After "booting" one room, Rennes enters but is sprayed by acid, which dissolves his face and kills him. They realize that there are different kinds of motion detectors, and Quentin deduces that this motion detector was triggered on heat.
