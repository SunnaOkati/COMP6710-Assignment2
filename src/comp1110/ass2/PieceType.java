package comp1110.ass2;

import static comp1110.ass2.Colors.*;

public enum PieceType {
    a,b,c,d,e,f,g,h,i,j;

    /**
     * Return the state for a given board position given x and y offsets
     * from the pieces's location and its orientation.  If the position is
     * not covered by the tile, return null.
     *
     * The following indices reflect the four rotations of a piece.   The top
     * left represents the piece location.   The integer indicates the index
     * into the state array for the tile.
     *
     * [Later]
     *
     * @param xoff The x-offset from the tile's location
     * @param yoff The y-offset from the tile's location
     * @param orientation The orientation of the piece
     * @return The state at the given position given the orientation, or null if
     * not covered by the piece.
     */

    private static Colors[][] statemap = {
    };

    public Colors stateFromOffset(int xoff, int yoff, Orientation orientation){

        return null;
    }
}
