import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;


/**
 * This class represents a Puzzle, which can be loaded from a text file.
 */
public class Puzzle {
    private final ArrayList<String> lines = new ArrayList<>();
    private boolean isFileRead;
    private int[] startPoint;
    private int[] endPoint;
    private int[][] maze;
    private boolean isLoaded;
    private File inputFile;

    /**
     * Read a puzzle file using a file dialog.
     */
    public void readFile() {
        // Create a file dialog window
        FileDialog windowFile = new FileDialog((Frame)null, "");
        windowFile.setMode(FileDialog.LOAD);
        windowFile.setDirectory("algo cw/PuzzleFiles");
        windowFile.setFile("*.txt");
        windowFile.setVisible(true);
        try {
            // Check for errors in the selected file
            checkFileErrors(windowFile);
        } catch (Exception e) {
            // Do nothing
        }
        this.inputFile = windowFile.getFiles()[0];
        this.isFileRead = true;
    }


    /**
     * Check for errors in the selected file.
     * @param fileDialog The file dialog window
     * @throws Exception If there is an error with the file
     */
    private void checkFileErrors(FileDialog fileDialog) throws Exception {
        String textFile = fileDialog.getFile();
        String fileExt = textFile.substring(Math.max(0, textFile.length()-4));

        // Check if the file has a .txt extension
        if (!fileExt.equals(".txt"))
            throw new Exception("the extension of the file should be '.txt'");

        File inputFile = fileDialog.getFiles()[0];
        // Check if a file has been selected
        if (inputFile.length() == 0) throw new Exception("Hasn't selected a file");
    }

    /**
     * Load the puzzle from the selected file.
     */
    public void loadPuzzles() {
        ArrayList<String> lines = this.getLines();
        int columnSize = lines.get(0).trim().length();
        this.maze = new int[lines.size()][columnSize];

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            int[] floor = new int[columnSize];

            for (int n = 0; n < columnSize; n++) {
                char charr = line.charAt(n);

                // Assign values to the maze based on characters in the file
                switch (charr) {
                    case '0' -> floor[n] = 1; // Wall
                    case '.' -> floor[n] = 0; // Empty space
                    case 'S' -> {
                        startPoint = new int[]{i, n}; // Start point
                        floor[n] = 0;
                    }
                    case 'F' -> {
                        endPoint = new int[]{i, n}; // End point
                        floor[n] = 0;
                    }
                }
            }
            maze[i] = floor;
        }
    }

    /**
     * Get the loaded puzzle.
     * @return The loaded puzzle
     */
    public int[][] getPuzzle() {
        if (isPuzzleLoaded()) {
            return this.maze;
        }
        return null;
    }

    /**
     * Check if a file has been read.
     * @return true if a file has been read, otherwise false
     */
    public Boolean isFileRead() {
        return this.isFileRead;
    }

    /**
     * Get the starting position of the puzzle.
     * @return The starting position of the puzzle
     */
    public int[] getStartingPos() {
        if (isPuzzleLoaded()) {
            return this.startPoint;
        }
        return null;
    }

    /**
     * Check if a puzzle has been loaded.
     * @return true if a puzzle has been loaded, otherwise false
     */
    public Boolean isPuzzleLoaded() {
        if (this.isFileRead()) {
            return this.isLoaded;
        }
        return null;
    }

    /**
     * Get the ending position of the puzzle.
     * @return The ending position of the puzzle
     */
    public int[] getEndingPos() {
        if (isPuzzleLoaded()) {
            return this.endPoint;
        }
        return null;
    }

    /**
     * Load lines from the file.
     * @throws IOException If there is an error reading the file
     */
    public void loadLines() throws IOException {
        if (this.isFileRead) {
            lines.addAll(Files.readAllLines(inputFile.toPath(), Charset.defaultCharset()));
            this.isLoaded = true;
        }
    }

    /**
     * Get the name of the loaded file.
     * @return The name of the loaded file
     */
    public String getFileName() {
        if (isLoaded) {
            return inputFile.getName();
        }
        return null;
    }

    /**
     * Get the lines from the file.
     * @return The lines from the file
     */
    public ArrayList<String> getLines() {
        if (this.isFileRead) {
            return this.lines;
        }
        return null;
    }

}
