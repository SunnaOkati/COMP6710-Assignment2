package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class Board extends Application {

    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;

    // FIXME Task 7: Implement a basic playable Focus Game in JavaFX that only allows pieces to be placed in valid places
    /*use javafx to create gui and event operating code in start()
     -->when the player put a new pieces:event
     -->update the placement string:event-->{operation}
     -->judge whether the placement is valid by referencing the methods we had created:
     isPiecePlacementWellFormed(String piecePlacement),isPlacementStringWellFormed(String placement),isPlacementStringValid(String placement)
      */

    // FIXME Task 8: Implement challenges (you may use challenges and assets provided for you in comp1110.ass2.gui.assets: sq-b.png, sq-g.png, sq-r.png & sq-w.png)

    /*
    * Extend GUI to provide opportunities for the user to select a challenge number.
    * Now given the challenge number, retrieve objective and "solution" from TestUtility.java. (which is a string)
    * "objective" is a string of 9 characters refering to color codes for 3 * 3 matrix.
    * Construct and display the 3 * 3 matrix using assets sq-b.png, sq-g.png, sq-r.png & sq-w.png
    * "solution" is a concatenated string of piece placements to achieve the solution
    * Update expectedBoardState - a two dimensinal array of colors - based on the retreived solution 
    */
    // FIXME Task 10: Implement hints
     /* -->create some new stages with the Text object of hint information from:getSolution(String challenge)   task 9
        -->when the player put a new pieces:event
        -->update the placement string:event-->{operation}
        -->judge the placement string(if(placement==xxx){....}), show the corresponding stage which contain information:stage.show()
     */

    // FIXME Task 11: Generate interesting challenges (each challenge may have just one solution)

    @Override
    public void start(Stage primaryStage) {

    }
}
