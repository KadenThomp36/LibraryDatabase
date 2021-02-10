//Controller class for adding a new book to the system
package Forms;

import Model.Book;
import Model.Library;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddNewBook implements Initializable {
    @FXML private TextField txtName;
    @FXML private ListView lstPublisher;
    @FXML private TextField txtAuthor;
    @FXML private TextField txtISBN;
    @FXML private ComboBox cboGenre;
    @FXML private TextField txtYear;
    @FXML private Button btnRegister;

    //observable list for both publishers and Genre
    private ObservableList<String> publishers = FXCollections.observableArrayList();
    private ObservableList<String> Genre = FXCollections.observableArrayList();
    Library mainLibrary; //reference to the main library

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //runs when the form is initialized and adds the publishers and Genre to the form
        publishers.addAll("Allen and Unwin", "Disney", "Pearson", "Penguin", "McGraw Hill");
        Genre.addAll("Education", "Adventure", "Thriller", "History");
        lstPublisher.setItems(publishers);
        cboGenre.setItems(Genre);
    }

    //called from the main menu form and sets the main library for this application.
    public void setMainLibrary(Library mainLibrary){
        this.mainLibrary = mainLibrary;
    }

    //called when user clicks on Add Book button it will error check and then create a new book object and
    //add the new book to the array list in the Library class.
    public void AddBook() throws SQLException {
        Book newBook; //used for creating a new book

        if (FormErrorChecking()){ // if all fields are valid create the new book
            newBook = new Book(txtName.getText(), txtAuthor.getText(), lstPublisher.getSelectionModel().getSelectedItem().toString(),
                    cboGenre.getValue().toString(), txtISBN.getText(), Long.parseLong(txtYear.getText()));
            mainLibrary.addBook(newBook);

            //gets the current form and closes it after book has been added.
            Stage stage = (Stage) txtYear.getScene().getWindow();
            stage.close();
        }
    }

    //checks the validity of the data in the form before a new book can be added.
    //returns a boolean indicating whether data is valid or not.
    public boolean FormErrorChecking(){
        boolean validData = true; //initially valid unless something is incorrect.
        StringBuilder errorMsg = new StringBuilder(); //build a string of all incorrect items
        Alert invalid = new Alert(Alert.AlertType.ERROR); // prepare to create a error message box

        //checks to see if the name field is empty
        if(txtName.getText().equals("")){
            errorMsg.append("Name Field cannot be empty\n");
            validData = false;
        }
        //checks to see if the publisher field is empty
        if(lstPublisher.getSelectionModel().getSelectedItem() == null){
            errorMsg.append("Must Select a publisher\n");
            validData = false;
        }
        //checks to see if the author field is empty
        if(txtAuthor.getText().equals("")){
            errorMsg.append("Author Field cannot be empty\n");
            validData = false;
        }
        //shecks to see if the ISBN field is empty
        if(txtISBN.getText().equals("")){
            errorMsg.append("ISBN Field cannot be empty\n");
            validData = false;
        }
        //checks to see if a Genre has been selected
        if(cboGenre.getValue() == null){
            errorMsg.append("Must select a Genre from Combo Box\n");
            validData = false;
        }
        //checks to see if the year is in the form of a number
        try{
            Long.parseLong(txtYear.getText());
        } catch (Exception e){
            errorMsg.append("Year Field must be numeric\n");
            validData = false;
        }

        //checks to see if the year field is empty
        if(txtYear.getText().equals("")){
            errorMsg.append("Year Field cannot be empty\n");
            validData = false;
        }

        // if the data is not valid create an error message box
        if(!validData){
            invalid.setTitle(("Invalid Entry"));
            invalid.setHeaderText(null);
            invalid.setContentText((errorMsg.toString()));
            invalid.initOwner(btnRegister.getScene().getWindow());
            invalid.show();
        }

        //return the result of the error checking.
        return validData;
    }
}
