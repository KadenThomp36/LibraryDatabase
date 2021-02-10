package Database;

import Model.Transaction;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Class that is used to interface between database and Model
 */
public class TransactionsDAO {
    Connection connection = null;
    String url;

    /**
     * Argument constructor for receiving the URL connection string to database.
     * @param url string that contains path to database
     */
    public TransactionsDAO(String url) throws SQLException {
        this.url = url;
    }

    /**
     * Used to insert new transaction objects into database using SQL
     * @param t object reference to the transaction that will be added to database
     * @throws SQLException In case something goes wrong while talking to database
     */
    public void insert(Transaction t) throws SQLException {
        connection = DriverManager.getConnection(url);
        LocalDate javaDate = t.getIssueDate();
        java.sql.Date sqlDate = java.sql.Date.valueOf(javaDate);

        PreparedStatement preparedStatement = connection.prepareStatement("Insert into libTransactions values (?,?,?,?)");
        preparedStatement.setInt(1, t.getBookID());
        preparedStatement.setInt(2, t.getUserID());
        preparedStatement.setDate(3, sqlDate);
        preparedStatement.setBoolean(4, t.isStatus());

        preparedStatement.executeUpdate();
        connection.close();
    }

    /**
     * used to delete transaction objects from database using SQL
     * @param t object reference to the transaction that will be deleted from database
     * @throws SQLException In case something goes wrong while talking to database
     */
    public void delete(Transaction t) throws SQLException {
        connection = DriverManager.getConnection(url);
        PreparedStatement preparedStatement = connection.prepareStatement("delete from libTransactions where userID=?");
        preparedStatement.setInt(1, t.getUserID());
        preparedStatement.executeUpdate();
        connection.close();
    }

    /**
     * Used to update transaction objects in database using SQL
     * @param t object reference to the transaction that will be updated in the database
     * @throws SQLException In case something goes wrong while talking to database
     */
    public void update(Transaction t) throws SQLException {
        connection = DriverManager.getConnection(url);
        LocalDate javaDate = t.getIssueDate();
        java.sql.Date sqlDate = java.sql.Date.valueOf(javaDate);

        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE libTransactions SET bookID = ?, userID = ?, issueDate = ?, status = ? WHERE bookID = ?");
        preparedStatement.setInt(1, t.getBookID());
        preparedStatement.setInt(2, t.getUserID());
        preparedStatement.setDate(3, sqlDate);
        preparedStatement.setBoolean(4, t.isStatus());
        preparedStatement.setInt(5, t.getBookID());
        preparedStatement.executeUpdate();
        connection.close();
    }

    /**
     * Used to get all transaction objects currently stored in database
     * @return ArrayList of type transaction that will return all the transactions in database
     * @throws SQLException In case something goes wrong while talking to database
     */
    public ArrayList<Transaction> getAll() throws SQLException {
        connection = DriverManager.getConnection(url);
        LocalDate javaDate;

        PreparedStatement preparedStatement = connection.prepareStatement("select * from libTransactions");
        ResultSet rs = preparedStatement.executeQuery();
        ArrayList<Transaction> temp = new ArrayList<>();
        while (rs.next()){
            javaDate = rs.getDate(3).toLocalDate();
            temp.add(new Transaction(rs.getInt(1), rs.getInt(2), javaDate, rs.getBoolean(4)));
        }
        connection.close();
        return temp;
    }

    /**
     * Used to get only to get transactions that are checked out in the library, ignore non checked out transactions
     * @return ArrayList of transactions that are in a checked out state
     * @throws SQLException In case something goes wrong while talking to database
     */
    public ArrayList<Transaction> getCurrent() throws SQLException {
        connection = DriverManager.getConnection(url);
        LocalDate javaDate;

        PreparedStatement preparedStatement = connection.prepareStatement("select * from libTransactions where status = false");

        ResultSet rs = preparedStatement.executeQuery();
        ArrayList<Transaction> temp = new ArrayList<>();
        while (rs.next()){
            javaDate = rs.getDate(3).toLocalDate();
            temp.add(new Transaction(rs.getInt(1), rs.getInt(2), javaDate, rs.getBoolean(4)));
        }
        connection.close();
        return temp;
    }

    /**
     * used to return a specific transaction by the user ID using SQL
     * @param userID Id of the user in which their transactions you would like to return
     * @return Array list of transactions made by a specific user
     * @throws SQLException In case there is a issue while talking to database
     */
    public ArrayList<Transaction> getByUser(int userID) throws SQLException {
        connection = DriverManager.getConnection(url);
        LocalDate javaDate;

        PreparedStatement preparedStatement = connection.prepareStatement("select * from libTransactions where userID = ?");
        preparedStatement.setInt(1, userID);
        ResultSet rs = preparedStatement.executeQuery();
        ArrayList<Transaction> temp = new ArrayList<>();
        while (rs.next()){
            javaDate = rs.getDate(3).toLocalDate();
            temp.add(new Transaction(rs.getInt(1), rs.getInt(2), javaDate, rs.getBoolean(4)));
        }
        connection.close();
        return temp;
    }

    /**
     * used to return a specific transaction by the book ID using SQL
     * @param bookID Id of the book in which their transactions you would like to return
     * @return Array list of transactions made by a specific user
     * @throws SQLException In case their is a issue while talking to database
     */
    public ArrayList<Transaction> getByBook(int bookID) throws SQLException {
        connection = DriverManager.getConnection(url);
        LocalDate javaDate;

        PreparedStatement preparedStatement = connection.prepareStatement("select * from libTransactions where bookID = ?");
        preparedStatement.setInt(1, bookID);
        ResultSet rs = preparedStatement.executeQuery();
        ArrayList<Transaction> temp = new ArrayList<>();
        while (rs.next()){
            javaDate = rs.getDate(3).toLocalDate();
            temp.add(new Transaction(rs.getInt(1), rs.getInt(2), javaDate, rs.getBoolean(4)));
        }
        connection.close();
        return temp;
    }
}
