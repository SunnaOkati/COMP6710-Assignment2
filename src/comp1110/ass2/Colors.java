package comp1110.ass2;

public enum Colors {
    WHITE, // White color
    RED,   // Red color
    GREEN, // Green color
    BLUE;   // Blue color

    public static Colors getColors(char x){
        Colors colors=null;
        switch (x){
            case 'W':
                colors=WHITE;
                break;
            case 'R':
                colors=RED;
                break;
            case 'G':
                colors=GREEN;
                break;
            case 'B':
                colors=BLUE;
                break;
        }
        return colors;
    }
}
