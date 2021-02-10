//class that manages users books and transactions

package Model;

import Database.BookDAO;
import Database.TransactionsDAO;
import Database.UserDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Library {
    private final int MAX_BOOK_LIMIT = 3;
    private final int MAX_LOAN_DAYS = 14;
    private final String DB_PATH = "jdbc:sqlite:libraryDB.db";

    private TransactionsDAO transactions;
    private UserDAO users;
    private BookDAO books;
    public ArrayList<String> msgLog = new ArrayList<>();

    //empty no arg constructor
    public Library() throws SQLException {
        transactions = new TransactionsDAO(DB_PATH);
        users = new UserDAO(DB_PATH);
        books = new BookDAO(DB_PATH);
    }

    public ArrayList<Transaction> getTransactions() throws SQLException {
        return transactions.getAll();
    }

    public ArrayList<User> getUsers() throws SQLException {
        return users.getAll();
    }

    public ArrayList<Book> getBooks() throws SQLException {
        return books.getAll();
    }

    public void collectFine(User selectedUser) throws SQLException {
        selectedUser.setBalance(0);
        users.update(selectedUser);
    }

    //adds a book to the books array list
    //takes in a book book object
    public void addBook(Book bookToAdd) throws SQLException {
        books.insert(bookToAdd);
    }

    //adds a user to the user array list
    //takes in a user object
    public void addUser(User userToAdd) throws SQLException {
        users.insert(userToAdd);
    }

    //adds a transaction to the transaction array list
    //takes in a transaction object
    public void addTransaction(Transaction transactionToAdd) throws SQLException {
        transactions.insert(transactionToAdd);
    }

    //checks to see if a book is available for renting
    //takes in an integer that is the ID of the book to check
    //returns boolean determining if a book is available or not
    public boolean isAvailable(int bookID) throws SQLException {
        //receives a book ID and checks against all transactions to see if that book has been checked out
        for (Transaction transaction : transactions.getAll()){
            if (transaction.getBookID() == bookID && !transaction.isStatus()) {
                return false;
            }
        }
        return true; //if not found in transaction then it is available.
    }

    //counts the amount of books that a specific user has rented.
    //takes in a integer that is the ID of the user to check
    //returns an integer that contains the amount of books rented by a user.
    public int getBorrowCount(int userID) throws SQLException {
        int amtBooksBorrowed = 0; //holds the number of currently checked out books

        ArrayList<Transaction> checkedOutBooks =  transactions.getCurrent();


        //loop through all transactions and check to see if the transaction is specific to userID received
        for (Transaction transaction : checkedOutBooks) {
            //if it is the specified user and the book status is false (checked out) increment the counter.
            if (transaction.getUserID() == userID) {
                amtBooksBorrowed++;
            }
        }
        //return the amount
        return amtBooksBorrowed;
    }

    //gets all the books that a specified user has checked out.
    //takes in a userID to find all checked out books
    //returns all of the books a user has checked out as a String array list.
    public ArrayList<String> getBorrowedBooksNames(int userID) throws SQLException {
        ArrayList<String> bookNames = new ArrayList<>(); //holds all the checked out books a user has
        ArrayList<Transaction> checkedOutBooks =  transactions.getByUser(userID);
        int bookID; //holds the ID of the transaction that matches our search.


        //loop through all transactions and check to see if the transaction is specific to userID received
        for (Transaction transaction : checkedOutBooks) {
            //if it is the specified user and the book status is false (checked out) add it to the array list.
            if (!transaction.isStatus()) {
                bookID = transaction.getBookID();
                bookNames.add(getBook(bookID).getName()); //only need the name of the book.
            }
        }
        //return the array list.
        return bookNames;
    }

    //gets all of the books that are currently checked out by any user.
    //returns an array list of transactions that have yet to be returned.
    public ArrayList<Transaction> getAllCheckedOutBooks() throws SQLException {
        ArrayList<Transaction> checkedOutBooks = transactions.getCurrent(); //holds all checked out transactions.

        return checkedOutBooks;
    }

    //finds the due date for a specific date of checkout
    //takes in a date that is the date the book is issued
    //returns a date that is the due date of the book
    public LocalDate getDueDate(LocalDate issueDate){
        java.util.Date date = java.sql.Date.valueOf(issueDate);
        //creates a calendar object for easier date manipulation
        Calendar dueDate = Calendar.getInstance();
        //sets the calendar date to the issued date
        dueDate.setTime(date);
        dueDate.add(Calendar.DATE, MAX_LOAN_DAYS); //adds on the the max amount of days for a loan
        date = dueDate.getTime();
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); //returns the date version of the due date
    }

    //creates a new transaction when a book is issued to a specified user, only if they are allowed to receive the book
    //takes in a bookID and a userID to check and see if the book can be rented for that user and book
    //returns a boolean telling if the book was able to be issued or not.
    public boolean issueBook(int bookID, int userID) throws SQLException {

        msgLog.clear();

        FillMessageLog(bookID, userID);

        //if the user doesn't have too many books, the book is available and the balance is 0 then add the transaction.
        if (isAvailable(bookID) && getBorrowCount(userID) < MAX_BOOK_LIMIT && users.getUser(userID).getBalance() == 0){
            Transaction temp = new Transaction(bookID, userID); //create a transaction status is set to true in constructor
            addTransaction(temp);
            msgLog.add(books.getBook(bookID).getName() + " has been issued to " + users.getUser(userID).getName());
            msgLog.add("The due date is " + getDueDate(temp.getIssueDate()).toString());
            return true;
        }
        return false; //if there was an issue adding the book then don't create a new transaction.
    }

    //fills the message log for when a user attempts to check out a book.
    public void FillMessageLog(int bookID, int userID) throws SQLException {
        //if the users balance is greater than zero indicate there is an outstanding balance.
        if (users.getUser(userID).getBalance() > 0){
            msgLog.add("User has an outstanding balance.");
        }
        //if the user has the max books checked out indicate they are at the limit
        if (getBorrowCount(userID) >= MAX_BOOK_LIMIT){
            msgLog.add("User has too many books checked out.");
        }
        //if the books is already checked out then indicate it is not available.
        if (!isAvailable(bookID)){
            msgLog.add("This book is currently unavailable.");
        }
    }

    //changes the status of a book to false (not checked out) and computes the fine (if there is one)
    //takes in a bookID to change the status of that book
    //returns boolean if a book was successfully returned or not
    public boolean returnBook(int bookID) throws SQLException {
        msgLog.clear();
        //find the book in transactions array list

        for (Transaction transaction : transactions.getByBook(bookID)){
            if (!transaction.isStatus()) {

                transaction.setStatus(true); // change the status of the book.
                transactions.update(transaction);
                //compute the fine for the user and fill the message log to be printed on the form..
                msgLog.add("You returned " + getBook(bookID).getName());
                msgLog.add("Your outstanding balance is: " + computeFine(transaction));
                return true;
            }
        }
        //if there was nothing returned the book was not checked out add that to the message log.
        msgLog.add("This book was not checked out.");
        return false; //if transaction was not found.
    }

    //computes the fine for how many days the book was overdue
    //takes in a transaction object to determine the fine for that user for a specific transaction
    //returns the amount the fine will be as an integer.
    private double computeFine(Transaction transaction) throws SQLException {
        int userToFine = transaction.getUserID(); //user ID of the user who returned book
        int daysOver;
        LocalDate today = LocalDate.now(); //creates a new date at current time.
        LocalDate issueDate = getDueDate(transaction.getIssueDate()); //gets the date the book was issued.
        LocalDate dueDate = getDueDate(issueDate);
        User user = users.getUser(userToFine);

        daysOver = (int) ChronoUnit.DAYS.between(dueDate, today);
        //if the today is after the due date of the book then compute the fine and set the users balance.
        if (daysOver >=1){
            //calculates the amount of days over which is equal to the size of the fine
            //converts to int because amount is based on amount of days over not including hours.
            user.setBalance(daysOver); //sets balance to the daysOver which is same as fine
            users.update(user);
            return daysOver; //return the size of the fine.
        }
        return 0.0; // if today is not after the due date return 0
    }

    //searches all books that contain part of a title the user was looking for
    //takes in a string as a search or keyword
    //returns an array list that partially or completely matches what the user was looking for
    public ArrayList<Book> searchBook(String title) throws SQLException {

        //return the results of the search
        return books.getByQuery(title);
    }

    //searches all users that contain part of a name the user was looking for
    //takes in a string as a search or keyword
    //returns an array list that partially or completely matches what the user was looking for
    public ArrayList<User> searchUser(String name) throws SQLException {

        //return the results of the search
        return users.getByQuery(name);
    }

    //returns a book given the book ID
    //takes in a book ID
    //returns the book object that the book ID was associated with
    public Book getBook(int bookID) {
        try {
            return books.getBook(bookID);
        } catch (Exception e){
            return null;
        }
    }

    //returns a user given the user ID
    //takes in a user ID
    //returns the user object that the user ID was associated with
    public User getUser(int userID) {
        try {
            return users.getUser(userID);
        } catch (Exception e){
            return null;
        }
    }
}
