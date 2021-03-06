package comp1110.ass2.gui;
import comp1110.ass2.FocusGame;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//pop window with title and message
//@author Rong Hu
public class PopWindow {
    public static void display(String title, String message){
        Stage window=new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label popLabel=new Label();
        popLabel.setText(message);
        Button closeButton=new Button("close");
        closeButton.setOnAction(e->window.close());

        VBox layout=new VBox(10);
        layout.getChildren().addAll(popLabel,closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene popScene=new Scene(layout);
        window.setScene(popScene);
        window.showAndWait();
    }
    static String guidance="Press the play button to display a new challenge\n" +
            "Hold the Hint button for a new hint\n" +
            "Clicking on a piece will send it to piece box\nThere you can rotate it by clicking the right hand mouse button\n" +
            "From there, you can drag the piece to the board\nOr into the bin if you selected it by accident\n" +
            "After completing the game you can reset by pressing the reset button\nClick play for a new challenge!\n" +
            "Have fun!!!" ;
}
