import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.Color;
import java.awt.BasicStroke;

/**
 * Write a description of class Unit here.
 * 
 * @author TuTu
 * @version 2007.11.8
 */
public abstract class Unit implements Selectable //MouseInputListener
{
    /**
     * Constructor for objects of class Unit
     */
    public Unit(Player own, Board b, BlockShape bShape, Point2D.Double pos, int type, int hCapacity, double maxS)
    {
//         unitAI = ai;
        owner = own;
        board = b;
//         position = pos;
        unitType = type;        
        healthCapacity = hCapacity;
        health = healthCapacity;
        maxSpeed = maxS;
        
//         boardPosition = transformBlockToBoardCoord(blockPosition);
        pivotPosition = new Point2D.Double();
        drawPosition = pos;
        selected = false;
        visible = true;
//         velocity = new MathVector2D(0., 0.);
        speed = maxS;
        blockShape = bShape;
        state = STATE_STANDBY;
        
        owner.addUnit(this);        
        blockShape.addUnit(this);
        
        mobility = false;
        attackability = false;
    }
    
//     public void mouseClicked(MouseEvent e){};
//     public void mouseEntered(MouseEvent e){};
//     public void mouseExited(MouseEvent e){};
//     public void mousePressed(MouseEvent e){};
//     public void mouseReleased(MouseEvent e){};
//     public void mouseDragged(MouseEvent e){};
//     public void mouseMoved(MouseEvent e){};

    /**
     * Draw the body of this unit.
     * 
     * @param  g2   the context of the graphics
     * @param  layer    the layer to be drawn
     */
    public abstract void draw(Graphics2D g2, final int layer);
    
    /**
     * Draw the health of this unit.
     * 
     * @param  g2   the context of the graphics
     */
    protected abstract void drawHealth(Graphics2D g2);    

    /**
     * Draw the selection glow of this unit.
     * 
     * @param  g2   the context of the graphics
     */
    public abstract void drawSelection(Graphics2D g2);    
    
    /**
     * Get the position in paint coords of this unit
     * 
     * @return position
     */
    public final Point2D.Double getDrawPosition()
    {
        return drawPosition;
    }
    
    /**
     * Get the pivot position in paint coords of this unit
     * 
     * @return pivot position
     */
    public final Point2D.Double getPivotPosition()
    {
        return pivotPosition;
    }    
    
//     /**
//      * Get the position in block coords of this unit
//      * 
//      * @return position
//      */
//     public final Point getPosition()
//     {
//         return position;
//     }    

    /**
     * Get the board where the unit is on
     * 
     * @return board
     */
    public final Board getBoard()
    {
        return board;
    }
    
    /**
     * Get the BlockShape of this unit
     * 
     * @return BlockShape
     */
    public BlockShape getBlockShape()
    {
        return blockShape;
    }    
    
//     /**
//      * Transform the coordinates from relative to block to relative to board
//      * 
//      * @return position relative to board
//      */
//     private Point2D.Double transformBlockToBoardCoord(Point2D.Double blockCoord)
//     {
//         return  new Point2D.Double(position.x + blockCoord.x, position.y + blockCoord.y);
//     }    

//     /**
//      * Transform the coordinates from relative to board to relative to block
//      * 
//      * @return position relative to block
//      */
//     private Point2D.Double transformBoardToBlockCoord(Point2D.Double boardCoord)
//     {
//         return  new Point2D.Double(boardCoord.x - position.x, boardCoord.y - position.y);
//     }   

//     /**
//      * Transform the coordinates from board coords to position block-coords
//      * 
//      * @return position in terms of block
//      */
//     private Point transformBoardToPosition(Point2D.Double boardCoord)
//     {
//         return  new Point((int)boardCoord.x, (int)boardCoord.y);
//     }    
    
    /**
     * Get the owner of this unit
     * 
     * @return     position 
     */
    public final Player getOwner()
    {
        return owner;
    }
    
    /**
     * Get the type of the unit
     * 
     * @return     position 
     */
    public final int getType()
    {
        return unitType;
    }    
    
    /**
     * Get the healthCapcity of this unit
     * 
     * @return     healthCapcity 
     */
    public final double getHealthCapacity()
    {
        return healthCapacity;
    }    
 
    /**
     * Get the health of this unit
     * 
     * @return     health
     */
    public final double getHealth()
    {
        return health;
    }
    
    /**
     * Get the speed of this unit
     * 
     * @return     speed
     */
    public final double getSpeed()
    {
        return speed;
    }    
    
//     /**
//      * Get the velocity of this unit
//      * 
//      * @return     velocity
//      */
//     public final MathVector2D getVelocity()
//     {
//         return velocity;
//     }    
    
    /**
     * Return true if this unit is selected by user.
     * 
     * @return     true or false
     */
    public final boolean isSelected()
    {
        return selected;
    }
    
    /**
     * Select this unit
     * 
     */
    public void selectIt()
    {
        selected = true;
    }
    
    /**
     * De-select this unit
     * 
     */
    public void deSelectIt()
    {
        selected = false;
    }
    
    /**
     * If this unit is alive return true
     * 
     * @return true if alive
     */
    public boolean isAlive()
    {
        boolean alive;
        
        if (health > 0)
            alive = true;
        else
            alive = false;
            
        return alive;
    }
    
    /**
     * Set the mobility of this unit
     * 
     * @param   mob  the mobility to be set
     */
    public void setMobility(boolean mob)
    {
        mobility = mob;
    }
    
    /**
     * Set the attackability of this unit
     * 
     * @param   attack  the attackability to be set
     */
    public void setAttackability(boolean attack)
    {
        attackability = attack;
    }    
    
    /**
     * If this unit is able to move return true
     * 
     * @return true if movable
     */
    public boolean isMoveable()
    {
        return mobility;
    }
    
    /**
     * If this unit is able to attack return true
     * 
     * @return true if attackable
     */
    public boolean isAttackable()
    {
        return attackability;
    }    
    
//     /**
//      * Test if a point is inside it.
//      * 
//      * @return Return true if a point is inside it.
//      */
//     abstract public boolean isInside(Point2D.Double p); 

    /**
     * If this unit is visible
     * 
     * @return true if visible
     */
    public boolean isVisible()
    {
        return visible;
    }    
 
    /**
     * Set the visibility of this unit
     * 
     * @param v true if set visible
     */
    public void setVisible(boolean v)
    {
        visible = v;
    }

    /**
     * Set the drawPosition of this unit
     * 
     * @param   pos   the position to be set
     */
    protected void setDrawPosition(Point2D.Double pos)
    {
        drawPosition.setLocation(pos.x, pos.y);
//         makePivotPosition();
    }
   
    /**
     * Set the drawPosition of this unit
     * 
     * @param   x   the x coord to be set
     * @param   y   the y coord to be set
     */
    protected void setDrawPosition(double x, double y)
    {
        drawPosition.setLocation(x, y);
//         makePivotPosition();
    }
    
    /**
     * Make the pivotPosition according to the drawPosition of this unit
     */
    protected abstract void makePivotPosition();
    
    /**
     * Make the drawPosition according to the pivotPosition of this unit
     */
    protected abstract void makeDrawPosition();    

    /**
     * Set the pivotPosition of this unit
     * 
     * @param   x   the x coord to be set
     * @param   y   the y coord to be set
     */
    public void setPivotPosition(double x, double y)
    {
        pivotPosition.setLocation(x, y);
        makeDrawPosition();
    }
    
    /**
     * Get the state of this unit
     * 
     * @return blockPositions
     */
    public final int getState()
    {
        return state;
    }    
    
    /**
     * Set the state of this unit
     * 
     * @param   st   the state to be set
     */
    public void setState(final int st)
    {
        state = st;
    }    
    
    /**
     * Let the unit take action
     * 
     * @param   timeStep    time step for taking action
     */
    public abstract void takeAction(double timeStep);   
    
    /**
     * Let the unit take attack
     * 
     * @param   attackPower    the attackPower this unit is taking
     */
    public void takeAttack(double attackPower)
    {
        health -= attackPower;
        if (health <= 0)
        {
            die();
        }
    }
    
    /**
     * Let the unit die
     * 
     * @param   attackPower    the attackPower this unit is taking
     */
    public void die()
    {
        blockShape.removeUnit();
        owner.removeUnit(this);
        owner = board.getMotherPlayer();
        visible = false;
    }
    
    /**
     * Set the sight range of this unit
     * 
     * @param   sight   the sight to be set
     */
    public void setSightRange(final int sight)
    {
        sightRange = sight;
    }
    
    /**
     * Get the sight range of this unit
     * 
     * @return  SightRange
     */
    public final int getSightRange()
    {
        return sightRange;
    }
    
    private int health;
    private final int healthCapacity;
//     private Point position; // the top-left position in the view of the whole board in block coords
    private Point2D.Double drawPosition; // the top-left position in the view of the whole board in painting coords
    private Point2D.Double pivotPosition; // the draw position of the pivot point, it is related to draw posiiton
    private Player owner;
    private final int unitType;
    private boolean selected;
    private boolean visible;
    private final double maxSpeed; // blocks width per second
//     private MathVector2D velocity; // blocks width per second
    private double speed;
    private Board board;
    private BlockShape blockShape;
    private int state;
    private boolean mobility;
    private boolean attackability;
    private int sightRange;
    
    public static final int TYPE_BASIC_BATTLE = 1;
    public static final int TYPE_BASE = 2;
    public static final int TYPE_RESOURCE = 3;
    
    public static final Color SELECTION_GLOW_COLOR = Color.GREEN;
//     public static final double SELECTION_GLOW_WIDTH = 0.2;
    public static final double LENGH_PER_HEALTH_POINT = 0.0095;
    public static final double HEALTH_BAR_HEIGHT = 0.3;
    public static final BasicStroke SELECTION_STROKE = new BasicStroke(0.3f);
    
    public static final int STATE_STANDBY = 0;
    public static final int STATE_MOVE = 1;
    public static final int STATE_ATTACK = 2;

//     private UnitAI unitAI;
//     private Timer movingTimer;
//     private static final int MOVING_TIMER_DELAY = 30; // mili-second
}
