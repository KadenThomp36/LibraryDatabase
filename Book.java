//class used for creating books Objects

package Model;

public class Book {

    //attributes of a book
    private int ID;
    private String name;
    private String author;
    private String publisher;
    private String genre;
    private String ISBN;
    private long year;

    //argument constructor
    public Book(String name, String author, String publisher, String genre, String ISBN, long year){
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
        this.ISBN = ISBN;
        this.year = year;
    }

    public Book(int ID, String name, String author, String publisher, String genre, String ISBN, long year) {
        this.ID = ID;
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
        this.ISBN = ISBN;
        this.year = year;
    }

    //print out the book ID and the name of the book
    //returns a string of the output
    @Override
    public String toString() {
        return "Book ID: " + ID + " name: " + name;
    }

    //check to see if two books objects have the same ID
    //takes in an object that is casted to a book and compared
    //returns if the objects equal each other (ID)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return ID == book.ID;
    }

    //getters
    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getGenre() {
        return genre;
    }

    public String getISBN() {
        return ISBN;
    }

    public long getYear() {
        return year;
    }

    //setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setYear(long year) {
        this.year = year;
    }
}
