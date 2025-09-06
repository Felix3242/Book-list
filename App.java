import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class App {

    // Method for performing a linear search
    public static ArrayList<Integer> linearSearch(ArrayList<String> arr, String target) {
        // List to store the indices of the target
        ArrayList<Integer> indices = new ArrayList<>();
    
        // Convert the target to lowercase 
        String lowerCaseTarget = target.toLowerCase();
    
        // Iterate through the array
        for (int i = 0; i < arr.size(); i++) {
            // If the current element matches the target, add its index to the list
            if (lowerCaseTarget.equals(arr.get(i).toLowerCase())) {
                indices.add(i);
            }
        }
    
        return indices;
    }

    // Recursive binary search method for strings
    public static ArrayList<Integer> binarySearch(String[][] arr, int left, int right, String target, int col) {
        // Base case: stop if range is invalid
        if (left > right) {
            return new ArrayList<>(); // Return an empty ArrayList if the search range is invalid
        }
        // Find the middle index
        int mid = left + (right - left) / 2;

        // Compare the middle element with the target
        int comparison = target.compareTo(arr[mid][col].toLowerCase());

        ArrayList<Integer> indices = new ArrayList<>();

        if (comparison == 0) {
            // Add the current index to the list
            indices.add(mid);

            // Combine results from the left and right searches
            indices.addAll(binarySearch(arr, left, mid - 1, target, col));
            indices.addAll(binarySearch(arr, mid + 1, right, target, col));
        } else if (comparison < 0) {
            // Search the left half
            indices.addAll(binarySearch(arr, left, mid - 1, target, col));
        } else {
            // Search the right half
            indices.addAll(binarySearch(arr, mid + 1, right, target, col));
        }

        return indices;
    }

    // Main method to set up the GUI and perform actions
    public static void main(String[] args) {
        String filePath = "C:\\Users\\14163\\Downloads\\U3A3_FelixGao\\BookList.txt";

        // Arrays to hold the data for ISBN, Book Title, and Author Name
        ArrayList<String> ISBN = new ArrayList<>();
        ArrayList<String> bookTitle = new ArrayList<>();
        ArrayList<String> authorName = new ArrayList<>();

        // Read the file and populate the arrays with ISBN, Book Title, and Author Name
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int count = 0;

            // Read each line and classify it into the appropriate list
            while ((line = br.readLine()) != null) {
                if (count % 3 == 0) {
                    ISBN.add(line.trim()); // Trim spaces from ISBN
                } 
                else if (count % 3 == 1) {
                    bookTitle.add(line.trim()); // Trim spaces from Book Title
                } 
                else {
                    authorName.add(line.trim()); // Trim spaces from Author Name
                }
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace if there is an error reading the file
        }

        // Create a 2D array to store book information
        String[][] bookList = new String[ISBN.size()][3];

        // Populate the 2D array with data from the lists
        for (int i = 0; i < ISBN.size(); i++) {
            bookList[i][0] = ISBN.get(i);         // Assign ISBN
            bookList[i][1] = bookTitle.get(i);    // Assign book title
            bookList[i][2] = authorName.get(i);   // Assign author name
        }

        // Initialize the JFrame (window)
        JFrame frame = new JFrame("Searching for a Book");
        frame.setSize(700, 700); // Set the size of the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close operation
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS)); // Set layout

        // Top panel with labels
        JPanel pnlTop = new JPanel(new GridLayout(2, 1));
        JLabel lblTitle = new JLabel("Children's Classic");
        JLabel lblText = new JLabel("This program will find the title, author and ISBN of a book");
        pnlTop.add(lblTitle); // Add the title label to the top panel
        pnlTop.add(lblText);  // Add the description label to the top panel

        // Middle panel with input fields and buttons
        JPanel pnlMid = new JPanel(new GridLayout(3, 3));
        JLabel lblISBN = new JLabel("Enter the ISBN #: ");
        JTextField txtISBN = new JTextField(); // Text field for ISBN input
        JButton btnISBN = new JButton("Find it!"); // Button for finding ISBN
        JLabel lblBookTitle = new JLabel("Enter the Book Title: ");
        JTextField txtBookTitle = new JTextField(); // Text field for Book Title input
        JButton btnBookTitle = new JButton("Find it!"); // Button for finding Book Title
        JLabel lblAuthorName = new JLabel("Enter the Author Name: ");
        JTextField txtAuthorName = new JTextField(); // Text field for Author Name input
        JButton btnAuthorName = new JButton("Find it!"); // Button for finding Author Name
        pnlMid.add(lblISBN); // Add ISBN label to middle panel
        pnlMid.add(txtISBN); // Add ISBN text field to middle panel
        pnlMid.add(btnISBN); // Add ISBN button to middle panel
        pnlMid.add(lblBookTitle); // Add Book Title label to middle panel
        pnlMid.add(txtBookTitle); // Add Book Title text field to middle panel
        pnlMid.add(btnBookTitle); // Add Book Title button to middle panel
        pnlMid.add(lblAuthorName); // Add Author Name label to middle panel
        pnlMid.add(txtAuthorName); // Add Author Name text field to middle panel
        pnlMid.add(btnAuthorName); // Add Author Name button to middle panel

        // Bottom panel with results and lists
        JPanel pnlBot = new JPanel(new GridLayout(4, 2));
        JLabel lblLinearSearch = new JLabel("Linear Search: ");
        JTextArea txtLinearSearch = new JTextArea(6, 20); // Text area for linear search results
        txtLinearSearch.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Add border
        txtLinearSearch.setEditable(false); // Set text area to be non-editable
        JScrollPane scpLinearSearch = new JScrollPane(txtLinearSearch); // Scroll pane for linear search

        JLabel lblBinarySearch = new JLabel("Binary Search:");
        JTextArea txtBinarySearch = new JTextArea(6, 20); // Text area for binary search results
        txtBinarySearch.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Add border
        txtBinarySearch.setEditable(false); // Set text area to be non-editable
        JScrollPane scpBinarySearch = new JScrollPane(txtBinarySearch); // Scroll pane for binary search
        JTextField txtError = new JTextField(); // Text field for displaying errors
        JButton btnList = new JButton("List"); // Button for listing all books
        JTextArea txtList = new JTextArea(80, 30); // Text area for displaying all books
        txtList.setEditable(false); // Set text area to be non-editable
        txtList.setPreferredSize(new Dimension(400, 800)); // Set preferred size for text area
        JScrollPane scpList = new JScrollPane(txtList); // Scroll pane for the list of all books

        pnlBot.add(lblLinearSearch); // Add Linear Search label to bottom panel
        pnlBot.add(scpLinearSearch); // Add Linear Search scroll pane to bottom panel
        pnlBot.add(lblBinarySearch); // Add Binary Search label to bottom panel
        pnlBot.add(scpBinarySearch); // Add Binary Search scroll pane to bottom panel
        pnlBot.add(txtError); // Add error text field to bottom panel
        pnlBot.add(btnList); // Add "List" button to bottom panel
        pnlBot.add(scpList); // Add scroll pane for listing all books to bottom panel

        frame.add(pnlTop); // Add top panel to the frame
        frame.add(pnlMid); // Add middle panel to the frame
        frame.add(pnlBot); // Add bottom panel to the frame

        // Make the frame visible
        frame.setVisible(true);
    

        // Add a key listener to the ISBN text field to allow only digits or 'x'/'X'
        txtISBN.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent k) {
                char keyChar = k.getKeyChar();  // Get the character from the key event

                // Allow only digits (0-9) or 'x' or 'X'
                if (!Character.isDigit(keyChar) && keyChar != 'x' && keyChar != 'X') {
                    k.consume();  // Consume the key event if it's not a digit or 'x'/'X'
                }
            }
        });

        // Add a key listener to the Book Title text field to allow only letters, spaces, dots, and apostrophes
        txtBookTitle.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent k) {
                char keyChar = k.getKeyChar();  // Get the character from the key event

                // Check if the character is a letter or space
                if (!Character.isLetter(keyChar) && keyChar != ' ' && keyChar != '.' && keyChar != '\'') {
                    k.consume();  // Consume the key event if it's not a letter or space
                }
            }
        });

        // Add a key listener to the Author Name text field to allow only letters, spaces, dots, and apostrophes
        txtAuthorName.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent k) {
                char keyChar = k.getKeyChar();  // Get the character from the key event

                // Check if the character is a letter or space
                if (!Character.isLetter(keyChar) && keyChar != ' ' && keyChar != '.' && keyChar != '\'') {
                    k.consume();  // Consume the key event if it's not a letter or space
                }
            }
        });

        // Add an action listener to the ISBN button
        btnISBN.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // Clear the output fields before starting the search
                txtLinearSearch.setText("");                
                txtBinarySearch.setText("");
                txtError.setText("");

                // Get the input value and convert it to lowercase
                String input = txtISBN.getText().toLowerCase();

                // Check if the ISBN input is empty
                if(input.isEmpty()){
                    txtError.setText("Please enter a proper ISBN");
                    return;
                }

                // Perform a linear search for the ISBN
                ArrayList<Integer> ISBNIndexes = linearSearch(ISBN, input);
                if (!ISBNIndexes.isEmpty()) {
                    // If found, display the details in the linear search area
                    for(int i = 0; i < ISBNIndexes.size(); i++){
                        txtError.setText("This ISBN exists in this book list");
                        txtLinearSearch.append(ISBN.get(ISBNIndexes.get(i)) + "\n");
                        txtLinearSearch.append(bookTitle.get(ISBNIndexes.get(i)) + "\n");
                        txtLinearSearch.append(authorName.get(ISBNIndexes.get(i)) + "\n");
                    }
                } else { 
                    // If not found, display an error message
                    txtError.setText("This ISBN does not exist in this book list");                
                }

                
                String[][] ISBNCopy = new String[bookList.length][];
                for (int i = 0; i < bookList.length; i++) {
                    ISBNCopy[i] = bookList[i].clone();  // Clone each row
                }

                // Sort the book list by ISBN
                Arrays.sort(ISBNCopy, (row1, row2) -> row1[0].compareTo(row2[0]));
                // Perform the binary search for the ISBN
                ArrayList<Integer> ISBNIndices = binarySearch(ISBNCopy, 0, ISBNCopy.length-1, input, 0);
                if (!ISBNIndices.isEmpty()) {
                    // If found, display the details in the binary search area
                    for(int i = 0; i < ISBNIndices.size(); i++){
                        txtError.setText("This ISBN does exist in this book list");                
                        txtBinarySearch.append(ISBNCopy[ISBNIndices.get(i)][0] + "\n");
                        txtBinarySearch.append(ISBNCopy[ISBNIndices.get(i)][1] + "\n");
                        txtBinarySearch.append(ISBNCopy[ISBNIndices.get(i)][2] + "\n");
                    }
                } else { 
                    // If not found, display an error message
                    txtError.setText("This ISBN does not exist in this book list");                
                }
            }
        });

        // Add an action listener to the Book Title button
        btnBookTitle.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // Clear the output fields before starting the search
                txtLinearSearch.setText("");
                txtBinarySearch.setText("");
                // Get the input value and convert it to lowercase
                String input = txtBookTitle.getText().toLowerCase();
                if(input.isEmpty()){
                    txtError.setText("Please enter a proper book title");
                    return;
                }
                // Perform a linear search for the book title
                ArrayList<Integer> bookTitleIndexes = linearSearch(bookTitle, input);
                if (!bookTitleIndexes.isEmpty()) {
                    // If found, display the details in the linear search area
                    for(int i = 0; i < bookTitleIndexes.size(); i++){
                        txtError.setText("This book title does exist in this book list");                
                        txtLinearSearch.append(ISBN.get(bookTitleIndexes.get(i)) + "\n");
                        txtLinearSearch.append(bookTitle.get(bookTitleIndexes.get(i)) + "\n");
                        txtLinearSearch.append(authorName.get(bookTitleIndexes.get(i)) + "\n");
                    }
                } else { 
                    // If not found, display an error message
                    txtError.setText("This book title does not exist in this book list");                
                }

                String[][] bookTitleCopy = new String[bookList.length][];
                for (int i = 0; i < bookList.length; i++) {
                    bookTitleCopy[i] = bookList[i].clone();  // Clone each row
                }

                // Sort the book list by book title
                Arrays.sort(bookTitleCopy, (row1, row2) -> row1[1].compareTo(row2[1]));
                // Perform the binary search for the book title
                ArrayList<Integer> bookTitleIndices = binarySearch(bookTitleCopy, 0, bookTitleCopy.length-1, input, 1);
                if (!bookTitleIndices.isEmpty()) {
                    // If found, display the details in the binary search area
                    for(int i = 0; i < bookTitleIndices.size(); i++){
                        txtError.setText("This book title does exist in this book list");                
                        txtBinarySearch.append(bookTitleCopy[bookTitleIndices.get(i)][0] + "\n");
                        txtBinarySearch.append(bookTitleCopy[bookTitleIndices.get(i)][1] + "\n");
                        txtBinarySearch.append(bookTitleCopy[bookTitleIndices.get(i)][2] + "\n");
                    }
                } else {
                    // If not found, display an error message
                    txtError.setText("This book title does not exist in this book list");                
                }

            }
        });

        // Add an action listener to the Author Name button
        btnAuthorName.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // Clear the output fields before starting the search
                txtLinearSearch.setText("");
                txtBinarySearch.setText("");
                // Get the input value and convert it to lowercase, prepending '#' to match the input format
                String input = "#" + txtAuthorName.getText().toLowerCase();

                if(input.equals("#")){
                    txtError.setText("Please enter a proper author name");
                    return;
                }

                // Perform a linear search for the author name
                ArrayList<Integer> authorNameIndexes = linearSearch(authorName, input);
                if (!authorNameIndexes.isEmpty()) {
                    // If found, display the details in the linear search area
                    for(int i = 0; i < authorNameIndexes.size(); i++){
                        txtError.setText("This author name does exist in this book list");                
                        txtLinearSearch.append(ISBN.get(authorNameIndexes.get(i)) + "\n");
                        txtLinearSearch.append(bookTitle.get(authorNameIndexes.get(i)) + "\n");
                        txtLinearSearch.append(authorName.get(authorNameIndexes.get(i)) + "\n");
                    }
                } else { 
                    // If not found, display an error message
                    txtError.setText("This author name does not exist in this book list");                
                }

                String[][] authorNameCopy = new String[bookList.length][];
                for (int i = 0; i < bookList.length; i++) {
                    authorNameCopy[i] = bookList[i].clone();  // Clone each row
                }

                // Sort the book list by author name
                Arrays.sort(authorNameCopy, (row1, row2) -> row1[2].compareTo(row2[2]));
                // Perform the binary search for the author name
                ArrayList<Integer> authorNameIndices = binarySearch(authorNameCopy, 0, authorNameCopy.length-1, input, 2);
                if (!authorNameIndices.isEmpty()) {
                    // If found, display the details in the binary search area
                    for(int i = 0; i < authorNameIndices.size(); i++){
                        txtError.setText("This author name does exist in this book list");                
                        txtBinarySearch.append(authorNameCopy[authorNameIndices.get(i)][0] + "\n");
                        txtBinarySearch.append(authorNameCopy[authorNameIndices.get(i)][1] + "\n");
                        txtBinarySearch.append(authorNameCopy[authorNameIndices.get(i)][2] + "\n");
                    }
                } else {
                    // If not found, display an error message
                    txtError.setText("This author name does not exist in this book list");                
                }
            }
        });

        // Add an action listener to the List button to display the entire book list
        btnList.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // Loop through the book list and display each entry
                for(int i = 0; i < ISBN.size(); i++){
                    txtList.append(ISBN.get(i) + "\n");
                    txtList.append(bookTitle.get(i) + "\n");
                    txtList.append(authorName.get(i) + "\n");
                }
            }
        });
    }
}
