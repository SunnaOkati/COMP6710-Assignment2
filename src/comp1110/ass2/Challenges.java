package comp1110.ass2;

public class Challenges {
    private int challengeNumber;          // The problem number from the original board game
    private String colorConstraint;    // The color constraint of 3 * 3 matrix.

    static Challenges[] challenge = {

    }; //List of challenges.

    /**
     * Given the two parts of a game objective and a problem number, constructs an `Challenges` object
     *
     * @param colorConstraint    A string representing the color constraint of 3 * 3 matrix.
     *
     * @param challengeNumber   The problem number from the original board game,
     *                        a value from 1 to 80.
     */
    public Challenges(String colorConstraint, int challengeNumber) {

        //assert challengeNumber >= 1 && challengeNumber <= 80;
        this.colorConstraint = colorConstraint;
        this.challengeNumber = challengeNumber;
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



}
