//class used to populate the table found in the Issued Books Table form.
//holds only data that pertains to the table for easier translation from the main transaction class.
//objects of this class are only transactions that have not been returned so it is more refined in that nature.
package Forms;

import java.time.LocalDate;
import java.util.Date;

public class Transactions {
        int bookID;
        int userID;
        LocalDate issueDate;

        public Transactions(int bookID, int userID, LocalDate issueDate){
            this.bookID = bookID;
            this.userID = userID;
            this.issueDate = issueDate;
        }

        public int getBookID() {
            return bookID;
        }

        public void setBookID(int bookID) {
            this.bookID = bookID;
        }

        public int getUserID() {
            return userID;
        }

        public void setUserID(int userID) {
            this.userID = userID;
        }

        public LocalDate getIssueDate() {
            return issueDate;
        }

        public void setIssueDate(LocalDate issueDate) {
            this.issueDate = issueDate;
        }
}
