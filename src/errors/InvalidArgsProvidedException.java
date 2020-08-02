// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: santhos7
// UT Student #: 1006094673
// Author: Shawn Santhoshgeorge
//
// Student2:
// UTORID user_name: shaiskan
// UT Student #: 1006243940
// Author: Keshavaa Shaiskandan
//
// Student3:
// UTORID user_name: patelt26
// UT Student #: 1005904103
// Author: Tirth Patel
//
// Student4:
// UTORID user_name: pate1101
// UT Student #: 1006315765
// Author: Abhay Patel
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package errors;

/**
 * Class InvalidArgsProvidedException handles 
 * expections related to invalid user input 
 */

@SuppressWarnings("serial")
public class InvalidArgsProvidedException extends Exception {

    /**
     * Handles the message to be sent to the console
     * 
     * @param message the message to be sent about error
     */
    public InvalidArgsProvidedException(String message) {
        //Returns an error message from where it was thrown
        super(message);
    }
}