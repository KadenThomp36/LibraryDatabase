package Database;

import Model.Book;

import java.sql.*;
import java.util.ArrayList;

/**
 * Class that is used to interface between database and Model
 */
public class BookDAO {
    Connection connection = null;
    String url;

    /**
     * Argument constructor for receiving the URL connection string to database.
     * @param url string that contains path to database
     */
    public BookDAO(String url){
        this.url = url;
    }

    /**
     * Used to insert new book objects into database using SQL
     * @param b object reference to the book that will be added to database
     * @throws SQLException In case something goes wrong while talking to database
     */
    public void insert(Book b) throws SQLException {
        connection = DriverManager.getConnection(url);

        PreparedStatement preparedStatement = connection.prepareStatement("Insert into books(name, author, publisher, genre, ISBN, year) values (?,?,?,?,?,?)");
        preparedStatement.setString(1, b.getName());
        preparedStatement.setString(2, b.getAuthor());
        preparedStatement.setString(3, b.getPublisher());
        preparedStatement.setString(4, b.getGenre());
        preparedStatement.setString(5, b.getISBN());
        preparedStatement.setLong(6, b.getYear());

        preparedStatement.executeUpdate();

        connection.close();
    }

    /**
     * used to delete books objects from database using SQL
     * @param b object reference to the book that will be deleted from database
     * @throws SQLException In case something goes wrong while talking to database
     */
    public void delete(Book b) throws SQLException {
        connection = DriverManager.getConnection(url);

        PreparedStatement preparedStatement = connection.prepareStatement("delete from books where ID=?");
        preparedStatement.setInt(1, b.getID());
        preparedStatement.executeUpdate();

        connection.close();
    }

    /**
     * Used to update book objects in database using SQL
     * @param b object reference to the book that will be updated in the database
     * @throws SQLException In case something goes wrong while talking to database
     */
    public void update(Book b) throws SQLException {
        connection = DriverManager.getConnection(url);

        PreparedStatement preparedStatement = connection.prepareStatement("update books set" +
                "name = ?, author = ?, publisher = ?, genre = ?, ISBN = ?, year = ? where ID = ?");
        preparedStatement.setString(1, b.getName());
        preparedStatement.setString(2, b.getAuthor());
        preparedStatement.setString(3, b.getPublisher());
        preparedStatement.setString(4, b.getGenre());
        preparedStatement.setString(5, b.getISBN());
        preparedStatement.setLong(6, b.getYear());
        preparedStatement.setInt(7, b.getID());
        preparedStatement.executeUpdate();
        connection.close();
    }

    /**
     * Used to get all book objects currently stored in database
     * @return ArrayList of type book that will return all the books in database
     * @throws SQLException In case something goes wrong while talking to database
     */
    public ArrayList<Book> getAll() throws SQLException {
        connection = DriverManager.getConnection(url);

        PreparedStatement preparedStatement = connection.prepareStatement("select * from books");
        ResultSet rs = preparedStatement.executeQuery();
        ArrayList<Book> temp = new ArrayList<>();
        while (rs.next()){
            temp.add(new Book(rs.getInt(1), rs.getString(2), rs.getString(3),
                    rs.getString(4), rs.getString(5), rs.getString(6), rs.getLong(7)));
        }
        connection.close();
        return temp;
    }

    /**
     * used to search the database for names of books that are similar to the search string provided
     * @param searchString string that contains search word for database
     * @return ArrayList of books that contain all cases that match the search string
     * @throws SQLException In case something goes wrong talking to database
     */
    public ArrayList<Book> getByQuery(String searchString) throws SQLException {
        connection = DriverManager.getConnection(url);

        PreparedStatement preparedStatement = connection.prepareStatement("select * from books where name LIKE '" +searchString + "%'");
        ResultSet rs = preparedStatement.executeQuery();
        ArrayList<Book> temp = new ArrayList<>();
        while (rs.next()){
            temp.add(new Book(rs.getInt(1), rs.getString(2), rs.getString(3),
                    rs.getString(4), rs.getString(5), rs.getString(6), rs.getLong(7)));
        }
        connection.close();
        return temp;
    }

    /**
     * used to get a specific book from database depending on the bookID (using SQL)
     * @param bookID integer that holds a book ID
     * @return returns the Book object that was requested by the user
     * @throws SQLException In case something happens while talking to the database
     */
    public Book getBook(int bookID) throws SQLException {
        connection = DriverManager.getConnection(url);

        PreparedStatement preparedStatement = connection.prepareStatement("select * from books where ID = ?");
        preparedStatement.setInt(1, bookID);
        ResultSet rs = preparedStatement.executeQuery();
        Book temp = new Book(rs.getInt(1), rs.getString(2), rs.getString(3),
                rs.getString(4), rs.getString(5), rs.getString(6), rs.getLong(7));
        connection.close();

        return temp;
    }
}
