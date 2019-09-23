package comp1110.ass2;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


public class task8Tests {

    public boolean tempMethod(){
        return true;
    }

    @Test
    public void checker(){
        assertFalse("beep boop",tempMethod());
    }

}
