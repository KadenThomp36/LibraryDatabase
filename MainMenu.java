//Controller class for the main menu of the library where users can enter different functions.

package Forms;

import Model.Library;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


//no need to implement initialize.

//all functions are identical to the add user function, look there for comments.
public class MainMenu {

    //main library reference. This will be passed to all functions.
    public Library mainLibrary = new Library();

    public MainMenu() throws SQLException {
    }

    //Opens Add User form in MODAL mode.
    public void AddUser(ActionEvent e) throws IOException {
        Stage stage = new Stage(); //create a new stage
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Add New User.fxml")); //call the FXML file.
        Parent root = loader.load();

        //creates a handle to the controller class
        AddNewUser controller = loader.getController();
        controller.setMainLibrary(mainLibrary); //sets the main library.
        stage.setTitle("Add New User"); //sets the title of the form
        stage.initModality(Modality.WINDOW_MODAL); //MODAL mode
        Stage parentStage = (Stage) ((Node) e.getSource()).getScene().getWindow(); //sets the parent stage to this form.
        stage.initOwner(parentStage);
        stage.setScene(new Scene(root, 342, 400)); //sets the size of the new form.
        stage.show(); //shows the new form.
    }
    //Opens Add Book form in MODAL mode.
    public void AddBook(ActionEvent e) throws IOException{
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Add New Book.fxml"));
        Parent root = loader.load();
        AddNewBook controller = loader.getController();
        controller.setMainLibrary(mainLibrary);
        stage.setTitle("Add New Book");
        stage.initModality(Modality.WINDOW_MODAL);
        Stage parentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.initOwner(parentStage);
        stage.setScene(new Scene(root, 342, 400));
        stage.show();
    }
    //Opens Issue Book form in MODAL mode.
    public void IssueBook(ActionEvent e) throws IOException, SQLException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Issue Book.fxml"));
        Parent root = loader.load();
        IssueBook controller = loader.getController();
        controller.setMainLibrary(mainLibrary);
        stage.setTitle("Issue Book");
        stage.initModality(Modality.WINDOW_MODAL);
        Stage parentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.initOwner(parentStage);
        stage.setScene(new Scene(root, 600, 513));
        stage.show();
    }
    //Opens Return Book form in MODAL mode.
    public void ReturnBook(ActionEvent e) throws IOException, SQLException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Return Book.fxml"));
        Parent root = loader.load();
        ReturnBook controller = loader.getController();
        controller.setMainLibrary(mainLibrary);
        stage.setTitle("Return Book");
        stage.initModality(Modality.WINDOW_MODAL);
        Stage parentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.initOwner(parentStage);
        stage.setScene(new Scene(root, 342, 495));
        stage.show();
    }
    //Opens User Details form in MODAL mode.
    public void UserDetails(ActionEvent e) throws IOException, SQLException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("User Details.fxml"));
        Parent root = loader.load();
        UserDetails controller = loader.getController();
        controller.setMainLibrary(mainLibrary);
        stage.setTitle("User Details");
        stage.initModality(Modality.WINDOW_MODAL);
        Stage parentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.initOwner(parentStage);
        stage.setScene(new Scene(root, 342, 603));
        stage.show();
    }
    //Opens Issued Books Table form in MODAL mode.
    public void IssuedBooksTable(ActionEvent e) throws IOException, SQLException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Issued Books Table.fxml"));
        Parent root = loader.load();
        stage.setTitle("Issued Books Table");
        stage.initModality(Modality.WINDOW_MODAL);
        Stage parentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.initOwner(parentStage);
        stage.setScene(new Scene(root, 539, 310));
        stage.show();
        IssuedBooksTable controller = loader.getController();
        controller.setMainLibrary(mainLibrary);
    }
}
