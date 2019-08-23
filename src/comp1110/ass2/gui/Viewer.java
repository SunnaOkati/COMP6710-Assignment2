package comp1110.ass2.gui;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;




//import com.sun.prism.Image;

/**
 * A very simple viewer for piece placements in the IQ-Focus game.
 * <p>
 * NOTE: This class is separate from your main game class.  This
 * class does not play a game, it just illustrates various piece
 * placements.
 */
public class Viewer extends Application {

    /* board layout */
    private static final int SQUARE_SIZE = 60;
    private static final int VIEWER_WIDTH = 720;
    private static final int VIEWER_HEIGHT = 480;

    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private TextField textField;


    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     */
    void makePlacement(String placement) {
        // FIXME Task 4: implement the simple placement viewer
        //idea: divided the placement string in each 4 characters string(each piece)-->choose the pictures（/gui/assets/a.png） corresponding to
        // each piece by the orientation,type, location we obtained from the 4 characters string(each piece) --> put it on
        //javaFX stage



        //divided the placement string in each 4 characters string

        String piece[]=new String[10];
        for (int i=0;i<placement.length();i=i+4){
            piece[i/4]=placement.substring(i,i+4);
        }


        //set location of every piece
        for(int i=0;i<10;i++){
            char type=piece[i].charAt(0);
            int x=piece[i].charAt(1);
            int y=piece[i].charAt(2);
            int orientation=piece[i].charAt(3);
            //String delivery="file:assets"+String.valueOf(type)+".png";
            Image image=new Image(Viewer.class.getResource(URI_BASE + type + ".png").toString());
            ImageView a = new ImageView(image);
            //set height
            if (type=='f'&&(orientation==0||orientation==2)){
                a.setFitHeight(1*SQUARE_SIZE);
            }else if (((type=='a'||type=='b'||type=='c'||type=='d'||type=='e'||type=='g'||type=='j')&&(orientation==0||orientation==2))||type=='i'){
                a.setFitHeight(2*SQUARE_SIZE);
            }else if(((type=='a'||type=='e'||type=='f'||type=='g'||type=='d')&&(orientation==1||orientation==3))||type=='h'){
                a.setFitHeight(3*SQUARE_SIZE);
            }else if(((type=='b'||type=='c'||type=='j')&&(orientation==1||orientation==3))){
                a.setFitHeight(4*SQUARE_SIZE);
            }else{

            }
            //set width
            if (type=='f'&&(orientation==1||orientation==3)){
                a.setFitWidth(1*SQUARE_SIZE);
            }else if (((type=='a'||type=='b'||type=='c'||type=='d'||type=='e'||type=='g'||type=='j')&&(orientation==1||orientation==3))||type=='i'){
                a.setFitWidth(2*SQUARE_SIZE);
            }else if(((type=='a'||type=='e'||type=='f'||type=='g'||type=='d')&&(orientation==0||orientation==2))||type=='h'){
                a.setFitWidth(3*SQUARE_SIZE);
            }else if((type=='b'||type=='c'||type=='j')&&(orientation==0||orientation==2)){
                a.setFitWidth(4*SQUARE_SIZE);
            }else{

            }

            a.setRotate(90*orientation);
            a.setLayoutX(x*60);
            a.setLayoutY(y*60);
            root.getChildren().add(a);

        }









    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label label1 = new Label("Placement:");
        textField = new TextField();
        textField.setPrefWidth(300);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                makePlacement(textField.getText());
                textField.clear();
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button);
        hb.setSpacing(10);
        hb.setLayoutX(130);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("FocusGame Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        root.getChildren().add(controls);

        makeControls();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
