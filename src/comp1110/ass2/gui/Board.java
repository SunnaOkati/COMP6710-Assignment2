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
import javafx.geometry.Insets;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;


import java.util.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.*;

import static comp1110.ass2.FocusGame.isPlacementStringValid;


public class  Board extends Application {

    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;

    private boolean isPlaced = true;
    //private int orientation = 0;
    //private char ChosenType=' ';
    private String placement = "";
    private String challengeString = "";
    Group root = new Group();
    GridPane pane = new GridPane();      //the pane of pieceButtons

    String rightPlacement="";
    ChosenPieceImage next_chosenPieceImage=null;
    ArrayList<Tile> boardArray = new ArrayList<>();
    LinkedList<ChosenPieceImage> placedImage=new LinkedList<ChosenPieceImage>();
    LinkedList<ImageView> ChosenButtons=new LinkedList<ImageView>(); //set of Chosed piece button


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
        //Group root = new Group();

        //Button style and size
        String buttonStyle = "-fx-font: 16 arial";
        String hintStyle = "-fx-base:#E93939; -fx-font: 16 arial";
        int[] buttonSize = {80,40};


        //Creating the play and reset buttons using "button" class
        button playButton = new button(buttonStyle, "Play", buttonSize);
        button resetButton = new button(buttonStyle, "Reset", buttonSize);
        button hintButton = new button(hintStyle, "Hint", buttonSize);



        //implement hintButton: give a hint of next step

        hintButton.setOnMousePressed(e-> {
            //get the hint placement
            String hintPlacement=Hint.giveHint(placement,rightPlacement,challengeString);
            System.out.println("hint: "+hintPlacement);
            //modify all the original placement
            Iterator<ChosenPieceImage> chosenPieceIter=placedImage.iterator();
            for (int i=0;i<placement.length();i=i+4){
                int x=hintPlacement.charAt(i+1)-48;
                int y=hintPlacement.charAt(i+2)-48;
                int angle=(hintPlacement.charAt(i+3)-48)*90;
                //get the original piece them set it to right placement
                ChosenPieceImage tempChosenPiece=chosenPieceIter.next();
                tempChosenPiece.imageView.setRotate(angle);
                //set location of the modified piece
                if (angle%180==90){
                    tempChosenPiece.imageView.setLayoutX(boardArray.get(y*9+x).getLayoutX()-(tempChosenPiece.imageView.getFitWidth()-tempChosenPiece.imageView.getFitHeight())/2);
                    tempChosenPiece.imageView.setLayoutY(boardArray.get(y*9+x).getLayoutY()+(tempChosenPiece.imageView.getFitWidth()-tempChosenPiece.imageView.getFitHeight())/2);
                }else{
                    tempChosenPiece.imageView.setLayoutX(boardArray.get(y*9+x).getLayoutX());
                    tempChosenPiece.imageView.setLayoutY(boardArray.get(y*9+x).getLayoutY());
                }
            }
            //if hint has next step, show it
            if (hintPlacement.length()>placement.length()){
                //get the parameter of the next step
                String nextStep=hintPlacement.substring(hintPlacement.length()-4);
                //System.out.println("next step: "+nextStep);
                char type=nextStep.charAt(0);
                int x=nextStep.charAt(1)-48;
                int y=nextStep.charAt(2)-48;
                int angle=(nextStep.charAt(3)-48)*90;
                // set the img and rotate it
                next_chosenPieceImage=new ChosenPieceImage(type,this);
                Image img = new Image(Board.class.getResource("assets/"+String.valueOf(type)+".png" ).toString());
                next_chosenPieceImage.imageView.setImage(img);
                next_chosenPieceImage.imageView.setFitWidth(next_chosenPieceImage.imageView.getImage().getWidth() * 0.7);
                next_chosenPieceImage.imageView.setFitHeight(next_chosenPieceImage.imageView.getImage().getHeight() * 0.7);
                next_chosenPieceImage.imageView.setRotate(angle);
                //set location of the modified piece
                if (angle%180==90){
                    next_chosenPieceImage.imageView.setLayoutX(boardArray.get(y*9+x).getLayoutX()-(next_chosenPieceImage.imageView.getFitWidth()-next_chosenPieceImage.imageView.getFitHeight())/2);
                    next_chosenPieceImage.imageView.setLayoutY(boardArray.get(y*9+x).getLayoutY()+(next_chosenPieceImage.imageView.getFitWidth()-next_chosenPieceImage.imageView.getFitHeight())/2);
                }else{
                    next_chosenPieceImage.imageView.setLayoutX(boardArray.get(y*9+x).getLayoutX());
                    next_chosenPieceImage.imageView.setLayoutY(boardArray.get(y*9+x).getLayoutY());
                }
                root.getChildren().add(next_chosenPieceImage.imageView);
            }

        });

        //when release mouse,show the original set
        hintButton.setOnMouseReleased(e->{
            Iterator<ChosenPieceImage> oriPieceIter=placedImage.iterator();
            for (int i=0;i<placement.length();i=i+4){
                int x=placement.charAt(i+1)-48;
                int y=placement.charAt(i+2)-48;
                int angle=(placement.charAt(i+3)-48)*90;
                //get the original piece them set it to right placement
                ChosenPieceImage tempChosenPiece=oriPieceIter.next();
                tempChosenPiece.imageView.setRotate(angle);
                //set location of the modified piece
                if (angle%180==90){
                    tempChosenPiece.imageView.setLayoutX(boardArray.get(y*9+x).getLayoutX()-(tempChosenPiece.imageView.getFitWidth()-tempChosenPiece.imageView.getFitHeight())/2);
                    tempChosenPiece.imageView.setLayoutY(boardArray.get(y*9+x).getLayoutY()+(tempChosenPiece.imageView.getFitWidth()-tempChosenPiece.imageView.getFitHeight())/2);
                }else{
                    tempChosenPiece.imageView.setLayoutX(boardArray.get(y*9+x).getLayoutX());
                    tempChosenPiece.imageView.setLayoutY(boardArray.get(y*9+x).getLayoutY());
                }
            }
            if (next_chosenPieceImage!=null){
                root.getChildren().remove(next_chosenPieceImage.imageView);
                next_chosenPieceImage=null;
            }

        });

        //implement resetButton: remove all the piece on the board
        resetButton.setOnAction(e-> {
            Iterator<ChosenPieceImage> chosenPieceIter=placedImage.iterator();
            while (chosenPieceIter.hasNext()){
                ChosenPieceImage tempChosenPiece=chosenPieceIter.next();
                root.getChildren().remove(tempChosenPiece.imageView);
                int i=tempChosenPiece.type-97;
                pane.add(ChosenButtons.get(i), i %5, i/5,1,1);
            }

            placedImage.clear();
            placement="";
        });


        //Creating a horizontal box with play and reset buttons
        HBox buttons = new HBox();
        buttons.setSpacing(5);
        buttons.setPadding(new Insets(20, 0, 10, 5));
        buttons.getChildren().addAll(playButton,resetButton,hintButton);



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
                    Tile temp = new Tile(j,i);
                    boardArray.add(temp);
                    //System.out.println(boardArray.get(i * j));
                    paneBoard.add(temp, j, i, 1, 1);
                }
            }
        }





        VBox challengePiece = new VBox();
        //Creating a vertical box to display the "challenge" button and the Challenge.
        VBox challenge = new VBox();
        Text startText=new Text("Start Area: ");
        Text hintText=new Text("(Click right mouse to rotate piece!)");
        hintText.setFont(Font.font ("Tahoma", 14));
        startText.setFont(Font.font ("Tahoma", 20));
        startText.setFill(Color.GREY);
        hintText.setFill(Color.GREY);
        challenge.setPrefSize(300,300);
        challenge.setStyle("-fx-padding: 5;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
        challenge.getChildren().addAll(startText,hintText);
        challenge.setAlignment(Pos.TOP_LEFT);


        // -------------------------------------Task 8------------------------------------------------------------------------
        // Author: Victor
        // Lines from random generation to challenge.Button inclusive
        // Challenge Virtual
        Random rand = new Random();


        //Vertically aligns buttons, chosenPiece and challenge
        // A test sample of challenges
        String[] challengesList = {"RRRBWBBRB","RRBBBBGGB","RRRRRWRWW","RRRBWBBRB"};

        // This challenge button generates a new challenge grid on mouseclick
        playButton.setOnAction(e-> {
            challengePiece.getChildren().clear();
            challengeString=challengesList[rand.nextInt(challengesList.length)];
            challengeGridVisualiser(challengeString,challengePiece);
            challengePiece.setLayoutX(boardArray.get(12).getLayoutX());
            challengePiece.setLayoutY(boardArray.get(12).getLayoutY());
            challengePiece.setBlendMode(BlendMode.MULTIPLY);
            rightPlacement= FocusGame.getSolution(challengeString);
            //System.out.println("solution: "+ rightPlacement);
            });


        // Looks like vertically aligns playButton, chosenPiece and challenge
        VBox vboxRight = new VBox();
        //vboxRight.setSpacing(5);
        vboxRight.getChildren().addAll( buttons,challenge);

        //Horizontally aligns board and vboxRight
        HBox hboxTop = new HBox();
        hboxTop.setSpacing(paneBoard.getMaxWidth());
        hboxTop.setSpacing(5);
        hboxTop.getChildren().addAll(paneBoard, vboxRight);

        //Creates a grid to hold the pieces

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

            ChosenButtons.add(i,view);

            //Captures mouse click event and replaces chosen piece image in chosen piece image view.
            view.setOnMouseClicked(( MouseEvent event ) ->
            {
                if (isPlaced) {
                    //ChosenType= message;
                    ChosenPieceImage temp_chosenPieceImage=new ChosenPieceImage(message,this);
                    temp_chosenPieceImage.imageView.setImage(img);
                    temp_chosenPieceImage.imageView.setLayoutX(680);
                    temp_chosenPieceImage.imageView.setLayoutY(150);
                    temp_chosenPieceImage.imageView.setFitWidth(temp_chosenPieceImage.imageView.getImage().getWidth() * 0.7);
                    temp_chosenPieceImage.imageView.setFitHeight(temp_chosenPieceImage.imageView.getImage().getHeight() * 0.7);
                    root.getChildren().add(temp_chosenPieceImage.imageView);
                    temp_chosenPieceImage.imageView.toFront();
                    pane.getChildren().remove(view);
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
        vboxPieces.setStyle("-fx-padding: 5;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
        vboxPieces.getChildren().add(pane);


        HBox hboxBottom = new HBox();
        hboxBottom.setSpacing(10);
        hboxBottom.getChildren().addAll(vboxPieces);

        //Creating a root node and aligning vertically the "hboxTop" and "hboxBottom".
        //Group root = new Group();
        root.getChildren().addAll(new VBox(hboxTop, hboxBottom),challengePiece);
        return root;
    }



    //-----------------------------------------Task 7----------------------------------------------------------------
    //Author: Ranjth Raj
    //Ron: should add setOnMouseReleased() event to make the piece place in the nearest location
    private class ChosenPieceImage extends ImageView{
        char type;
        Board board;
        private double mouseX;
        private double mouseY;
        ImageView imageView;
        Can can=new Can();

        public ChosenPieceImage(char type,Board board){
            this.type=type;
            this.board=board;
            imageView=new ImageView();
            imageView.setBlendMode(BlendMode.MULTIPLY);
            imageView.setOnMousePressed(event->{
                MouseButton button = event.getButton();
                if (button==MouseButton.PRIMARY){
                    mouseX = event.getSceneX();
                    mouseY = event.getSceneY();
                    //show the remove area
                    //can.setLayoutY(0);
                    can.setLayoutX(BOARD_WIDTH-80);
                    root.getChildren().addAll(can);
                }else {
                    double angle=imageView.getRotate();
                    imageView.setRotate(angle+90);
                }

            });
            imageView.setOnMouseDragged(event->{
                //System.out.println(event.getSceneX());
                //imageView.toFront();
                MouseButton button = event.getButton();
                if (button==MouseButton.PRIMARY){
                    double movementX = event.getSceneX() - mouseX;
                    double movementY = event.getSceneY() - mouseY;
                    imageView.setLayoutX(imageView.getLayoutX()+movementX);
                    imageView.setLayoutY(imageView.getLayoutY()+movementY);
                    mouseX=event.getSceneX();
                    mouseY=event.getSceneY();
                }
            });
            imageView.setOnMouseReleased(event -> {
                Tile nearLoc=null;
                MouseButton button = event.getButton();
                if (button==MouseButton.PRIMARY){
                    mouseX = imageView.getLayoutX();
                    mouseY= imageView .getLayoutY();

                    // if drag the piece to the remove area, remove it
                    if (mouseX>700){
                        //update placement string
                        for (int i=0;i<placement.length();i++){
                            if (placement.charAt(i)==type){
                                String replece1=placement.substring(0,i);
                                String replece2=placement.substring(i+4);
                                placement=replece1+replece2;
                                break;
                            }
                        }

                        placedImage.remove(this);
                        //show the Piecebutton that is corresponding with the removed piece
                        int i=this.type-97;
                        pane.add(ChosenButtons.get(i), i %5, i/5,1,1);
                        root.getChildren().remove(this.imageView);
                    }

                    double angle=imageView.getRotate();
                    //Tile nearLoc=board.findNearestTile(mouseX,mouseY);
                    if (angle%180==90){
                        nearLoc=board.findNearestTile(mouseX+(imageView.getFitWidth()-imageView.getFitHeight())/2,mouseY-(imageView.getFitWidth()-imageView.getFitHeight())/2);
                        imageView.setLayoutX(nearLoc.getLayoutX()-(imageView.getFitWidth()-imageView.getFitHeight())/2);
                        imageView.setLayoutY(nearLoc.getLayoutY()+(imageView.getFitWidth()-imageView.getFitHeight())/2);
                    }else{
                        nearLoc=board.findNearestTile(mouseX,mouseY);
                        imageView.setLayoutX(nearLoc.getLayoutX());
                        imageView.setLayoutY(nearLoc.getLayoutY());
                    }

                    //generate the placement string and verify it
                    int x=nearLoc.posX;
                    int y=nearLoc.posY;
                    int orientation=(int)(imageView.getRotate()/90)%4;
                    String tempPlacement=""+type+x+y+orientation;

                    //edit palcement when put the piece on the board
                    if (placedImage.contains(this)){
                        for (int i=0;i<placement.length();i++){
                            if (placement.charAt(i)==type){
                                String replece1=placement.substring(0,i);
                                String replece2=placement.substring(i+4);
                                placement=replece1+tempPlacement+replece2;
                                break;
                            }
                        }
                    }else {
                        placement=placement+tempPlacement;
                        placedImage.add(this);
                    }
                    //System.out.println(placement+" overlap: "+isPlacementStringValid(placement)+" challenge: "+FocusGame.verifyChallenge(tempPlacement,challengeString));
                    System.out.println("challenge String :"+challengeString);

                    //if placement is invalid, placement string should be the original one and move the piece to the start area
                    if (isPlacementStringValid(placement)==false||FocusGame.verifyChallenge(tempPlacement,challengeString)==true){
                        for (int i=0;i<placement.length();i++){
                            if (placement.charAt(i)==type){
                                String replece1=placement.substring(0,i);
                                String replece2=placement.substring(i+4);
                                placement=replece1+replece2;
                                break;
                            }
                        }
                        imageView.setLayoutX(680);
                        imageView.setLayoutY(150);
                        placedImage.remove(this);
                    }
                }
                root.getChildren().remove(can);
            });
        }
    }



    // vertical box of remove area
    private class  Can extends VBox{
        public Can(){
            Image canImg = new Image(Board.class.getResource("assets/can.png" ).toString());
            this.setPrefSize(100,BOARD_HEIGHT-50);
            ImageView viewCan= new ImageView();
            viewCan.setImage(canImg);
            viewCan.setFitWidth(50);
            viewCan.setFitHeight(50);
            this.getChildren().addAll(viewCan);
            this.setAlignment(Pos.CENTER);
            this.setStyle(" -fx-background-color: rgba(224,0,0,0.55);" + "-fx-border-radius: 5;" + "-fx-background-radius: 10;" );
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
            rect.setStyle("-fx-border-style: solid;-fx-background-color: #D5D5D5; -fx-min-width: 70; -fx-min-height:70; -fx-max-width:70; -fx-max-height: 70");


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

        Font font = Font.font(72);
        String startButtonStyle = "-fx-font: 72 arial";
        Button startButton = new Button("Start!");
        startButton.setFont(font);
        startButton.setStyle(startButtonStyle);
        VBox vBox = new VBox(50,startButton);
        vBox.setTranslateX(336.5);
        vBox.setTranslateY(500);
        primaryStage.setTitle("IQ-Focus");

        Text info = new Text(     "Press the play button to display a new challenge\n" +
                                  "Hold the Hint button for a new hint\n" +
                                  "Clicking on a piece will send it to piece box, there you can rotate it by clicking the right hand mouse button\n" +
                                  "From there, you can drag the piece to the board, or into the bin if you selected it by accident\n" +
                                  "After completing the game you can reset by pressing the reset button, and click play for a new challenge!\n" +
                                  "Have fun!!!");
        StackPane root = new StackPane();
        root.getChildren().add(info);
        root.getChildren().add(vBox);
        Scene scene = new Scene(root,933,700);
        primaryStage.setScene(scene);
        Scene secondScene = new Scene(createContent());
        startButton.setOnAction(e->primaryStage.setScene(secondScene));

        primaryStage.show();



    }
}
