package comp1110.ass2.gui;

import comp1110.ass2.FocusGame;
import java.util.HashSet;
import java.util.Set;

public class Hint {
    public static String giveHint(String placement, String rightPlacement, String challenge) {
        //String rightPlacement= FocusGame.getSolution(challenge);
        String hintPlacement = "";
        boolean change = false;
        String nextStep = "";
        Set<String> placedPieces=new HashSet<String>();
        for (int i = 0; i < placement.length(); i = i + 4) {
            //get the type of present placement piece
            char type = placement.charAt(i);
            String tempPlacement=placement.substring(i, i + 4);
            String tempModify = "";
            //find the right present placement piece
            for (int j = 0; j < rightPlacement.length(); j = j + 4) {
                if (type == rightPlacement.charAt(j)) {
                    tempModify = rightPlacement.substring(j, j + 4);
                    //if change
                    if (tempModify.equals(tempPlacement)==false){
                        change = true;
                    }
                    break;
                }
            }
            placedPieces.add(tempModify);
            hintPlacement = hintPlacement + tempModify;
        }
        //if all pieces placements are right, add next step
        if (change == false) {
            //find the next step by scan through the right placement
            for (int i = 0; i < rightPlacement.length(); i = i + 4) {
                String tempPlacement=rightPlacement.substring(i, i + 4);
                if (placedPieces.contains(tempPlacement)){
                    continue;
                }else {
                    nextStep=tempPlacement;
                    break;
                }
            }
            hintPlacement = hintPlacement + nextStep;
        }
        return hintPlacement;
    }






}
