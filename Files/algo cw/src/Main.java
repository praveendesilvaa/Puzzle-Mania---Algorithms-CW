import java.io.File;
import java.util.Scanner;
import java.time.Duration;
import java.time.Instant;

/**
 * This program allows users to play Puzzle Mania, where they can load puzzles and find the shortest path
 * from the starting point to the ending point using the BFS algorithm.
 */
public class Main {
    private final static Scanner input = new Scanner(System.in);
    private static Puzzle parsedInputFile;

    /**
     * Main method to run the Puzzle Mania game.
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        // Displaying welcome message and menu options
        System.out.println("-----------------------");
        System.out.println("Welcome to Puzzle Mania");
        System.out.println("-----------------------");
        System.out.println("\nConsole Menu Hub");
        System.out.println("""

                1: Play Puzzle Mania
                2: Exit the Program""");
        boolean cycle = true;
        while (cycle) {
            // Validating user input for menu choice
            int choice = IntegerValidation("Please enter your choice", "Invalid keyword! Please Enter 1 to play Puzzle Mania or 2 to Exit", 1, 2);
            if (choice == 1) {
                loadInput(); // Loading puzzle
            } else if (choice == 2) {
                exitProgram(); // Exiting the program
                cycle = false;
            } else {
                System.out.println("Invalid choice! Please enter 1 or 2.");
            }
            if (cycle) {
                String repeatTask;
                while (true) {
                    // Asking user if they want to perform another task
                    System.out.print("\nDo you wish to perform another task? (Y/N): ");
                    repeatTask = input.next().toUpperCase();
                    if (repeatTask.equals("N")) {
                        exitProgram(); // Exiting the program
                        cycle = false;
                        break;
                    } else if (repeatTask.equals("Y")) {
                        // Displaying menu options again
                        System.out.println("Loading...");
                        System.out.println("-----------------------");
                        System.out.println("Console Menu Hub");
                        System.out.println("-----------------------");
                        System.out.println("1: Load a puzzle");
                        System.out.println("2: Print list of puzzles");
                        System.out.println("3: Print mazes and puzzles");
                        System.out.println("4: Exit the Program");
                        break;
                    } else {
                        System.out.println("Invalid choice! Please choose 'Y' or 'N' only ");
                    }
                }
            }
        }
    }

    /**
     * Exiting the program.
     */
    public static void exitProgram() {
        System.out.println("\nThank you for using Puzzle Mania!");
        System.out.println("\nexiting....");
        System.exit(0);
    }

    /**
     * Validating integer input within a specified range.
     * @param printStatement The message to display for user input
     * @param printOutOfRange The message to display if the input is out of range
     * @param min The minimum value allowed
     * @param max The maximum value allowed
     * @return The validated integer input
     */
    public static int IntegerValidation(String printStatement, String printOutOfRange, int min, int max) {
        int changeKey;
        while (true) {
            System.out.print("\n" + printStatement + ": ");
            try {
                changeKey = Integer.parseInt(input.next());
                if (changeKey >= min && changeKey <= max) {
                    break;
                } else {
                    System.out.println(printOutOfRange);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please retry with an integer.");
                input.nextLine();
            }
        }
        return changeKey;
    }

    /**
     * Calculating distance using the BFS algorithm.
     */
    private static void calculateTheDistance() {
        // Record start time
        Instant startTime = Instant.now();
        Duration timeElapsed; // Declare timeElapsed here

        // Check if a puzzle is loaded
        if (parsedInputFile == null) {
            System.out.println("No puzzle loaded.");
            return;
        }

        // Get the selected puzzle, start, and end coordinates
        int[][] selectedPuzzle = parsedInputFile.getPuzzle();
        int[] mazeStartCoordinates = parsedInputFile.getStartingPos();
        int[] mazeEndCoordinates = parsedInputFile.getEndingPos();

        // Create an instance of BFSAlgorithm
        BFSAlgorithm shortPath = new BFSAlgorithm();

        // Finding the shortest path
        System.out.println("\nShortest PATH: \n");
        System.out.println("File name: " + parsedInputFile.getFileName());
        System.out.println("Total number of lines: " + parsedInputFile.getLines().size());

        // Calculate the shortest path
        String path = shortPath.shortestDistance(selectedPuzzle, mazeStartCoordinates, mazeEndCoordinates);

        // Record end time after the path is calculated and printed
        Instant endTime = Instant.now();
        // Calculate time elapsed
        timeElapsed = Duration.between(startTime, endTime);

        // Print the path
        System.out.print(path);

        // Print message indicating the process is done
        System.out.println("\nDone! ");

        // Print time taken to calculate the path
        System.out.println("Time Taken: " + timeElapsed.toMillis() + " ms");
    }



    /**
     * Loading a new puzzle.
     */
    private static void loadInput() {
        while (true) {
            System.out.println("""

                    1: Select a puzzle
                    2: Print list of puzzles
                    3: Print mazes and puzzles
                    4: Exit the Program""");
            int loopVariable = IntegerValidation("Please enter your choice", "Invalid keyword! Please Enter 1 to select a puzzle, 2 to print the puzzle list, 3 to print mazes and puzzles, or 4 to exit the system", 1, 4);

            if (loopVariable == 1) {
                if (!selectFile()) {
                    printMazesAndPuzzles(); // Print the maze after loading
                    if (parsedInputFile != null) {
                        calculateTheDistance(); // Calculate and print shortest path
                    }
                }
            } else if (loopVariable == 2) {
                printListOfPuzzles(); // Print list of available puzzles
            } else if (loopVariable == 3) {
                if (!selectFile()) {
                    printMazesAndPuzzles(); // Print the maze after loading
                }
            } else if (loopVariable == 4) {
                exitProgram(); // Exiting the program
                break;
            } else {
                System.out.println("Invalid choice! Please Choose 1 to select a puzzle, 2 to print list of puzzles, 3 to print mazes and puzzles, or 4 to exit ");
            }
            // Wait for user input
            String repeatTask;
            while (true) {
                System.out.print("\nDo you wish to perform another task? (Y/N): ");
                repeatTask = input.next().toUpperCase();
                if (repeatTask.equals("N")) {
                    exitProgram(); // Exiting the program
                    break;
                } else if (repeatTask.equals("Y")) {
                    break;
                } else {
                    System.out.println("Invalid choice! Please choose 'Y' or 'N' only ");
                }
            }
            if (repeatTask.equals("N")) {
                break;
            }
        }
    }

    /**
     * Selecting a puzzle file.
     * @return true if there was an error reading the file, otherwise false.
     */
    private static boolean selectFile() {
        // Prompt the user to choose a puzzle from the dialog box
        System.out.println("Choose the puzzle from the Dialog box.");
        try {
            Puzzle file = new Puzzle();
            file.readFile(); // Read the selected file
            file.loadLines(); // Load lines from the file
            file.loadPuzzles(); // Load puzzles from the file
            // Check if the file is successfully read
            if (!file.isFileRead()) {
                // If file is not read successfully, throw an exception
                throw new Exception("File could not be loaded. Please try again");
            }

            // Assign the parsed puzzle file to parsedInputFile
            parsedInputFile = file;
            // Return false to indicate successful file selection
            return false;

        } catch (Exception e) {
            // Print the exception message
            System.out.println(e);
            // Print an error message
            System.out.println("\nError reading the puzzle file, please try again\n");
            // Return true to indicate that an error occurred while reading the puzzle file
            return true;
        }
    }

    /**
     * Printing the list of available puzzles.
     */
    private static void printListOfPuzzles() {
        File folder = new File("algo cw/PuzzleFiles");
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            System.out.println("List of puzzles available:");
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    System.out.println((i + 1) + ": " + listOfFiles[i].getName());
                }
            }
        } else {
            System.out.println("No puzzles found.");
        }
    }

    /**
     * Printing the loaded puzzle.
     */
    private static void printMazesAndPuzzles() {
        // Check if parsedInputFile is not null
        if (parsedInputFile != null) {
            int[][] puzzle = parsedInputFile.getPuzzle();
            // Check if puzzle is not null
            if (puzzle != null) {
                System.out.println("___________________");
                System.out.println("     Maze Map");
                System.out.println("___________________");
                // Print the maze
                for (int[] row : puzzle) {
                    for (int cell : row) {
                        // Print empty cell
                        if (cell == 0) {
                            System.out.print(".");
                        }
                        // Print obstacle
                        else if (cell == 1) {
                            System.out.print("0");
                        }
                        // Print other characters
                        else {
                            System.out.print((char) cell);
                        }
                    }
                    System.out.println();
                }
            } else {
                // Print message if no puzzles found
                System.out.println("No puzzles found.");
            }
        } else {
            // Print message if no puzzle loaded
            System.out.println("No puzzle loaded.");
        }
    }

}

//references

//https://www.geeksforgeeks.org/breadth-first-search-or-bfs-for-a-graph/
//https://www.google.com/search?client=safari&rls=en&q=bfs+implementation+in+java&ie=UTF-8&oe=UTF-8