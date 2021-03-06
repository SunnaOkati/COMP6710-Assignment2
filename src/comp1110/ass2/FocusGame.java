package comp1110.ass2;

import java.lang.reflect.Array;
import java.util.*;


/**
 * This class provides the text interface for the IQ Focus Game
 * <p>
 * The game is based directly on Smart Games' IQ-Focus game
 * (https://www.smartgames.eu/uk/one-player-games/iq-focus)
 */
public class FocusGame {
    Colors[][] boardState = new Colors[5][9];

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

    // Author: Ranjth
    static boolean isPiecePlacementWellFormed(String piecePlacement) {
        // FIXME Task 2: determine whether a piece placement is well-formed
        
        /* Seperate 4 characters from "piecePlacement" by using String.charAt() and check whether
         each lie within the above specified limits.
        */
        if (piecePlacement.length() != 4) //Check whether the length of the string = 4
            return false;

        Location location = Piece.placementToLocation(piecePlacement);
        Orientation orientation = Piece.placementToOrientation(piecePlacement);
        PieceType type = Piece.placementToPieceType(piecePlacement);

        if ((type != null) && (orientation != null) && //Check whether type and orientation are valid
                (location.getX() >= 0) && (location.getX() < 9) && //Check whether x lies within 0 to 8.
                (location.getY() >= 0) && (location.getY() < 5) ) //Check whether y lies within 0 to 4.
        {
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

    // Author: Ranjth
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

        //Check whether it contains N four character piece placements
        if (placement.length() % 4 == 0 && placement.length() != 0) {
            for (int i = 0; i < placement.length(); i = i + 4) {

                //Check whether each substrings are well formed
                if (!isPiecePlacementWellFormed(placement.substring(i, i + 4))) {
                    return false;
                } else {
                    for (int j = 0; j < dupCheck.length(); j++) {

                        //Check whether the piece has been placed already
                        if (dupCheck.charAt(j) == placement.substring(i, i + 4).charAt(0))
                            return false;
                    }

                    //Add the piece if it doesnt exist already.
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

    //@author Rong Hu
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
                        if (x>5||y>3||(x==0 && y==3)){
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

    // Author: Ranjth
    /*
    * Fills the board's state wrt to given challenge constraint(encoding)
     */
    public static Colors[][] fillingChallenge(String challenge){

        Colors[][] boardState = new Colors[5][9];
        int count=0;
        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                Colors constrain=Colors.getColors(challenge.charAt(count));
                count++;
                boardState[1+i][3+j]=constrain;
            }
        }
        return boardState;
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
    //@author Rong Hu
    static Set<String> getViablePiecePlacements(String placement, String challenge, int col, int row) {
        // FIXME Task 6: determine the set of all viable piece placements given existing placements and a challenge
        Colors[][] boardState = new Colors[5][9];
        boardState = fillingChallenge(challenge);

        if (!placement.isEmpty())
            boardState = FocusGame.fillBoard(placement, boardState);

        Set<String> viablePiece=new HashSet<String>();

        for (int t=0;t<10;t++){
            for (int d=0;d<4;d++){
                for (int x=findStartX((char)(t+97),d,col,row);x<=col;x++){
                    for (int y=findStartY((char)(t+97),d,col,row);y<=row;y++){
                        String tempPlace=""+((char)(t+97))+x+y+d;
                        String newPlacement=placement.concat(tempPlace);
                        //System.out.println(newPlacement);
                        if (isPlacementStringValid(newPlacement)==false){
                            continue;
                        }

                        //@Ron, I implemented the below method. I felt implementing this way seemed a bit clean.
                        Colors[][] tempPiece= Piece.placementToPieceArray(tempPlace);

                        int length=tempPiece[0].length;
                        int width=tempPiece.length;
                        boolean sig = true;
                        boolean sig2 = false;
                        for (int j = 0; j < width; j++) {
                            for (int k = 0; k < length; k++) {
                                if (tempPiece[j][k] == null){
                                    continue;
                                }
                                Location rotateLoc = PieceType.rotateXY(k, j, length, width, d);
                                //@Ron, I created the below two variables, because it is less time consuming if you call Location.getX() once
                                // and store it for further use,
                                //rather than calling it each time you need.
                                int rotatedIndexY = y + rotateLoc.getY();
                                int rotatedIndexX = x + rotateLoc.getX();
                                if (tempPiece[j][k] != null) {
                                    //@Ron, I created a method for the following conditional check.
                                    if (isWithinChallengeSquare(rotatedIndexX, rotatedIndexY)) {
                                        if (boardState[rotatedIndexY][rotatedIndexX] != tempPiece[j][k]) {
                                            sig = false;
                                        }
                                    }
                                    if (rotatedIndexY == row && rotatedIndexX == col && sig == true) {
                                        sig2 = true;
                                    }
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

        return viablePiece.isEmpty()? null: viablePiece;
    }

    //Author: Ranjth
    //Checks whether the co-ordinates within the challenge square(area)
    public static boolean isWithinChallengeSquare(int x, int y){
        if ((y <=3 && y >= 1)&&(x <=5 &&x >= 3))//Y:Between second and fourth row X: Between fourth and sixth column
            return true;

        return false;
    }

    //find the range of X according to the type and orientation of the piece
    public static int findStartX(char type, int orientation, int x, int y){
        int startX;
        String pieceString=""+type+0+0+orientation;
        Colors[][] tempPiece= Piece.placementToPieceArray(pieceString);
        int length=tempPiece[0].length;
        int width=tempPiece.length;
        if (orientation==0||orientation==2){
            startX=x-length<0 ? 0: x-length;
        }else {
            startX=x-width<0 ? 0: x-width;
        }
        return startX;
    }

    public static int findStartY(char type, int orientation, int x, int y){
        int startY;
        String pieceString=""+type+0+0+orientation;
        Colors[][] tempPiece= Piece.placementToPieceArray(pieceString);
        int length=tempPiece[0].length;
        int width=tempPiece.length;
        if (orientation==0||orientation==2){
            startY=y-width<0 ? 0: y-width;
        }else {
            startY=y-length<0 ? 0: y-length;
        }
        return startY;
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


    //@author Rong Hu
    public static String getSolution(String challenge) {
        // FIXME Task 9: determine the solution to the game, given a particular challenge
        String placement="";                           //the solution
        Colors[][] boardState = new Colors[5][9];          //the current state of the board
        List<Set<String>> solutionStep = new ArrayList<Set<String>>();        //store the possibility in every step solutionStep


        //start first step at the middle cell(4,2)
        Set<String> firstStep = getViablePiecePlacements2(placement,challenge,4,2);
        //Checks whether any piece placement is valid in the specified co-ordinates for given challenge
        if ( firstStep == null || firstStep.size() == 0)
            return "";
        solutionStep.add(firstStep);
        Iterator<String> iter=firstStep.iterator();
        placement=iter.next();
        boardState=FocusGame.fillBoard(placement,boardState);


        while (placement.length()<40){
            //get the possible solution of the empty square
            Location empty=FocusGame.findEmpty(boardState,3,5,1,3);
            if (empty==null){
                empty=FocusGame.findEmpty(boardState,0,8,0,4);
            }
            Set<String> viablePiece=getViablePiecePlacements(placement,challenge,empty.getX(),empty.getY());

            //if the empty square have solution, add it to solutionStep and placement string
            if (viablePiece!=null){
                solutionStep.add(viablePiece);
                iter=viablePiece.iterator();
                String nextStep=iter.next();
                placement=placement+nextStep;
                boardState=FocusGame.fillBoard(nextStep,boardState);
                //System.out.println("Piece placement: " + nextStep);
            } else {
                boolean change=false;
                String tempPlacement="";
                while (change==false) {
                    //remove the former last step
                    //System.out.println("Piece removal: ");

                    if(solutionStep.size() == 0){
                        return "";
                    }

                    Set<String> placementSet = solutionStep.get(solutionStep.size() - 1);

                    String lastPiece = placement.substring(placement.length() - 4);
                    placementSet.remove(lastPiece);
                    boardState=FocusGame.removeBoard(lastPiece,boardState);
                    placement = placement.substring(0, placement.length() - 4);
                    //if last step has other choice, try it
                    if (placementSet.isEmpty() != true) {
                        iter = placementSet.iterator();
                        tempPlacement=iter.next();
                        placement = placement + tempPlacement;
                        boardState=FocusGame.fillBoard(tempPlacement,boardState);
                        change=true;
                        break;
                        //if last step do not has other choice, reduce one step and update the former step
                    } else {
                        solutionStep.remove(solutionStep.size() - 1);
                    }
                }
            }
        }

        //deal with symmetry pieces, e.g. g002 -> g000
        String newPlacement="";
        for (int i=0;i<40;i=i+4){
            String subPlacement=placement.substring(i,i+4);
            if ((subPlacement.charAt(0)=='g'||subPlacement.charAt(0)=='f')&&(subPlacement.charAt(3)=='2'||subPlacement.charAt(3)=='3')){
                if (subPlacement.charAt(3)=='2'){
                    newPlacement=newPlacement+subPlacement.substring(0,3)+"0";
                }else if (subPlacement.charAt(3)=='3'){
                    newPlacement=newPlacement+subPlacement.substring(0,3)+"1";
                }
            }else {
                newPlacement=newPlacement+subPlacement;
            }
        }

        //System.out.println(challenge);
        //System.out.println("newplacement: "+newPlacement);

        //System.out.println("placement: "+placement);
        //return null;
        placement=newPlacement;
        return placement;
    }



    //find the empty square on the board
    //@author Rong Hu
    public static Location findEmpty(Colors[][] boardState, int startX, int endX, int startY, int endY){
        int x,y;
        Location empty=null;
        boolean quit=true;
        for (x=startX;x<=endX && quit;x++){
            for (y=startY;y<=endY&&quit;y++){
                if ((x==0 && y==4)||(x==8 && y==4)){
                    continue;
                }else if (boardState[y][x]==null){
                    empty=new Location(x,y);
                    quit=false;
                }
            }
        }
        return empty;
    }




    //veryfy whether the placement of the piece will produce dead cell, such as (0,0) of a001
    //@author Rong Hu
    public static boolean isDeadCell(String placement){
        boolean sig=false;
        char type=placement.charAt(0);
        int x=(int)placement.charAt(1)-48;
        int y=(int)placement.charAt(2)-48;
        int orientation=placement.charAt(3)-48;
        String xy=String.valueOf(x)+String.valueOf(y);
        switch (xy){
            case "00":
                if (orientation==0&&(type=='c'||type=='b')){
                    sig=true;
                }else if (orientation==1&&(type=='a'||type=='b'||type=='d'||type=='g'||type=='i'||type=='j')){
                    sig=true;
                }else if (orientation==2&&(type=='a'||type=='b'||type=='e'||type=='j')){
                    sig=true;
                }else if(orientation==3&&(type=='c'||type=='g'||type=='b')){
                    sig=true;
                }
                break;
            case "50":
                if (orientation==0&&type=='c'){
                    sig=true;
                }
                break;
            case "60":
                if (orientation==2&&(type=='a'||type=='d'||type=='g')){
                    sig=true;
                }else if (orientation==0&&type=='g'){
                    sig=true;
                }
                break;
            case "70":
                if (orientation==1&&(type=='b'||type=='c')){
                    sig=true;
                }else if (orientation==2&&type=='i'){
                    sig=true;
                }else if (orientation==3&&(type=='a'||type=='b'||type=='e'||type=='j')){
                    sig=true;
                }
                break;
            case "53":
                if (orientation==0&&(type=='b'||type=='j'|| type=='e')){
                    sig=true;
                }else if(orientation==2&&type=='c'){
                    sig=true;
                }
                break;
            case "01":
                if (orientation==1 && (type=='a'||type=='b'||type=='e'||type=='j')){
                    sig=true;
                }else if(orientation==3 && type=='c'){
                    sig=true;
                }
                break;
            case "02":
                if (orientation==0&&(type=='g'||type=='i'||type=='a')){
                    sig=true;
                }else if (orientation==2&&(type=='c'||type=='g')){
                    sig=true;
                }
                break;
            case "03":
                if (orientation==0&&type=='d'){
                    sig=true;
                }
                break;
            case "71":
                if (type=='g'&&(orientation==1||orientation==3)){
                    sig=true;
                }
                break;
            case "72":
                if (orientation==3 &&(type=='a'||type=='d'||type=='i')){
                    sig=true;
                }
                break;
            case "62":
                if (orientation==0 && type=='a'){
                    sig=true;
                }else if (orientation==3 && ( type=='a'|| type=='g')){
                    sig=true;
                }else if (orientation==1 && type=='g'){
                    sig=true;
                }
                break;
            case "52":
                if (orientation==2 && type=='b'){
                    sig=true;
                }
                break;
            case "13":
                if (orientation == 0 &&(type=='a'||type== 'd'||type=='g'||type=='i')) {
                    sig=true;
                }else if (orientation=='2'&&(type=='c'||type=='g')){
                    sig=true;
                }
                break;
            case "63":
                if (orientation==0 && type=='a'){
                    sig=true;
                }else if (orientation==3 &&type=='i'){
                    sig=true;
                }
                break;
            case "12":
                if (orientation==1 && (type=='a'|| type=='e')){
                    sig=true;
                }
                break;
            case "43":
                if ((orientation==0||orientation==2) && type=='b'){
                    sig=true;

                }else if (orientation==3 && type=='c'){
                    sig=true;
                }
                break;
            case "11":
                if (orientation==3 && type=='b'){
                    sig=true;
                }
                break;
            case "61":
                if (orientation==1 && type=='c'){
                    sig=true;
                }
                break;

        }
        return sig;
    }

    //find all possible occasions of specific square based on task 9 request
    //@author Rong Hu
    static Set<String> getViablePiecePlacements2(String placement, String challenge, int col, int row) {
        Colors[][] boardState = new Colors[5][9];
        boardState = fillingChallenge(challenge);

        Set<String> viablePiece=new HashSet<String>();

        // FIXME Set by Victor
        // Ron, why is this set up as at least 4 nested for loops (t,x,y,d) when nothing happens in the t,x or y loop?
        // You still haven't named these constants so I don't know what they do or why they're here
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
                        if (sig == true && sig2 == true && isDeadCell(tempPlace) != true) {
                            if ((tempPlace.charAt(0) == 'g' || tempPlace.charAt(0) == 'f') && (tempPlace.charAt(3) == '2' || tempPlace.charAt(3) == '3')) {
                                if (tempPlace.charAt(3) == '2') {
                                    tempPlace = tempPlace.substring(0, 3) + "0";

                                } else if (tempPlace.charAt(3) == '3') {
                                    tempPlace = tempPlace.substring(0, 3) + "1";
                                }
                            }
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

    //verify whether the placement(4 characters) is valid based on the challenge), if invalid, return true
    static public boolean verifyChallenge(String placement,String challenge){
        Colors[][] boardState = new Colors[5][9];
        int count=0;
        char type=placement.charAt(0);
        int x=(int)placement.charAt(1)-48;
        int y=(int)placement.charAt(2)-48;
        int orientation=placement.charAt(3)-48;
        //fill the board with chanllenge
        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                Colors constrain=Colors.getColors(challenge.charAt(count));
                count++;
                boardState[1+i][3+j]=constrain;
            }
        }
        //verify whether each of square of the piece is corresponding with the challenge
        Colors[][] tempPiece=Piece.placementToPieceArray(placement);
        int length=tempPiece[0].length;
        int width=tempPiece.length;
        boolean sig=false;
        for (int j=0;j<width;j++){
            for (int k=0;k<length;k++){
                Location rotateLoc=PieceType.rotateXY(k,j,length,width,orientation);
                if ((y+rotateLoc.getY()<=3 && y+rotateLoc.getY()>=1)&&(x+rotateLoc.getX()<=5 && x+rotateLoc.getX()>=3)){
                    if (boardState[y+rotateLoc.getY()][x+rotateLoc.getX()]!=tempPiece[j][k]&& tempPiece[j][k]!=null){
                        sig=true;
                    }
                }
            }
        }
        return sig;
    }


    //fill the cells based on the input placement string(4 characters)
    //@author Rong Hu
    static public Colors[][] fillBoard(String placement,Colors[][] board){
        char type=placement.charAt(0);
        int x=(int)placement.charAt(1)-48;
        int y=(int)placement.charAt(2)-48;
        int orientation=placement.charAt(3)-48;
        Colors[][] tempPiece=Piece.placementToPieceArray(placement);
        int length=tempPiece[0].length;
        int width=tempPiece.length;
        for (int j=0;j<width;j++){
            for (int k=0;k<length;k++){
                Location rotateLoc=PieceType.rotateXY(k,j,length,width,orientation);
                if (tempPiece[j][k]!=null){
                    //System.out.println("Entered: "+ tempPiece[j][k]);
                    board[y+rotateLoc.getY()][x+rotateLoc.getX()]=tempPiece[j][k];
                }
            }
        }
        return board;
    }

    //remove the cells based on the input placement string(4 characters)
    //@author Rong Hu
    static public Colors[][] removeBoard(String placement,Colors[][] boardState){
        char type=placement.charAt(0);
        int x=(int)placement.charAt(1)-48;
        int y=(int)placement.charAt(2)-48;
        int orientation=placement.charAt(3)-48;
        Colors[][] tempPiece=Piece.placementToPieceArray(placement);
        int length=tempPiece[0].length;
        int width=tempPiece.length;
        for (int j=0;j<width;j++){
            for (int k=0;k<length;k++){
                Location rotateLoc=PieceType.rotateXY(k,j,length,width,orientation);
                if (tempPiece[j][k]!=null){
                    boardState[y+rotateLoc.getY()][x+rotateLoc.getX()]=null;
                }
            }
        }
        return boardState;
    }


}
