package comp1110.ass2.gui;

import comp1110.ass2.FocusGame;

public class Hint {
    public static String giveHint(String placement, String rightPlacement, String challenge) {
        //String rightPlacement= FocusGame.getSolution(challenge);
        String hintPlacement = "";
        boolean change = false;
        String nextStep = "";
        for (int i = 0; i < placement.length(); i = i + 4) {
            //get the type of present placement piece
            char type = placement.charAt(i);
            String tempModify = "";
            //find the right present placement piece
            for (int j = 0; j < rightPlacement.length(); j = j + 4) {
                if (type == rightPlacement.charAt(j)) {
                    tempModify = rightPlacement.substring(j, j + 4);
                    change = true;
                    break;
                } else {
                    nextStep = rightPlacement.substring(j, j + 4);
                }
            }
            hintPlacement = hintPlacement + tempModify;
        }
        //if all pieces placements are right, add next step
        if (change == false) {
            hintPlacement = hintPlacement + nextStep;
        }
        return hintPlacement;
    }






}
