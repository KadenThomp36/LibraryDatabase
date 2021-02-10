//controller class for adding new users to the system.
package Forms;

import Model.Library;
import Model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddNewUser implements Initializable {
    @FXML private TextField txtName;
    @FXML private TextField txtEmail;
    @FXML private TextArea txtAddress;
    @FXML private RadioButton rdoStudent;
    @FXML private RadioButton rdoFaculty;
    @FXML private DatePicker dtpDateOfBirth;
    @FXML private Button btnRegister;

    //reference to the main library
    private Library mainLibrary;

    // group to contain the radio buttons so only one can be selected.
    ToggleGroup group = new ToggleGroup();

    //sets the reference to the main library from the main menu
    public void setMainLibrary(Library mainLibrary){
        this.mainLibrary = mainLibrary;
    }

    //called when the user clicks Add user.
    public void RegisterUser() throws SQLException {
        User newUser; //used to create a new user

        //if the data is valid create the new user
        if (FormErrorChecking()){
            newUser = new User(txtName.getText(), txtEmail.getText(), txtAddress.getText(), dtpDateOfBirth.getValue(),
                    rdoStudent.isSelected());
            mainLibrary.addUser(newUser); //adds new user to the array list in the main library
            Stage stage = (Stage) btnRegister.getScene().getWindow(); //close the form
            stage.close();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //called when the form loads and sets the toggle group for the radio buttons and defaults the students to selected.
        rdoStudent.setToggleGroup(group);
        rdoStudent.setSelected(true);
        rdoFaculty.setToggleGroup(group);
    }

    //checks the validity of the data on the form.
    //returns a boolean indicating the validity of the data
    public boolean FormErrorChecking(){
        boolean validData = true; //true until something is incorrect
        StringBuilder errorMsg = new StringBuilder(); //builds a string of issues on the form
        Alert invalid = new Alert(Alert.AlertType.ERROR); //prepare to create error message box

        //checks to see if the name field is empty
        if(txtName.getText().equals("")){
            errorMsg.append("Name Field cannot be empty\n");
            validData = false;
        }
        //checks to see if the email field is empty
        if(txtEmail.getText().equals("")){
            errorMsg.append("Email Field cannot be empty\n");
            validData = false;
        }
        //checks to see if the address field is empty
        if(txtAddress.getText().equals("")){
            errorMsg.append("Address Field cannot be empty\n");
            validData = false;
        }
        //checks to see if a date of birth has been selected
        if(dtpDateOfBirth.getValue() == null){
            errorMsg.append("Must select a valid date of birth\n");
            validData = false;
        }

        //if the data is not valid show the error message box.
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
