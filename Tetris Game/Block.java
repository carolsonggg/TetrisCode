import java.awt.Color;
/**
 * class BLock encapsulates a Block abstraction which can be placed into a Gridworld style grid
 * @author Carol Song
 * @version February 20, 2022
 */
public class Block
{
    private MyBoundedGrid<Block> grid;
    private Location location;
    private Color color;
    /**
     * constructs a blue block, because blue is the greatest color ever!
     */
    public Block()
    {
        color = Color.BLUE;
        grid = null;
        location = null;
    }

    /**
     * returns the color of the block
     * @return Color
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * sets the color of the block to the color in the parameter
     * @param newColor Color
     */
    public void setColor(Color newColor)
    {
        color = newColor;
    }

    /**
     * returns the grid with the block in it
     * @return MyBoundedGrid<Block>
     */
    public MyBoundedGrid<Block> getGrid()
    {
        return grid;
    }

    /**
     * returns the location of the block
     * @return Location
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * removes this block from grid
     * @precondition you are in the grid
     * @postcondition the grid does not have you in it 
     *      and the color, grid, and location are all set to null
     */
    public void removeSelfFromGrid()
    {
        grid.remove(location);
        grid = null;
        location = null;
    }

    /**
     * puts this block into the grid
     * @precondition gr and loc are not null
     * @postcondition the grid has another block in it
     * @param gr MyBoundedGrid<Block> the grid you want to put this block in
     * @param loc Location the location you want to put this block in
     */
    public void putSelfInGrid(MyBoundedGrid<Block> gr, Location loc)
    {
        Block old = gr.get(loc);
        if(old != null)
        {
            old.grid = null;
            old.location = null;
        }
        grid = gr;
        location = loc;
        gr.put(loc, this);
    }

    /**
     * moves the current block to the new location in the parameter
     * @precondition newLocation is a valid location
     * @postcondition this block is moved to the new location and it is no longer
     *      at its old location
     * @param newLocation Location the new location you want to move this block to
     */
    public void moveTo(Location newLocation)
    {
        Location old = location;
        putSelfInGrid(grid, newLocation);
        grid.remove(old);
        grid.put(newLocation, this);
    }

    /**
     * returns a string with the location and color of this block
     * @return String
     */
    public String toString()
    {
        return "Block[location=" + location + ",color=" + color + "]";
    }
}