//controller class for filling TableView with all the issued books

package Forms;

import Model.Library;
import Model.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class IssuedBooksTable {
    @FXML private TableView<Transactions> table;
    @FXML private TableColumn<Transactions, Integer> bookID = new TableColumn<>();
    @FXML private TableColumn<Transactions, Integer> userID = new TableColumn<>();
    @FXML private TableColumn<Transactions, LocalDate> issueDate = new TableColumn<>();

    //holds all the books that have been issued.
    ArrayList<Transaction> issuedBooks = new ArrayList<>();
    //holds the formatted version of the issued books
    ObservableList<Transactions> formattedIssuedBooks = FXCollections.observableArrayList();

    //reference to the main library.
    Library mainLibrary;

    //sets the main library and then binds the data in the table
    public void setMainLibrary(Library mainLibrary) throws SQLException {
        this.mainLibrary = mainLibrary;
        bookID.setCellValueFactory(new PropertyValueFactory<Transactions, Integer>("bookID"));
        userID.setCellValueFactory(new PropertyValueFactory<Transactions, Integer>("userID"));
        issueDate.setCellValueFactory(new PropertyValueFactory<Transactions, LocalDate>("issueDate"));

        //fills the table with issued books.
       FillTable();

    }

    //fills the table with issued books.
    public void FillTable() throws SQLException {
        //variables to translate the data found in the transaction into a new Transaction class
        int bookID;
        int userID;
        LocalDate issueDate;
        //gets all the books that are checked out, their transactions.
        issuedBooks = mainLibrary.getAllCheckedOutBooks();

        //for all issued books create a new formatted transaction for filling the table.
        for(Transaction transaction: issuedBooks){
            bookID = transaction.getBookID();
            userID = transaction.getUserID();
            issueDate = transaction.getIssueDate();
            formattedIssuedBooks.add(new Transactions(bookID, userID, issueDate));
        }
        //set the issued books to the table.
        table.setItems(formattedIssuedBooks);

    }
}

