import java.awt.Point;
import java.awt.geom.*;
import java.util.ArrayList;

/**
 * Write a description of interface UnitAI here.
 * 
 * @author TuTu
 * @version 2007.11.15
 */
public abstract class UnitAI
{
    /**
     * Constructor for objects of class UnitAI
     */
    public UnitAI(Unit u, Board b)
    {
        unit = u;
        board = b;
        target = null;
//         fitInBlock = true;
//         midDestination = ;
    }

    /**
     * Take actions according to the state of this unit, such as move, attack, etc.
     * 
     * @param  timeStep    timeStep
     */
    public void takeAction(double timeStep)
    {
        if (unit.getState() == Unit.STATE_STANDBY)
        {
            if (unit.isAttackable())
            {
                Attackable attackUnit = (Attackable)unit;
                if (attackUnit.getMode() == Attackable.MODE_NORMAL)
                {
                    BlockShape sightRangeBlockShape 
                        = unit.getBlockShape().getPivotBlock().getBlockShapeInRange(unit.getSightRange());
                    ArrayList<Unit> unitList = sightRangeBlockShape.getUnitList();
                    if (unitList.size() > 1) // if there is OTHER unit
                    {
                        for (int i = 0; i < unitList.size(); i++)
                        {
                            if (!unitList.get(i).getOwner().equals(unit.getOwner())
                                && !unitList.get(i).getOwner().equals(board.getMotherPlayer()))
                            {
                                attack(unitList.get(i));
                            }
                        }
                    }
                }
            }
        }
        if (unit.getState() == Unit.STATE_MOVE)
        {
            if (move(timeStep))
            {
                unit.setState(Unit.STATE_STANDBY);
            }
        }
        else if (unit.getState() == Unit.STATE_ATTACK)
        {
// System.out.println("In STATE_ATTACK");
            if (target.isAlive())
            {
// System.out.println("attack(timeStep)");
                attack(timeStep);  
            }
            else
            {
// System.out.println("unit.setState(Unit.STATE_STANDBY)");
                // target distroyed
                unit.setState(Unit.STATE_STANDBY);
            }
        }
    }
    
    /**
     * Generate a unit with specified type
     * 
     * @param  TYPE    unit type to be generated
     * @return        true if generating succeed
     */
    public abstract boolean generateUnit(final int childType);

    /**
     * Move a unit to the destination block
     * This is the method called by Unit.
     * 
     * @param  dest    destination
     * @return        true if generating succeed
     */
    public void move(final Point dest)
    {
        unit.setState(Unit.STATE_MOVE);
        destination = dest;
    }
    
    /**
     * Move a unit to the destination block
     * 
     * @param  dest    destination
     * @return        true if generating succeed
     */
    protected abstract boolean move(double timeStep);

    /**
     * Move a unit to the direction with specified time step
     * 
     * @param  dir      direction
     * @param  timeStep time step
     * @param  dest     destination point
     * @return  true if the unit has reached the destination point
     */
    protected abstract boolean move(Direction dir, Point dest, double timeStep);
    
    /**
     * Command this unit to attack the specified unit
     * 
     * @param  target    the target to be attacked
     */
    protected void attack(Unit tar)
    {
        unit.setState(Unit.STATE_ATTACK);
        target = tar;
    }

    /**
     * Perform the attack action
     * 
     * @param  timeStep    the timeStep
     */
    protected abstract void attack(double timeStep);
    
    protected Point destination;
    protected Unit target;
//     private int state;
    
    protected Unit unit;
    protected Board board;
//     protected ArrayList orderList;
    
//     protected static final int STATE_STANDBY = 0;
//     protected static final int STATE_MOVE = 1;
}
