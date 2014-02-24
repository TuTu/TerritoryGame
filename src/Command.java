
/**
 * Write a description of interface Command here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public interface Command
{
    /**
     * Get the commander
     * 
     * @param  y    a sample parameter for a method
     * @return        the result produced by sampleMethod 
     */
    Player getCommander();
    
    /**
     * An example of a method header - replace this comment with your own
     * 
     * @param  y    a sample parameter for a method
     * @return        the result produced by sampleMethod 
     */
    Unit getExecuter();
}
