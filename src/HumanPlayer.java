import java.util.ArrayList;
import java.awt.Color;
import java.awt.Point;

/**
 * Write a description of class Human here.
 * 
 * @author TuTu
 * @version 2007.11.12
 */
public class HumanPlayer extends Player
{
    /**
     * Constructor for objects of class HumanPlayer
     */
    public HumanPlayer(int i, int j)
    {
        super(i, j);
        selectedUnitList = new ArrayList<Selectable>(INI_NUM_SELECT_LIMIT);
        selectedTarget = null;
    }
    
    /**
     * Get a selected unit.
     * 
     * @param  index    index of the selected unit
     */
    public Unit getSelectedUnit(int index)
    {
        return (Unit)selectedUnitList.get(index);
    }
    
    /**
     * Get selectedUnitListSize
     * 
     * @return  selectedUnitListSize
     */
    public int getSelectedUnitListSize()
    {
        return selectedUnitList.size();
    }    
    
//     /**
//      * Return true if human player has selected his own unit
//      * 
//      * @return   true if human player has selected his own unit
//      */
//     public boolean isSelectingOwnUnit()
//     {
//         for (int i = 0; i < selectedUnitList.size(); i++)
//         {
//             if (getSelectedUnit(i).getOwner().equals(this) 
//                 && getSelectedUnit(i).isAttackable())
//             {
//                 return true;
//             }
//         }
//         return false;
//     }    
    
    /**
     * Select a unit.
     * Add a unit to selectedUnitList and change its selected state.
     * 
     * @param   unit to be selected
     */
    public void selectUnit(Selectable u)
    {
        if (!selectedUnitList.contains(u))
        {
            selectedUnitList.add(u);
            u.selectIt();
        }
    }
    
    /**
     * Select a target.
     * Assign the specified unit to selectedTarget and change its selected state.
     * 
     * @param   target to be selected
     */
    public void selectTarget(Selectable u)
    {
        if (selectedTarget == null)
        {
            selectedTarget = u;
            u.selectIt();
        }
        else if (!selectedTarget.equals(u))
        {
            deSelectTarget();
            selectedTarget = u;
            u.selectIt();
        }
    }    

    /**
     * De-select the target.
     * Remove the target unit from selectedTarget and change its selected state.
     */
    public void deSelectTarget()
    {
        if (selectedTarget != null)
        {
            selectedTarget.deSelectIt();
            selectedTarget = null;
        }
        else
        {
            System.out.println("Human has tried to de-select a non-existence target!");
        }
    }    
    
    /**
     * De-select a unit.
     * Remove a unit from selectedUnitList and change its selected state.
     * 
     * @param   unit to be de-selected
     */
    public void deSelectUnit(Selectable u)
    {
        if (selectedUnitList.remove(u))
        {
            u.deSelectIt();
        }
        else
        {
            System.out.println("Human has tried to de-select a non-existence unit!");
        }
    }
    
    /**
     * De-select all units of this player.
     * Remove all unit from selectedUnitList and change their selected state.
     */
    public void deSelectAllUnit()
    {
        for (int i = 0; i < selectedUnitList.size(); i++)
        {
            getSelectedUnit(i).deSelectIt();
        }
        selectedUnitList.clear();
        
        if (selectedTarget != null)
        {
            deSelectTarget();
        }
    }
    
    /**
     * Order the selected units to move to the destination
     * 
     * @param   dest    moving destination
     */
    public void orderMove(Point dest)
    {
        for (int i = 0; i < selectedUnitList.size(); i++)
        {
            if (getSelectedUnit(i).getOwner().equals(this) 
                && getSelectedUnit(i).isMoveable())
            {
                ((Moveable)getSelectedUnit(i)).move(dest);
            }
        }
    } 
    
    /**
     * Order the selected units to attack the target
     * 
     * @param   target    target to be attacked
     */
    public void orderAttack(Unit target)
    {
        for (int i = 0; i < selectedUnitList.size(); i++)
        {
            if (getSelectedUnit(i).getOwner().equals(this) 
                && getSelectedUnit(i).isAttackable())
            {
                selectTarget(target);
                ((Attackable)getSelectedUnit(i)).attack(target);
            }
        }
    }    
        
    
//     private Player player;
//     private Board board;
    private ArrayList<Selectable> selectedUnitList;
    private Selectable selectedTarget;
    
    private static final int INI_NUM_SELECT_LIMIT = 20;
}
