import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

/**
 * Write a description of class BlockShape here.
 * 
 * @author TuTu
 * @version 2007.11.12
 */
public class BlockShape
{
    /**
     * Constructor for objects of class BlockShape
     */
    public BlockShape(Board b)
    {
        board = b;
        blockList = new ArrayList<Block>(DEFAULT_NUM_BLOCK_CAPACITY);
//         pivotBlockIndex = 0;
    }

    /**
     * Make and return the block shape of this unit
     */
    public static BlockShape makeRectBlockShape(Board board, Dimension dim, Point pos)
    {
        BlockShape bShape = new BlockShape(board);
        for (int i = 0; i < dim.width * dim.height; i++)
        {
            bShape.addBlock(new Point((pos.x + (i % dim.width)), (pos.y + (i / dim.width))));
        }
        bShape.setPivotBlock(board.getBlock(new Point((pos.x + (dim.width/2)), (pos.y + (dim.height/2)))));
//         bShape.setPivotBlockIndex((int)(dim.width * dim.height / 2));
        return bShape;
    }
    
    /**
     * Add a block to this block shape
     * 
     * @param    block    the block to be added
     */
    public void addBlock(Block block)
    {
        if (!blockList.contains(block))
        {
            blockList.add(block);
        }
    }

    /**
     * Add a block to this block shape
     * 
     * @param    pos    the block point to be added
     */
    public void addBlock(Point pos)
    {
        if (!blockList.contains(board.getBlock(pos)))
        {
            blockList.add(board.getBlock(pos));
        }
    }
    
    /**
     * Add a block position to this block shape
     * 
     * @param   x   the block point.x to be added
     * @param   y   the block point.y to be added
     */
    public void addBlock(int x, int y)
    {
        if (!blockList.contains(board.getBlock(x, y)))
        {
            blockList.add(board.getBlock(x, y));
        }
    }
    
   /**
     * Make a block position to be pivot block
     * 
     * @return   the block point to be added
     */
    public void setPivotBlock(Block pivot)
    {
        if (blockList.contains(pivot))
        {
            pivotBlockIndex = blockList.indexOf(pivot);
        }
        else
        {
            blockList.add(pivot);
            pivotBlockIndex = blockList.indexOf(pivot);
        }
    }
    
//    /**
//      * Set the step on ability to this block shape
//      * @param   able    true or false to set if this block shape can be steo on
//      */
//     public void setStepOnAbility(boolean able)
//     {
//         for (int i = 0; i < blockList.size(); i++)
//             getBlock(i).setStepOnAbility(able);
//     }    
 
    /**
     * Add a unit to this whole block shape
     * 
     * @param    unit    the unit to be added
     */
    public void addUnit(Unit unit)
    {
        for (int i = 0; i < blockList.size(); i++)
        {
            if (!getBlock(i).isUnitOn())
            {
                getBlock(i).addUnit(unit);
            }
            else if (getBlock(i).isUnitOn() && getBlock(i).getUnit().equals(unit))
            {
                // unit alread added, do nothing
            }
            else
            {
                throw new IllegalArgumentException("Another unit is already on this block, unable to add!");
            }
        }
    }
    
    /**
     * Remove the unit on this block shape
     */
    public void removeUnit()
    {
        for (int i = 0; i < blockList.size(); i++)
        {
            getBlock(i).removeUnit();
        }
//         pivotBlock.removeUnit();   
    }    
    
   /**
     * Make a block position to be pivot block
     */
    public void translate(int dx, int dy)
    {
        for (int i = 0; i < blockList.size(); i++)
        {
            Point pos = (getBlock(i)).getPosition();
            blockList.set(i, board.getBlock(pos.x + dx, pos.y + dy));
        }
//         pivotBlock = board.getBlock(pivotBlock.getPosition().x + dx,
//                                     pivotBlock.getPosition().y + dy);
    }    

   /**
     * Get a block of specified index
     * 
     * @return   the block 
     */
    public final Block getBlock(int index)
    {
        return ((Block)blockList.get(index));
    }
    
    
   /**
     * Get pivot block of this block shape
     * 
     * @return   the pivot block
     */
    public final Block getPivotBlock()
    {
        return (Block)blockList.get(pivotBlockIndex);
    }
    
   /**
     * Get the index of the specified block
     * If there is no such block in this block shape, return -1
     * 
     * @return   the block index
     */
    public final int getBlockIndex(Block b)
    {
        return blockList.indexOf(b);
    }   
    
   /**
     * Get the most bottom right block of this block shape.
     * Note: bottom is prior to right
     * 
     * @return   the most bottom right block
     */
    public final Block getBottomRightBlock()
    {
        Point pos1 = new Point();
        Point pos2 = new Point();
        pos1.setLocation((getBlock(0)).getPosition());
        for (int i = 1; i < blockList.size(); i++)
        {
            pos2.setLocation(getBlock(i).getPosition());
            if (pos2.y > pos1.y)
            {
                pos1 = pos2;
            }
            else if (pos2.y == pos1.y && pos2.x > pos1.x)
            {
                pos1 = pos2;
            }
        }
        return board.getBlock(pos1);
    }    
    
   /**
     * Get number of blocks in this shape
     * 
     * @return   the shape size in blocks
     */
    public final int getSize()
    {
        return blockList.size();
    }    
    
    /**
     * Get the outer boundary block shape of specified direction of this block shape
     * 
     * @param   dir  the direction to find outer bounary
     * @return     the outer boundary block shape
     */
    public BlockShape getOuterBoundaryBlockShape(Direction dir)
    {
        BlockShape boundaryBlockShape = new BlockShape(board);
        Point pos = new Point();
        for (int i = 0; i < blockList.size(); i++)
        {        
            pos.setLocation(getBlock(i).getPosition());
            pos.translate(dir.x, dir.y);
            if (!board.isInside(pos))
            {
                boundaryBlockShape.addBlock(Board.NULL_BLOCK);
            }
            else if (!blockList.contains(board.getBlock(pos)))
            {
                boundaryBlockShape.addBlock(board.getBlock(pos));
            }
        }
        return boundaryBlockShape;
    }
    
    /**
     * Get the inner boundary block shape of this block shape
     * 
     * @return     the inner boundary block shape
     */
    public BlockShape getInnerBoundaryBlockShape()
    {
        BlockShape boundaryBlockShape = new BlockShape(board);
        Point pos = new Point();
        Direction dir = new Direction(Direction.UP);
        for (int i = 0; i < blockList.size(); i++)
        {  
            
            for (int j = 0; j < 8; j++) // go through eight directions
            {
                pos.setLocation(getBlock(i).getPosition());
                pos.translate(dir.x, dir.y);
                if (!board.isInside(pos))
                {
                    boundaryBlockShape.addBlock(getBlock(i));
                    break;
                }
                else if (!blockList.contains(board.getBlock(pos)))
                {
                    boundaryBlockShape.addBlock(getBlock(i));
                    break;
                }
                dir.turnClockwise();
            }
        }
        return boundaryBlockShape;
    }

    /**
     * Return the distance from the pivot block of this block shape to
     * the closest block of the specified block shape.
     * 
     * @param   bShape
     * @return  the distance
     */
    public int getDistance(BlockShape bShape)
    {
        BlockShape boundaryBlockShape = bShape.getInnerBoundaryBlockShape();
        int distance1;
        int distance2;
        distance1 = Board.getDistance(getPivotBlock(), boundaryBlockShape.getBlock(0));
        for (int i = 1; i < boundaryBlockShape.getSize(); i++)
        {  
            distance2 = Board.getDistance(getPivotBlock(), boundaryBlockShape.getBlock(i));
            if (distance2 < distance1)
            {
                distance1 = distance2;
            }
        }
        return distance1;
    }  
    
    /**
     * Return the closest block of the specified block shape with respect to 
     * the pivot block of this block shape.
     * 
     * @param   bShape
     * @return  the block
     */
    public Block getClosestBlock(BlockShape bShape)
    {
        BlockShape boundaryBlockShape = bShape.getInnerBoundaryBlockShape();
        Block closestBlock;
        int distance1;
        int distance2;
        closestBlock = boundaryBlockShape.getBlock(0);
        distance1 = Board.getDistance(getPivotBlock(), boundaryBlockShape.getBlock(0));
        for (int i = 1; i < boundaryBlockShape.getSize(); i++)
        {  
            distance2 = Board.getDistance(getPivotBlock(), boundaryBlockShape.getBlock(i));
            if (distance2 < distance1)
            {
                distance1 = distance2;
                closestBlock = boundaryBlockShape.getBlock(i);
            }
        }
        return closestBlock;
    }    
    
//     /**
//      * Get the OUTER boundary block shape of specified direction of this block shape
//      * 
//      * @param   dir  the direction to find outer bounary
//      * @param   numLayer  the number of layers to be find
//      * @return     the outer boundary block shape with numLayer layers
//      */
//     public BlockShape getOuterBoundaryBlockShape(Direction dir, int numLayer)
//     {
//         BlockShape boundaryBlockShape = getOuterBoundaryBlockShape(dir);
//         for (int i = 1; i < numLayer; i++)
//         {
//             boundaryBlockShape = boundaryBlockShape.createUnion(boundaryBlockShape.getOuterBoundaryBlockShape(dir));
//         }
//         
//         return boundaryBlockShape;
//     }
    
    
    /**
     * Create a union block shape
     * 
     * @param   bShape  the block shape to be added in this union
     * @return     the union block shape
     */
    public BlockShape createUnion(BlockShape bShape)
    {
        BlockShape unionBlockShape = new BlockShape(board);
        for (int i = 0; i < getSize(); i++)
        {
            unionBlockShape.addBlock(getBlock(i));
        }
        
        for (int i = 0; i < bShape.getSize(); i++)
        {
            unionBlockShape.addBlock(bShape.getBlock(i));
        }        
        return unionBlockShape;
    }
    
    /**
     * isAbleToStepOn
     * 
     * @return   true if the blocks of this block shape are all able to be stepped on
     */
    public final boolean isAbleToStepOn()
    {
        for (int i = 0; i < getSize(); i++)
        {
            if (!getBlock(i).isAbleToStepOn())
                return false;
        }
        return true;
    }
    
    /**
     * isMoving
     * 
     * @return   true if all the units on this block shape are moving
     */
    public final boolean isMoving()
    {
        for (int i = 0; i < getSize(); i++)
        {
            if (getBlock(i).isUnitOn() 
                && getBlock(i).getUnit().getState() != Unit.STATE_MOVE)
                return false;
        }
        return true;
    }    
    
    /**
     * Test if this block shape certains certain block
     * 
     * @return   true if this block shape certains certain block
     */
    public final boolean contains(Block b)
    {
        return blockList.contains(b);
    }
    
    /**
     * Test if this block shape intersects with another specified block shape
     * 
     * @return   true if this block shape intersects with another specified block shape
     */
    public final boolean intersects(BlockShape bShape)
    {
        for (int i = 0; i < bShape.getSize(); i++)
        {
            if (blockList.contains(bShape.getBlock(i)))
                return true;
        }
        return false;
    }   
    
    /**
     * Get all the units on this block shape
     * 
     * @return   the unitList of this block shape
     */
    public final ArrayList<Unit> getUnitList()
    {
        ArrayList<Unit> unitList = new ArrayList<Unit>();
        for (int i = 0; i < getSize(); i++)
        {
            if (getBlock(i).isUnitOn())
            {
                if (!unitList.contains(getBlock(i).getUnit()))
                {
                    unitList.add(getBlock(i).getUnit());
                }
            }
        }
        return unitList;
    }
    
    
//     private class BlockPosition extends Point
//     {
//         public BlockPosition(int x, int y)
//         {
//             super(x, y);
//             checked = false;
//         }
//         
//         public BlockPosition(Point pos)
//         {
//             super(pos);
//             checked = false;
//         }
//         
//         private boolean checked;
//     }
    
    // blocksPosition stores the positions of all the blocks of this unit
    // it should work fine even if the indexes of the blocks are randomly arranged 
    // Customarily index start from 0 at the most top then left block of this unit,
    // and increase right then down.
    private ArrayList<Block> blockList;
    private int pivotBlockIndex; // the pivot block remains the same place when rotate
    
    private Board board;
//     private int referenceBlockIndex; // the main block 
//     private int faceDirection;
    private static final int DEFAULT_NUM_BLOCK_CAPACITY = 64;
}
