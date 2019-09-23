package comp1110.ass2;


import comp1110.ass2.gui.Board;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


public class task8Tests {



    @Test
    public void testRegexChecker(){
        assertTrue("Didn't return character /' a /'", Board.regexChecker("[abc]","a").equals("a"));
        assertTrue("Didn't care about case", Board.regexChecker("[A-Z]","ABCDEFabcdef").equals("ABCDEF"));
        assertFalse("Didn't care about character amount restriction",Board.regexChecker("[A-Z]{9}","RRRWWWBBBZ").equals("RRRWWWBBBZ"));
        assertFalse("Matches numbers", Board.regexChecker("[a-z,A-Z]","0123ab8c").equals("01238"));
    }





}
