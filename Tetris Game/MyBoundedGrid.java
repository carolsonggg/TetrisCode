import java.util.ArrayList;

/**
 * MyBoundedGrid is the grid on which the game is played and on which the blocks exist.
 * It is a rectangular grid with a finite number of rows and columns.
 * 
 * @author  Dave Feinberg
 * @author  Richard Page
 * @author  Susan King
 * @author  Carol Song
 * @version May 13, 2015 
 * 
 * @param <E> the elements that may be put in the grid are any objects
 */
public class MyBoundedGrid<E>
{
    // instance variables - replace the example below with your own
    private int r;
    private int c;
    Object[][] grid;
    /**
     * Constructor for objects of class MyBoundedGrid
     * @param rows int
     * @param cols int
     */
    public MyBoundedGrid(int rows, int cols)
    {
        // initialise instance variables
        r = rows;
        c = cols;
        grid = new Object[r][c];
    }

    /**
     * Returns the number of rows in the grid
     * @return int
     */
    public int getNumRows()
    {
        // put your code here
        return grid.length;
    }
    /**
     * Returns the number of columns in the grid
     * @return int
     */
    public int getNumCols()
    {
        return c;
    }
    /**
     * Boolean method that tests whether the location is valid
     * 
     * @return true if location is valid; otherwise, false
     * @param loc the location that is being checked
     */
    public boolean isValid(Location loc)
    {
        if (loc.getRow() < r && loc.getCol() < c && loc.getRow() >= 0 && loc.getCol() >= 0)
        {
            return true;
        }
        return false;
    }
    /**
     * Method to place E obj
     * 
     * @return E which is the object getting placed
     * @param loc the location that is being filled
     * @param obj the object getting placed
     */
    public E put(Location loc, E obj)
    {
        E temp = this.get(loc);
        grid[loc.getRow()][loc.getCol()] = obj;
        return temp;
    }
    /**
     * Method to remove E obj
     * 
     * @return E which is the object getting removed
     * @param loc the location that is being removed
     */
    public E remove(Location loc)
    {
        E temp = (E) grid[loc.getRow()][loc.getCol()];
        grid[loc.getRow()][loc.getCol()] = null;
        return temp;
    }
    /**
     * Method to get E obj
     * 
     * @return E which is the object
     * @param loc the location of the obj that is being returned
     */
    public E get(Location loc)
    {
        return (E) grid[loc.getRow()][loc.getCol()];
    }
    /**
     * Return an arraylist of locations that are occupied
     * 
     * @return ArrayList<Location> of occupied locations
     */
    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList<Location> array = new ArrayList<Location>();
        for (int i = 0; i < grid.length; i++)
        {
            for (int j = 0; j < grid[i].length; j++)
            {
                if (grid[i][j]!=null)
                {
                    array.add(new Location(i, j));
                }
            }
        }
        return array;
    }
}