
/**
 * Write a description of interface Attackable here.
 * 
 * @author TuTu
 * @version 2007.11.26
 */

public interface Attackable
{
    /**
     * Command this unit to attack the specified unit
     * 
     * @param  target    the target to be attacked
     */
    void attack(Unit target);
    
    /**
     * Return the upper limit of the attack range of this unit
     * 
     * @return  the upper limit of the attack range of this unit in block coords
     */
    int getAttackRangeUpperLimit();
    
   /**
     * Return the lower limit of the attack range of this unit
     * 
     * @return  the lower limit of the attack range of this unit in block coords
     */
    int getAttackRangeLowerLimit();
    
    /**
     * Return the attackPeriod of this unit
     * 
     * @return  the attackPeriod of this unit in seconds
     */
    double getAttackPeriod();
    
    /**
     * Reset the attack time counter to attack time period
     */
    void resetAttackTimeCounter();

    /**
     * Count down the attack time counter
     */
    void countDownAttackTimeCounter(double timeStep);
    
    /**
     * Get the attack time counter
     */
    double getAttackTimeCounter();
    
    /**
     * Shoot the target
     * 
     * @param target    target to be attacked
     * @param targetBlockIndex   the index of the block to be shoot at
     */
    void shoot(Unit target, int targetBlockIndex);
    
    /**
     * Get the mode of this unit
     * 
     * @return  mode
     */
    int getMode();
    
//     /**
//      * Get the block shape within the attack range of this unit
//      * 
//      * @return BlockShape
//      */
//     BlockShape getAttackRangeBlockShape();
    
    public static final int MODE_STANDSTILL = 0;
    public static final int MODE_DEFENSE = 1;
    public static final int MODE_NORMAL = 2;
    public static final int MODE_OFFENSE = 3;
}
