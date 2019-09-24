package comp1110.ass2;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertTrue;

public class TestPlacementStringWellFormed {

    private void test(String placement, boolean expected){
        boolean predicted = FocusGame.isPlacementStringWellFormed(placement);
        assertTrue("For string "+placement+" expected " + expected+ ", but got " + predicted,predicted == expected);
    }

    @Test
    //Check whether the function accepts strings which are of length not divisible by 4.
    public void nSubStrings(){
        String proper = "a000";
        String complete = "";

        Random random = new Random(1234);
        test("", false);
        for (int x = 0 ; x < 10 ; x++)
            for (int i = 0 ;  i< 9;i++){
                for (int j = 0; j< 5;j++) {
                    for (int k = 0; k < 4; k++){
                        proper = Character.toString('a' + x) + i + j + k;
                        complete += proper;
                        test(proper,true);
                        test(proper + "a", false);
                        test(proper+ random.nextInt(),false);
                    }
                }
            }
        test(complete + "k001", false);
    }

    @Test
    //Check whether the function accepts randomly generated string with repetitions
    public void pieceRepetation(){
        String placement = "";
        String pieces = "";
        Random random = new Random(1234);
        for (int i = 0; i < 4; i ++){
            placement += Character.toString('a' + i) + random.nextInt(9) + random.nextInt(5) + random.nextInt(4) ;
        }
        test(placement,true);

        placement += Character.toString('a' + random.nextInt(4)) + random.nextInt(9) + random.nextInt(5)+ random.nextInt(4);
        test(placement,false);
    }
}
