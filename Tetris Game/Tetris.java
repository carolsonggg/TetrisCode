
/**
 * This is the Tetris class.
 * This class displays and plays the tetris game.
 * It implements ArrowListener 
 * @author Carol Song
 * @version February 22, 2022
 */
public class Tetris implements ArrowListener
{
    // instance variables - replace the example below with your own
    private MyBoundedGrid<Block> grid;
    private BlockDisplay display;
    private Tetrad tetrad;
    private int rowsCleared;
    private int points;
    private int level;
    private int rowsOnce;
    private boolean changed;
    private static boolean gameOver;

    /**
     * Constructor for objects of class Tetris
     * Creates a 20x10 grid.
     * Creates the playing field and begins the game.
     */
    public Tetris()
    {
        grid = new MyBoundedGrid(20,10);
        display = new BlockDisplay(grid);
        display.setTitle("Tetris");
        display.setArrowListener(this);
        tetrad = new Tetrad(grid);
        rowsCleared = 0;
        rowsOnce = 0;
        changed = false;
        gameOver = false;
        points = 0;
        level = 1;
        display.showBlocks();
    }

    /**
     * When the up arrow is pressed, rotates the tetrad if it can.
     */
    @Override
    public void upPressed()
    {
        tetrad.rotate();
        display.showBlocks();
    }

    /**
     * When the down arrow is pressed, shifts the tetrad one down.
     */
    @Override
    public void downPressed()
    {
        tetrad.translate(1, 0);
        display.showBlocks();
    }

    /**
     * When the left arrow is pressed, shifts the tetrad one to the left.
     */
    @Override
    public void leftPressed()
    {
        tetrad.translate(0, -1);
        display.showBlocks();
    }

    /**
     * When the right arrow is pressed, shifts the tetrad one to the right.
     */
    @Override
    public void rightPressed()
    {
        tetrad.translate(0, 1);
        display.showBlocks();
    }

    /**
     * When space arrow is pressed, brings tetrad all the way to the bottom.
     */
    @Override
    public void spacePressed()
    {
        int count = 0;
        while (tetrad.translate(1,0))
        {
            count++;
        }
        tetrad.translate(count++, 0);
        display.showBlocks();
    }

    /**
     * Makes the active tetrad drop one space each second. 
     * After the tetrad reaches the bottoms, creates a new tetrad at the top 
     * and sets that new tetrad to the active tetrad.
     */
    public void play()
    {
        try
        {
            Thread.sleep(1000/level);
        }
        catch(InterruptedException e)
        {
            // do nothing
        }
        display.showBlocks();
        while (tetrad.translate(1,0))
        {
            try
            {
                Thread.sleep(1000/level);
            }
            catch(InterruptedException e)
            {
                // do nothing
            }
            display.showBlocks();
        }
        clearCompletedRows();
        if (changed)
        {
            if (rowsCleared > 0)
            {
                System.out.println("you have cleared " + rowsCleared + 
                    " rows in level " + level + "!");
                System.out.println("you have " + points + " points");
            }
            changed = false;
        }

        if (grid.get(new Location(0, 5)) != null||grid.get(new Location(0, 4)) != null)
        {
            System.out.println("game over");
            gameOver = true;
        }
        else
        {
            Tetrad newTetrad = new Tetrad(grid);
            tetrad = newTetrad;
        }
    }

    /**
     * Checks if each column of the inputted row
     * is occupied by a block.
     * @precondition 0 <= row < number of rows
     * @postcondition returns true if every cell in the 
     *                given row is occupied;
     *                returns false otherwise.
     * @param row int the row you want to test is cleared
     * @return boolean true, if the row is completed;
     *                 false if there is an empty space in the row
     */
    private boolean isCompletedRow(int row)
    {
        for (int i = 0; i < 10; i++)
        {
            if (grid.get(new Location(row, i)) == null)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Clears all the blocks in the row and shifts the blocks above that row down.
     * @precondition 0 <= row < number of rows
     *               given row is full of blocks
     * @postcondition Every block in the given row has been
     *                removed, and every block above row
     *                has been moved down one row.
     * @param row int the row you want to clear
     */
    private void clearRow(int row)
    {
        for (int i = 0; i < grid.getNumCols(); i++)
        {
            grid.remove(new Location(row, i));
        }
        for (int i = row - 1; i >= 0; i--)
        {
            for (int j = 0; j < grid.getNumCols(); j++)
            {
                if (grid.get(new Location(i, j)) == null)
                {
                }
                else
                {
                    grid.get(new Location(i, j)).moveTo(new Location(i+1, j));
                }
            }
        }
    }

    /**
     * Clears all the completed rows by iterating through them
     * @postcondition all the completed rows have been cleared
     */
    private void clearCompletedRows()
    {
        for (int i = 0; i < grid.getNumRows(); i++)
        {
            if (isCompletedRow(i))
            {
                clearRow(i);
                rowsCleared++;
                rowsOnce++;
                points += 40;
                changed = true;
            }
        }
        if (rowsCleared >= 10)
        {
            level++;
            rowsCleared = 0;
            System.out.println("you have advanced to level " + level + "!");
        }
        if (rowsOnce > 1)
        {
            points += points*rowsOnce;
        }
        rowsOnce = 0;
    }

    /**
     * Main method for Tetris Class
     * 
     * @param args the argument passed in
     */
    public static void main(String[] args)
    {
        Tetris game = new Tetris();
        while (!(gameOver))
        {
            game.play();
        }
    }
}
