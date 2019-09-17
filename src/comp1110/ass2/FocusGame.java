package comp1110.ass2;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

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


    public static boolean isPlacementStringValid(String placement) {
        // FIXME Task 5: determine whether a placement string is valid
        if (isPlacementStringWellFormed(placement)==false){
            return false;
        }
        boolean sig=true;
        //verify whether pieces are entirely on the board
        for (int i = 0; i < placement.length(); i = i + 4) {
            char type=placement.charAt(i);
            int x=(int)placement.charAt(i+1)-48;
            int y=(int)placement.charAt(i+2)-48;
            int orientation=placement.charAt(i+3)-48;
            switch (type){
                case 'a':
                    if (orientation==0){
                        if (x>6||y>3){
                            sig=false;
                        }
                    }else if (orientation==2){
                        if (x>6||y>3||(x==0&&y==3||x==6&&y==3)){
                            sig=false;
                        }
                    }else if (orientation==1){
                        if (x==8||(x==7&&y==2)||y>2){
                            sig=false;
                        }
                    }else {
                        if (x==8||(x==0&&y==2)||y>2){
                            sig=false;
                        }
                    }
                    break;
                case 'b':
                    if (orientation==0||orientation==2){
                        if (x>5||y>3){
                            sig=false;
                        }
                    } else if (orientation==1||orientation==3){
                        if (x>7||y>1||(x==7&&y==1)){
                            sig=false;
                        }
                    }
                    break;
                case 'c':
                    if (orientation==0){
                        if (x>5||y>3||(x==0&&y==3)||(x==5&&y==3)){
                            sig=false;
                        }
                    } else if (orientation==2){
                        if (x>5||y>3){
                            sig=false;
                        }
                    } else if (orientation==1){
                        if (x>7||y>1||(x==0&&y==1)){
                            sig=false;
                        }
                    }else if(orientation==3){
                        if (x>7||y>1||(x==7&&y==1)){
                            sig=false;
                        }
                    }
                    break;
                case 'd':
                    if (orientation==0){
                        if (x>6||y>3||(x==6&&y==3)){
                            sig=false;
                        }
                    }else if (orientation==2){
                        if (x>6||y>3||(x==6&&y==3)||(x==0&&y==3)){
                            sig=false;
                        }
                    }else if (orientation==1){
                        if (x>7||y>2||(x==0&&y==2)||(x==7&&y==2)){
                            sig=false;
                        }
                    }else if (orientation==3){
                        if (x>7||y>2||(x==0&&y==2)){
                            sig=false;
                        }
                    }
                    break;
                case 'e':
                    if (orientation==0){
                        if (x>6||y>3||(x==0&&y==3)){
                            sig=false;
                        }
                    }else if (orientation==2){
                        if (x>6||y>3||(x==0&&y==3)||(x==6&&y==3)){
                            sig=false;
                        }
                    }else if (orientation==1){
                        if (x>7||y>2||(x==7&&y==2)){
                            sig=false;
                        }
                    }else if (orientation==3){
                    if (x>7||y>2||(x==7&&y==2)||(x==0&&y==2)){
                        sig=false;
                    }
                }
                    break;
                case 'f':
                    if (orientation==0||orientation==2){
                        if (x>6||(x==0&&y==4)||(x==6&&y==4)){
                            sig=false;
                        }
                    }else if (orientation==1||orientation==3){
                        if (y>2||(x==0&&y==2)||(x==8&&y==2)){
                            sig=false;
                        }
                    }
                    break;
                case 'g':
                    if (orientation==0||orientation==2){
                        if (x>6||y>3||(x==6&&y==3)){
                            sig=false;
                        }
                    }else if (orientation==1||orientation==3){
                        if (x>7||y>2||(x==0&&y==2)){
                            sig=false;
                        }
                    }
                    break;
                case 'h':
                    if (orientation==0){
                        if (x>6||y>2||(x==0&&y==2)){
                            sig=false;
                        }
                    }else if (orientation==2||orientation==3){
                        if (x>6||y>2||(x==0&&y==2)||(x==6&&y==2)){
                            sig=false;
                        }
                    }else if (orientation==1){
                        if (x>6||y>2||(x==6&&y==2)){
                            sig=false;
                        }
                    }
                    break;
                case 'i':
                    if (orientation==0){
                        if (x>7||y>3||(x==7&&y==3)){
                            sig=false;
                        }
                    }else if (orientation==2||orientation==1){
                        if (x>7||y>3||(x==7&&y==3)||(x==0&&y==3)){
                            sig=false;
                        }
                    }else if (orientation==3){
                        if (x>7||y>3||(x==0&&y==3)){
                            sig=false;
                        }
                    }
                    break;
                case 'j':
                    if (orientation==0){
                        if (x>5||y>3||(x==0&&y==3)){
                            sig=false;
                        }
                    }else if (orientation==2){
                        if (x>5||y>3||(x==0&&y==3)||(x==5&&y==3)){
                            sig=false;
                        }
                    }else if (orientation==1){
                        if (x>7||y>1||(x==7&&y==1)){
                            sig=false;
                        }
                    }else if (orientation==3){
                        if (x>7||y>1||(x==7&&y==1)||(x==0&&y==1)){
                            sig=false;
                        }
                    }
                    break;
            }
        }

        if (sig==false){
            return false;
        }

        boolean sig2=true;
        Colors[][] boardState = new Colors[5][9];

        //verify whether pieces are overlap
        for (int i = 0; i < placement.length(); i = i + 4) {
            char type=placement.charAt(i);
            int x=(int)placement.charAt(i+1)-48;
            int y=(int)placement.charAt(i+2)-48;
            int orientation=placement.charAt(i+3)-48;
            int length;
            int width;

            switch (type){
                case 'a':
                    length=PieceType.pieceA[0].length;
                    width=PieceType.pieceA.length;
                    //System.out.println(width);
                    for (int j=0;j<width;j++){
                        for (int k=0;k<length;k++){
                            Location rotateLoc=PieceType.rotateXY(k,j,length,width,orientation);
                            //System.out.println("x"+(x+rotateLoc.getX())+"y"+(y+rotateLoc.getY()));
                            if (PieceType.pieceA[j][k]!=null && boardState[y+rotateLoc.getY()][x+rotateLoc.getX()]!=null){
                                sig2=false;
                            }else if (PieceType.pieceA[j][k]!=null){
                                boardState[y+rotateLoc.getY()][x+rotateLoc.getX()]=PieceType.pieceA[j][k];
                            }
                        }
                    }
                    break;
                case 'b':
                    length=PieceType.pieceB[0].length;
                    width=PieceType.pieceB.length;
                    for (int j=0;j<width;j++){
                        for (int k=0;k<length;k++){
                            Location rotateLoc=PieceType.rotateXY(k,j,length,width,orientation);
                            if (PieceType.pieceB[j][k]!=null && boardState[y+rotateLoc.getY()][x+rotateLoc.getX()]!=null){
                                sig2=false;
                            }else if (PieceType.pieceB[j][k]!=null){
                                boardState[y+rotateLoc.getY()][x+rotateLoc.getX()]=PieceType.pieceB[j][k];
                            }
                        }
                    }
                    break;
                case 'c':
                    length=PieceType.pieceC[0].length;
                    width=PieceType.pieceC.length;
                    for (int j=0;j<width;j++){
                        for (int k=0;k<length;k++){
                            Location rotateLoc=PieceType.rotateXY(k,j,length,width,orientation);
                            if (PieceType.pieceC[j][k]!=null && boardState[y+rotateLoc.getY()][x+rotateLoc.getX()]!=null){
                                sig2=false;
                            }else if (PieceType.pieceC[j][k]!=null){
                                boardState[y+rotateLoc.getY()][x+rotateLoc.getX()]=PieceType.pieceC[j][k];
                            }
                        }
                    }
                    break;
                case 'd':
                    length=PieceType.pieceD[0].length;
                    width=PieceType.pieceD.length;
                    for (int j=0;j<width;j++){
                        for (int k=0;k<length;k++){
                            Location rotateLoc=PieceType.rotateXY(k,j,length,width,orientation);
                            if (PieceType.pieceD[j][k]!=null && boardState[y+rotateLoc.getY()][x+rotateLoc.getX()]!=null){
                                sig2=false;
                            }else if (PieceType.pieceD[j][k]!=null){
                                boardState[y+rotateLoc.getY()][x+rotateLoc.getX()]=PieceType.pieceD[j][k];
                            }
                        }
                    }
                    break;
                case 'e':
                    length=PieceType.pieceE[0].length;
                    width=PieceType.pieceE.length;
                    for (int j=0;j<width;j++){
                        for (int k=0;k<length;k++){
                            Location rotateLoc=PieceType.rotateXY(k,j,length,width,orientation);
                            if (PieceType.pieceE[j][k]!=null && boardState[y+rotateLoc.getY()][x+rotateLoc.getX()]!=null){
                                sig2=false;
                            }else if (PieceType.pieceE[j][k]!=null){
                                boardState[y+rotateLoc.getY()][x+rotateLoc.getX()]=PieceType.pieceE[j][k];
                            }
                        }
                    }
                    break;
                case 'f':
                    length=PieceType.pieceF[0].length;
                    width=PieceType.pieceF.length;
                    for (int j=0;j<width;j++){
                        for (int k=0;k<length;k++){
                            Location rotateLoc=PieceType.rotateXY(k,j,length,width,orientation);
                            if (PieceType.pieceF[j][k]!=null && boardState[y+rotateLoc.getY()][x+rotateLoc.getX()]!=null){
                                sig2=false;
                            }else if (PieceType.pieceF[j][k]!=null){
                                boardState[y+rotateLoc.getY()][x+rotateLoc.getX()]=PieceType.pieceF[j][k];
                            }
                        }
                    }
                    break;
                case 'g':
                    length=PieceType.pieceG[0].length;
                    width=PieceType.pieceG.length;
                    for (int j=0;j<width;j++){
                        for (int k=0;k<length;k++){
                            Location rotateLoc=PieceType.rotateXY(k,j,length,width,orientation);
                            if (PieceType.pieceG[j][k]!=null && boardState[y+rotateLoc.getY()][x+rotateLoc.getX()]!=null){
                                sig2=false;
                            }else if (PieceType.pieceG[j][k]!=null){
                                boardState[y+rotateLoc.getY()][x+rotateLoc.getX()]=PieceType.pieceG[j][k];
                            }
                        }
                    }
                    break;
                case 'h':
                    length=PieceType.pieceH[0].length;
                    width=PieceType.pieceH.length;
                    for (int j=0;j<width;j++){
                        for (int k=0;k<length;k++){
                            Location rotateLoc=PieceType.rotateXY(k,j,length,width,orientation);
                            if (PieceType.pieceH[j][k]!=null && boardState[y+rotateLoc.getY()][x+rotateLoc.getX()]!=null){
                                sig2=false;
                            }else if (PieceType.pieceH[j][k]!=null){
                                boardState[y+rotateLoc.getY()][x+rotateLoc.getX()]=PieceType.pieceH[j][k];
                            }
                        }
                    }
                    break;
                case 'i':
                    length=PieceType.pieceI[0].length;
                    width=PieceType.pieceI.length;
                    for (int j=0;j<width;j++){
                        for (int k=0;k<length;k++){
                            Location rotateLoc=PieceType.rotateXY(k,j,length,width,orientation);
                            if (PieceType.pieceI[j][k]!=null && boardState[y+rotateLoc.getY()][x+rotateLoc.getX()]!=null){
                                sig2=false;
                            }else if (PieceType.pieceI[j][k]!=null){
                                boardState[y+rotateLoc.getY()][x+rotateLoc.getX()]=PieceType.pieceI[j][k];
                            }
                        }
                    }
                    break;
                case 'j':
                    length=PieceType.pieceJ[0].length;
                    width=PieceType.pieceJ.length;
                    for (int j=0;j<width;j++){
                        for (int k=0;k<length;k++){
                            Location rotateLoc=PieceType.rotateXY(k,j,length,width,orientation);
                            if (PieceType.pieceJ[j][k]!=null && boardState[y+rotateLoc.getY()][x+rotateLoc.getX()]!=null){
                                sig2=false;
                            }else if (PieceType.pieceJ[j][k]!=null){
                                boardState[y+rotateLoc.getY()][x+rotateLoc.getX()]=PieceType.pieceJ[j][k];
                            }
                        }
                    }
                    break;
            }
        }

        if (sig2==false){
            return false;
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
        Colors[][] boardState = new Colors[5][9];
        //fill the challenge
        int count=0;
        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                Colors constrain=Colors.getColors(challenge.charAt(count));
                count++;
                boardState[1+i][3+j]=constrain;
            }
        }
        Set<String> viablePiece=new HashSet<String>();;

        for (int t=0;t<10;t++){
            for (int x=0;x<9;x++){
                for (int y=0;y<5;y++){
                    for (int d=0;d<4;d++){
                        String tempPlace=""+((char)(t+97))+x+y+d;
                        String newPlacement=placement.concat(tempPlace);
                        //System.out.println(newPlacement);
                        if (isPlacementStringValid(newPlacement)==false){
                            continue;
                        }
                        Colors[][] tempPiece=null;
                        switch ((char)(t+97)){
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
                        int length=tempPiece[0].length;
                        int width=tempPiece.length;
                        boolean sig=true;
                        boolean sig2=false;
                        for (int j=0;j<width;j++){
                            for (int k=0;k<length;k++){
                                Location rotateLoc=PieceType.rotateXY(k,j,length,width,d);
                                if ((y+rotateLoc.getY()<=3 && y+rotateLoc.getY()>=1)&&(x+rotateLoc.getX()<=5 && x+rotateLoc.getX()>=3)){
                                    if (boardState[y+rotateLoc.getY()][x+rotateLoc.getX()]!=tempPiece[j][k]&& tempPiece[j][k]!=null){
                                        sig=false;
                                    }
                                }
                                if (y+rotateLoc.getY()==row && x+rotateLoc.getX()==col && tempPiece[j][k]!=null){
                                    sig2=true;
                                }
                            }
                        }
                        if (sig==true && sig2==true){
                            viablePiece.add(tempPlace);
                        }
                    }
                }
            }
        }

        if (viablePiece.isEmpty()==true){
            return null;
        }
        return viablePiece;
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
        Colors[][] boardState = new Colors[5][9];
        String placement="";
        for (int j=0;j<9;j++){
            for (int i=0;i<5;i++){
                //find the placement of current coordinate
                Set<String> viablePiece=getViablePiecePlacements(placement,challenge,j,i);
                if (viablePiece==null){
                    continue;
                }
                Iterator<String> iter=viablePiece.iterator();
                String tempPlacement="";
                while (iter.hasNext()){
                    tempPlacement=iter.next();
                    char type;
                    if (placement.length()==0){
                            placement=tempPlacement;
                    }else {
                        type=placement.charAt(placement.length()-4);
                        if (tempPlacement.charAt(0)==(char)(type+1)){
                            placement=placement.concat(tempPlacement);
                            break;
                        }else {
                            continue;
                        }
                    }
                }

                //filled the boardState based tempPlacement
                Colors[][] tempPiece=null;
                char type=tempPlacement.charAt(0);
                int x=(int)tempPlacement.charAt(1)-48;
                int y=(int)tempPlacement.charAt(2)-48;
                int orientation=tempPlacement.charAt(3)-48;
                switch (type){
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
                int length=tempPiece[0].length;
                int width=tempPiece.length;
                for (int r=0;r<width;r++){
                    for (int c=0;c<length;c++){
                        Location rotateLoc=PieceType.rotateXY(c,r,length,width,orientation);
                            if (tempPiece[r][c]!=null){
                                boardState[y+rotateLoc.getY()][x+rotateLoc.getX()]=tempPiece[r][c];
                    }
                    }
                }
            }
        }

        return placement;
    }
}
