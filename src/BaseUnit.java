import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.Color;
import java.awt.Point;
import java.awt.Dimension;
// import java.util.ArrayList;

/**
 * Write a description of class BaseUnit here.
 * 
 * @author TuTu
 * @version 2007.11.9
 */
public class BaseUnit extends RectShapeUnit implements Generatable
{
    /**
     * Constructor for objects of class BaseUnit
     */
    public BaseUnit(Player owner, Board board, Point pos, int hCapacity, Dimension dim)
    {
//         super(b, Block.TYPE_BASE, x, y, owner, makePenetratability(b, x, y));
        super(owner, board, pos, TYPE_BASE, hCapacity, 0., dim);
//         rectangle = new Rectangle2D.Double();
//         glowRectangle = new Rectangle2D.Double();
//         setRectangle();
//         setGlowRectangle();
        
        unitAI = new UnitAI0(this, board);
        generatingPeriod = GENERATING_PERIOD;
        generatingTimeCounter = generatingPeriod;
        
        Color ownerColor = getOwner().getColor();
        baseColor = new Color((ownerColor.getRed() > 0 ? ownerColor.getRed() : 100),
                              (ownerColor.getGreen() > 0 ? ownerColor.getGreen() : 100),
                              (ownerColor.getBlue() > 0 ? ownerColor.getBlue() : 100),
                               BASE_COLOR_ALPHA);        
//         board.getBlock(pos).setStepOn(false);
//         for (int k = 0; k < iniNumUnits; k++)
//         {
//             Unit u = generateUnit(Unit.TYPE_ELLIPSE);
//         }
//         rectangle = new Rectangle2D.Double(pos.x, pos.y, 1, 1);        
    }    
    
    public BaseUnit(Player owner, Board board, Point pos)
    {
        this(owner, board, pos, HEALTH_CAPACITY, DIMENSION);
    }
    
//     /**
//      * Set the glowing rectangle for drawing selection
//      * 
//      * @return  the glowing rectangle to be drawn
//      */
//     private void setGlowRectangle()
//     {
//         final double WIDTH = 2*SELECTION_GLOW_WIDTH + rectangle.width;
//         final double HEIGHT = 2*SELECTION_GLOW_WIDTH + rectangle.height;
//         glowRectangle.setFrame(rectangle.x - SELECTION_GLOW_WIDTH, rectangle.y - SELECTION_GLOW_WIDTH, WIDTH, HEIGHT);
//     }    
    
    /**
     * Draw the block. Note: units are not included.
     * 
     * @param  g2   the context of the graphics
     * @param  layer    the layer to be drawn
     */
    public void draw(Graphics2D g2, final int layer)
    {
        g2.setStroke(Game.BASIC_STROKE);
        if (layer == 1)
        {
            if (getState() != Unit.STATE_STANDBY)
            {
                setRectangle();
            }
            
            g2.setColor(baseColor);
            g2.draw(rectangle);        
            g2.fill(rectangle);          
            
            if (isSelected())
            {
                drawSelection(g2);
            }
    //         Color ownerColor = getOwner().getColor();
            // make the base color lighter
    //         Color baseColor 
    //             = new Color((ownerColor.getRed() > 0 ? ownerColor.getRed() : 100),
    //                         (ownerColor.getGreen() > 0 ? ownerColor.getGreen() : 100),
    //                         (ownerColor.getBlue() > 0 ? ownerColor.getBlue() : 100),
    //                          COLOR_ALPHA);
        }
        else if (layer == 2)
        {
            if (getHealth() < getHealthCapacity() || isSelected())
            {
                drawHealth(g2);
            } 
        }
    }

//     /**
//      * Draw the selection glow of this unit.
//      * 
//      * @param  g2   the context of the graphics
//      */
//     protected void drawSelection(Graphics2D g2)
//     {
//         if (getSpeed() != 0.)
//         {
//             setGlowRectangle();
//         }
//         
//         g2.setColor(SELECTION_GLOW_COLOR);
//         g2.fill(glowRectangle);
//         g2.draw(glowRectangle);
//     }
    
    /**
     * Draw the health of this unit.
     * 
     * @param  g2   the context of the graphics
     */
    protected void drawHealth(Graphics2D g2)
    {
        g2.setStroke(Game.BASIC_STROKE);
        final double TOTAL_WIDTH = Unit.LENGH_PER_HEALTH_POINT * getHealthCapacity();
        final double HEIGHT = HEALTH_BAR_HEIGHT;
        final double WIDTH = TOTAL_WIDTH * getHealth() / getHealthCapacity();
//         Point2D.Double centerPosition = calculateCenterExactPosition();
        final double X = getDrawPosition().x + getDimension().width/2. - TOTAL_WIDTH/2.;
        final double Y = getDrawPosition().y;

        Rectangle2D.Double healthCapacityBar = new Rectangle2D.Double(X, Y, TOTAL_WIDTH, HEIGHT);
        Rectangle2D.Double healthBar = new Rectangle2D.Double(X, Y, WIDTH, HEIGHT);
        
        g2.setColor(Color.RED); 
        g2.fill(healthCapacityBar);
        g2.draw(healthCapacityBar);
        g2.setColor(Color.GREEN);
        g2.fill(healthBar);
        g2.setColor(Color.GREEN.darker());
        g2.draw(healthBar);
    }

    /**
     * Generate a unit with specified type
     * 
     * @param  TYPE    unit type to be generated
     * @return        true if generating succeed
     */
    public boolean generateUnit(final int TYPE)
    {
        return unitAI.generateUnit(TYPE);
    }
    
    /**
     * Let the unit take action
     * 
     * @param   timeStep    time step for taking action
     */
    public void takeAction(double timeStep)
    {    
        generatingTimeCounter -= timeStep;
// System.out.println("generatingTimeCounter = " + generatingTimeCounter);
        if (generatingTimeCounter <= 0.)
        {
// System.out.println("Generating unit!");
            generateUnit(Unit.TYPE_BASIC_BATTLE);
            generatingTimeCounter = generatingPeriod;
        }
    }     
    
    private double generatingTimeCounter;
    private double generatingPeriod;
    private Color baseColor;
    
    private static final int BASE_COLOR_ALPHA = 255; // 0~255;
    public static final int DEFAULT_INI_NUM_UNITS = 3;
//     private static UnitAI DEFAULT_AI = new UnitAI1();
    public static final Dimension DIMENSION = new Dimension(9, 9);
    public static final double GENERATING_PERIOD = 10.;
    private static final int HEALTH_CAPACITY = 100 * DIMENSION.width;    
    
    private UnitAI unitAI;
}
