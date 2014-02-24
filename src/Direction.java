
/**
 * Write a description of class Direction here.
 * 
 * @author TuTu
 * @version 2007.11.13
 */
public class Direction implements Cloneable
{
    /**
     * Constructor for objects of class Direction
     */
    public Direction(final int dir)
    {
        if (dir < 0 || dir > 7)
            throw new IllegalArgumentException("Input direction not defined!");
            
        direction = dir;
        setVector(dir);

    }
    
    public Direction(final int x, final int y)
    {
        setDirection(x, y);
    }
    
    public Direction(Direction dir)
    {
        direction = dir.getDirection() ;
        setVector(direction);
    }    
    
    public Direction()
    {
        // construct a random direction
        this(getRandomDirection());
    }    

    /**
     * Clone this direction
     * 
     * @return    the clone of this direction
     */
    public Direction clone()
    {
        return new Direction(this);
    }    
    
    /**
     * Generate a random direction
     * 
     * @return    random direction int
     */
    public static int getRandomDirection()
    {
        int dir;
        do
        {
            dir = (int)(Math.random() * 8);
        }while(dir == 8);
        return dir;
    }

    /**
     * Return true if the position of the input block equals to this block
     *
     * @return  true or false if the position of the input block equals to this block
     */    
    public boolean equals(Direction dir)
    {
        return direction == dir.getDirection();
    }    
    
    /**
     * Turn clockwise
     * 
     * @return     turn this direction 45 degrees right
     */
    public Direction turnClockwise()
    {
        direction = (direction + 1) % 8;
        setVector(direction);
        return this;
    }
    
    /**
     * Turn Counterclockwise
     * 
     * @return     turn this direction 45 degrees left
     */
    public Direction turnCounterclockwise()
    {
        if (direction == 0)
            direction = 7;
        else
            direction = (direction - 1) % 8;
        setVector(direction);        
        return this;
    }
 
    /**
     * Turn this direction toward the specified direction for 45 degrees
     * if they are in the opposite direction, randomly turn clockwise or counterclockwise
     */
    public void turnToward(Direction dir)
    {
        int indexDiff = dir.getDirection() - direction;
        if (indexDiff < 0)
        {
            indexDiff = 8 + indexDiff;
        }
        
        if (indexDiff == 0)
        {
            // they are in the same direction, do nothing
        }
        else if (indexDiff < 4)
        {
            this.turnClockwise();
        }
        else if (indexDiff > 4)
        {
            this.turnCounterclockwise();
        }
        else // they are in the opposite direction
        {
            if (Math.random() < 0.5)
            {
                this.turnClockwise();
            }
            else
            {
                this.turnCounterclockwise();
            }
        }
    } 
    
    /**
     * Turn this direction against the specified direction for 45 degrees
     * if they are in the same direction, randomly turn clockwise or counterclockwise
     */
    public void turnAgainst(Direction dir)
    {
        int indexDiff = dir.getDirection() - direction;
        if (indexDiff < 0)
        {
            indexDiff = 8 + indexDiff;
        }
 
        if (indexDiff == 0)
        {
            if (Math.random() < 0.5)
            {
                this.turnClockwise();
            }
            else
            {
                this.turnCounterclockwise();
            }
        }
        else if (indexDiff < 4)
        {
            this.turnCounterclockwise();
        }
        else if (indexDiff > 4)
        {
            this.turnClockwise();
        }
        else // they are in the opposite direction
        {
            // they are in the opposite direction, do nothing
        }        
    
    }    
    
    /**
     * Set the vector according to the direction int inputted
     */
    private void setVector(final int dir)
    {
        switch (dir)
        {
            case (VAL_UP):
                x = 0;
                y = -1;
                normalX = 0.;
                normalY = -1.;
                break;
            case (VAL_UP_RIGHT):
                x = 1;
                y = -1;
                normalX = 1./Math.sqrt(2);
                normalY = -1./Math.sqrt(2);
                break;
            case (VAL_RIGHT):
                x = 1;
                y = 0;
                normalX = 1.;
                normalY = 0.;
                break;
            case (VAL_DOWN_RIGHT):
                x = 1;
                y = 1;
                normalX = 1./Math.sqrt(2);
                normalY = 1./Math.sqrt(2);
                break;
            case (VAL_DOWN):
                x = 0;
                y = 1;
                normalX = 0.;
                normalY = 1.;               
                break;
            case (VAL_DOWN_LEFT):
                x = -1;
                y = 1;
                normalX = -1./Math.sqrt(2);
                normalY = 1./Math.sqrt(2);
                break;
            case (VAL_LEFT):
                x = -1;
                y = 0;
                normalX = -1.;
                normalY = 0.;
                break;
            case (VAL_UP_LEFT):
                x = -1;
                y = -1;
                normalX = -1./Math.sqrt(2);
                normalY = -1./Math.sqrt(2);
                break;
        }
    }    
        
    
    /**
     * Get direction
     * 
     * @return     this direction
     */
    public final int getDirection()
    {
        return direction;
    }
    
    /**
     * Set direction
     * 
     * @param   x   the direction.x to be set
     * @param   y   the direction.y to be set
     */
    public void setDirection(final int x, final int y )
    {
        if (x > 0)
        {
            if (y > 0)
                direction = 3;
            else if (y < 0)
                direction = 1;
            else
                direction = 2;
        }
        else if (x < 0)
        {
            if (y > 0)
                direction = 5;
            else if (y < 0)
                direction = 7;
            else
                direction = 6;
        }
        else
        {
            if (y > 0)
                direction = 4;
            else if (y < 0)
                direction = 0;
            else
                throw new IllegalArgumentException("Undefined direction: (0, 0)");
        }
        setVector(direction);
    } 
    
    /**
     * Set direction
     * 
     * @param   dir  set this direction
     */
    public void setDirection(final int dir )
    {
        direction = dir;
        setVector(direction);
    }
    
//     /**
//      * Get direction
//      * 
//      * @return   direction
//      */
//     protected final int getDirection()
//     {
//         return direction;
//     }    
    
    
    private int direction;
    
    // the vector this direction represents
    public int x;
    public int y;
    
    // the normalized vector this direction represents
    public double normalX;
    public double normalY;
    
    public static final int VAL_UP = 0;
    public static final int VAL_UP_RIGHT = 1;
    public static final int VAL_RIGHT = 2;
    public static final int VAL_DOWN_RIGHT = 3;
    public static final int VAL_DOWN = 4;
    public static final int VAL_DOWN_LEFT = 5;
    public static final int VAL_LEFT = 6;
    public static final int VAL_UP_LEFT = 7;
    
    public static final Direction UP = new Direction(VAL_UP);
    public static final Direction UP_RIGHT = new Direction(VAL_UP_RIGHT);
    public static final Direction RIGHT = new Direction(VAL_RIGHT);
    public static final Direction DOWN_RIGHT = new Direction(VAL_DOWN_RIGHT);
    public static final Direction DOWN = new Direction(VAL_DOWN);
    public static final Direction DOWN_LEFT = new Direction(VAL_DOWN_LEFT);
    public static final Direction LEFT = new Direction(VAL_LEFT);
    public static final Direction UP_LEFT = new Direction(VAL_UP_LEFT);  
}
