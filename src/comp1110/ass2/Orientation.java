package comp1110.ass2;

public enum Orientation {
    ZERO,ONE,TWO,THREE;

    /**
     * Return the single character associated with a `Orientation`, which is the numerical value
     * ('0', '1', '2', '3')
     *
     * @return A char value equivalent to the `Orientation` enum
     */

    public char toChar() {
        char c = ' ';
        if (Orientation.ZERO.equals(this))
            c = '0';
        if (Orientation.ONE.equals(this))
            c= '1';
        if (Orientation.TWO.equals(this))
            c = '2';
        if (Orientation.THREE.equals(this))
            c= '3';
        return c;
    }

}
