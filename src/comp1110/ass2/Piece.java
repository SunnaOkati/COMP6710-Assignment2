package comp1110.ass2;

public class Piece {

    private PieceType pieceType;         // Which tile type is it (a ... f)
    private Location location;         // The tile's current location on board
    private Orientation orientation;   // The tile's current orientation

    public Piece(String placement) {
        this.pieceType = PieceType.valueOf(Character.toString((placement.charAt(0) - 32)));
        this.orientation = placementToOrientation(placement);
        this.location = placementToLocation(placement);
    }

    public Location getLocation() {
        return location;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public PieceType getTileType() {
        return pieceType;
    }

    /**
     * Given a four-character tile placement string, decode the piece's orientation.
     * <p>
     * You will need to read the description of the encoding in the class `Objective`.
     *
     * @param placement A string representing the placement of a piece on the game board
     * @return A value of type `Orientation` corresponding to the piece's orientation on board
     */

    public static Orientation placementToOrientation(String placement) {
        //Used switch case
        char orientation = placement.charAt(3);
        Orientation p = Orientation.ZERO;
        switch (orientation) {
            case '0':
                p = Orientation.ZERO;
                break;
            case '1':
                p = Orientation.ONE;
                break;
            case '2':
                p = Orientation.TWO;
                break;
            case '3':
                p = Orientation.THREE;
                break;
        }
        return p;
    }

    /**
     * Given a four-character piece placement string, decode the piece's location.
     * <p>
     * You will need to read the description of the encoding in the class `Objective'
     *
     * @param placement A string representing the placement of a piece on the game board
     * @return A value of type `Location` corresponding to the piece's location on the board
     */

    public static Location placementToLocation(String placement) {
        // Separated characters(co-ordinates) from string "placement"
        int x = Character.getNumericValue(placement.charAt(1));
        int y = Character.getNumericValue(placement.charAt(2));
        return new Location(x, y);
    }

}
