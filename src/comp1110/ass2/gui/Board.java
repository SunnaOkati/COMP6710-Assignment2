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
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseButton;
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
    private int ChosenNum=Integer.MAX_VALUE;
    private String boardState = "";

    ArrayList<Tile> boardArray = new ArrayList<>();
    LinkedList<ChosenPieceImage> placedImage=new LinkedList<ChosenPieceImage>();

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
    // Vbox because that's what the challenge box is
    public static void challengeGridVisualiser(String encodedChallenge, VBox box){

        double HBOXWIDTH = 0.0;
        double SQUARESIZE = 70.0;
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
                ImageView img = new ImageView(Challenge.squareColour(encodedChallenge.charAt(i)));
                img.setFitHeight(SQUARESIZE);
                img.setFitWidth(SQUARESIZE);
                rowTop.getChildren().add(img);
                i++;
            }
            // Second row
            while(i < 6){
                ImageView img = new ImageView(Challenge.squareColour(encodedChallenge.charAt(i)));
                img.setFitHeight(SQUARESIZE);
                img.setFitWidth(SQUARESIZE);
                rowMid.getChildren().add(img);
                i++;
            }
            // Third row
            while(i < 9){
                ImageView img = new ImageView(Challenge.squareColour(encodedChallenge.charAt(i)));
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


    //---------------------------------Task 7---------------------------------------------------------------------------
    //Author: Ranjth Raj, Rong HU
    //find  triangle with the shortest distance to (x,y)
    Tile findNearestTile(double x, double y){
        Tile nearTile=null;
        Iterator<Tile> iterTile=boardArray.iterator();
        //System.out.println(boardArray.size());
        double min_dis=Double.MAX_VALUE;
        while (iterTile.hasNext()){
            Tile tempTile=iterTile.next();
            //System.out.println(tempTile.getLayoutX());
            double temp_dis=tempTile.distance(x,y);
            if (temp_dis<min_dis){
                min_dis=temp_dis;
                nearTile=tempTile;
            }
        }
        return nearTile;
    }

    //component:play/reset buttons, paneBoard,
    private Parent createContent(){
        DropShadow ds = new DropShadow( 20, Color.AQUA );
        String[] pieces = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
        Group root = new Group();

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

        //ChosenPieceImage chosenPieceImage = new ChosenPieceImage(this);

        //Creating a horizontal box to display the chosen piece and rotate button
        HBox chosenPiece = new HBox();
        chosenPiece.setCache(false);
        chosenPiece.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
        chosenPiece.setSpacing(10);
        chosenPiece.getChildren().addAll(rotateButton);
        chosenPiece.setAlignment(Pos.CENTER);

        /*
        //Captures "rotate" button click event and uses math to change orientation of piece
        rotateButton.setOnAction(new EventHandler<ActionEvent>() {
            ChosenPieceImage selectedPiece=null;
            @Override
            public void handle(ActionEvent actionEvent) {
                 orientation = (orientation+ 1 )% 4;
                 if (selectedPiece!=null){
                     selectedPiece.setRotate(90 * orientation);
                 }
            }
        });
         */
        VBox challengePiece = new VBox();
        //Creating a vertical box to display the "challenge" button and the Challenge.
        VBox challenge = new VBox();
        challenge.setPrefSize(200,200);
        challenge.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: blue;");


        // -------------------------------------Task 8------------------------------------------------------------------------
        // Author: Victor
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
            challengePiece.getChildren().clear();
            challengeGridVisualiser((challengesList[rand.nextInt(challengesList.length)]),challengePiece);
            challengePiece.setLayoutX(boardArray.get(12).getLayoutX());
            challengePiece.setLayoutY(boardArray.get(12).getLayoutY());
            challengePiece.setBlendMode(BlendMode.MULTIPLY);
            });


        // Looks like vertically aligns playButton, chosenPiece and challenge
        VBox vboxRight = new VBox();
        vboxRight.getChildren().addAll( buttons,chosenPiece,challenge);

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
            //ChosenPieceImage temp_chosenPieceImage=new ChosenPieceImage(this);
            ImageView view = new ImageView();
            view.setImage(img);
            view.setCache(true);
            view.setPickOnBounds(true);
            view.setFitHeight(img.getHeight()/2);
            view.setFitWidth(img.getWidth()/2);
            char message = pieces[i].charAt(0);


            //Captures mouse click event and replaces chosen piece image in chose piece image view.
            view.setOnMouseClicked(( MouseEvent event ) ->
            { if (isPlaced) {
                    ChosenNum= (int)message-97;
                    //chosenPieceImage.imageView.setImage(img);
                    //chosenPieceImage.imageView.setLayoutX(700);
                    //chosenPieceImage.imageView.setLayoutY(200);
                    //chosenPieceImage.imageView.setFitWidth(chosenPieceImage.imageView.getImage().getWidth() * 0.7);
                    //chosenPieceImage.imageView.setFitHeight(chosenPieceImage.imageView.getImage().getHeight() * 0.7);
                    ChosenPieceImage temp_chosenPieceImage=new ChosenPieceImage((int)message-97,this);
                    temp_chosenPieceImage.imageView.setImage(img);
                    temp_chosenPieceImage.imageView.setLayoutX(700);
                    temp_chosenPieceImage.imageView.setLayoutY(200);
                    temp_chosenPieceImage.imageView.setFitWidth(temp_chosenPieceImage.imageView.getImage().getWidth() * 0.7);
                    temp_chosenPieceImage.imageView.setFitHeight(temp_chosenPieceImage.imageView.getImage().getHeight() * 0.7);
                    root.getChildren().add(temp_chosenPieceImage.imageView);
                    root.getChildren().get(1).toFront();
                    placedImage.add(temp_chosenPieceImage);
                    //System.out.println(placedImage.size());
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
        //Group root = new Group();
        challengePiece.toFront();
        root.getChildren().addAll(new VBox(hboxTop, hboxBottom),challengePiece);

        return root;
    }



    //-----------------------------------------Task 7----------------------------------------------------------------
    //Author: Ranjth Raj
    //Ron: should add setOnMouseReleased() event to make the piece place in the nearest location
    private class ChosenPieceImage extends ImageView{
        int typeNum;
        Board board;
        private double mouseX;
        private double mouseY;
        ImageView imageView;

        public ChosenPieceImage(int typeNum,Board board){
            this.typeNum=typeNum;
            this.board=board;
            imageView=new ImageView();
            imageView.setOnMousePressed(event->{
                MouseButton button = event.getButton();
                if (button==MouseButton.PRIMARY){
                    mouseX = event.getSceneX();
                    mouseY = event.getSceneY();
                }else {
                    double angle=imageView.getRotate();
                    imageView.setRotate(angle+90);
                }

            });
            imageView.setOnMouseDragged(event->{
                //System.out.println(event.getSceneX());
                //imageView.toFront();
                double movementX = event.getSceneX() - mouseX;
                double movementY = event.getSceneY() - mouseY;
                //imageView.setFitWidth(imageView.getImage().getWidth() * 0.7);
                //imageView.setFitHeight(imageView.getImage().getHeight() * 0.7);
                imageView.setLayoutX(imageView.getLayoutX()+movementX);
                imageView.setLayoutY(imageView.getLayoutY()+movementY);
                mouseX=event.getSceneX();
                mouseY=event.getSceneY();
            });
            imageView.setOnMouseReleased(event -> {
                MouseButton button = event.getButton();
                if (button==MouseButton.PRIMARY){
                    mouseX = imageView.getLayoutX();
                    mouseY= imageView.getLayoutY();
                    Tile nearLoc=board.findNearestTile(mouseX,mouseY);
                    double angle=imageView.getRotate();
                    if (angle%180==90&&(imageView.getFitWidth()-imageView.getFitHeight())%140==70){
                        imageView.setLayoutX(nearLoc.getLayoutX()-35);
                        imageView.setLayoutY(nearLoc.getLayoutY()-35);
                    }else {
                        imageView.setLayoutX(nearLoc.getLayoutX());
                        imageView.setLayoutY(nearLoc.getLayoutY());
                    }

                }

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
            rect.setStyle("-fx-border-style: solid;-fx-background-color: CAC9CC; -fx-min-width: 70; -fx-min-height:70; -fx-max-width:70; -fx-max-height: 70");


            getChildren().add(rect);
        }
        //return the distance from(x,y) to the centre of traingle
        private double distance(double x, double y){
            double disX=Math.pow(this.getLayoutX()-x,2);
            double disY=Math.pow(this.getLayoutY()-y,2);
            double dis=Math.sqrt(disX+disY);
            return dis;
        }
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.setTitle("IQ-Focus");
        primaryStage.show();
    }
}
