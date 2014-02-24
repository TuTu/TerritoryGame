import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.geom.*;
import java.awt.Point;

/**
 * Write a description of class Block here.
 * 
 * @author TuTu
 * @version 2007.11.9
 */
public class Block
{
    /**
     * Constructor for objects of class Block
     */
    public Block(Board b, int x, int y)
    {
        board = b;
//         blockType = type;
        position = new Point(x, y);
        unit = null;
        stepOnAbility = true;
        blockRect = new Rectangle2D.Double(x, y, 1, 1);
//         firstDraw = true;
//         owner = own;
//         penetratability = penetrate;
    }
    
    protected Block(Object NULL)
    {
        position = new Point(-1, -1);
        unit = null;
        setStepOnAbility(false);
        if (NULL != null)
            throw new IllegalArgumentException("Block(Object NULL) can only be called by NullBlock!");
    }

    /**
     * Return true if the position of the input block equals to this block
     *
     * @return  true or false if the position of the input block equals to this block
     */    
    public boolean equals(Block block)
    {
        return getPosition().equals(block.getPosition());
    }    
    
    /**
     * Draw the block - units on it.
     * 
     * @param  g2   the context of the graphics
     * @param  layer    the layer to be drawn
     */
    public void draw(Graphics2D g2, final int layer)
    {
        if (layer == 0)
        {
            if (isUnitOn())
            {
//                drawBackground(g2, Color.BLACK);
                unit.draw(g2, layer);
            }
//            drawGrid(g2);
        }
        else
        {
            if (isUnitOn())
            {                          
                if (unit.getBlockShape().getBottomRightBlock().equals(this))
                {                
                    unit.draw(g2, layer);
                }
            }
        }
//         Color ownerColor = getOwner().getColor();
//         Rectangle2D.Double rect = new Rectangle2D.Double(position.x, position.y, 1., 1.);
//         g2.setColor(ownerColor);
//         g2.draw(rect);        
//         g2.fill(rect);        
    }
    
    /**
     * Get the position of the block
     * 
     * @return     position 
     */
    public final Point getPosition()
    {
        return position;
    }

    /**
     * draw the backGround
     */
    public void drawBackground(Graphics2D g2, Color color)
    {
        g2.setColor(color);
        g2.fill(blockRect);
    }    
    
    /**
     * draw the grid
     */
    public void drawGrid(Graphics2D g2)
{
        g2.setColor(Color.BLACK);
        g2.draw(blockRect);
    }
    
    /**
     * Add a unit to this block
     * 
     * @param   unit to be added on this block
     */
    public void addUnit(Unit u)
    {
        if (unit != null)
        {
            throw new IllegalStateException("A block can only have one unit at a time.");
        }
        else
        {
            unit = u;
            stepOnAbility = false;
        }
    }
    
    /**
     * Remove the unit on this block
     */
    public void removeUnit()
    {
        unit = null;
        stepOnAbility = true;
    }    
    
    /**
     * Get the current unit on this block
     * 
     * @return   current unit on this block
     */
    public Unit getUnit()
    {
        if (unit == null)
            throw new IllegalStateException("No unit on this block, cannot get it!");
        return unit;
    }
    
    /**
     * See if there is any unit on this block
     * 
     * @param   return true if there is a unit on it
     */
    public boolean isUnitOn()
    {
        if (unit == null)
            return false;
        else 
            return true;
    }
    
    /**
     * Get the block shape in specified range relative to this block
     * 
     * @return   block shape
     */
    public BlockShape getBlockShapeInRange(final int range)
    {
        BlockShape blockShape = new BlockShape(board);
        final int q = range / Board.ORTHOGONAL_DISTANCE;
        for (int y = position.y - q; y < position.y + q + 1; y++)
        {
            for (int x = position.x - q; x < position.x + q + 1; x++)
            {
                if (board.isInside(x, y))
                {
                    Block block = board.getBlock(x, y);
                    if (y == position.y - q || y == position.y + q)
                    {
                        if (x == position.x)
                        {
                            blockShape.addBlock(block);
                        }
                        else if (x > position.x - q && x < position.x + q)
                        {
                            if (getDistance(block) <= range)
                            {
                                blockShape.addBlock(block);
                            }
                        }
                    }
                    else if (x == position.x -q || x == position.x + q)
                    {
                        if (y == position.y)
                        {
                            blockShape.addBlock(block);
                        }
                        else
                        {
                            if (getDistance(block) <= range)
                            {
                                blockShape.addBlock(block);
                            }
                        }
                    }
                    else
                    {
                        blockShape.addBlock(block);
                    }
                }
//                 else
//                 {
//                     blockShape.addBlock(Board.NULL_BLOCK);
//                 }
            }
        }
        return blockShape;
    }
    
    /**
     * Get the distance between this block and the specified block.
     * Discard the decimal numbers
     * 
     * @param   b   the block to be measured
     * @return  the distance between this block and the specified block
     */
    public final int getDistance(Block b)
    {
        return Board.getDistance(this, b);
    }    
 
    /**
     * isAbleToStepOn
     * 
     * @return   true if this block is able to be stepped on
     */
    public final boolean isAbleToStepOn()
    {
        return stepOnAbility;
    }
    
    /**
     * set step on ability
     * 
     * @param   able    whether or not this block can be stepped on
     */
    public void setStepOnAbility(boolean able)
    {
        stepOnAbility = able;
    }    
    
//     /**
//      * Get the board where the block is on
//      * 
//      * @return   board
//      */
//     public final Board getBoard()
//     {
//         return board;
//     }    
 
//     /**
//      * Make penetratability according to the position of this block.
//      * Subclass can override this method to create new penetratability.
//      * 
//      * @param  x   the x coord of the block
//      * @param  y   the y coord of the block 
//      */
//     protected static boolean[] makePenetratability(Board b, int x, int y)
//     {
//         boolean[] penetrate = new boolean[]{true, true, true, true};
//         if (x == 0)
//             penetrate[Block.LEFT_WALL] = false;
//         else if (x == b.getDimension().width - 1)
//             penetrate[Block.RIGHT_WALL] = false;
//             
//         if (y == 0)
//             penetrate[Block.TOP_WALL] = false;
//         else if (y == b.getDimension().height - 1)
//             penetrate[Block.BOTTOM_WALL] = false;
//             
//         return penetrate;
//     }    

//     /**
//      * Get the board where this block is on
//      * 
//      * @return position
//      */
//     public final Board getBoard()
//     {
//         return board;
//     }
    
//     /**
//      * Get the type of this block
//      * 
//      * @return position
//      */
//     public final int getType()
//     {
//         return blockType;
//     }    
    
    private Board board;
    private Point position;
    private Unit unit;
    private boolean stepOnAbility;
    private Rectangle2D.Double blockRect;
    private boolean firstDraw;
//     private final int blockType;
    
    private static final int INI_UNIT_CAPACITY = 20;
//     public static final int TOP_WALL = 0;
//     public static final int RIGHT_WALL = 1;
//     public static final int BOTTOM_WALL = 2;
//     public static final int LEFT_WALL = 3;
    
//     public static final int TYPE_GROUND = 1;
//     public static final int TYPE_BASE = 2;
//     public static final int TYPE_RESOURCE = 3;    
}