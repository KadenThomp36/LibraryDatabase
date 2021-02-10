//controller class for filling out the data on each user based on who the librarian selects.

package Forms;

import Model.Library;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;


import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserDetails implements Initializable {

    @FXML  private ComboBox cboUsers;
    @FXML  private Label lblName;
    @FXML  private Label lblEmail;
    @FXML  private Label lblAddress;
    @FXML  private Label lblBirthday;
    @FXML  private Label lblType;
    @FXML  private Label lblBalance;
    @FXML  private Button btnCollectFine;
    @FXML  private ListView lstCheckedOutBooks;

    //array list that holds all user from the main library.
    private ArrayList<User> usersList = new ArrayList<>();
    //list that holds all of the users names
    private ObservableList<String> usersNames = FXCollections.observableArrayList();

    //list that holds all the books the user checked out and has not yet returned.
    private ObservableList<String> booksNames = FXCollections.observableArrayList();

    //holds a reference to the user that was selected in the combo box.
    User selectedUser;

    //holds reference to the main library
    Library mainLibrary;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //when the form is launched don't show collect fine until a user who has a balance is chosen.
        btnCollectFine.setVisible(false);
        //don't show and of the labels that output data. until a user is selected to fill these labels.
        ChangeLabelVisible(false);

    }

    //sets the reference to the main library.
    //adds all users to the user list to be processed.
    public void setMainLibrary(Library mainLibrary) throws SQLException {
        this.mainLibrary = mainLibrary;
        usersList.addAll(mainLibrary.getUsers());
        FillComboBox(); //process the users
    }

    //fills the combo box with only the users name
    public void FillComboBox(){
        //add users names to the observable list
        for(User user : usersList){
            usersNames.add(user.getName());
        }
        //show the observable list in the combo box.
        cboUsers.setItems(usersNames);
    }

    //called when the user selects a user from the combo box.
    public void UserSelected() throws SQLException {
        int userID; // holds the ID of the user chosen based on its position in the combo box.

        ChangeLabelVisible(true); //shows all the labels for user data.
        userID = cboUsers.getSelectionModel().getSelectedIndex(); //user ID correlates to the index in the combo box.
        selectedUser = mainLibrary.getUser(userID + 1); //get the selected user.

        //updates all the label fields with correct data.
        UpdateDisplay(userID + 1);
    }

    //changes all data label visibility based on what is passed to this method
    //takes in a boolean that determines the visibility of the labels
    public void ChangeLabelVisible(boolean visibility){
        lblName.setVisible(visibility);
        lblEmail.setVisible(visibility);
        lblAddress.setVisible(visibility);
        lblBirthday.setVisible(visibility);
        lblType.setVisible(visibility);
        lblBalance.setVisible(visibility);

    }

    //called when the user clicks collect fine button that only shows when there is a balance. sets the balance to 0
    public void CollectFine() throws SQLException {
        mainLibrary.collectFine(selectedUser);
        UpdateDisplay(selectedUser.getID() + 1);
    }

    //Fills all the books that a user has checked out to the list box.
    public void FillCheckedOutBooks(int userID) throws SQLException {
        //use a function found in the main library for getting all books a user has checked out based on ID
        booksNames.addAll(mainLibrary.getBorrowedBooksNames(userID));
        //output the checked out books to the list.
        lstCheckedOutBooks.setItems(booksNames);
    }

    //fills all the label data based on what user is selected.
    public void UpdateDisplay(int userID) throws SQLException {
        DecimalFormat currency = new DecimalFormat("0.00");

        //gets the properties from the selected user set earlier.
        lblName.setText(selectedUser.getName());
        lblEmail.setText(selectedUser.getEmail());
        lblAddress.setText(selectedUser.getAddress());
        lblBirthday.setText(selectedUser.getDateOfBirth().toString());
        //checks to see if the user is a student or a Faculty.
        if(selectedUser.getStudent()){
            lblType.setText("Student");
        } else {
            lblType.setText("Faculty");
        }
        //sets the balance associated with the selected user. and formats it as a currency.
        lblBalance.setText("$" + currency.format(selectedUser.getBalance()));

        //show the button if the balance for the user is greater than 0
        if (selectedUser.getBalance() > 0){
            btnCollectFine.setVisible(true);
        }else
            btnCollectFine.setVisible(false);

        //fills all books that a user has checked out currently.
        FillCheckedOutBooks(userID);
    }
}
