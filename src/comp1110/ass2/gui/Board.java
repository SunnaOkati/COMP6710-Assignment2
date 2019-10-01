package comp1110.ass2.gui;

import comp1110.ass2.FocusGame;
import comp1110.ass2.gittest.Main;
import java.io.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.css.Style;
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



import java.util.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.*;



public class  Board extends Application {

    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;

    private boolean isPlaced = true;
    private int orientation = 0;
    private String piece = "";
    private String boardState = "";

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

    // Author: Victor
    // Task 8
    // This regex checker is added just to clean up the fileScraper method
    public static String regexChecker(String regex, String checkString){
        Pattern checkRegex = Pattern.compile(regex);
        Matcher regexMatcher = checkRegex.matcher(checkString);

        // I'm unsure how regex groups work, but this makes sure that the output is a String
        // Git commit comment
        String myStr = null;

        // While searching through the string
        while(regexMatcher.find()){
            // Ensures that no empty strings are passed through
            if(regexMatcher.group().length() != 0){

                // If the string is empty give it a value, else add to its previous value
                if(myStr == null){
                    myStr = regexMatcher.group();
                }else{
                    myStr = myStr + regexMatcher.group();
                }

            }
        }

        // Just for debugging
        System.out.println("myStr final " + myStr);
        return myStr;
    }

    // Author: Victor
    // Task 8
    // Scrapes the TestUtility.java file
    public static String fileScraper(String fileName){
        // References each line 1 by 1
        String line = null;
        String output = null;

        try {
            FileReader fileReader = new FileReader(fileName);

            // BufferedReader is here for good practice
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                // While the line isn't empty, if it is one of the solutions,
                // which will be found out using the regex method
                // then print it to the terminal for testing
                if (regexChecker("[A,Z]{9}", line) != null) {
                    System.out.println(regexChecker("[A,Z]{9}", line));
                    if(output == null){
                        output = regexChecker("[A,Z]{9}", line);
                    }else{
                        output = output + regexChecker("[A,Z]{9}", line);
                    }
                }
            }


            bufferedReader.close();
        }
        // These catches were taken straight from the source material
        // FileReader does not compile without exception handling
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");

        }
        return output;
    }

    // Just a series of if statements that decide which square is chosen
    public static String squareColour(Character square){
        if(square == 'B') {
            return "comp1110/ass2/gui/assets/sq-b.png";
        }
        if(square == 'G') {
            return "comp1110/ass2/gui/assets/sq-g.png";
        }
        if(square == 'R') {
            return "comp1110/ass2/gui/assets/sq-r.png";
        }
        if(square == 'W'){
            return "comp1110/ass2/gui/assets/sq-w.png";
        }else{
            System.out.println(square);
            System.out.println("Invalid input, learn how to throw errors");
            return null;
        }
    }

    // Author: Victor
    // Task 8
    // Vbox because that's what the challenge box is
    public static void challengeGridVisualiser(String encodedChallenge, VBox box){

        double HBOXWIDTH = 20.0;
        double SQUARESIZE = 60.0;
        HBox rowTop = new HBox(HBOXWIDTH);
        HBox rowMid = new HBox(HBOXWIDTH);
        HBox rowBot = new HBox(HBOXWIDTH);

        // Makes the invisible grid layout for each colour
        /*
        ____________________
        |       rowTop
        |___________________
        |       rowMid
        |___________________
        |       rowBot
        |___________________
        */
        box.getChildren().addAll(rowTop,rowMid,rowBot);

        /*
        For every character in the encodedChallenge, get its associated img and add it to the vBox required
        i is the index of the grid co-ordinate
        9 is the limit because the grid always consists of 9 squares
        Each while loop makes each row by
            getting the appropriate square image, setting its size and then adding it to the appropriate Hbox
        */
        for(int i = 0; i < 9; i++){
            // First row
            // Get the square img, set it to size and then add it to the appropriate box
            while(i < 3){
                ImageView img = new ImageView(squareColour(encodedChallenge.charAt(i)));
                img.setFitHeight(SQUARESIZE);
                img.setFitWidth(SQUARESIZE);
                rowTop.getChildren().add(img);
                i++;
            }
            // Second row
            while(i < 6){
                ImageView img = new ImageView(squareColour(encodedChallenge.charAt(i)));
                img.setFitHeight(SQUARESIZE);
                img.setFitWidth(SQUARESIZE);
                rowMid.getChildren().add(img);
                i++;
            }
            // Third row
            while(i < 9){
                ImageView img = new ImageView(squareColour(encodedChallenge.charAt(i)));
                img.setFitHeight(SQUARESIZE);
                img.setFitWidth(SQUARESIZE);
                rowBot.getChildren().add(img);
                i++;
            }
        }

    }

        class button extends Button{

        public button(String style, String text, int[] size){
            this.setStyle(style);
            this.setText(text);
            this.setPrefSize(size[0], size[1]);
        }

        public button(String style, String text){
            this.setStyle(style);
            this.setText(text);
        }
    }

    //Author: Ranjth Raj
    //Task 7
    //component:play/reset buttons
    private Parent createContent(){
        DropShadow ds = new DropShadow( 20, Color.AQUA );
        String[] pieces = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};

        //Button style and size
        String buttonStyle = "-fx-base:#bcd4e6; -fx-font: 24 arial";
        int[] buttonSize = {100,50};

        //Creating the play and reset buttons using "button" class
        button playButton = new button(buttonStyle, "Play", buttonSize);
        button resetButton = new button(buttonStyle, "Reset", buttonSize);

        //Creating a horizontal box with play and reset buttons
        HBox buttons = new HBox();
        buttons.setSpacing(20);
        buttons.getChildren().addAll(playButton,resetButton);

        //Creating a grid pane to place the tiles in 2-D fashion (left->right; top->bottom)
        GridPane paneBoard = new GridPane();
        paneBoard.setStyle(" -fx-background-color: gray;" +" -fx-padding: 10;" + "-fx-border-style: solid;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-background-radius: 10;" + "-fx-background-insets: 5 ");


        //Creating a 2-D board of tiles using the "Tile" class
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

        //Creating the rotate button with "rotate.png" image beside the text
        button rotateButton = new button("-fx-font: 12 arial; -fx-base: #bcd4e6;", "Rotate");
        Image imageRotate = new Image(Board.class.getResource("assets/rotate.png" ).toString());
        ImageView imageRotateView = new ImageView();
        imageRotateView.setFitWidth(10);
        imageRotateView.setFitHeight(10);
        imageRotateView.setImage(imageRotate);
        rotateButton.setGraphic(imageRotateView);

        //Creating an image view to display the selected piece
        ChosenPieceImage chosenPieceImage = new ChosenPieceImage();

        //Creating a horizontal box to display the chosen piece and rotate button
        HBox chosenPiece = new HBox();
        chosenPiece.setCache(false);
        chosenPiece.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
        chosenPiece.setSpacing(10);
        chosenPiece.getChildren().addAll(chosenPieceImage, rotateButton);
        chosenPiece.setAlignment(Pos.CENTER);

        //Captures "rotate" button click event and uses math to change orientation of piece
        rotateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                 orientation = (orientation+ 1 )% 4;
                chosenPieceImage.setRotate(90 * orientation);
            }
        });




        //Creating a vertical box to display the "challenge" button and the Challenge.
        VBox challenge = new VBox();
        challenge.setPrefSize(200,200);
        challenge.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: blue;");

        // Author: Victor
        // Task 8
        // Lines from random generation to challenge.Button inclusive
        // Challenge Virtual
        Random rand = new Random();
        // Challenge button
        // Random is just added for testing
        button challengeButton = new button("-fx-font: 12 arial; -fx-base: #bcd4e6;", "Different Challenge");

        // This just verifies that the challenge button is being pressed
        // In the future it will be using the fileScraper method to return an appropriate value
        challengeButton.setOnAction(e-> System.out.println(rand.nextInt(5)));

        challenge.getChildren().add(challengeButton);

        //Vertically aligns buttons, chosenPiece and challenge
        // A test sample of challenges
        String[] challengesList = {"RRRBWBBRB","RRBBBBGGB","RRRRRWRWW","RRRBWBBRB"};

        // This challenge button generates a new challenge grid on mouseclick
        challengeButton.setOnAction(e-> {
            challenge.getChildren().clear();
            challenge.getChildren().add(challengeButton);
            challengeGridVisualiser((challengesList[rand.nextInt(challengesList.length)]),challenge);
            });


        // Looks like vertically aligns playButton, chosenPiece and challenge
        VBox vboxRight = new VBox();
        vboxRight.getChildren().addAll( buttons, chosenPiece, challenge);

        //Horizontally aligns board and the above vertically aligned box.
        HBox hboxTop = new HBox();
        hboxTop.setSpacing(paneBoard.getMaxWidth());
        hboxTop.setSpacing(50);
        hboxTop.getChildren().addAll(paneBoard, vboxRight);

        //Creates a grid to hold the pieces
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER_LEFT);
        pane.setHgap(10);
        pane.setVgap(10);

        //Creating the piece's image as a image view and adding it in a grid
        for (int i = 0; i < pieces.length; i++){
            Image img = new Image(Board.class.getResource("assets/"+pieces[i]+".png" ).toString());
            ImageView view = new ImageView();
            view.setImage(img);
            view.setCache(true);
            view.setPickOnBounds(true);
            view.setFitHeight(img.getHeight()/2);
            view.setFitWidth(img.getWidth()/2);
            String message = pieces[i];

            //Captures mouse click event and replaces chosen piece image in chose piece image view.
            view.setOnMouseClicked(( MouseEvent event ) ->
            { if (isPlaced) {
                    piece = message;
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

        //Creating a vertical box to hold the ten different pieces which are set in a grid.
        VBox vboxPieces = new VBox();
        vboxPieces.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
        vboxPieces.getChildren().add(pane);


        HBox hboxBottom = new HBox();
        hboxBottom.setSpacing(10);
        hboxBottom.getChildren().addAll(vboxPieces);

        //Creating a root node and aligning vertically the "hboxTop" and "hboxBottom".
        Group root = new Group();
        root.getChildren().add(new VBox(hboxTop, hboxBottom));
        return root;
    }

    //Author: Ranjth Raj
    //Task 7
    //Ron: should add setOnMouseReleased() event to make the piece place in the nearest location
    private class ChosenPieceImage extends ImageView{
        private double mouseX;
        private double mouseY;

        public ChosenPieceImage(){
            setFitWidth(100);
            setFitHeight(100);

            setOnMousePressed(event->{
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
                System.out.println("Mouse pressed at " + event.getSceneX() + " * " + event.getSceneY());
            });
            setOnMouseDragged(event->{
                double disX = event.getSceneX() - mouseX;
                double disY = event.getSceneY() - mouseY;
                setFitWidth(getImage().getWidth() * 0.7);
                setFitHeight(getImage().getHeight() * 0.7);
                this.setTranslateX(disX);
                this.setTranslateY(disY);
            });

        }
    }

    //Author: Ranjth Raj
    //Task 7
    //Class Tile to create tiles.
    private class  Tile extends StackPane{
        private int posX = 0;
        private int posY = 0;

        public Tile(int x, int y){
            posX = x;
            posY = y;

            //Creating a Pane to visualize a rectangle.
            Pane rect = new Pane();
            rect.setStyle("-fx-border-style: solid; -fx-min-width: 70; -fx-min-height:70; -fx-max-width:70; -fx-max-height: 70");

            //Captures mouse press event and highlights the tile green if a piece is selected otherwise red
            setOnMousePressed(event ->{
                if(!isPlaced){
                    rect.setEffect(new DropShadow( 20, Color.GREEN));
                }
                else {
                    rect.setEffect(new DropShadow( 20, Color.INDIANRED));
                }
            });

            //Captures mouse release event and verifies whether the placement is valid and proceeds with the placement in the board.
            setOnMouseReleased(event -> {
                rect.setEffect(null);
                String placement = piece + posY + posX + orientation;
                //System.out.println(placement);
                if((!isPlaced) && (FocusGame.isPlacementStringValid(placement))){
                    Image img = new Image(Viewer.class.getResource("assets/"+ piece +".png" ).toString());
                    ImageView view = new ImageView();
                    view.setImage(img);
                    view.setCache(false);
                    view.setFitHeight(img.getHeight() * 0.7);
                    view.setFitWidth(img.getWidth() * 0.7);
                    if(img.getHeight()!=img.getWidth() && (orientation %2 != 0))
                    {
                        if((img.getWidth() == 300) &&(img.getHeight()==200)){
                            view.setX(-35);
                            view.setY(35);
                        }
                        else{
                            view.setX(-70);
                            view.setY(70);
                        }
                    }
                    view.setRotate( 90 * orientation);
                    rect.getChildren().add(view);
                isPlaced = true;
                }

            });
            getChildren().add(rect);
        }
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.setTitle("IQ-Focus");
        primaryStage.show();
    }
}
