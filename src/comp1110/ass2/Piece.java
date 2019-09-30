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
            default:
                p = null;
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

    public static  PieceType placementToPieceType(String placement){
        // Use switch case
        char t = placement.charAt(0);
        switch (t){
            case 'a':
                return PieceType.a;
            case 'b':
                return PieceType.b;
            case 'c':
                return PieceType.c;
            case 'd':
                return PieceType.d;
            case 'e':
                return PieceType.e;
            case 'f':
                return PieceType.f;
            case 'g':
                return PieceType.g;
            case 'h':
                return PieceType.h;
            case 'i':
                return PieceType.i;
            case 'j':
                return PieceType.j;
            default:
                return null;

        }
    }

    //find the corresponding array of different type
    //@author Rong Hu
    public static  Colors[][] placementToPieceArray(String placement){
        // Use switch case
        char t = placement.charAt(0);
        Colors[][] tempPiece=null;
        switch (t){
            case 'a':
                tempPiece=PieceType.pieceA;
                break;
            case 'b':
                tempPiece=PieceType.pieceB;
                break;
            case 'c':
                tempPiece=PieceType.pieceC;
                break;
            case 'd':
                tempPiece=PieceType.pieceD;
                break;
            case 'e':
                tempPiece=PieceType.pieceE;
                break;
            case 'f':
                tempPiece=PieceType.pieceF;
                break;
            case 'g':
                tempPiece=PieceType.pieceG;
                break;
            case 'h':
                tempPiece=PieceType.pieceH;
                break;
            case 'i':
                tempPiece=PieceType.pieceI;
                break;
            case 'j':
                tempPiece=PieceType.pieceJ;
                break;
        }
        return tempPiece;
    }

}
