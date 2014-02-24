import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.Color;
import java.awt.Point;
import java.awt.Dimension;

/**
 * Write a description of class ControlPointUnit here.
 * 
 * @author TuTu
 * @version 2007.11.12
 */
public class ControlPointUnit extends RectShapeUnit implements Capturable
{
    /**
     * Constructor for objects of class ControlPointUnit
     */
    public ControlPointUnit(Player owner, Board board, Point pos)
    {
        // control point has no health, thus 0
        super(owner, board, pos, TYPE_RESOURCE, 0, 0, BASIC_DIMENSION);
        rectangle = new Rectangle2D.Double(pos.x, pos.y, 1, 1);
        unitAI = new UnitAI0(this, board);
    }
    
    /**
     * Draw the body of this unit.
     * 
     * @param  g2   the context of the graphics
     * @param  layer    the layer to be drawn
     */
    public void draw(Graphics2D g2, final int layer)
    {
//         if (getVelocity().getLength() != 0.)
//             ellipse.setFrame(getExactPosition().x, getExactPosition().y, getDimension().width, getDimension().height);
        Player owner = getOwner();
        Color unitColor;
//         Color unitColor = ownerColor; // writing like this to make futre updating easier
 
        if (owner.getColor() == Player.getMotherColor())
        {
            unitColor = Color.GRAY;
        }
        else
        {
            unitColor = owner.getColor();
//             unitColor = new Color(ownerColor.getRed(), ownerColor.getGreen(), ownerColor.getBlue(), COLOR_ALPHA);
        }        
        
        if (isSelected())
            drawSelection(g2);
        g2.setColor(unitColor);
        g2.fill(rectangle);
//         g2.setColor(ownerColor.darker());        
        g2.draw(rectangle);
//         drawHealth(g2);
    }

    /**
     * Draw the selection glow of this unit.
     * 
     * @param  g2   the context of the graphics
     */
    public void drawSelection(Graphics2D g2)
    {
        final double GLOW_WIDTH = 0.05;
        final double WIDTH = 2*GLOW_WIDTH + rectangle.width;
        final double HEIGHT = 2*GLOW_WIDTH + rectangle.height;
        Ellipse2D.Double glowRect = new Ellipse2D.Double(rectangle.x - GLOW_WIDTH, rectangle.y - GLOW_WIDTH, WIDTH, HEIGHT);
        
        g2.setColor(SELECTION_GLOW_COLOR);
        g2.fill(glowRect);
        g2.draw(glowRect);
    }     
    
    
//     /**
//      * Draw the block. Note: units are not included.
//      * 
//      * @param  g2   the context of the graphics
//      */
//     public void draw(Graphics2D g2)
//     {
//         Color resourceColor;
//         if (getOwner().getSide() == Player.MOTHER)
//         {
//             resourceColor = Color.GRAY;
//         }
//         else
//         {
//             Color ownerColor = getOwner().getColor();
//             resourceColor = new Color(ownerColor.getRed(), ownerColor.getGreen(), ownerColor.getBlue(), COLOR_ALPHA);
//         }
//         Rectangle2D.Double rect = new Rectangle2D.Double(getPosition().x, getPosition().y, 1., 1.);
//         g2.setColor(resourceColor);
// //         g2.draw(rect);        
//         g2.fill(rect);
//     }

    /**
     * Let the unit take action
     * 
     * @param   timeStep    time step for taking action
     */
    public void takeAction(double timeStep)
    {
    } 

    private Rectangle2D.Double rectangle;
    private Rectangle2D.Double glowRectangle;
    private static final int COLOR_ALPHA = 255; // 0~255;
    private static final Dimension BASIC_DIMENSION = new Dimension(3,3);   
    
    private UnitAI unitAI;
}
