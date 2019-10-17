package comp1110.ass2;

import java.util.*;

import static comp1110.ass2.FocusGame.fillBoard;
import static comp1110.ass2.FocusGame.getViablePiecePlacements;

public class Challenges {
    private int challengeNumber;          // The problem number from the original board game
    private String colorConstraint;    // The color constraint of 3 * 3 matrix.
    private static Challenges[] challenge = new Challenges[120]; //List of challenges.
    // This is for the playButton to access
    public static String[] challengesList = {
            "RRRBWBBRB", "RWWRRRWWW", "BGGWGGRWB", "WRRWRRGWW", "GWRGWWGGG", "GRWGRWWWW",
            "RGGRGGRRB", "GGGRGRBBB", "RGGGGRBGG", "BBBWRWGGG", "WBWWWWRWG", "BBGRWBRRB",
            "WWRGWWGGW", "WRRRRRWWW", "WWWWRWBBB", "GBBRRRBBG", "BRRBWRBBB", "GGGWGWGGB",
            "GWRGGWGGG", "WWRGWRWWR", "WWRGGWGGW", "RBGWBBGWR", "BGWBWRBBB", "BGRWWWWWW",
            "WBWWBWGGG", "WGBWGBWGB", "BBBGGGWWW", "GBBGWWGBB", "BWGGWGGWB", "WBWGGBWGW",
            "GWGGWBGGG", "BGGWGGGWB", "GWWGWBGGG", "GBGWWBWWG", "GWWBWWGBG", "BGBWWWWBW",
            "GGWWGGBWG", "WBWWWWGGG", "BBBBGGBGB", "BBBWWWBGB", "WGGBGGGBW", "GBGWWWBGB",
            "BWGWWWGWB", "BGBBGGWBB", "BGBBWBBGB", "WWGBBWWBW", "WBWGGBBGW", "BWWBWGBWW",
            "RRRRRRRRR", "RRRRRWRWW", "RWWWRWWWR", "BWBBWBBBB", "BWWBBBBWB", "WWBWWBWWB",
            "BWBBBBWWW", "BBBBWBBWB", "BBBBWBBBB", "BBBWWBWBB", "WBBWBWWBW", "BBBWWBBBB",
            "WGGWWGWWW", "GGGGWGWWG", "GGWWGGWWG", "WBBWWBWWW", "BBBBWWBBB", "WBBBWWBWW",
            "WWRWRWWWR", "RRWWRWWRR", "WWWRRWWRR", "WGGGGGGGW", "WGGGGGWWW", "GGGGGGGGG",
            "GGRBGRBBR", "BGBGBRGRR", "WBWWRWWGW", "GGBRRGBRG", "GWWWRWWWB", "BBRWBBGWB",
            "BGWWBGWWW", "GWBGGWWGG", "BGGBGBBGG", "GGRGGRRRR", "GWGGWRGGG", "RRWGRRGGR",
            "GRRGGGRRG", "WRWWRWGWG", "RGGRWGGRR", "GGGGGGRWR", "GGGRRRWRW", "GRGWRRRWG",
            "RBRRBBRRB", "RRBWBRWWR", "WBWWRWWBW", "WWWRBRRBR", "RWWRWBWWB", "RBRRBRRBR",
            "WWWWWWWWW", "RRBBBBGGB", "GGGBBGRBG", "GBGRRRBRB", "GBRGBRGGG", "GBWRBBRRG",
            "WWBWGWWWR", "BGGRWBBGG", "BGRBGRGGG", "BRWRRRWRB", "BBBRRBWRB", "BWRBBWWBB",
            "RBBRBBRRR", "RWWBWWBBR", "RBBRWBRRB", "BBRRRRBBR", "RBBBWWBWR", "BWRBWRBWR",
            "BBBWRWRRR", "BBBRWBWRB", "RWBWWWBWR", "WRBWRBWRB", "BWRBRWBWR", "BRBBRBBWB",

    };


    public Challenges(int challengeNumber, String colorConstraint){
        this.challengeNumber = challengeNumber;
        this.colorConstraint = colorConstraint;
    }

    public String getColorConstraint() {
        return colorConstraint;
    }

    public int getChallengeNumber() {
        return challengeNumber;
    }

    public static Challenges getObjective(int index) {
        return challenge[index];
    }
    public static Challenges[] getOBJECTIVES() {
        return challenge;
    }

    /*
    * Returns placement encoding for the given challenge,
    * if the challenge already exists in provided 120 challenges.
    * Returns Null if it doesnt exist
    *
    * @param colorConstraint Color encoding of length 9 in the form of string.
    * @return Solution Placement encoding for the given challenge.
     */

    //Author: Ranjth
    public static String isExisting(String colorConstraint){

        for (int i = 0; i < Solution.SOLUTIONS.length ; i ++){
            challenge[i] = new Challenges(i,Solution.SOLUTIONS[i].objective);
        }

        for(Challenges i : challenge){
            if (i.colorConstraint == colorConstraint)
                return Solution.SOLUTIONS[i.challengeNumber].placement;
        }
        return "";
    }


    //---------------------------------Task 11---------------------------------------------------------------------------
    //Author: Ranjth
    public static Set<String> generateChallenege(){
        Random rand = new Random();
        String challenge = "";
        String colors = "RBWG";
        Set<String> solution = new HashSet<String>();
        boolean containsSolution = false;

        while (!containsSolution) {
            challenge = "";
            for (int i = 0; i < 9; i++)
                challenge += colors.charAt(rand.nextInt(colors.length()));

            //Checks whether there is an existing challenge with the same color constraint
            String placement = isExisting(challenge);
            if (placement != ""){
                Set<String> temp = new HashSet<String>();
                temp.add(placement);
                return temp;
            }
            //challenge = "GGWRBGGWW"; //Trying with a sample challenge
            System.out.println("Validating for the challenge: " + challenge);
            solution = getSolution(challenge) != null ? getSolution(challenge): null;

            //Checks whether there is a solution which is not NULL
            if ( solution != null) {
                System.out.println("The challenge is " + challenge);
                containsSolution = true;
            }
            else
                System.out.println("No possible solution exists.");
        }
        return solution;
    }

    static Set<String> getViablePiecePlacements(String placement, String challenge, int col, int row) {
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
        if (!placement.isEmpty())
            boardState = FocusGame.fillBoard(placement, boardState);

        Set<String> viablePiece=new HashSet<String>();

        for (int t=0;t<10;t++){
            for (int x=0;x<9;x++){
                for (int y=0;y<5;y++){
                    for (int d=0;d<4;d++){
                        String tempPlace=""+((char)(t+97))+x+y+d;
                        String newPlacement=placement.concat(tempPlace);
                        //System.out.println(newPlacement);
                        if (FocusGame.isPlacementStringValid(newPlacement)==false){
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
                                Location rotateLoc = PieceType.rotateXY(k, j, length, width, d);
                                //@Ron, I created the below two variables, because it is less time consuming if you call Location.getX() once
                                // and store it for further use,
                                //rather than calling it each time you need.
                                int rotatedIndexY = y + rotateLoc.getY();
                                int rotatedIndexX = x + rotateLoc.getX();
                                if (tempPiece[j][k] != null) {
                                    //@Ron, I created a method for the following conditional check.
                                    if (FocusGame.isWithinChallengeSquare(rotatedIndexX, rotatedIndexY)) {
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

        return viablePiece.isEmpty()? null: viablePiece;
    }

    /*
     *Returns the set of possible solutions given the challenge
     *
     *
     *@param challenge A challenge string.
     *@return A set of placement strings describing a canonical encoding of the solution to
     *the challenge.
     */

    //Author: Ranjth
    public static Set<String> getSolution(String challenge){

        String placement="";
        int length = 0;
        Colors[][] boardState = new Colors[5][9];
        List<Set<String>> solutionStep = new ArrayList<Set<String>>();        //store the possibility in every step solutionStep
        Iterator<Set<String>> listIterator = solutionStep.iterator();
        Set<String> solutions = new HashSet<String>();

        /*
        * Get viable piece placements at the co-ordinate (4,2) as first step, wrt given challenge.
        * Let me walk through this entire function with this sample challenge "WBWWBWGGG"
        *  This [g310, i311, b311, c422, i413] is a set of possible placements at (4,2) for given challenge
         */
        Set<String> step = FocusGame.getViablePiecePlacements2(placement,challenge,4,2);

        //System.out.println("The next set of placements for " + placement + " are " + step);

        //Checks whether any piece placement is valid in the specified co-ordinates for given challenge
        if ( step == null || step.size() == 0)
            return null;

        /*
        * Adding first set of viable pieceplacements to the solutions list
        * i.e., Adding set [g310, i311, b311, c422, i413] to solutions list as a result of first step
         */
        solutionStep.add(step);

        Iterator<String> iter=step.iterator();

        while (iter.hasNext()) {
            placement = iter.next();
            iter.remove();

            //Initialize the board to null each time
            //boardState = initializeBoardState();
            boardState = new Colors[5][9];

            //Update the boardstate with current placements
            for (int l = 0; l < placement.length(); l= l + 4){
                boardState = FocusGame.fillBoard(placement.substring(l, l+4), boardState);
            }

            //Find the co-ordinates in the board which are yet to be filled in the square region
            Location empty = FocusGame.findEmpty(boardState, 3,5,1,3);

            if(empty == null)
                empty = FocusGame.findEmpty(boardState, 0,8,0,4);


            Set<String> viable = null;
            if(empty == null){
                /*
                * Add the placement to solution to "solutions" string set
                * 'empty' being null states the there are no empty boxes
                * So our placements are right and they have filled the board
                * wrt challenge
                */
                solutions.add(placement);

            }
            else {
                /*
                * Generate the next set of viable placements possible given the current placements
                *
                * Now let's consider the first element from the set [g310, i311, b311, c422, i413] (i.e., g310)
                * The next set of placements for g310 is
                * [d002, e003, d003, b001, c002, d000, e001, a000, c001, f001, e000, f000, j000, i000, j001, h000, h001, i002, j003, i003, h003, b003, a003]
                 */
                viable = getViablePiecePlacements(placement, challenge, empty.getX(), empty.getY());
                //System.out.println("The next set of placements for " + placement + " are " + viable);
            }

            //System.out.println("Next possible placements: " + viable + " for existing placement " + placement + " at co-ordinate " + empty);

            /*
            * If there aren't any possible placements (i.e., viable = null),
            * then get the next set of placements from list that needs to be checked.
            *
            * If the next set of placements for i413(last element in the set) is empty (which is not in this case),
            * consider the next set in the list which is
             */
            if(viable == null) {
                if(!iter.hasNext()){
                    length++;
                    if(solutionStep.size() > length){
                        iter = solutionStep.get(length).iterator();
                    }
                }
                /*
                * Skip the below part, as we are considering the next set from the list
                 */
                continue;
            }

            //Create a dummy iterator and a dummy set
            Iterator<String> viableIter = viable.iterator();
            Set<String> temp = new HashSet<String>();


            /*
             * If there are elements in the set (i.e., next possible placements exists), then get those using iterator.
             *  Add the current placement(state) with each of possible placements,
             *  and store it in dummy set.
             *
             * The next set of placements for g310 is
             * [d002, e003, d003, b001, c002, d000, e001, a000, c001, f001, e000, f000, j000, i000, j001, h000, h001, i002, j003, i003, h003, b003, a003]
             * As there are elements, modify and add it to the dumpy set like the below
             * [g310e001, g310d000, g310f001, g310e000, g310f000, g310j000, g310a003, g310b003, g310i000, g310j001, g310h000, g310d003, g310b001, g310c002, g310h001, g310i003, g310i002, g310j003, g310e003, g310h003, g310d002, g310a000, g310c001]
             * */
            while (viableIter.hasNext()){
                //System.out.println("Added placement: " + iter.next());
                String subPlacement = viableIter.next();
                temp.add(placement + subPlacement);
            }

            /*
            *If the dummy set has elements, then add the set to solutions list.
            *
            * Add the set
            * [g310e001, g310d000, g310f001, g310e000, g310f000, g310j000, g310a003, g310b003, g310i000, g310j001, g310h000, g310d003, g310b001, g310c002, g310h001, g310i003, g310i002, g310j003, g310e003, g310h003, g310d002, g310a000, g310c001]
            * to the "solutionStep" list
             * */

            if(temp.size() > 0){
                solutionStep.add(temp);
            }
            //System.out.println("Temp: " + temp);
            //iter = temp.iterator();


            /*
            * Change the iterator to next Set in the list, as current set has no elements
            * that needs to be checked for next possible placements.
            *
            * This step is similar to the one performed before,
            * but this happens only when you are performing on the last element of the set
            * and it is supposed to contain next possible placements .
             */
            if(!iter.hasNext()){
                length++;
                if(solutionStep.size() > length){
                    iter = solutionStep.get(length).iterator();
                }
            }
        }


        //System.out.println("The solutions are " + solutions);
        return solutions.iterator().hasNext() ? solutions : null;
    }

    public static void main(String[] args) {

        //System.out.println("Debugging: "+ FocusGame.getSolution("GGWRBGGWW"));
        System.out.println("The solution for the challenge is "+ generateChallenege());

    }
}
