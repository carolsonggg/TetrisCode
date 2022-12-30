import  java.awt.Color;
import java.util.*;
import java.util.concurrent.Semaphore;
/**
 * This is the Tetrad classes that handles with Tetris' bricks
 * 
 * @author Carol Song
 * @version February 22, 2022
 */
public class Tetrad
{
    private MyBoundedGrid<Block> gr;
    private Block[] blocks;
    private Color color;
    private Semaphore lock;

    /**
     * Constructor for objects of class Tetrad.
     * Randomly creates a block to put into the grid.
     * Initializes the locations of the blocks, relative to which block was randomly chosen.
     * @param grid MyBoundedGrid<Block>
     */
    public Tetrad(MyBoundedGrid<Block> grid)
    {
        blocks = new Block[4];
        gr = grid;
        Location[] locs = new Location[4];
        int blockNumber = (int) (Math.random()*7);
        lock = new Semaphore(1, true);
        if (blockNumber == 0)
        {
            for (int i = 0; i < 4; i++)
            {
                blocks[i] = new Block();
                blocks[i].setColor(Color.RED);
            }
            locs[0] = new Location(1,4);
            locs[1] = new Location(0,4);
            locs[2] = new Location(2,4);
            locs[3] = new Location(3,4);
        }
        else if (blockNumber == 1)
        {
            for (int i = 0; i < 4; i++)
            {
                blocks[i] = new Block();
                blocks[i].setColor(Color.GRAY);
            }
            locs[0] = new Location(0,5);
            locs[1] = new Location(0,4);
            locs[2] = new Location(0,6);
            locs[3] = new Location(1,5);
        }
        else if (blockNumber == 2)
        {
            for (int i = 0; i < 4; i++)
            {
                blocks[i] = new Block();
                blocks[i].setColor(Color.CYAN);
            }
            locs[0] = new Location(0,5);
            locs[1] = new Location(0,4);
            locs[2] = new Location(1,4);
            locs[3] = new Location(1,5);
        }
        else if (blockNumber == 3)
        {
            for (int i = 0; i < 4; i++)
            {
                blocks[i] = new Block();
                blocks[i].setColor(Color.YELLOW);
            }
            locs[0] = new Location(1,4);
            locs[1] = new Location(0,4);
            locs[2] = new Location(2,4);
            locs[3] = new Location(2,5);
        }
        else if (blockNumber == 4)
        {
            for (int i = 0; i < 4; i++)
            {
                blocks[i] = new Block();
                blocks[i].setColor(Color.MAGENTA);
            }
            locs[0] = new Location(1,5);
            locs[1] = new Location(0,5);
            locs[2] = new Location(2,5);
            locs[3] = new Location(2,4);
        }
        else if (blockNumber == 5)
        {
            for (int i = 0; i < 4; i++)
            {
                blocks[i] = new Block();
                blocks[i].setColor(Color.BLUE);
            }
            locs[0] = new Location(0,5);
            locs[1] = new Location(0,6);
            locs[2] = new Location(1,5);
            locs[3] = new Location(1,4);
        }
        else
        {
            for (int i = 0; i < 4; i++)
            {
                blocks[i] = new Block();
                blocks[i].setColor(Color.GREEN);
            }
            locs[0] = new Location(0,5);
            locs[1] = new Location(0,4);
            locs[2] = new Location(1,5);
            locs[3] = new Location(1,6);
        }
        addToLocation(gr, locs);
    }

    /**
     * Puts all the blocks into the grid as a full brick
     * @precondition blocks are not in any grid
     *               locs.length = 4
     * @postcondition The locations of blocks match locs,
     *                and blocks have been put in the grid
     * @param grid MyBoundedGrid<Block> there are no blocks in this grid
     * @param locs Location[] location where you want the blocks
     */
    private void addToLocation(MyBoundedGrid<Block> grid, Location[] locs)
    {
        for (int i = 0; i < locs.length; i++)
        {
            blocks[i].putSelfInGrid(grid, locs[i]);
        }
    }

    /**
     * Removes all four blocks
     * @precondition Blocks are in the grid.
     * @postcondition Returns old locations of blocks.
     *                blocks have been removed from the grid
     * @return Location[] locations of the old blocks
     */
    private Location[] removeBlocks()
    {
        Location[] locs = new Location[blocks.length];
        for (int i = 0; i < blocks.length; i++)
        {
            locs[i] = blocks[i].getLocation();
            blocks[i].removeSelfFromGrid();
        }
        return locs;
    }

    /**
     * Tests if the array of locations are valid and empty.
     * @precondition none
     * @postcondition returns true if each of locs is
     *                valid and empty in grid.
     *                false otherwise
     * @param grid MyBoundedGrid<Block>
     * @param locs Location[] locations that are being tested
     * @return boolean true if the locations are empty and valid.
     *                 false if they aren't
     */
    private boolean areEmpty(MyBoundedGrid<Block> grid, Location[] locs)
    {
        ArrayList<Location> locations = gr.getOccupiedLocations();
        boolean isEmpty = true;
        for (int i = 0; i < locs.length; i++)
        {
            for (int j = 0; j < locations.size(); j++)
            {
                if (locations.get(j).equals(locs[i])) 
                {
                    isEmpty = false;
                }
            }
            if (!gr.isValid(locs[i]) || !isEmpty) 
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Shifts the active tetrad the inputted number of rows and columns.
     * @precondition none
     * @postcondition attemps to move this tetrad deltaRow
     *                rows down and deltaCol columns to the
     *                right, if those positions are valid
     *                and empty; returns true if successful
     *                and false otherwise.
     * @param deltaRow int
     * @param deltaCol int
     * @return boolean true if the tetrad can be translated to the new location
     *                 false if it can't
     */
    public boolean translate(int deltaRow, int deltaCol)
    {
        try
        {
            lock.acquire();
            Location[] oldLocs = removeBlocks();
            Location[] newLocs = new Location[blocks.length];
            for (int i = 0; i < newLocs.length; i++)
            {
                newLocs[i] = new Location(oldLocs[i].getRow() + deltaRow, 
                    oldLocs[i].getCol() + deltaCol);
            }
            if (areEmpty(gr, newLocs))
            {
                addToLocation(gr, newLocs);
                return true;
            }
            else
            {
                addToLocation(gr, oldLocs);
                return false;
            }
        }
        catch(InterruptedException e)
        {
            return false;
        }
        finally
        {
            lock.release();
        }
    }

    /**
     * Attempts to rotate this tetrad clockwise by 90 degrees about its
     * center, if the necessary positions are empty; returns true if 
     * successful and false otherwise
     * 
     * @return boolean true if the tetrad can be rotated.
     *                 false if it can't
     */
    public boolean rotate()
    {
        if(blocks[0].getColor().equals(Color.CYAN))
            return false;
        try
        {
            lock.acquire();
            Location[] oldLocs = removeBlocks();
            Location[] newLocs = new Location[blocks.length];
            int row = oldLocs[0].getRow();
            int col = oldLocs[0].getCol();
            for (int i = 0; i < newLocs.length; i++)
            {
                newLocs[i] = new Location(row - col + oldLocs[i].getCol(), 
                    row + col - oldLocs[i].getRow());
            }
            if (areEmpty(gr, newLocs))
            {
                addToLocation(gr, newLocs);
                return true;
            }
            else
            {
                addToLocation(gr, oldLocs);
                return false;
            }
        }
        catch(InterruptedException e)
        {
            return false;
        }
        finally
        {
            lock.release();
        }
    }
}