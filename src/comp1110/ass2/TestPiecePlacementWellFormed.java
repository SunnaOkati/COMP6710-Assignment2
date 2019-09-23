package comp1110.ass2;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestPiecePlacementWellFormed {

    @Test
    //Check whether the function accepts an empty string
    public void emptyString(){
        assertFalse(" Accepts Empty string.",FocusGame.isPiecePlacementWellFormed(""));
    }

    @Test
    //Check whether the function a string of length containing characters from the specified range
    public void strLengthOne(){
        for (int i = 0 ;  i< 10;i++){
            char check = (char)('a' + i);
            assertFalse("Accepts string" + Character.toString(check)+ " of length 1.",FocusGame.isPiecePlacementWellFormed( Character.toString(check)));
        }
    }

    @Test
    //Check whether the function accepts strings of length 3 which is combination of piece type and co-ordinates.
    public void strLengthThree(){
        String check = "a";
        for (int i = 0 ;  i< 9;i++){
            for (int j = 0; j< 5;j++) {
                check = check + i + j;
                assertFalse("Accepts string" + check + " of length 3.", FocusGame.isPiecePlacementWellFormed(check));
            }
        }
    }

    @Test
    //Check whether the function accepts different combinations generated using elements within possible ranges
    public void correctCombinations(){
        String check = "";
        //Expected to return true
        for (int x = 0 ; x < 10 ; x++)
            for (int i = 0 ;  i< 9;i++){
                for (int j = 0; j< 5;j++) {
                    for (int k = 0; k < 4; k++){
                        check = Character.toString('a' + x) + i + j + k;
                        assertTrue("Accepts string" + check + " of length 4.", FocusGame.isPiecePlacementWellFormed(check));
                    }
                }
            }
    }

    @Test
    //Check whether the function accepts different combinations generated using elements outside possible ranges
    public void incorrectCombinations(){
        String check = "";
        //Expected false
        for (int x = 10 ; x < 12 ; x++)
            for (int i = 9 ;  i< 12;i++){
                for (int j = 5; j< 7;j++) {
                    for (int k = 4; k < 6; k++){
                        check = Character.toString('a' + x) + i + j + k;
                        assertFalse("Accepts string" + check + " of length 4.", FocusGame.isPiecePlacementWellFormed(check));
                    }
                }
            }
    }

}
