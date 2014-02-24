import java.awt.geom.Point2D;

/**
 * class MathVector2D
 * Implements the two-dimensional vector in mathmatics
 * 
 * @author TuTu
 * @version 2007.11.10
 */
public class MathVector2D implements Cloneable
{
    public MathVector2D(Point2D.Double pStart, Point2D.Double pEnd)
    {
        x = pEnd.x - pStart.x;
        y = pEnd.y - pStart.y;
    }    

    public MathVector2D(double xx, double yy)
    {
        x = xx;
        y = yy;
    }
    
    public MathVector2D()
    {
        x = 0.;
        y = 0.;
    }

    /**
     * Creates and returns a copy of this object.
     * return  a clone of this instance.
     */
    public MathVector2D clone()
    {
        return  new MathVector2D(this.x, this.y);
    }    
    
    // instance methods
    /**
     * Set this vector
     * @param   vX  set to this.x
     * @param   vY  set to this.y
     */
    public double getLength()
    {
        return Math.sqrt(x*x + y*y);
    }    
    
    /**
     * Set this vector
     * @param   vX  set to this.x
     * @param   vY  set to this.y
     */
    public void setVector(double vX, double vY)
    {
        x = vX;
        y = vY;
    }
    
    /**
     * Add vector v to this vector
     * @param   v  vector to be added
     */
    public MathVector2D add(MathVector2D v)
    {
        return  new MathVector2D(x + v.x, y + v.y);
    }    

    /**
     * Multiply this vector with mult
     * @param   mult  number to be multiply
     * @return  the resulting vector
     */
    public MathVector2D multiply(double mult)
    {
        return  new MathVector2D(x * mult, y * mult);
    }
 
    /**
     * Return a normalized version of this vector
     * @return  the normalized vector
     */
    public MathVector2D getNormalized()
    {
        return this.multiply(1./this.getLength());
    }    
    
    // class methods
    /**
     * Static method to add two mathVectors
     * 
     * @param  v1   1st vector
     * @param  v2   2nd vector
     * * @return     the vector addition of v1 and v2 
     */
    public static MathVector2D add(MathVector2D v1, MathVector2D v2)
    {
        return  new MathVector2D(v1.x + v2.x, v1.y + v2.y);
    }

    /**
     * Static method to add this vector to a point p and get new point
     * 
     * @param  p   point
     * @param  v   vector
     * * @return     the vector inner product of x and y 
     */
    public static Point2D.Double add(Point2D.Double p, MathVector2D v)
    {
        return new Point2D.Double(p.x + v.x, p.y + v.y);
    }

    /**
     * Static method to make inner product of two mathVectors
     * 
     * @param  v1   1st vector
     * @param  v2   2nd vector
     * * @return     the vector inner product of x and y 
     */
    public static double dot(MathVector2D v1, MathVector2D v2)
    {
        return v1.x * v2.x + v1.y * v2.y;
    }    
    
    public double x;
    public double y;
}
