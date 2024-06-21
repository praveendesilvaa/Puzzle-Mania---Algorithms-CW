import java.util.LinkedList;
import java.util.Queue;

public class BFSAlgorithm {

    /**
     * Finds the shortest path from start position to end position in a maze using BFS algorithm.
     *
     * @param loadedMaze    The maze represented as a 2D array where 0 represents an empty cell and 1 represents an obstacle.
     * @param mazeStartPos  The starting position in the maze. mazeStartPos[0] represents the row and mazeStartPos[1] represents the column.
     * @param mazeEndPos    The ending position in the maze. mazeEndPos[0] represents the row and mazeEndPos[1] represents the column.
     * @return              The shortest path from start to end position in the maze.
     */
    public String shortestDistance(int[][] loadedMaze, int[] mazeStartPos, int[] mazeEndPos) {
        int rows = loadedMaze.length;
        int columns = loadedMaze[0].length;
        boolean[][] visited = new boolean[rows][columns];

        // Queue to store the coordinates to be visited
        Queue<Coordinate> queueList = new LinkedList<>();
        queueList.offer(new Coordinate(mazeStartPos[0], mazeStartPos[1], 0, "", new int[]{}, 1));

        // Directions in which movement is allowed
        String[] movableDirections = {"Move up to", "Move down to", "Move left to", "Move right to"};

        // Coordinates for movement in each direction
        int[][] movableCoordinates = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        // BFS algorithm
        while (!queueList.isEmpty()) {
            Coordinate position = queueList.poll();
            if (position.row == mazeEndPos[0] && position.column == mazeEndPos[1]) {
                // If the end position is reached, return the path
                return position.toString();
            }

            // Explore all possible movements from the current position
            for (int i = 0; i < movableCoordinates.length; i++) {
                // Initialize variables with current position and path information
                int row = position.row;
                int column = position.column;
                int movements = position.movements;
                String path = position.path;
                int step = position.step;

                // Move in the current direction until an obstacle is encountered or the end position is reached
                while (row >= 0 && row < rows
                        && column >= 0 && column < columns
                        && loadedMaze[row][column] == 0
                        && (row != mazeEndPos[0] || column != mazeEndPos[1])) {
                    // Update the current position
                    row += movableCoordinates[i][0];
                    column += movableCoordinates[i][1];
                    // Increment the movement count
                    movements += 1;
                }



                // If the end position is not reached and the current position is not the end position
                if (row != mazeEndPos[0] || column != mazeEndPos[1]) {
                    // Backtrack to the previous valid position
                    row -= movableCoordinates[i][0];
                    column -= movableCoordinates[i][1];
                    // Decrement the movement count as we are undoing the last movement
                    movements -= 1;
                }


                // If the new position has not been visited, mark it as visited and add it to the queue
                if (!visited[row][column]) {
                    visited[row][column] = true;
                    // Add the new position to the queue along with updated path information
                    queueList.offer(new Coordinate(row, column, movements, path + step + ". " + movableDirections[i] + " (" + (column + 1) + ", " + (row + 1) + ")\n", mazeStartPos, step + 1));
                }
            }
        }
        // If no path is found, return appropriate message
        return "No path was found!";
    }

    // Class representing coordinates in the maze
    static class Coordinate {
        int row;
        int column;
        int movements;
        String path;
        int step;
        int[] startCoordinates;

        Coordinate(int row, int column, int movements, String path, int[] startCoordinates, int step) {
            // Initialize the row of the coordinate
            this.row = row;
            // Initialize the column of the coordinate
            this.column = column;
            // Initialize the number of movements required to reach this coordinate
            this.movements = movements;
            // Initialize the path to reach this coordinate
            this.path = path;
            // Initialize the starting coordinates of the maze
            this.startCoordinates = startCoordinates;
            // Initialize the step number of this coordinate in the path
            this.step = step;
        }


        @Override
        public String toString() {
            // Return the path as a string
            return "Start at: (" + (startCoordinates[1] + 1) + ", " + (startCoordinates[0] + 1) + ")\n" + path;
        }
    }
}
