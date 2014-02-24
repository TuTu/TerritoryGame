import java.awt.Graphics2D;
import java.awt.Dimension;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.Point;
import java.awt.event.*;
import javax.swing.event.*;

/**
 * Write a description of class Board here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Board implements MouseInputListener
{
    /**
     * Constructor for objects of class Board
     */
    public Board(JPanel p, Dimension dim, AIPlayer mother, HumanPlayer human)
    {
        panel = p;
        dimension = dim;
        motherPlayer = mother;
        humanPlayer = human;        
        makeCoordTransform();
        makeBlocks();
        mouseButtonStatus = new boolean[]{false, false, false, false};
        mousePos = new Point2D.Double();
        mouseBlockPos = new Point();
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
        selectionRect = new Rectangle2D.Double();
        selectionRectCorner1 = new Point2D.Double();
        selectionRectCorner2 = new Point2D.Double();
        selectionRectCornerBlock1 = new Point();
        selectionRectCornerBlock2 = new Point();
    }

    /**
     * Make the board
     */
    private void makeBlocks()
    {
        final int w = dimension.width;
        final int h = dimension.height;
        blocks = new Block[w][h];
        for (int y = 0; y < h; y++)
        {
            for (int x = 0; x < w; x++)
            {
                blocks[y][x] = new Block(this, x, y);                                    
//                 if (y == 0)
//                 {
//                     if (x == 0)
//                         blocks[y][x] = new BaseBlock(this, x, y, players[Player.TOP_LEFT_SIDE]);                    
//                     else if (x == w-1)
//                         blocks[y][x] = new BaseBlock(this, x, y, players[Player.TOP_RIGHT_SIDE]);                                                                    
//                     else
//                         blocks[y][x] = new GroundBlock(this, x, y, players[Player.MOTHER]);                        
//                 }
//                 else if (y == h-1)
//                 {
//                     if (x == 0)
//                         blocks[y][x] = new BaseBlock(this, x, y, players[Player.BOTTOM_LEFT_SIDE]);
//                     else if (x == w-1)
//                         blocks[y][x] = new BaseBlock(this, x, y, players[Player.BOTTOM_RIGHT_SIDE]);                                    
//                     else
//                         blocks[y][x] = new GroundBlock(this, x, y, players[Player.MOTHER]);                        
//                 }
//                 else if( ((y == 3) || (y == 6)) && ((x == 3) || (x == 6)))
//                     blocks[y][x] = new ResourceBlock(this, x, y, players[Player.MOTHER]);
//                 else
//                     blocks[y][x] = new GroundBlock(this, x, y, players[Player.MOTHER]);
            }
        }
    } 
    
    /**
     * Draw the whole board. Note: units are not included.
     * 
     * @param  g2   the context of the graphics
     */
    public void draw(Graphics2D g2)
    {
//         drawBackground(g2);
        for (int layer = 0; layer < NUM_LAYER; layer++)
        {
            for (int i = 0; i < dimension.height; i++)
            {
                for (int j = 0; j < dimension.width; j++)
                {
                    blocks[i][j].draw(g2, layer);
                }
            }
        }
        drawSelectionRectangle(g2);
     }

//     /**
//      * draw the background
//      * 
//      * @param  g2   the context of the graphics
//      */
//     private void drawBackground(Graphics2D g2)
//     {
//         for (int i = 0; i < dimension.height; i++)
//         {
//             for (int j = 0; j < dimension.width; j++)
//             {
// //                 if (!blocks[i][j].isAbleToStepOn())
// //                 {
// //                     blocks[i][j].drawBackground(g2, Color.GRAY);
// //                 }
//                 if (blocks[i][j].isUnitOn())
//                 {
//                     blocks[i][j].drawBackground(g2, Color.BLACK);
//                 }
//                 blocks[i][j].drawGrid(g2);
//             }
//         }
//     }      

    /**
     * draw the selectionRectangle
     * 
     * @param  g2   the context of the graphics
     */
    private void drawSelectionRectangle(Graphics2D g2)
    {
// System.out.println("selectionRectCorner1:" + selectionRectCorner1.toString());
// System.out.println("selectionRectCorner2:" + selectionRectCorner2.toString());
// System.out.println("selectionRect:" + selectionRect.toString());        
        g2.setStroke(Unit.SELECTION_STROKE);
        g2.setColor(Unit.SELECTION_GLOW_COLOR);
        g2.draw(selectionRect);        
    }    
    
    public void mouseClicked(MouseEvent e){};
    public void mouseEntered(MouseEvent e){};
    public void mouseExited(MouseEvent e){};
    
    public void mousePressed(MouseEvent e)
    {
// System.out.println("mousePressed event occured!");        
        mouseButtonStatus[e.getButton()] = true;
        mousePos.setLocation(e.getX(), e.getY());
        getDeviceToUserCoordTransform().transform(mousePos, mousePos);
        mouseBlockPos.setLocation((int)mousePos.x, (int)mousePos.y);
        
// System.out.println(mouseBlockPos);
        if (e.getButton() == MouseEvent.BUTTON1)
        {
            selectionRectCorner1.setLocation(mousePos.x, mousePos.y);
            selectionRectCornerBlock1.setLocation(mouseBlockPos.x, mouseBlockPos.y);
            humanPlayer.deSelectAllUnit();
        }
        
        if (e.getButton() == MouseEvent.BUTTON3)
        {
            if (getBlock(mouseBlockPos.x, mouseBlockPos.y).isUnitOn()
                && !getBlock(mouseBlockPos.x, mouseBlockPos.y).getUnit().getOwner().isTeammate(humanPlayer))
            {
                humanPlayer.orderAttack(getBlock(mouseBlockPos.x, mouseBlockPos.y).getUnit());
//                 boolean isGoingToAttack = false;
//                 for (int i = 0; i < humanPlayer.getSelectedUnitListSize(); i++)
//                 {
// //                     if (humanPlayer.getSelectedUnit(i).isAttackable())
// //                     {
//                         isGoingToAttack = true;
//                         break;
// //                     }
//                 }
                
//                 if (isGoingToAttack)
//                 {
//                     humanPlayer.selectTarget(getBlock(mouseBlockPos.x, mouseBlockPos.y).getUnit());
//                 }
            }
            else
            {
                humanPlayer.orderMove((Point)mouseBlockPos.clone());
            }
        }
    }
    
    public void mouseReleased(MouseEvent e)
    {
// System.out.println("mouseReleased event occured!");           
        mouseButtonStatus[e.getButton()] = false;
        mousePos.setLocation(e.getX(), e.getY());
        getDeviceToUserCoordTransform().transform(mousePos, mousePos);
        mouseBlockPos.setLocation((int)mousePos.x, (int)mousePos.y);
        if (e.getButton() == MouseEvent.BUTTON1)
        {
            selectionRectCorner2.setLocation(mousePos.x, mousePos.y);
            selectionRectCornerBlock2.setLocation(mouseBlockPos.x, mouseBlockPos.y);
            
//             if (getBlock(mouseBlockPos).isUnitOn())
//             {
//                 Unit mousePressedUnit = getBlock(mouseBlockPos).getUnit();
//                 if (mousePressedUnit.getOwner().equals(humanPlayer))
//                 {
//                     humanPlayer.selectUnit(mousePressedUnit);
// // System.out.println(mousePressedUnit.getBlockShape().getPivotBlock().getPosition().toString());
// // System.out.println(mousePressedUnit.getDrawPosition().toString());
// // System.out.println(mousePressedUnit.getPivotPosition().toString());
//                 }
//             }

            // if mouse has not dragged, select any unit on that block
            // else if mouse has dragged, select only human units
            if (selectionRectCornerBlock1.x == selectionRectCornerBlock2.x
                && selectionRectCornerBlock1.y == selectionRectCornerBlock2.y
                && getBlock(mouseBlockPos.x, mouseBlockPos.y).isUnitOn())
            {
                humanPlayer.selectUnit(getBlock(mouseBlockPos.x, mouseBlockPos.y).getUnit());
            }
            else
            {
                int xMin, xMax;
                int yMin, yMax;
                if (selectionRectCornerBlock1.x >= selectionRectCornerBlock2.x)
                {
                    xMin = selectionRectCornerBlock2.x;
                    xMax = selectionRectCornerBlock1.x;
                }
                else
                {
                    xMin = selectionRectCornerBlock1.x;
                    xMax = selectionRectCornerBlock2.x;
                }
                
                if (selectionRectCornerBlock1.y >= selectionRectCornerBlock2.y)
                {
                    yMin = selectionRectCornerBlock2.y;
                    yMax = selectionRectCornerBlock1.y;
                }
                else
                {
                    yMin = selectionRectCornerBlock1.y;
                    yMax = selectionRectCornerBlock2.y;
                }
                
                Point pos = new Point();
                for (int x = xMin; x <= xMax; x++)
                {
                    for (int y = yMin; y <= yMax; y++)
                    {
                        pos.setLocation(x, y);
                        if (isInside(pos) && getBlock(pos).isUnitOn() 
                            && getBlock(x, y).getUnit().getOwner().equals(humanPlayer))
                        {
                            humanPlayer.selectUnit(getBlock(x, y).getUnit());
                        }
                    }
                }                
            }
            selectionRect.setFrame(0,0,0,0);
    //             selectionRectCorner1.setLocation(-1,-1);
    //             selectionRectCorner2.setLocation(-1,-1);
    //             selectionRectCornerBlock1.setLocation(-1,-1);
    //             selectionRectCornerBlock2.setLocation(-1,-1);
        }
    }
    public void mouseDragged(MouseEvent e)
    {
// System.out.println("mouseDragged event occured!");
// System.exit(1);       
        mousePos.setLocation(e.getX(), e.getY());
        getDeviceToUserCoordTransform().transform(mousePos, mousePos);
        mouseBlockPos.setLocation((int)mousePos.x, (int)mousePos.y);
        if (mouseButtonStatus[MouseEvent.BUTTON1])
        {
            selectionRectCorner2.setLocation(mousePos.x, mousePos.y);
            selectionRectCornerBlock2.setLocation(mouseBlockPos.x, mouseBlockPos.y);
            selectionRect.setFrameFromDiagonal(selectionRectCorner1, selectionRectCorner2);
        }
    }
    
    public void mouseMoved(MouseEvent e)
    {
// System.out.println("mouseMoved event occured!");        
//         mousePos.setLocation(e.getX(), e.getY());
//         getDeviceToUserCoordTransform().transform(mousePos, mousePos);
//         if (mouseButtonStatus[MouseEvent.BUTTON1])
//         {
//             selectionRectCorner2.setLocation(mousePos.x, mousePos.y);
//             selectionRect.setFrameFromDiagonal(selectionRectCorner1, selectionRectCorner2);
//         }
    }
    
    /**
     * Get the dimension of the board
     * 
     * @return     dimension 
     */
    public final Dimension getDimension()
    {
        return dimension;
    }

    /**
     * Get the specified block of this board
     * 
     * @return     block 
     */
    public final Block getBlock(int x, int y)
    {
        if (x < 0 || x > dimension.width || y < 0 || y > dimension.height)
            throw new ArrayIndexOutOfBoundsException("The block is out of board");
        return blocks[y][x];
    }
    
    /**
     * Get the specified block of this board
     * 
     * @return     block 
     */
    public final Block getBlock(Point pos)
    {
        if (!isInside(pos))
            throw new ArrayIndexOutOfBoundsException("The position is out of board");
        return blocks[pos.y][pos.x];
    }
    
    /**
     * Check whether a point is inside this board
     * 
     * @param   pos
     * @return     true if inside, false if outside 
     */
    public final boolean isInside(final Point pos)
    {
        return isInside(pos.x, pos.y);
    }
    
    /**
     * Check whether a point is inside this board
     * 
     * @param   x
     * @param   y
     * @return     true if inside, false if outside 
     */
    public final boolean isInside(final int x, final int y)
    {
        if (x < 0 || x > dimension.width-1 || y < 0 || y > dimension.height-1)
            return false;
        else
            return true;
    }    
    
    /**
     * Get the panel of this board
     * 
     * @return     panel 
     */
    public final JPanel getPanel()
    {
        return panel;
    }

    /**
     * Make the coordinate transformations of this board
     */
    public void makeCoordTransform()
    {
        makeUserToDeviceCoordTransform();
        makeDeviceToUserCoordTransform();
    }    
    
    /**
     * Make the deviceToUserCoordTransform of this board
     */
    private void makeDeviceToUserCoordTransform()
    {
        try
        {
            deviceToUserCoordTransform = userToDeviceCoordTransform.createInverse();
        }
        catch (java.awt.geom.NoninvertibleTransformException e)
        {
            System.out.println("java.awt.geom.NoninvertibleTransformException");
            System.exit(1);
        }
    }    

    /**
     * Make the userToDeviceCoordTransform of this board
     */
    private void makeUserToDeviceCoordTransform()
    {
        AffineTransform coordT = new AffineTransform();     
        double xscale = (panel.getSize().width) / (double)dimension.width;
        double yscale = (panel.getSize().height) / (double)dimension.height;
        coordT.scale(xscale, yscale);
        userToDeviceCoordTransform = coordT;
    }    
    
    /**
     * Get the userToDeviceCoordTransform of this board
     * 
     * @return userToDeviceCoordTransform
     */
    public final AffineTransform getUserToDeviceCoordTransform()
    {
        return userToDeviceCoordTransform;
    }    
    
    /**
     * Get the deviceToUserCoordTransform of this board
     * 
     * @return deviceToUserCoordTransform
     */
    public final AffineTransform getDeviceToUserCoordTransform()
    {
        return deviceToUserCoordTransform;
    }
    
    /**
     * Get the distance between two specified blocks.
     * Discard the decimal numbers
     * 
     * @param   b1  block1
     * @param   b2  block2
     * @return  the distance between two specified blocks
     */
    public static final int getDistance(Block b1, Block b2)
    {
        int dX = Math.abs(b1.getPosition().x - b2.getPosition().x);
        int dY = Math.abs(b1.getPosition().y - b2.getPosition().y);
        
        return Math.min(dX,dY) * DIAGONAL_DISTANCE + Math.abs(dX - dY) * ORTHOGONAL_DISTANCE;
    }
    
//     /**
//      * Set the MouseButtonStatus
//      */
//     public void setMouseButtonStatus(final int MB, boolean pressed)
//     {
//         mouseButtonStatus[MB] = pressed;
//     }
// 
//     /**
//      * Get the MouseButtonStatus
//      */
//     public boolean getMouseButtonStatus(final int MB)
//     {
//         return mouseButtonStatus[MB];
//     }    
    
     /**
     * Get the MotherPlayer
     */
    public AIPlayer getMotherPlayer()
    {
        return motherPlayer;
    }
    
     /**
     * Get the HumanPlayer
     */
    public HumanPlayer getHumanPlayer()
    {
        return humanPlayer;
    }    

    private Block[][] blocks;
    private Dimension dimension;
    private HumanPlayer humanPlayer;
    private AIPlayer motherPlayer;
    private JPanel panel;
    
    private boolean[] mouseButtonStatus; // {Empty, Button1, Button2, Button3}  
    private Rectangle2D.Double selectionRect;
    private Point2D.Double selectionRectCorner1;
    private Point2D.Double selectionRectCorner2;
    private Point selectionRectCornerBlock1;
    private Point selectionRectCornerBlock2;
    private Point2D.Double mousePos;
    private Point mouseBlockPos;
    
    private AffineTransform deviceToUserCoordTransform;
    private AffineTransform userToDeviceCoordTransform;
    
    public static final NullBlock NULL_BLOCK = new NullBlock();
    public static final int NUM_LAYER = 3; // number of layers
    public static final int ORTHOGONAL_DISTANCE = 10;
    public static final int DIAGONAL_DISTANCE = 14;
}
