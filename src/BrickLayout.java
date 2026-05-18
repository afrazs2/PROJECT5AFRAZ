import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BrickLayout {

    private ArrayList<Brick> bricks;
    private int[][] grid;
    private int brickIndex = 0; // current brick, when we do brick ++ it moves to the next brick

    private ArrayList<Brick> fallBrick; // stores all the bricks that are falling at the same time
    private ArrayList<Integer> fallRows; // stores the row that each falling brick is on, so both of these together are used to find where the brick is and what row its on

    public BrickLayout(String inputFile) {
        ArrayList<String> fileData = getFileData(inputFile);
        bricks = new ArrayList<Brick>();
        fallBrick = new ArrayList<Brick>();
        fallRows = new ArrayList<Integer>(); //initializes these as arraylists, it is arraylist<brick> because it stores the objects from the input file, and then fallingrows just stores an integer

        for (String line : fileData) {
            String[] points = line.split(",");
            int start = Integer.parseInt(points[0]);
            int end = Integer.parseInt(points[1]);
            Brick b = new Brick(start, end);
            bricks.add(b);
        }

        grid = new int[30][40];
    }

    public int[][] getGrid() {
        return grid;
    }

    public void dropOneBrick() { // drops the brick
        if (brickIndex >= bricks.size()) {
            return;
        }

        Brick b = bricks.get(brickIndex);

        for (int col = b.getStart(); col <= b.getEnd(); col++) {
            grid[0][col] = 1;
        }

        fallBrick.add(b);
        fallRows.add(0);

        brickIndex++;
    }
    public void falldown() { // for the animation, this helper moves all the bricks that are currently on the window to move down 1 row
        for (int i = fallBrick.size() - 1; i >= 0; i--) { // prevents skipping items on the arraylist
            Brick b = fallBrick.get(i);
            int row = fallRows.get(i);

            if (moveCheck(row, b))
            { // checks if it can move down, and if it can it gets rid of the brick on its current row and moves it down 1
                for (int col = b.getStart(); col <= b.getEnd(); col++) {
                    grid[row][col] = 0;
                }

                row++;

                for (int col = b.getStart(); col <= b.getEnd(); col++) {
                    grid[row][col] = 1;
                }

                fallRows.set(i, row);//updates arraylist with new information
            }
            else {
                fallBrick.remove(i); // if it cant move down anymore then it makes it so it doesnt get erased or move down anymore.
                fallRows.remove(i);
            }
        }
    }

    private boolean moveCheck(int row, Brick b) { // this entire helper is used to check if the brick can move down another row by check if the brick is already at the bottom or if there is a 1 value underneath to indicate if another brick is there
        if (row + 1 >= grid.length) {
            return false;
        }

        for (int col = b.getStart(); col <= b.getEnd(); col++) {
            if (grid[row + 1][col] == 1) {
                return false;
            }
        }

        return true;
    }

    public ArrayList<String> getFileData(String fileName) {
        File f = new File(fileName);
        Scanner s = null;

        try {
            s = new Scanner(f);
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(1);
        }

        ArrayList<String> fileData = new ArrayList<String>();

        while (s.hasNextLine()) {
            fileData.add(s.nextLine());
        }

        return fileData;
    }
}