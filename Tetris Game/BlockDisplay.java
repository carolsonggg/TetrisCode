import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * BlockDisplay is used to display the contents of the
 * game board.
 * 
 * @author Adheet Gahesh
 * @version Feb 22, 2019
 * Changed to add code so the window is the right size and can be resized on PC.
 *
 * @author Anu Datar
 * @version Feb 20, 2016
 * Changed block size and added a split panel display for next block and Score.
 *
 * @author Ryan Adolf
 * @version Feb 16, 2016
 * Fixed the lag issue with block rendering.
 * Removed the JPanel
 * 
 * @author  Dave Feinberg
 * @author  Richard Page
 * @author  Susan King     Added documentation
 * @version May 13, 2015
 */
// Used to display the contents of a game board
public class BlockDisplay extends JComponent implements KeyListener
{
    private static final Color BACKGROUND = Color.BLACK;
    private static final Color BORDER = Color.DARK_GRAY;

    private static final int OUTLINE = 2;
    private static final int BLOCKSIZE = 20;

    private MyBoundedGrid<Block> board;
    private JFrame frame;
    private ArrowListener listener;

    // Constructs a new display for displaying the given board
    /**
     * Constructor of the BlockDisplay class
     * 
     * @param board which is what the board will be set to
     */
    public BlockDisplay(MyBoundedGrid<Block> board)
    {
        this.board = board;
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    createAndShowGUI();
                }
            });

        //Wait until display has been drawn
        try
        {
            while (frame == null || !frame.isVisible())
                Thread.sleep(1);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI()
    {
        //Create and set up the window.
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.addKeyListener(this);

        //Display the window.
        this.setPreferredSize(new Dimension(
                BLOCKSIZE * board.getNumCols(),
                BLOCKSIZE * board.getNumRows()));

        frame.pack();
        frame.setVisible(true);
    }
    /**
     * Paints the component
     * 
     * @param g the graphics
     */
    public void paintComponent(Graphics g)
    {
        g.setColor(BACKGROUND);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(BORDER);
        g.fillRect(0, 0, BLOCKSIZE * board.getNumCols() + OUTLINE, BLOCKSIZE * board.getNumRows());
        for (int row = 0; row < board.getNumRows(); row++)
            for (int col = 0; col < board.getNumCols(); col++)
            {
                Location loc = new Location(row, col);

                Block square = board.get(loc);

                if (square == null)
                    g.setColor(BACKGROUND);
                else
                    g.setColor(square.getColor());

                g.fillRect(col * BLOCKSIZE + OUTLINE/2, row * BLOCKSIZE + OUTLINE/2,
                    BLOCKSIZE - OUTLINE, BLOCKSIZE - OUTLINE);
            }

    }

    //Redraws the board to include the pieces and border colors.
    /**
     * Redraws the board to include the pieces and border colors
     */
    public void showBlocks()
    {
        repaint();
    }

    // Sets the title of the window.
    /**
     * Sets the title of the window.
     * @param title the title that will be set
     */
    public void setTitle(String title)
    {
        frame.setTitle(title);
    }
    /**
     * The key typed
     * @param e key that is typed
     */
    public void keyTyped(KeyEvent e)
    {
    }
    /**
     * The key released
     * @param e key that is released
     */
    public void keyReleased(KeyEvent e)
    {
    }
    /**
     * The key pressed
     * @param e key that is pressed
     */
    public void keyPressed(KeyEvent e)
    {
        if (listener == null)
            return;
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT)
            listener.leftPressed();
        else if (code == KeyEvent.VK_RIGHT)
            listener.rightPressed();
        else if (code == KeyEvent.VK_DOWN)
            listener.downPressed();
        else if (code == KeyEvent.VK_UP)
            listener.upPressed();
        else if (code == KeyEvent.VK_SPACE)
            listener.spacePressed();
    }
    /**
     * Runs arrow listener 
     * @param listen what this listener is set to
     */
    public void setArrowListener(ArrowListener listen)
    {
        this.listener = listen;
    }
}