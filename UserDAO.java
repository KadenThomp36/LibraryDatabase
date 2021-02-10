package Database;

import Model.Book;
import Model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Class that is used to interface between database and Model
 */
public class UserDAO {
    String url;
    Connection connection = null;

    /**
     * Argument constructor for receiving the URL connection string to database.
     * @param url string that contains path to database
     */
    public UserDAO(String url) {
        this.url = url;

    }

    /**
     * Used to insert new user objects into database using SQL
     * @param u object reference to the user that will be added to database
     * @throws SQLException In case something goes wrong while talking to database
     */
    public void insert(User u) throws SQLException {
        connection = DriverManager.getConnection(url);
        LocalDate javaDate = u.getDateOfBirth();
        java.sql.Date sqlDate = java.sql.Date.valueOf(javaDate);

        PreparedStatement preparedStatement = connection.prepareStatement("Insert into users(name, email, address, dateOfBirth, isStudent, balance) values (?,?,?,?,?,?)");
        preparedStatement.setString(1, u.getName());
        preparedStatement.setString(2, u.getEmail());
        preparedStatement.setString(3, u.getAddress());
        preparedStatement.setDate(4, sqlDate);
        preparedStatement.setBoolean(5, u.getStudent());
        preparedStatement.setDouble(6, u.getBalance());

        preparedStatement.executeUpdate();
        connection.close();
    }

    /**
     * used to delete user objects from database using SQL
     * @param u object reference to the user that will be deleted from database
     * @throws SQLException In case something goes wrong while talking to database
     */
    public void delete(User u) throws SQLException {
        connection = DriverManager.getConnection(url);
        PreparedStatement preparedStatement = connection.prepareStatement("delete from users where ID=?");
        preparedStatement.setInt(1, u.getID());
        preparedStatement.executeUpdate();
        connection.close();
    }

    /**
     * Used to update user objects in database using SQL
     * @param u object reference to the user that will be updated in the database
     * @throws SQLException In case something goes wrong while talking to database
     */
    public void update(User u) throws SQLException {
        connection = DriverManager.getConnection(url);
        LocalDate javaDate = u.getDateOfBirth();
        java.sql.Date sqlDate = java.sql.Date.valueOf(javaDate);

        PreparedStatement preparedStatement = connection.prepareStatement("update users set" +
                " name = ?, email = ?, address = ?, dateOfBirth = ?, isStudent = ?, balance = ? where ID = ?");
        preparedStatement.setString(1, u.getName());
        preparedStatement.setString(2, u.getEmail());
        preparedStatement.setString(3, u.getAddress());
        preparedStatement.setDate(4, sqlDate);
        preparedStatement.setBoolean(5, u.getStudent());
        preparedStatement.setDouble(6, u.getBalance());
        preparedStatement.setInt(7, u.getID());
        preparedStatement.executeUpdate();
        connection.close();

    }

    /**
     * Used to get all user objects currently stored in database
     * @return ArrayList of type user that will return all the users in database
     * @throws SQLException In case something goes wrong while talking to database
     */
    public ArrayList<User> getAll() throws SQLException {
        connection = DriverManager.getConnection(url);
        LocalDate javaDate;

        PreparedStatement preparedStatement = connection.prepareStatement("select * from users");
        ResultSet rs = preparedStatement.executeQuery();
        ArrayList<User> temp = new ArrayList<>();
        while (rs.next()){
            javaDate = rs.getDate(5).toLocalDate();
            temp.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3),
                    rs.getString(4), javaDate, rs.getBoolean(6), rs.getDouble(7)));
        }
        connection.close();
        return temp;
    }

    /**
     * used to search the database for names of users that are similar to the search string provided
     * @param searchString string that contains search word for database
     * @return ArrayList of users that contain all cases that match the search string
     * @throws SQLException In case something goes wrong talking to database
     */
    public ArrayList<User> getByQuery(String searchString) throws SQLException {
        connection = DriverManager.getConnection(url);
        LocalDate javaDate;

        PreparedStatement preparedStatement = connection.prepareStatement("select * from users where name LIKE '" +searchString + "%'");
        ResultSet rs = preparedStatement.executeQuery();
        ArrayList<User> temp = new ArrayList<>();
        while (rs.next()){
            javaDate = rs.getDate(5).toLocalDate();
            temp.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3),
                    rs.getString(4), javaDate, rs.getBoolean(6), rs.getDouble(7)));
        }
        connection.close();
        return temp;
    }

    /**
     * used to get a specific user from database depending on the userID (using SQL)
     * @param userID integer that holds a user ID
     * @return returns the User object that was requested by the user
     * @throws SQLException In case something happens while talking to the database
     */
    public User getUser(int userID) throws SQLException {
        connection = DriverManager.getConnection(url);
        LocalDate javaDate;

        PreparedStatement preparedStatement = connection.prepareStatement("select * from users where ID = ?");
        preparedStatement.setInt(1, userID);
        ResultSet rs = preparedStatement.executeQuery();
        javaDate = rs.getDate(5).toLocalDate();
        User temp = new User(rs.getInt(1), rs.getString(2), rs.getString(3),
                rs.getString(4), javaDate, rs.getBoolean(6), rs.getDouble(7));

        connection.close();
        return temp;
    }
}
