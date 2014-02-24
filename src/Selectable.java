import java.awt.Graphics2D;

/**
 * Write a description of interface Selectable here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public interface Selectable
{
    /**
     * Return true if this unit is selected by user.
     * 
     * @return     true or false
     */
    public boolean isSelected();
    
    /**
     * Select this unit
     * 
     */
    public void selectIt();
    
    /**
     * De-select this unit
     * 
     */
    public void deSelectIt();
    
     /**
      * Draw the selection glow of this unit.
      * 
      * @param  g2   the context of the graphics
      */
     void drawSelection(Graphics2D g2);    
}
