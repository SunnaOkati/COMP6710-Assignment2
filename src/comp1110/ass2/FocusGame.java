package comp1110.ass2;

import java.util.Set;

/**
 * This class provides the text interface for the IQ Focus Game
 * <p>
 * The game is based directly on Smart Games' IQ-Focus game
 * (https://www.smartgames.eu/uk/one-player-games/iq-focus)
 */
public class FocusGame {

    /**
     * Determine whether a piece placement is well-formed according to the
     * following criteria:
     * - it consists of exactly four characters
     * - the first character is in the range a .. j (shape)
     * - the second character is in the range 0 .. 8 (column)
     * - the third character is in the range 0 .. 4 (row)
     * - the fourth character is in the range 0 .. 3 (orientation)
     *
     * @param piecePlacement A string describing a piece placement
     * @return True if the piece placement is well-formed
     */
    static boolean isPiecePlacementWellFormed(String piecePlacement) {
        // FIXME Task 2: determine whether a piece placement is well-formed
        
        /* Seperate 4 characters from "piecePlacement" by using String.charAt() and check whether
         each lie within the above specified limits.
        */
        if (piecePlacement.length() != 4)
            return false;

        Location location = Piece.placementToLocation(piecePlacement);
        Orientation orientation = Piece.placementToOrientation(piecePlacement);
        PieceType type = Piece.placementToPieceType(piecePlacement);

        if ((type != null) && (location.getX() >= 0) && (location.getX() < 9) && (location.getY() >= 0) && (location.getY() < 5) && (orientation != null)) {
            return true;
        }
        return false;
    }

    /**
     * Determine whether a placement string is well-formed:
     * - it consists of exactly N four-character piece placements (where N = 1 .. 10);
     * - each piece placement is well-formed
     * - no shape appears more than once in the placement
     *
     * @param placement A string describing a placement of one or more pieces
     * @return True if the placement is well-formed
     */
    public static boolean isPlacementStringWellFormed(String placement) {
        // FIXME Task 3: determine whether a placement is well-formed
        /*
        Iterating throughout the string, it can be pass every four characters through isPiecePlacementWellFormed()
            to check if they are all well formed to begin with

        After that, we can utilise .charAt() to check that every fourth character is within the range of a-j
            Simultaneously, we will store these .charAt() values in a seperate string named "dupeCheck"

        Iterating through dupeCheck, we can see if there are any duplicated pieces present

        return true if all the above criteria is met

         */

        String dupCheck = "";
        if (placement.length() % 4 == 0 && placement.length() != 0) {
            for (int i = 0; i < placement.length(); i = i + 4) {
                // Check whether the substrings are well formed
                if (!isPiecePlacementWellFormed(placement.substring(i, i + 4))) {
                    return false;
                } else {
                    // Redundancy check
                    for (int j = 0; j < dupCheck.length(); j++) {
                        if (dupCheck.charAt(j) == placement.substring(i, i + 4).charAt(0))
                            return false;
                    }
                    dupCheck += placement.substring(i, i + 4).charAt(0);
                }
            }
            return true;
        }

        return false;
    }

    /**
     * Determine whether a placement string is valid.
     *
     * To be valid, the placement string must be:
     * - well-formed, and
     * - each piece placement must be a valid placement according to the
     * rules of the game:
     * - pieces must be entirely on the board
     * - pieces must not overlap each other
     *
     * @param placement A placement string
     * @return True if the placement sequence is valid
     */


    public static Colors[][] pieceA = {
            {Colors.GREEN, Colors.WHITE, Colors.RED},
            {null, Colors.RED, null}
    };
    public static Colors[][] pieceB = {
            {null, Colors.BLUE, Colors.GREEN, Colors.GREEN},
            {Colors.WHITE, Colors.WHITE, null, null}
    };
    public static Colors[][] pieceC = {
            {null, null, Colors.GREEN, null},
            {Colors.RED, Colors.RED, Colors.WHITE, Colors.BLUE}
    };
    public static Colors[][] pieceD = {
            {Colors.RED, Colors.RED, Colors.RED},
            {null, null, Colors.BLUE}
    };
    public static Colors[][] pieceE = {
            {Colors.BLUE, Colors.BLUE, Colors.BLUE},
            {null, Colors.RED, Colors.RED}
    };
    public static Colors[][] pieceF = {
            {Colors.WHITE, Colors.WHITE, Colors.WHITE}
    };
    public static Colors[][] pieceG = {
            {Colors.WHITE, Colors.BLUE, null},
            {null, Colors.BLUE, Colors.WHITE}
    };
    public static Colors[][] pieceH = {
            {Colors.RED, Colors.GREEN, Colors.GREEN},
            {Colors.WHITE, null, null},
            {Colors.WHITE, null, null}
    };
    public static Colors[][] pieceI = {
            {Colors.BLUE, Colors.BLUE},
            {null, Colors.WHITE}
    };
    public static Colors[][] pieceJ = {
            {Colors.GREEN, Colors.GREEN, Colors.WHITE, Colors.RED},
            {Colors.GREEN, null, null, null}
    };


    public static boolean isPlacementStringValid(String placement) {
        // FIXME Task 5: determine whether a placement string is valid
        
        /*
        To be valid it has to satisfy two conditions
        1) Use isPlacementStringWellFormed(), we can say whether "placement" is well formed
        2) Rules:
            a) Find possible boxes(co-ordinates) for a particular piece placement and check whether 0 <= row < 5 and 0 <= column < 10.
            b) Use the boardstates which contains the colors associated with each square(block) 
            and verify that current set of placement co-ordinates doesn't have any color associated with it already.
        */

        /*
        Board is set up here for the function, this should
        be initialised somewhere else as the project progresses
        Potentially it's own Class/Enum in the future.
        */

        Colors[][] initialBoard = {
                //Every value begins as null because it's an empty board state
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}

        };

        // If the placement string shouldn't even exist in the first place.
        if (!isPlacementStringWellFormed(placement)) {
            return false;
        }


        //Just an arbitrarily chosen default starting piece for creating the variable
        //      Colors[][] piece

        Colors[][] piece = pieceA;
        int p = 0;
        int q = 4;
        String currentPiece = "";
        // For every piece in the string placement
        for (int pieceCounter = 0; pieceCounter < (placement.length() / 4); pieceCounter++) {

            // This extracts singular pieces out of placement at a time
            if (isPiecePlacementWellFormed(placement.substring(p, q))) {
                currentPiece = placement.substring(p, q);
            }

            // The neatest way we could think of to designate the piece type
            switch (currentPiece.charAt(0)) {
                case 'a':
                    piece = pieceA;
                case 'b':
                    piece = pieceB;
                case 'c':
                    piece = pieceC;
                case 'd':
                    piece = pieceD;
                case 'e':
                    piece = pieceE;
                case 'f':
                    piece = pieceF;
                case 'g':
                    piece = pieceG;
                case 'h':
                    piece = pieceH;
                case 'i':
                    piece = pieceI;
                case 'j':
                    piece = pieceJ;
            }

            // These will be replaced by getX & getY in future
            // x & y are for the board
            // a & b are for the piece

            int x = Character.getNumericValue(currentPiece.charAt(1));
            int y = Character.getNumericValue(currentPiece.charAt(2));
            int a = 0;
            int b = 0;

            // Iterates vertically through the board
            for (int i = 0; i < piece[1].length; i++) {
                // If that location on the board has previously been written to
                // This also catches on the colors.BLACK tiles
                // This check happens multiple times.
                if (initialBoard[x][y] != null) {
                    return false;
                }

                initialBoard[x][y] = piece[i][b];

                // Nested for loop that iterates sideways through the board
                for (int j = 0; j < piece[0].length; j++) {
                    if (initialBoard[x][y] != null) {
                        return false;
                    }
                    initialBoard[x][y] = piece[a][j];
                    y++; // Ensures that a new horizontal board co-ord is being accessed each loop
                }

                x++; // Ensures that a new vertical board co-ord is being accessed each loop

            }


            // These coOrds exist as null for the board, but are the indented, non existent corners in the actual board
            if (initialBoard[4][0] != null || initialBoard[4][9] != null) {
                return false;
            }



            p = p + 4;
            q = q + 4;

        }

        return true;
    }


    /**
     * Given a string describing a placement of pieces and a string describing
     * a challenge, return a set of all possible next viable piece placements
     * which cover a specific board cell.
     *
     * For a piece placement to be viable
     * - it must be valid
     * - it must be consistent with the challenge
     *
     * @param placement A viable placement string
     * @param challenge The game's challenge is represented as a 9-character string
     *                  which represents the color of the 3*3 central board area
     *                  squares indexed as follows:
     *                  [0] [1] [2]
     *                  [3] [4] [5]
     *                  [6] [7] [8]
     *                  each character may be any of
     *                  - 'R' = RED square
     *                  - 'B' = Blue square
     *                  - 'G' = Green square
     *                  - 'W' = White square
     * @param col       The cell's column.
     * @param row       The cell's row.
     * @return A set of viable piece placements, or null if there are none.
     */
    static Set<String> getViablePiecePlacements(String placement, String challenge, int col, int row) {
        // FIXME Task 6: determine the set of all viable piece placements given existing placements and a challenge
        /*
            Regarding the placement string, if it results in having pieces that satisfy the challenge then allow that
                placement

            Checking each individual piece for each of its tiles to see if they're within the 3*3 challenge square
                will be sufficient for this function

            Along with checking the range of each tile, this function will also check the colour of the tiles within
                the 3*3 range to see if they're the correctly coloured tiles.

            To tidy this method up and for documentation purposes, there will be a method to see each individual
                pieces tiles, and another function to see match that against the challenge square.

            The first method may have to be hardcoded, the second method will check the just to see if the correct
                colours are present, with respect to the challenge.
         */
        return null;
    }

    /**
     * Return the canonical encoding of the solution to a particular challenge.
     *
     * A given challenge can only solved with a single placement of pieces.
     *
     * Since some piece placements can be described two ways (due to symmetry),
     * you need to use a canonical encoding of the placement, which means you
     * must:
     * - Order the placement sequence by piece IDs
     * - If a piece exhibits rotational symmetry, only return the lowest
     * orientation value (0 or 1)
     *
     * @param challenge A challenge string.
     * @return A placement string describing a canonical encoding of the solution to
     * the challenge.
     */
    public static String getSolution(String challenge) {
        // FIXME Task 9: determine the solution to the game, given a particular challenge
        /*
            The methods from task 5 & 6 will be used within this task, as they can check whether certain strings will
                be suitable for the board & the challenge respectively.

            Right now, with a limited understanding of the nuance of the game, the current approach is to brute force

            Brute forcing can be done by using task 5 to find all the string placements that are allowed to exist on
                the board, these strings can then be stored in a separate file under "all viable strings"

            All of the strings from "all viable strings" can then be input into the methods from task 6 to find
                the one string that satisfies the challenge. This will become the output for task 9.

         */
        return null;
    }
}
