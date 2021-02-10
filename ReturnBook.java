//controller class for returning a book and marking it as not checked out.

package Forms;

import Model.Book;
import Model.Library;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReturnBook implements Initializable {
    @FXML private TextField txtBookSearch;
    @FXML private ListView lstBookList;
    @FXML private TextField txtBookID;
    @FXML private Label lblMessageLabel;

    //Temporary list of all books
    ArrayList<Book> tempBooks;

    //used to translate the checked out books to the on form List. only holds checked out books.
    private ObservableList<Book> booksList = FXCollections.observableArrayList();

    //reference to the main library.
    Library mainLibrary;

    //sets the main library and fills the tempbooks array list with all books.
    public void setMainLibrary(Library mainLibrary) throws SQLException {
        this.mainLibrary = mainLibrary;
        tempBooks = mainLibrary.getBooks();
        FillBooks(); //fills the books list.
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblMessageLabel.setText(""); //sets the text of the message to blank before showing it to the user.
    }

    //search book method to filter list based on what user searches for.
    public void SearchBooks() throws SQLException {
        //holds the string the user enters.
        String searchString = txtBookSearch.getText();
        //holds the books that match the search.
        tempBooks = mainLibrary.searchBook(searchString);
        //clears the list.
        lstBookList.getItems().clear();
        //refills the list.
        FillBooks();
    }

    //places the book ID in the text field based on what the user clicks on in the list of books.
    public void PlaceBookID(){
        Book tmpBook; //holds a reference to the selected book.
        int IDtoPlace; //ID of the selected book.

        //sets the reference.
        tmpBook = (Book) lstBookList.getSelectionModel().getSelectedItem();
        IDtoPlace = tmpBook.getID(); //gets the ID
        txtBookID.setText(IDtoPlace + ""); //fills the text field.
    }

    //fills the books list with what was in temp books based on search or initialization.
    public void FillBooks(){
        booksList.addAll(tempBooks);
        lstBookList.setItems(booksList); //outputs the list to the form.
    }

    //called when the user clicks on return book.
    public void ReturnSelectedBook() throws SQLException {
        if(FormErrorChecking()){ //checks to make sure the data in the fields are valid.
            mainLibrary.returnBook(Integer.parseInt(txtBookID.getText())); //returns the book based on the book ID.
            PrintMessageLog(); //prints weather the books was returned or not.
        }
    }

    //checks the validity of the data on the form.
    //returns whether the data was valid or not.
    public boolean FormErrorChecking(){
        boolean validData = true; //true until something is not correct.
        Alert invalid = new Alert(Alert.AlertType.ERROR); //prepare to create an error message box.

        StringBuilder errorMsg = new StringBuilder(); //holds a built up error message.

        if(txtBookID.getText().equals("")){
            errorMsg.append("Book ID Cannot be blank\n");
            validData = false;
        }
        try{
            Integer.parseInt(txtBookID.getText());
        } catch (Exception e){
            errorMsg.append("Book ID must be an Integer\n");
            validData = false;
        }

        //if something is incorrect then print the error message box
        if(!validData){
            invalid.setTitle(("Invalid Entry"));
            invalid.setHeaderText(null);
            invalid.setContentText((errorMsg.toString()));
            invalid.initOwner(txtBookID.getScene().getWindow());
            invalid.show();
        }

        //return the validity of the data.
        return validData;
    }

    //prints what was found in the message log from the main library.
    public void PrintMessageLog(){
        //array list for holding a reference to the message log.
        ArrayList<String> msgLog = mainLibrary.msgLog;
        StringBuilder toMessageLabel = new StringBuilder(); //holds the contents of the message log with line breaks.
        for (String msg : msgLog){
            toMessageLabel.append(msg).append("\n");
        }
        //if the message log contains the keyword returned then the text will be green it was a success
        if (toMessageLabel.toString().contains("returned")){
            lblMessageLabel.setTextFill(Color.GREEN);
        } else //else there was no return made and the color will be red
            lblMessageLabel.setTextFill(Color.RED);

        //outputs the contents of the message log.
        lblMessageLabel.setText(toMessageLabel.toString());
    }
}
