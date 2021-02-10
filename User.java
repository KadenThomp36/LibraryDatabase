//class that is used for creating User objects

package Model;

import java.time.LocalDate;

public class User {

    //user attributes
    private int ID;
    private String name;
    private String email;
    private String address;
    private LocalDate dateOfBirth;
    private boolean isStudent;
    private double balance;

    //argument constructor for user
    public User(String name, String email, String address, LocalDate dateOfBirth, boolean isStudent) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.isStudent = isStudent;
        this.balance = 0; //sets initial balance to 0
    }

    public User(int ID, String name, String email, String address, LocalDate dateOfBirth, boolean isStudent, double balance) {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.isStudent = isStudent;
        this.balance = balance;
    }
    //prints the user ID and their name
    //returns string of output
    @Override
    public String toString() {
        return "User ID: " + this.ID + "name: " + this.name;
    }

    //checks to see if two user Objects IDs are the same
    //returns boolean if the users are the same or not.
    //takes in object that is casted to a user.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return ID == user.ID;
    }

    //getters
    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public boolean getStudent() {
        return isStudent;
    }

    public double getBalance() {
        return balance;
    }

    //setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setStudent(boolean student) {
        isStudent = student;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
