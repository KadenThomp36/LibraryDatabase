//controller class for creating transactions (issuing books to user)
package Forms;

import Model.Book;
import Model.Library;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class IssueBook implements Initializable {
    @FXML private TextField txtBookSearch;
    @FXML private TextField txtUserSearch;
    @FXML private ListView lstBookList;
    @FXML private ListView lstUserList;
    @FXML private TextField txtBookID;
    @FXML private TextField txtUserID;
    @FXML private Button btnIssueBook;
    @FXML private Label lblMessageLabel;

    //observable lists for filling the lists of users and books in the issue book form
    private ObservableList<Book> booksList = FXCollections.observableArrayList();
    private ObservableList<User> usersList = FXCollections.observableArrayList();

    //holds the value of the selected book and user ID
    int selectedBookID;
    int selectedUserID;

    //reference to the main library
    Library mainLibrary;

    //Array list to hold the the users and books from the main library, for easier manipulation.
    ArrayList<User> tempUsers;
    ArrayList<Book> tempBooks;

    //set the reference to the main library, fill the temporary array lists and fill the lists on the form.
    public void setMainLibrary(Library mainLibrary) throws SQLException {
        this.mainLibrary = mainLibrary;
        tempUsers = mainLibrary.getUsers();
        tempBooks = mainLibrary.getBooks();
        FillBooks();
        FillUsers();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblMessageLabel.setText(""); //sets the message label to blank initially
    }

    //allows user to filter the books in the list based on what they are looking for, updates after each key stroke
    public void SearchBooks() throws SQLException {
        //gets the letter or string the user entered
        String searchString = txtBookSearch.getText();
        //fills temp books with a search book call to the main library.
        tempBooks = mainLibrary.searchBook(searchString);
        //clears the list
        lstBookList.getItems().clear();
        //refills the list.
        FillBooks();
    }

    //allows the user to filter users in the list based on what they are looking for, updates after each key stroke
    public void SearchUsers() throws SQLException {
        //gets the string the user entered to search with
        String searchString = txtUserSearch.getText();
        //fills temp users with a search user call to the main library.
        tempUsers = mainLibrary.searchUser(searchString);
        //clears the list
        lstUserList.getItems().clear();
        //refills the list.
        FillUsers();
    }

    //when the user clicks on a user or book, it will auto fill the ID field to match what they clicked on.
    public void PlaceBookID(){
        //temporary book object.
        Book tmpBook;
        int IDtoPlace; //ID that needs to be placed from the book.
        tmpBook = (Book) lstBookList.getSelectionModel().getSelectedItem(); //gets the book obj from the list.
        IDtoPlace = tmpBook.getID(); //gets the ID from the selected Obj
        txtBookID.setText(IDtoPlace + ""); //sets the text to the ID
    }
    public void PlaceUserID(){ //same as PlaceBookID
        User tmpUser;
        int IDtoPlace;
        tmpUser = (User) lstUserList.getSelectionModel().getSelectedItem();
        IDtoPlace = tmpUser.getID();
        txtUserID.setText(IDtoPlace + "");
    }

    //fills the books and the users lists based on what was searched for.
    public void FillBooks(){
        booksList.addAll(tempBooks); //temp books could be a refined search or the initial load
        lstBookList.setItems(booksList);
    }
    public void FillUsers(){ //same as fill books
        usersList.addAll(tempUsers);
        lstUserList.setItems(usersList);
    }

    //creates transaction in the main library
    public void CreateTransaction() throws SQLException {
        //checks to see if the text fields are valid and the transaction can be created.
        selectedBookID = Integer.parseInt(txtBookID.getText());
        selectedUserID = Integer.parseInt(txtUserID.getText());
        if(FormErrorChecking()) {
            mainLibrary.issueBook(selectedBookID, selectedUserID);
            //prints the result of the transaction.
            PrintMessageLog();
        }
    }

    //used for printing the message log found in the main library
    public void PrintMessageLog(){
        //array list that holds a local version of the message log found in the main library.
        ArrayList<String> msgLog = mainLibrary.msgLog;
        //string builder that will contain the data stored in msgLog.
        StringBuilder toMessageLabel = new StringBuilder();

        //iterate through all the messages.
        for (String msg : msgLog){
            //add the messages the string builder and insert a line break.
            toMessageLabel.append(msg).append("\n");
        }
        //if the string contains the keyword issued to then the text will be green to indicate the transaction went through
        if (toMessageLabel.toString().contains("issued to")){
            //change the color of label to green.
            lblMessageLabel.setTextFill(Color.GREEN);
        } else
            lblMessageLabel.setTextFill(Color.RED); //if transaction failed change color to red.
        lblMessageLabel.setText(toMessageLabel.toString()); //set the text to the string builder.
    }

    //Checks the validity of the data on the form.
    //returns a boolean indicating the validity of the data.
    public boolean FormErrorChecking(){
        boolean validData = true; //true unless something on the form is not valid.
        StringBuilder errorMsg = new StringBuilder(); //builds an error message based on what is wrong.
        Alert invalid = new Alert(Alert.AlertType.ERROR); //prepare to create an error message box.

        //if there is no book attached to the entered ID then not valid
        if(mainLibrary.getBook(selectedBookID) == null){
            errorMsg.append("Not a valid Book ID\n");
            validData = false;
        }
        //checks to see there is a user attached to the ID entered.
        if(mainLibrary.getUser(selectedUserID) == null){
            errorMsg.append("Not a valid User ID\n");
            validData = false;
        }
        //checks to see if what is entered in the text field is numeric
        try{
            selectedBookID = Integer.parseInt(txtBookID.getText());
        }catch (Exception e){
            errorMsg.append("Book ID must be an Integer\n");
            validData = false;
        }
        //checks to see if what is entered in the text field is numbers
        try{
            selectedUserID = Integer.parseInt(txtUserID.getText());
        }catch (Exception e){
            errorMsg.append("User ID must be an Integer\n");
            validData = false;
        }

        //if the data is not valid then show the error message.
        if(!validData){
            invalid.setTitle(("Invalid Entry"));
            invalid.setHeaderText(null);
            invalid.setContentText((errorMsg.toString()));
            invalid.initOwner(txtUserID.getScene().getWindow());
            invalid.show();
        }
        //return the validity of the data.
        return validData;
    }
}
