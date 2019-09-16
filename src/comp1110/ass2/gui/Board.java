package comp1110.ass2.gui;

import comp1110.ass2.FocusGame;
import comp1110.ass2.gittest.Main;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;

public class Board extends Application {

    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;

    private boolean isPlaced = true;
    private int orientation = 0;
    private String piece = "";

    private ArrayList<Tile> boardArray = new ArrayList<>();
    // FIXME Task 7: Implement a basic playable Focus Game in JavaFX that only allows pieces to be placed in valid places
    /*use javafx to create gui and event operating code in start()
     -->when the player put a new pieces:event
     -->update the placement string:event-->{operation}
     -->judge whether the placement is valid by referencing the methods we had created:
     isPiecePlacementWellFormed(String piecePlacement),isPlacementStringWellFormed(String placement),isPlacementStringValid(String placement)
      */

    // FIXME Task 8: Implement challenges (you may use challenges and assets provided for you in comp1110.ass2.gui.assets: sq-b.png, sq-g.png, sq-r.png & sq-w.png)

    /*
    --> Extend GUI to provide opportunities for the user to select a challenge number.
    --> Now given the challenge number, retrieve "objective" and "solution" which are strings(from TestUtility.java).
    --> "objective" is a string of 9 characters refering to color codes for 3 * 3 matrix.
    --> Construct and display the 3 * 3 matrix using assets sq-b.png, sq-g.png, sq-r.png & sq-w.png.
    --> "solution" is a concatenated string of piece placements to achieve the solution.
    --> Update expectedBoardState - a two dimensinal array of colors - based on the retreived data.
    --> Now Game is set by creating a focus game instance.
    */

    // FIXME Task 10: Implement hints
     /* -->create some new stages with the Text object of hint information from:getSolution(String challenge)   task 9
        -->when the player put a new pieces:event
        -->update the placement string:event-->{operation}
        -->judge the placement string(if(placement==xxx){....}), show the corresponding stage which contain information:stage.show()
     */

    // FIXME Task 11: Generate interesting challenges (each challenge may have just one solution)
    
    /* Generate random combinations of length 9 using {'R', 'B', 'W', 'G'}.
        --> Verify whether a solution exists using getSolution() 
        --> If exists, repeat task 8.
    */


    private Parent createContent(){


        DropShadow ds = new DropShadow( 20, Color.AQUA );
        String[] pieces = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};

        //Creating the play button
        Button playButton = new Button("Play");

        GridPane paneBoard = new GridPane();
        paneBoard.setStyle(" -fx-background-color: gray;" +" -fx-padding: 10;" + "-fx-border-style: solid;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-background-radius: 10;" + "-fx-background-insets: 5 ");
        //vboxLeft.setPrefSize(1200,720);

        /*
        for (int t = 0; t < (9 * 5); t ++){
            Tile tile = new Tile(t/9,t%9);
            boardArray.add(tile);
        }
         */

        // Looks like creates the board or at least its 2d array
        for (int i = 0; i< 5 ; i++){
            for (int j = 0; j < 9 ; j++){
                if (((i == 4) && (j == 0)) || ((i == 4) && (j == 8))){

                }
                else{
                    Tile temp = new Tile(i,j);
                    boardArray.add(temp);
                    //System.out.println(boardArray.get(i * j));
                    paneBoard.add(temp, j, i, 1, 1);
                }
            }
        }

        HBox chosenPiece = new HBox();

        // FIXME should this say rotate button instead of stop button?
        //Creating the stop button
        Button rotateButton = new Button("Rotate");
        ImageView chosenPieceImage = new ImageView();
        chosenPieceImage.setFitWidth(100);
        chosenPieceImage.setFitHeight(100);
        chosenPiece.setCache(false);

        //chosenPiece.setPrefSize(200,200);
        chosenPiece.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: blue;");



        // Challenge button
        Button challengeButton = new Button("Different Challenge");
        challengeButton.setOnAction(e-> System.out.println("BEEP BOOP"));

        chosenPiece.setSpacing(10);

        // Looks like when event occurs, use maths to change orientation of piece
        rotateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                orientation++;
                chosenPieceImage.setRotate(90 * orientation);
            }
        });
        chosenPiece.getChildren().addAll(chosenPieceImage, rotateButton);
        chosenPiece.setAlignment(Pos.CENTER);

        // Looks like it sets up a window just for the 3x3 challenge grid
        Pane challenge = new Pane();
        challenge.setPrefSize(200,200);
        challenge.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: blue;");

        // Looks like vertically aligns playButton, chosenPiece and challenge
        VBox vboxRight = new VBox();
        //vboxRight.setPrefSize(600,700);
        // Admittedly I(Victor) did just shove challenge button into here and call it a night, but it worked and I wanted to collab
        // with Ranjth in person to complete this and align it to make sure I don't break anything
        vboxRight.getChildren().addAll( playButton, chosenPiece, challenge, challengeButton);
        // Looks like horizontally aligns paneBoard and vboxRight
        HBox hboxTop = new HBox();
        hboxTop.setSpacing(paneBoard.getMaxWidth());
        //hboxTop.setPrefSize(1800,700);
        hboxTop.setSpacing(50);
        hboxTop.getChildren().addAll(paneBoard, vboxRight);

        VBox vboxPieces = new VBox();
        //vboxPieces.setPrefSize(1800,500);
        vboxPieces.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
        //Pane pane = new HBox();

        // Looks like this creates the grey grid
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER_LEFT);
        pane.setHgap(10);
        pane.setVgap(10);

        // Looks like the pieces are all accessed and printed through this for loop
        for (int i = 0; i < pieces.length; i++){
            Image img = new Image(Viewer.class.getResource("assets/"+pieces[i]+".png" ).toString());
            ImageView view = new ImageView();
            view.setImage(img);
            view.setCache(true);
            view.setPickOnBounds(true);
            view.setFitHeight(img.getHeight()/2);
            view.setFitWidth(img.getWidth()/2);
            String message = pieces[i];
            view.setOnMouseClicked(( MouseEvent event ) ->
            {
                if (isPlaced) {
                    //view.setEffect(ds);
                    piece = message;
                    //chosenPieceImage.setFitHeight(img.getHeight()/2);
                    //chosenPieceImage.setFitWidth(img.getWidth()/2);
                    chosenPieceImage.setImage(img);
                    isPlaced = false;
                }
                else{
                    view.setEffect(null);
                    isPlaced = true;
                }
            } );

            pane.add(view, i %5, i/5,1,1);
        }
        vboxPieces.getChildren().add(pane);

        HBox hboxBottom = new HBox();
        hboxBottom.setSpacing(10);
        //hboxBottom.setPrefSize(1800,500);
        hboxBottom.getChildren().addAll(vboxPieces);

        //Creating a Group object
        Group root = new Group();
        root.getChildren().add(new VBox(hboxTop, hboxBottom));
        return root;
    }

    private class  Tile extends StackPane{
        private int posX = 0;
        private int posY = 0;
        public Tile(int x, int y){
            posX = x;
            posY = y;

            Pane rect = new Pane();
            //rect.setStyle("-fx-background-color: grey; -fx-border-style: solid; -fx-border-width: 5; -fx-border-color: darkgrey; -fx-min-width: 70; -fx-min-height:70; -fx-max-width:70; -fx-max-height: 70");
            rect.setStyle("-fx-border-style: solid; -fx-min-width: 70; -fx-min-height:70; -fx-max-width:70; -fx-max-height: 70");
            setOnMousePressed(event ->{
                if(!isPlaced){
                    //if(FocusGame.isPlacementStringValid())
                    rect.setEffect(new DropShadow( 20, Color.GREEN));
                }
                else {
                    rect.setEffect(new DropShadow( 20, Color.INDIANRED));
                }
            });

            setOnMouseReleased(event -> {
                rect.setEffect(null);
                String placement = piece + posY + posX + orientation;
                System.out.println(placement);
                if(!isPlaced){
                    Image img = new Image(Viewer.class.getResource("assets/"+ piece +".png" ).toString());
                    ImageView view = new ImageView();
                    view.setImage(img);
                    view.setCache(true);
                    view.setFitHeight(img.getHeight() * 0.7);
                    view.setFitWidth(img.getWidth() * 0.7);
                    rect.getChildren().add(view);
                /*
                for ( Node check : boardArray){
                    if (check != rect){
                        if (check.getBoundsInParent().intersects(view.getBoundsInLocal())){
                            System.out.println(GridPane.getRowIndex(check) + " " + GridPane.getColumnIndex(check) );
                            //System.out.println("Enter");
                            //((Tile) check).getChildren().add(view);
                    }
                    }
                }
                */
                isPlaced = true;
                }

            });
            getChildren().add(rect);
        }
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }
}
