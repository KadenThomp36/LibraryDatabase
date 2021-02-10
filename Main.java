//Class that launches the main menu and starts the program.

package Forms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    //starts the main menu of the program.
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Main Menu.fxml"));
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    //launch the start routine.
    public static void main(String[] args){
        launch(args);
    }

}
