import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.event.*;
import java.awt.Dimension;

/**
 * Write a description of class RectShapeUnit here.
 * 
 * @author TuTu
 * @version %I%, %G%
 */
abstract public class RectShapeUnit extends Unit
{
    /**
     * Constructor for objects of class RectShapeUnit
     */
    public RectShapeUnit(Player owner, Board board, Point pos,
                         final int TYPE, final int hCapacity, double maxSpeed, Dimension dim)
    {
        super(owner, board, BlockShape.makeRectBlockShape(board, dim, pos), new Point2D.Double((double)pos.x, (double)pos.y), TYPE, hCapacity, maxSpeed);
        // ensure the width and the height of this unit are both odd integers, so that there is a symmetric central point
//         if ((dim.width/2. != dim.width/2) || (dim.height/2. != dim.height/2))
//         {
//             throw new IllegalArgumentException("The dimension width and height of a rectangle unit must be both odd integers!");
//         }
        dimension = dim;
        makePivotPosition();
        rectangle = new Rectangle2D.Double();
        glowRectangle = new Rectangle2D.Double();
        setRectangle();
//         setGlowRectangle();
//         numOfTotalBlocks = dim.width * dim.height;
//         blockPositions = new Point[numOfTotalBlocks];
//         makeBlockPositions();
    }
    
    /**
     * Set the rectangle for drawing
     * 
     * @return  the rectangle to be drawn
     */
    public void setRectangle()
    {
        rectangle.setFrame(getDrawPosition().x, getDrawPosition().y, getDimension().width, getDimension().height);
    }

//     public RectShapeUnit(Player owner, Board board, Point pos, Point2D.Double centerPos, double rad)
//     {
//         this(owner, board, pos, centerPos, rad, HEALTH_CAPACITY, MAX_SPEED);
//     }    
//     
//     public RectShapeUnit(Player owner, Board board, Point pos, Point2D.Double centerPos)
//     {
//         this(owner, board, pos, centerPos, RADIUS);
//     }
    
//     /**
//      * Test if a point is inside it.
//      * 
//      * @return Return true if a point is inside it.
//      */
//     public boolean isInside(Point2D.Double p)
//     {
//         if (p.x > getDrawPosition().x && p.x < (getDrawPosition().x + dimension.width)
//             && p.y > getDrawPosition().y && p.y < (getDrawPosition().y + dimension.height))
//             return true;
//         else
//             return false;
//     }
    
//     public void mouseClicked(MouseEvent e)
//     {
// //         AffineTransform affineT = (Graphics2D)(((Game.GamePanel)(getBoard().getPanel())).getGraphics()).getTransform();
// 
// //         if (e.getButton() == MouseEvent.BUTTON1)
// //         {
// //             Point2D.Double mousePos = new Point2D.Double(e.getX(), e.getY());
// //             getBoard().getDeviceToUserCoordTransform().transform(mousePos, mousePos);     
// //             if (mousePos.distance(circle.getCenterX(), circle.getCenterY()) <= radius)
// //                 selectIt();
// //             else
// //                 deSelectIt();
// //         }
//     }
// //     public void mouseEntered(MouseEvent event)
// //     public void mouseExited(MouseEvent event)
//     public void mousePressed(MouseEvent e)
//     {
//         getBoard().setMouseButtonStatus(e.getButton(), true);
//         if (e.getButton() == MouseEvent.BUTTON1)
//         {
//             Point2D.Double mousePos = new Point2D.Double(e.getX(), e.getY());
//             getBoard().getDeviceToUserCoordTransform().transform(mousePos, mousePos);     
//             if (mousePos.distance(circle.getCenterX(), circle.getCenterY()) <= radius)
//                 selectIt();
//             else
//                 deSelectIt();
//         }        
//     }
//     
//     public void mouseReleased(MouseEvent e)
//     {        
//         getBoard().setMouseButtonStatus(e.getButton(), false);    
//     }
//     public void mouseDragged(MouseEvent event)
//     public void mouseMoved(MouseEvent event)
    
    /**
     * Draw the body of this unit.
     * 
     * @param  g2   the context of the graphics
     * @param  layer    the layer to be drawn
     */
    abstract public void draw(Graphics2D g2, final int layer);
//     {
//         if (getVelocity().getLength() != 0.)
//             circle.setFrame(getBoardPosition().x, getBoardPosition().y, 2*radius, 2*radius);
//         Player owner = getOwner();
//         Color ownerColor = owner.getColor();
//         Color unitColor = ownerColor; // writing like this to make futre updating easier
//          
//         if (isSelected())
//             drawSelection(g2);
//         g2.setColor(unitColor);
//         g2.fill(circle);
//         g2.setColor(ownerColor.darker());        
//         g2.draw(circle);
//     }

    /**
     * Draw the selection glow of this unit.
     * 
     * @param  g2   the context of the graphics
     */
    public void drawSelection(Graphics2D g2)
    {
//         if (getState() != Unit.STATE_STANDBY)
//         {
//             setGlowRectangle();
//         }
//         g2.setColor(SELECTION_GLOW_COLOR);
//         g2.fill(glowRectangle);
//         g2.draw(glowRectangle);
        g2.setStroke(Unit.SELECTION_STROKE);
        g2.setColor(SELECTION_GLOW_COLOR);
        g2.draw(rectangle);
    }    
    
//     /**
//      * Set the glowing rectangle for drawing selection
//      */
//     private void setGlowRectangle()
//     {
//         final double WIDTH = 2*SELECTION_GLOW_WIDTH + rectangle.width + 0.1;
//         final double HEIGHT = 2*SELECTION_GLOW_WIDTH + rectangle.height + 0.1;
//         glowRectangle.setFrame(rectangle.x - SELECTION_GLOW_WIDTH, rectangle.y - SELECTION_GLOW_WIDTH, WIDTH, HEIGHT);
//     }    
    
    /**
     * Draw the health of this unit.
     * 
     * @param  g2   the context of the graphics
     */
    protected void drawHealth(Graphics2D g2)
    {
        final double TOTAL_WIDTH = Unit.LENGH_PER_HEALTH_POINT * getHealthCapacity();
        final double HEIGHT = HEALTH_BAR_HEIGHT;
        final double WIDTH = TOTAL_WIDTH * getHealth() / getHealthCapacity();
        Point2D.Double centerPosition = calculateCenterExactPosition();
        final double X = centerPosition.x - TOTAL_WIDTH/2.;
        double Y = getDrawPosition().y - HEIGHT;
        if (Y < 0.)
        {
            Y = 0.;
        }
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
     * Calculate and return the center position in paint coords of this unit
     * 
     * @return position
     */
    private final Point2D.Double calculateCenterExactPosition()
    {
        double halfWidth = (double)dimension.width/2.;
        double halfHeight = (double)dimension.height/2.;
        double centerX = (double)getDrawPosition().x + halfWidth;
        double centerY = (double)getDrawPosition().y + halfHeight;        
        return  new Point2D.Double(centerX, centerY);
    }
    
    /**
     * Get the dimension of this rect-shape unit
     * 
     * @return dimension
     */
    public final Dimension getDimension()
    {
        return dimension;
    }
    
//    /**
//     * Make and return the block shape of this unit
//     */
//    private static BlockShape makeBlockShape(Board board, Dimension dim, Point pos)
//    {
//        BlockShape bShape = new BlockShape(board);
//        for (int i = 0; i < dim.width * dim.height; i++)
//        {
//            bShape.addBlock(new Point((pos.x + (i % dim.width)), (pos.y + (i / dim.width))));
//        }
//        bShape.setPivotBlock(board.getBlock(new Point((pos.x + (dim.width/2)), (pos.y + (dim.height/2)))));
////         bShape.setPivotBlockIndex((int)(dim.width * dim.height / 2));
//        return bShape;
//    }
    
    /**
     * Make the pivotPosition according to the drawPosition of this unit
     */
    protected void makePivotPosition()
    {
        setPivotPosition(getDrawPosition().x + (getDimension().width/2), getDrawPosition().y + (getDimension().height/2));
    }
    
    /**
     * Make the drawPosition according to the pivotPosition of this unit
     */
    protected void makeDrawPosition()
    {
        setDrawPosition(getPivotPosition().x - (getDimension().width/2), getPivotPosition().y - (getDimension().height/2));
    }
    
//     /**
//      * Get opLeftPosition
//      */
//     public Point getTopLeftPosition()
//     {
//         return topLeftPosition;
//     }
    
//     /**
//      * get the position of the specified block by the index 
//      * 
//      * @return blockPositions
//      */
//     public final Point getBlockPosition(int i)
//     {
//         if (i < 0 || i > numOfTotalBlocks)
//             throw new ArrayIndexOutOfBoundsException(i);
//         else
//             return blockPositions[i];
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
    
//     private Ellipse2D.Double circle;
    private Dimension dimension;
    protected Rectangle2D.Double rectangle;
    protected Rectangle2D.Double glowRectangle;
//     private Point topLeftPosition;
    
//     public static final double RADIUS = 0.1;
//     private static final int HEALTH_CAPACITY = 100;
//     private static final double MAX_SPEED = 0.5; // blocks per second
}
