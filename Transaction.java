//object used for creating transactions that are stored in an array list. relates book and user

package Model;

import java.time.LocalDate;
import java.util.Date;

public class Transaction {

    //attributes of a transaction
    private int bookID;
    private int userID;
    private LocalDate issueDate;
    private boolean status;

    //argument constructor for transaction
    public Transaction(int bookID, int userID) {
        this.bookID = bookID;
        this.userID = userID;
        setIssueDate(issueDate = LocalDate.now()); //sets the issue date to today
        this.status = false;
    }

    public Transaction(int bookID, int userID, LocalDate issueDate, boolean status) {
        this.bookID = bookID;
        this.userID = userID;
        this.issueDate = issueDate; //sets the issue date to today
        this.status = status;
    }

    //getters
    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getUserID() {
        return userID;
    }

    //setters
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    //checks the status of a book.
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
