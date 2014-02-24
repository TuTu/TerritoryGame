import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BasicStroke;

/**
 * Write a description of class BasicBattleUnit here.
 * 
 * @author TuTu
 * @version 2007.11.9
 */
public class BasicBattleUnit extends RectShapeUnit implements Attackable, Moveable
{
    /**
     * Constructor for objects of class BattleUnit
     */
    public BasicBattleUnit(Player owner, Board board, Point pos, final int hCapacity, double maxSpeed, Dimension dim)
    {
        super(owner, board, pos, Unit.TYPE_BASIC_BATTLE, hCapacity, maxSpeed, dim);
        ellipse = new Ellipse2D.Double();
        makeBody();
        board.getBlock(pos).setStepOnAbility(false); 
        unitAI = new UnitAI0(this, board);
        setMobility(true);
        setAttackability(true);
        setSightRange(SIGHT_RANGE);
//        glowEllipse = new Ellipse2D.Double();
//         setGlowEllipse();
        attackRangeLowerLimit = ATTACK_RANGE_LOWER_LIMIT;
        attackRangeUpperLimit = ATTACK_RANGE_UPPER_LIMIT;
        attackPeriod = ATTACK_PERIOD;
        attackTimeCounter = attackPeriod;
        shootPeriod = SHOOT_PERIOD;
        shootTimeCounter = 0.;
        shootLine = new Line2D.Double();
        Color ownerColor = getOwner().getColor();
        shootColor = new Color((ownerColor.getRed() > 0 ? ownerColor.getRed() : 100),
                               (ownerColor.getGreen() > 0 ? ownerColor.getGreen() : 100),
                               (ownerColor.getBlue() > 0 ? ownerColor.getBlue() : 100),
                                SHOOT_COLOR_ALPHA);
        attackPower = ATTACK_POWER;
        mode = Attackable.MODE_NORMAL;
    }

    public BasicBattleUnit(Player owner, Board board, Point pos)
    {
        this(owner, board, pos, HEALTH_CAPACITY, MAX_SPEED, DIMENSION);
    }
    
    /**
     * Make and set the body shape of this unit.
     */
    private void makeBody()
    {
        ellipse.setFrame(getDrawPosition().x + WHITE_EDGE_WIDTH, getDrawPosition().y + WHITE_EDGE_WIDTH,
                         getDimension().width - 2*WHITE_EDGE_WIDTH, getDimension().height - 2*WHITE_EDGE_WIDTH);
    }
    
    /**
     * Draw the body of this unit.
     * 
     * @param  g2   the context of the graphics
     * @param  layer    the layer to be drawn
     */
    public void draw(Graphics2D g2, final int layer)
    {
        g2.setStroke(Game.BASIC_STROKE);
        Player owner = getOwner();
        Color ownerColor = owner.getColor();
        Color unitColor = ownerColor; // writing like this to make futre updating easier
        
        if (layer == 0)
        {
// // draw attack range/////
// if (owner.equals(getBoard().getHumanPlayer()))
// {
// Attackable attackUnit;
// BlockShape bs = getBlockShape().getPivotBlock().getBlockShapeInRange(getAttackRangeUpperLimit());
// for (int i = 0; i < bs.getSize(); i++)
// {
//     bs.getBlock(i).drawBackground(g2, Color.GRAY);
// }
// }               
// /////////////////////////
        }
        else if (layer == 1)
        {
            if (getState() != Unit.STATE_STANDBY)
            {
                makeBody();
//                 setGlowEllipse();
    //             ellipse.setFrame(getDrawPosition().x + WHITE_EDGE_WIDTH, getDrawPosition().y + WHITE_EDGE_WIDTH,
    //                              getDimension().width - 2*WHITE_EDGE_WIDTH, getDimension().height - 2*WHITE_EDGE_WIDTH);
            }           
            
            g2.setColor(unitColor);
            g2.fill(ellipse);
            
            if (isSelected())
            {
                drawSelection(g2);
            }
        }
        else if (layer == 2)
        {
//             if (getState() != Unit.STATE_STANDBY)
//             {
//                 setGlowEllipse();
//             }
//             
            if (getHealth() < getHealthCapacity() || isSelected())
            {
                drawHealth(g2);
            }
     
            // draw shoot line
            drawShootLine(g2);
//             g2.setStroke(SHOOT_STROKE);
//             g2.setColor(shootColor);
//             g2.draw(shootLine);
        }
//         else
//         {
//             throw new IllegalArgumentException("Undfined layer to be drawn! Layer:" + layer);
//         }
        
//         g2.setColor(ownerColor.darker());        
//         g2.draw(ellipse);      
// System.out.println(((Point2D.Double)shootLine.getP1()).toString() + ((Point2D.Double)shootLine.getP2()).toString());
// g2.drawString("(" + getBlockShape().getPivotBlock().getPosition().x + ","
//               + getBlockShape().getPivotBlock().getPosition().y + ")", (float)getDrawPosition().x, (float)getDrawPosition().y);        
    }    

    /**
     * Draw the selection glow of this unit.
     * 
     * @param  g2   the context of the graphics
     */
    public void drawSelection(Graphics2D g2)
    {
//         if (getState() == Unit.STATE_MOVE)
//         {
//             setGlowEllipse();
//         }
//         g2.setStroke(Game.BASIC_STROKE);
//         g2.setColor(SELECTION_GLOW_COLOR);
//         g2.fill(glowEllipse);
//         g2.draw(glowEllipse);
        g2.setStroke(Unit.SELECTION_STROKE);
        g2.setColor(SELECTION_GLOW_COLOR);
        g2.draw(ellipse);
    }

    /**
     * Draw the ShootLine of this unit.
     * 
     * @param  g2   the context of the graphics
     */
    private void drawShootLine(Graphics2D g2)
    {
        g2.setStroke(SHOOT_STROKE);
        g2.setColor(shootColor);
        g2.draw(shootLine);
    }    
    
    /**
     * Set the ShootLine of this unit.
     */
    private void setShootLine()
    {
        if(shootTimeCounter <= 0.)
        {
            shootLine.setLine(0., 0., 0., 0.);
        } 
        else
        {
            double dx = target.getPivotPosition().x - target.getBlockShape().getPivotBlock().getPosition().x;
            double dy = target.getPivotPosition().y - target.getBlockShape().getPivotBlock().getPosition().y;
            Block targetBlock = target.getBlockShape().getBlock(targetBlockIndex);
            Point2D.Double p1 = new Point2D.Double(getPivotPosition().x + 0.5, getPivotPosition().y + 0.5);
            Point2D.Double p2 = new Point2D.Double(targetBlock.getPosition().x + dx + 0.5, targetBlock.getPosition().y + dy + 0.5);
            shootLine.setLine(p1.x, p1.y, p2.x, p2.y);
        }
    }     
    
//     /**
//      * Set the glowing ellipse for drawing selection
//      */
//     private void setGlowEllipse()
//     {
//         final double WIDTH = 2*SELECTION_GLOW_WIDTH + ellipse.width;
//         final double HEIGHT = 2*SELECTION_GLOW_WIDTH + ellipse.height;
//         glowEllipse.setFrame(ellipse.x - SELECTION_GLOW_WIDTH, ellipse.y - SELECTION_GLOW_WIDTH, WIDTH, HEIGHT);
//     }    
//     
    /**
     * Let the unit take action
     * 
     * @param   timeStep    time step for taking action
     */
    public void takeAction(double timeStep)
    {
        unitAI.takeAction(timeStep);
        setShootLine();
        shootTimeCounter -= timeStep;
    }    
    
    /**
     * Move this object
     * 
     * @param  dest    destination
     */
    public void move(Point dest)
    {
        unitAI.move(dest);
    }
    
    /**
     * Command this unit to attack the specified unit
     * 
     * @param  target    the target to be attacked
     */
    public void attack(Unit target)
    {    
        unitAI.attack(target);
    }
    
    /**
     * Return the upper limit of the attack range of this unit
     * 
     * @return  the upper limit of the attack range of this unit in block coords
     */
    public int getAttackRangeUpperLimit()
    {
        return attackRangeUpperLimit;
    }
    
    /**
     * Return the lower limit of the attack range of this unit
     * 
     * @return  the lower limit of the attack range of this unit in block coords
     */
    public int getAttackRangeLowerLimit()
    {
        return attackRangeLowerLimit;
    } 
    
    /**
     * Return the attackPeriod of this unit
     * 
     * @return  the attackPeriod of this unit in seconds
     */
    public double getAttackPeriod()
    {
        return attackPeriod;
    }
 
    /**
     * Reset the attack time counter to attack time period
     */
    public void resetAttackTimeCounter()
    {
        attackTimeCounter = attackPeriod;
    }
    
    /**
     * Count down the attack time counter
     */
    public void countDownAttackTimeCounter(double timeStep)
    {
        attackTimeCounter -= timeStep;
    }
    
    /**
     * Get the attack time counter
     */
    public final double getAttackTimeCounter()
    {
        return attackTimeCounter;
    }
    
    /**
     * Shoot the target
     * 
     * @param target    target to be attacked
     * @param targetBlock   the block to be shoot at
     */
    public void shoot(Unit tar, final int blockIndex)
    {
// System.out.println("Shoot!");
// System.exit(1);
        shootTimeCounter = shootPeriod;
//         targetBlock = tBlock;
        targetBlockIndex = blockIndex;
        target = tar;
        target.takeAttack(attackPower);
    }    
    
    /**
     * Get the mode of this unit
     */
    public int getMode()
    {
        return mode;
    }

    /**
     * Set the mode of this unit
     * 
     * @param   md   the mode to be set
     */
    public void setMode(final int md)
    {
        mode = md;
    }
    
//     /**
//      * Get the block shape within the attack range of this unit
//      * 
//      * @return  block shape
//      */
//     public BlockShape getAttackRangeBlockShape()
//     {
//         return getBlockShape().getPivotBlock().getBlockShapeInRange(getAttackRangeUpperLimit());
//     }
//     /**
//      * Calculate the top left position of this circle block from its center position and radius
//      * 
//      * @return     center 
//      */
//     private static final Point2D.Double getCornerFromCenter(Point2D.Double centerPos, double rad)
//     {
//         return new Point2D.Double(centerPos.x - rad, centerPos.y - rad);
//     }  
    
    private Ellipse2D.Double ellipse;
//    private Ellipse2D.Double glowEllipse;
    
    private int mode;
    private int attackRangeUpperLimit;
    private int attackRangeLowerLimit;
    private double attackPeriod;
    private double attackTimeCounter;
    private double attackPower;
    private double shootPeriod;
    private double shootTimeCounter;
    private Line2D.Double shootLine;
    private Color shootColor;
    private Unit target;
    private int targetBlockIndex;
    
    public static final Dimension DIMENSION = new Dimension(3,3);
    public static final int HEALTH_CAPACITY = 100 * DIMENSION.width;
    public static final double MAX_SPEED = 5.; // blocks per second
    public static final int ATTACK_RANGE_LOWER_LIMIT = ((Math.max(DIMENSION.width, DIMENSION.height) - 1) / 2 + 1)
                                                        * Board.ORTHOGONAL_DISTANCE;
    public static final int ATTACK_RANGE_UPPER_LIMIT = ATTACK_RANGE_LOWER_LIMIT 
                                                       + (Math.max(DIMENSION.width, DIMENSION.height)-1)
                                                          * Board.ORTHOGONAL_DISTANCE;
    public static final int ATTACK_POWER = 20;
    public static final double ATTACK_PERIOD = 1.;
    public static final double SHOOT_PERIOD = 0.5;
    public static final int SIGHT_RANGE = ATTACK_RANGE_UPPER_LIMIT + (int)(ATTACK_RANGE_UPPER_LIMIT * 0.5);
    private static final double WHITE_EDGE_WIDTH = 0.1;
    private static final int SHOOT_COLOR_ALPHA = 240; // 0~255
    private static final BasicStroke SHOOT_STROKE = new BasicStroke(0.5f);
    
    private UnitAI unitAI;
}
